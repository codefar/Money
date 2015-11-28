package com.example.money;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import com.example.money.entity.WebViewCommunication;
import com.example.money.entity.User;
import com.example.money.utils.UiHelper;

import android.annotation.TargetApi;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.res.Resources;
import android.graphics.Bitmap;
import android.hardware.Sensor;
import android.hardware.SensorManager;
import android.os.Build;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.text.TextUtils;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.webkit.CookieManager;
import android.webkit.CookieSyncManager;
import android.webkit.WebChromeClient;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.Button;
import android.widget.EditText;
import android.widget.FrameLayout;
import android.widget.LinearLayout;
import android.widget.ProgressBar;

/**
 * Created by su on 2014/9/28.
 */
public class WebViewActivity extends CommonBaseActivity implements View.OnClickListener {

    private static final String TAG = WebViewActivity.class.getSimpleName();
    public static final String NEED_TO_REFRESH = "need_to_refresh";
    private Resources mResources;
    private ProgressBar progressbar;
    private String mTitle;
    private String mUrl;
    private WebView mWebView;
    private HashMap<String, String> mHeader;
    private List<String> mHeaderList = new ArrayList<String>();
    private boolean mHideActionMenu;
    private Sensor mAccelerometerSensor;
    private SensorManager mSensorManager;
    private long mLastTime = 0;
    private long mInterval = 40L;
    private boolean mNeedToRefresh = false;
    private LinearLayout mButtonLayout;
    private EditText mInputUrlEditText;
    private Button mGoButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_webview);

        mWebView = (WebView) findViewById(R.id.web_view);
        SharedPreferences sp = PreferenceManager.getDefaultSharedPreferences(this);
        boolean debug = sp.getBoolean("webview_debug", false);
        if (AoShan.DEBUG && debug) {
            initDebug();
        }
        mResources = getResources();
        mSensorManager = (SensorManager) getSystemService(Context.SENSOR_SERVICE);

        Intent intent = getIntent();
        mUrl = intent.getStringExtra("url");
        mTitle = intent.getStringExtra("title");
        mNeedToRefresh = intent.getBooleanExtra("need_to_refresh", false);
        mHideActionMenu = intent.getBooleanExtra("hide_action_menu", false);

        setupTitleBar(mTitle);
        progressbar = new ProgressBar(this, null, android.R.attr.progressBarStyleHorizontal);
        FrameLayout.LayoutParams lp = new FrameLayout.LayoutParams(FrameLayout.LayoutParams.MATCH_PARENT, mResources.getDimensionPixelOffset(R.dimen.dimen_3_dip));
        lp.topMargin = AoShan.sActionBarHeight;
        addContentView(progressbar, lp);

        WebSettings webSettings = mWebView.getSettings();
        webSettings.setUserAgentString(webSettings.getUserAgentString() + " souyidai " + AoShan.sVersionCode);
        webSettings.setCacheMode(WebSettings.LOAD_NO_CACHE);
        webSettings.setSupportZoom(true);
        webSettings.setJavaScriptEnabled(true);
        webSettings.setLoadWithOverviewMode(true);
        CookieManager cookieManager = CookieManager.getInstance();
        cookieManager.removeSessionCookie();
        mWebView.addJavascriptInterface(new WebViewCommunication(this), "JsCommunication");
        User user = User.getInstance(this);
        if (user.getWayToLogin() != null) {
            mWebView.addJavascriptInterface(user.getId(), "uid");
            mWebView.addJavascriptInterface(user.getToken(), "sydaccesstoken");
        }
        webSettings.setDomStorageEnabled(true);

        mWebView.setScrollBarStyle(View.SCROLLBARS_INSIDE_OVERLAY);
        mWebView.setWebChromeClient(new WebChromeClient() {
            public void onProgressChanged(WebView view, int progress) { //设置 加载进程
                if (progress == 100) {
                    progressbar.setVisibility(View.GONE);
                    UiHelper.showLoadingLayout(WebViewActivity.this, false);
                } else {
                    if (progressbar.getVisibility() == View.GONE) {
                        progressbar.setVisibility(View.VISIBLE);
                    }
                    progressbar.setProgress(progress);
                }
            }

            @Override
            public void onReceivedTitle(WebView view, String title) {
                super.onReceivedTitle(view, title);
                if (TextUtils.isEmpty(mTitle) && !TextUtils.isEmpty(title)) {
                    mTitle = title;
                    setupTitleBar(mTitle);
                    mTitle = "";
                }
            }
        });
        webSettings.setBuiltInZoomControls(true);
        mWebView.setWebViewClient(new WebViewClient() {
            public void onReceivedError(WebView view, int errorCode, String description, String failingUrl) {
                view.stopLoading();
                view.clearView();
                UiHelper.showLoadErrorLayout(WebViewActivity.this, true, WebViewActivity.this);
                UiHelper.showLoadingLayout(WebViewActivity.this, false);
            }

            public boolean shouldOverrideUrlLoading(WebView view, String url) {
                UiHelper.showLoadingLayout(WebViewActivity.this, true);
                mUrl = url;
                view.loadUrl(url, mHeader);
                return true;
            }

            public void onPageStarted(WebView view, String url, Bitmap favicon) {
                if (mHeaderList.size() == 0) {
                    mHeaderList.add(url);
                } else {
                    String lastUrl = mHeaderList.get(mHeaderList.size() - 1);
                    if (!lastUrl.equals(url)) {
                        mHeaderList.add(url);
                    }
                }
            }
        });

        mHeader = new HashMap<String, String>();
        mHeader.put("sydaccesstoken", user.getToken());
        mHeader.put("uid", String.valueOf(user.getId()));
        UiHelper.showLoadingLayout(WebViewActivity.this, true);
        addCookie(mUrl);
        mWebView.loadUrl(mUrl, mHeader);
    }

    public void addCookie(String url) {
        User user = User.getInstance(WebViewActivity.this);
        if (user.getWayToLogin() != null) {
            CookieSyncManager.createInstance(this);
            CookieManager cookieManager = CookieManager.getInstance();
            cookieManager.setAcceptCookie(true);
            String domain = ".souyidai.com";
            String cookie = "syd_auth_verify=" + user.getId() + "|" + user.getToken() + ";domain=" + domain + ";" + "path=/";
            cookieManager.setCookie(url, cookie);
            CookieSyncManager.getInstance().sync();
        }
    }

    @Override
    protected void onResume() {
        super.onResume();
        addCookie(mUrl);
    }

    @Override
    protected void onRestart() {
        super.onRestart();
        if (mNeedToRefresh) {
            refresh();
        }
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.load_error_layout:
                refresh();
                UiHelper.showLoadErrorLayout(WebViewActivity.this, false, this);
                break;
            case R.id.go:
                mWebView.loadUrl(mInputUrlEditText.getText().toString());
                break;
            default:
                break;
        }
    }

    @Override
    public boolean onPrepareOptionsMenu(Menu menu) {
        return super.onPrepareOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem menuItem) {
        switch (menuItem.getItemId()) {
            default:
                break;
        }
        return super.onOptionsItemSelected(menuItem);
    }

    private void refresh() {
        UiHelper.showLoadingLayout(this, true);
        UiHelper.showLoadErrorLayout(WebViewActivity.this, false, this);
        mWebView.reload();
    }

    public static final int REQUEST_REFRESH = 1;

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == REQUEST_REFRESH) {
            refresh();
        }
    }

    @Override
    public void onBackPressed() {
        if (mWebView.isFocused() && mHeaderList.size() > 1) {
            UiHelper.showLoadingLayout(this, true);
            UiHelper.showLoadErrorLayout(WebViewActivity.this, false, this);
//            mWebView.goBack();
            mHeaderList.remove(mHeaderList.size() - 1);
            mUrl = mHeaderList.get(mHeaderList.size() - 1);
            if (mHeaderList.size() == 1) {
                mWebView.loadUrl(mUrl, mHeader);
            } else {
                mWebView.loadUrl(mUrl);
            }
        } else {
            super.onBackPressed();
        }
    }

    @TargetApi(19)
    private void initDebug() {
        findViewById(R.id.debug_layout).setVisibility(View.VISIBLE);
        mButtonLayout = (LinearLayout) findViewById(R.id.button_layout);
        mInputUrlEditText = (EditText) findViewById(R.id.input_url);
        mGoButton = (Button) findViewById(R.id.go);
        mGoButton.setOnClickListener(this);
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
            mWebView.setWebContentsDebuggingEnabled(true);
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
    }

    @Override
    protected String getTag() {
        return TAG;
    }
}
