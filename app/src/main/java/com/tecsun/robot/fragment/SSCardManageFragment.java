package com.tecsun.robot.fragment;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import com.tecsun.robot.nanninig.R;

/**
 * 社保卡管理-第二层
 * */
public class SSCardManageFragment extends BaseFragment {

    public FragmentTransaction ft;
    FragmentManager fm;

    TextView name;

    View mView;
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        mView = inflater.inflate(R.layout.activity_sscard_manager, container, false);
        name = (TextView) mView.findViewById(R.id.name);
        changeFragment(new SheBaoKaGuanliFragment());
        return mView;
    }


    private void changeFragment(Fragment fragment) {
        fm = getFragmentManager();
        ft = fm.beginTransaction();
        ft.replace(R.id.cl_content, fragment, fragment.getTag());
        ft.commit();
    }



}
