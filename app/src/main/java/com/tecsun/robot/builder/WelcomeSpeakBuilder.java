package com.tecsun.robot.builder;

import android.os.Handler;
import android.os.Message;
import android.text.TextUtils;
import android.util.Log;

import androidx.annotation.NonNull;

import com.sanbot.opensdk.function.beans.FaceRecognizeBean;
import com.tecsun.robot.nanning.builder.BaseBuilder;
import com.tecsun.robot.nanning.builder.BuilderLifeCycleObserver;
import com.tecsun.robot.nanning.lib_base.BaseActivity;
import com.tecsun.robot.nanning.util.log.LogUtil;
import com.tecsun.robot.nanninig.R;

import java.util.LinkedList;
import java.util.Random;

/**
 * 欢迎语处理
 *
 * @author liudongwen
 * @date 2020/02/29
 */
public class WelcomeSpeakBuilder extends BaseBuilder {

    private static final String TAG = WelcomeSpeakBuilder.class.getSimpleName();

    private final LinkedList<Long> lLong = new LinkedList<>();

    private final LinkedList<FaceRecognizeBean> lBean = new LinkedList<>();

    private final int INT_FIND_USER = 0;

    private final int INT_NORMAL_USER = 1;

    private final long LONG_SPEAK_INTERNAL = 15 * 1000;

    private final long collectTimeInternal = 1300;

    private long lastSpeakLogTime = 0;

    private BaseActivity baseActivity;

    String[] functionTextArr;

    //增加识别到跟上一张脸并不一样会主动问好;
    private String lastWelcomeName = "";

    private String getLastWelcomeName() {
        return lastWelcomeName;
    }

    public WelcomeSpeakBuilder(BaseActivity baseActivity) {
        this.baseActivity = baseActivity;
        baseActivity.getLifecycle().addObserver(new BuilderLifeCycleObserver(this));
    }

    private final String getRandomText() {
        if (functionTextArr == null || functionTextArr.length == 0) {
            return baseActivity.getResources().getString(R.string.app_text_information);
        }
        if (functionTextArr.length == 1) {
            return functionTextArr[0];
        }

        //不包括最后一个;
        int code = new Random().nextInt(functionTextArr.length - 1);
        return functionTextArr[code];
    }

    public final boolean speak(FaceRecognizeBean bean) {
        if (System.currentTimeMillis() - lastSpeakLogTime > LONG_SPEAK_INTERNAL) {
            lastSpeakLogTime = System.currentTimeMillis();
            if (!TextUtils.isEmpty(bean.getUser())) {
                baseActivity.speak(String.format(baseActivity.getResources().getString(R.string.app_welcome_user), bean.getUser() + "", getRandomText()));
            } else {
                baseActivity.speak(String.format(baseActivity.getResources().getString(R.string.app_welcome), getRandomText()));
            }
            return true;
        }
        return false;
    }

    /**
     * 识别到vip就立刻跟vip问好;
     *
     * @param bean
     */
    public final void delaySpeak(FaceRecognizeBean bean) {
        long internalTimeLocal = System.currentTimeMillis() - lastSpeakLogTime;

        if (bean != null && !TextUtils.isEmpty(bean.getUser())) {
            lLong.add(INT_FIND_USER, System.currentTimeMillis());
            lBean.add(INT_FIND_USER, bean);
        }

        //普通
        if (bean != null && TextUtils.isEmpty(bean.getUser()) && internalTimeLocal > LONG_SPEAK_INTERNAL) {
            isVip = false;
            lastSpeakLogTime = System.currentTimeMillis();
            mHandler.removeCallbacks(runnable);
            //后台识别vip照片成功次数比普通的少,添加延迟方便走普通-->vip
            mHandler.postDelayed(runnable, collectTimeInternal);
            LogUtil.e(TAG, "普通");
        }
        //普通-->vip
        else if (bean != null && !TextUtils.isEmpty(bean.getUser()) && !isVip && internalTimeLocal > 1000) {
            isVip = true;
            lastSpeakLogTime = System.currentTimeMillis();
            mHandler.removeCallbacks(runnable);
            mHandler.post(runnable);
            LogUtil.e(TAG, "普通-->vip");
        }
        //vip--->vip
        else if (isVip && internalTimeLocal > LONG_SPEAK_INTERNAL) {
            isVip = true;
            lastSpeakLogTime = System.currentTimeMillis();
            mHandler.removeCallbacks(runnable);
            mHandler.post(runnable);
            LogUtil.e(TAG, "vip-->vip");
        }
    }

