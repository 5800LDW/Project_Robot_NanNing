package com.tecsun.robot.fragment;

import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import com.tecsun.robot.nanninig.R;

/**
 * 社保卡查询界面
 * */
public class SScardQueryFragment extends Fragment {
    Button btn_ylbx2,btn_gsbx,btn_sybx;
    LinearLayout lin_yl01_01;
    LinearLayout lin_yl_01,lin_yl_02;
    TextView name;
//    YiliaoFragment yiliaoFragment= new YiliaoFragment();
//    YangLaoFragment yangLaoFragment= new YangLaoFragment();
//    GongshangFragment gongshangFragment= new GongshangFragment();
//    ShiYeFragment shiYeFragment= new ShiYeFragment();
    View mView;
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        mView = inflater.inflate(R.layout.activity_ssquery, container, false);

        name = (TextView) mView.findViewById(R.id.name);

//        btn_ylbx = (Button) mView.findViewById(R.id.btn_ylbx);
        btn_ylbx2 = (Button) mView.findViewById(R.id.btn_ylbx2);
        btn_gsbx = (Button) mView.findViewById(R.id.btn_gsbx);
        btn_sybx = (Button) mView.findViewById(R.id.btn_sybx);

        //默认养老保险
        changeFragment(new YangLaoFragment
                ());
//        //医疗保险
//        btn_ylbx.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//
//                changeFragment(new YiliaoFragment());
//                Log.d("输出点击","输出点击");
//                btn_ylbx.setTextColor(getResources().getColor(R.color.white));
//                btn_ylbx2.setTextColor(getResources().getColor(R.color.c_black_1));
//                btn_gsbx.setTextColor(getResources().getColor(R.color.c_black_1));
//                btn_sybx.setTextColor(getResources().getColor(R.color.c_black_1));
//
//                btn_ylbx.setBackground(getResources().getDrawable(R.mipmap.btn_dianji));
//                btn_ylbx2.setBackground(getResources().getDrawable(R.mipmap.btn_moren));
//                btn_gsbx.setBackground(getResources().getDrawable(R.mipmap.btn_moren));
//                btn_sybx.setBackground(getResources().getDrawable(R.mipmap.btn_moren));
//            }
//        });
        //养老保险
        btn_ylbx2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                changeFragment(new YangLaoFragment());

//                btn_ylbx.setTextColor(getResources().getColor(R.color.c_black_1));
                btn_ylbx2.setTextColor(getResources().getColor(R.color.white));
                btn_gsbx.setTextColor(getResources().getColor(R.color.c_black_1));
                btn_sybx.setTextColor(getResources().getColor(R.color.c_black_1));

//                btn_ylbx.setBackground(getResources().getDrawable(R.mipmap.btn_moren));
                btn_ylbx2.setBackground(getResources().getDrawable(R.mipmap.btn_dianji));
                btn_gsbx.setBackground(getResources().getDrawable(R.mipmap.btn_moren));
                btn_sybx.setBackground(getResources().getDrawable(R.mipmap.btn_moren));
            }
        });
        //工伤保险
        btn_gsbx.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                changeFragment(new GongshangFragment());

//                btn_ylbx.setTextColor(getResources().getColor(R.color.c_black_1));
                btn_ylbx2.setTextColor(getResources().getColor(R.color.c_black_1));
                btn_gsbx.setTextColor(getResources().getColor(R.color.white));
                btn_sybx.setTextColor(getResources().getColor(R.color.c_black_1));


//                btn_ylbx.setBackground(getResources().getDrawable(R.mipmap.btn_moren));
                btn_ylbx2.setBackground(getResources().getDrawable(R.mipmap.btn_moren));
                btn_gsbx.setBackground(getResources().getDrawable(R.mipmap.btn_dianji));
                btn_sybx.setBackground(getResources().getDrawable(R.mipmap.btn_moren));
            }
        });
        //失业保险
        btn_sybx.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                changeFragment(new ShiYeFragment());

//                btn_ylbx.setTextColor(getResources().getColor(R.color.c_black_1));
                btn_ylbx2.setTextColor(getResources().getColor(R.color.c_black_1));
                btn_gsbx.setTextColor(getResources().getColor(R.color.c_black_1));
                btn_sybx.setTextColor(getResources().getColor(R.color.white));


//                btn_ylbx.setBackground(getResources().getDrawable(R.mipmap.btn_moren));
                btn_ylbx2.setBackground(getResources().getDrawable(R.mipmap.btn_moren));
                btn_gsbx.setBackground(getResources().getDrawable(R.mipmap.btn_moren));
                btn_sybx.setBackground(getResources().getDrawable(R.mipmap.btn_dianji));
            }
        });

        return mView;
    }

    private void changeFragment(Fragment fragment) {
        //实例化碎片管理器对象
        FragmentManager fm = getActivity().getSupportFragmentManager();
        FragmentTransaction ft = fm.beginTransaction();
        //选择fragment替换的部分
        ft.replace(R.id.cl_content, fragment);
        ft.commitAllowingStateLoss();
    }


}
