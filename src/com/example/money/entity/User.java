package com.example.money.entity;

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.util.Date;

import com.example.money.AoShan;
import com.example.money.Constants;
import com.example.money.common.AppHelper;

import android.content.Context;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;
import android.text.TextUtils;
import android.util.Log;

/**
 * Created by su on 2015/11/29.
 */
public class User {

    public static final boolean DEBUG = AoShan.DEBUG;
    private static final String TAG = User.class.getSimpleName();

    private String mId;
    private String mSessionId;
    private String mId5;
    private String mName;
    private String mRealName;
    private String mNickName;
    private String mToken = "";
    private long mLoginTime;
    private String phone;
    private long mExpireTime;

    private static User sUser = null;

    private User() {
    }

    public static boolean isLogin(Context c) {
    	return !TextUtils.isEmpty(getInstance(c).getPhone());
    }

    public static synchronized User getInstance(Context context) {
        if (sUser == null) {
            sUser = new User();
            SharedPreferences sp = PreferenceManager.getDefaultSharedPreferences(context);
            sUser.phone = sp.getString(Constants.SP_COLUMN_USER_PHONE_NUMBER, "");
            //未登录
            if (TextUtils.isEmpty(sUser.phone)) {
                return sUser;
            } else {
                sUser.mId = sp.getString(Constants.SP_COLUMN_USER_ID, "");
                sUser.mName = sp.getString(Constants.SP_COLUMN_USER_NAME, "");
                sUser.mToken = sp.getString(Constants.SP_COLUMN_USER_TOKEN, "");
                sUser.mLoginTime = sp.getLong(Constants.SP_COLUMN_USER_LOGIN_TIME, -1);
                sUser.mExpireTime = sp.getLong(Constants.SP_COLUMN_USER_EXPIRE_TIME, -1);

                sUser.mId5 = sp.getString(Constants.SP_COLUMN_USER_PHONE_ID5, "");
                sUser.setNickName(sp.getString(Constants.SP_COLUMN_USER_PHONE_NICKNAME, ""));
            }
        } else {
            if (TextUtils.isEmpty(sUser.phone) && !isValid()) {
                clearUser(context);
            }
        }

        return sUser;
    }

    @Override
	public String toString() {
		return "User [mId=" + mId + ", mSessionId=" + mSessionId + ", mId5=" + mId5 + ", mName=" + mName
				+ ", mRealName=" + mRealName + ", mNickName=" + mNickName + ", mToken=" + mToken + ", mLoginTime="
				+ mLoginTime + ", phone=" + phone + ", mExpireTime=" + mExpireTime + "]";
	}

	public String getId() {
        return mId;
    }

    public void setId(String id) {
        this.mId = id;
    }

    public String getSessionId() {
		return mSessionId;
	}

	public void setSessionId(String sessionId) {
		this.mSessionId = sessionId;
	}

	public String getName() {
        return mName;
    }

    public void setName(String name) {
        this.mName = name;
    }

    public String getRealName() {
        return mRealName;
    }

    public void setRealName(String realName) {
        this.mRealName = realName;
    }

    public String getToken() {
        return mToken;
    }

    public void setToken(String mToken) {
        this.mToken = mToken;
    }

    public String getEncodedToken() {
        String encodedToken = "";
        try {
            encodedToken = URLEncoder.encode(mToken, "UTF-8");
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }
        return encodedToken;
    }

    public long getExpireTime() {
        return mExpireTime;
    }

    public void setExpireTime(long expireTime) {
        this.mExpireTime = expireTime;
    }

    public long getLoginTime() {
        return mLoginTime;
    }

