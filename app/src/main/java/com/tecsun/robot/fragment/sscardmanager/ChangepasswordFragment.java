package com.tecsun.robot.fragment.sscardmanager;

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
import android.os.HandlerThread;
import android.os.Message;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.tecsun.jc.base.utils.ToastUtils;
import com.tecsun.jni.RetInfo;
import com.tecsun.jni.SSCardReader;
import com.tecsun.robot.MainApp;
import com.tecsun.robot.common.Defs;
import com.tecsun.robot.fragment.BaseFragment;
import com.tecsun.robot.nanninig.R;

import org.w3c.dom.Text;

import java.util.HashMap;
import java.util.Iterator;
import java.util.List;

public class ChangepasswordFragment extends BaseFragment implements View.OnClickListener {
    View mView;
    TextView tv_ysmm,tv_xsmm,tv_qrmm;
    TextView tv_pass1,tv_pass2,tv_pass3;
    Button btn_ok;//确定修改
    Button btn_1,btn_2,btn_3,btn_4,btn_5,btn_6,btn_7,btn_8,btn_9,btn_0,btn_delete;
    String str_ysmm="";//原始密码
    String str_xsmm="";//新设密码
    String str_qrmm="";//确认密码
    int step=1;//密码成功步骤1原始密码2新设密码3确认密码
    LinearLayout lin_right;//密码键盘布局
    RelativeLayout lin_ok;//确定布局
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

    String m_strPINText="";
    int numberType;//10-15

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

    //创建线程
    private Handler hHandler;
    private boolean mRunning = false;
    HandlerThread hThread;
    boolean isXJL=true;//是否小精灵
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        mView = inflater.inflate(R.layout.activity_change_pass, container, false);
        initview();//初始化按钮
        /**初始化读卡相关*/
       int return_code =  MainApp.cepp_dri.EPP_OpenDevice("/dev/ttyUSB1",9600);
       if (return_code!=0){
           isXJL=false;
           lin_right.setVisibility(View.VISIBLE);
           lin_ok.setVisibility(View.VISIBLE);
//           ToastUtils.INSTANCE.showGravityShortToast(getActivity(),"打开设备失败");
       }else{
           lin_right.setVisibility(View.GONE);
           lin_ok.setVisibility(View.GONE);
       }

        byte bResult = 0x01;
        int nflag = MainApp.cepp_dri.EPP_UseEppPlainTextMode(bResult);
        if(nflag < 0){
//            ToastUtils.INSTANCE.showTopShortToast(getActivity(),"打开键盘失败");
        }
        else{
//            ToastUtils.INSTANCE.showTopShortToast(getActivity(),"打开键盘明文成功");
        }
        mRunning = true;
        hThread = new HandlerThread("MyHandlerThread");
        hThread.start();//创建一个HandlerThread并启动它
        hHandler = new Handler(hThread.getLooper());//使用HandlerThread的looper对象创建Handler，如果使用默认的构造方法，很有可能阻塞UI线程
        hHandler.post(mBackgroundRunnable);//将线程post到Handler中

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

