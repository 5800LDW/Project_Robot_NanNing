package com.tecsun.robot.dance;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.view.View;

import com.example.xukefeng.musicplayer.Animation.AnimImageViewLoader;
import com.sanbot.dance_play.DanceBean;
import com.sanbot.dance_play.DanceInterface;
import com.sanbot.dance_play.DanceManager;
import com.sanbot.opensdk.function.unit.interfaces.speech.WakenListener;
import com.tecsun.robot.MainApp;
import com.tecsun.robot.nanning.lib_base.BaseActivity;
import com.tecsun.robot.nanning.lib_base.BaseRecognizeListener;
import com.tecsun.robot.nanning.util.pinyin.PinYinUtil;
import com.tecsun.robot.nanninig.R;

import java.util.List;

import static com.sanbot.dance_play.DanceManager.DANCE_STATUS_COMPLETE;

public class DanceActivity extends BaseActivity {


    protected AnimImageViewLoader animLoader;

    @Override
    protected void onMainServiceConnected() {
        super.onMainServiceConnected();
//        speak("我要跳舞了，请确保一米范围内没有物体存在，以免发生碰撞");
    }

    //    protected FrameAnimationView animLoader;
    private DanceManager danceManager = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setWakeUpSpeechManager(false);
        setContentView(R.layout.activity_dance);
        View view = findViewById(R.id.gif_dance);
        view.setOnClickListener(v -> {
            if (danceManager != null) {
                danceManager.stop();
            }
            setResult(0x01);
            Handler handler = new Handler();
            handler.postDelayed(() -> {
                myFinish();
            }, 2000);
        });
        Intent i = getIntent();
        Bundle bundle = i.getExtras();
        int index = -1;
        if (bundle != null) {
            index = bundle.getInt("position");
            danceManager = MainApp.getInstance().getManager();
            danceManager.setCallBack(new DanceInterface() {
                @Override
                public void onErrorListener(int i, String s) {
                    Log.d("======>", "DanceManager onErrorListener:code="+i+",msg="+s);
                }

                @Override
                public void onStateCallBack(int i) {
                    if(i == DANCE_STATUS_COMPLETE){
                        danceManager.stop();
                        setResult(0x00);
                        myFinish();
                    }
                }
            });

            List<DanceBean> list = danceManager.getDanceList(this);
            if (list != null && list.size() > 0) {
                danceManager.setDanceById(list.get(index).getId());
                Handler handler = new Handler();
                handler.postDelayed(() -> {
                    danceManager.start();
                }, 2000);
            }
        } else {
            setResult(0x01, null);
            myFinish(); //结束当前的activity的生命周期
        }


        speechManager.setOnSpeechListener(new WakenListener() {
            @Override
            public void onWakeUp() {
            }

            @Override
            public void onSleep() {
                speechManager.doWakeUp();
            }

            @Override
            public void onWakeUpStatus(boolean b) {

            }
        });


        //语音监听结果回调
        speechManager.setOnSpeechListener(new BaseRecognizeListener() {
            @Override
            public void voiceRecognizeText(String voiceTXT) {
                //语音监听返回
                if (PinYinUtil.isMatch(voiceTXT, getResources().getStringArray(com.example.xukefeng.musicplayer.R.array.app_arr_backromain))) {
                    if (danceManager != null) {
                        danceManager.stop();
                    }
                    Handler handler = new Handler();
                    handler.postDelayed(() -> {
                        setResult(0x00);
                        myFinish();
                    }, 2000);
                } else if (PinYinUtil.isMatch(voiceTXT, getResources().getStringArray(com.example.xukefeng.musicplayer.R.array.app_arr_back))) {
                    if (danceManager != null) {
                        danceManager.stop();
                    }
                    Handler handler = new Handler();
                    handler.postDelayed(() -> {
                        setResult(0x01);
                        myFinish();
                    }, 2000);
                }
            }
        });

    }


    @Override
    public void onDestroy() {
        Log.d("onDestroy", "onDestroy");
        if (animLoader != null) {
            animLoader.stopAnimation();
            animLoader = null;
        }
        if (danceManager != null) {
            danceManager.destroy();
        }
        super.onDestroy();
    }
}
