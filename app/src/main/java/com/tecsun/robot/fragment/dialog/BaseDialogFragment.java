package com.tecsun.robot.fragment.dialog;

import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.os.Bundle;
import android.util.Log;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.DialogFragment;

import com.tecsun.robot.nanninig.R;

/**
 * DialogFragment基类
 * Created by _Smile on 2016/6/1.
 */
public abstract class BaseDialogFragment extends DialogFragment implements View.OnClickListener {

    protected Dialog dialog;
    protected boolean mCanceledOnTouchOutside = false;

    @NonNull
    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        getActivity().getWindow().setBackgroundDrawableResource(android.R.color.transparent);
        dialog = new Dialog(getActivity(), R.style.style_dialog);
        initDialogView();
        dialog.setCancelable(mCanceledOnTouchOutside);
        dialog.setCanceledOnTouchOutside(mCanceledOnTouchOutside);
        Log.d("dialog","dialogxxx");
//      warningDialog.show(getSupportFragmentManager(), null);

        return dialog;
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);

    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        onKeyBackListener();

        return super.onCreateView(inflater, container, savedInstanceState);
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
    }

    /**
     * 初始化Dialog视图
     */
    protected abstract void initDialogView();

    /**
     * 监听返回按钮监听
     */
    private void onKeyBackListener() {
        this.getDialog().setOnKeyListener(new DialogInterface.OnKeyListener() {
            @Override
            public boolean onKey(DialogInterface dialog, int keyCode, KeyEvent event) {
                return keyCode == KeyEvent.KEYCODE_BACK;
            }
        });
    }

    @Override
    public void onClick(View view) {
        
    }

}
