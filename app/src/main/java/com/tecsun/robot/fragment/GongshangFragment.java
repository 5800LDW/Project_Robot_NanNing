package com.tecsun.robot.fragment;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.tecsun.robot.nanninig.R;

/**工商保险查询界面*/
public class GongshangFragment extends BaseFragment implements View.OnClickListener {
    public View mView;
    LinearLayout lin_gs_01,lin_gs_02,lin_gs_03,lin_gs_04,lin_gs_05;
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
         mView = inflater.inflate(R.layout.layout_gsbx, container, false);
        lin_gs_01 = (LinearLayout) mView.findViewById(R.id.lin_gs_01);
        lin_gs_02 = (LinearLayout) mView.findViewById(R.id.lin_gs_02);
        lin_gs_03 = (LinearLayout) mView.findViewById(R.id.lin_gs_03);
        lin_gs_04 = (LinearLayout) mView.findViewById(R.id.lin_gs_04);
        lin_gs_05 = (LinearLayout) mView.findViewById(R.id.lin_gs_05);
        lin_gs_01.setOnClickListener(this);
        lin_gs_02.setOnClickListener(this);
        lin_gs_03.setOnClickListener(this);
        lin_gs_04.setOnClickListener(this);
        lin_gs_05.setOnClickListener(this);
        return mView;
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()){
            case R.id.lin_gs_01:
                Toast.makeText(getActivity(),getString(R.string.Toast_Intent),Toast.LENGTH_SHORT).show();
                break;
            case R.id.lin_gs_02:
                Toast.makeText(getActivity(),getString(R.string.Toast_Intent),Toast.LENGTH_SHORT).show();
                break;
            case R.id.lin_gs_03:
                Toast.makeText(getActivity(),getString(R.string.Toast_Intent),Toast.LENGTH_SHORT).show();
                break;
            case R.id.lin_gs_04:
                Toast.makeText(getActivity(),getString(R.string.Toast_Intent),Toast.LENGTH_SHORT).show();
                break;
            case R.id.lin_gs_05:
                Toast.makeText(getActivity(),getString(R.string.Toast_Intent),Toast.LENGTH_SHORT).show();
                break;
        }
    }


}
