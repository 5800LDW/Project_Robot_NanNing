package com.tecsun.robot.fragment.shiye;

import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.tecsun.robot.adapter.LinearAdapter;
import com.tecsun.robot.bean.YangLaoJFBean;
import com.tecsun.robot.common.Constant;
import com.tecsun.robot.common.Defs;
import com.tecsun.robot.fragment.BaseFragment;
import com.tecsun.robot.nanninig.R;
import com.tecsun.robot.param.IdNameYanglaoJfBean;
import com.tecsun.robot.request.impl.CardRequestServerImpl;
import com.tecsun.robot.utils.StaticBean;
import com.tecsun.tsb.network.bean.ReplyBaseResultBean;
import com.tecsun.tsb.network.subscribers.ProgressSubscriber;
import com.tecsun.tsb.network.subscribers.SubscriberResultListener;

import java.util.ArrayList;
import java.util.List;

/**
 * 失业保险缴费明细查询
 * */

public class SybxjfmxFragment extends BaseFragment {
    View mView;
    RecyclerView recycler_view;
    LinearAdapter adapter;
    List<YangLaoJFBean> mylist=new ArrayList<>();

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
        mView = inflater.inflate(R.layout.activity_yljfmx, container, false);
        recycler_view = mView.findViewById(R.id.recycler_view);
        recycler_view.setLayoutManager(new LinearLayoutManager(getActivity()));
        adapter=new LinearAdapter(getActivity(),mylist);
        recycler_view.setAdapter(adapter);

        tv_title = (TextView) mView.findViewById(R.id.tv_title_2);
        getIntentValues();
        shiyelistInfo();
        return mView;
    }


    /**
     * 获取Intent传值tv_title_2
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
    private void shiyelistInfo() {
        IdNameYanglaoJfBean param = new IdNameYanglaoJfBean(StaticBean.name, StaticBean.idcard, Constant.Sybx,"1","1000");
        CardRequestServerImpl.getInstance().getYanglaolistInfo(param, new ProgressSubscriber<ReplyBaseResultBean<List<YangLaoJFBean>>>(
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
        ReplyBaseResultBean<List<YangLaoJFBean>> resultBean = (ReplyBaseResultBean<List<YangLaoJFBean>>) o;

        if (resultBean.isSuccess()) {
            mylist.clear();
            List<YangLaoJFBean> bean = resultBean.data;
            Log.d("数组大小",bean.size()+"");
            mylist.addAll(bean);
            adapter.notifyDataSetChanged();

        }
        else{
            showWarningDialog(resultBean.message);
        }
    }


}
