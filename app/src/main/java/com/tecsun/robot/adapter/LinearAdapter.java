package com.tecsun.robot.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.recyclerview.widget.RecyclerView;

import com.tecsun.robot.bean.YangLaoJFBean;
import com.tecsun.robot.nanninig.R;

import java.util.ArrayList;
import java.util.List;

public class LinearAdapter extends RecyclerView.Adapter <LinearAdapter.LinearViewHolder>{
    //context
    private Context mContext;
    //展示的数据
    List<YangLaoJFBean> mylist=new ArrayList<>();
    public LinearAdapter(Context context,List<YangLaoJFBean> list){
        this.mylist=list;
        this.mContext=context;
//        for(int i=0;i<30;i++){
//            list.add(String.format("%s-%s", i/10+1,i));
//        }
    }

    //onCreateViewHolder方法创建一个viewHolder，viewholder可以理解为一条数据的展示布局，这里我们自定义类LinearViewHolder创建一个只有TextView的item
    //这里我们需要创建每条布局使用的layout：layout_linear_item
    @Override
    public LinearViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        return new LinearViewHolder(LayoutInflater.from(mContext).inflate(R.layout.item_ylbxjfmx,parent,false));
    }

    //onBindViewHolder方法为item的UI绑定展示数据，
    @Override
    public void onBindViewHolder(LinearViewHolder holder, int position) {
        holder.tv_01.setText(mylist.get(position).aae044);
        holder.tv_02.setText(mylist.get(position).aae002);
        holder.tv_03.setText(mylist.get(position).aaa115);
        holder.tv_04.setText(mylist.get(position).aae180);
        holder.tv_05.setText(mylist.get(position).yab139);
        holder.tv_06.setText(mylist.get(position).aae201);
        holder.tv_07.setText(mylist.get(position).aae079);
        if (position%2==0){
            holder.lin_item.setBackground(mContext.getResources().getDrawable(R.color.white));
         }
        else{
            holder.lin_item.setBackground(mContext.getResources().getDrawable(R.color.c_gray_1));
        }

        }

    //item的总长度
    @Override
    public int getItemCount() {
        return mylist.size();
    }

    //LinearViewHolder可以看作一个item的展示和内部逻辑处理，故而如果需要绑定事件，获取控件的时候要在itemView中获取
    //itemView由onCreateViewHolder方法LayoutInflater.inflatec创建
    class LinearViewHolder extends RecyclerView.ViewHolder{
        private TextView tv_01,tv_02,tv_03,tv_04,tv_05,tv_06,tv_07;
        private LinearLayout lin_item;
        public LinearViewHolder(View itemView){
            super(itemView);
            lin_item = (LinearLayout) itemView.findViewById(R.id.lin_item);
            tv_01=(TextView) itemView.findViewById(R.id.tv_01);
            tv_02=(TextView) itemView.findViewById(R.id.tv_02);
            tv_03=(TextView) itemView.findViewById(R.id.tv_03);
            tv_04=(TextView) itemView.findViewById(R.id.tv_04);
            tv_05=(TextView) itemView.findViewById(R.id.tv_05);
            tv_06=(TextView) itemView.findViewById(R.id.tv_06);
            tv_07=(TextView) itemView.findViewById(R.id.tv_07);


        }
    }
}