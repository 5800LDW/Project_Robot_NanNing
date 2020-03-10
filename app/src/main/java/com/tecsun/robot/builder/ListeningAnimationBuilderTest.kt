//package com.tecsun.robot.builder
//
//import android.util.Log
//import android.view.View
//import android.widget.ImageView
//import android.widget.LinearLayout
//import com.tecsun.robot.nanning.builder.BaseBuilder
//import com.tecsun.robot.nanning.lib_base.BaseActivity
//import com.tecsun.robot.nanninig.R
//
///**
// * 动画处理
// *
// * @author liudongwen
// * @date 2020/03/02
// */
//class ListeningAnimationBuilderTest(private val baseActivity: BaseActivity) : BaseBuilder() {
//    private var ivListenButton1: ImageView? = null
//    private var ivListenButton2: ImageView? = null
//    private var ivRobot1: ImageView? = null
//    private var ivRobot2: ImageView? = null
//    private var llListen: LinearLayout? = null
//    override fun onCreate() {
//        Log.e(TAG, "onCreate()")
//        ivListenButton1 = baseActivity.findViewById(R.id.ivListenButton1)
//        ivListenButton2 = baseActivity.findViewById(R.id.ivListenButton2)
//        llListen = baseActivity.findViewById(R.id.llListen)
//        ivRobot1 = baseActivity.findViewById(R.id.ivRobot1)
//        ivRobot2 = baseActivity.findViewById(R.id.ivRobot2)
//        val flListener = baseActivity.findViewById<View>(R.id.flListener)
//        flListener?.setOnClickListener { v: View? ->
//            //停止机器人说话
//            baseActivity.myStopSpeak()
//            baseActivity.speechManagerWakeUp()
//            Log.e("TAGTAGTAG", "baseActivity.speechManagerWakeUp()")
//        }
//        val flListenerLogo = baseActivity.findViewById<View>(R.id.flListenerLogo)
//        flListenerLogo?.setOnClickListener { v: View? ->
//            //停止机器人说话
//            baseActivity.myStopSpeak()
//            baseActivity.speechManagerWakeUp()
//        }
//        //显示或隐藏动画
//        if (baseActivity.isRobotServiceConnected) {
//            myWakeup()
//        } else {
//            mySleep()
//        }
//    }
//
//    override fun onStart() {
//        Log.e(TAG, "onStart()")
//    }
//
//    override fun onResume() {}
//    override fun onPause() {
//        Log.e(TAG, "onPause()")
//    }
//
//    override fun onDestroy() {
//        Log.e(TAG, "onDestroy()")
//        super.onDestroy()
//    }
//
//    fun myWakeup() {
//        baseActivity.runOnUiThread {
//            Log.e(TAG, "onWakeup()")
//            if (ivListenButton1 != null) {
//                ivListenButton1!!.visibility = View.GONE
//            }
//            if (ivRobot1 != null) {
//                ivRobot1!!.visibility = View.GONE
//            }
//            if (llListen != null) {
//                llListen!!.visibility = View.GONE
//            }
//            if (ivListenButton2 != null) {
//                ivListenButton2!!.visibility = View.VISIBLE
//            }
//            if (ivRobot2 != null) {
//                ivRobot2!!.visibility = View.VISIBLE
//            }
//        }
//    }
//
//    fun mySleep() {
//        baseActivity.runOnUiThread {
//            Log.e(TAG, "onSleep()")
//            if (ivListenButton1 != null) {
//                ivListenButton1!!.visibility = View.VISIBLE
//            }
//            if (ivRobot1 != null) {
//                ivRobot1!!.visibility = View.VISIBLE
//            }
//            if (llListen != null) {
//                llListen!!.visibility = View.VISIBLE
//            }
//            if (ivListenButton2 != null) {
//                ivListenButton2!!.visibility = View.GONE
//            }
//            if (ivRobot2 != null) {
//                ivRobot2!!.visibility = View.GONE
//            }
//        }
//    }
//
//    companion object {
//        private val TAG = ListeningAnimationBuilderTest::class.java.simpleName
//    }
//
//    init {
//        baseActivity.lifecycle.addObserver(BuilderLifeCycleObserver(this))
//    }
//}