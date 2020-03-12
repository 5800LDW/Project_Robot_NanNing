package com.tecsun.robot.nanning.builder

import android.os.Handler
import android.os.Looper
import com.tecsun.robot.nanning.util.log.LogUtil
import java.util.*

object TimeBuilder {

    private val TAG = TimeBuilder::class.java.simpleName

    const val TIME_1 = 55

    const val TIME_2 = 70

    private val secondsList = LinkedList<Int>()

    private val mHandler = Handler(Looper.getMainLooper())

    private val bizList: LinkedList<TimeListener?> = LinkedList()


    fun addBiz(l: TimeListener?) {
        LogUtil.i(TAG,"addBiz()")
        mHandler.post {
            secondsList.clear()
            bizList.clear()
            bizList.addFirst(l)
//            mHandler.removeCallbacks(checkRunnable)
        }
    }

    fun startBiz() {
        LogUtil.i(TAG,"startBiz()")
        mHandler.post (checkRunnable)
    }

    private fun doBiz() {
        LogUtil.i(TAG,"doBiz()")
        bizList.first?.biz(secondsList.size)
    }

     fun removeBiz() {
        LogUtil.i(TAG,"removeBiz()")
        mHandler.post {
            bizList.clear()
            secondsList.clear()
            mHandler.removeCallbacks(checkRunnable)
        }
    }

    private val checkRunnable = Runnable {
        checkTime()
    }

    private fun checkTime() {
        LogUtil.i(TAG,">>>>>>>>>>>>>>>>>>>>>>>>>checkTime()")
        secondsList.add(0)
        if (secondsList.size > TIME_2 ) {
            removeBiz()
        } else {
            doBiz()
            mHandler.postDelayed(checkRunnable, 1000)
        }
    }

    interface TimeListener {
        /**
         * 超时多少秒
         * @param second
         */
        fun biz(second: Int)
    }

    fun initTime() {
        LogUtil.i(TAG,"initTime()")
        mHandler.post {
            secondsList.clear()
        }
    }


}