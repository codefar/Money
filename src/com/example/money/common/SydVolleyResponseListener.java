package com.example.money.common;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.util.Log;
import android.widget.Toast;
import com.alibaba.fastjson.JSONObject;
import com.android.volley.Response;
import com.example.money.MainActivity;
import com.example.money.R;
import com.example.money.AoShan;
import com.example.money.entity.PageInfo;

/**
 * Created by su on 15-3-30.
 */
public abstract class SydVolleyResponseListener implements Response.Listener<JSONObject> {

    private Context mContext;
    private String mUrl;

    public SydVolleyResponseListener(Context context) {
        this.mContext = context;
    }

    public SydVolleyResponseListener(String url, Context context) {
        this.mContext = context;
        this.mUrl = url;
    }

    /*
        * 302 重新登录
        * 699 系统维护
        * */
    public void onResponse(JSONObject response) {
        Integer errorCode = response.getInteger("errorCode");
        if (errorCode != null) {
            if (AoShan.DEBUG) {
                Log.d("SydVolleyResponseListener", "url: " + mUrl + "\t errorCode: " + errorCode);
            }
            if (errorCode == 302) {//302 重新登录
                if (mContext != null && mContext instanceof Activity) {
                    Activity activity = (Activity) mContext;
                    if (!activity.isFinishing()) {
                        showExpiredDialog(mContext);
                    }
                }
            } else if (errorCode == 699) {//699 系统维护
                if (mContext != null) {
                    Toast.makeText(mContext, "平台正在停机维护,请稍侯", Toast.LENGTH_LONG).show();
                }
            }
            onSuccessfulResponse(response);
        }
    }

    public abstract void onSuccessfulResponse(JSONObject response);

    private static AlertDialog mAlertDialog;

    private static synchronized void showExpiredDialog(final Context context) {
        if (mAlertDialog == null || !mAlertDialog.isShowing()) {
            
        }
    }
}
