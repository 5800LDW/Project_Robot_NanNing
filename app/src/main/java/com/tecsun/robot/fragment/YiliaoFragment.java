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
import com.tecsun.robot.fragment.yiliao.YblxcxFragment;
import com.tecsun.robot.nanninig.R;
import com.tecsun.robot.utils.IntentUtils;
import com.tecsun.robot.utils.StaticBean;

/**医疗保险查询界面*/
public class YiliaoFragment extends BaseFragment implements View.OnClickListener {
    public View mView;
    LinearLayout lin_yiliao_01,lin_yiliao_02,lin_yiliao_03,lin_yiliao_04,lin_yiliao_05,lin_yiliao_06,lin_yiliao_07,lin_yiliao_08,lin_yiliao_09;
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
         mView = inflater.inflate(R.layout.layout_ylbx, container, false);
        lin_yiliao_01 = (LinearLayout) mView.findViewById(R.id.lin_yiliao_01);
        lin_yiliao_02 = (LinearLayout) mView.findViewById(R.id.lin_yiliao_02);
        lin_yiliao_03 = (LinearLayout) mView.findViewById(R.id.lin_yiliao_03);
        lin_yiliao_04 = (LinearLayout) mView.findViewById(R.id.lin_yiliao_04);
        lin_yiliao_05 = (LinearLayout) mView.findViewById(R.id.lin_yiliao_05);
        lin_yiliao_06 = (LinearLayout) mView.findViewById(R.id.lin_yiliao_06);
        lin_yiliao_07 = (LinearLayout) mView.findViewById(R.id.lin_yiliao_07);
        lin_yiliao_08 = (LinearLayout) mView.findViewById(R.id.lin_yiliao_08);
        lin_yiliao_09 = (LinearLayout) mView.findViewById(R.id.lin_yiliao_09);
        lin_yiliao_01.setOnClickListener(this);
        lin_yiliao_02.setOnClickListener(this);
        lin_yiliao_03.setOnClickListener(this);
        lin_yiliao_04.setOnClickListener(this);
        lin_yiliao_05.setOnClickListener(this);
        lin_yiliao_06.setOnClickListener(this);
        lin_yiliao_07.setOnClickListener(this);
        lin_yiliao_08.setOnClickListener(this);
        lin_yiliao_09.setOnClickListener(this);
        return mView;
    }
    public boolean islogin(){
        if (TextUtils.isEmpty(StaticBean.idcard)) {

            Log.d("登录","登录");
            Bundle bundle01 = new Bundle();
            bundle01.putInt(Defs.OPTION_ID, Defs.CANCEL_REPORT_LOSS);
            IntentUtils.startActivity(getActivity(),"登录标题",
                    new LoginFragment(), bundle01);
//            startActivity(new Intent(getActivity(), LoginFragment.class));
            return false;
        }
        return true;
    }
    @Override
    public void onClick(View view) {

        Bundle bundle01 = new Bundle();
        switch (view.getId()) {

            case R.id.lin_yiliao_01:
                if (islogin()){
                    bundle01.putInt(Defs.OPTION_ID, Defs.CANCEL_REPORT_LOSS);
                    IntentUtils.startActivity(getActivity(),"医疗保险>医疗信息查询",
                            new YblxcxFragment(), bundle01);
                }
                else {
                return;
                }

                break;
            case R.id.lin_yiliao_02:
//                bundle01.putInt(Defs.OPTION_ID, Defs.CANCEL_REPORT_LOSS);
//                IntentUtils.startActivity(getActivity(),"医疗保险>医保待遇支付信息查询",
//                        new YbdyzfxxFragment(), bundle01);

                Toast.makeText(getActivity(),getString(R.string.Toast_Intent),Toast.LENGTH_SHORT).show();

                break;
            case R.id.lin_yiliao_03:
                Toast.makeText(getActivity(),getString(R.string.Toast_Intent),Toast.LENGTH_SHORT).show();

                break;
            case R.id.lin_yiliao_04:
                Toast.makeText(getActivity(),getString(R.string.Toast_Intent),Toast.LENGTH_SHORT).show();

                break;
            case R.id.lin_yiliao_05:
                Toast.makeText(getActivity(),getString(R.string.Toast_Intent),Toast.LENGTH_SHORT).show();

                break;
            case R.id.lin_yiliao_06:
                Toast.makeText(getActivity(),getString(R.string.Toast_Intent),Toast.LENGTH_SHORT).show();

                break;
            case R.id.lin_yiliao_07:
                Toast.makeText(getActivity(),getString(R.string.Toast_Intent),Toast.LENGTH_SHORT).show();

                break;
            case R.id.lin_yiliao_08:
                Toast.makeText(getActivity(),getString(R.string.Toast_Intent),Toast.LENGTH_SHORT).show();

                break;
            case R.id.lin_yiliao_09:
                Toast.makeText(getActivity(),getString(R.string.Toast_Intent),Toast.LENGTH_SHORT).show();

                break;

        }
    }



}
