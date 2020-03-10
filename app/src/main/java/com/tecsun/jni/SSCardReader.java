package com.tecsun.jni;

import android.hardware.usb.UsbDeviceConnection;
import android.hardware.usb.UsbEndpoint;

/*
 * 安卓外接读卡器SO库
 * @author ljx 20200116
 */

public class SSCardReader {

	/**
	 * 初始化读卡器
	 *  @param[IN]  deviceConnection:	UsbDeviceConnection对象<br>
	 *              usbEpIn:			UsbEndpoint对象 <br>
	 *              usbEpOut:			UsbEndpoint对象 <br>
	 *              特别说明：这3个参数由APP层打开USB设备获取传入底层
	 *  @return     返回值0表示成功，非0表示失败 <br>
	 */
	public static native int iInitReader(UsbDeviceConnection deviceConnection, UsbEndpoint usbEpIn, UsbEndpoint usbEpOut);

	/**
	 * 读基本信息
	 *  @param[IN]  iType:	1接触式；2非接触式<br>
	 *              obj:	返回数据对象 <br>
	 *  @return     返回值0表示成功，输出信息为：发卡地区行政区划代码（卡识别码前6位）、社会保障号码、卡号、卡识别码、姓名、卡复位信息（仅取历史字节）、规范版本、发卡日期、卡有效期、终端机编号、终端设备号，中间用“|”分隔 <br>
	 *  			返回值非0表示失败，输出信息为错误信息描述 <br>
	 */
	public static native RetInfo iReadCardBas(int iType, RetInfo obj);
	/**
	 * PIN校验
	 *  @param[IN]  iType:	1接触式；2非接触式<br>
	 *              pPIN:	4-16位待校验密码 <br>
	 *              obj:	返回数据对象 <br>
	 *  @return     返回值0表示成功，非0表示失败 <br>
	 */
	public static native RetInfo iVerifyPINK(int iType, String pPIN, RetInfo obj);
	/**
	 * PIN修改
	 *  @param[IN]  iType:	1接触式；2非接触式<br>
	 *              pOldPIN:4-16位原密码 <br>
	 *              pNewPIN:4-16位新密码 <br>
	 *              obj:	返回数据对象 <br>
	 *  @return     返回值0表示成功，非0表示失败 <br>
	 */
	public static native RetInfo iChangePINK(int iType, String pOldPIN, String pNewPIN, RetInfo obj);

	/**
	 * 读身份证数据（原始数据串，未解析）
	 *  @param[IN]  obj:	返回数据对象<br>
	 *  @return     返回值0表示成功，非0表示失败 <br>
	 */
	public static native RetInfo iReadIDOrgData(RetInfo obj);
	// 软件版本
	public static native String GetLibVer();

	static {
		System.loadLibrary("F4_MDS");
		System.loadLibrary("SSCardReader");
	}
}
