package com.tecsun.robot;

import com.sanbot.opensdk.function.unit.interfaces.speech.WakenListener;
import com.tecsun.robot.nanning.lib_base.BaseActivity;
import com.tecsun.robot.nanning.lib_base.BaseRecognizeListener;
import com.tecsun.robot.nanning.util.log.LogUtil;

public class MyBaseActivity extends BaseActivity {
    private static final String TAG = MyBaseActivity.class.getSimpleName();

    @Override
    protected void onResume() {
        super.onResume();
        myStopSpeak();
    }


    @Override
    protected void onRobotServiceConnected() {
        setSpeakManager();
        speechManagerSleep();
    }

    private final void setSpeakManager() {
        //设置唤醒，休眠回调
        speechManager.setOnSpeechListener(new WakenListener() {
            @Override
            public void onWakeUp() {
                LogUtil.i(TAG, "onWakeUp()");
                speechManagerSleep();
            }

            @Override
            public void onSleep() {
                LogUtil.i(TAG, "onSleep()");
//                speechManagerWakeUp();
            }

            @Override
            public void onWakeUpStatus(boolean b) {
                LogUtil.i(TAG, "onWakeUpStatus = " + b);
            }
        });

        //监听到"返回"就自动结束当前Activity
        speechManager.setOnSpeechListener(new BaseRecognizeListener() {
            @Override
            public void voiceRecognizeText(String voiceTXT) {

                myStopSpeak();
                speechManagerSleep();

//                if (PinYinUtil.isMatch(voiceTXT, getResources().getStringArray(R.array.app_arr_back))) {
//                    finish();
//                }
                LogUtil.i(TAG, "voiceRecognizeText = " + voiceTXT);
            }
        });
    }

}
