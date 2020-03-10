package com.tecsun.robot.fragment.sscardmanager;

import android.app.Activity;
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
import android.text.TextUtils;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.tecsun.jni.RetInfo;
import com.tecsun.jni.SSCardReader;
import com.tecsun.robot.common.Defs;
import com.tecsun.robot.fragment.BaseFragment;
import com.tecsun.robot.nanninig.R;

import java.util.HashMap;
import java.util.Iterator;
import java.util.List;

public class ChangepasswordFragment extends BaseFragment {
    View mView;


    EditText et_password;//密码
    Button btn_ok;
    int success_type=0;//是否成功步骤
    //    String oldPassword="123456";
    String oldPassword_type1="";//旧密码
    String oldPassword_type2="";

    private ImageView ivChangePwdLine02, ivChangePwdLine03, ivChangePwdLine04;
    private ImageView ivChangePwdStep02, ivChangePwdStep03;
    private TextView tvChangePwd02, tvChangePwd03;

    /****读卡相关****/
    public static final String ACTION_USB_PERMISSION = "com.android.example.USB_PERMISSION";
    private UsbManager m_manager;			// USB管理器
    private UsbDevice m_UsbDevice;			// 找到的USB设备
    private UsbInterface m_Interface;		// USB接口
    private boolean m_IsReaderOpen = false;	// 读卡器是否获取打开权限
    public UsbDeviceConnection mDeviceConnection;
    public UsbEndpoint usbEpOut;			//代表一个接口的某个节点的类:写数据节点
    public UsbEndpoint usbEpIn;				//代表一个接口的某个节点的类:读数据节点

    boolean isRead = false;
    boolean isStop = false;
    ReadThread mReadThread;


    /**
     * int型标题内容
     */
    protected Integer intTitleObj;
    /**
     * 标题类型，0：int型；1：char型
     */
    protected int titleType;


    /**
     * char型标题内容
     */
    protected CharSequence charTitleObj;

    private TextView tv_title;


    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        mView = inflater.inflate(R.layout.activity_change_pass, container, false);

        initpass(mView);
        /**初始化读卡相关*/
        try {
            initUsbData();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        getIntentValues();
        return mView;
    }
    /**
     * 获取Intent传值tv_title
     */
    private void getIntentValues() {
        titleType = getActivity().getIntent().getIntExtra(Defs.KEY_TITLE_TYPE, -1);
        if (titleType == 0) {
            intTitleObj = getActivity().getIntent().getIntExtra(Defs.KEY_TITLE, 0);
        } else if (titleType == 1) {
            charTitleObj =  getActivity().getIntent().getCharSequenceExtra(Defs.KEY_TITLE);
        }
        tv_title.setText(charTitleObj+"");

    }

