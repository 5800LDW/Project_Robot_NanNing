package com.tecsun.tsb.network.retrofit;

import android.content.Context;

import com.tecsun.tsb.network.inter.RetrofitAPIInterface;

import java.io.InputStream;

import retrofit2.Retrofit;
import rx.Observable;
import rx.Subscriber;

/**
 * Retrofit请求管理类
 * Created by _Smile on 2016/4/20.
 */
public class RetrofitAPIImpl extends RetrofitConfig implements RetrofitAPIInterface {

    @Override
    public Retrofit initRetrofit(Context ctx, String serverUrl) {
        return getRetrofit(ctx, serverUrl, null, "");
    }

    @Override
    public Retrofit initVerifyRetrofit(Context ctx, String serverUrl, InputStream certificateIn, String certificatePwd) {
        return getRetrofit(ctx, serverUrl, certificateIn, certificatePwd);
    }

    @Override
    public <T> void toSubscribe(Observable<T> observable, Subscriber<T> subscriber) {
        super.toSubscribes(observable, subscriber);
    }
}
