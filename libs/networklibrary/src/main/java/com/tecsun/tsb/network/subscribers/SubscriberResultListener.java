package com.tecsun.tsb.network.subscribers;

/**
 * 观察者结果监听接口定义
 * @param <T>
 * Created by _Smile on 2016/5/12.
 */
public interface SubscriberResultListener<T> {
    /**
     * 请求成功结果返回
     * @param t 结果泛型
     */
    void onNext(T t);

    /**
     * 请求错误
     * @param e 错误
     */
    void onErr(Throwable e);
}
