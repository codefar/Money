package com.example.money;

import java.text.DecimalFormat;
import java.text.SimpleDateFormat;

/**
 * Created by su on 2014/6/16.
 */
public class Constants {
    // App
    public static final String APP_TYPE = "android";
    public static final int APP_ID = 1000;
    public static final int PROMOTION_ID = 1; // PC
    //判断手机号码
    public static final String MOBILE_REGEXP = "^1[34578][0-9]{9}$";

    public static final SimpleDateFormat SDF_YYYY_MM = new SimpleDateFormat("yyyy年MM月");
    public static final SimpleDateFormat SDF_YYYY_MM_DD = new SimpleDateFormat("yyyy-MM-dd");
    public static final SimpleDateFormat SDF_YYYY_MM_DD_SLASH = new SimpleDateFormat("yyyy/MM/dd");
    public static final SimpleDateFormat SDF_YYYY_MM_DD_HH_MM = new SimpleDateFormat("yyyy-MM-dd HH:mm");
    public static final SimpleDateFormat SDF_YYYY_MM_DD_HH_MM_SS = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
    public static final SimpleDateFormat SDF_YYYY_MM_DD_HH_MM_SS_FILE_NAME = new SimpleDateFormat("yyyy_MM_dd_HH_mm_ss");
    public static final SimpleDateFormat SDF_YYYY_MM_DD_T_HH_MM_SS = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss");
    public static final SimpleDateFormat SDF_YYYY_MM_DD_HH_MM_SS_SSS = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss SSS");

    public static final String PLATFORM = "android_";
    public static final String LOCK_PATTERN_FILE = "gesture.key";
    public static final String LOCK_PASSWORD_FILE = "password.key";
    public static final String FILE_FORMAL_UMENG_FEEDBACK = "umeng_feedback_conversations.xml";
    public static final String FILE_FORMAL_UMENG_CACHE = "umeng_it.cache";

    public static final String PASSWORD_STR = "[~`!@#$%^&*()_=+\\[{\\]}\\|;:'\",<.>/?0-9a-zA-Z-]{5,20}";
    /*
    * same as microlog.appender.FileAppender.File in assets/microlog.properties
    * */
    public static final String APP_DIR_ROOT = "souyidai/";
    public static final String APP_DIR_TEMP = "souyidai/temp/";
    public static final String APP_DIR_PIC = "souyidai/pic/";
    public static final String APP_DIR_IMG_TEMP = "souyidai/img/temp";
    public static final String PRODUCT_ID = "T";

