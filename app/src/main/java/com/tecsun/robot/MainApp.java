package com.tecsun.robot;

import android.content.Context;
import android.os.PowerManager;
import android.util.Log;

import androidx.fragment.app.FragmentActivity;
import androidx.multidex.MultiDexApplication;

import com.example.kmy.CEPP_Dri;
import com.sanbot.dance_play.DanceInterface;
import com.sanbot.dance_play.DanceManager;
import com.tecsun.tsb.network.manager.NetworkManager;
import com.tecsun.tsb.network.retrofit.RetrofitAPIImpl;

import retrofit2.Retrofit;

/**
 * Created by chen on 2019/12/12.
 */
public class MainApp extends MultiDexApplication {

    private static Context ctx = null;

    private static final String TAG = "MainApp";
    private static MainApp instance = null;
    public static RetrofitAPIImpl retrofitAPI = null;
    private static Retrofit mRetrofit = null;
    public static String deviceId = "";
    public static String tokenId = "";
    private PowerManager.WakeLock wakeLock = null;

    public static FragmentActivity mFragmentActivity;
    private static DanceManager manager = null;

    //    public static String IP = "10.190.149.110"; //内网
//    public static int ID = 8283; //内网
    //广西外网
    public static String IP = "116.10.194.35";
    public static int ID = 9393;
    //公司转发
//    public static String IP = "61.28.113.182";
//    public static int ID = 9395;


    //    public static String IP = "192.168.1.153";
//    public static int ID = 8000;
    @Override
    protected void attachBaseContext(Context base) {
        super.attachBaseContext(base);
//        MultiDex.install(this);
    }

    public static MainApp getInstance() {
        if (instance == null) {
            synchronized (MainApp.class) {
                instance = new MainApp();
            }
        }
        return instance;
    }

    public  static CEPP_Dri cepp_dri;
    public DanceManager getManager(){
        return manager;
    }

    public static Context getContext() {
        return ctx;
    }

    @Override
    public void onCreate() {
        acquireWakeLock();
        ctx = getApplicationContext();
//        manager = SanbotDanceManager.Companion.getInstance();
//        manager.init();
        cepp_dri=new CEPP_Dri();
        DanceManager.init(this);
        manager = DanceManager.getSelf();
        manager.setCallBack(new DanceInterface() {
            @Override
            public void onErrorListener(int i, String s) {
                Log.d("======>", "DanceManager onErrorListener:code="+i+",msg="+s);
            }

            @Override
            public void onStateCallBack(int i) {
                Log.d("======>", "DanceManager onStateCallBack:code="+i+",msg="+i);
            }
        });

//        if (!BuildConfig.DEBUG) {
//            CrashHandler crashHandler = CrashHandler.getInstance();
//            crashHandler.init(ctx);
//        } else {        //DEBUG 模式
//            //        LogUtils.DEBUG_MODE = true;
//            LogUtil.isShowLog = true;
//            openOrCloseLeakCanary(true);
////        LogUtil.isShowLog = false;
//        }


//        LogUtils.DEBUG_MODE = true;
//        LogUtil.isShowLog = true;
//        LogUtil.isShowLog = false;

        long time = System.currentTimeMillis();

//        LogUtil.e("MainApp-onCreate");
        initRetrofit();

        super.onCreate();

//        LogUtil.d("Application onCreate [" + (System.currentTimeMillis() - time) + "ms]");
//        //拷贝金融配置文件到  /cache/tecsun/
//        if (!AppUtils.copyApkFromAssets(this, "/mnt/sdcard/", "KeyBoard.ini")) {
//            LogUtil.e("!!!!!! 金融配置文件拷贝失败！ 请检查文件是否存在");
//        }
    }
//
//    /**
//     * 是否开启LeakCanary检测
//     *
//     * @param isOpen
//     */
//    private void openOrCloseLeakCanary(boolean isOpen) {
//        if (isOpen) {
//            if (LeakCanary.isInAnalyzerProcess(this)) {
//                return;
//            }
//            LeakCanary.install(this);
//        }
//    }

    public static void setRetrofit(Retrofit retrofit) {
        mRetrofit = retrofit;
    }

    public static Retrofit getRetrofit() {
        if (mRetrofit == null) {
            initRetrofit();
        }
        return mRetrofit;
    }

