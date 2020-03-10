package com.tecsun.robot.fragment.yiliao;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.tecsun.robot.bean.YangLaoInfoBean;
import com.tecsun.robot.bean.YiliaoInfoBean;
import com.tecsun.robot.common.Defs;
import com.tecsun.robot.fragment.BaseFragment;
import com.tecsun.robot.nanninig.R;
import com.tecsun.robot.param.IdNameBean;
import com.tecsun.robot.request.impl.CardRequestServerImpl;
import com.tecsun.robot.utils.StaticBean;
import com.tecsun.tsb.network.bean.ReplyBaseResultBean;
import com.tecsun.tsb.network.subscribers.ProgressSubscriber;
import com.tecsun.tsb.network.subscribers.SubscriberResultListener;

/**
 * 医保信息查询
 * */

public class YblxcxFragment extends BaseFragment {
    View mView;
    TextView tv_dwmc;
    TextView tv_name;//姓名
    TextView tv_rybh;//个人编号
    TextView tv_idcard;//身份证号
    TextView tv_ybzhye;//医保账户余额
    TextView tv_bndzje;//本年度总金额
    TextView tv_ryjfzt;//人员缴费状态
    TextView tv_nianxian;//年限
    TextView tv_ylbxzsr;//当前医疗保险总收入


    /**
     * int型标题内容
     */
    protected Integer intTitleObj;
    /**
     * 标题类型，0：int型；1：char型
     */
    protected int titleType;


    /**
     * char型标题内容
     */
    protected CharSequence charTitleObj;

    private TextView tv_title;


    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        mView = inflater.inflate(R.layout.activity_ybxxcx, container, false);
        tv_dwmc = (TextView) mView.findViewById(R.id.tv_dwmc);
        tv_name = (TextView) mView.findViewById(R.id.tv_name);
        tv_rybh = (TextView) mView.findViewById(R.id.tv_rybh);
        tv_idcard = (TextView) mView.findViewById(R.id.tv_idcard);
        tv_ybzhye = (TextView) mView.findViewById(R.id.tv_ybzhye);
        tv_ryjfzt = (TextView) mView.findViewById(R.id.tv_ryjfzt);
        tv_nianxian = (TextView) mView.findViewById(R.id.tv_nianxian);
        tv_bndzje = (TextView) mView.findViewById(R.id.tv_bndzje);
        tv_title = (TextView) mView.findViewById(R.id.tv_title_2);
        tv_ylbxzsr = (TextView) mView.findViewById(R.id.tv_ylbxzsr);
        initview();
        getIntentValues();
        return mView;
    }

    private void initview(){
        YanglaoInfo();
    }
    /**
     * 获取Intent传值tv_title
     */
    private void getIntentValues() {
        titleType = getActivity().getIntent().getIntExtra(Defs.KEY_TITLE_TYPE, -1);
        if (titleType == 0) {
            intTitleObj = getActivity().getIntent().getIntExtra(Defs.KEY_TITLE, 0);
        } else if (titleType == 1) {
            charTitleObj =  getActivity().getIntent().getCharSequenceExtra(Defs.KEY_TITLE);
        }
        tv_title.setText(charTitleObj+"");

    }

    /**
     *
     */
    private void YanglaoInfo() {
        IdNameBean param = new IdNameBean(StaticBean.name, StaticBean.idcard);
        CardRequestServerImpl.getInstance().getYiliaoInfo(param, new ProgressSubscriber<ReplyBaseResultBean<YiliaoInfoBean>>(
                getActivity(), new SubscriberResultListener() {
            @Override
            public void onNext(Object o) {
                loadVerifyData(o);
            }

            @Override
            public void onErr(Throwable e) {
                showWarningDialog(R.string.tip_request_err,onClickListenerFinish);
            }
        }
        ));
    }

    private void loadVerifyData(Object o) {
        if (o == null) {
            showWarningDialog(R.string.tip_not_query_info);
            return;
        }
        ReplyBaseResultBean<YiliaoInfoBean> resultBean = (ReplyBaseResultBean<YiliaoInfoBean>) o;

        if (resultBean.isSuccess()) {

            YiliaoInfoBean bean = resultBean.data;
            tv_dwmc.setText(bean.aab004+"");
            tv_name.setText(StaticBean.name+"");
            tv_rybh.setText(bean.aac001+"");
            tv_idcard.setText(bean.aac002+"");
            tv_ybzhye.setText(bean.ake513+"");
            tv_bndzje.setText(bean.ake511+"");
//            tv_isdg.setText(bean.yae022+"");
//            tv_sfzz.setText(bean.yac084+"");
//            tv_ryjfzt.setText(bean.aac031+"");
            tv_nianxian.setText(bean.akc503+"");
            tv_ylbxzsr.setText(bean.ake522);
        }
        else{

        }
    }


}
