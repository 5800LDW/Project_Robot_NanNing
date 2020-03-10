package com.tecsun.robot.fragment;

import android.app.PendingIntent;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.hardware.usb.UsbDevice;
import android.hardware.usb.UsbDeviceConnection;
import android.hardware.usb.UsbEndpoint;
import android.hardware.usb.UsbInterface;
import android.hardware.usb.UsbManager;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.tecsun.jc.base.utils.ToastUtils;
import com.tecsun.jni.RetInfo;
import com.tecsun.jni.SSCardReader;
import com.tecsun.robot.bean.evenbus.IdCardBean;
import com.tecsun.robot.common.Defs;
import com.tecsun.robot.fragment.sscardmanager.ChangepasswordFragment;
import com.tecsun.robot.nanninig.R;
import com.tecsun.robot.nanninig.ReadSScardActivity;
import com.tecsun.robot.utils.IntentUtils;
import com.tecsun.robot.utils.StaticBean;

import org.greenrobot.eventbus.EventBus;

import java.util.HashMap;
import java.util.Iterator;

public class ReadSSCardFragment extends BaseFragment{
    public static final String ACTION_USB_PERMISSION = "com.android.example.USB_PERMISSION";
    private UsbManager m_manager;			// USB管理器
    private UsbDevice m_UsbDevice;			// 找到的USB设备
    private UsbInterface m_Interface;		// USB接口
    private boolean m_IsReaderOpen = false;	// 读卡器是否获取打开权限
    public UsbDeviceConnection mDeviceConnection;
    public UsbEndpoint usbEpOut;			//代表一个接口的某个节点的类:写数据节点
    public UsbEndpoint usbEpIn;				//代表一个接口的某个节点的类:读数据节点
    //    private TextView txtView;
    boolean isRead = false;
    boolean isStop = false;


    private int optionId;
    private String optionName;

    ImageView img_sfz;
    View mView;
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        mView = inflater.inflate(R.layout.activity_read_fmidcard, container, false);

        img_sfz = (ImageView) mView.findViewById(R.id.img_sfz);
        Glide.with(getActivity()).load(R.mipmap.logo_dqsbk)
                .diskCacheStrategy(DiskCacheStrategy.ALL).into(img_sfz);

        if (getArguments() != null) {
            optionId = getArguments().getInt(Defs.OPTION_ID, -1);
            optionName = getArguments().getString(Defs.OPTION_NAME, "");
            Log.d("收到的optionId",optionId+"---"+optionName);
        }

        if (optionId == Defs.CARD_LOCATION){

        }else if (optionId == Defs.CARD_GRANT){

        }

