package com.tecsun.robot.request;

import com.tecsun.robot.bean.QrinfoLXBean;
import com.tecsun.robot.bean.YangLaoInfoBean;
import com.tecsun.robot.bean.YangLaoJFBean;
import com.tecsun.robot.bean.YiliaoInfoBean;
import com.tecsun.robot.bean.yanglao.YangLaoFFBean;
import com.tecsun.robot.common.CardAPICommon;
import com.tecsun.robot.param.IdNameBean;
import com.tecsun.robot.param.IdNameYanglaoJfBean;
import com.tecsun.tsb.network.bean.ReplyBaseResultBean;
import com.tecsun.tsb.network.bean.param.IdNameParam;
import com.tecsun.tsb.network.common.NWConstant;

import com.tecsun.robot.bean.qrinfoBean;
import java.util.List;

import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.Query;
import rx.Observable;

/**
 * 社保卡服务接口定义
 * Created by _Smile on 2016/10/26.
 */
public interface CardRequestServer {

    /**
     * 获取个人基础信息
     * @param deviceId 机器码
     * @param tokenId token值
     * @param param 参数
     * @return 订阅者内容
     */
    @POST(CardAPICommon.CARD_BASIC_INSURED_INFO)
    Observable<ReplyBaseResultBean<String>> getPersonalInfo(
            @Query(NWConstant.DEVICE_ID) String deviceId,
            @Query(NWConstant.TOKEN_ID) String tokenId,
            @Body IdNameParam param
    );

    /**
     * 医疗保险信息查询
     * @param param 参数
     * @return 订阅者内容
     */
    @POST(CardAPICommon.Yiliao_info)
    Observable<ReplyBaseResultBean<YiliaoInfoBean>> getYIliaoInfo(
            @Body IdNameBean param
    );
    /**
     * 养老保险信息查询
     * @param param 参数
     * @return 订阅者内容
     */
    @POST(CardAPICommon.yanglao_info)
    Observable<ReplyBaseResultBean<YangLaoInfoBean>> getYanglaoInfo(
            @Body IdNameBean param
    );
    /**
     * 养老保险列表信息查询
     * @param param 参数
     * @return 订阅者内容
     */
    @POST(CardAPICommon.yanglaolist_info)
    Observable<ReplyBaseResultBean<List<YangLaoJFBean>>> getYanglaolistInfo(
            @Body IdNameYanglaoJfBean param
    );



//    /**
//     * 医保待遇支付信息查询
//     * @param param 参数
//     * @return 订阅者内容
//     */
//    @POST(CardAPICommon.yiliao_HealthCarePaymentInfo)
//    Observable<ReplyBaseResultBean<List<YangLaoJFBean>>> getHealthCarePaymentInfo(
//            @Body IdNameYanglaoJfBean param
//    );

    /**
     * 医保待遇支付信息查询
     * @param param 参数
     * @return 订阅者内容
     */
    @POST(CardAPICommon.yiliao_HealthCarePaymentInfo)
    Observable<ReplyBaseResultBean<List<YangLaoFFBean>>> getyiliao_HealthCarePaymentInfo(
            @Body IdNameBean param
    );

    /**
     * 养老待遇发放信息查询
     * @param param 参数
     * @return 订阅者内容
     */
    @POST(CardAPICommon.yanglao_paymentInfo)
    Observable<ReplyBaseResultBean<List<YangLaoFFBean>>> getpaymentInfo(
            @Body IdNameBean param
    );
    /**
     * 获取二维码登录
     * @param param 参数
     * @return 订阅者内容
     */
    @GET(CardAPICommon.getqr_info)
    Observable<ReplyBaseResultBean<qrinfoBean>> getqrinfo(
    );
    /**
     * 获取二维码登录轮询状态
     * @param param 参数
     * @return 订阅者内容
     */
    @GET(CardAPICommon.getqr_lunxun)
    Observable<ReplyBaseResultBean<QrinfoLXBean>> getQrLunxun(
            @Query("code") String code
    );

}
