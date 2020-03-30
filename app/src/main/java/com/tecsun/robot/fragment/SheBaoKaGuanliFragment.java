package com.tecsun.robot.fragment;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.tecsun.robot.common.Defs;
import com.tecsun.robot.nanning.widget.SingleClickListener;
import com.tecsun.robot.nanninig.R;
import com.tecsun.robot.utils.IntentUtils;

/**社保卡管理*/
public class SheBaoKaGuanliFragment extends BaseFragment {
    public View mView;
    LinearLayout lin_sbk_01,lin_sbk_02,lin_sbk_03,lin_sbk_04,lin_sbk_05,lin_sbk_06;
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
         mView = inflater.inflate(R.layout.layout_sbkgx, container, false);
         initview(mView);
        return mView;
    }

    public void initview(View mView){
        lin_sbk_01 = (LinearLayout) mView.findViewById(R.id.lin_sbk_01);
        lin_sbk_02 = (LinearLayout) mView.findViewById(R.id.lin_sbk_02);
        lin_sbk_03 = (LinearLayout) mView.findViewById(R.id.lin_sbk_03);
        lin_sbk_04 = (LinearLayout) mView.findViewById(R.id.lin_sbk_04);
        lin_sbk_05 = (LinearLayout) mView.findViewById(R.id.lin_sbk_05);
        lin_sbk_06 = (LinearLayout) mView.findViewById(R.id.lin_sbk_06);
        lin_sbk_01.setOnClickListener(new SingleClickListener() {
            @Override
            public void onSingleClick(View v) {
                Toast.makeText(getActivity(),getString(R.string.Toast_Intent),Toast.LENGTH_SHORT).show();
            }
        });
        lin_sbk_02.setOnClickListener(new SingleClickListener() {
            @Override
            public void onSingleClick(View v) {
                Toast.makeText(getActivity(),getString(R.string.Toast_Intent),Toast.LENGTH_SHORT).show();
            }
        });
        lin_sbk_03.setOnClickListener(new SingleClickListener() {
            @Override
            public void onSingleClick(View v) {
                Toast.makeText(getActivity(),getString(R.string.Toast_Intent),Toast.LENGTH_SHORT).show();
            }
        });
        lin_sbk_04.setOnClickListener(new SingleClickListener() {
            @Override
            public void onSingleClick(View v) {
                Toast.makeText(getActivity(),getString(R.string.Toast_Intent),Toast.LENGTH_SHORT).show();
            }
        });
        lin_sbk_05.setOnClickListener(new SingleClickListener() {
            @Override
            public void onSingleClick(View v) {
                Bundle bundle = new Bundle();
                bundle.putInt(Defs.OPTION_ID, Defs.CHANGE_SSCARD_PASSWORD);
                bundle.putString(Defs.OPTION_NAME, "修改社保卡密码");
                IntentUtils.startActivity(getActivity(),"修改社保卡密码",
                        new ReadSSCardFragment(), bundle);
            }
        });
        lin_sbk_06.setOnClickListener(new SingleClickListener() {
            @Override
            public void onSingleClick(View v) {
                Toast.makeText(getActivity(),getString(R.string.Toast_Intent),Toast.LENGTH_SHORT).show();
            }
        });
    }


//    @Override
//    public void onClick(View view) {
//        switch (view.getId()) {
//            case R.id.lin_sbk_01:
//                Toast.makeText(getActivity(),getString(R.string.Toast_Intent),Toast.LENGTH_SHORT).show();
//
//                break;
//            case R.id.lin_sbk_02:
//                Toast.makeText(getActivity(),getString(R.string.Toast_Intent),Toast.LENGTH_SHORT).show();
//
//                break;
//            case R.id.lin_sbk_03:
//                Toast.makeText(getActivity(),getString(R.string.Toast_Intent),Toast.LENGTH_SHORT).show();
//
//                break;
//            case R.id.lin_sbk_04:
//                Toast.makeText(getActivity(),getString(R.string.Toast_Intent),Toast.LENGTH_SHORT).show();
//
//                break;
//            case R.id.lin_sbk_05:
//                Bundle bundle = new Bundle();
//                bundle.putInt(Defs.OPTION_ID, Defs.CHANGE_SSCARD_PASSWORD);
//                bundle.putString(Defs.OPTION_NAME, "修改社保卡密码");
//                IntentUtils.startActivity(getActivity(),"修改社保卡密码",
//                        new ReadSSCardFragment(), bundle);
//                break;
//            case R.id.lin_sbk_06:
//                Toast.makeText(getActivity(),getString(R.string.Toast_Intent),Toast.LENGTH_SHORT).show();
//                break;
//        }
//    }



}
