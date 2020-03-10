package com.tecsun.robot.nanninig;

import android.content.Intent;
import android.os.Bundle;
import android.view.KeyEvent;
import android.view.View;
import android.widget.TextView;

import com.google.gson.Gson;
import com.sanbot.opensdk.beans.FuncConstant;
import com.sanbot.opensdk.function.beans.FaceRecognizeBean;
import com.sanbot.opensdk.function.beans.speech.Grammar;
import com.sanbot.opensdk.function.unit.HardWareManager;
import com.sanbot.opensdk.function.unit.interfaces.hardware.InfrareListener;
import com.sanbot.opensdk.function.unit.interfaces.hardware.PIRListener;
import com.sanbot.opensdk.function.unit.interfaces.media.FaceRecognizeListener;
import com.sanbot.opensdk.function.unit.interfaces.speech.WakenListener;
import com.tecsun.jc.base.common.BaseConstant;
import com.tecsun.robot.builder.ListeningAnimationBuilder;
import com.tecsun.robot.builder.WelcomeSpeakBuilder;
import com.tecsun.robot.nanning.lib_base.BaseActivity;
import com.tecsun.robot.nanning.lib_base.BaseRecognizeListener;
import com.tecsun.robot.nanning.util.DeviceUtil;
import com.tecsun.robot.nanning.util.ExitUtils;
import com.tecsun.robot.nanning.util.log.LogUtil;
import com.tecsun.robot.nanning.util.pinyin.PinYinUtil;

import org.jetbrains.annotations.NotNull;

import java.util.List;

import static android.view.KeyEvent.KEYCODE_BACK;

/**
 * 主页
 *
 * @author liudongwen
 * @date 2020/02/27
 */
public final class HomePageActivity extends BaseActivity {
    TextView tvFunction01, tvFunction02, tvFunction03, tvFunction04;

    private View ll01, ll02, ll03, ll04;

    private static final String TAG = HomePageActivity.class.getSimpleName();

    private WelcomeSpeakBuilder wsb = new WelcomeSpeakBuilder(this);

    private ListeningAnimationBuilder lab = new ListeningAnimationBuilder(this);

