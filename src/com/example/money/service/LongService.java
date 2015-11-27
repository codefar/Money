package com.example.money.service;

import android.app.ActivityManager;
import android.app.Service;
import android.content.ComponentName;
import android.content.Intent;
import android.os.IBinder;
import android.util.Log;
import com.example.money.AoShan;

import java.util.List;
import java.util.Timer;
import java.util.TimerTask;

/**
 * Created by su on 2014/10/23.
 */
public class LongService extends Service {

    private static final String TAG = LongService.class.getSimpleName();
    private ActivityManager mActivityManager;
    private Timer mTimer;

    @Override
    public void onCreate() {
        super.onCreate();
        mActivityManager = (ActivityManager) getSystemService(ACTIVITY_SERVICE);
        if (AoShan.DEBUG) {
            Log.d(TAG, "LongService is creating!");
        }
        startTimer();
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        if (AoShan.DEBUG) {
            Log.d(TAG, "LongService is destroying!");
        }
        if (mTimer != null) {
            mTimer.cancel();
        }
    }

    private void startTimer() {
        if (mTimer == null) {
            mTimer = new Timer();
            LockTask lockTask = new LockTask(getPackageName());
            mTimer.schedule(lockTask, 0L, 1000L);
        }
    }

    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }

    public class LockTask extends TimerTask {
        final String mPackageName;

        public LockTask(String packageName) {
            mPackageName = packageName;
        }

        @Override
        public void run() {
            List<ActivityManager.RunningTaskInfo> runningTaskList = mActivityManager.getRunningTasks(1);
            if (runningTaskList == null || runningTaskList.size() == 0) {
                AoShan.sIsInBackground = true;
                if (AoShan.DEBUG) {
                    Log.d(TAG, "sIsInBackground: " + AoShan.sIsInBackground + " \trunningTaskList: " + runningTaskList);
                }
                return;
            }
            ComponentName topActivity = runningTaskList.get(0).topActivity;
            String packageName = topActivity.getPackageName();
            String className = topActivity.getClassName();
            //TODO 修改标志位
            if (!mPackageName.equals(packageName)) {
                AoShan.sIsInBackground = true;
            } else {
                if (className.equals("com.example.money.FromWebActivity")) {
                    AoShan.sIsInBackground = true;
                } else if (className.equals("com.example.money.DownloadDialogActivity")) {
                    AoShan.sIsInBackground = true;
                }
            }
            if (AoShan.DEBUG) {
                Log.d(TAG, "sIsInBackground: " + AoShan.sIsInBackground + " \tpackageName: " + packageName + " \tclassName: " + className);
            }
        }
    }
}
