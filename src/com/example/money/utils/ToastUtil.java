package com.example.money.utils;

import android.content.Context;
import android.widget.Toast;

public class ToastUtil {
    /**
     * 显示toast
     * call this on thread which has looper, usually on main_thread.
     * @param context
     * @param resId
     */
    public static void showLongToast(final Context context, final int resId){
        if (null != context){
            Toast.makeText(context, resId, Toast.LENGTH_LONG).show();
        }
    }

    /**
     * 显示toast
     * call this on thread which has looper, usually on main_thread.
     * @param context
     * @param msg
     */
    public static void showLongToast(final Context context, final String msg){
        if (null != context){
            Toast.makeText(context, msg, Toast.LENGTH_LONG).show();
        }
    }

    /**
     * 显示toast
     * call this on thread which has looper, usually on main_thread.
     * @param context
     * @param resId
     */
    public static void showShortToast(final Context context, final int resId){
        if (null != context){
            Toast.makeText(context, resId, Toast.LENGTH_SHORT).show();
        }
    }

    /**
     * 显示toast
     * call this on thread which has looper, usually on main_thread.
     * @param context
     * @param msg
     */
    public static void showShortToast(final Context context, final String msg){
        if (null != context){
            Toast.makeText(context, msg, Toast.LENGTH_SHORT).show();
        }
    }
}
