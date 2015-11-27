package com.example.money.common;

import java.io.File;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.security.KeyStore;
import java.text.SimpleDateFormat;
import java.util.HashMap;
import java.util.Map;
import java.util.regex.Pattern;

import org.apache.http.HttpVersion;
import org.apache.http.client.HttpClient;
import org.apache.http.conn.ClientConnectionManager;
import org.apache.http.conn.scheme.PlainSocketFactory;
import org.apache.http.conn.scheme.Scheme;
import org.apache.http.conn.scheme.SchemeRegistry;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.impl.conn.tsccm.ThreadSafeClientConnManager;
import org.apache.http.params.BasicHttpParams;
import org.apache.http.params.HttpParams;
import org.apache.http.params.HttpProtocolParams;
import org.apache.http.protocol.HTTP;

import com.example.money.Constants;
import com.example.money.MainActivity;
import com.example.money.AoShan;
import com.nostra13.universalimageloader.core.ImageLoader;
import com.nostra13.universalimageloader.core.ImageLoaderConfiguration;
import com.nostra13.universalimageloader.core.assist.QueueProcessingType;

import android.content.ClipData;
import android.content.ClipboardManager;
import android.content.Context;
import android.content.Intent;
import android.provider.Settings;
import android.util.Log;

/**
 * Created by su on 2014/8/20.
 */
public class AppHelper {

    private static final String TAG = AppHelper.class.getSimpleName();
    private static final boolean DEBUG = AoShan.DEBUG;

    public static Intent makeLogoutIntent(Context context) {
        Intent intent = new Intent(context, MainActivity.class);
        intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
        return intent;
    }

    public static void startLauncher(Context context) {
        Intent intent = new Intent();
        intent.setAction(Intent.ACTION_MAIN);
        intent.addCategory(Intent.CATEGORY_HOME);
        context.startActivity(intent);
    }

    public static String getUUID() {
        return Settings.Secure.ANDROID_ID;
    }
    
    public static HttpClient getNewHttpClient() {
        try {
            KeyStore trustStore = KeyStore.getInstance(KeyStore.getDefaultType());
            trustStore.load(null, null);

            SSLSocketFactory sf = new SSLSocketFactory(trustStore);
            HttpParams params = new BasicHttpParams();
            HttpProtocolParams.setVersion(params, HttpVersion.HTTP_1_1);
            HttpProtocolParams.setContentCharset(params, HTTP.UTF_8);

            SchemeRegistry registry = new SchemeRegistry();
            registry.register(new Scheme("http", PlainSocketFactory.getSocketFactory(), 80));
            registry.register(new Scheme("https", sf, 443));

            ClientConnectionManager ccm = new ThreadSafeClientConnManager(params, registry);

            return new DefaultHttpClient(ccm, params);
        } catch (Exception e) {
            return new DefaultHttpClient();
        }
    }

    public static File createGestureFileIfNotExist(Context context) {
        String gestureFilename = context.getFilesDir().getAbsolutePath() + "/" + Constants.LOCK_PATTERN_FILE;
        File file = new File(gestureFilename);
        try {
            if (!file.exists()) {
                file.createNewFile();
            }
            return file;
        } catch (IOException e) {
            Log.e(TAG, e.getMessage());
        }
        return null;
    }

    public static File createPasswordFileIfNotExist(Context context) {
        String passwordFilename = context.getFilesDir().getAbsolutePath() + "/" + Constants.LOCK_PASSWORD_FILE;
        File file = new File(passwordFilename);
        try {
            if (!file.exists()) {
                file.createNewFile();
            }
            return file;
        } catch (IOException e) {
            Log.e(TAG, e.getMessage());
        }
        return null;
    }

    public static String formatAmount(long amount) {
        return Constants.FORMAT_AMOUNT.format(amount / (double) 100).replaceAll("(\\d)(?=(?:\\d{3})+(?:\\.\\d+)?$)", "$1,");
    }

    public static String formatAmount(double amount) {
        return Constants.FORMAT_AMOUNT.format(amount).replaceAll("(\\d)(?=(?:\\d{3})+(?:\\.\\d+)?$)", "$1,");
    }

    public static String formatPercent(long percent) {
        return formatAmount(percent);
    }

    public static String fillNullNumberString(String s) {
        return (s == null || s.equals("")) ? "0.00" : s;
    }

