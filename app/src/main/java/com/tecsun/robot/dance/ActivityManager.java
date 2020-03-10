package com.tecsun.robot.dance;

import android.app.Activity;
import android.util.Log;

import java.util.HashSet;

public class ActivityManager {

    private static ActivityManager instance = new ActivityManager();
    private static HashSet<Activity> hashSet = new HashSet<>();
    private ActivityManager(){

    }
    public static ActivityManager getInstance() {
        return instance;
    }

    /**
     * 每一个Activity 在 onCreate 方法的时候，可以装入当前this
     * @param activity
     */
    public void addActivity(Activity activity) {
        try {
            Log.d("添加activity",activity.getPackageName());
            hashSet.add(activity);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * 调用此方法用于销毁所有的Activity，然后我们在调用此方法之前，调到登录的Activity
     */
    public void exit() {
        try {
            for (Activity activity : hashSet) {

                Log.d("退出activity",activity.getPackageName());
                if (activity != null)
                    activity.finish();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }


}