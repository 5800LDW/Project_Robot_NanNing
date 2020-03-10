package com.tecsun.robot.fragment.dialog;

import android.text.Html;
import android.view.LayoutInflater;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.tecsun.robot.nanninig.R;
import com.tecsun.robot.utils.ViewUtils;
/**
 * 警告性弹框提示
 * Created by _Smile on 2016/9/27.
 */
public class WarningDialogFragment extends BaseDialogFragment {

    private View.OnClickListener mOnClickListener;
    private Object mWarningContentObject;
    private Object mWarningBtnObject;
    private int mWarningIcon;
    private boolean isHtml = false;

    @Override
    protected void initDialogView() {
        mCanceledOnTouchOutside = false;

        View view = LayoutInflater.from(getActivity()).inflate(R.layout.dialog_warning, null, false);
        dialog.setContentView(view);

        ImageView ivWarningIcon = ViewUtils.xFindViewById(view, R.id.iv_warning_icon);
        ivWarningIcon.setImageResource(getWarningIcon());

        TextView tvWarningContent = ViewUtils.xFindViewById(view, R.id.tv_warning_content);
        if (isHtml) {
            tvWarningContent.setText(Html.fromHtml((String) getWarningContentObject()));
        } else {
            ViewUtils.loadContent(tvWarningContent, getWarningContentObject());
        }
        TextView tvWarningBtn = ViewUtils.xFindViewById(view, R.id.tv_warning_btn);
        ViewUtils.loadContent(tvWarningBtn, getWarningBtnObject());

        LinearLayout btnWarning = ViewUtils.xFindViewById(view, R.id.ll_warning_btn);
        if (getOnClickListener() != null) {
            btnWarning.setOnClickListener(getOnClickListener());
        } else {
            btnWarning.setOnClickListener(this);
        }

    }

    @Override
    public void onStart() {
        Window window = getActivity().getWindow();
        window.setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        //window.requestWindowFeature(Window.FEATURE_NO_TITLE); 用在activity中，去标题
        int uiOptions = View.SYSTEM_UI_FLAG_HIDE_NAVIGATION
                | View.SYSTEM_UI_FLAG_LAYOUT_STABLE
                | View.SYSTEM_UI_FLAG_HIDE_NAVIGATION
                | View.SYSTEM_UI_FLAG_LAYOUT_HIDE_NAVIGATION
                | View.SYSTEM_UI_FLAG_IMMERSIVE
                | View.SYSTEM_UI_FLAG_FULLSCREEN;

        window.getDecorView().setSystemUiVisibility(uiOptions);
        super.onStart();
    }

    @Override
    public void onClick(View view) {
        dismissDialog();
    }

    /**
     * 销毁弹框
     */
    public void dismissDialog() {
        if (dialog != null){
            dialog.cancel();
            dialog.dismiss();
            dialog = null;
        }
    }


    public View.OnClickListener getOnClickListener() {
        return mOnClickListener;
    }

    public void setOnClickListener(View.OnClickListener mOnClickListener) {
        this.mOnClickListener = mOnClickListener;
    }

    public Object getWarningContentObject() {
        return mWarningContentObject;
    }

    public void setWarningContentObject(Object mWarningContentObject) {
        this.mWarningContentObject = mWarningContentObject;
    }

    public int getWarningIcon() {
        return mWarningIcon;
    }

    public void setWarningIcon(int mWarningIcon) {
        this.mWarningIcon = mWarningIcon;
    }

    public Object getWarningBtnObject() {
        return mWarningBtnObject;
    }

    public void setWarningBtnObject(Object mWarningBtnObject) {
        this.mWarningBtnObject = mWarningBtnObject;
    }

    public boolean isHtml() {
        return isHtml;
    }

    public void setHtml(boolean html) {
        isHtml = html;
    }
}
