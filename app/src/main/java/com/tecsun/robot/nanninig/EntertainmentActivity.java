package com.tecsun.robot.nanninig;

import android.Manifest;
import android.os.Bundle;
import android.os.Handler;
import android.view.View;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.example.xukefeng.musicplayer.MusicListActivity;
import com.sanbot.opensdk.function.beans.speech.Grammar;
import com.sanbot.opensdk.function.unit.interfaces.speech.WakenListener;
import com.tecsun.lib_videoplayer2.ui.SimpleListVideoActivityModeRV;
import com.tecsun.robot.builder.ListeningAnimationBuilder;
import com.tecsun.robot.dance.DanceMusicListActivity;
import com.tecsun.robot.nanning.lib_base.BaseActivity;
import com.tecsun.robot.nanning.lib_base.BaseRecognizeListener;
import com.tecsun.robot.nanning.util.PermissionsUtils;
import com.tecsun.robot.nanning.util.log.LogUtil;
import com.tecsun.robot.nanning.util.pinyin.PinYinUtil;

import org.jetbrains.annotations.NotNull;

public class EntertainmentActivity extends BaseActivity {

    final String[] permissions = {Manifest.permission.WRITE_EXTERNAL_STORAGE};

    /**
     * 监听语音按钮,动画和点击事件
     */
    private ListeningAnimationBuilder lab = new ListeningAnimationBuilder(this);
    final String TAG = EntertainmentActivity.class.getName();
    private View fonction1, fonction2,fonction3;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.app_activity_entertainment);
        findViewById(R.id.appItemPageBack).setOnClickListener(v -> myFinish());

        fonction1 = findViewById(R.id.fonction1);
        fonction1.setOnClickListener(v -> {
            myStopSpeak();
            myStartActivity( MusicListActivity.class);
            myFinish();
        });

        fonction2 = findViewById(R.id.fonction2);
        fonction2.setOnClickListener(v -> {
            myStopSpeak();
            myStartActivity(DanceMusicListActivity.class);
            myFinish();
        });

        fonction3 = findViewById(R.id.fonction3);
        fonction3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                myStopSpeak();
                myStartActivity(SimpleListVideoActivityModeRV.class);
                myFinish();
            }
        });

        registerNetWorkMonitor(null,null);

        boolean hadPermission = PermissionsUtils.startRequestPermission(this, permissions,101);
//        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M && !hadPermission) {
//            String[] permissions = {Manifest.permission.WRITE_EXTERNAL_STORAGE};
//            requestPermissions(permissions, 1110);
//        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        boolean sdPermissionResult = PermissionsUtils.requestPermissionResult(this,permissions,grantResults);
        if (!sdPermissionResult) {
            Toast.makeText(this, "没获取到sd卡权限，无法播放本地视频哦", Toast.LENGTH_LONG).show();
        }
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
                    speakAndCheckComplete("嗨，我还是一个多才多艺的机器人", new SpeakComplete() {
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

                LogUtil.e("TAG!!!!!",voiceTXT);

                if (isSpeaking()) {
                    return;
                }

                //语音监听返回
                if (PinYinUtil.isMatch(voiceTXT, getResources().getStringArray(R.array.app_arr_back))) {
                    myFinish();
                } else if (PinYinUtil.isMatch(voiceTXT, getResources().getStringArray(R.array.app_arr_song))) {
                    speakAndCheckComplete("好的，请选择你要听的歌曲",()->{
                        fonction1.performClick();
                    } );
                } else if (PinYinUtil.isMatch(voiceTXT, getResources().getStringArray(R.array.app_arr_dance))) {
                    speakAndCheckComplete("好的，请选择你要看的舞蹈",()->{
                        fonction2.performClick();
                    } );
                }
                else if (PinYinUtil.isMatch(voiceTXT, getResources().getStringArray(R.array.app_arr_movie))) {
                    speakAndCheckComplete("好的，请选择你要看的视频",()->{
                        fonction3.performClick();
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
//            //人脸识别回调
//            hdCameraManager.setMediaListener(new FaceRecognizeListener() {
//                @Override
//                public void recognizeResult(List<FaceRecognizeBean> list) {
//                    runOnUiThread(() -> {
//                        speechManagerWakeUp();
//
//                    });
//                }
//            });
//

            //关闭白光灯
            hardWareManager.switchWhiteLight(false);
        }
    }
}
