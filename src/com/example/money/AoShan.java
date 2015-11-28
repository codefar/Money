package com.example.money;

import java.io.File;

import com.android.volley.RequestQueue;
import com.android.volley.toolbox.Volley;
import com.tencent.mm.sdk.openapi.IWXAPI;
import com.tencent.mm.sdk.openapi.WXAPIFactory;
import com.tencent.tauth.Tencent;

import android.app.Application;
import android.content.Context;
import android.content.SharedPreferences;
import android.content.pm.ApplicationInfo;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.graphics.Point;
import android.os.Environment;
import android.preference.PreferenceManager;
import android.util.Log;
import android.view.Display;
import android.view.WindowManager;

/**
 * Created by su on 2014/9/15.
 */
public class AoShan extends Application {

    public static boolean DEBUG = true;
    public static final String TAG = AoShan.class.getSimpleName();
    public static final String MOBILE_MD5_SIGN = "j7dAuXMhpE76LRrETe8bTQ";

    public static RequestQueue sRequestQueue;
    public static final String HOST = "http://.../"; //TODO 补全host
    public static String sVersionName;
    public static int sVersionCode;
    public static String sPackageName;
    public static String sAppName;
    public static boolean sDebuggable = true;
    public static int sScreenWidth;
    public static int sScreenHeight;
    public static String sApplicationLabel;
    private static boolean sInit;
    public static int sActionBarHeight;

    public static final String sRootDirPath;
    public static final String sTempDirPath;
    public static final String sPicDirPath;
    public static String sDeviceId;
    public static String sUMengChannel;

    public static final String SP_COLUMN_NEED_TO_UPDATE = "need_to_update";
    public static final String SP_COLUMN_FORCE_TO_UPDATE = "force_to_update";
    public static final String SP_COLUMN_NEW_VERSION_APK_NAME = "new_version_apk_name";
    public static final String SP_COLUMN_NEW_VERSION = "new_version";
    public static final String SP_COLUMN_NEW_VERSION_URL = "new_version_url";
    public static final String SP_COLUMN_NEW_VERSION_DESCRIPTION = "new_version_description";
    public static final String SP_COLUMN_NEW_VERSION_TITLE = "new_version_title";

    public static boolean sIsInBackground = true;

    public static final String WX_APP_ID = "wxece2d80f3747bc52";
    public static final String QQ_APP_ID = "1103532582";
    private IWXAPI mWxApi;
    public static Tencent mTencent;

    static {
        File externalStorage = Environment.getExternalStorageDirectory();
        String prefix = externalStorage.getAbsolutePath() + File.separator;
        sRootDirPath = prefix + Constants.APP_DIR_ROOT;
        File rootDirFile = new File(sRootDirPath);
        if (!rootDirFile.exists()) {
            rootDirFile.mkdir();
        } else {
            if (!rootDirFile.isDirectory()) {
                if (rootDirFile.delete()) {
                    rootDirFile.mkdir();
                }
            }
        }

        sTempDirPath = prefix + Constants.APP_DIR_TEMP;
        File tempDirFile = new File(sTempDirPath);
        if (!tempDirFile.exists()) {
            tempDirFile.mkdir();
        } else {
            if (!tempDirFile.isDirectory()) {
                if (tempDirFile.delete()) {
                    tempDirFile.mkdir();
                }
            }
        }

        sPicDirPath = prefix + Constants.APP_DIR_PIC;
        File picDirFile = new File(sPicDirPath);
        if (!picDirFile.exists()) {
            picDirFile.mkdir();
        } else {
            if (!picDirFile.isDirectory()) {
                if (picDirFile.delete()) {
                    picDirFile.mkdir();
                }
            }
        }
    }

    private void regToWx() {
        mWxApi = WXAPIFactory.createWXAPI(this, WX_APP_ID, true);
        mWxApi.registerApp(WX_APP_ID);
    }

    @Override
    public void onCreate() {
        super.onCreate();

        final SharedPreferences defaultSP = PreferenceManager.getDefaultSharedPreferences(this);
//        AppHelper.getNewHttpClient();

        sRequestQueue = Volley.newRequestQueue(this);

        if (sVersionName == null) {
            try {
                PackageManager pm = getPackageManager();
                PackageInfo pi = pm.getPackageInfo(getPackageName(), 0);
                sVersionName = pi.versionName;
                sVersionCode = pi.versionCode;
                sPackageName = pi.packageName;
                sAppName = pi.applicationInfo.loadLabel(getPackageManager()).toString();
                ApplicationInfo applicationInfo = pm.getApplicationInfo(getPackageName(), PackageManager.COMPONENT_ENABLED_STATE_DEFAULT);
                sApplicationLabel = pm.getApplicationLabel(applicationInfo).toString();
                ApplicationInfo appInfo = pm.getApplicationInfo(getPackageName(), PackageManager.GET_META_DATA);
                sUMengChannel = appInfo.metaData.getString("UMENG_CHANNEL");
                if (sUMengChannel == null || "".equals(sUMengChannel)) {
                    sUMengChannel = String.valueOf(appInfo.metaData.getInt("UMENG_CHANNEL"));
                }
                DEBUG = appInfo.metaData.getBoolean("LOG");
                sDebuggable = (applicationInfo.flags & ApplicationInfo.FLAG_DEBUGGABLE) == ApplicationInfo.FLAG_DEBUGGABLE;
            } catch (PackageManager.NameNotFoundException e) {
                if (DEBUG) {
                    Log.d(TAG, "onCreate: " + e.getMessage());
                }
            }
        }

        sInit = defaultSP.getBoolean(Constants.SP_COLUMN_INIT, true);
        if (sInit) {
            defaultSP.edit().putBoolean(Constants.SP_COLUMN_INIT, false).apply();
        }

        WindowManager wm = (WindowManager) getSystemService(Context.WINDOW_SERVICE);
        Display display = wm.getDefaultDisplay();
        Point size = new Point();
        display.getSize(size);
        if (size.x < size.y) {
            sScreenWidth = size.x;
            sScreenHeight = size.y;
        } else {
            sScreenWidth = size.y;
            sScreenHeight = size.x;
        }

        regToWx();
        mTencent = Tencent.createInstance(QQ_APP_ID, this);

    }
}
