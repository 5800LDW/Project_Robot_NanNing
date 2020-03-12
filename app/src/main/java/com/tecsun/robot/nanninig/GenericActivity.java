package com.tecsun.robot.nanninig;


import android.os.Bundle;
import android.os.CountDownTimer;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.Nullable;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import com.tecsun.robot.MyBaseActivity;
import com.tecsun.robot.bean.evenbus.IsStartTimerBean;
import com.tecsun.robot.common.Defs;
import com.tecsun.robot.dance.ActivityManager;
import com.tecsun.robot.fragment.BaseFragment;
import com.tecsun.robot.fragment.LoginFragment;
import com.tecsun.robot.utils.IntentUtils;
import com.tecsun.robot.utils.StaticBean;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import java.util.ArrayList;
import java.util.List;

/**
 * 公共层
 * */
public class GenericActivity extends MyBaseActivity {
    LinearLayout lin_home,lin_back;
    Button btn_exit;
    TextView name,tv_name;
    public FragmentTransaction ft;
    FragmentManager fm;
    List<BaseFragment> baseFragmentList;//用来存储打开的所有fragment


    /**
     * int型标题内容
     */
    protected Integer intTitleObj;
    /**
     * 标题类型，0：int型；1：char型
     */
    protected int titleType;

    /**
     * char型标题内容
     */
    protected CharSequence charTitleObj;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_three);
        ActivityManager.getInstance().addActivity(this);
        if (!EventBus.getDefault().isRegistered(this)) {
            EventBus.getDefault().register(this);
        }
        btn_exit = (Button) findViewById(R.id.btn_exit);
        lin_home = (LinearLayout) findViewById(R.id.lin_home);
        lin_back = (LinearLayout) findViewById(R.id.lin_back);
        name = (TextView) findViewById(R.id.name);
        tv_name= (TextView) findViewById(R.id.tv_name);
        btn_exit = (Button) findViewById(R.id.btn_exit);

        baseFragmentList = new ArrayList<>();//初始化list

        String str=getIntent().getStringExtra(Defs.KEY_TITLE);

        btn_exit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                if (btn_exit.getText().toString().equals(getString(R.string.app_text_login))){

                    Bundle bundle01 = new Bundle();
                    bundle01.putInt(Defs.OPTION_ID, Defs.CANCEL_REPORT_LOSS);
                    IntentUtils.startActivity(GenericActivity.this,"登录标题",
                            new LoginFragment(), bundle01);
                }
                else{
                    Exit();
                }

            }
        });
        lin_home.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                ActivityManager.getInstance().exit();
            }
        });
        lin_back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                onBackPressed();
            }
        });
        getIntentValues();
    }



    /**
     * 获取Intent传值tv_title_2
     */
    private void getIntentValues() {
        titleType = getIntent().getIntExtra(Defs.KEY_TITLE_TYPE, -1);
        if (titleType == 0) {
            intTitleObj = getIntent().getIntExtra(Defs.KEY_TITLE, 0);
        } else if (titleType == 1) {
            charTitleObj = getIntent().getCharSequenceExtra(Defs.KEY_TITLE);
        }

    }

    protected void initActivityOperate() {
//        if (mContentFragment != null) {
//            FragmentTransaction ft = getSupportFragmentManager().beginTransaction();
//            ft.replace(R.id.fl_activity_content, mContentFragment);
//            ft.commit();
//        }

        if (IntentUtils.mContentFragment != null) {

            Log.d("退出界面","进来替换"+IntentUtils.mContentFragment);
            ft = getSupportFragmentManager().beginTransaction();
            ft.replace(R.id.fl_activity_content, IntentUtils.mContentFragment);
            ft.addToBackStack(null);
            ft.commit();
            String str=IntentUtils.mContentFragment+"";

            if (str.contains("LoginFragment")){
                btn_exit.setText(getString(R.string.app_text_exitlogin));
            }

            IntentUtils.mContentFragment = null;

        }
    }

    //接收事件
    @Subscribe(threadMode = ThreadMode.MAIN)
    public void IsStartTimerBean(IsStartTimerBean bean){
        if (bean.getType()==0){
            StopTime();
        }

    }


    @Override
    protected void onDestroy() {
        super.onDestroy();
        if (EventBus.getDefault().isRegistered(this)) {
            EventBus.getDefault().unregister(this);
        }
        if (timer!=null){
            timer.cancel();
        }
    }

//    private void changeFragment(Fragment fragment) {
//        //实例化碎片管理器对象
//        FragmentManager fm = getSupportFragmentManager();
//        FragmentTransaction ft = fm.beginTransaction();
//        //选择fragment替换的部分
//        ft.replace(R.id.cl_content, fragment);
//        ft.commitAllowingStateLoss();
//    }

    @Override
    protected void onResume() {
        super.onResume();
        StartTime();
        if (!TextUtils.isEmpty(StaticBean.name)){
            name.setText(StaticBean.name+"，您好!");
            btn_exit.setText(getString(R.string.app_text_exitlogin));
        }
        else{
            btn_exit.setText(getString(R.string.app_text_login));
            name.setText(StaticBean.name);
        }

        initActivityOperate();
    }


    /**
     * activity的回退事件
     */
    @Override
    public void onBackPressed() {
        /** 事件：点击返回按钮(rl_title_back) */
        time = 60;
            //检查栈中是否还有fm
            if( getFragmentManager().getBackStackEntryCount() > 1 ){
                getFragmentManager().popBackStack();
            }else {
                myFinish();
            }

    }




    /**
     * 是否启动定时器倒计时
     * */
    public void StartTime(){
            StopTime();
            time=60;
            timer.start();
    }

    public void StopTime(){
        if (timer!=null){
            timer.cancel();
        }
    }

    public static int time = 60;
    CountDownTimer timer = new CountDownTimer(time*1000, 1000) {
        @Override
        public void onTick(long millisUntilFinished) {
            tv_name.setText("剩余时间："+millisUntilFinished / 1000+" 秒后退出");
        }

        @Override
        public void onFinish() {
            Exit();
        }
    };

    /**退出登录*/
    public void Exit(){
        StaticBean.clear();
        ActivityManager.getInstance().exit();
    }
    @Override
    protected void onStop() {
        super.onStop();
        StopTime();
    }

//    @Override
//    protected void onRobotServiceConnected() {
//        super.onRobotServiceConnected();
//        setSpeakManager();
//    }

//    private final void setSpeakManager() {
//        speechManager.setOnSpeechListener(new BaseRecognizeListener() {
//            @Override
//            public void voiceRecognizeText(String voiceTXT) {
//                /**返回**/
//                if (PinYinUtil.isMatch(voiceTXT, getResources().getStringArray(R.array.app_arr_back))) {
//                    onBackPressed();
//                }
//                /**退出登录*/
//                if (PinYinUtil.isMatch(voiceTXT, getResources().getStringArray(R.array.app_exitlogin_back))) {
//                    StaticBean.clear();
//                    ActivityManager.getInstance().exit();
//                }
//                /**首页*/
//                if (PinYinUtil.isMatch(voiceTXT, getResources().getStringArray(R.array.app_index_back))) {
//                    ActivityManager.getInstance().exit();
//                }
//            }
//        });
//    }

}
