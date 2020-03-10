package com.tecsun.robot.utils;

import android.content.ActivityNotFoundException;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;

import androidx.fragment.app.Fragment;

import com.tecsun.robot.common.Defs;
import com.tecsun.robot.nanning.lib_base.BaseActivity;
import com.tecsun.robot.nanninig.GenericActivity;

/**
 * Intent工具类
 * Created by _Smile on 2016/8/10.
 */
public class IntentUtils {

    private static final Object lock = new Object();

    public static Fragment mContentFragment;

    /**
     * Activity跳转
     * @param context 上下文
     * @param tClass 跳转对象
     * @param titleObj 标题内容
     * @param contentFragment 主体内容Fragment
     * @param <T> 跳转对象泛型
     */
    public static <T> void startActivity(Context context, Class<T> tClass, Object titleObj, Fragment contentFragment) {
        synchronized (lock) {
            try {
                Intent intent = new Intent(context, tClass);
                if (titleObj != null && titleObj instanceof CharSequence) {
                    intent.putExtra(Defs.KEY_TITLE_TYPE, 1);
                    intent.putExtra(Defs.KEY_TITLE, (CharSequence) titleObj);
                } else if (titleObj != null && titleObj instanceof Integer) {
                    intent.putExtra(Defs.KEY_TITLE_TYPE, 0);
                    intent.putExtra(Defs.KEY_TITLE, (Integer) titleObj);
                }
                if (contentFragment != null) {
//                    BaseFragmentActivity.mContentFragment = contentFragment;
                    mContentFragment = contentFragment;
                }
                context.startActivity(intent);
            } catch (ActivityNotFoundException e) {
                e.printStackTrace();
            }
        }
    }

    /**
     * Activity跳转
     * @param context 上下文
     * @param tClass 跳转对象
     * @param titleObj 标题内容
     * @param contentFragment 主体内容Fragment
     * @param bundle fragment传值
     * @param <T> 跳转对象泛型
     */
    public static <T> void startActivity(Context context, Class<T> tClass, Object titleObj, Fragment contentFragment, Bundle bundle) {
        synchronized (lock) {
            try {
                Intent intent = new Intent(context, tClass);
                intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                if (titleObj != null && titleObj instanceof CharSequence) {
                    intent.putExtra(Defs.KEY_TITLE_TYPE, 1);
                    intent.putExtra(Defs.KEY_TITLE, (CharSequence) titleObj);
                } else if (titleObj != null && titleObj instanceof Integer) {
                    intent.putExtra(Defs.KEY_TITLE_TYPE, 0);
                    intent.putExtra(Defs.KEY_TITLE, (Integer) titleObj);
                }
                if (contentFragment != null) {
//                    BaseFragmentActivity.mContentFragment = contentFragment;
                    mContentFragment = contentFragment;
                }
                if (bundle != null) {
                    contentFragment.setArguments(bundle);
                }
                context.startActivity(intent);
            } catch (ActivityNotFoundException e) {
                e.printStackTrace();
            }
        }
    }

    /**
     * 跳转通用Activity
     * @param context 上下文
     * @param titleObj 标题内容
     * @param contentFragment 主体内容Fragment
     */
    public static void startActivity(Context context, Object titleObj, Fragment contentFragment) {
        startActivity(context, GenericActivity.class, titleObj, contentFragment);
    }

    /**
     * 跳转通用Activity
     * @param context 上下文
     * @param titleObj 标题内容
     * @param contentFragment 主体内容Fragment
     *      @param bundle 传值
     */
    public static void startActivity(Context context, Object titleObj, Fragment contentFragment, Bundle bundle) {

        if(context instanceof BaseActivity){
            ((BaseActivity)context).speechManager.cancelSemanticRequest();
        }
        startActivity(context, GenericActivity.class, titleObj, contentFragment, bundle);
    }


    /**
     * 跳转目标Activity
     * @param context 上下文
     * @param tClass 目标Activity
     * @param titleObj 标题内容
     * @param <T> 泛型
     */
    public static <T> void startActivity(Context context, Class<T> tClass, Object titleObj) {
        startActivity(context, tClass, titleObj, null);
    }

    /**
     * 跳转通用Activity -- 最新
     *
     * @param context         上下文
     * @param titleObj        标题内容
     * @param contentFragment 主体内容Fragment
     * @param bundle          传值
     */
    public static void startActivityNew(Context context, Object titleObj, Fragment contentFragment, Bundle bundle) {
        startActivity(context, GenericActivity.class, titleObj, contentFragment, bundle);
    }
}
