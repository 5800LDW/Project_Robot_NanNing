package com.tecsun.robot.common;

/**
 * 社保卡业务配置
 * Created by _Smile on 2016/10/25.
 */
public class CardServiceConfig {


    /*输入新密码*/
    public static final int CHANGE_PWD_STEP_NEW = 0x0010;
    /*输入确认密码*/
    public static final int CHANGE_PWD_STEP_CONFIRM = 0x0011;
    /*输入确认密码失败*/
    public static final int CHANGE_PWD_STEP_CONFIRM_FAILURE = 0x0012;
    /*输入密码成功*/
    public static final int CHANGE_PWD_STEP_SUCCESS = 0x0013;
    /*输入原密码*/
    public static final int CHANGE_PWD_STEP_OLD = 0x0014;


}
