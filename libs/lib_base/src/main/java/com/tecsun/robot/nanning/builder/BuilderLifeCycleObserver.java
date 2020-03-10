package com.tecsun.robot.nanning.builder;

import android.util.Log;

import androidx.lifecycle.Lifecycle;
import androidx.lifecycle.LifecycleObserver;
import androidx.lifecycle.OnLifecycleEvent;


/**
 * 生命周期自动回调释放资源
 * @author liudongwen
 */
final public class BuilderLifeCycleObserver implements LifecycleObserver {

    private static final String TAG = BuilderLifeCycleObserver.class.getSimpleName();

    private BaseSpeechFunctionImpl baseSpeechFunctionImpl;
    public BuilderLifeCycleObserver(BaseSpeechFunctionImpl speechFunctionImpl) {
        this.baseSpeechFunctionImpl = speechFunctionImpl;
    }

    @OnLifecycleEvent(Lifecycle.Event.ON_CREATE)
    public void onActivityCreate() {
        Log.d(TAG, ">>>>>>>>>>>>>>>>>>>>> onActivityCreate");
        if(baseSpeechFunctionImpl!=null){
            baseSpeechFunctionImpl.onCreate();
        }
    }
    @OnLifecycleEvent(Lifecycle.Event.ON_START)
    public void onActivityStart() {
        Log.d(TAG, ">>>>>>>>>>>>>>>>>>>>> onActivityStart");
        if(baseSpeechFunctionImpl!=null){
            baseSpeechFunctionImpl.onStart();
        }
    }

    @OnLifecycleEvent(Lifecycle.Event.ON_RESUME)
    public void onActivityResume() {
        Log.d(TAG, ">>>>>>>>>>>>>>>>>>>>> onActivityResume");
        if(baseSpeechFunctionImpl!=null){
            baseSpeechFunctionImpl.onResume();
        }
    }

    @OnLifecycleEvent(Lifecycle.Event.ON_PAUSE)
    public void onActivityPause() {
        Log.d(TAG, ">>>>>>>>>>>>>>>>>>>>> onActivityPause");
        if(baseSpeechFunctionImpl!=null){
            baseSpeechFunctionImpl.onPause();
        }
    }

    @OnLifecycleEvent(Lifecycle.Event.ON_DESTROY)
    public void onActivityDestroy() {
        Log.d(TAG, ">>>>>>>>>>>>>>>>>>>>> onActivityDestroy");
        if(baseSpeechFunctionImpl!=null){
            baseSpeechFunctionImpl.onDestroy();
        }
    }
}












