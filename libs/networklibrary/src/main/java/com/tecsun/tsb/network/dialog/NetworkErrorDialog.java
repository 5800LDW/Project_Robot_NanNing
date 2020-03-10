package com.tecsun.tsb.network.dialog;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import com.tecsun.tsb.network.R;

/**
 * 警告性弹框提示
 * Created by _Smile on 2016/9/27.
 */
public class NetworkErrorDialog extends AlertDialog implements View.OnClickListener {

    private Context mContext;

    private boolean isCloseActivity = true;

    public NetworkErrorDialog(Context context,boolean isCloseActivity) {
        super(context, R.style.style_dialog);
        this.mContext = context;
        this.isCloseActivity = isCloseActivity;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.dialog_network_error);
        setCancelable(false);
        setCanceledOnTouchOutside(false);

        TextView btnNetwork = (TextView) findViewById(R.id.btn_network_error);
        btnNetwork.setOnClickListener(this);

    }

    @Override
    public void onClick(View view) {
        this.cancel();
        this.dismiss();
        if ( isCloseActivity ){
            ((Activity) mContext).finish();
        }
    }
}
