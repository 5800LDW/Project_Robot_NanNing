package com.tecsun.robot.nanning.builder;

import android.os.Handler;
import android.os.Looper;

public abstract class BaseBuilder extends BaseSpeechFunctionImpl{
    protected Handler mHandler = new Handler(Looper.myLooper());

    @Override
    protected void onDestroy() {
        mHandler.removeCallbacksAndMessages(null);
    }
}
