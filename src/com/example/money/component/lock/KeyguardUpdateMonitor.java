/*
 * Copyright (C) 2008 The Android Open Source Project
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package com.example.money.component.lock;

import android.content.Context;
import android.os.Handler;
import android.os.Message;
import android.util.Log;
import com.example.money.AoShan;

import java.util.ArrayList;

/**
 * Watches for updates that may be interesting to the keyguard, and provides
 * the up to date information as well as a registration for callbacks that care
 * to be updated.
 * <p/>
 * Note: under time crunch, this has been extended to include some stuff that
 * doesn't really belong here.  see {@link #getFailedAttempts()}, {@link #reportFailedAttempt()}
 * and {@link #clearFailedAttempts()}.  Maybe we should rename this 'KeyguardContext'...
 */
public class KeyguardUpdateMonitor {

    static private final String TAG = KeyguardUpdateMonitor.class.getSimpleName();
    static private final boolean DEBUG = AoShan.DEBUG;

//    private boolean mKeyguardBypassEnabled;

    private int mFailedAttempts = 0;

    private boolean mClockVisible;

    private Handler mHandler;

    private ArrayList<InfoCallback> mInfoCallbacks = Lists.newArrayList();
//    private ContentObserver mContentObserver;

    // messages for the handler
    private static final int MSG_CLOCK_VISIBILITY_CHANGED = 307;

    public KeyguardUpdateMonitor(Context context) {

        mHandler = new Handler() {
            @Override
            public void handleMessage(Message msg) {
                switch (msg.what) {
                    case MSG_CLOCK_VISIBILITY_CHANGED:
                        handleClockVisibilityChanged();
                        break;
                }
            }
        };

//        mKeyguardBypassEnabled = context.getResources().getBoolean(
//                com.android.internal.R.bool.config_bypass_keyguard_if_slider_open);
    }

    private void handleClockVisibilityChanged() {
        if (DEBUG) Log.d(TAG, "handleClockVisibilityChanged()");
        for (int i = 0; i < mInfoCallbacks.size(); i++) {
            mInfoCallbacks.get(i).onClockVisibilityChanged();
        }
    }

    /**
     * Remove the given observer from being registered from any of the kinds
     * of callbacks.
     *
     * @param observer The observer to remove (an instance of {@link InfoCallback})
     */
    public void removeCallback(Object observer) {
        mInfoCallbacks.remove(observer);
    }

    /**
     * Callback for general information relevant to lock screen.
     */
    interface InfoCallback {
        /**
         * Called when visibility of lockscreen clock changes, such as when
         * obscured by a widget.
         */
        void onClockVisibilityChanged();
    }

    /**
     * Register to receive notifications about general keyguard information
     * (see {@link InfoCallback}.
     *
     * @param callback The callback.
     */
    public void registerInfoCallback(InfoCallback callback) {
        if (!mInfoCallbacks.contains(callback)) {
            mInfoCallbacks.add(callback);
            // Notify listener of the current state
            callback.onClockVisibilityChanged();
        } else {
            if (DEBUG) Log.e(TAG, "Object tried to add another INFO callback",
                    new Exception("Whoops"));
        }
    }

    public void reportClockVisible(boolean visible) {
        mClockVisible = visible;
        mHandler.obtainMessage(MSG_CLOCK_VISIBILITY_CHANGED).sendToTarget();
    }
//
//    public boolean isKeyguardBypassEnabled() {
//        return mKeyguardBypassEnabled;
//    }

    public int getFailedAttempts() {
        return mFailedAttempts;
    }

    public void clearFailedAttempts() {
        mFailedAttempts = 0;
    }

    public void reportFailedAttempt() {
        mFailedAttempts++;
    }

    public boolean isClockVisible() {
        return mClockVisible;
    }
}
