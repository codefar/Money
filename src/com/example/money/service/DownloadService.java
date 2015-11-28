package com.example.money.service;

import android.app.IntentService;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.res.Resources;
import android.net.Uri;
import android.os.Build;
import android.os.Environment;
import android.os.Handler;
import android.os.IBinder;
import android.os.Message;
import android.preference.PreferenceManager;
import android.support.v4.app.NotificationCompat;
import android.text.TextUtils;
import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.util.IOUtils;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.example.money.Constants;
import com.example.money.DownloadDialogActivity;
import com.example.money.R;
import com.example.money.AoShan;
import com.example.money.Url;
import com.example.money.common.FastJsonRequest;
import com.example.money.common.SydVolleyResponseErrorListener;
import com.example.money.entity.VersionEntity;
import com.example.money.utils.Logger;
import com.example.money.utils.StorageUtil;
import com.example.money.utils.ToastUtil;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;

/**
 * Created by su on 2014/7/30.
 */
public class DownloadService extends IntentService {

    private static final String TAG = DownloadService.class.getSimpleName();
    private NotificationManager mNotificationManager;
    private Resources mResources;
    private NotificationCompat.Builder mBuilder;

    private static final int DOWNLOAD_STORAGE_ERROR = 1;
    private static final int DOWNLOAD_NETWORK_ERROR = 2;

    public static final String ACTION_CHECK_VERSION = "check_version";
    public static final String ACTION_DOWNLOAD_WITH_DOWNLOAD_MANAGER = "download_with_download_manager";
    private static final int ACTION_NOTIFICATION_DOWNLOAD = 0;
    private static final int ACTION_NOTIFICATION_REPEAT = 1;

    private static final int UI_TOAST_DOWNLOAD_STORAGE_ERROR = 1;
    private static final int UI_TOAST_DOWNLOAD_NETWORK_ERROR = 2;
    private static final int UI_TOAST_DOWNLOAD_URL_ERROR = 3;

    private static final int NOTIFICATION_DOWNLOAD_PROGRESS = 0x10;
    private static final int NOTIFICATION_DOWNLOAD_FINISH = 0x11;