    /**
     * 小精灵密码键盘键盘获取
     * */
    public void getNumbertype(int numberType){
        Log.d("点击","numberType:"+numberType);
        switch (numberType){
            case 10://删除
                getPassword(11);
                break;
            case 11:

                break;
            case 12:
                getPassword(11);
                break;
            case 13:

                break;
            case 14:

                break;
            case 15://确认
                isok();
                break;
        }
    }
    /**清空密码相关*/
    private void clear(){
        tv_xsmm.setText("");
        tv_qrmm.setText("");
        str_ysmm="";
        str_xsmm="";
        str_qrmm="";
    }
    /**
     * 确认ok密码
     * */
    public void isok(){
        str_ysmm = tv_ysmm.getText().toString();
        str_xsmm = tv_xsmm.getText().toString();
        str_qrmm = tv_qrmm.getText().toString();
        switch (step){
            case 1://验证原始密码

                if (str_ysmm.length()<6){
                    ToastUtils.INSTANCE.showGravityShortToast(getActivity(),getString(R.string.password_six));
                }
                else{
                    //验证原始密码
                    String  verifypass=verifySSCard(str_ysmm);
                    if ("成功".equals(verifypass)){
                        step=2;
                        tv_pass2.setTextColor(getResources().getColor(R.color.c_blue_2));
                    }
                    else{
                        ToastUtils.INSTANCE.showGravityShortToast(getActivity(),verifypass);
                    }
                }
                break;
            case 2://输入新密码

                if (str_xsmm.length()<6){
                    ToastUtils.INSTANCE.showGravityShortToast(getActivity(),getString(R.string.password_six));
                }
                else{
                    tv_pass3.setTextColor(getResources().getColor(R.color.c_blue_2));
                    step=3;
                }
                break;

            case 3://验证新密码是否设置成功

                if (str_qrmm.length()<6){
                    ToastUtils.INSTANCE.showGravityShortToast(getActivity(),getString(R.string.password_six));
                    break;
                }
                if (!str_xsmm.equals(str_qrmm)){//密码不一致
                    ToastUtils.INSTANCE.showGravityShortToast(getActivity(),getString(R.string.password_two));
                    tv_pass2.setTextColor(getResources().getColor(R.color.c_blue_2));
                    tv_pass3.setTextColor(getResources().getColor(R.color.c_black_1));
                    clear();
                    step=2;
                    break;
                }
                //修改密码成功
                String str_changepass=ChangeSSCardPass(str_ysmm,str_xsmm);
                if ("成功".equals(str_changepass)){

                    ToastUtils.INSTANCE.showGravityShortToast(getActivity(),getString(R.string.password_change_success));
                    getActivity().finish();
                }
                else{
                    tv_xsmm.setTextColor(getResources().getColor(R.color.c_blue_2));
                    tv_qrmm.setTextColor(getResources().getColor(R.color.c_black_1));
                    clear();
                    ToastUtils.INSTANCE.showGravityShortToast(getActivity(),str_changepass);
                }
                break;
        }
    }
    //更新密码ui
    public void getPassword(int number){
        Log.d("点击","当前传过来的number:"+number);
        str_ysmm = tv_ysmm.getText().toString();
        str_xsmm = tv_xsmm.getText().toString();
        str_qrmm = tv_qrmm.getText().toString();
        switch (step){
            case 1://步骤一

                if (number==11){//删除
                    if (str_ysmm.length()>0){//必须大于0
                        str_ysmm= str_ysmm.substring(0,str_ysmm.length()-1);
                        tv_ysmm.setText(str_ysmm);

                        Log.d("点击","删除："+tv_ysmm.getText().toString());
                    }
                }
                else if (str_ysmm.length()<6){//添加保留6位
                     str_ysmm=str_ysmm+number;
                     tv_ysmm.setText(str_ysmm);

                    Log.d("点击","更新后的数字："+tv_ysmm.getText().toString());
                }
                break;

            case 2://步骤二
                if (number==11){//删除
                    if (str_xsmm.length()>0){//必须大于0
                        str_xsmm = str_xsmm.substring(0,str_xsmm.length()-1);
                        tv_xsmm.setText(str_xsmm);
                    }
                }
                else if (str_xsmm.length()<6){//添加保留6位
                    str_xsmm= str_xsmm+number;
                    tv_xsmm.setText(str_xsmm);
                }
                break;
            case 3://步骤三
                if (number==11){//删除
                    if (str_qrmm.length()>0){//必须大于0
                        str_qrmm= str_qrmm.substring(0,str_qrmm.length()-1);
                        tv_qrmm.setText(str_qrmm);
                    }
                }
                else if (str_qrmm.length()<6){//添加保留6位
                    str_qrmm=str_qrmm+number;
                    tv_qrmm.setText(str_qrmm);
                }
                break;
        }
    }
    @Override
    public void onClick(View view) {
        switch (view.getId()){
            case R.id.btn_0:
                Log.d("点击","0000");
                getPassword(0);
                break;
            case R.id.btn_1:
                getPassword(1);
                break;
            case R.id.btn_2:
                getPassword(2);
                break;
            case R.id.btn_3:
                getPassword(3);
                break;
            case R.id.btn_4:
                getPassword(4);
                break;
            case R.id.btn_5:
                getPassword(5);
                break;
            case R.id.btn_6:
                getPassword(6);
                break;
            case R.id.btn_7:
                getPassword(7);
                break;
            case R.id.btn_8:
                getPassword(8);
                break;
            case R.id.btn_9:
                getPassword(9);
                break;
            case R.id.btn_delete:
                getPassword(11);
                break;
            case R.id.btn_ok:
                Log.d("点击","0000kkk");
                isok();
                break;
        }
    }

