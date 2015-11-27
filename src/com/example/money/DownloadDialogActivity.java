package com.example.money;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import com.example.money.entity.User;
import com.example.money.service.DownloadService;

/**
 * Created by su on 2014/7/31.
 * Only for download apk!
 */
public class DownloadDialogActivity extends Activity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Intent intent = getIntent();
        if (intent != null) {
            String title = intent.getStringExtra("title");
            if (title == null || title.trim().equals("")) {
                title = getString(R.string.check_version);
            }
            final String description = intent.getStringExtra("description");
            final String url = intent.getStringExtra("url");
            boolean force = intent.getBooleanExtra("force", false);
            final String version = intent.getStringExtra("version");
            final boolean forward = intent.getBooleanExtra("forward", true);
            AlertDialog.Builder builder = new AlertDialog.Builder(DownloadDialogActivity.this, AlertDialog.THEME_HOLO_LIGHT);
            if (force) {
                builder.setTitle(title)
                        .setMessage(description)
                        .setPositiveButton(R.string.download_new_version, new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                Intent intent = new Intent(DownloadDialogActivity.this, DownloadService.class);
                                intent.setAction(DownloadService.ACTION_DOWNLOAD_WITH_DOWNLOAD_MANAGER);
                                intent.putExtra("url", url);
                                intent.putExtra("version", version);
                                intent.putExtra("file_name", "souyidai" + version + ".apk");
                                startService(intent);
                                if (forward) {
                                    Intent homeIntent = new Intent(Intent.ACTION_MAIN);
                                    homeIntent.addCategory(Intent.CATEGORY_HOME);
                                    startActivity(homeIntent);
                                } else {
                                    startLockScreenActivity();
                                }
                                finish();
                            }
                        });
            } else {
                builder.setTitle(title)
                        .setMessage(description)
                        .setPositiveButton(R.string.download_new_version, new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                Intent intent = new Intent(DownloadDialogActivity.this, DownloadService.class);
                                intent.setAction(DownloadService.ACTION_DOWNLOAD_WITH_DOWNLOAD_MANAGER);
                                intent.putExtra("url", url);
                                intent.putExtra("version", version);
                                intent.putExtra("file_name", "souyidai" + version + ".apk");
                                startService(intent);
                                finish();
                                startLockScreenActivity();
                            }
                        });
                builder.setNegativeButton(R.string.download_later, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        finish();
                        startLockScreenActivity();
                    }
                });
            }

            builder.setCancelable(false);
            builder.show();
        }
    }

    private void startLockScreenActivity() {
        User user = User.getInstance(this);
        if (AoShan.sIsInBackground && user.getWayToLogin() != null) {
            SharedPreferences sp = PreferenceManager.getDefaultSharedPreferences(this);
            boolean enable = sp.getBoolean(Constants.SP_COLUMN_PATTERN_PASSWORD_ENABLE, false);
            if (enable) {
//                startActivity(new Intent(DownloadDialogActivity.this, LockScreenActivity.class));
            }
        }
    }

    @Override
    protected void onNewIntent(Intent intent) {
        super.onNewIntent(intent);
    }
}
