package com.tecsun.tsb.network.retrofit;

import android.content.Context;
import android.text.TextUtils;

import com.tecsun.tsb.network.progress.ProgressHelper;
import com.tecsun.tsb.network.tool.NetworkConfig;
import com.tecsun.tsb.network.tool.NullHostNameVerifier;
import com.tecsun.tsb.network.tool.SSLSocketClient;
import com.tecsun.tsb.network.tool.SSLSocketFactoryEx;

import java.io.InputStream;
import java.util.concurrent.TimeUnit;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import javax.net.ssl.SSLSocketFactory;

import okhttp3.OkHttpClient;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava.RxJavaCallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;
import rx.Observable;
import rx.Subscriber;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;

/**
 * Retrofit功能配置
 * Created by _Smile on 2016/7/13.
 */
public class RetrofitConfig {

    /**
     * 获取Retrofit设置
     * @param ctx 上下文
     * @param serverUrl 请求地址
     * @param certificateIn 证书文件流
     * @param certificatePwd 证书密码
     * @return retrofit
     */
    protected Retrofit getRetrofit(Context ctx, String serverUrl, InputStream certificateIn, String certificatePwd) {
        Retrofit.Builder builder = new Retrofit.Builder();
        builder.baseUrl(serverUrl);
        builder.client(genericClient(ctx, serverUrl, certificateIn, certificatePwd));
        builder.validateEagerly(true);
        builder.addConverterFactory(GsonConverterFactory.create());
        builder.addCallAdapterFactory(RxJavaCallAdapterFactory.create());

        return builder.build();
    }

    /**
     * 设置通用okhttpclient
     * @param ctx 上下文
     * @param serverUrl  请求地址
     * @param certificateIn 证书文件流
     * @param certificatePwd 证书密码
     * @return client
     */
    private OkHttpClient genericClient(Context ctx, String serverUrl, InputStream certificateIn, String certificatePwd) {
        HttpLoggingInterceptor loggingInterceptor = new HttpLoggingInterceptor();
        //包含header、body数据
        loggingInterceptor.setLevel(HttpLoggingInterceptor.Level.BODY);


        OkHttpClient.Builder builder = new OkHttpClient.Builder();
//        builder.addInterceptor(new OkhttpInterceptor(ctx));
        builder.addInterceptor( loggingInterceptor );
        // test add by yeqw for update progress
        builder = ProgressHelper.addProgress(builder);

        builder.connectTimeout(NetworkConfig.CONNECTION_TIME, TimeUnit.SECONDS);
        builder.writeTimeout(NetworkConfig.WRITE_TIME, TimeUnit.SECONDS);
        builder.readTimeout(NetworkConfig.READ_TIME, TimeUnit.SECONDS);
        builder.retryOnConnectionFailure(true);
        builder.hostnameVerifier(new NullHostNameVerifier());
        builder.sslSocketFactory(SSLSocketClient.getSSLSocketFactory());//配置信任所有证书
        builder.hostnameVerifier(SSLSocketClient.getHostnameVerifier());//配置信任所有证书
        if (certificateIn != null || !TextUtils.isEmpty(certificatePwd)) {
            SSLSocketFactory sslSocketFactory = (new SSLSocketFactoryEx(certificateIn, certificatePwd)).getDelegate();
            if (sslSocketFactory != null) {
                builder.sslSocketFactory(sslSocketFactory);
            }
        }

        return builder.build();
    }

    /**
     * 检查URL类型，https添加证书
     * 不需要做此判断
     * @return 是否是https
     */
    @Deprecated
    private boolean checkHttps(String serverUrl) {
        Pattern pattern = Pattern.compile("(https):\\/\\/([\\w.]+\\/?)\\S*");
        Matcher matcher = pattern.matcher(serverUrl);

        return  matcher.matches();
    }

    /**
     * 设置订阅属性
     * @param observable 观察者
     * @param subscriber 订阅者
     * @param <T>
     */
    protected  <T> void toSubscribes(Observable<T> observable, Subscriber<T> subscriber){
        observable.subscribeOn(Schedulers.io())
                .unsubscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(subscriber);
    }

}
