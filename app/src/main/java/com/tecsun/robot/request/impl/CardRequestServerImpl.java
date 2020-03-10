package com.tecsun.robot.request.impl;
import com.tecsun.robot.bean.QrinfoLXBean;
import com.tecsun.robot.bean.YangLaoInfoBean;
import com.tecsun.robot.bean.YangLaoJFBean;
import com.tecsun.robot.bean.YiliaoInfoBean;
import com.tecsun.robot.bean.yanglao.YangLaoFFBean;
import com.tecsun.robot.param.IdNameBean;
import com.tecsun.robot.param.IdNameYanglaoJfBean;
import com.tecsun.robot.request.CardRequestServer;
import com.tecsun.tsb.network.bean.ReplyBaseResultBean;
import com.tecsun.tsb.network.bean.param.IdNameParam;
import com.tecsun.tsb.network.manager.NetworkManager;
import com.tecsun.tsb.network.retrofit.RetrofitAPIImpl;
import com.tecsun.tsb.network.subscribers.ProgressSubscriber;

import com.tecsun.robot.bean.qrinfoBean;
import java.util.List;

import retrofit2.Retrofit;
import rx.Observable;
import rx.Subscriber;

/**
 * 处理社保卡业务网络请求
 * Created by _Smile on 2016/10/26.
 */
public class CardRequestServerImpl {

    private static CardRequestServerImpl deviceInstance = new CardRequestServerImpl();
    private CardRequestServer cardServiceServer;
    private RetrofitAPIImpl retrofitAPI;

    public static CardRequestServerImpl getInstance() {
        return deviceInstance;
    }

    private CardRequestServerImpl() {
        init();
    }

    private void init() {
        retrofitAPI = NetworkManager.getRetrofitAPI();
        Retrofit retrofit = NetworkManager.getRetrofit();
        cardServiceServer = retrofit.create(CardRequestServer.class);
    }

    /**
     * 获取个人基本参保信息
     * @param param 请求参数
     * @param subscriber 订阅者
     */
    public void getPersonalInfo(IdNameParam param, Subscriber<ReplyBaseResultBean<String>> subscriber) {
        Observable observable = cardServiceServer.getPersonalInfo(NetworkManager.getDeviceId(),
                NetworkManager.getTokenId(), param);
        retrofitAPI.toSubscribe(observable, subscriber);
    }

    /**
     * 医疗保险信息查询
     * @param param 请求参数
     * @param subscriber 订阅者
     */
    public void getYiliaoInfo(IdNameBean param, Subscriber<ReplyBaseResultBean<YiliaoInfoBean>> subscriber) {
        Observable observable = cardServiceServer.getYIliaoInfo(param);
        retrofitAPI.toSubscribe(observable, subscriber);
    }

    /**
     * 养老保险信息查询
     * @param param 请求参数
     * @param subscriber 订阅者
     */
    public void getYanglaoInfo(IdNameBean param, Subscriber<ReplyBaseResultBean<YangLaoInfoBean>> subscriber) {
        Observable observable = cardServiceServer.getYanglaoInfo(param);
        retrofitAPI.toSubscribe(observable, subscriber);
    }
    /**
     * 养老保险缴费列表信息查询
     * @param param 请求参数
     * @param subscriber 订阅者
     */
    public void getYanglaolistInfo(IdNameYanglaoJfBean param, Subscriber<ReplyBaseResultBean<List<YangLaoJFBean>>> subscriber) {
        Observable observable = cardServiceServer.getYanglaolistInfo(param);
        retrofitAPI.toSubscribe(observable, subscriber);
    }

//    /**
//     * 医保待遇支付信息查询
//     * @param param 参数
//     * @return 订阅者内容
//     */
//    public void getHealthCarePaymentInfo(IdNameYanglaoJfBean param, Subscriber<ReplyBaseResultBean<List<YangLaoJFBean>>> subscriber) {
//        Observable observable = cardServiceServer.getHealthCarePaymentInfo(param);
//        retrofitAPI.toSubscribe(observable, subscriber);
//    }

    /**
     * yiliao_HealthCarePaymentInfo
     * @param param 参数
     * @return 订阅者内容
     */
    public void getyiliao_HealthCarePaymentInfo(IdNameBean param, Subscriber<ReplyBaseResultBean<List<YangLaoFFBean>>> subscriber) {
        Observable observable = cardServiceServer.getyiliao_HealthCarePaymentInfo(param);
        retrofitAPI.toSubscribe(observable, subscriber);
    }

    /**
     * 养老待遇发放信息查询
     * @param param 参数
     * @return 订阅者内容
     */
    public void getpaymentInfo(IdNameBean param, Subscriber<ReplyBaseResultBean<List<YangLaoFFBean>>> subscriber) {
        Observable observable = cardServiceServer.getpaymentInfo(param);
        retrofitAPI.toSubscribe(observable, subscriber);
    }

    /**
     * 获取二维码登录
     * @param param 参数
     * @param subscriber
     * @return 订阅者内容
     */
    public void getQr(ProgressSubscriber<ReplyBaseResultBean<qrinfoBean>> subscriber) {
        Observable observable = cardServiceServer.getqrinfo();
        retrofitAPI.toSubscribe(observable, subscriber);
    }
    /**
     * 获取二维码登录轮循
     * @param param 参数
     * @param subscriber
     * @return 订阅者内容
     */
    public void getQrLunxun(String code,ProgressSubscriber<ReplyBaseResultBean<QrinfoLXBean>> subscriber) {
        Observable observable = cardServiceServer.getQrLunxun(code);
        retrofitAPI.toSubscribe(observable, subscriber);
    }
}
