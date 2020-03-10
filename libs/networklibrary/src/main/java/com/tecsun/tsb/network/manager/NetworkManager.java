package com.tecsun.tsb.network.manager;

import com.tecsun.tsb.network.retrofit.RetrofitAPIImpl;

import retrofit2.Retrofit;

/**
 * Created by chen on 2018/1/4.
 */

public class NetworkManager {

    private static RetrofitAPIImpl retrofitAPI;
    private static Retrofit retrofit;
    private static String deviceId;
    private static String tokenId;

    public static Retrofit getRetrofit() {
        return retrofit;
    }

    public static void setRetrofit(Retrofit retrofit) {
        NetworkManager.retrofit = retrofit;
    }

    public static RetrofitAPIImpl getRetrofitAPI() {
        return retrofitAPI;
    }

    public static void setRetrofitAPI(RetrofitAPIImpl retrofitAPI) {
        NetworkManager.retrofitAPI = retrofitAPI;
    }

    public static String getDeviceId() {
        return deviceId;
    }

    public static void setDeviceId(String deviceId) {
        NetworkManager.deviceId = deviceId;
    }

    public static String getTokenId() {
        return tokenId;
    }

    public static void setTokenId(String tokenId) {
        NetworkManager.tokenId = tokenId;
    }


}
