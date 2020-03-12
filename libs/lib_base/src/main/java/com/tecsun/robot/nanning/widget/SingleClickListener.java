package com.tecsun.robot.nanning.widget;

import android.util.Log;
import android.view.View;


/**
 *
 * 间隔时间内只允许单次点击
 *
 * @author liudongwen
 * @version
 * @date 2019/05/29
 */
public abstract class SingleClickListener implements View.OnClickListener {
    private static long  mLastClickTime;
    /**
     * 间隔的点击时间
     */
    private static long timeInterval = 500L;

    public SingleClickListener() {

    }

    public long getTimeInterval() {
        return timeInterval;
    }

    public void setTimeInterval(long t) {
        timeInterval = t;
    }

    @Override
    public void onClick(View v) {
        long nowTime = System.currentTimeMillis();
        if (nowTime - mLastClickTime < timeInterval) {
            Log.d("TAG", "连续点击间隔太短被过滤");
            return;
        }
        mLastClickTime = nowTime;
        onSingleClick(v);
    }

    /**
     * 间隔时间内只允许单次点击
     * @param v
     */
    public abstract void onSingleClick(View v);

}