    private void initpass(View view){
        tv_title = (TextView) mView.findViewById(R.id.tv_title_2);
        et_password = (EditText) view.findViewById(R.id.et_password);
        btn_ok = (Button) view.findViewById(R.id.btn_ok);
        initFragmentView(view);



        btn_ok.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String password=et_password.getText().toString().trim();
                switch (success_type){
                    case 0:
                        if (password.length()<6){
                            Toast.makeText(getActivity(),getString(R.string.password_six),Toast.LENGTH_SHORT).show();
                            return;
                        }
                        //验证原始密码
                        String  verifypass=verifySSCard(password);
                        if ("成功".equals(verifypass)){
                            setChangePwdStep02();
                            oldPassword_type1=password;//保存成功验证的密码
                            et_password.setText("");//清空
                            success_type=1;//通过原始密码验证
                        }
                        else{
                            Toast.makeText(getActivity(),verifypass,Toast.LENGTH_SHORT).show();
                        }

                        break;
                    case 1:
                        if (password.length()<6){
                            Toast.makeText(getActivity(),getString(R.string.password_six),Toast.LENGTH_SHORT).show();
                            return;
                        }
                        oldPassword_type2=password;
                        setChangePwdStep03();
                        oldPassword_type2=password;//保存成功验证的密码
                        et_password.setText("");//清空
                        success_type=2;//通过原始密码验证
                        break;

                    case 2:
                        if (password.length()<6){
                            Toast.makeText(getActivity(),getString(R.string.password_six),Toast.LENGTH_SHORT).show();
                            return;
                        }
                        if (!oldPassword_type2.equals(password)){
                            Toast.makeText(getActivity(),getString(R.string.password_two),Toast.LENGTH_SHORT).show();
                            setChangePwdStep03Failure();
                            et_password.setText("");//清空
                            success_type=1;//通过原始密码验证
                            return;
                        }
                        //修改密码成功
                        String str_changepass=ChangeSSCardPass(oldPassword_type1,password);
                        if ("成功".equals(str_changepass)){
                            et_password.setText("");//清空
                            success_type=3;//通过原始密码验证
                            setChangePwdStep04();//输入密码成功
                            //延迟1秒让他们看到界面
                            new Handler().postDelayed(new Runnable() {
                                @Override
                                public void run() {
                                    Toast.makeText(getActivity(),getString(R.string.password_change_success),Toast.LENGTH_SHORT).show();
                                    getActivity().finish();
                                }
                            },1000);

                        }
                        else{
                            Toast.makeText(getActivity(),str_changepass,Toast.LENGTH_SHORT).show();
                        }

                        break;

                }



            }
        });
    }


    public void initFragmentView(View mView) {
        ivChangePwdLine02 = (ImageView) mView.findViewById(R.id.iv_change_pwd_line_02);
        ivChangePwdLine03 = (ImageView) mView.findViewById(R.id.iv_change_pwd_line_03);
        ivChangePwdLine04 = (ImageView) mView.findViewById(R.id.iv_change_pwd_line_04);

        ivChangePwdStep02 = (ImageView) mView.findViewById(R.id.iv_change_pwd_step_02);
        ivChangePwdStep03 = (ImageView) mView.findViewById(R.id.iv_change_pwd_step_03);

        tvChangePwd02 = (TextView) mView.findViewById(R.id.tv_change_pwd_02);
        tvChangePwd03 = (TextView) mView.findViewById(R.id.tv_change_pwd_03);
    }

    /**
     * 设置修改密码第二步，输入新密码
     */
    private void setChangePwdStep02() {
        ivChangePwdLine02.setBackgroundResource(R.drawable.ic_existed_arrow);
        ivChangePwdStep02.setBackgroundResource(R.drawable.ic_new_pwd_press);
        tvChangePwd02.setTextColor(getResources().getColor(R.color.c_blue_1));

    }

    /**
     * 输入确认密码
     */
    private void setChangePwdStep03() {

        ivChangePwdLine03.setBackgroundResource(R.drawable.ic_existed_arrow);
        ivChangePwdStep03.setBackgroundResource(R.drawable.ic_new_pwd_press);
        tvChangePwd03.setTextColor(getResources().getColor(R.color.c_blue_1));

    }

    /**
     * 输入确认密码失败
     */
    private void setChangePwdStep03Failure() {

        ivChangePwdLine03.setBackgroundResource(R.drawable.ic_not_exist_arrow);
        ivChangePwdStep03.setBackgroundResource(R.mipmap.ic_new_pwd_normal);
        tvChangePwd03.setTextColor(getResources().getColor(R.color.c_black_2));

    }

    /**
     * 密码输入成功
     */
    private void setChangePwdStep04() {

        ivChangePwdLine04.setBackgroundResource(R.drawable.ic_existed_line);

    }



    /*******************读卡相关*************************/

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
//            name.setText("未找到读卡器，请连接读卡器并重启该应用！");
            Toast.makeText(getActivity(),"未找到读卡器，请连接读卡器并重启该应用！",Toast.LENGTH_SHORT).show();
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
//                        name.setText("未获取到读卡器操作权限");
                        Toast.makeText(getActivity(),"未获取到读卡器操作权限",Toast.LENGTH_SHORT).show();
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

    /*********读取社保卡密码验证是否正确**********/
    public String verifySSCard(String password){
        RetInfo iRetInfo = new RetInfo();
        int iRet = 0;

        if (!m_IsReaderOpen) {
//            name.setText("读卡器连接失败，请重新连接读卡器并重启该应用！");
            return "读卡器连接失败，请重新连接读卡器并重启该应用！";
        }
        iRet = SSCardReader.iInitReader(mDeviceConnection, usbEpIn, usbEpOut);
        Log.d("SSCardReaderDemo", "iInitReader finish");
        if (iRet != 0) {
//            name.setText("读卡器初始化操作失败");
            return "读卡器初始化操作失败";
        }
        Log.d("iReadCardBas", "iVerifyPINK start");
        iRetInfo = SSCardReader.iVerifyPINK(1, password, iRetInfo);
        String strOut = "返回码="+String.valueOf(iRetInfo.GetRet()) + "，输出信息为:"+ iRetInfo.GetInfo();
//        name.setText(strOut);
        Log.d("iVerifyPINK", strOut);
        if (iRetInfo.GetRet()==0){
            return "成功";
        }
        else{
            return iRetInfo.GetInfo();
        }
    }

    /********修改社保卡密码********/
    public String ChangeSSCardPass(String oldpassword,String newpassword){

        RetInfo iRetInfo = new RetInfo();
        int iRet = 0;

        if (!m_IsReaderOpen) {
//            name.setText("读卡器连接失败，请重新连接读卡器并重启该应用！");
            return "读卡器连接失败，请重新连接读卡器并重启该应用！";
        }
        iRet = SSCardReader.iInitReader(mDeviceConnection, usbEpIn, usbEpOut);
        Log.d("SSCardReaderDemo", "iInitReader finish");
        if (iRet != 0) {
//            name.setText("读卡器初始化操作失败");
            return "读卡器初始化操作失败";
        }
        iRetInfo = SSCardReader.iChangePINK(1, oldpassword, newpassword, iRetInfo);
        if (iRetInfo.GetRet() != 0) {
//            name.setText("修改密码失败！错误信息为：" + iRetInfo.GetInfo());
            return iRetInfo.GetInfo();
        }else {
            return "成功";
//            name.setText("修改密码成功");
        }
    }



}
