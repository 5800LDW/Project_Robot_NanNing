package com.tecsun.robot.fragment;

import android.util.Log;
import android.view.View;

import androidx.fragment.app.Fragment;

import com.tecsun.robot.dance.ActivityManager;
import com.tecsun.robot.fragment.dialog.WarningDialogFragment;
import com.tecsun.robot.nanninig.GenericActivity;

/**
 * 父类的fragment
 * @author tmy
 *
 */
public abstract class BaseFragment extends Fragment {

    protected WarningDialogFragment warningDialogFragment;
    protected WarningDialogFragment warningIconDialog ;

    @Override
    public void onHiddenChanged(boolean hidden) {
        // TODO Auto-generated method stub
        super.onHiddenChanged(hidden);


    }

    /**
     * 警告提示弹框，默认关闭界面
     */
    protected void showWarningDialog(Object tipContent) {
        showWarningDialog(tipContent, onClickListenerFinish, false);
    }

    /**
     * 警告提示弹框
     */
    protected void showWarningDialog(Object tipContent, View.OnClickListener clickListener) {
        showWarningDialog(tipContent, clickListener, false);
    }

    protected void showWarningDialog(Object tipContent,Object btnContent, View.OnClickListener clickListener) {
        showWarningDialog(tipContent,btnContent,clickListener,false);
    }

    protected void showWarningDialog(Object tipContent, View.OnClickListener clickListener, boolean isHtml) {
        try {
            if (getActivity() != null && !getActivity().isFinishing()) {
                warningDialogFragment = new WarningDialogFragment();
                warningDialogFragment.setHtml(isHtml);
                warningDialogFragment.setWarningContentObject(tipContent);
                warningDialogFragment.setOnClickListener(clickListener);
                Log.d("弹出框111","弹出框111");

                warningDialogFragment.show(getActivity().getSupportFragmentManager(), null);


            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    protected void showWarningDialog(Object tipContent,Object btnContent, View.OnClickListener clickListener, boolean isHtml) {
        try {
            if (getActivity() != null && !getActivity().isFinishing()) {
                warningDialogFragment = new WarningDialogFragment();
                warningDialogFragment.setHtml(isHtml);
                warningDialogFragment.setWarningContentObject(tipContent);
                warningDialogFragment.setWarningBtnObject(btnContent);
                warningDialogFragment.setOnClickListener(clickListener);
                warningDialogFragment.show(getActivity().getSupportFragmentManager(), null);

            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    protected void showWarningIconDialog(Object tipContent,Object btnContent,int warningIcon , View.OnClickListener clickListener) {
        try {
            if (getActivity() != null && !getActivity().isFinishing()) {
                warningIconDialog= new WarningDialogFragment();
                warningIconDialog.setHtml(false);
                warningIconDialog.setWarningContentObject(tipContent);
                warningIconDialog.setWarningBtnObject(btnContent);
                warningIconDialog.setWarningIcon(warningIcon);
                warningIconDialog.setOnClickListener(clickListener);
                warningIconDialog.show(getActivity().getSupportFragmentManager(), null);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void dismissIconDialog(){
        if (getActivity() != null && !getActivity().isFinishing()){
            if (warningIconDialog != null){
                warningIconDialog.dismissDialog();
            }
        }
    }

    public View.OnClickListener onClickListenerFinish = new View.OnClickListener() {
        @Override
        public void onClick(View view) {
            getActivity().finish();
        }
    };

    public View.OnClickListener onClickListenerCancel = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            if (warningDialogFragment != null){
                warningDialogFragment.dismissDialog();
            }
        }
    };

    public View.OnClickListener onClickListenerFinishAll = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            ActivityManager.getInstance().exit();
        }
    };

    @Override
    public void onDestroy() {
        super.onDestroy();
    }
}
