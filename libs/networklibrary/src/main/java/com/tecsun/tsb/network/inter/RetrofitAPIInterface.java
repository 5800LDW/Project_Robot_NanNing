package com.tecsun.tsb.network.inter;

import android.content.Context;

import java.io.InputStream;

import retrofit2.Retrofit;
import rx.Observable;
import rx.Subscriber;

/**
 * Retrofit初始化方法接口定义
 * Created by _Smile on 2016/7/13.
 */
public interface RetrofitAPIInterface {

    /**
     * 初始化无证书验证Retrofit
     * @param ctx 上下文
     * @param serverUrl 请求地址
     * @return retrofit
     */
    Retrofit initRetrofit(Context ctx, String serverUrl);

    /**
     * 初始化证书验证Retrofit
     * @param ctx 上下文
     * @param serverUrl 请求地址
     * @param certificateIn 验证证书文件流
     * @param certificatePwd 验证证书密码
     * @return retrofit
     */
    Retrofit initVerifyRetrofit(Context ctx, String serverUrl, InputStream certificateIn, String certificatePwd);

    /**
     * 设置订阅属性
     * @param observable 观察者
     * @param subscriber 订阅者
     * @param <T> 泛型
     */
    <T> void toSubscribe(Observable<T> observable, Subscriber<T> subscriber);
}
