package com.example.money.entity;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.example.money.AoShan;
import com.example.money.WebViewActivity;
import com.example.money.common.AppHelper;

import android.app.Activity;
import android.content.Intent;
import android.util.Log;
import android.webkit.JavascriptInterface;

/**
 * Created by su on 2015/1/28.
 */
public class WebViewCommunication {

    protected Activity mActivity;

    public WebViewCommunication(Activity activity) {
        mActivity = activity;
    }

    @JavascriptInterface
    public void finish() {
        mActivity.finish();
    }

    //TODO finish later
    @JavascriptInterface
    public void setActivityTitle(String title) {
    }

    @JavascriptInterface
    public void callCustomerService() {
        AppHelper.call(mActivity);
    }

    /**
     * @param { key："",
     *          data:{}
     *          }
     * @name 生成回调WebView JS的脚本
     */
    @JavascriptInterface
    public static String makeJsCallbackJs(String jsParam) {
        return "javascript:SYDBridge.nativeCallback('" + jsParam + "');";
    }

    @JavascriptInterface
    public static String makeJsCallbackParam(String key, Object data) {
        JSONObject jsonObject = new JSONObject();
        jsonObject.put("key", key);
        jsonObject.put("data", data);
        if (AoShan.DEBUG) {
            Log.i("JS-PARA", "makeJsCallbackParam " + jsonObject.toJSONString());
        }
        return jsonObject.toJSONString();
    }

    //TODO finish later
    @JavascriptInterface
    public void onMoneyChange() {
    }

    @JavascriptInterface
    public void webview(String data) {
        if (data != null) {
            JSONObject param = JSON.parseObject(data);
            String title = param.getString("title");
            String url = param.getString("webViewUrl");
            boolean needToRefresh = param.getBooleanValue("refresh");
            Intent intent = new Intent(mActivity, WebViewActivity.class);
            intent.putExtra("title", title);
            intent.putExtra("url", url);
            intent.putExtra("need_to_refresh", needToRefresh);
            mActivity.startActivity(intent);
        }
    }
}
