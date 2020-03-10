package com.tecsun.robot.builder;

import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;

import com.tecsun.robot.nanning.builder.BaseBuilder;
import com.tecsun.robot.nanning.builder.BuilderLifeCycleObserver;
import com.tecsun.robot.nanning.lib_base.BaseActivity;
import com.tecsun.robot.nanninig.R;

/**
 * 动画处理
 *
 * @author liudongwen
 * @date 2020/03/02
 */
public class ListeningAnimationBuilder extends BaseBuilder {

    private ImageView ivListenButton1, ivListenButton2, ivRobot1, ivRobot2;
    private LinearLayout llListen;
    private static final String TAG = ListeningAnimationBuilder.class.getSimpleName();
    private BaseActivity baseActivity;

    public ListeningAnimationBuilder(BaseActivity baseActivity) {
        this.baseActivity = baseActivity;
        baseActivity.getLifecycle().addObserver(new BuilderLifeCycleObserver(this));

    }

    @Override
    protected void onCreate() {
        Log.e(TAG, "onCreate()");

        ivListenButton1 = baseActivity.findViewById(R.id.ivListenButton1);
        ivListenButton2 = baseActivity.findViewById(R.id.ivListenButton2);
        llListen = baseActivity.findViewById(R.id.llListen);
        ivRobot1 = baseActivity.findViewById(R.id.ivRobot1);
        ivRobot2 = baseActivity.findViewById(R.id.ivRobot2);


        View flListener = baseActivity.findViewById(R.id.flListener);
        if (flListener != null) {

            flListener.setOnClickListener(v -> {
                        //停止机器人说话
                        baseActivity.myStopSpeak();

                        baseActivity.speechManagerWakeUp();
                        Log.e("TAGTAGTAG", "baseActivity.speechManagerWakeUp()");
                    }
            );
        }
        View flListenerLogo = baseActivity.findViewById(R.id.flListenerLogo);
        if (flListenerLogo != null) {
            flListenerLogo.setOnClickListener(v -> {
                        //停止机器人说话
                        baseActivity.myStopSpeak();
                        baseActivity.speechManagerWakeUp();
            });
        }


        //显示或隐藏动画
        if (baseActivity.isRobotServiceConnected()) {
            myWakeup();
        } else {
            mySleep();
        }
    }

    @Override
    protected void onStart() {
        Log.e(TAG, "onStart()");
    }

    @Override
    protected void onResume() {
    }

    @Override
    protected void onPause() {
        Log.e(TAG, "onPause()");
    }

    @Override
    protected void onDestroy() {
        Log.e(TAG, "onDestroy()");
        super.onDestroy();
    }

    public final void myWakeup() {
        baseActivity.runOnUiThread(() -> {
            Log.e(TAG, "onWakeup()");
            if (ivListenButton1 != null) {
                ivListenButton1.setVisibility(View.GONE);
            }
            if (ivRobot1 != null) {
                ivRobot1.setVisibility(View.GONE);
            }
            if (llListen != null) {
                llListen.setVisibility(View.GONE);
            }

            if (ivListenButton2 != null) {
                ivListenButton2.setVisibility(View.VISIBLE);
            }
            if (ivRobot2 != null) {
                ivRobot2.setVisibility(View.VISIBLE);
            }

        });
    }

    public final void mySleep() {
        baseActivity.runOnUiThread(() -> {
            Log.e(TAG, "onSleep()");
            if (ivListenButton1 != null) {
                ivListenButton1.setVisibility(View.VISIBLE);
            }
            if (ivRobot1 != null) {
                ivRobot1.setVisibility(View.VISIBLE);
            }
            if (llListen != null) {
                llListen.setVisibility(View.VISIBLE);
            }

            if (ivListenButton2 != null) {
                ivListenButton2.setVisibility(View.GONE);
            }
            if (ivRobot2 != null) {
                ivRobot2.setVisibility(View.GONE);
            }
        });
    }
}






















