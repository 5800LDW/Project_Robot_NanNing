package com.tecsun.tsb.network.retrofit;

import android.content.Context;
import android.os.Handler;
import android.os.Message;
import android.util.Log;

import com.tecsun.tsb.network.dialog.NetworkErrorDialog;

import java.io.IOException;

import okhttp3.Interceptor;
import okhttp3.Request;
import okhttp3.Response;

/**
 * Okhttp请求过滤器
 * Created by _Smile on 2016/4/20.
 */
public class OkhttpInterceptor implements Interceptor {

    private static final String TAG = "OkhttpInterceptor";
    private Context mContext;

    public OkhttpInterceptor(Context ctx) {
        this.mContext = ctx;
    }

    @Override
    public Response intercept(Chain chain) throws IOException {
        Response response = null;
        try {
            long startTime = System.nanoTime();
            Request request = chain.request();

                request.newBuilder()
                        .addHeader("Content-Type", "application/x-www-form-urlencoded; multipart/form-data; text/html; charset=UTF-8")
                        .addHeader("Accept-Encoding", "gzip, deflate")
                        .addHeader("Connection", "keep-alive")
                        .addHeader("Accept", "*/*")
                        .build();

                response = chain.proceed(request);
                long endTime = System.nanoTime();
                Log.i(TAG, String.format("Received response for %s in %.1fms%n%s",
                        request.url(), (endTime - startTime) / 1e6d, response.headers()));

        } catch (Exception e) {
//            mOKHttpInterceptorHandler.sendEmptyMessage(0);
            e.printStackTrace();
        }
        return response;
    }

    Handler mOKHttpInterceptorHandler = new Handler() {

        @Override
        public void handleMessage(Message msg) {
            new NetworkErrorDialog(mContext, false).show();
            super.handleMessage(msg);
        }
    };
}
