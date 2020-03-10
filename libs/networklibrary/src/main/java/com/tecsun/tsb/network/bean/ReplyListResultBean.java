package com.tecsun.tsb.network.bean;


/**
 * 请求返回列表类结果
 * Created by _Smile on 2016/10/24.
 */
public class ReplyListResultBean<T> {

    /*返回数据当前页数*/
    public int pageNo;

    /*每页数据*/
    public int pageSize;

    /*总条数*/
    public int count;

    public String payNum;

    /*泛型数据*/
    public T data;

    @Override
    public String toString() {
        return "pageNo:" + pageNo + "\n"
                +"pageSize:" + pageSize + "\n"
                +"count:" + count + "\n";
    }
}
