package com.tecsun.robot.nanning.widget

import android.view.View
import com.tecsun.robot.nanning.util.log.LogUtil

/**
 *
 * 间隔时间内只允许单次点击
 *
 * @author liudongwen
 * @version
 * @date 2019/05/29
 *
 *
 *
//    override fun onClick(v: View) {
//        val nowTime = System.currentTimeMillis()
//        if (nowTime - mLastClickTime < timeInterval) {
//            Log.d("TAG", "连续点击间隔太短被过滤")
//            return
//        }
//        mLastClickTime = nowTime
//        onSingleClick(v)
//    }
 */
interface SingleClickListener2 : View.OnClickListener {

    override fun onClick(v: View?) {
        val nowTime = System.currentTimeMillis()
        if (nowTime - mLastClickTime < timeInterval) {
            LogUtil.i("TAG", "连续点击间隔太短被过滤")
            return
        }
    }

    
    companion object {
        private var mLastClickTime: Long = 0
        /**
         * 间隔的点击时间
         */
        private var timeInterval = 500L

        private val TAG = SingleClickListener2::class.java.simpleName
    }
}