package com.tecsun.robot.common;

/**
 * 缺省值
 * Created by _Smile on 2016/9/19.
 */
public class Defs {

    /** 标题固定KEY值 */
    public static final String KEY_TITLE = "key_title";
    /** 标题内容类型 */
    public static final String KEY_TITLE_TYPE = "key_title_type";

    /** 1分钟倒计时，单位秒 */
    public static final int COUNTDOWN_1 = 60;
    /** 5分钟倒计时，单位秒 */
    public static final int COUNTDOWN_5 = 300;
//    public static final int COUNTDOWN_5 = 10;

    public static final String OPTION_ID = "option_id";
    public static final String OPTION_NAME = "option_name";

    public static final int CARD_PROGRESS = 0x0101;
    public static final int CHANGE_PWD = 0x0102;
    public static final int ACTIVATION_CARD = 0x0103;
    public static final int REPORT_LOSS = 0x0104;
    public static final int CANCEL_REPORT_LOSS = 0x0105;
    public static final int VERIFY_CARD_PWD = 0x0106;
    public static final int CARD_BASE_INFO = 0x0107;
    public static final int CARD_RETENTION = 0x0108;
    public static final int CARD_LOCATION = 0x0109;
    public static final int CARD_GRANT = 0x01010;
    public static final int RESET_PWD = 0x01011;
    public static final int DETECTION_REPAIR = 0x01012;

    public static final int CHANGE_SSCARD_PASSWORD = 0x01013;//修改社保卡密码
    /*返回键默认返回上一界面*/
    public static final int BACK_CODE_NORMAL = -1;

    /* 隐藏返回键 */
    public static final int HIDE_TITLE_BACK = 0x00000099;
    public static final int SHOW_TITLE_EXIT = 0x00000088;
    public static final int RETENTION_EXIT = 0x00000077;
    public static final int SHOW_EXIT_ACCOUNT = 0x00000066;
    public static final int SHOW_ACCOUNT_INFO = 0x00000055;
    public static final int VD_ACCOUNT_INFO = 0x00000044;
    public static final int SHOW_FORGET_PWD = 0x00000033;
    public static final int SHOW_FILTRATE = 0x00000022;
    public static final int SHOW_CLOSE = 0x00000011;
    public static final int SHOW_COMPILE = 0x00000010;
    public static final int SHOW_RECORD = 0x00000012;
    public static final int GONE_RECORD = 0x00000013;
    public static final int SHOW_SEARCH = 0x00000014;
    public static final int GOVE_COMPILE = 0x00000015;

    public static final int FINISH_ACTIVITY = 0x00000016;

    public static final String ACTION_CHECK_SS_CARD = "com.tecsun.tsb.yth.CheckSSCard";
    public static final String ACTION_CHECK_ID_CARD = "com.tecsun.tsb.yth.CheckIdCard";

    /*社保卡申请标题定义本地广播Action值*/
    public static final String ACTION_TITLE = "com.tecsun.tsb.yth.Title";
    /*社保卡申请标题传参*/
    public static final String CHANGE_CARD_APP_TITLE = "change_card_app_title";
    /*社保卡申请返回编码*/
    public static final String CHANGE_CARD_APP_BACK_CODE = "change_card_app_back_code";
    /*滞留卡退出*/
    public static final String ACTION_RETENTION_EXIT= "com.tecsun.tsb.yth.retention.exit";
    public static final String RETENTION_EXIT_CODE= "retention_exit_code";

    public static final String ACTION_VILLAGE_DOCTOR= "com.tecsun.tsb.yth.village.doctor";
    public static final String VILLAGE_DOCTOR_CODE= "village_doctor_code";

    public static final String ACTION_FORGET_PWD= "com.tecsun.tsb.yth.forget.pwd";
    public static final String FORGET_PWD_CODE= "forget_pwd_code";

    public static final String ACTION_FILTRATE= "com.tecsun.tsb.yth.filtrate";
    public static final String FILTRATE_CODE= "filtrate_code";

    public static final String ACTION_CLOSE= "com.tecsun.tsb.yth.close";
    public static final String CLOSE_CODE= "close_code";

    public static final String ACTION_COMPILE= "com.tecsun.tsb.yth.compile";
    public static final String COMPILE_CODE= "compile_code";

    public static final String ACTION_RECORD= "com.tecsun.tsb.yth.record";
    public static final String RECORD_CODE= "record_code";

    public static final String ACTION_SEARCH= "com.tecsun.tsb.yth.search";
    public static final String SEARCH_CODE= "search_code";


    public static final String ACTION_FINISH = "com.tecsun.tsb.yth.finish";
    public static final String FINISH_CODE= "finish_code";

}
