package com.tecsun.robot.nanning.lib_base;

import com.sanbot.opensdk.function.unit.interfaces.speech.WakenListener;
import com.tecsun.robot.nanning.util.log.LogUtil;

public class BaseWakenListener implements WakenListener {
    private static final String TAG = BaseRecognizeListener.class.getSimpleName();

    @Override
    public void onWakeUp() {
        LogUtil.i(TAG, "onWakeUp >>>>>>>>>>>>>>>>");
    }

    @Override
    public void onSleep() {

        LogUtil.i(TAG, "onSleep >>>>>>>>>>>>>>>> ");
    }

    @Override
    public void onWakeUpStatus(boolean b) {
        LogUtil.i(TAG, "onWakeUpStatus >>>>>>>>>>>>>>>> = " + b);
    }
}
