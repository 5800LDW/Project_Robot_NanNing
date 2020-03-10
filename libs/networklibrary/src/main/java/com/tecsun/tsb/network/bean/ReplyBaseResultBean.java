package com.tecsun.tsb.network.bean;

import android.text.TextUtils;

import java.io.Serializable;

/**
 * 请求返回基础数据
 * Created by _Smile on 2016/10/24.
 */
public class ReplyBaseResultBean<T> implements Serializable {

    /*返回结果码*/
    public String state;

    /*返回结果描述*/
    public String message;

    /*泛型数据*/
    public T data;

    public boolean isSuccess() {
        return !TextUtils.isEmpty(state) && (state.equals("true")||state.equals("200"));
    }

    @Override
    public String toString() {
        return "state:" + state + "\n"
                + "message:" + message + "\n"
                ;
    }
}
