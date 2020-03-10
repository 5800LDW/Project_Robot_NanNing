package com.tecsun.robot.nanninig;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.view.View;

import androidx.annotation.Nullable;

import com.example.xukefeng.musicplayer.MusicListActivity;
import com.sanbot.opensdk.function.beans.FaceRecognizeBean;
import com.sanbot.opensdk.function.beans.speech.Grammar;
import com.sanbot.opensdk.function.unit.interfaces.media.FaceRecognizeListener;
import com.sanbot.opensdk.function.unit.interfaces.speech.WakenListener;
import com.tecsun.robot.builder.ListeningAnimationBuilder;
import com.tecsun.robot.dance.DanceMusicListActivity;
import com.tecsun.robot.nanning.lib_base.BaseActivity;
import com.tecsun.robot.nanning.lib_base.BaseRecognizeListener;
import com.tecsun.robot.nanning.util.pinyin.PinYinUtil;

import org.jetbrains.annotations.NotNull;

import java.util.List;

public class EntertainmentActivity extends BaseActivity {

    /**
     * 监听语音按钮,动画和点击事件
     */
    private ListeningAnimationBuilder lab = new ListeningAnimationBuilder(this);
    final String TAG = EntertainmentActivity.class.getName();
    private View ll1, ll2;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_entertainment);
        findViewById(R.id.image_retrun).setOnClickListener(v -> myFinish());

        ll1 = findViewById(R.id.ll1);
        ll1.setOnClickListener(v -> {
            speechManager.cancelSemanticRequest();
            speechManager.stopSpeak();
            startActivity(new Intent(this, MusicListActivity.class));
            myFinish();
        });
        ll2 = findViewById(R.id.ll2);
        ll2.setOnClickListener(v -> {
            speechManager.cancelSemanticRequest();
            speechManager.stopSpeak();
            startActivity(new Intent(this, DanceMusicListActivity.class));
            myFinish();
        });

        registerNetWorkMonitor(null,null);
    }

    @Override
    protected void onResume() {
        super.onResume();
    }


    private boolean isFirstShow = true;

    @Override
    protected void onRobotServiceConnected() {
        super.onRobotServiceConnected();
        setSpeakManager();
        setHardWareManager();

        if (isFirstShow) {
            new Handler().postDelayed(new Runnable() {
                @Override
                public void run() {
                    speakAndCheckComplete("嗨，我还是一个多才多艺的机器人，请问您需要听我唱歌还是看我跳舞呢？", new SpeakComplete() {
                        @Override
                        public void biz() {
                            speechManagerWakeUp();
                        }
                    });
                }
            }, 10);
            isFirstShow = false;
        }
    }

    private final void setSpeakManager() {
        //设置唤醒，休眠回调
        speechManager.setOnSpeechListener(new WakenListener() {
            @Override
            public void onWakeUp() {
                lab.myWakeup();
            }

            @Override
            public void onSleep() {
                lab.mySleep();
            }

            @Override
            public void onWakeUpStatus(boolean b) {
            }
        });

        //语音监听结果回调
        speechManager.setOnSpeechListener(new BaseRecognizeListener() {
            @Override
            public void voiceRecognizeText(String voiceTXT) {

                if (isSpeaking()) {
                    return;
                }

                //语音监听返回
                if (PinYinUtil.isMatch(voiceTXT, getResources().getStringArray(R.array.app_arr_back))) {
                    finish();
                } else if (PinYinUtil.isMatch(voiceTXT, getResources().getStringArray(R.array.app_arr_song))) {
                    speakAndCheckComplete("好的，请选择你要听的歌曲",()->{
                        ll1.performClick();
                    } );
                } else if (PinYinUtil.isMatch(voiceTXT, getResources().getStringArray(R.array.app_arr_dance))) {
                    speakAndCheckComplete("好的，请选择你要看的舞蹈",()->{
                        ll2.performClick();
                    } );
                }
            }

            @Override
            public void onStartRecognize() {
                super.onStartRecognize();
                lab.myWakeup();
            }

            @Override
            public void onStopRecognize() {
                super.onStopRecognize();
                lab.mySleep();
            }

            @Override
            public boolean onRecognizeResult(@NotNull Grammar grammar) {
                return true;
            }

        });
    }

    private final void setHardWareManager() {
        if (hardWareManager != null) {
            //人脸识别回调
            hdCameraManager.setMediaListener(new FaceRecognizeListener() {
                @Override
                public void recognizeResult(List<FaceRecognizeBean> list) {
                    runOnUiThread(() -> {
                        speechManagerWakeUp();

                    });
                }
            });

        }
    }
}
