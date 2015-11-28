package com.example.money;

import java.util.Map;

import com.example.money.common.AppHelper;
import com.example.money.common.PageReferenceManager;
import com.example.money.entity.PageInfo;
import com.example.money.entity.User;

import android.app.Activity;
import android.app.ActivityManager;
import android.app.AlertDialog;
import android.content.BroadcastReceiver;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;

public abstract class BaseAppCompatActivity extends Activity {

    private SharedPreferences mSP;
    protected Map<String, String> mUrlParamMap;
    protected String mPageId;

    private void setupTitleBar(String title, int options) {

    }

    protected void setupTitleBar(String title) {

    }

    protected void setupTitleBar(int titleRes) {

    }

    protected void setupCustomTitleBar(String title) {

    }

    protected void setupCustomTitleBar(int titleRes) {

    }

    protected abstract String getTag();

    private BroadcastReceiver mBroadcastReceiver = new BroadcastReceiver() {
        @Override
        public void onReceive(Context context, Intent intent) {
            if (Intent.ACTION_SCREEN_ON.equals(intent.getAction())) {
                if (User.isLogin(context)
                        && mSP.getBoolean(Constants.SP_COLUMN_PATTERN_PASSWORD_ENABLE, false)) {
                    ActivityManager activityManager = (ActivityManager) getSystemService(Context.ACTIVITY_SERVICE);
                    ComponentName topActivity = activityManager.getRunningTasks(1).get(0).topActivity;
                    String packageName = topActivity.getPackageName();
                    String className = topActivity.getClassName();
                    if (getPackageName().equals(packageName)) {
//                        startActivity(new Intent(context, LockScreenActivity.class));
                    }
                }
            }
        }
    };

    protected int mTabId;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mPageId = Integer.toHexString(hashCode());
        PageReferenceManager.addPage(mPageId, new PageInfo(getClass().getSimpleName(), mPageId));
        PageReferenceManager.logCache();

        mSP = PreferenceManager.getDefaultSharedPreferences(this);
        IntentFilter intentFilter = new IntentFilter(Intent.ACTION_SCREEN_ON);
        registerReceiver(mBroadcastReceiver, intentFilter);

        Intent intent = getIntent();
        mUrlParamMap = (Map<String, String>) intent.getSerializableExtra("url_param");

        boolean showDialog = intent.getBooleanExtra("show_dialog", false);
        if (showDialog) {
            String title = intent.getStringExtra("title");
            String msg = intent.getStringExtra("msg");
            new AlertDialog.Builder(this).setTitle(title).setMessage(msg).setPositiveButton(R.string.known, null).show();
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        unregisterReceiver(mBroadcastReceiver);
        PageReferenceManager.removePage(mPageId);
        PageReferenceManager.logCache();
    }

    @Override
    protected void onRestart() {
        super.onRestart();

        if (AoShan.sIsInBackground
                && User.isLogin(this)
                && mSP.getBoolean(Constants.SP_COLUMN_PATTERN_PASSWORD_ENABLE, false)) {
//            startActivity(new Intent(this, LockScreenActivity.class));
        }
        if (AoShan.DEBUG) {
            Log.d(getTag(), "onRestart sIsInBackground: " + AoShan.sIsInBackground);
        }
    }

    @Override
    protected void onStart() {
        super.onStart();
        if (AoShan.sIsInBackground
                && User.isLogin(this)
                && mSP.getBoolean(Constants.SP_COLUMN_PATTERN_PASSWORD_ENABLE, false)) {
//            startActivity(new Intent(this, LockScreenActivity.class));
        }
        if (AoShan.DEBUG) {
            Log.d(getTag(), "onStart sIsInBackground: " + AoShan.sIsInBackground);
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
//        MenuInflater menuInflater = getMenuInflater();
//        menuInflater.inflate(R.menu.main_menu, menu);
        return true;
    }

    @Override
    public boolean onPrepareOptionsMenu(Menu menu) {
//        MenuItem login = menu.findItem(R.id.action_login);
//        MenuItem register = menu.findItem(R.id.action_register);
//        if (login != null) {
//            login.setVisible(false);
//        }
//        if (register != null) {
//            register.setVisible(false);
//        }
        return super.onPrepareOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem menuItem) {
        User user = User.getInstance(this);
        return (super.onOptionsItemSelected(menuItem));
    }

    public Map<String, String> getUrlParamMap() {
        return mUrlParamMap;
    }

    protected void logout() {
        User.clearUser(this);
        startActivity(AppHelper.makeLogoutIntent(this));
    }
}