    public static void initImageLoader(Context context) {
        ImageLoaderConfiguration.Builder builder = new ImageLoaderConfiguration.Builder(context)
                .threadPriority(Thread.NORM_PRIORITY - 2)
                .denyCacheImageMultipleSizesInMemory()
                .discCacheSize(50 * 1024 * 1024) // 50 Mb
                .tasksProcessingOrder(QueueProcessingType.LIFO);

        if (AoShan.DEBUG) {
            builder.writeDebugLogs();
        }
        ImageLoaderConfiguration config = builder.build();
        // Initialize ImageLoader with configuration.
        ImageLoader.getInstance().init(config);
    }

    public static void call(final Context context) {
//        new AlertDialog.Builder(context, AlertDialog.THEME_HOLO_LIGHT)
//                .setTitle(R.string.call_dialog_title)
//                .setMessage(R.string.customer_service_number_display)
//                .setNegativeButton(R.string.cancel, null)
//                .setPositiveButton(R.string.call, new DialogInterface.OnClickListener() {
//                    @Override
//                    public void onClick(DialogInterface dialog, int which) {
//                        Intent intent = new Intent(Intent.ACTION_CALL, Uri.parse("tel:" + context.getText(R.string.customer_service_number_real)));
//                        context.startActivity(intent);
//                    }
//                })
//                .show();
    }

    public static boolean isPhoneNumber(String s) {
        return Pattern.matches("1[3-9]\\d{9}", s);
    }

    public static String formatTimeToHMS(long time) {
        int h = (int) (time / (60 * 60 * 1000));
        time = time - 60 * 60 * 1000 * h;
        int m = (int) (time / (60 * 1000));
        time = time - 60 * 1000 * m;
        int s = (int) (time / 1000);
        return h + "时" + ((m < 10) ? ("0" + m) : m) + "分" + ((s < 10) ? ("0" + s) : s) + "秒";
    }

    public static boolean hasNewVersion(String oldVersion, String newVersion) {
        newVersion = "".equals(newVersion) ? "1" : newVersion;
        String[] olds = oldVersion.split("\\.");
        String[] news = newVersion.split("\\.");
        int length = olds.length > news.length ? news.length : olds.length;
        for (int i = 0; i < length; i++) {
            if (Integer.parseInt(olds[i]) > Integer.parseInt(news[i])) {
                return false;
            } else if (Integer.parseInt(olds[i]) < Integer.parseInt(news[i])) {
                return true;
            }
        }

        if (olds.length == news.length) {
            return false;
        } else if (olds.length < news.length) {
            for (int i = olds.length; i < news.length; i++) {
                if (Integer.parseInt(news[i]) > 0) {
                    return true;
                }
            }
        } else {
            for (int i = news.length; i < olds.length; i++) {
                if (Integer.parseInt(olds[i]) > 0) {
                    return false;
                }
            }
        }
        return false;
    }

    public static String encodeString(String str) {
        if (str == null) {
            return null;
        }
        try {
            str = URLEncoder.encode(str, "UTF-8");
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }
        return str;
    }

    public static int[] getTimeArray(long time) {
        int[] array = new int[4];
        int h = (int) (time / (60 * 60 * 1000));
        time = time - 60 * 60 * 1000 * h;
        int m = (int) (time / (60 * 1000));
        time = time - 60 * 1000 * m;
        int s = (int) (time / 1000);
        int ms = (int) (time - s * 1000);

        array[0] = h;
        array[1] = m;
        array[2] = s;
        array[3] = ms;
        return array;
    }

    public static void copyToClipboard(Context context, String label, String text) {
        ClipboardManager cmb = (ClipboardManager) context.getSystemService(Context.CLIPBOARD_SERVICE);
        cmb.setPrimaryClip(ClipData.newPlainText(label, text));
    }

    private static Map<String, ThreadLocal<SimpleDateFormat>> sSdfMap = new HashMap<String, ThreadLocal<SimpleDateFormat>>();

    public synchronized static SimpleDateFormat getSdf(final String pattern) {
        ThreadLocal<SimpleDateFormat> tl = sSdfMap.get(pattern);
        if (tl == null) {
            tl = new ThreadLocal<SimpleDateFormat>() {
                @Override
                protected SimpleDateFormat initialValue() {
                    Log.d(TAG, "thread: " + Thread.currentThread() + " init pattern: " + pattern);
                    return new SimpleDateFormat(pattern);
                }
            };
            sSdfMap.put(pattern, tl);
        }
        return tl.get();
    }
}