    private void initview(){
        tv_pass1 = (TextView) mView.findViewById(R.id.tv_pass1) ;
        tv_pass2 = (TextView) mView.findViewById(R.id.tv_pass2) ;
        tv_pass3 = (TextView) mView.findViewById(R.id.tv_pass3) ;
        lin_ok = (RelativeLayout) mView.findViewById(R.id.lin_ok);
        tv_title = (TextView) mView.findViewById(R.id.tv_title_2);
        lin_right = (LinearLayout) mView.findViewById(R.id.lin_right);
        tv_ysmm = (TextView) mView.findViewById(R.id.tv_ysmm);
        tv_xsmm = (TextView) mView.findViewById(R.id.tv_xsmm);
        tv_qrmm = (TextView) mView.findViewById(R.id.tv_qrmm);
        btn_ok = (Button) mView.findViewById(R.id.btn_ok);
        btn_1 = (Button) mView.findViewById(R.id.btn_1);
        btn_2 = (Button) mView.findViewById(R.id.btn_2);
        btn_3 = (Button) mView.findViewById(R.id.btn_3);
        btn_4 = (Button) mView.findViewById(R.id.btn_4);
        btn_5 = (Button) mView.findViewById(R.id.btn_5);
        btn_6 = (Button) mView.findViewById(R.id.btn_6);
        btn_7 = (Button) mView.findViewById(R.id.btn_7);
        btn_8 = (Button) mView.findViewById(R.id.btn_8);
        btn_9 = (Button) mView.findViewById(R.id.btn_9);
        btn_0 = (Button) mView.findViewById(R.id.btn_0);
        btn_delete = (Button) mView.findViewById(R.id.btn_delete);
        btn_ok.setOnClickListener(this);
        btn_1.setOnClickListener(this);
        btn_2.setOnClickListener(this);
        btn_3.setOnClickListener(this);
        btn_4.setOnClickListener(this);
        btn_5.setOnClickListener(this);
        btn_6.setOnClickListener(this);
        btn_7.setOnClickListener(this);
        btn_8.setOnClickListener(this);
        btn_9.setOnClickListener(this);
        btn_0.setOnClickListener(this);
        btn_delete.setOnClickListener(this);
        tv_pass1.setTextColor(getResources().getColor(R.color.c_blue_2));
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
    public Handler mHandler=new Handler()
    {
        public void handleMessage(Message msg)
        {
            switch(msg.what)
            {
                case 4:
                    Log.d("密码键盘",m_strPINText+"");

                    if ("30".equals(m_strPINText)){

                        getPassword(0);
                    }
                    else if("31".equals(m_strPINText)){
                        getPassword(1);
                    }
                    else if("32".equals(m_strPINText)){

                        getPassword(2);
                    }
                    else if("33".equals(m_strPINText)){
                        getPassword(3);
                    }
                    else if("34".equals(m_strPINText)){
                        getPassword(4);
                    }
                    else if("35".equals(m_strPINText)){
                        getPassword(5);
                    }
                    else if("36".equals(m_strPINText)){
                        getPassword(6);
                    }
                    else if("37".equals(m_strPINText)){
                        getPassword(7);
                    }
                    else if("38".equals(m_strPINText)){
                        getPassword(8);
                    }
                    else if("39".equals(m_strPINText)){
                        getPassword(9);
                    }
                    else if ("08".equals(m_strPINText)){
                        numberType=10;//退格
                    }
                    else if ("2E".equals(m_strPINText)){
                        numberType=11;//点
                    }
                    else if ("1B".equals(m_strPINText)){
                        numberType=12;//返回
                    }
                    else if ("21".equals(m_strPINText)){
                        numberType=13;//上翻
                    }
                    else if ("22".equals(m_strPINText)){
                        numberType=14;//下翻
                    }
                    else if ("0D".equals(m_strPINText)){
                        numberType=15;//确认
                    }
                    else{

                    }
                    /****执行删除确认操作****/
                    if (numberType==10||numberType==12||numberType==15){
                        getNumbertype(numberType);
                    }
                    /**
                     * 清空操作缓存
                     * */
                    m_strPINText="";
                    numberType=0;
                    break;
                default:
                    break;
            }
            super.handleMessage(msg);
        }
    };



    private Runnable mBackgroundRunnable = new Runnable() {
        @Override
        public void run() {
            byte[] KeyValue = new byte[1];
            int ret = -1;

            // TODO Auto-generated method stub
            while(true)
            {
                if(mRunning == false)
                    break;
                ret = MainApp.cepp_dri.EPP_GetKey(KeyValue);
                if(ret == 0)
                {
                    Log.v("Key Press", String.valueOf((int)KeyValue[0]));
                    if(KeyValue[0] > 0)
                    {
                        Message message = new Message();
                        message.what=4;
                        m_strPINText = bytesToHexString(KeyValue);
                        Log.v("Key Press",m_strPINText);
                        mHandler.sendMessage(message);
                    }
                }
            }
        }
    };
    public static final String bytesToHexString(byte[] bArray)
    {
        StringBuffer sb = new StringBuffer(bArray.length);
        String sTemp;
        for (int i = 0; i < bArray.length; i++)
        {
            sTemp = Integer.toHexString(0xFF & bArray[i]);
            if (sTemp.length() < 2)
                sb.append(0);
            sb.append(sTemp.toUpperCase());
        }
        return sb.toString();
    }
    @Override
    public void onDestroy() {
        super.onDestroy();
        byte bResult = 0x00;
        mRunning = false;
        int nflag = MainApp.cepp_dri.EPP_ExitInputMode();

        if(nflag < 0){
//            ToastUtils.INSTANCE.showTopShortToast(getActivity(),"关闭键盘失败");
        }
        else{
//            ToastUtils.INSTANCE.showTopShortToast(getActivity(),"关闭键盘明文成功");
        }
        int nflag2 = MainApp.cepp_dri.EPP_UseEppPlainTextMode(bResult);

        if(nflag2 < 0){
//            ToastUtils.INSTANCE.showTopShortToast(getActivity(),"关闭蜂鸣器失败");
        }
        hThread.quit();
        mHandler.removeCallbacks(mBackgroundRunnable);
        MainApp.cepp_dri.EPP_CloseDevice();
    }
}
