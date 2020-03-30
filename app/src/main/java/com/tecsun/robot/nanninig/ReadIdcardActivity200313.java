//package com.tecsun.robot.nanninig;
//
//import android.app.PendingIntent;
//import android.content.BroadcastReceiver;
//import android.content.Context;
//import android.content.Intent;
//import android.content.IntentFilter;
//import android.hardware.usb.UsbDevice;
//import android.hardware.usb.UsbDeviceConnection;
//import android.hardware.usb.UsbEndpoint;
//import android.hardware.usb.UsbInterface;
//import android.hardware.usb.UsbManager;
//import android.os.Bundle;
//import android.os.CountDownTimer;
//import android.os.Handler;
//import android.util.Log;
//import android.view.View;
//import android.widget.Button;
//import android.widget.ImageView;
//import android.widget.TextView;
//
//import androidx.annotation.Nullable;
//
//import com.bumptech.glide.Glide;
//import com.bumptech.glide.load.engine.DiskCacheStrategy;
//import com.tecsun.jni.RetInfo;
//import com.tecsun.jni.SSCardReader;
//import com.tecsun.robot.MyBaseActivity;
//import com.tecsun.robot.bean.IDCardBean;
//import com.tecsun.robot.bean.evenbus.IdCardBean;
//import com.tecsun.robot.utils.IdCardUtils;
//import com.tecsun.robot.utils.StaticBean;
//
//import org.greenrobot.eventbus.EventBus;
//
//import java.util.HashMap;
//import java.util.Iterator;
//
///**
// * 读取身份证界面
// * */
//public class ReadIdcardActivity200313 extends MyBaseActivity {
//    public static final String ACTION_USB_PERMISSION = "com.android.example.USB_PERMISSION";
//    private UsbManager m_manager;			// USB管理器
//    private UsbDevice m_UsbDevice;			// 找到的USB设备
//    private UsbInterface m_Interface;		// USB接口
//    private boolean m_IsReaderOpen = false;	// 读卡器是否获取打开权限
//    public UsbDeviceConnection mDeviceConnection;
//    public UsbEndpoint usbEpOut;			//代表一个接口的某个节点的类:写数据节点
//    public UsbEndpoint usbEpIn;				//代表一个接口的某个节点的类:读数据节点
////    private TextView txtView;
//    boolean isRead = false;
//    boolean isStop = false;
//
//    Button btn_close;
//    ImageView img_sfz;
//    TextView tv_time;
//
//    boolean Flag_Toast_Login = true;//不重复弹出toast;
//
//    @Override
//    protected void onCreate(@Nullable Bundle savedInstanceState) {
//        super.onCreate(savedInstanceState);
//        setContentView(R.layout.activity_read_idcard);
//        tv_time = (TextView) findViewById(R.id.tv_time);
//        StartTime();
//        img_sfz = (ImageView) findViewById(R.id.img_sfz);
//        btn_close = (Button) findViewById(R.id.btn_close);
//        Glide.with(ReadIdcardActivity200313.this).load(R.mipmap.logo_dqsfz)
//                .diskCacheStrategy(DiskCacheStrategy.ALL).into(img_sfz);
//
//        try {
//            initUsbData();
//        } catch (InterruptedException e) {
//            e.printStackTrace();
//        }
//        btn_close.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
////                finish();
//                 finish();
//
//            }
//        });
//
//        //延迟1秒让他们看到界面
//        new Handler().postDelayed(new Runnable() {
//            @Override
//            public void run() {
//                start();
//            }
//        },1000);
//
//    }
//
//    //初始化USB接口
//    private void initUsbData()throws InterruptedException {
//        boolean IsExistReader = false;
//        // 获取USB设备
//        m_manager = (UsbManager) getSystemService(Context.USB_SERVICE);
//        //获取到设备列表
//        HashMap<String, UsbDevice> deviceList = m_manager.getDeviceList();
//        Iterator<UsbDevice> deviceIterator = deviceList.values().iterator();
//        while (deviceIterator.hasNext()) {
//            Log.d("test mUsbDevice", "find usbdevice");
//            // 读卡器PID：FFFF
//            m_UsbDevice = deviceIterator.next();
//            if ((0xffff == m_UsbDevice.getVendorId()) && (0xffff == m_UsbDevice.getProductId())) {//找到指定设备
//                IsExistReader = true;
//                break;
//            }
//        }
//        if (!IsExistReader) {
////            txtView.setText("未找到读卡器，请连接读卡器并重启该应用！");
//            Log.d("Tag","未找到读卡器，请连接读卡器并重启该应用！");
//            return;
//        }
//        //获取设备接口
//        for (int i = 0; i < m_UsbDevice.getInterfaceCount(); ) {
//            // 一般来说一个设备都是一个接口，你可以通过getInterfaceCount()查看接口的个数
//            // 这个接口上有两个端点，分别对应OUT 和 IN
//            UsbInterface usbInterface = m_UsbDevice.getInterface(i);
//            m_Interface = usbInterface;
//            break;
//        }
//
//        if (m_Interface != null) {	// 判断是否有权限
//            if (m_manager.hasPermission(m_UsbDevice)) {
//                afterGetUsbPermission();
//            } else {
//                Log.d("SSCardReaderDemo", "request to get permission");
//                IntentFilter filter = new IntentFilter(ACTION_USB_PERMISSION);
//                registerReceiver(mUsbPermissionActionReceiver, filter);
//
//                PendingIntent mPermissionIntent = PendingIntent.getBroadcast(this, 0, new Intent(ACTION_USB_PERMISSION), 0);
//                m_manager.requestPermission(m_UsbDevice, mPermissionIntent);
//            }
//        }
//
//    }
//
//
//    private final BroadcastReceiver mUsbPermissionActionReceiver = new BroadcastReceiver() {
//        public void onReceive(Context context, Intent intent) {
//            String action = intent.getAction();
//            if (ACTION_USB_PERMISSION.equals(action)) {
//                synchronized (this) {
//                    UsbDevice usbDevice = (UsbDevice)intent.getParcelableExtra(UsbManager.EXTRA_DEVICE);
//                    if (intent.getBooleanExtra(UsbManager.EXTRA_PERMISSION_GRANTED, false)) {
//                        //user choose YES for your previously popup window asking for grant perssion for this usb device
//                        if(null != usbDevice){
//                            afterGetUsbPermission();
//                        }
//                    }
//                    else {
//                        //user choose NO for your previously popup window asking for grant perssion for this usb device
////                        txtView.setText("未获取到读卡器操作权限");
//                        Log.d("权限","未获取到读卡器操作权限");
//                    }
//                }
//            }
//        }
//    };
//
//    public void afterGetUsbPermission(){
//        // 打开设备，获取 UsbDeviceConnection 对象，连接设备，用于后面的通讯
//        Log.d("SSCardReaderDemo", "afterGetUsbPermission start");
//        mDeviceConnection = m_manager.openDevice(m_UsbDevice);
//        if (mDeviceConnection == null) {
//            m_IsReaderOpen = false;
//            return;
//        }
//        if (mDeviceConnection.claimInterface(m_Interface, true)) {
//            //用UsbDeviceConnection 与 UsbInterface 进行端点设置和通讯
//            if (m_Interface.getEndpoint(1) != null) {
//                usbEpOut = m_Interface.getEndpoint(1);
//                usbEpOut.getAddress();
//            }
//            if (m_Interface.getEndpoint(0) != null) {
//                usbEpIn = m_Interface.getEndpoint(0);
//                usbEpIn.getAddress();
//            }
//            m_IsReaderOpen = true;
//        } else {
//            mDeviceConnection.close();
//            m_IsReaderOpen = false;
//        }
//    }
//
//    class ReadThread extends Thread {
//        @Override
//        public void run() {
//
//            while (isRead) {
//                isStop = true;
//                RetInfo iRetInfo = new RetInfo();
//                Log.d("ReadThread", "=====>toreadcard");
//                iRetInfo = SSCardReader.iReadCardBas(3, iRetInfo);
//                if (iRetInfo.GetRet() == 0) {
//                    String strOut = "返回码="+String.valueOf(iRetInfo.GetRet()) + "，输出信息为:"+ iRetInfo.GetInfo();
//                    Log.d("iReadCardBas", strOut);
//                    try {
//                        Thread.sleep(10000);
//                        Log.d("ReadThread", "=====>sleep");
//                    } catch (InterruptedException e) {
//                        // TODO Auto-generated catch block
//                        e.printStackTrace();
//                    }
//                }
//
//            }
//        }
//    }
//   int i=0;
//
//
//    public void start(){
//                initView();
//    }
//
//    @Override
//    protected void onDestroy() {
//        super.onDestroy();
//        StopTime();
//        mHandler.removeCallbacks(mBackgroundRunnable);
//
//    }
//
//    Runnable mBackgroundRunnable=new Runnable() {
//        @Override
//        public void run() {
//            RetInfo iRetInfo = new RetInfo();
//            int iRet = 0;
//
//            if (!m_IsReaderOpen) {
////            txtView.setText("读卡器连接失败，请重新连接读卡器并重启该应用！");
//                Log.d("权限","读卡器连接失败，请重新连接读卡器并重启该应用！");
//                return;
//            }
//            iRet = SSCardReader.iInitReader(mDeviceConnection, usbEpIn, usbEpOut);
//            Log.d("SSCardReaderDemo", "iInitReader finish");
//            if (iRet != 0) {
//                Log.d("权限","读卡器初始化操作失败");
////            txtView.setText("读卡器初始化操作失败");
//                return;
//            }
//            Log.d("iReadIDOrgData", "iReadIDOrgData start");
//            Log.d ("iReadIDOrgData",iRetInfo.strInfo+"--");
////        if (StringUtil.isNullOrEmpty(iRetInfo.strInfo)){
////            ToastUtils.INSTANCE.showTopShortToast(getApplicationContext(),"读取身份证失败");
////            finish();
////            return;
////        }
//            iRetInfo = SSCardReader.iReadIDOrgData(iRetInfo);
//            String strOut = "返回码="+String.valueOf(iRetInfo.GetRet()) + "，输出信息为:"+ iRetInfo.GetInfo();
////        txtView.setText(strOut);
//            Log.d("iReadIDOrgData",strOut);
//            IdCardUtils idCardUtils = new IdCardUtils();
//            IDCardBean idCardBean = idCardUtils.getCard(iRetInfo.GetInfo(),200, ReadIdcardActivity200313.this);
//            Log.d("iReadIDOrgData", strOut);
//            if (iRetInfo.GetRet() != 0) {
//                Log.d("iReadIDOrgData","读身份证失败");
////            txtView.setText("读身份证失败");
//                initView();
//            }else {
//                Log.d("iReadIDOrgData","读身份证成功");
////            txtView.setText("读身份证成功");
//                Log.d("iReadIDOrgData","身份证号:"+idCardBean.getIdCardNo()
//                        +"，姓名:"+idCardBean.getName().trim()+"，民族:"+idCardBean.getNation()
//                        +"，性别:"+idCardBean.getSex());
//                Log.d("图片",idCardBean.getPhoto()+"");
//                StaticBean.name=idCardBean.getName().trim();
//                StaticBean.idcard=idCardBean.getIdCardNo();
////            StaticBean.name="徐树仁";
////            StaticBean.idcard="360734199403150017";
//                if (Flag_Toast_Login) {
//                    Flag_Toast_Login = false;
//                    finish();
//                    EventBus.getDefault().post(new IdCardBean(1));
//                }
////            image.setImageBitmap(idCardBean.getPhoto());
//                //读取成功退出当前界面
//
//            }
//        }
//    };
//
//    Handler mHandler = new Handler();
//    private void initView() {
//        mHandler.post(mBackgroundRunnable);//mBackgroundRunnable为线程对象
//    }
//
//
//
//    /**
//     * 是否启动定时器倒计时
//     * */
//    public void StartTime(){
//
//        StopTime();
//        time=60;
//        timer.start();
//    }
//
//    public void StopTime(){
//        if (timer!=null){
//            timer.cancel();
//        }
//
//    }
//
//    public static int time = 60;
//    CountDownTimer timer = new CountDownTimer(time*1000, 1000) {
//        @Override
//        public void onTick(long millisUntilFinished) {
//            Log.d("idcard定时器",millisUntilFinished / 1000 + "");
//            tv_time.setText("剩余"+millisUntilFinished / 1000+"秒");
//        }
//
//        @Override
//        public void onFinish() {
//            finish();
//            EventBus.getDefault().post(new IdCardBean(3));
//        }
//    };
//}
