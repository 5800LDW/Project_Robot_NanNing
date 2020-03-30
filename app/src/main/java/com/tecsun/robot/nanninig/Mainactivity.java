package com.tecsun.robot.nanninig;

import android.os.Bundle;
import android.os.CountDownTimer;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.Nullable;

import com.tecsun.robot.MyBaseActivity;
import com.tecsun.robot.common.Defs;
import com.tecsun.robot.fragment.LoginFragment;
import com.tecsun.robot.fragment.SSCardManageFragment;
import com.tecsun.robot.fragment.SScardQueryFragment;
import com.tecsun.robot.nanning.widget.SingleClickListener;
import com.tecsun.robot.utils.IntentUtils;
import com.tecsun.robot.utils.StaticBean;

public class Mainactivity extends MyBaseActivity {
    LinearLayout lin_login;
    TextView tv_name,tv_login;
    Button btn_sbkxxcx;
    Button btn_sscard_manager;
    Button btn_ldjy,btn_sbyw,btn_qtyw;
    LinearLayout lin_grjb,lin_ylzh,lin_ywbl,lin_cbjf,lin_back;


    SSCardManageFragment ssCardManageActivity = new SSCardManageFragment();
    SScardQueryFragment sScardQueryActivity= new SScardQueryFragment();
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main2);

//            StaticBean.name="徐树仁";
//            StaticBean.idcard="360734199403150017";
        lin_login = (LinearLayout) findViewById(R.id.lin_login);
        btn_sbkxxcx = (Button) findViewById(R.id.btn_sbkxxcx);
        tv_name = (TextView) findViewById(R.id.tv_name);
        tv_login = (TextView) findViewById(R.id.tv_login);
        btn_ldjy = (Button) findViewById(R.id.btn_ldjy);
        btn_sbyw = (Button) findViewById(R.id.btn_sbyw);
        btn_qtyw = (Button) findViewById(R.id.btn_qtyw);
        lin_grjb = (LinearLayout) findViewById(R.id.lin_grjb);
        lin_ylzh = (LinearLayout) findViewById(R.id.lin_ylzh);
        lin_ywbl = (LinearLayout) findViewById(R.id.lin_ywbl);
        lin_cbjf = (LinearLayout) findViewById(R.id.lin_cbjf);
        lin_back = (LinearLayout) findViewById(R.id.lin_back);
        SingleClickListener.setTimeInterval(500L);
        lin_back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                myFinish();
            }
        });
        btn_ldjy.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Toast.makeText(Mainactivity.this,getString(R.string.Toast_Intent),Toast.LENGTH_SHORT).show();
            }
        });
        btn_sbyw.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Toast.makeText(Mainactivity.this,getString(R.string.Toast_Intent),Toast.LENGTH_SHORT).show();
            }
        });
        btn_qtyw.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Toast.makeText(Mainactivity.this,getString(R.string.Toast_Intent),Toast.LENGTH_SHORT).show();
            }
        });
        lin_grjb.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Toast.makeText(Mainactivity.this,getString(R.string.Toast_Intent),Toast.LENGTH_SHORT).show();
            }
        });
        lin_ylzh.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Toast.makeText(Mainactivity.this,getString(R.string.Toast_Intent),Toast.LENGTH_SHORT).show();
            }
        });
        lin_ywbl.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Toast.makeText(Mainactivity.this,getString(R.string.Toast_Intent),Toast.LENGTH_SHORT).show();
            }
        });
        lin_cbjf.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Toast.makeText(Mainactivity.this,getString(R.string.Toast_Intent),Toast.LENGTH_SHORT).show();
            }
        });

        btn_sscard_manager = (Button) findViewById(R.id.btn_sscard_manager);
        lin_login.setOnClickListener(new SingleClickListener() {
            @Override
            public void onSingleClick(View v) {
                login();
            }
        });
        //社保卡信息查询
        btn_sbkxxcx.setOnClickListener(new SingleClickListener() {
            @Override
            public void onSingleClick(View v) {
                Bundle bundle02 = new Bundle();
                bundle02.putInt(Defs.OPTION_ID, Defs.CANCEL_REPORT_LOSS);
                IntentUtils.startActivity(Mainactivity.this,"",
                        sScardQueryActivity, bundle02);
            }
        });
        //社保卡管理
        btn_sscard_manager.setOnClickListener(new SingleClickListener() {
            @Override
            public void onSingleClick(View v) {

                Bundle bundle02 = new Bundle();
                bundle02.putInt(Defs.OPTION_ID, Defs.CANCEL_REPORT_LOSS);
                IntentUtils.startActivity(Mainactivity.this,"",
                        ssCardManageActivity, bundle02);
            }
        });
    }
    @Override
    protected void onResume() {
        super.onResume();
        tv_name.setText(TextUtils.isEmpty(StaticBean.name)?"":"欢迎您，"+StaticBean.name);
        if (!TextUtils.isEmpty(StaticBean.name)){
            tv_login.setText(getString(R.string.app_text_exitlogin));
        }
        else{
            tv_login.setText(getString(R.string.app_text_login));
        }
        StartTime();
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
//            tv_name.setText("剩余时间："+millisUntilFinished / 1000+" 秒后退出");
            Log.d("定时器",millisUntilFinished / 1000 + "");
        }

        @Override
        public void onFinish() {
            myFinish();
        }
    };

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
//
//    private final void setSpeakManager() {
//        speechManager.setOnSpeechListener(new BaseRecognizeListener() {
//            @Override
//            public void voiceRecognizeText(String voiceTXT) {
//                if (PinYinUtil.isMatch(voiceTXT, getResources().getStringArray(R.array.app_arr_back))) {
//                    finish();
//                }
//                /**登录*/
//                if (PinYinUtil.isMatch(voiceTXT, getResources().getStringArray(R.array.app_login_back))) {
//                    login();
//                }
//            }
//        });
//    }

    public void login(){
        if (TextUtils.isEmpty(StaticBean.name)){ Bundle bundle01 = new Bundle();
            bundle01.putInt(Defs.OPTION_ID, Defs.CANCEL_REPORT_LOSS);
            IntentUtils.startActivity(Mainactivity.this,"",
                    new LoginFragment(), bundle01);
        }
        else{
            StaticBean.clear();
            tv_login.setText(getString(R.string.app_text_login));
            tv_name.setText(StaticBean.name);
        }

    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        StaticBean.clear();
//        SingleClickListener.setTimeInterval(500L);
    }
}
