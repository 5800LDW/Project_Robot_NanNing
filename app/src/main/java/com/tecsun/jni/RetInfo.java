package com.tecsun.jni;

public class RetInfo {
    public String strInfo; 	// 返回信息
    public int iRet; 		// 返回值

    public RetInfo() {
        strInfo = "";
        iRet = 0;
    }
    public int GetRet()
    {
        return iRet;
    }
    public String GetInfo()
    {
        return strInfo;
    }
}
