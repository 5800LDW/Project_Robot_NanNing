package com.tecsun.tsb.network.tool;

import android.content.Context;
import android.graphics.drawable.AnimationDrawable;
import android.util.AttributeSet;
import android.widget.ImageView;

/**
 * 适用于animation-list 动画
 * Created by _Smile on 2016/6/13.
 */

public class AnimImageViewLoader extends ImageView {

    private AnimationDrawable frameAnimation;

    public AnimImageViewLoader(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
        init();
    }

    public AnimImageViewLoader(Context context, AttributeSet attrs) {
        super(context, attrs);
        init();
    }

    public AnimImageViewLoader(Context context) {
        super(context);
        init();
    }

    /**
     * 初始化动画信息
     */
    private void init() {
        frameAnimation = (AnimationDrawable) getBackground();
        post(new Runnable(){
            public void run(){
                frameAnimation.start();
            }
        });
    }

    /**
     * 开启动画显示
     */
    public void startAnimation() {
        frameAnimation = (AnimationDrawable) getBackground();
        post(new Runnable() {
            @Override
            public void run() {
                frameAnimation.start();
            }
        });
    }

    /**
     * 关闭动画显示
     */
    public void stopAnimation() {
        post(new Runnable() {
            @Override
            public void run() {
                frameAnimation.stop();
            }
        });
    }
}