    private Handler mHandler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            switch (msg.what) {
                case UI_TOAST_DOWNLOAD_STORAGE_ERROR:
                    ToastUtil.showLongToast(DownloadService.this, R.string.download_failed_check_sdcard);
                    break;
                case UI_TOAST_DOWNLOAD_NETWORK_ERROR:
                case UI_TOAST_DOWNLOAD_URL_ERROR:
                    ToastUtil.showLongToast(DownloadService.this, R.string.internet_exception);
                    break;
                case NOTIFICATION_DOWNLOAD_PROGRESS:
                    int progress = msg.arg1;
                    showDownloadProgressNotification(progress, 100, getString(R.string.app_name), getString(R.string.download_progress_percent, String.valueOf(progress)), null);
                    break;
                case NOTIFICATION_DOWNLOAD_FINISH:
                    Intent downloadIntent = downloadIntent();
                    downloadIntent.setClass(DownloadService.this, DownloadService.class);
                    PendingIntent contentIntent = PendingIntent.getService(DownloadService.this, 0, downloadIntent, PendingIntent.FLAG_CANCEL_CURRENT);
                    showDownloadProgressNotification(0, 0, getString(R.string.download_failed_notification_content),
                            AoShan.sApplicationLabel + getString(R.string.update_failed), contentIntent);
                    break;
                default:
                    break;
            }
        }
    };

    private void showDownloadProgressNotification(final int progress, final int maxProgress, final String title,
                                                  final String content, final PendingIntent contentIntent) {
        mBuilder.setSmallIcon(R.drawable.ic_launcher)
                .setContentTitle(title)
                .setContentText(content)
                .setContentIntent(contentIntent)
                .setProgress(maxProgress, progress, false)
                .setAutoCancel(true);
        mNotificationManager.notify(ACTION_NOTIFICATION_DOWNLOAD, mBuilder.build());
    }

    public DownloadService() {
        super("DownloadService");
    }

    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }

    @Override
    protected void onHandleIntent(Intent intent) {
        if (intent != null) {
            String action = intent.getAction();
            if (ACTION_CHECK_VERSION.equals(action)) {
                boolean autoDownload = intent.getBooleanExtra("auto_download", false); // re-download/force download
                boolean showDialog = intent.getBooleanExtra("show_dialog", false);
                checkVersion(autoDownload, showDialog);
            } else if (ACTION_DOWNLOAD_WITH_DOWNLOAD_MANAGER.equals(action)) {
                File externalStorage = Environment.getExternalStorageDirectory();
                if (!externalStorage.exists() || !StorageUtil.isExternalStorageAvailable()) {
                    mHandler.sendEmptyMessage(UI_TOAST_DOWNLOAD_STORAGE_ERROR);
                    stopSelf();
                } else {
                    final String url = intent.getStringExtra("url");
                    final String version = intent.getStringExtra("version");
                    final String fileName = intent.getStringExtra("file_name");

                    File dir = new File(externalStorage.getAbsolutePath() + File.separator
                            + Constants.APP_DIR_TEMP);
                    if (!dir.exists()) {
                        dir.mkdirs();
                    }

                    final File file = new File(externalStorage.getAbsolutePath() + File.separator
                            + Constants.APP_DIR_TEMP + File.separator + fileName);

                    if (TextUtils.isEmpty(url)) {
                        notifyRepeatDownload(version);
                        stopSelf();
                    } else {
                        downloadWithHttp(url, file, new DownloadListener() {
                            @Override
                            public void onDownloadComplete() {
                                Logger.i("###### downloadFile ######## " + file.getAbsolutePath());
                                mHandler.post(new Runnable() {
                                    @Override
                                    public void run() {
                                        showSuccessfulNotification(file, version);
                                        install(file);
                                    }
                                });
                            }

                            @Override
                            public void onDownloadFailed(int errorCode, String errorMessage) {
                                Logger.i("###### onDownloadFailed ######## " + "errorCode + : " + errorCode + " : " + errorMessage);
                                mHandler.obtainMessage(NOTIFICATION_DOWNLOAD_FINISH).sendToTarget();
                            }

                            @Override
                            public void onProgress(long totalBytes, long downloadedBytes, int progress) {
                                Logger.i("###### onProgress ######## " + " : " + progress);
                                mHandler.obtainMessage(NOTIFICATION_DOWNLOAD_PROGRESS, progress, (int) downloadedBytes).sendToTarget();
                            }
                        });
                    }
                }
            }
        }

    }

    @Override
    public void onDestroy() {
        super.onDestroy();
    }

    @Override
    public void onCreate() {
        super.onCreate();
        mNotificationManager = (NotificationManager) getSystemService(Context.NOTIFICATION_SERVICE);
        mResources = getResources();
        mBuilder = new NotificationCompat.Builder(this);
    }

    /**
     * 下载文件
     *
     * @param url
     * @param downloadFile
     * @param downloadListener
     */
    private void downloadWithHttp(final String url, final File downloadFile, final DownloadListener downloadListener) {
        if (!StorageUtil.isExternalStorageAvailable()) {
            notifyDownloadDiskError(downloadListener);
            return;
        }
        Logger.i("###### downloadFile ######## " + downloadFile.getAbsolutePath() + " url=" + url);
        HttpURLConnection urlConnection = null;
        try {
            URL downloadUrl = new URL(url);
            urlConnection = (HttpURLConnection) downloadUrl.openConnection();
            urlConnection.setRequestProperty("Accept-Encoding", "identity");
            urlConnection.connect();
            if (urlConnection.getResponseCode() == HttpURLConnection.HTTP_OK) {
                long contentLength = urlConnection.getContentLength();
                if (isAlreadyDownload(downloadFile, contentLength)) {
                    downloadListener.onDownloadComplete();
                } else {
                    saveStreamToFile(downloadFile, downloadListener, contentLength, urlConnection.getInputStream());
                }
                return;
            }
            throw new IOException("net wrong response code = " + urlConnection.getResponseCode());
        } catch (MalformedURLException e) {
            mHandler.obtainMessage(UI_TOAST_DOWNLOAD_URL_ERROR).sendToTarget();
        } catch (IOException e) {
            notifyDownloadNetError(downloadListener);
        } finally {
            if (urlConnection != null) {
                urlConnection.disconnect();
            }
        }
    }

    private void notifyDownloadDiskError(DownloadListener downloadListener) {
        mHandler.obtainMessage(UI_TOAST_DOWNLOAD_STORAGE_ERROR).sendToTarget();
        if (downloadListener != null) {
            downloadListener.onDownloadFailed(DOWNLOAD_STORAGE_ERROR, "");
        }
    }

    private void notifyDownloadNetError(DownloadListener downloadListener) {
        mHandler.obtainMessage(DOWNLOAD_NETWORK_ERROR).sendToTarget();
        if (downloadListener != null) {
            downloadListener.onDownloadFailed(DOWNLOAD_NETWORK_ERROR, "");
        }
    }

    /**
     * 检查文件是否已经下载
     *
     * @param downloadFile
     * @param contentLength
     * @return　true 已经下载完成　false
     */
    private boolean isAlreadyDownload(File downloadFile, long contentLength) {
        Logger.i("###### downloadFile ######## " + " contentLength=" + contentLength);
        if (downloadFile.exists()) {
            Logger.i("###### downloadFile ######## " + " downloadFile =" + downloadFile.length());
            return (downloadFile.length() == contentLength);
        }
        return false;
    }

    /**
     * 将流保存至文件
     *
     * @param downloadFile     　目标文件
     * @param downloadListener 　进度回调接口
     * @param fileSize         　     文件总长度
     * @param inputStream
     */
    private void saveStreamToFile(File downloadFile, DownloadListener downloadListener, long fileSize, InputStream inputStream) {
        File tempFile = new File(downloadFile.getParentFile(), downloadFile.getName() + ".tmp");
        if (tempFile.exists()) {
            tempFile.delete();
        }
        try {
            FileOutputStream fileOutputStream = new FileOutputStream(tempFile);
            if (isHasEnoughDiskSpace(fileSize)) {
                copyStream(downloadListener, fileSize, inputStream, fileOutputStream);
                tempFile.renameTo(downloadFile);
                return;
            }
            throw new IOException("no enough diskspace");
        } catch (IOException e) {
            e.printStackTrace();
            notifyDownloadDiskError(downloadListener);
        } finally {
            IOUtils.close(inputStream);
        }
    }

    private boolean isHasEnoughDiskSpace(long needSize) {
        return needSize <= 0 || (needSize > 0 && StorageUtil.isExternalStorageAvailable(needSize));
    }

    /**
     * 复制流
     *
     * @param downloadListener
     * @param contentLength
     * @param inputStream
     * @param fileOutputStream
     * @throws IOException
     */
    private void copyStream(DownloadListener downloadListener, long contentLength, InputStream inputStream, FileOutputStream fileOutputStream) throws IOException {
        long totalReadBytes = 0, lastDownLoadProgress = -1;
        int readBytes;
        byte[] buffer = new byte[2 * 1024]; //设置缓冲区大小 2M
        while ((readBytes = inputStream.read(buffer)) != -1) {
            fileOutputStream.write(buffer, 0, readBytes);
            totalReadBytes += readBytes;
            int progress = calculateProgress(contentLength, totalReadBytes);
            if (downloadListener != null && progress > lastDownLoadProgress) {
                downloadListener.onProgress(contentLength, totalReadBytes, progress);
                lastDownLoadProgress = progress;
            }
        }
        fileOutputStream.flush();
        if (downloadListener != null)
            downloadListener.onDownloadComplete();
    }

    private int calculateProgress(long totalBytes, long totalReadBytes) {
        int progress = 0;
        if (totalBytes > 0) {
            progress = (int) ((totalReadBytes * 100) / totalBytes);
        }
        return progress;
    }

    private void checkVersion(final boolean autoDownload, final boolean showDialog) {
        String jsonDataUrl = AoShan.HOST + Url.VERSION_CHECK_SUFFIX
                + "?version=" + AoShan.sVersionName
                + "&versionCode=" + AoShan.sVersionCode
                + "&appName=" + getPackageName()
                + "&sdkInt=" + Build.VERSION.SDK_INT;
        FastJsonRequest jsonObjectRequest = new FastJsonRequest(Request.Method.GET, jsonDataUrl, null,
                new Response.Listener<com.alibaba.fastjson.JSONObject>() {
                    @Override
                    public void onResponse(com.alibaba.fastjson.JSONObject response) {
                        if (response.getInteger("errorCode") == 0) {
                            String data = response.getString("data");
                            SharedPreferences sp = PreferenceManager.getDefaultSharedPreferences(DownloadService.this);
                            VersionEntity ve = JSON.parseObject(data, VersionEntity.class);
                            if (ve.isNewVersion()) {
                                sp.edit().putBoolean(AoShan.SP_COLUMN_NEED_TO_UPDATE, true).
                                        putBoolean(AoShan.SP_COLUMN_FORCE_TO_UPDATE, ve.isForce()).
                                        putString(AoShan.SP_COLUMN_NEW_VERSION, ve.getVersion()).
                                        putString(AoShan.SP_COLUMN_NEW_VERSION_URL, ve.getUrl()).
                                        putString(AoShan.SP_COLUMN_NEW_VERSION_DESCRIPTION, ve.getDescription()).
                                        putString(AoShan.SP_COLUMN_NEW_VERSION_APK_NAME, getNewApkName(ve.getVersion())).apply();

                                if (autoDownload) {
                                    startUpdateVersion(ve);
                                } else if (showDialog) {
                                    showUpdateDialog(ve);
                                    stopSelf();
                                }
                            } else {
                                sp.edit().putBoolean(AoShan.SP_COLUMN_NEED_TO_UPDATE, false)
                                        .putBoolean(AoShan.SP_COLUMN_FORCE_TO_UPDATE, false).apply();
                            }
                        } else {
                            mHandler.sendEmptyMessage(UI_TOAST_DOWNLOAD_NETWORK_ERROR);
                        }
                    }
                }, new SydVolleyResponseErrorListener(this, TAG, jsonDataUrl) {
            @Override
            public void onResponseFail(VolleyError volleyError) {
                mHandler.sendEmptyMessage(UI_TOAST_DOWNLOAD_NETWORK_ERROR);
            }
        });
        AoShan.sRequestQueue.add(jsonObjectRequest);
    }

    private void showUpdateDialog(VersionEntity ve) {
        Intent intent = new Intent(DownloadService.this, DownloadDialogActivity.class);
        intent.putExtra("title", ve.getTitle());
        intent.putExtra("description", ve.getDescription());
        intent.putExtra("url", ve.getUrl());
        intent.putExtra("force", ve.isForce());
        intent.putExtra("version", ve.getVersion());
        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        startActivity(intent);
    }

    private void startUpdateVersion(VersionEntity ve) {
        Intent intent = new Intent(DownloadService.this, DownloadService.class);
        intent.setAction(ACTION_DOWNLOAD_WITH_DOWNLOAD_MANAGER);
        intent.putExtra("url", ve.getUrl());
        intent.putExtra("version", ve.getVersion());
        intent.putExtra("file_name", getNewApkName(ve.getVersion()));
        startService(intent);
    }

    private void notifyRepeatDownload(String version) {
        NotificationManager notificationManager = (NotificationManager) getSystemService(Context.NOTIFICATION_SERVICE);

        NotificationCompat.Builder builder = new NotificationCompat.Builder(this);
        builder.setContentTitle(mResources.getString(R.string.download_failed_notification_title, version))
                .setContentText(mResources.getString(R.string.download_failed_notification_content))
                .setAutoCancel(true)
                .setSmallIcon(R.drawable.ic_launcher);
        notificationManager.notify(ACTION_NOTIFICATION_REPEAT, builder.build());
    }

    private void showSuccessfulNotification(File file, String version) {
        Intent installIntent = installIntent(file);
        mBuilder.setAutoCancel(true)
                .setSmallIcon(R.drawable.ic_launcher)
                .setContentText(mResources.getString(R.string.download_successful_notification_content))
                .setContentTitle(mResources.getString(R.string.download_successful_notification_title, version))
                .setProgress(0, 0, false);
        PendingIntent contentIntent = PendingIntent.getActivity(DownloadService.this, 0, installIntent, PendingIntent.FLAG_CANCEL_CURRENT);
        mBuilder.setContentIntent(contentIntent);
        mNotificationManager.notify(ACTION_NOTIFICATION_DOWNLOAD, mBuilder.build());
    }

    private void install(File file) {
        Intent installIntent = installIntent(file);
        startActivity(installIntent);
    }

    private Intent installIntent(File file) {
        Intent intent = new Intent(Intent.ACTION_VIEW);
        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        intent.setDataAndType(Uri.fromFile(file), "application/vnd.android.package-archive");
        return intent;
    }

    private Intent downloadIntent() {
        SharedPreferences sp = PreferenceManager.getDefaultSharedPreferences(this);
        Intent intent = new Intent(DownloadService.ACTION_DOWNLOAD_WITH_DOWNLOAD_MANAGER);
        intent.putExtra("url", sp.getString(AoShan.SP_COLUMN_NEW_VERSION_URL, ""));
        intent.putExtra("version", sp.getString(AoShan.SP_COLUMN_NEW_VERSION, ""));
        intent.putExtra("file_name", "aoshan" + sp.getString(AoShan.SP_COLUMN_NEW_VERSION, "") + ".apk");
        return intent;
    }

    public static String getNewApkName(String version) {
        return "aoshan" + version + ".apk";
    }

    public interface DownloadListener {
        void onDownloadComplete();

        void onDownloadFailed(int errorCode, String errorMessage);

        void onProgress(long totalBytes, long downloadedBytes, int progress);
    }
}
