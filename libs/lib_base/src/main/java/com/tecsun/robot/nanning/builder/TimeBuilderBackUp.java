//package com.tecsun.robot.nanning.builder;
//
//import com.tecsun.robot.nanning.lib_base.BaseActivity;
//import com.tecsun.robot.nanning.util.log.LogUtil;
//
//import java.util.LinkedList;
//
//public final class TimeBuilderBackUp extends BaseBuilder {
//
//    private static final String TAG = TimeBuilderBackUp.class.getSimpleName();
//
//    private BaseActivity baseActivity;
//
//    private TimeListener listener;
//
//    private final LinkedList<Integer> list = new LinkedList<>();
//
//    public static final int TIME_1 = 55;
//
//    public static final int TIME_2 = 70;
//
//    public TimeBuilderBackUp(BaseActivity baseActivity, TimeListener listener) {
//        this.baseActivity = baseActivity;
//        this.listener = listener;
//        baseActivity.getLifecycle().addObserver(new BuilderLifeCycleObserver(this));
//    }
//
//    private boolean isQuit = false;
//
//    public void release() {
//        isQuit = true;
//        mHandler.removeCallbacks(checkRunnable);
//        mHandler.removeCallbacksAndMessages(null);
//    }
//
//    @Override
//    protected void onCreate() {
//        mHandler.removeCallbacksAndMessages(null);
//        mHandler.postDelayed(checkRunnable, 1000);
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
//        mHandler.removeCallbacks(checkRunnable);
//        super.onDestroy();
//    }
//
//    private Runnable checkRunnable = () -> {
//        checkTime();
//    };
//
//    private final void checkTime() {
//        LogUtil.i(TAG, "checkTime()");
//
//        if (baseActivity != null && !isQuit) {
//            baseActivity.runOnUiThread(() -> {
//
//                list.add(0);
//
//                LogUtil.i(TAG, "list.size() = " + list.size());
//
//                if (listener != null) {
//                    listener.biz(list.size());
//                }
//
//                mHandler.postDelayed(checkRunnable, 1000);
//            });
//        }
//    }
//
//    public interface TimeListener {
//        /**
//         * 超时多少秒
//         *
//         * @param second
//         */
//        void biz(int second);
//
//    }
//
//    public final void initTime() {
//        baseActivity.runOnUiThread(() -> {
//            LogUtil.i(TAG, "initTime()");
//            list.clear();
//        });
//    }
//}
//
//
//
//
//
//
//
//
//
//
//
//
//
//
