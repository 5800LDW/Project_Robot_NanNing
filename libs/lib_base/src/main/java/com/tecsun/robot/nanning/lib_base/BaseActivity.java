package com.tecsun.robot.nanning.lib_base;

import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.text.TextUtils;
import android.util.Log;
import android.view.KeyEvent;
import android.view.MotionEvent;
import android.view.View;
import android.view.WindowManager;

import com.sanbot.opensdk.base.TopBaseActivity;
import com.sanbot.opensdk.beans.FuncConstant;
import com.sanbot.opensdk.beans.Order;
import com.sanbot.opensdk.function.beans.speech.SpeakStatus;
import com.sanbot.opensdk.function.unit.FaceTrackManager;
import com.sanbot.opensdk.function.unit.HDCameraManager;
import com.sanbot.opensdk.function.unit.HardWareManager;
import com.sanbot.opensdk.function.unit.SpeechManager;
import com.sanbot.opensdk.function.unit.interfaces.speech.SpeakListener;
import com.tecsun.jc.base.utils.ToastUtils;
import com.tecsun.robot.nanning.builder.TimeBuilder;
import com.tecsun.robot.nanning.builder.net.monitor.NetChangeObserver;
import com.tecsun.robot.nanning.util.log.LogUtil;
import com.tecsun.robot.nanning.util.network.NetWorkBuilder;

import org.greenrobot.eventbus.EventBus;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import static android.view.KeyEvent.KEYCODE_BACK;

public class BaseActivity extends TopBaseActivity {
    private final String TAG = BaseActivity.class.getSimpleName();
    /***
     * 在跳转到其他activity的时候会自动断开连接机器人, 所以在onResume里面初始化;在robotServiceConnected()会显示初始化完成(包括连接上机器人)!!!
     *
     * 语音处理类
     */
    public SpeechManager speechManager;

    /**
     * 在跳转到其他activity的时候会自动断开连接机器人, 所以在onResume里面初始化;在robotServiceConnected()会显示初始化完成(包括连接上机器人)!!!
     * <p>
     * 头像监听处理类,直接注册监听,检测到人像会自动回调;
     */
    public HDCameraManager hdCameraManager;

    /**
     * 人脸跟踪以及主动打招呼
     */
    public FaceTrackManager faceTrackManager;

    /**
     * 硬件状态获取管理类
     */
    public HardWareManager hardWareManager;

    private boolean isRobotServiceConnected = false;

    private boolean isWakeUpSpeechManager = true;

    public void setWakeUpSpeechManager(boolean wakeUpSpeechManager) {
        isWakeUpSpeechManager = wakeUpSpeechManager;
    }

    /**
     * 测试是每次进入activity都会执行下面的方法, 所以那几个SpeechManager,HDCameraManager和FaceTrackManager都要写在这个方法里面重新setListener
     */
    @Override
    protected void onMainServiceConnected() {
        Log.e("TAG", getClass() + "   onMainServiceConnected() ======");
        isRobotServiceConnected = true;
        onRobotServiceConnected();
        speechManagerWakeUp();
    }

    protected void onRobotServiceConnected() {

    }

    public final boolean isRobotServiceConnected() {
        return isRobotServiceConnected;
    }

