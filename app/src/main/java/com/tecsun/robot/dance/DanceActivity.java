package com.tecsun.robot.dance;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
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
import static com.sanbot.dance_play.DanceManager.DANCE_STATUS_PLAYING;
import static com.sanbot.dance_play.DanceManager.DANCE_STATUS_STOP;

public class DanceActivity extends BaseActivity {


    protected AnimImageViewLoader animLoader;

    private int result = 1;
    private int currState = -1;
//    private DesktopMotionManager desktopManager;
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
                //danceManager.pause();
                if(currState == DANCE_STATUS_PLAYING){
                    danceManager.stop();
                    result = 0x01;
                }
            }

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
                    setResult(0x01);
                    Handler handler = new Handler(Looper.getMainLooper());
                    handler.postDelayed(() -> {
                        myFinish();
                    }, 500);
                }

                @Override
                public void onStateCallBack(int i) {

                    if(i == DANCE_STATUS_STOP){
//                        desktopManager.doReset();
                        Handler handler = new Handler(Looper.getMainLooper());
                        handler.postDelayed(() -> {
                            setResult(result);
                            myFinish();
                        }, 500);
                    }else if(i == DANCE_STATUS_COMPLETE && currState != -1){
                        danceManager.stop();
                        setResult(0x01);
                    }
                    currState = i;
                }
            });

            List<DanceBean> list = danceManager.getDanceList(this);
            if (list != null && list.size() > 0) {
                if(danceManager == null){
                    myFinish();
                    return;
                }
                danceManager.setDanceById(list.get(index).getId());
                new Thread(() -> {
                    while (!isRobotServiceConnected()) {
                        try {
                            Thread.sleep(10);
                        } catch (InterruptedException e) {
                            e.printStackTrace();
                        }
                    }
                    speakAndCheckComplete("好的，我要跳舞了，请保证我身边一米范围内没有物体，以免发生碰撞", () -> {
                        danceManager.start();
                    });
                }).start();

//                Handler handler = new Handler(Looper.getMainLooper());
//                handler.postDelayed(() -> {
//                    danceManager.start();
//                }, 1000);
            }else {     //读取舞蹈数据异常
               myFinish();
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
                    if (danceManager != null && currState == DANCE_STATUS_PLAYING) {
                        //danceManager.pause();
                        danceManager.stop();
                    }
                    result = 0;
                } else if (PinYinUtil.isMatch(voiceTXT, getResources().getStringArray(com.example.xukefeng.musicplayer.R.array.app_arr_back))) {
                    if (danceManager != null && currState == DANCE_STATUS_PLAYING) {
                        //danceManager.pause();
                        danceManager.stop();
                    }
                    result = 1;
                }
            }
        });

//        desktopManager = (DesktopMotionManager)getUnitManager(FuncConstant.DESKTOPMOTION_MANAGER);

//        SystemManager systemManager= (SystemManager)getUnitManager(FuncConstant. SYSTEM_MANAGER);
//        if(systemManager!= null){
//            systemManager.switchFloatBar(false,getClass().getName());
//        }
    }


    @Override
    public void onDestroy() {
        Log.d("onDestroy", "onDestroy");
        if (animLoader != null) {
            animLoader.stopAnimation();
            animLoader = null;
        }
        super.onDestroy();

    }

    @Override
    public void onBackPressed() {
        if (danceManager != null && currState == DANCE_STATUS_PLAYING) {
            setResult(0x01);
            danceManager.stop();
        }
    }
}