        try {
            initUsbData();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        //延迟1秒让他们看到界面
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                start();
            }
        },1000);

        return mView;
    }


    //初始化USB接口
    private void initUsbData()throws InterruptedException {
        boolean IsExistReader = false;
        // 获取USB设备
        m_manager = (UsbManager) getActivity().getSystemService(Context.USB_SERVICE);
        //获取到设备列表
        HashMap<String, UsbDevice> deviceList = m_manager.getDeviceList();
        Iterator<UsbDevice> deviceIterator = deviceList.values().iterator();
        while (deviceIterator.hasNext()) {
            Log.d("test mUsbDevice", "find usbdevice");
            // 读卡器PID：FFFF
            m_UsbDevice = deviceIterator.next();
            if ((0xffff == m_UsbDevice.getVendorId()) && (0xffff == m_UsbDevice.getProductId())) {//找到指定设备
                IsExistReader = true;
                break;
            }
        }
        if (!IsExistReader) {
//            txtView.setText("未找到读卡器，请连接读卡器并重启该应用！");
            Log.d("Tag","未找到读卡器，请连接读卡器并重启该应用！");
            return;
        }
        //获取设备接口
        for (int i = 0; i < m_UsbDevice.getInterfaceCount(); ) {
            // 一般来说一个设备都是一个接口，你可以通过getInterfaceCount()查看接口的个数
            // 这个接口上有两个端点，分别对应OUT 和 IN
            UsbInterface usbInterface = m_UsbDevice.getInterface(i);
            m_Interface = usbInterface;
            break;
        }

        if (m_Interface != null) {	// 判断是否有权限
            if (m_manager.hasPermission(m_UsbDevice)) {
                afterGetUsbPermission();
            } else {
                Log.d("SSCardReaderDemo", "request to get permission");
                IntentFilter filter = new IntentFilter(ACTION_USB_PERMISSION);
                getActivity().registerReceiver(mUsbPermissionActionReceiver, filter);

                PendingIntent mPermissionIntent = PendingIntent.getBroadcast(getActivity(), 0, new Intent(ACTION_USB_PERMISSION), 0);
                m_manager.requestPermission(m_UsbDevice, mPermissionIntent);
            }
        }

    }


    private final BroadcastReceiver mUsbPermissionActionReceiver = new BroadcastReceiver() {
        @Override
        public void onReceive(Context context, Intent intent) {
            String action = intent.getAction();
            if (ACTION_USB_PERMISSION.equals(action)) {
                synchronized (this) {
                    UsbDevice usbDevice = (UsbDevice)intent.getParcelableExtra(UsbManager.EXTRA_DEVICE);
                    if (intent.getBooleanExtra(UsbManager.EXTRA_PERMISSION_GRANTED, false)) {
                        //user choose YES for your previously popup window asking for grant perssion for this usb device
                        if(null != usbDevice){
                            afterGetUsbPermission();
                        }
                    }
                    else {
                        //user choose NO for your previously popup window asking for grant perssion for this usb device
//                        txtView.setText("未获取到读卡器操作权限");
                        Log.d("权限","未获取到读卡器操作权限");
                    }
                }
            }
        }
    };

    public void afterGetUsbPermission(){
        // 打开设备，获取 UsbDeviceConnection 对象，连接设备，用于后面的通讯
        Log.d("SSCardReaderDemo", "afterGetUsbPermission start");
        mDeviceConnection = m_manager.openDevice(m_UsbDevice);
        if (mDeviceConnection == null) {
            m_IsReaderOpen = false;
            return;
        }
        if (mDeviceConnection.claimInterface(m_Interface, true)) {
            //用UsbDeviceConnection 与 UsbInterface 进行端点设置和通讯
            if (m_Interface.getEndpoint(1) != null) {
                usbEpOut = m_Interface.getEndpoint(1);
                usbEpOut.getAddress();
            }
            if (m_Interface.getEndpoint(0) != null) {
                usbEpIn = m_Interface.getEndpoint(0);
                usbEpIn.getAddress();
            }
            m_IsReaderOpen = true;
        } else {
            mDeviceConnection.close();
            m_IsReaderOpen = false;
        }
    }

    class ReadThread extends Thread {
        @Override
        public void run() {

            while (isRead) {
                isStop = true;
                RetInfo iRetInfo = new RetInfo();
                Log.d("ReadThread", "=====>toreadcard");
                iRetInfo = SSCardReader.iReadCardBas(3, iRetInfo);
                if (iRetInfo.GetRet() == 0) {
                    String strOut = "返回码="+String.valueOf(iRetInfo.GetRet()) + "，输出信息为:"+ iRetInfo.GetInfo();
                    Log.d("iReadCardBas", strOut);
                    try {
                        Thread.sleep(10000);
                        Log.d("ReadThread", "=====>sleep");
                    } catch (InterruptedException e) {
                        // TODO Auto-generated catch block
                        e.printStackTrace();
                    }
                }

            }
        }
    }

    Handler handler=new Handler();
    Runnable runnable;
    public void start(){
        runnable = new Runnable(){
            @Override
            public void run(){
                initView();
                //延迟2秒执行
                handler.postDelayed(this, 1000);
            }
        };
        handler.post(runnable);
    }

    @Override
    public void onDestroy() {
        super.onDestroy();

        handler.removeCallbacksAndMessages(null);
    }

    private void initView() {
        int iRet = 0;

        if (!m_IsReaderOpen) {
//            txtView.setText("读卡器连接失败，请重新连接读卡器并重启该应用！");
            Log.d("SSCardReaderDemo","读卡器连接失败，请重新连接读卡器并重启该应用！");
            return;
        }
        Log.d("SSCardReaderDemo", "iInitReader start");
        iRet = SSCardReader.iInitReader(mDeviceConnection, usbEpIn, usbEpOut);
        Log.d("SSCardReaderDemo", "iInitReader finish");
        if (iRet != 0) {
//            txtView.setText("读卡器初始化操作失败");
            Log.d("SSCardReaderDemo","读卡器初始化操作失败");
            return;
        }
        RetInfo iRetInfo = new RetInfo();
        String strOut = "";
        Log.d("iReadCardBas", "iReadCardBas start");
        iRetInfo = SSCardReader.iReadCardBas(1, iRetInfo);
        if (iRetInfo.GetRet() == 0) {
            //社保卡基本信息：发卡地区行政区划代码（卡识别码前6位）、社会保障号码、卡号、卡识别码、姓名、卡复位信息（仅取历史字节）、规范版本、发卡日期、卡有效期、终端机编号、终端设备号。
            strOut = "读社保卡基本信息成功！输出信息为：" + iRetInfo.GetInfo();
            String[] list_sbk=strOut.split("\\|");
            Log.d("SSCardReaderDemo","长度"+list_sbk.length);
            if (list_sbk.length>5){
                StaticBean.idcard=list_sbk[1];
                StaticBean.sscard_number=list_sbk[2];
                StaticBean.name=list_sbk[4];
                //跳转到对应的界面
                IntentFragment();
                getActivity().finish();
            }
        }else {
            strOut = "读社保卡基本信息失败！错误信息为：" + iRetInfo.GetInfo();
        }

        Log.d("SSCardReaderDemo",""+strOut);
//        txtView.setText(strOut);
        Log.d("iReadCardBas", strOut);
    }

    public void IntentFragment(){
        if (optionId==Defs.CHANGE_SSCARD_PASSWORD){
            Bundle bundle = new Bundle();
            bundle.putInt(Defs.OPTION_ID, Defs.CHANGE_SSCARD_PASSWORD);
            bundle.putString(Defs.OPTION_NAME, "修改社保卡密码");
            IntentUtils.startActivity(getActivity(),"社保卡管理>修改社保卡密码",
                    new ChangepasswordFragment(), bundle);

        }
    }

}
