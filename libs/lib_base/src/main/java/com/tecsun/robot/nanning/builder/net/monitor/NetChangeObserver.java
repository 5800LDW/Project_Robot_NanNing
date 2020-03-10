package com.tecsun.robot.nanning.builder.net.monitor;

/**
 * 网络改变观察者，观察网络改变后回调的方法
 * 16/9/13.
 */
public interface NetChangeObserver {

    /**
     * 网络连接回调 type为网络类型
     */
     void onNetConnected(NetUtils.NetType type);

    /**
     * 没有网络
     */
     void onNetDisConnect();
}