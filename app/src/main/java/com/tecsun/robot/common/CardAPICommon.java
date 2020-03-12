package com.tecsun.robot.common;

/**
 * 接口配置
 * Created by _Smile on 2016/10/26.
 */
public class CardAPICommon {

    /*获取个人基础信息*/
    public static final String CARD_BASIC_INSURED_INFO = "/user/login";

    public static final String Yiliao_info = "/sanb/healthCare/selectHealthCareInfo";//医疗

    public static final String yanglao_info = "/sanb/healthCare/selectHealthInfo";//【个人参保信息查询】

    public static final String yanglaolist_info = "/sanb/healthCare/selectPaymentInfo";//养老缴费【个人缴费信息查询】

    public static final String yiliao_HealthCarePaymentInfo = "/sanb/healthCare/selectHealthCarePaymentInfo";//医保待遇支付信息查询

    public static final String yanglao_paymentInfo = "/sanb/healthCare/selectPensionPaymentInfo";//养老待遇发放信息查询


    public static final String getqr_info= "/loginServer/getQrCodePic";//获取二维码登录
    public static final String getqr_lunxun= "/loginServer/getQrCodeUseStatus";//获取二维码登录轮询状态


}