    public static RetrofitAPIImpl getRetrofitAPI() {
        if (retrofitAPI == null) {
            initRetrofit();
        }
        return retrofitAPI;
    }


//    @Override
//    public void onTerminate() {
//        LogUtil.d("MainApp-onTerminate");
//        releaseWakeLock();
//        SignInAlarmManagerUtil.getInstance(getApplicationContext()).stopSignInService();
//        SignInAlarmManagerUtil.getInstance(getApplicationContext()).stopHBService();
//        ActivityManager manager = (ActivityManager) this.getSystemService(ACTIVITY_SERVICE); //获取应用程序管理器
//        manager.killBackgroundProcesses(getPackageName()); //强制结束当前应用程序
//        super.onTerminate();
//    }

    /**
     * 获取电源锁，保持该服务在屏幕熄灭时仍然获取CPU时，保持运行
     */

    public void acquireWakeLock() {
        if (null == wakeLock) {
            PowerManager pm = (PowerManager) getSystemService(Context.POWER_SERVICE);
            wakeLock = pm.newWakeLock(PowerManager.SCREEN_BRIGHT_WAKE_LOCK
                    | PowerManager.ON_AFTER_RELEASE, getClass().getCanonicalName());

            if (null != wakeLock) {
                Log.i(TAG, "call acquireWakeLock");
                wakeLock.acquire();
            }
        }
    }

    // 释放设备电源锁
    private void releaseWakeLock() {
        if (null != wakeLock && wakeLock.isHeld()) {
//            LogUtil.i(TAG, "call releaseWakeLock");
            wakeLock.release();
            wakeLock = null;
        }
    }

    @Override
    public void onLowMemory() {
        System.gc();
        super.onLowMemory();
    }


    //app网络初始化

    private static void setNetworkConfig(RetrofitAPIImpl api, Retrofit retrofit) {
        NetworkManager.setDeviceId(MainApp.deviceId);
        NetworkManager.setRetrofitAPI(api);
        NetworkManager.setRetrofit(retrofit);
    }

//    /**
//     * 获取机器号
//     *
//     * @return 优先获取读写器序列号，若无则获取设备号
//     */
//    public static String getDeviceId() {
//        if (TextUtils.isEmpty(deviceId)) {
//            String tmp = "";
//            for (int i = 0; i < 3; i++) {
//                tmp = ICCardUtils.getCpuSerial();
//                if (!TextUtils.isEmpty(tmp)) {
//                    break;
//                }
//            }
//            deviceId = TextUtils.isEmpty(tmp) ? SystemInfoUtil.getDeviceId(getContext())
//                    : tmp.toLowerCase();
//        }
//        //TODO TEST DEBUG CODE
//        if(BuildConfig.DEBUG){
////        deviceId = "05545aee0000017f";
////            deviceId =  "0554ad16000003e9";(接口组专用，其他人勿用)
////        deviceId =  "0554ad190000040b";
//            deviceId =  "075cec910000340f";           //生产环境CPU号
//        }
//        LogUtil.d("deviceId", deviceId);
//        return deviceId;
//    }


    /**
     * 初始化网络服务
     * 【注意】io线程操作
     */
    public static void initRetrofit() {
//        LogUtil.d("initRetrofit");
//        getDeviceId();

        MainApp.retrofitAPI = new RetrofitAPIImpl();

        MainApp.setRetrofit(MainApp.retrofitAPI.initRetrofit(getContext(), "http://" + MainApp.IP + ":" + MainApp.ID + "/IFaceSuportService/"));

        setNetworkConfig(MainApp.retrofitAPI, MainApp.getRetrofit());
    }

    public static int TIMEOUT_CHECK_HOST = 1000;//检查端口是否可用，超时时间
    //备用host
    String[] IPs = {
//        "61.28.113.182",//测试环境外网
    };
    int[] IDs = {
//        4487,//测试环境外网
    };

//    public boolean checkHosts() {
//        try {
//            if (new NetworkUtil().isNetworkConnected(getApplicationContext()) && !NetworkUtil.isOkHost(MainApp.IP, MainApp.ID, TIMEOUT_CHECK_HOST)) {
//                LogUtil.e(MainApp.IP + MainApp.ID + "不可以用");
//
//                for (int i = 0; i < IPs.length; i++) {
//                    if (NetworkUtil.isOkHost(IPs[i], IDs[i], TIMEOUT_CHECK_HOST)) {
//                        MainApp.IP = IPs[i];
//                        MainApp.ID = IDs[i];
//                        LogUtil.e("启用备用host:" + MainApp.IP + MainApp.ID);
//                        return true;
//                    }
//                }
//            }
//        } catch (Exception e) {
//            e.printStackTrace();
//        }
//        return false;
//    }


    @Override
    public void onTerminate() {
        super.onTerminate();
        if (manager != null) {
            manager.destroy();
        }
    }
}
