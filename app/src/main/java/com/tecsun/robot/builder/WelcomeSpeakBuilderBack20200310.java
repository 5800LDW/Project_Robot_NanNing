//package com.tecsun.robot.builder;
//
//import android.os.Handler;
//import android.os.Message;
//import android.text.TextUtils;
//import android.util.Log;
//
//import androidx.annotation.NonNull;
//
//import com.sanbot.opensdk.function.beans.FaceRecognizeBean;
//import com.tecsun.robot.nanning.builder.BaseBuilder;
//import com.tecsun.robot.nanning.builder.BuilderLifeCycleObserver;
//import com.tecsun.robot.nanning.lib_base.BaseActivity;
//import com.tecsun.robot.nanning.util.log.LogUtil;
//import com.tecsun.robot.nanninig.R;
//
//import java.util.LinkedList;
//import java.util.Random;
//
///**
// * 欢迎语处理
// *
// * @author liudongwen
// * @date 2020/02/29
// */
//public class WelcomeSpeakBuilderBack20200310 extends BaseBuilder {
//
//    private static final String TAG = WelcomeSpeakBuilderBack20200310.class.getSimpleName();
//
//    private final LinkedList<Long> lLong = new LinkedList<>();
//
//    private final LinkedList<FaceRecognizeBean> lBean = new LinkedList<>();
//
//    private final int INT_FIND_USER = 0;
//
//    private final int INT_NORMAL_USER = 1;
//
//    private final long speakInternal = 15 * 1000;
//
//    private final long collectTimeInternal = 1300;
//
//    private long lastSpeakLogTime = 0;
//
//    private BaseActivity baseActivity;
//
//    String[] functionTextArr;
//
//
//    //增加识别到跟上一张脸并不一样会主动问好;
//    private String lastWelcomeName = "";
//
//    private String getLastWelcomeName() {
//        return lastWelcomeName;
//    }
//
////    private void setWelcomeName(String name) {
////        lastWelcomeName = name;
////    }
//
//    public final boolean isNewHumanFace(FaceRecognizeBean bean) {
//        boolean b = false;
//        if (bean != null &&
//                !TextUtils.isEmpty(bean.getUser()) &&
//                !getLastWelcomeName().equals(bean.getUser())) {
//            //名字不同
//            b = true;
//
//        } else {
//            b = false;
//        }
//        return b;
//    }
//
//    public WelcomeSpeakBuilderBack20200310(BaseActivity baseActivity) {
//        this.baseActivity = baseActivity;
//        baseActivity.getLifecycle().addObserver(new BuilderLifeCycleObserver(this));
//    }
//
//    private final String getRandomText() {
//        if (functionTextArr == null || functionTextArr.length == 0) {
//            return baseActivity.getResources().getString(R.string.app_text_information);
//        }
//        if (functionTextArr.length == 1) {
//            return functionTextArr[0];
//        }
//
//        //不包括最后一个;
//        int code = new Random().nextInt(functionTextArr.length - 1);
//        return functionTextArr[code];
//    }
//
//    public final boolean speak(FaceRecognizeBean bean) {
//        if (System.currentTimeMillis() - lastSpeakLogTime > speakInternal) {
//            lastSpeakLogTime = System.currentTimeMillis();
//            if (!TextUtils.isEmpty(bean.getUser())) {
//                baseActivity.speak(String.format(baseActivity.getResources().getString(R.string.app_welcome_user), bean.getUser() + "", getRandomText()));
//            } else {
//                baseActivity.speak(String.format(baseActivity.getResources().getString(R.string.app_welcome), getRandomText()));
//            }
//            return true;
//        }
//        return false;
//    }
//
//    public final void delaySpeak(FaceRecognizeBean bean) {
//        long internalTimeLocal = System.currentTimeMillis() - lastSpeakLogTime;
//
////        //TODO
////        if(bean == null){
////            bean = new FaceRecognizeBean();
////        }
//
//        if (bean != null && !TextUtils.isEmpty(bean.getUser())) {
//            lLong.add(INT_FIND_USER, System.currentTimeMillis());
//            lBean.add(INT_FIND_USER, bean);
//
//        }
//
//        //5000毫秒内检测到不同人脸打断上一次讲话,直接讲最新的人脸;
//        //刷新人脸后会记录名字,然后如果是同一张脸在屏幕前,isNewHumanFace(bean)就会返回fale就变成走上面的逻辑每隔15秒左右问候下;
//        if ((internalTimeLocal > speakInternal) || (isNewHumanFace(bean) && internalTimeLocal > 5 * 1000)) {
//
//            if (!TextUtils.isEmpty(bean.getUser())) {
//                lastWelcomeName = bean.getUser();
//            }
//
//            lastSpeakLogTime = System.currentTimeMillis();
//
//            //休眠 这里不用休眠了, HomeActivity的逻辑已经加了判断过滤机器人的声音;
////            baseActivity.speechManagerSleep();
//
//            mHandler.postDelayed(() -> {
//
//                BaseActivity.SpeakComplete speakComplete = () -> {
//                    //唤醒
//                    baseActivity.speechManagerWakeUp();
//
////                        if (listener != null) {
////                            listener.start();
////                        }
//                };
//
//                if (lLong.size() == 0 || lBean.size() == 0 || lLong.get(INT_FIND_USER) == null || lBean.get(INT_FIND_USER) == null) {
//                    baseActivity.speakAndCheckComplete(String.format(baseActivity.getResources().getString(R.string.app_welcome), getRandomText()), speakComplete);
//                } else if (System.currentTimeMillis() - lLong.get(INT_FIND_USER) > collectTimeInternal * 2) {
//                    baseActivity.speakAndCheckComplete(String.format(baseActivity.getResources().getString(R.string.app_welcome), getRandomText()), speakComplete);
//                } else if (lBean.get(INT_FIND_USER) != null) {
//                    baseActivity.speakAndCheckComplete(String.format(baseActivity.getResources().getString(R.string.app_welcome_user),
//                            lBean.get(INT_FIND_USER).getUser(), getRandomText()), speakComplete);
//                }
//                lLong.clear();
//                lBean.clear();
//
//            }, collectTimeInternal);
//        }
//    }
//
//    public interface StartListener {
//        void start();
//    }
//
//
//    public static final int STATE_FREE = 0;
//
//    public static final int STATE_WORKING = 1;
//
//    public static final int STATE_PAUSE = 3;
//
//    private static int currentState = 0;
//
//    private long lastUnFreeTime = 0;
//
//    private static final long CHECK_TIME_INTERNAL = 25 * 1000;
//
//    private Handler stateHandler = new Handler() {
//        @Override
//        public void handleMessage(@NonNull Message msg) {
//            super.handleMessage(msg);
//            currentState = msg.what;
//            if (msg.what == STATE_FREE) {
//                //清除上一个人脸的名字信息;
//                LogUtil.e("TAGTAGTAGTAG1", "STATE_FREE = " + STATE_FREE);
//                lastWelcomeName = "";
////                setWelcomeName("");
//            } else if (msg.what == STATE_WORKING) {
//                lastUnFreeTime = System.currentTimeMillis();
//            } else if (msg.what == STATE_PAUSE) {
//                lastUnFreeTime = System.currentTimeMillis();
//            }
//        }
//    };
//
//    private Runnable checkRunnable = () -> {
//        checkTime();
//    };
//
//    private final void checkTime() {
//        //非空闲态(每次讲话都会刷新初始时间)和非讲话
//        if (STATE_FREE != currentState &&
//                STATE_PAUSE != currentState &&
//                (System.currentTimeMillis() - lastUnFreeTime > (CHECK_TIME_INTERNAL - 1)) &&
//                !baseActivity.isSpeaking()
//        ) {
//            stateHandler.sendEmptyMessage(STATE_FREE);
//        }
//
//        stateHandler.postDelayed(checkRunnable, CHECK_TIME_INTERNAL);
//    }
//
//    public final int getCurrentState() {
//        return currentState;
//    }
//
//    public final void setStateWorking() {
//        stateHandler.sendEmptyMessage(STATE_WORKING);
//    }
//
//    private int onStartTimes = 0;
//
//    @Override
//    protected void onCreate() {
//        Log.e(TAG, "onCreate()");
//        functionTextArr = baseActivity.getApplicationContext().getResources().getStringArray(R.array.app_arr_function_text);
////        stateHandler.sendEmptyMessage(STATE_FREE);
//        checkTime();
//    }
//
//    @Override
//    protected void onStart() {
//        Log.e(TAG, "onStart()");
//        if (onStartTimes != 0) {
//            stateHandler.sendEmptyMessage(STATE_WORKING);
//        }
//        onStartTimes++;
//    }
//
//    @Override
//    protected void onResume() {
//
//    }
//
//    @Override
//    protected void onPause() {
//        Log.e(TAG, "onPause()");
//        stateHandler.sendEmptyMessage(STATE_PAUSE);
//    }
//
//    @Override
//    protected void onDestroy() {
//        Log.e(TAG, "onDestroy()");
//        super.onDestroy();
//        stateHandler.removeCallbacks(checkRunnable);
//        stateHandler.removeCallbacksAndMessages(null);
//    }
//
//}
