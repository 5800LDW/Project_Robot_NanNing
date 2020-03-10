//package com.tecsun.robot.builder;
//
//import android.os.Handler;
//import android.os.Message;
//import android.text.TextUtils;
//
//import androidx.annotation.NonNull;
//
//import com.sanbot.opensdk.function.beans.FaceRecognizeBean;
//import com.tecsun.robot.nanning.lib_base.BaseActivity;
//import com.tecsun.robot.nanninig.R;
//
//import java.util.LinkedList;
//
//public class WelcomeSpeakBuilder_BACK extends BaseBuilder {
//    private final LinkedList<Long> lLong = new LinkedList<>();
//    private final LinkedList<FaceRecognizeBean> lBean = new LinkedList<>();
//    private final int INT_FIND_USER = 0;
//    private final int INT_NORMAL_USER = 1;
//    private final long speakInternal = 20 * 1000;
//    private final long collectTimeInternal = 1200;
//    private long lastSpeakLogTime = 0;
//    private BaseActivity baseActivity;
//
//    public WelcomeSpeakBuilder_BACK(BaseActivity baseActivity) {
//        this.baseActivity = baseActivity;
//    }
//
//    public boolean speak(FaceRecognizeBean bean) {
//        if (System.currentTimeMillis() - lastSpeakLogTime > speakInternal) {
//            lastSpeakLogTime = System.currentTimeMillis();
//            if (!TextUtils.isEmpty(bean.getUser())) {
//                baseActivity.speak(String.format(baseActivity.getResources().getString(R.string.app_welcome_user), bean.getUser()));
//            } else {
//                baseActivity.speak(baseActivity.getResources().getString(R.string.app_welcome));
//            }
//            return true;
//        }
//        return false;
//    }
//
//    public void delaySpeak(FaceRecognizeBean bean , StartListener listener) {
//        long internalTimeLocal = System.currentTimeMillis() - lastSpeakLogTime;
//
//        if (!TextUtils.isEmpty(bean.getUser())) {
//            lLong.add(INT_FIND_USER, System.currentTimeMillis());
//            lBean.add(INT_FIND_USER, bean);
//        }
//
//        if (internalTimeLocal > speakInternal) {
//            lastSpeakLogTime = System.currentTimeMillis();
//            //休眠
//            baseActivity.speechManagerSleep();
//            mHandler.postDelayed(() -> {
//                if (lLong.size() == 0 || lBean.size() == 0 || lLong.get(INT_FIND_USER) == null || lBean.get(INT_FIND_USER) == null) {
//                    baseActivity.speak(baseActivity.getResources().getString(R.string.app_welcome));
//                } else if (System.currentTimeMillis() - lLong.get(INT_FIND_USER)  > collectTimeInternal * 2 ) {
//                    baseActivity.speak(baseActivity.getResources().getString(R.string.app_welcome));
//                } else if (lBean.get(INT_FIND_USER) != null) {
//                    baseActivity.speak(String.format(baseActivity.getResources().getString(R.string.app_welcome_user),
//                            lBean.get(INT_FIND_USER).getUser()));
//                }
//                lLong.clear();
//                lBean.clear();
//                mHandler.postDelayed(()->{
//                    if(listener!=null){
//                        listener.start();
//                    }
//                },5000);
//            }, collectTimeInternal);
//        }
//    }
//
//    public interface StartListener{
//        void start();
//    }
//
//
//
//
//    private static final int STATE_FREE = 0;
//    private static final int STATE_WORKING = 1;
//    private static final int STATE_PAUSE = 3;
//    private static int currentState = 0;
//    private long lastUnFreeTime = 0;
//    private static final long CHECK_TIME_INTERNAL = 30 * 1000;
//    private Handler stateHandler = new Handler() {
//        @Override
//        public void handleMessage(@NonNull Message msg) {
//            super.handleMessage(msg);
//            currentState = msg.what;
//            if (msg.what == STATE_FREE) {
//            } else if (msg.what == STATE_WORKING) {
//                lastUnFreeTime = System.currentTimeMillis();
//            } else if (msg.what == STATE_PAUSE) {
//                lastUnFreeTime = System.currentTimeMillis();
//            }
//        }
//    };
//
//
//
//
//    @Override
//    protected void onCreate() {
//
//    }
//
//    @Override
//    protected void onStart() {
//
//    }
//
//    @Override
//    protected void onResume() {
//
//    }
//
//    @Override
//    protected void onPause() {
//
//    }
//
//    @Override
//    protected void onDestroy() {
//
//    }
//
//}