    public static final String SP_COLUMN_INIT = "init";
    public static final String SP_COLUMN_AD_SHOW = "ad_show";
    public static final String SP_COLUMN_SUPPORTED_BANKS_DIALOG_SHOW = "supported_banks_dialog_show";
    public static final String SP_COLUMN_SET_PATTERN_PASSWORD_HINT = "set_pattern_password_hint";
    public static final String SP_COLUMN_REAL_NAME = "real_name";
    public static final String SP_COLUMN_REAL_NAME_AUTHENTICATION_STATUS = "real_name_authentication_status";
    public static final String SP_COLUMN_PATTERN_PASSWORD_ENABLE = "pattern_password_enable";
    public static final String SP_COLUMN_WAY_TO_LOGIN = "way_to_login";
    public static final String SP_COLUMN_USER_ID = "user_id";
    public static final String SP_COLUMN_USER_NAME = "user_name";
    public static final String SP_COLUMN_USER_REAL_NAME = "user_real_name";
    public static final String SP_COLUMN_USER_TOKEN = "user_token";
    public static final String SP_COLUMN_USER_LOGIN_TIME = "user_login_time";
    public static final String SP_COLUMN_USER_PHONE_NUMBER = "user_phone_number";
    public static final String SP_COLUMN_USER_PHONE_NICKNAME = "user_phone_nickname";
    public static final String SP_COLUMN_USER_PHONE_EMAIL = "user_phone_email";
    public static final String SP_COLUMN_USER_PHONE_ID5 = "user_phone_id5";
    public static final String SP_COLUMN_USER_EXPIRE_TIME = "user_expire_time";
    public static final String SP_COLUMN_USER_BIND_JPUSH = "user_bind_jpush";
    public static final String SP_COLUMN_NEED_TO_UPDATE = "need_to_update";
    public static final String SP_COLUMN_FORCE_TO_UPDATE = "force_to_update";
    public static final String SP_COLUMN_NEW_VERSION_APK_NAME = "new_version_apk_name";
    public static final String SP_COLUMN_TEMP_USER_ID = "temp_user_id";
    public static final String SP_COLUMN_SHOW_UPLOAD_HINT = "show_upload_hint";
    public static final String SP_COLUMN_NEW_VERSION = "new_version";
    public static final String SP_COLUMN_NEW_VERSION_URL = "new_version_url";
    public static final String SP_COLUMN_NEW_VERSION_DESCRIPTION = "new_version_description";
    public static final String SP_COLUMN_ENVIRONMENT = "environment";
    public static final String SP_COLUMN_MSG_PUSH = "msg_push";
    public static final String SP_COLUMN_WRONG_DATE = "wrong_date";
    public static final String SP_COLUMN_WRONG_TIMES = "wrong_times";
    public static final String SP_COLUMN_TRADE_PASSWORD_STATUS = "trade_password_status";
    public static final String SP_COLUMN_NICKNAME_STATUS = "nickname_status";
    public static final String SP_COLUMN_EMAIL_STATUS = "email_status";
    public static final String SP_COLUMN_QUESTION_ID = "question_id";
    public static final String SP_COLUMN_X_AUTH_TOKEN = "x-auth-token";
    public static final String SP_COLUMN_HAS_NEW_MESSAGE = "has_new_message";
    public static final String SP_COLUMN_MAX_UNREAD_MESSAGE_ID = "max_unread_message_id";
    public static final String SP_COLUMN_MAX_UNREAD_NOTICE_ID = "max_unread_notice_id";
    public static final String SP_COLUMN_INVEST_REMIND_INITED_2 = "invest_remind_inited_2";

    public static final String AUTH_TOKEN_SECRET_KEY = "f91f65e1c42d58822a94a7460361e5d7";
    public static final boolean DEBUG = false;

    public static final int LOCK_STATUS_LOCK = 0;
    public static final int LOCK_STATUS_SET = 1;

    public static final DecimalFormat FORMAT_AMOUNT = new DecimalFormat("###,###,###,##0.00");
    public static final DecimalFormat FORMAT_PERCENTAGE = new DecimalFormat("###0.00");
    public static final DecimalFormat FORMAT_WITH_NO_SUFFIX_ZERO = new DecimalFormat("###0.00");

    public static final String UMENG_ACTION_SIGNIN = "signin";//登录用户数
    public static final String UMENG_ACTION_SIGNUP1 = "signup1";//进入注册页面次数
    public static final String UMENG_ACTION_SIGNUP2 = "signup2";//点击注册“下一步”按键数
    public static final String UMENG_ACTION_SIGNUP3 = "signup3";//提交注册数
    public static final String UMENG_ACTION_SIGNUP4 = "signup4";//注册成功用户数
    public static final String UMENG_ACTION_SPECIAL_ZR_CPA_SIGNUP4 = "zr_cpa_signup4";//中润无限应用宝CPA注册成功用户数
    public static final String UMENG_ACTION_BIDLIST = "bidlist";//点击抢标按键数（列表）
    public static final String UMENG_ACTION_BIDMORE = "bidmore";//点击抢标按键数（详情）
    public static final String UMENG_ACTION_BID1 = "bid1";//点击同意协议并投标按键数
    public static final String UMENG_ACTION_BID2 = "bid2";//点击投资金额确认按键数
    public static final String UMENG_ACTION_BID3 = "bid3";//投资成功数
    public static final String UMENG_ACTION_READ = "read";//用户进入标的详情页的次数
    public static final String UMENG_ACTION_GESTURE1 = "gesture1";//使用手势密码的用户
    public static final String UMENG_ACTION_GESTURE2 = "gesture2";//未使用手势密码的用户
    public static final String UMENG_ACTION_PUSH_SD = "push_sd";//push notify条数
    public static final String UMENG_ACTION_PUSH_DJ = "push_dj";//Push点击数
}
