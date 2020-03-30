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

import com.tecsun.robot.common.Defs;
import com.tecsun.robot.fragment.shiye.SybxjfmxFragment;
import com.tecsun.robot.fragment.yanglao.YbbxgrjfxxFragment;
import com.tecsun.robot.nanning.widget.SingleClickListener;
import com.tecsun.robot.nanninig.R;
import com.tecsun.robot.utils.IntentUtils;

/**失业保险查询界面*/
public class ShiYeFragment extends BaseFragment implements View.OnClickListener {

    public View mView;
    LinearLayout lin_sy_01,lin_sy_02;
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
         mView = inflater.inflate(R.layout.layout_sybx, container, false);
        lin_sy_01 = (LinearLayout) mView.findViewById(R.id.lin_sy_01);
        lin_sy_02 = (LinearLayout) mView.findViewById(R.id.lin_sy_02);
        lin_sy_01.setOnClickListener(this);
        lin_sy_02.setOnClickListener(new SingleClickListener() {
            @Override
            public void onSingleClick(View v) {
                if (islogin()){
                    Bundle bundle = new Bundle();
                    bundle.putInt(Defs.OPTION_ID, Defs.CANCEL_REPORT_LOSS);
                    IntentUtils.startActivity(getActivity(),"养老保险>失业保险缴费明细查询",
                            new SybxjfmxFragment(), bundle);
                }
                else {
                    return;
                }
            }
        });

        return mView;
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.lin_sy_01:
                Toast.makeText(getActivity(),getString(R.string.Toast_Intent),Toast.LENGTH_SHORT).show();

                break;

        }
    }


}
