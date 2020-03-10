package com.tecsun.robot.fragment;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.Button;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.tecsun.robot.bean.evenbus.IdCardBean;
import com.tecsun.robot.bean.evenbus.IdCardBean2;
import com.tecsun.robot.bean.evenbus.IsStartTimerBean;
import com.tecsun.robot.nanninig.R;
import com.tecsun.robot.nanninig.ReadIdcardActivity;
import com.tecsun.robot.nanninig.ReadQrActivity;
import com.tecsun.robot.nanninig.ReadSScardActivity;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

public class LoginFragment extends BaseFragment {

    Button btn_sfz,btn_sbk,btn_ewm;

    View mView;
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        mView = inflater.inflate(R.layout.activity_login, container, false);
        EventBus.getDefault().register(this);
        btn_sfz = (Button) mView.findViewById(R.id.btn_sfz);
        btn_sbk = (Button) mView.findViewById(R.id.btn_sbk);
        btn_ewm = (Button) mView.findViewById(R.id.btn_ewm);


        btn_sfz.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View view) {

                StopTimer();
                Intent intent=new Intent(getActivity(),ReadIdcardActivity.class);
                startActivity(intent);
            }
        });
        btn_sbk.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View view) {

                StopTimer();
                Intent intent=new Intent(getActivity(),ReadSScardActivity.class);
                startActivity(intent);
            }
        });

        btn_ewm.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View view) {

                StopTimer();
                Intent intent=new Intent(getActivity(),ReadQrActivity.class);
                startActivity(intent);
//
//                Toast.makeText(getActivity(),getString(R.string.Toast_Intent),Toast.LENGTH_SHORT).show();

            }
        });

        return mView;
    }



    public void StopTimer(){
        EventBus.getDefault().post(new IsStartTimerBean(0));
    }

    //接收事件
    @Subscribe(threadMode = ThreadMode.MAIN)
    public void IdCardBean(IdCardBean idCardBean){

        //延迟1秒让他们看到界面
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                if (getActivity() != null &&!getActivity().isFinishing()){
                    getActivity().finish();
                }

            }
        },500);


        Log.d("收到通知1",idCardBean.getName()+"");
    }

    //接收事件
    @Subscribe(threadMode = ThreadMode.MAIN)
    public void IdCardBean2(IdCardBean2 idCardBean){
        Log.d("收到通知2",idCardBean.getName()+"");
        //延迟1秒让他们看到界面
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
               getActivity().finish();
            }
        },500);

    }


    @Override
    public void onDestroy() {
        super.onDestroy();
        if (EventBus.getDefault().isRegistered(this)) {
            EventBus.getDefault().unregister(this);
        }
    }
}