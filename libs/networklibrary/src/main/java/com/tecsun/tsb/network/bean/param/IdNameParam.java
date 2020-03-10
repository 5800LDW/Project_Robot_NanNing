package com.tecsun.tsb.network.bean.param;

/**
 * 身份证号和姓名 参数组合
 * Created by _Smile on 2016/10/24.
 */
public class IdNameParam extends RequestBaseParam{

    public String sfzh;

    public String xm;

    public String sex;

    public IdNameParam() {

    }

    public IdNameParam(String xm, String sfzh,String deviceid) {
        this.xm = xm;
        this.sfzh = sfzh;
        this.channelcode = "TSB";
        this.deviceid = deviceid;
    }

}
