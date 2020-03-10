package com.tecsun.robot.nanning.util

import android.app.ActivityManager
import android.content.Context
import android.util.Log
import com.tecsun.robot.nanning.pinyin.InitContentProvider

object ExitUtils {

    fun exit(){
        var mActivityManager = InitContentProvider.getStaticContext().getSystemService(Context.ACTIVITY_SERVICE) as ActivityManager
        mActivityManager?.let {
            var mList = mActivityManager.getRunningAppProcesses()
            for ( runningAppProcessInfo in mList) {
                if (runningAppProcessInfo.pid != android.os.Process.myPid()) {
                    Log.e("TAG","runningAppProcessInfo.pid = "+runningAppProcessInfo.pid)
                    android.os.Process.killProcess(runningAppProcessInfo.pid)
                }
            }
        }

        android.os.Process.killProcess(android.os.Process.myPid())
    }

}