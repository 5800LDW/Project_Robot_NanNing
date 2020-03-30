package com.tecsun.robot.nanninig;

import android.os.Bundle;
import android.os.CountDownTimer;
import android.os.Handler;
import android.util.Log;
import android.view.View;
import android.webkit.WebView;
import android.widget.Button;
import android.widget.TextView;

import androidx.annotation.Nullable;

import com.tecsun.jc.base.utils.ToastUtils;
import com.tecsun.robot.MyBaseActivity;
import com.tecsun.robot.bean.QrinfoLXBean;
import com.tecsun.robot.bean.evenbus.IdCardBean;
import com.tecsun.robot.bean.qrinfoBean;
import com.tecsun.robot.dance.ActivityManager;
import com.tecsun.robot.nanning.util.WebViewUtil;
import com.tecsun.robot.request.impl.CardRequestServerImpl;
import com.tecsun.robot.utils.StaticBean;
import com.tecsun.tsb.network.bean.ReplyBaseResultBean;
import com.tecsun.tsb.network.subscribers.ProgressSubscriber;
import com.tecsun.tsb.network.subscribers.SubscriberResultListener;

import org.greenrobot.eventbus.EventBus;

/**
 * 获取二维码
 * */
public class ReadQrActivity extends MyBaseActivity {
    Button btn_close;
    WebView webview;
    Handler handler=new Handler();
    Runnable runnable;

    boolean Flag_Toast_Login = true;//不重复弹出toast;
    TextView tv_time;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_read_qrinfo);

        btn_close = (Button) findViewById(R.id.btn_close);
        tv_time = (TextView) findViewById(R.id.tv_time);
        webview = (WebView) findViewById(R.id.webview);
        StartTime();
        btn_close.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });

        YangQrInfo();
    }
    /**
     * 查询二维码
     */
    private void YangQrInfo() {
        CardRequestServerImpl.getInstance().getQr(new ProgressSubscriber<ReplyBaseResultBean<qrinfoBean>>(
                this, new SubscriberResultListener() {
            @Override
            public void onNext(Object o) {
                    loadVerifyData(o);
            }

            @Override
            public void onErr(Throwable e) {

                ToastUtils.INSTANCE.showGravityShortToast(ReadQrActivity.this,getString(R.string.tip_request_err));
                finish();

            }
        }
        ));
    }

    /**二维码*/
    private void loadVerifyData(Object o) {
        if (o == null) {
            ToastUtils.INSTANCE.showGravityShortToast(this,"获取二维码失败");
            return;
        }
        ReplyBaseResultBean<qrinfoBean> resultBean = (ReplyBaseResultBean<qrinfoBean>) o;

        if (resultBean.isSuccess()) {
            WebViewUtil.set(webview);
            webview.setHorizontalScrollBarEnabled(false);//水平不显示
            webview.setVerticalScrollBarEnabled(false); //垂直不显示
            webview.loadUrl(resultBean.data.url);
            getCodeResult(resultBean.data.aaz346);//轮循
        }
        else{
            ToastUtils.INSTANCE.showGravityShortToast(this,"获取二维码失败");
        }
    }
    /**轮循*/
    private void loadVerifyDataLunxun(Object o) {
        if (o == null) {
//            showWarningDialog(com.tecsun.tsb.res.R.string.tip_not_query_info);
            return;
        }
        ReplyBaseResultBean<QrinfoLXBean> resultBean = (ReplyBaseResultBean<QrinfoLXBean>) o;

        if (resultBean.isSuccess()) {
            if ("1".equals(resultBean.data.ISAUTH)){//授权成功1、成功、0授权中
                StaticBean.name=resultBean.data.AAC003;
                StaticBean.idcard=resultBean.data.AAC002;
                if (Flag_Toast_Login){
                    Flag_Toast_Login=false;
                    this.finish();
                    EventBus.getDefault().post(new IdCardBean(1));
                }

            }
        }
        else{
            this.finish();
            ToastUtils.INSTANCE.showGravityShortToast(this,"获取二维码失败");
        }
    }

    /**
     * 轮循获取状态
     * */
    public void getCodeResult(String code){
        runnable = new Runnable(){
            @Override
            public void run(){
                YangQrLunxun(code);
                //延迟1秒执行
                handler.postDelayed(this, 1000);
            }
        };
        handler.post(runnable);
    }


    /**
     * 查询轮询查询状态
     */
    private void YangQrLunxun(String code) {
        CardRequestServerImpl.getInstance().getQrLunxun(code,new ProgressSubscriber<ReplyBaseResultBean<QrinfoLXBean>>(
                this,false, new SubscriberResultListener() {
            @Override
            public void onNext(Object o) {
                Log.d("输出返回值轮询",o+"---");
                loadVerifyDataLunxun(o);
            }

            @Override
            public void onErr(Throwable e) {
                Log.d("输出返回值轮询-err",e.getMessage()+"");
            }
        }
        ));
    }
    @Override
    protected void onDestroy() {
        super.onDestroy();
        StopTime();
        handler.removeCallbacksAndMessages(null);
        EventBus.getDefault().post(new IdCardBean(3));
    }


    /**
     * 是否启动定时器倒计时
     * */
    public void StartTime(){

        StopTime();
        time=60;
        timer.start();
    }

    public void StopTime(){
        if (timer!=null){
            timer.cancel();
        }

    }

    public static int time = 60;
    CountDownTimer timer = new CountDownTimer(time*1000, 1000) {
        @Override
        public void onTick(long millisUntilFinished) {
            Log.d("定时器",millisUntilFinished / 1000 + "");
            tv_time.setText("剩余"+millisUntilFinished / 1000+"秒");
        }

        @Override
        public void onFinish() {
            finish();
            EventBus.getDefault().post(new IdCardBean(3));
        }
    };

}
