package com.example.money.utils;

import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.net.wifi.WifiManager;
import android.telephony.TelephonyManager;
import android.text.TextUtils;
import android.util.Log;
import com.android.volley.VolleyError;
import com.example.money.AoShan;

import java.lang.reflect.Field;

/**
 * Created by wangyonghua on 15-11-12.
 */
public class Logger {
    private static final String TAG = Logger.class.getSimpleName();

    public static void i(final String msg) {
        i(TAG,msg);
    }

    public static void v(final String msg) {
        v(TAG, msg);
    }

    public static void d(final String msg) {
        d(TAG, msg);
    }

    public static void e(final String msg) {
        e(TAG, msg);
    }

    public static void e(final Throwable throwable) {
        e("", throwable);
    }

    public static void e(final String msg, final Throwable throwable) {
        if (AoShan.DEBUG) { Log.e(TAG, msg , throwable); }
    }

    public static void i(final String tag, final String msg) {
        if (AoShan.DEBUG) { Log.i(tag, msg); }
    }

    public static void v(final String tag, final String msg) {
        if (AoShan.DEBUG) { Log.v(tag, msg); }
    }

    public static void d(final String tag, final String msg) {
        if (AoShan.DEBUG) { Log.d(tag, msg); }
    }

    public static void e(final String tag, final String msg) {
        if (AoShan.DEBUG) { Log.e(tag, msg); }
    }

    /**
     * provide by umeng
     * http://www.umeng.com/test_devices
     */
    public static void logUMengDeviceInfo(Context context) {
        try {
            org.json.JSONObject json = new org.json.JSONObject();
            TelephonyManager tm = (TelephonyManager) context.getSystemService(Context.TELEPHONY_SERVICE);
            String deviceId = tm.getDeviceId();

            WifiManager wifi = (WifiManager) context.getSystemService(Context.WIFI_SERVICE);
            String mac = wifi.getConnectionInfo().getMacAddress();
            json.put("mac", mac);

            if (TextUtils.isEmpty(deviceId)) {
                deviceId = mac;
            }

            if (TextUtils.isEmpty(deviceId)) {
                deviceId = android.provider.Settings.Secure.getString(context.getContentResolver(), android.provider.Settings.Secure.ANDROID_ID);
            }

            json.put("device_id", deviceId);
            Log.d(AoShan.TAG, "Umeng_id: " + json);
        } catch (Exception e) {
            //ignore
        }
    }

    private static void logSeparators(boolean enable, boolean isStart) {
        if (enable) {
            if (isStart) {
                Log.i(TAG, ">>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>");
            } else {
                Log.i(TAG, "<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<");
            }
        }
    }

    public static Cursor logCursorAllTitles(Cursor c) {
        if (c != null) {
            String[] names = c.getColumnNames();
            int length = names.length;
            for (int i = 0; i < length; i++) {
                Log.d(TAG, names[i]);
            }
        }
        return c;
    }

    public static void log(String s) {
        Log.v(TAG, s);
    }

    public static void logComponentEnabledSetting(PackageManager packageManager, Context context, Class<?>[] classes) {
        if (classes != null) {
            for (Class<?> clazz : classes) {
                int state = packageManager.getComponentEnabledSetting(new ComponentName(context, clazz.getName()));
                if (state == PackageManager.COMPONENT_ENABLED_STATE_DISABLED) {
                    Log.d(TAG, clazz.getName() + " state = COMPONENT_ENABLED_STATE_DISABLED");
                } else if (state == PackageManager.COMPONENT_ENABLED_STATE_DISABLED_USER) {
                    Log.d(TAG, clazz.getName() + " state = COMPONENT_ENABLED_STATE_DISABLED_USER");
                } else if (state == PackageManager.COMPONENT_ENABLED_STATE_ENABLED) {
                    Log.d(TAG, clazz.getName() + " state = COMPONENT_ENABLED_STATE_ENABLED");
                } else {
                    Log.d(TAG, clazz.getName() + " state = COMPONENT_ENABLED_STATE_DEFAULT");
                }

            }
        }
    }

    public static Cursor logCursorWithTitle(Cursor c) {
        logSeparators(true, true);
        String separator = " | ";
        if (c != null) {
            String[] names = c.getColumnNames();
            int length = names.length;
            if (c.moveToFirst()) {
                do {
                    StringBuilder sb = new StringBuilder();
                    for (int i = 0; i < length; i++) {
                        sb.append(names[i]);
                        sb.append(": ");
                        sb.append(c.getString(i));
                        sb.append(separator);
                    }
                    sb.deleteCharAt(sb.length() - 1);
                    Log.d(TAG, sb.toString());
                } while (c.moveToNext());
            }
            c.moveToFirst();
        }
        logSeparators(true, false);
        return c;
    }

    public static Cursor logCursorWithHeader(Cursor c) {
        logSeparators(true, true);
        String separator = " | ";
        if (c != null) {
            String[] names = c.getColumnNames();
            int length = names.length;
            StringBuilder sb = new StringBuilder();
            for (int i = 0; i < length; i++) {
                sb.append(names[i]);
                sb.append(separator);
            }
            sb.deleteCharAt(sb.length() - 1);
            Log.e(TAG, sb.toString());
            if (c.moveToFirst()) {
                do {
                    sb = new StringBuilder();
                    for (int i = 0; i < length; i++) {
                        sb.append(c.getString(i));
                        sb.append(separator);
                    }
                    sb.deleteCharAt(sb.length() - 1);
                    Log.d(TAG, sb.toString());
                } while (c.moveToNext());
            }
            c.moveToFirst();
        }
        logSeparators(true, false);
        return c;
    }

    private static void modifyField(Class<?> clazz, Object o, String fieldName, Object v) {
        Field field = null;
        try {
            field = clazz.getField(fieldName);
            field.setAccessible(true);
            field.set(o, v);
            field.setAccessible(false);
        } catch (IllegalAccessException | NoSuchFieldException e) {
            e.printStackTrace();
        }
    }

    public static void logTestIntent(Intent intent, Object o) {
        String[] fieldNames = intent.getStringArrayExtra("fieldNames");
        String[] fieldTypes = intent.getStringArrayExtra("fieldTypes");
        int length = fieldNames.length;
        if (length != fieldTypes.length) {
            return;
        }

        Class<?> clazz = null;
        try {
            clazz = Class.forName(o.getClass().getName());
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }

        for (int i = 0; i < length; i++) {
            String type = fieldTypes[i];
            String name = fieldNames[i];
            Object value = null;
            if (type.equalsIgnoreCase("boolean")) {
                value = intent.getBooleanExtra(fieldNames[i], false);
            } else if (type.equalsIgnoreCase("byte")) {
                value = intent.getByteExtra(fieldNames[i], (byte) 0);
            } else if (type.equalsIgnoreCase("char")) {
                value = intent.getCharExtra(fieldNames[i], (char) 0);
            } else if (type.equalsIgnoreCase("int")) {
                value = intent.getIntExtra(fieldNames[i], 0);
            }
            modifyField(clazz, o, name, value);
        }
    }

    public static void logNetworkResponse(VolleyError volleyError, String tag) {
        if (AoShan.DEBUG) {
            Log.w(tag, "TAG", volleyError);
            if (volleyError.networkResponse != null) {
                Log.e(tag, "headers: " + volleyError.networkResponse.headers);
                Log.e(tag, "statusCode: " + volleyError.networkResponse.statusCode);
                Log.e(tag, "data: " + new String(volleyError.networkResponse.data));
            }
        }
    }
}