    public void setLoginTime(long loginTime) {
        this.mLoginTime = loginTime;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getId5() {
        return mId5;
    }

    public void setId5(String id5) {
        this.mId5 = id5;
    }

    public String getNickName() {
        return mNickName;
    }

    public void setNickName(String nickName) {
        this.mNickName = nickName;
    }

    public static String hideName(String name, int first, int last) {
        String hideName = "";
        if (name != null) {
            int length = name.length();
            for (int i = 0; i < length; i++) {
                if (i < first || i > length - last - 1) {
                    hideName += String.valueOf(name.charAt(i));
                } else {
                    hideName += "*";
                }
            }
        }
        return hideName;
    }

    public static synchronized void saveUser(Context context, User user) {
        //不要保存SP_COLUMN_USER_BIND_JPUSH字段
        SharedPreferences sp = PreferenceManager.getDefaultSharedPreferences(context);
        sp.edit().putString(Constants.SP_COLUMN_USER_ID, String.valueOf(user.mId))
                .putString(Constants.SP_COLUMN_USER_NAME, user.mName)
                .putString(Constants.SP_COLUMN_USER_TOKEN, user.mToken)
                .putString(Constants.SP_COLUMN_USER_PHONE_NUMBER, user.phone)
                .putString(Constants.SP_COLUMN_USER_PHONE_NICKNAME, user.mNickName)
                .putString(Constants.SP_COLUMN_USER_PHONE_ID5, user.mId5)
                .putLong(Constants.SP_COLUMN_USER_LOGIN_TIME, user.mLoginTime)
                .putLong(Constants.SP_COLUMN_USER_EXPIRE_TIME, user.mExpireTime)
                .apply();
        onSaveUser(context);
    }

    private static void onSaveUser(Context context) {

    }

    public static synchronized void clearUser(final Context context) {
        SharedPreferences sp = PreferenceManager.getDefaultSharedPreferences(context);
        sp.edit().putString(Constants.SP_COLUMN_WAY_TO_LOGIN, "")
                .putString(Constants.SP_COLUMN_USER_ID, "-1") //不删除temp_user_id，用于检测手势密码
                .putString(Constants.SP_COLUMN_USER_NAME, "")
                .putString(Constants.SP_COLUMN_USER_REAL_NAME, "")
                .putString(Constants.SP_COLUMN_USER_TOKEN, "")
                .putBoolean(Constants.SP_COLUMN_NICKNAME_STATUS, false)
                .putString(Constants.SP_COLUMN_USER_PHONE_NICKNAME, "")
                .putString(Constants.SP_COLUMN_USER_PHONE_ID5, "")
                .putLong(Constants.SP_COLUMN_USER_PHONE_NUMBER, -1)
                .putLong(Constants.SP_COLUMN_USER_LOGIN_TIME, -1)
//                .putLong(Constants.SP_COLUMN_DEFAULT_TRANSACTION_AMOUNT, context.getResources().getInteger(R.integer.default_transaction_amount))
                .putLong(Constants.SP_COLUMN_USER_EXPIRE_TIME, -1)
//                .putBoolean(Constants.SP_COLUMN_PATTERN_PASSWORD_ENABLE, false)
                .putInt(Constants.SP_COLUMN_REAL_NAME_AUTHENTICATION_STATUS, 0)
                .putString(Constants.SP_COLUMN_REAL_NAME, "")
                .putInt(Constants.SP_COLUMN_WRONG_TIMES, 0)
                .putLong(Constants.SP_COLUMN_WRONG_DATE, System.currentTimeMillis())
                .putString(Constants.SP_COLUMN_QUESTION_ID, "")
                .putBoolean(Constants.SP_COLUMN_TRADE_PASSWORD_STATUS, false)
                .putBoolean(Constants.SP_COLUMN_HAS_NEW_MESSAGE, false)
                .remove(Constants.SP_COLUMN_INVEST_REMIND_INITED_2)
                .apply();
        clear();
        if (AoShan.DEBUG) {
            Log.w(TAG, "clearUser!!", new Throwable());
        }
        new Thread() {
            public void run() {
                removeAllUserFiles(context);
            }
        }.start();
    }

    private static void removeAllUserFiles(Context context) {
        if (DEBUG) {
            Log.w(TAG, "removeAllUserFiles!!");
        }
//        new File(context.getFilesDir().getAbsolutePath() + "/" + Constants.LOCK_PATTERN_FILE).delete(); //不删除手势密码，以便下次同一用户登录使用
    }

    private static void clear() {
        sUser = new User();
    }

    /**
     * 判断token是否过期，如果用户修改系统时间，此函数则失效。<br/>
     * 时间可以从服务器获取，如果网络可用的话
     */
    public static boolean isValid() {
        if (sUser == null) {
            return false;
        }

        if (DEBUG) {
            Log.w(TAG, "mExpireTime: " + Constants.SDF_YYYY_MM_DD_HH_MM_SS_SSS.format(new Date(sUser.mExpireTime)));
            Log.w(TAG, "currentTimeMillis: " + Constants.SDF_YYYY_MM_DD_HH_MM_SS_SSS.format(new Date(System.currentTimeMillis())));
        }

        return sUser.mExpireTime > System.currentTimeMillis();
    }

    /**
     * 获取用户显示名-昵称->手机号
     */
    public static String getDisplayName(Context context) {
        User user = User.getInstance(context);
        if (!TextUtils.isEmpty(user.getNickName())) {
            return hideName(user.getNickName(), 1, 1);
        } else if (!TextUtils.isEmpty(user.getPhone()) && AppHelper.isPhoneNumber(String.valueOf(user.getPhone()))) {
            return hideName(String.valueOf(user.getPhone()), 3, 3);
        } else {
            String loginName = user.getName();
            if (loginName.contains("@")) {
                int atIndex = loginName.lastIndexOf("@");
                String hiddenEmail = loginName.substring(0, 1) + "***" + loginName.substring(atIndex - 1, loginName.length());
                return hiddenEmail;
            } else {
                return hideName(loginName, 1, 1);
            }
        }
    }
}