    private HardWareManager hardWareManager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.app_activity_home_page);
        tvFunction01 = findViewById(R.id.tvFunction01);
        tvFunction02 = findViewById(R.id.tvFunction02);
        tvFunction03 = findViewById(R.id.tvFunction03);
        tvFunction04 = findViewById(R.id.tvFunction04);
        ll01 = findViewById(R.id.ll01);
        ll02 = findViewById(R.id.ll02);
        ll03 = findViewById(R.id.ll03);
        ll04 = findViewById(R.id.ll04);
        ll01.setOnClickListener(v ->
                myStartActivity(ConsultationActivity.class)
        );
        ll02.setOnClickListener(v ->
                myStartActivity(Mainactivity.class)
        );
        ll03.setOnClickListener(v ->
        {
//            myStartActivity(PoliciesAndRegulationsActivity.class);
            speechManager.cancelSemanticRequest();
            Intent intent9 = new Intent();
            intent9.setClass(this, HtmlActivity.class);
            intent9.putExtra("title", "政策法规");
            intent9.putExtra("url", BaseConstant.URL_POLICIES_AND_REGULATIONS);
            startActivity(intent9);
        });
        ll04.setOnClickListener(v -> myStartActivity(EntertainmentActivity.class));

        hardWareManager = (HardWareManager) getUnitManager(FuncConstant.HARDWARE_MANAGER);
        setAll();
        initButtonText();

        LogUtil.e(TAG, DeviceUtil.getDeviceInfo());

        registerNetWorkMonitor(null,null);
    }

    @Override
    protected void onRobotServiceConnected() {
        super.onRobotServiceConnected();
        LogUtil.e(TAG, "onRobotServiceConnected()");
        setAll();

    }

    private final void setAll() {
        setSpeakManager();
        setHardWareManager();
    }

    private final void setSpeakManager() {

        //设置唤醒，休眠回调
        speechManager.setOnSpeechListener(new WakenListener() {
            @Override
            public void onWakeUp() {
                LogUtil.e(TAG, "onWakeUp >>>>>>>>>>>>>>>>");
                lab.myWakeup();
            }

            @Override
            public void onSleep() {
                LogUtil.e(TAG, "onSleep >>>>>>>>>>>>>>>> ");
                lab.mySleep();
            }

            @Override
            public void onWakeUpStatus(boolean b) {
                LogUtil.e(TAG, "onWakeUpStatus >>>>>>>>>>>>>>>> = " + b);
            }
        });

        //下面是精简写法;
        speechManager.setOnSpeechListener(new BaseRecognizeListener() {
            @Override
            public void voiceRecognizeText(String text) {
                LogUtil.e(TAG, "voiceRecognizeText = " + text);

                //不需要空闲态和工作态区分了, 从其他页面返回主页面也会主动询问;
//                wsb.setStateWorking();
                if (!isSpeaking()) {
                    skipNext(text);
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
                LogUtil.e("TAG!!!!",">>>>>>>>>>>>>>>>>>>>>>>> onRecognizeResult");
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
                    StringBuilder sb = new StringBuilder();
                    for (FaceRecognizeBean bean : list) {
                        sb.append(new Gson().toJson(bean));
                        sb.append("\n");

                        runOnUiThread(() -> {
                            //识别到人脸, 自动唤醒;
                            speechManagerWakeUp();

                            wsb.setStateWorking();
                            //不需要空闲态和工作态区分了, 从其他页面返回主页面也会主动询问;
//                        if (wsb.getCurrentState() == STATE_FREE) {
                            wsb.delaySpeak(bean);
//                        }
                        });
                    }
                    LogUtil.e(TAG, "人脸识别回调 >>>>>>>>>>>>>>>>" + sb.toString());
                }
            });

            //红外
            hardWareManager.setOnHareWareListener(new PIRListener() {
                @Override
                public void onPIRCheckResult(boolean isCheck, int part) {
//                Log.e("TAGTAG", (part == 1 ? "正面" : "背后") + "PIR被触发");
                }
            });

            //红外距离
            hardWareManager.setOnHareWareListener(new InfrareListener() {
                @Override
                public void infrareDistance(int part, int distance) {
//                    if (distance != 0 && distance <= 30) {
////                    System.out.print("部位" + part + "的距离为" + distance);
//                        LogUtil.e(TAG, "部位" + part + "的距离为" + distance);
//                        if (part == 5 && isKongKim() && distance < 25) {
//                            if (wsb.getCurrentState() == STATE_FREE) {
//                                wsb.delaySpeak(null, () -> {
//                                    speechManagerWakeUp();
//                                });
//                            }
//                        } else if (part == 1 && !isKongKim() && distance < 25) {
//                            if (wsb.getCurrentState() == STATE_FREE) {
//                                wsb.delaySpeak(null, () -> {
//                                    speechManagerWakeUp();
//                                });
//                            }
//                        }
//                    }
                }
            });
        }
    }

    private long exitTime = 0;

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if (keyCode == KEYCODE_BACK && event.getAction() == KeyEvent.ACTION_DOWN) {
            if (System.currentTimeMillis() - exitTime > 3000) {
                exitTime = System.currentTimeMillis();
                showToast(getString(R.string.app_drop_out));
            } else {
                myFinish();
            }
            return true;
        }
        return super.onKeyDown(keyCode, event);
    }

    @Override
    protected void onDestroy() {
        myStopSpeak();
        super.onDestroy();
        ExitUtils.INSTANCE.exit();
    }

    private void skipNext(String voiceTXT) {
//        wsb.setStateWorking();
        if (voiceTXT != null) {
            if (PinYinUtil.isMatch(voiceTXT, getResources().getStringArray(R.array.app_arr_function1_keyword))) {
                speakText(getResources().getString(R.string.app_text_information), () -> ll01.performClick());
            } else if (PinYinUtil.isMatch(voiceTXT, getResources().getStringArray(R.array.app_arr_function2_keyword))) {
                speakText(getResources().getString(R.string.app_text_handle), () -> ll02.performClick());
            } else if (PinYinUtil.isMatch(voiceTXT, getResources().getStringArray(R.array.app_arr_function3_keyword))) {
                speakText(getResources().getString(R.string.app_text_policies), () -> ll03.performClick());
            } else if (PinYinUtil.isMatch(voiceTXT, getResources().getStringArray(R.array.app_arr_function4_keyword))) {
                speakText(getResources().getString(R.string.app_text_recreation), () -> ll04.performClick());
            }
        }
    }

    private void initButtonText() {
        String[] functionTextArr = getResources().getStringArray(R.array.app_arr_function_text);
        tvFunction01.setText(String.format(getResources().getString(R.string.app_text_function01), functionTextArr[0]));
        tvFunction02.setText(String.format(getResources().getString(R.string.app_text_function02), functionTextArr[1]));
        tvFunction03.setText(String.format(getResources().getString(R.string.app_text_function03), functionTextArr[2]));
        tvFunction04.setText(String.format(getResources().getString(R.string.app_text_function04), functionTextArr[3]));
    }

    private void speakText(String str, SpeakComplete listener) {
        speakAndCheckComplete("好的," + str, listener);
    }
}






