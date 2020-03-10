package com.tecsun.tsb.network.progress;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.os.Handler;
import android.os.Message;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.TextView;

import com.tecsun.tsb.network.R;
import com.tecsun.tsb.network.tool.NavigationBarUtil;

/**
 * 加载提示
 * Created by _Smile on 2016/5/12.
 */
public class ProgressDialogHandler extends Handler {

    /** 加载提示 */
    public static final int SHOW_PROGRESS_DIALOG = 1;
    /** 销毁提示 */
    public static final int DISMISS_PROGRESS_DIALOG = 2;

    private ProgressDialog pd;

    private Context mContext;
    private boolean mCancelable;
    private Object mTvLoadTipObj;
    private boolean mIsShowDialog;
    private ProgressCancelListener mProgressCancelListener;

    public ProgressDialogHandler(Context context, Object tvLoadTipObj, boolean isShowPDDialog,
                                 ProgressCancelListener mProgressCancelListener,
                                 boolean cancelable) {
        super();
        this.mContext = context;
        this.mTvLoadTipObj = tvLoadTipObj;
        this.mIsShowDialog = isShowPDDialog;
        this.mProgressCancelListener = mProgressCancelListener;
        this.mCancelable = cancelable;
    }

    /**
     * 初始化加载进度提示
     */
    private void initProgressDialog(){
        if (pd == null) {
            if (mContext == null) {
                return;
            }
            pd = new ProgressDialog(mContext,R.style.CommProgressDialog);
            View view = LayoutInflater.from(mContext).inflate(R.layout.dialog_all_loading, null, false);
            TextView tvLoadTip = (TextView) view.findViewById(R.id.tv_all_load);
            loadContent(tvLoadTip, mTvLoadTipObj);

            pd.setCancelable(mCancelable);
            pd.setCanceledOnTouchOutside(false);

            if (mCancelable) {
                pd.setOnCancelListener(new DialogInterface.OnCancelListener() {
                    @Override
                    public void onCancel(DialogInterface dialogInterface) {
                        mProgressCancelListener.onCancelProgress();
                    }
                });
            }

            try {
                if (mContext != null && !((Activity) mContext).isFinishing() && !pd.isShowing()) {
                    //隐藏导航栏
                    //失能焦点
                    NavigationBarUtil.focusNotAle(pd.getWindow());
                    pd.show();
                    pd.setContentView(view);
                    //显示虚拟栏的时候 隐藏
                    NavigationBarUtil.hideNavigationBar(pd.getWindow());
                    //再清理失能焦点
                    NavigationBarUtil.clearFocusNotAle(pd.getWindow());
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

    /**
     * 传入Object对象，TextView加载内容
     * @param view 控件
     * @param objContent 加载内容
     */
    private void loadContent(View view, Object objContent) {
        if (view != null && objContent != null) {
            if (objContent instanceof Integer && (Integer) objContent != 0) {
                ((TextView) view).setText((Integer) objContent);
            } else if (objContent instanceof CharSequence) {
                ((TextView) view).setText((CharSequence) objContent);
            }
        }
    }

    /**
     * 销毁提示
     */
    private void dismissProgressDialog(){
        try {
            if (mContext != null && pd != null && pd.isShowing()) {
                pd.dismiss();
                pd = null;
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

    }

    @Override
    public void handleMessage(Message msg) {
        switch (msg.what) {
            case SHOW_PROGRESS_DIALOG:
                if (mIsShowDialog) {
                    initProgressDialog();
                }
                break;
            case DISMISS_PROGRESS_DIALOG:
                dismissProgressDialog();
                break;
        }
    }

}
