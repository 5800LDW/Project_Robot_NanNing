package com.tecsun.robot.nanninig;

import android.content.Context;
import android.os.Handler;

import com.tecsun.robot.nanning.lib_base.BaseActivity;

public abstract class BaseActivity2 extends BaseActivity {

    private Context mContext;

    private boolean isFirstShow = true;

    public final void firstShowSpeakText(String str) {
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                speechManagerSleep();
                new Handler().post(new Runnable() {
                    @Override
                    public void run() {
                        speakAndCheckComplete(str, new SpeakComplete() {
                            @Override
                            public void biz() {
                                speechManagerWakeUp();
                                onRobotServiceConnected();
                            }
                        });
                    }
                });

            }
        }, 1000);
    }

    @Override
    protected void onResume() {
        mContext = this;
        super.onResume();
        if (isFirstShow) {
            if (mContext instanceof EntertainmentActivity) {
                firstShowSpeakText("嗨，我还是一个多才多艺的机器人，请问您需要听我唱歌还是看我跳舞呢？");
            } else if (mContext instanceof ConsultationActivity) {
                firstShowSpeakText("您好，请问您要咨询什么问题？您可以对我说“养老金怎么申领”或者点击屏幕向我提问。");
            } else if (mContext instanceof HtmlActivity) {
                firstShowSpeakText("请点击屏幕选择需要查询的政策法规");
            }
            isFirstShow = false;
        }

    }

    @Override
    protected void onRobotServiceConnected() {
        super.onRobotServiceConnected();
        if (!isFirstShow) {
            mySetSpeakManager();
            mySetHardWareManager();
        }
    }

    protected abstract void mySetSpeakManager();

    protected void mySetHardWareManager() {
    }

}
