    boolean isVip = false;

    private Runnable runnable = () -> {
        //停止说话
//        if(baseActivity.isSpeaking()){
//            baseActivity.myStopSpeak();
//        }
        BaseActivity.SpeakComplete speakComplete = () -> {
            //唤醒
            baseActivity.speechManagerWakeUp();

        };

        if (lLong.size() == 0 || lBean.size() == 0 || lLong.get(INT_FIND_USER) == null || lBean.get(INT_FIND_USER) == null) {
            baseActivity.speakAndCheckComplete(String.format(baseActivity.getResources().getString(R.string.app_welcome), getRandomText()), speakComplete);
        } else if (System.currentTimeMillis() - lLong.get(INT_FIND_USER) > collectTimeInternal * 2) {
            baseActivity.speakAndCheckComplete(String.format(baseActivity.getResources().getString(R.string.app_welcome), getRandomText()), speakComplete);
        } else if (lBean.get(INT_FIND_USER) != null) {
            baseActivity.speakAndCheckComplete(String.format(baseActivity.getResources().getString(R.string.app_welcome_user),
                    lBean.get(INT_FIND_USER).getUser(), getRandomText()), speakComplete);
        }
        lLong.clear();
        lBean.clear();
    };

    public interface StartListener {
        void start();
    }


    public static final int STATE_FREE = 0;

    public static final int STATE_WORKING = 1;

    public static final int STATE_PAUSE = 3;

    private static int currentState = 0;

    private long lastUnFreeTime = 0;

    private static final long CHECK_TIME_INTERNAL = 25 * 1000;

    private Handler stateHandler = new Handler() {
        @Override
        public void handleMessage(@NonNull Message msg) {
            super.handleMessage(msg);
            currentState = msg.what;
            if (msg.what == STATE_FREE) {
                //清除上一个人脸的名字信息;
                LogUtil.e(TAG, "STATE_FREE = " + STATE_FREE);
                lastWelcomeName = "";
//                setWelcomeName("");
            } else if (msg.what == STATE_WORKING) {
                lastUnFreeTime = System.currentTimeMillis();
            } else if (msg.what == STATE_PAUSE) {
                lastUnFreeTime = System.currentTimeMillis();
            }
        }
    };

    private Runnable checkRunnable = () -> {
        checkTime();
    };

    private final void checkTime() {
        //非空闲态(每次讲话都会刷新初始时间)和非讲话
        if (STATE_FREE != currentState &&
                STATE_PAUSE != currentState &&
                (System.currentTimeMillis() - lastUnFreeTime > (CHECK_TIME_INTERNAL - 1)) &&
                !baseActivity.isSpeaking()
        ) {
            stateHandler.sendEmptyMessage(STATE_FREE);
        }

        stateHandler.postDelayed(checkRunnable, CHECK_TIME_INTERNAL);
    }

    public final int getCurrentState() {
        return currentState;
    }

    public final void setStateWorking() {
        stateHandler.sendEmptyMessage(STATE_WORKING);
    }

    private int onStartTimes = 0;

    @Override
    protected void onCreate() {
        Log.e(TAG, "onCreate()");
        functionTextArr = baseActivity.getApplicationContext().getResources().getStringArray(R.array.app_arr_function_text);
//        stateHandler.sendEmptyMessage(STATE_FREE);
        checkTime();
    }

    @Override
    protected void onStart() {
        Log.e(TAG, "onStart()");
        if (onStartTimes != 0) {
            stateHandler.sendEmptyMessage(STATE_WORKING);
        }
        onStartTimes++;
    }

    @Override
    protected void onResume() {

    }

    @Override
    protected void onPause() {
        Log.e(TAG, "onPause()");
        stateHandler.sendEmptyMessage(STATE_PAUSE);
    }

    @Override
    protected void onDestroy() {
        Log.e(TAG, "onDestroy()");
        super.onDestroy();
        stateHandler.removeCallbacks(checkRunnable);
        stateHandler.removeCallbacksAndMessages(null);
    }

}
