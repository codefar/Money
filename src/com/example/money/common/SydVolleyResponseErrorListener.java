package com.example.money.common;

import android.content.Context;
import android.util.Log;
import android.view.Gravity;
import android.widget.Toast;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.example.money.AoShan;

import java.util.Map;

/**
 * Created by su on 15-3-30.
 */
public abstract class SydVolleyResponseErrorListener implements Response.ErrorListener {

    private String mTag;
    private String mUrl;
    private Context mContext;

    public SydVolleyResponseErrorListener(Context context, String tag, String url) {
        mTag = tag;
        mUrl = url;
        mContext = context;
    }

    public void onErrorResponse(VolleyError volleyError) {
        logNetworkResponse(volleyError);
        onResponseFail(volleyError);
    }

    public abstract void onResponseFail(VolleyError volleyError);


    private void logNetworkResponse(VolleyError volleyError) {
        String data = null;
        int statusCode = -1;
        Map<String, String> headers = null;
        if (AoShan.DEBUG) {
            Log.w(mTag, "url: " + mUrl, volleyError);
            if (volleyError.networkResponse != null) {
                headers = volleyError.networkResponse.headers;
                statusCode = volleyError.networkResponse.statusCode;
                data = new String(volleyError.networkResponse.data);
                Log.e(mTag, "statusCode: " + statusCode);
                Log.e(mTag, "headers: " + headers);
                Log.e(mTag, "data: " + data);
            }

            if (mContext != null) {
                StringBuilder sb = new StringBuilder();
                sb.append("url: " + mUrl + "\n");

                if (statusCode > 0) {
                    sb.append("statusCode: " + statusCode + "\n");
                }

                if (headers != null) {
                    sb.append("headers: " + headers + "\n");
                }

                if (data != null) {
                    sb.append("data: " + data + "\n");
                }

                Toast t = Toast.makeText(mContext, sb, Toast.LENGTH_LONG);
                t.setGravity(Gravity.CENTER, 0, 0);
                t.show();
            }
        }
    }
}