    @Override
    protected void onStop() {
        super.onStop();
    }

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        register(getClass());
        //屏幕常亮
        getWindow().addFlags(WindowManager.LayoutParams.FLAG_KEEP_SCREEN_ON);
        super.onCreate(savedInstanceState);
        //初始化变量
        speechManager = (SpeechManager) getUnitManager(FuncConstant.SPEECH_MANAGER);
        hdCameraManager = (HDCameraManager) getUnitManager(FuncConstant.HDCAMERA_MANAGER);
        faceTrackManager = (FaceTrackManager) getUnitManager(FuncConstant.FACETRACK_MANAGER);
        hardWareManager = (HardWareManager) getUnitManager(FuncConstant.HARDWARE_MANAGER);
    }

    @Override
    protected void onResume() {
        super.onResume();
//        if (isRobotServiceConnected()) {
        speechManagerWakeUp();
//        }
    }

    @Override
    protected void onPause() {
        super.onPause();
        //休眠
        if (speechManager != null) {
            speechManager.doSleep();
            Log.e("TAG", "speechManager.doSleep()");
        }
    }

    protected void myStartActivity(Class cls) {
        speechManager.cancelSemanticRequest();
        startActivity(new Intent(this, cls));
    }

    public final void showToast(String str) {
        if (str != null) {
            ToastUtils.INSTANCE.showGravityLongToast(this, str);
        }
    }

    public final void speak(String content) {
        if (speechManager != null) {
//            SpeakOption speakOption = new SpeakOption();
            //设置合成语言
//            speakOption.setLanguageType(SpeakOption.LAG_CHINESE);
            //设置合成语速
//            speakOption.setSpeed(60);
            //设置合成声调
//            speakOption.setIntonation(69);

            //取消默认回调;
            speechManager.setOnSpeechListener(new SpeakListener() {
                @Override
                public void onSpeakStatus(@NotNull SpeakStatus speakStatus) {
                }
            });
            speechManager.startSpeak(content);
        }
    }

    public final void speakAndCheckComplete(String content, SpeakComplete listen) {
        if (speechManager != null) {
            speechManager.setOnSpeechListener(new SpeakListener() {
                @Override
                public void onSpeakStatus(@NotNull SpeakStatus speakStatus) {
                    if (speakStatus.getProgress() == -1f) {
//                    isSpeak = true
                    } else if (speakStatus.getProgress() >= 100.0) {
//                    isSpeak = false
                        if (listen != null) {
                            LogUtil.e(TAG, ">>>>>>>>>>>>>>>>>> listen.biz();");
                            listen.biz();

                            //取消默认回调;
                            speechManager.setOnSpeechListener(new SpeakListener() {
                                @Override
                                public void onSpeakStatus(@NotNull SpeakStatus speakStatus) {
                                }
                            });
                        }
                    }
                }
            });
            speechManager.startSpeak(content);
        }
    }

    public interface SpeakComplete {
        void biz();
    }


    /**
     * @return true 正在讲话, false 没在讲话
     */
    public final boolean isSpeaking() {

        try {
            if (speechManager != null &&
                    speechManager.isSpeaking() != null &&
                    speechManager.isSpeaking().getResult() != null &&
                    speechManager.isSpeaking() != null &&
                    speechManager.isSpeaking().getResult().equals("1")) {
                return true;
            } else if (speechManager != null &&
                    speechManager.isSpeaking() != null && speechManager.isSpeaking().getResult() != null &&
                    speechManager.isSpeaking() != null && speechManager.isSpeaking().getResult().equals("0")) {
                return false;
            } else {
                return false;
            }
        } catch (Exception e) {
            LogUtil.e(TAG, e);
            return false;
        }

    }

    public final void speechManagerWakeUp() {
        if (speechManager != null && isWakeUpSpeechManager) {
            speechManager.doWakeUp();
            Log.e("TAG", "speechManager.doWakeUp()");
        }
    }

    public final void speechManagerSleep() {
        if (speechManager != null) {
            speechManager.doSleep();
        }
    }

    public void unRegister() {
        if (EventBus.getDefault().isRegistered(this)) {
            EventBus.getDefault().unregister(this);
        }
    }

    @Override
    protected void onDestroy() {
        unRegister();
        super.onDestroy();
    }


    public final void sendQuestion(String question) {
        LogUtil.e(TAG, "question = " + question);
        //给主控发消息
        if (TextUtils.isEmpty(question)) {
            return;
        }

        LogUtil.e(TAG, "Build.MODEL = " + Build.MODEL);

        //停止机器人说话
        myStopSpeak();

        new Handler().post(() -> {
            //1480发文字，1481发舞蹈动作 //type在BD款机器人上传1480
            if (isKongKim()) {

                LogUtil.e(TAG, "question = " + question);
                //金刚 D款
                sendCommandToMainService(new Order(1480, 1480, question), null);
                LogUtil.e(TAG, "金刚");
            } else {
                //桌面款
                sendCommandToMainService(new Order(4865, 1480, question), null);
                LogUtil.e(TAG, "桌面款");
            }

        });
    }


    public boolean isKongKim() {
        return (Build.MODEL + "").toUpperCase().contains("FUWUDANDROID6MODEL");
    }

    public final void myStopSpeak() {
        if (speechManager != null) {
            speechManager.stopSpeak();
        }
    }

    public void myFinish() {
        finish();
        myStopSpeak();
        if (speechManager != null) {
            speechManager.cancelSemanticRequest();
        }
    }

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if (keyCode == KEYCODE_BACK && event.getAction() == KeyEvent.ACTION_DOWN) {
            myStopSpeak();
        }
        return super.onKeyDown(keyCode, event);
    }


    private NetWorkBuilder netWorkBuilder;

    /***
     * 注册网络监听,当网络没连上会在头部默认有提示;
     * @param locationView 根部局的view 或其他, 不传就默认根布局
     * @param netChangeObserver 网络回调
     */
    public void registerNetWorkMonitor(@Nullable View locationView, @androidx.annotation.Nullable NetChangeObserver netChangeObserver) {
        if (netWorkBuilder == null) {
            netWorkBuilder = new NetWorkBuilder(this, locationView, netChangeObserver);
        }
    }

    public final TimeBuilder timeBuilder = TimeBuilder.INSTANCE;

    /**
     * 超时监听
     *
     * @param listener
     */
    public TimeBuilder registerTimeMonitor(TimeBuilder.TimeListener listener) {
        timeBuilder.addBiz(listener);
        return timeBuilder;
    }

    @Override
    public boolean dispatchTouchEvent(MotionEvent ev) {
        if (ev.getAction() == MotionEvent.ACTION_DOWN) {
            //有按下动作时取消定时
            if (timeBuilder != null) {
                timeBuilder.initTime();
            }
        }
        return super.dispatchTouchEvent(ev);
    }
}
































