package com.tecsun.tsb.network;

import android.content.Context;

import com.tecsun.tsb.network.inter.RetrofitAPIInterface;
import com.tecsun.tsb.network.retrofit.RetrofitAPIImpl;

import java.io.InputStream;

import retrofit2.Retrofit;
import rx.Observable;
import rx.Subscriber;

/**
 * Created by _Smile on 2016/7/13.
 */
public class RetrofitInitManager {

    public Retrofit initRetrofit(Context ctx, String serverUrl) {
        RetrofitAPIInterface retrofitAPIInterface = new RetrofitAPIImpl();
        return retrofitAPIInterface.initRetrofit(ctx, serverUrl);
    }

    public Retrofit initVerifyRetrofit(Context ctx, String serverUrl, InputStream certificateIn, String certificatePwd) {
        RetrofitAPIInterface retrofitAPIInterface = new RetrofitAPIImpl();
        return retrofitAPIInterface.initVerifyRetrofit(ctx, serverUrl, certificateIn, certificatePwd);
    }

    public <T> void toSubscribes(Observable<T> observable, Subscriber<T> subscriber) {
        RetrofitAPIInterface retrofitAPIInterface = new RetrofitAPIImpl();
        retrofitAPIInterface.toSubscribe(observable, subscriber);
    }
}
