package com.tecsun.tsb.network.subscribers;

import android.content.Context;
import android.util.Log;

import com.tecsun.tsb.network.progress.ProgressCancelListener;
import com.tecsun.tsb.network.progress.ProgressDialogHandler;

import rx.Subscriber;

//import com.tecsun.tsb.func.utils.ToastUtils;

/**
 * 用于在Http请求开始时，自动显示一个ProgressDialog
 * 在Http请求结束是，关闭ProgressDialog
 * 调用者自己对请求数据进行处理
 * Created by _Smile on 2016/5/12.
 */
public class ProgressSubscriber<T> extends Subscriber<T> implements ProgressCancelListener {

    private SubscriberResultListener mSubscriberListener;
    private ProgressDialogHandler mProgressDialogHandler;

    private boolean isCloseActivity = true;
    private Context context;

    /**
     * 显示加载提示,默认提示
     * @param context 上下文
     * @param subscriberListener 订阅者监听
     */
    public ProgressSubscriber(Context context, SubscriberResultListener subscriberListener) {
        this.mSubscriberListener = subscriberListener;
        this.context = context;
        mProgressDialogHandler = new ProgressDialogHandler(context, null, true, this, true);
    }

    /**
     * 显示加载提示,网络错误是否关闭页面
     * @param isCloseActivity
     * @param context
     * @param subscriberListener
     */
    public ProgressSubscriber(boolean isCloseActivity,Context context,SubscriberResultListener subscriberListener) {
        this.mSubscriberListener = subscriberListener;
        this.context = context;
        this.isCloseActivity = isCloseActivity;
        mProgressDialogHandler = new ProgressDialogHandler(context, null, true, this, true);
    }
    /**
     * 显示加载提示,网络错误是否关闭页面,进度调内容
     * @param isCloseActivity
     * @param context
     * @param subscriberListener
     * @param tvLoadTip
     */
    public ProgressSubscriber(boolean isCloseActivity,Object tvLoadTip,Context context,SubscriberResultListener subscriberListener) {
        this.mSubscriberListener = subscriberListener;
        this.context = context;
        this.isCloseActivity = isCloseActivity;
        mProgressDialogHandler = new ProgressDialogHandler(context, tvLoadTip, true, this, true);
    }

    /**
     * 构造器,是否显示加载提示
     * @param context 上下文
     * @param isShowTip 是否显示加载提示
     * @param subscriberListener 订阅者监听
     */
    public ProgressSubscriber(Context context, boolean isShowTip, SubscriberResultListener subscriberListener) {
        this.mSubscriberListener = subscriberListener;
        this.context = context;
        mProgressDialogHandler = new ProgressDialogHandler(context, null, isShowTip, this, true);
    }

    /**
     * 显示加载提示，自定义加载内容
     * @param context 上下文
     * @param tvLoadTip 加载提示
     * @param subscriberListener 订阅者监听
     */
    public ProgressSubscriber(Context context, Object tvLoadTip, SubscriberResultListener subscriberListener) {
        this.mSubscriberListener = subscriberListener;
        this.context = context;
        mProgressDialogHandler = new ProgressDialogHandler(context, tvLoadTip, true, this, true);
    }

    /**
     * 构造器，自定义是否显示加载提示，自定义提示内容
     * @param context 上下文
     * @param tvLoadTip 加载提示内容
     * @param isShowLoad 是否显示加载提示
     * @param subscriberListener 订阅者监听
     */
    public ProgressSubscriber(Context context, Object tvLoadTip, boolean isShowLoad, SubscriberResultListener subscriberListener) {
        this.mSubscriberListener = subscriberListener;
        this.context = context;
        mProgressDialogHandler = new ProgressDialogHandler(context, tvLoadTip, isShowLoad, this, true);
    }

    /**
     * 显示加载进度提示
     */
    private void showProgressDialog(){
        if (mProgressDialogHandler != null) {
            mProgressDialogHandler.obtainMessage(ProgressDialogHandler.SHOW_PROGRESS_DIALOG).sendToTarget();
        }
    }

    /**
     * 销毁进度提示
     */
    private void dismissProgressDialog(){
        if (mProgressDialogHandler != null) {
            mProgressDialogHandler.obtainMessage(ProgressDialogHandler.DISMISS_PROGRESS_DIALOG).sendToTarget();
            mProgressDialogHandler = null;
        }
    }

    /**
     * 订阅开始时调用
     * 显示ProgressDialog
     */
    @Override
    public void onStart() {
        showProgressDialog();
    }

    /**
     * 完成，隐藏ProgressDialog
     */
    @Override
    public void onCompleted() {
        dismissProgressDialog();
    }

    /**
     * 对错误进行统一处理
     * 隐藏ProgressDialog
     * @param e
     */
    @Override
    public void onError(Throwable e) {
        dismissProgressDialog();
        Log.e(ProgressSubscriber.class.getName(), e.toString());
//        if (e instanceof SocketTimeoutException ||
//                e instanceof ConnectException ||
//                e instanceof BindException ||
//                e instanceof NoRouteToHostException ||
//                e instanceof UnknownHostException ||
//                e instanceof ProtocolException) {
//            // 网络异常处理
//            NetworkErrorDialog dialog = new NetworkErrorDialog(context,isCloseActivity);
//            dialog.show();
//        } else {
//            if (mSubscriberListener != null) {
//                mSubscriberListener.onErr(e);
//            }
//        }
        if (mSubscriberListener != null) {
            mSubscriberListener.onErr(e);
        }
        e.printStackTrace();
    }

    /**
     * 将onNext方法中的返回结果交给Activity或Fragment自己处理
     *
     * @param t 创建Subscriber时的泛型类型
     */
    @Override
    public void onNext(T t) {
        if (mSubscriberListener != null) {
            mSubscriberListener.onNext(t);
        }
    }

    /**
     * 取消ProgressDialog的时候，取消对observable的订阅，同时也取消了http请求
     */
    @Override
    public void onCancelProgress() {
        if (!this.isUnsubscribed()) {
            this.unsubscribe();
        }
    }
}