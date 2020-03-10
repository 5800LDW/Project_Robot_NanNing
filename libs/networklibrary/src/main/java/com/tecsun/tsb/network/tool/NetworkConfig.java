package com.tecsun.tsb.network.tool;

/**
 * 网络请求配置
 * Created by _Smile on 2016/8/26.
 */
public class NetworkConfig {

    /** 请求的连接时间 单位：秒*/
    public static final int CONNECTION_TIME = 60;//20170621
    /** 请求写入时间 单位：秒 */
    public static final int WRITE_TIME = 90;
    /** 请求返回的读取时间 单位：秒 */
    public static final int READ_TIME = 90;

    //设缓存有效期为1天
    public static final long CACHE_STALE_SEC = 60 * 60 * 24;
    //查询缓存的Cache-Control设置，使用缓存
    public static final String CACHE_CONTROL_CACHE = "only-if-cached, max-stale=" + CACHE_STALE_SEC;
    //查询网络的Cache-Control设置。不使用缓存
    public static final String CACHE_CONTROL_NETWORK = "max-age=0";
}
