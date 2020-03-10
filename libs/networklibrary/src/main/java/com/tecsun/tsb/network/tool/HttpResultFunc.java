package com.tecsun.tsb.network.tool;

/**
 * 用来统一处理Http的resultCode,并将HttpResult的Data部分剥离出来返回给subscriber
 *
 * @param <T>   Subscriber真正需要的数据类型，也就是Data部分的数据类型
 * 注意，现暂未使用，使用方法，在Impl类中如Observable observable = deviceService.getDeviceInfo(param).map(new HttpResultFunc<DeviceInfoBean>());
 */
//@Deprecated
//public class HttpResultFunc<T> implements Func1<ResultBean<T>, T> {
//
//    @Override
//    public T call(ResultBean<T> httpResult) {
//        return httpResult.data;
//    }
//}
