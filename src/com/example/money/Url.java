package com.example.money;

/**
 * Created by su on 2015/11/26.
 */
public final class Url {
    public static final String VERSION_CHECK_SUFFIX = "version/check";//？

    public static final String REGISTER = AoShan.HOST + "app/user/register"; //注册
    public static final String SEND_REGISTER_VERIFY_CODE = AoShan.HOST + "app/user/sendRegisterVerifyCode";//发送注册验证码
    public static final String LOGIN = AoShan.HOST + "app/user/login";//登录
    public static final String SEND_FIND_PWD_VERIFY_CODE = AoShan.HOST + "app/user/sendFindPwdVerifyCode";//忘记密码时请求验证码
    public static final String RESET_PASSWORD = AoShan.HOST + "app/user/resetPassword";//找回密码
    public static final String FEEDBACK = AoShan.HOST + "app/center/feedback";//意见反馈
    public static final String SERVICE_EX = AoShan.HOST + "app/user/serviceEx";//用户协议
    public static final String LOGOUT = AoShan.HOST + "user/logout";//登出
    public static final String CHANGE_PASSWORD = AoShan.HOST + "user/changePassword";//修改密码
    public static final String PAY_FROM_APP = AoShan.HOST + "user/payFromApp";//用户主动投标
    public static final String PAY_FROM_APP_BACK = AoShan.HOST + "user/payFromAppBack";//用户主动投标的投标结果
    public static final String START_USER_REGISTER = AoShan.HOST + "user/startUserRegister";//用户开户
    public static final String START_USER_REGISTER_BACK = AoShan.HOST + "user/startUserRegisterBack"; //用户开户结果
    public static final String START_CASH = AoShan.HOST + "user/ startCash"; //用户取现
    public static final String START_CASH_BACK = AoShan.HOST + "user/startCashBack"; //用户取现结果
    public static final String START_RECHARGE = AoShan.HOST + "user/startRecharge"; //用户充值
    public static final String START_RECHARGE_BACK = AoShan.HOST + "user/startRechargeBack"; //用户充值结果
    public static final String START_BIND_CARD = AoShan.HOST + "user/startBindCard"; //用户绑卡
    public static final String START_BIND_BACK = AoShan.HOST + "user/startBindBack"; //用户绑卡结果
    public static final String BANK_NUM = AoShan.HOST + "user/bankNum"; //可用充值银行
    public static final String START_CREDIT_ASSIGN = AoShan.HOST + "user/startCreditAssign"; //债权认购
    public static final String START_CREDIT_ASSIGN_BACK = AoShan.HOST + "user/startCreditAssignBack"; //债权认购结果
    public static final String LOAN_LIST = AoShan.HOST + "app/loan/loanList"; //获得投标列表
    public static final String LOAN_GET = AoShan.HOST + "app/loan/get"; //标的详情
    public static final String LOAN_GET_INVESTOR_TRADE_LIST = AoShan.HOST + "app/loan/getInvestorTradeList"; //投标纪录
    public static final String LOAN_GET_IMAGE_LIST = AoShan.HOST + "app/loan/getImageList"; //投标图片获取
    public static final String LOAN_PRIVACY_EX = AoShan.HOST + "app/loan/privacyEx"; //借款协议 TODO HTML???
    public static final String GET_APP_VERSION = AoShan.HOST + "app/user/getAppVersion"; //获取最新App版本号
    public static final String TOP_LOAN = AoShan.HOST + "app/loan/topLoan"; //获得首页推荐标
    public static final String LOAN_GET_DEBT_LIST = AoShan.HOST + "app/loan/getDebtList"; //获得债权转让列表
    public static final String GET_ACCOUNT_INFO = AoShan.HOST + "app/center/getAccountInfo"; //获取个人资料
    public static final String GET_REPAYING_LIST = AoShan.HOST + "app/center/getRepayingList"; //获取交易列表
    public static final String GET_DEBT_LIST_FOR_USER = AoShan.HOST + "app/center/getDebtList4User"; //债权转让纪录列表
    public static final String GET_SESSION_RECORD_LIST = AoShan.HOST + "app/center/getSessionRecordList"; //获取资金流水列表
    public static final String GET_INVEST_RECORD_LIST_FOR_BACKING = AoShan.HOST + "app/center/getInvestRecordList4Backing"; //处于还款中标列表
    public static final String SETUP_AUTO_INVEST = AoShan.HOST + "app/center/setupAutoInvest"; //自动投标设置
    public static final String SYS_MESSAGE_LIST = AoShan.HOST + "app/sys/message/list"; //获取消息推送
    public static final String GET_INVEST_RECORD_LIST_FOR_HISTORY = AoShan.HOST + "app/center/getInvestRecordList4History"; //获取用户历史投资
    public static final String GET_AUTO_INVEST = AoShan.HOST + "app/center/getAutoInvest"; //获取自动投标设置
    public static final String GET_BIDDING_LIST = AoShan.HOST + "app/center/getBiddingList"; //获取用户筹款中的标
    public static final String GET_INVEST_RECORD_LIST_FOR_NOW = AoShan.HOST + "app/center/getInvestRecordList4Now"; //获取用户投标中的借款
    public static final String SELL = AoShan.HOST + "app/center/sell"; //转让债权
    public static final String SELL_PRICE = AoShan.HOST + "app/center/sellPrice"; //转让债权价格
    public static final String UNSELL = AoShan.HOST + "app/center/unsell"; //撤销债权转让
    public static final String SYS_AD_LIST = AoShan.HOST + "app/sys/ad/list"; //首页广告列表
}
