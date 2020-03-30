package com.tecsun.robot.fragment;

import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.tecsun.robot.common.Defs;
import com.tecsun.robot.fragment.yanglao.YbbxffmxFragment;
import com.tecsun.robot.fragment.yanglao.YbbxgrjfxxFragment;
import com.tecsun.robot.fragment.yanglao.YbbxjfmxFragment;
import com.tecsun.robot.fragment.yanglao.YblxxxFragment;
import com.tecsun.robot.nanning.widget.SingleClickListener;
import com.tecsun.robot.nanninig.R;
import com.tecsun.robot.utils.IntentUtils;
import com.tecsun.robot.utils.StaticBean;

/**养老保险查询界面*/
public class YangLaoFragment extends BaseFragment implements View.OnClickListener {
    public View mView;


    LinearLayout lin_yl_01,lin_yl_02,lin_yl_03,lin_yl_04,lin_yl_05,lin_yl_06,lin_yl_07,lin_yl_08,lin_yl_09,lin_yl_10;
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
         mView = inflater.inflate(R.layout.layout_ylbx2, container, false);
        lin_yl_01 = (LinearLayout) mView.findViewById(R.id.lin_yl_01);
        lin_yl_02 = (LinearLayout) mView.findViewById(R.id.lin_yl_02);
        lin_yl_03 = (LinearLayout) mView.findViewById(R.id.lin_yl_03);
        lin_yl_04 = (LinearLayout) mView.findViewById(R.id.lin_yl_04);
        lin_yl_05 = (LinearLayout) mView.findViewById(R.id.lin_yl_05);
        lin_yl_06 = (LinearLayout) mView.findViewById(R.id.lin_yl_06);
        lin_yl_07 = (LinearLayout) mView.findViewById(R.id.lin_yl_07);
        lin_yl_08 = (LinearLayout) mView.findViewById(R.id.lin_yl_08);
        lin_yl_09 = (LinearLayout) mView.findViewById(R.id.lin_yl_09);
        lin_yl_10 = (LinearLayout) mView.findViewById(R.id.lin_yl_10);
        lin_yl_01.setOnClickListener(new SingleClickListener() {
            @Override
            public void onSingleClick(View v) {
                if (islogin()){
                    Bundle bundle = new Bundle();
                    Log.d("点击养老保险1","点击养老保险1");
                    bundle.putInt(Defs.OPTION_ID, Defs.CANCEL_REPORT_LOSS);
                    IntentUtils.startActivity(getActivity(),"养老保险>养老保险信息查询",
                            new YblxxxFragment(), bundle);
                }
                else{
                    return;
                }

            }
        });
        lin_yl_02.setOnClickListener(new SingleClickListener() {
            @Override
            public void onSingleClick(View v) {
                if (islogin()){
                    Bundle bundle = new Bundle();
                    bundle.putInt(Defs.OPTION_ID, Defs.CANCEL_REPORT_LOSS);
                    IntentUtils.startActivity(getActivity(),"养老保险>养老保险缴费明细查询",
                            new YbbxjfmxFragment(), bundle);
                }
                else {
                    return;
                }
            }
        });
        lin_yl_03.setOnClickListener(new SingleClickListener() {
            @Override
            public void onSingleClick(View v) {
                if (islogin()){
                    Bundle bundle = new Bundle();
                    bundle.putInt(Defs.OPTION_ID, Defs.CANCEL_REPORT_LOSS);
                    IntentUtils.startActivity(getActivity(),"养老保险>养老待遇发放信息查询",
                            new YbbxffmxFragment(), bundle);
                }
                else {
                    return;
                }
            }
        });
        lin_yl_04.setOnClickListener(this);
        lin_yl_05.setOnClickListener(this);
        lin_yl_06.setOnClickListener(this);
        lin_yl_07.setOnClickListener(this);
        lin_yl_08.setOnClickListener(this);
        lin_yl_09.setOnClickListener(new SingleClickListener() {
            @Override
            public void onSingleClick(View v) {
                if (islogin()){
                    Bundle bundle = new Bundle();
                    bundle.putInt(Defs.OPTION_ID, Defs.CANCEL_REPORT_LOSS);
                    IntentUtils.startActivity(getActivity(),"养老保险>机关事业单位社会保险个人缴费信息查询",
                            new YbbxgrjfxxFragment(), bundle);
                }
                else {
                    return;
                }
            }
        });
        lin_yl_10.setOnClickListener(this);

        return mView;
    }



    @Override
    public void onClick(View view) {


        Bundle bundle = new Bundle();
        switch (view.getId()){


            case R.id.lin_yl_04:
                Toast.makeText(getActivity(),getString(R.string.Toast_Intent),Toast.LENGTH_SHORT).show();

                break;
            case R.id.lin_yl_05:
                Toast.makeText(getActivity(),getString(R.string.Toast_Intent),Toast.LENGTH_SHORT).show();

                break;
            case R.id.lin_yl_06:
                Toast.makeText(getActivity(),getString(R.string.Toast_Intent),Toast.LENGTH_SHORT).show();

                break;
            case R.id.lin_yl_07:
                Toast.makeText(getActivity(),getString(R.string.Toast_Intent),Toast.LENGTH_SHORT).show();

                break;
            case R.id.lin_yl_08:
                Toast.makeText(getActivity(),getString(R.string.Toast_Intent),Toast.LENGTH_SHORT).show();

                break;
            case R.id.lin_yl_09:
                Toast.makeText(getActivity(),getString(R.string.Toast_Intent),Toast.LENGTH_SHORT).show();

                break;
            case R.id.lin_yl_10:
                Toast.makeText(getActivity(),getString(R.string.Toast_Intent),Toast.LENGTH_SHORT).show();

                break;

        }
    }



}
