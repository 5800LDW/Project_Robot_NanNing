package com.tecsun.robot.nanninig

import android.content.Intent
import android.os.Build
import android.os.Bundle
import android.os.Handler
import android.os.Message
import android.view.View
import android.view.Window
import android.view.WindowManager
import androidx.appcompat.app.AppCompatActivity
import java.lang.ref.WeakReference


class StartPageActivity : AppCompatActivity(), HandlerCallback {

    private val DELAY_MILLIS = 500
    lateinit var handler: SafeHandler

    override fun onCreate(savedInstanceState: Bundle?) {
        openFullScreenModel()
        super.onCreate(savedInstanceState)
        if (!isTaskRoot) {
            finish()
            return
        }



        handler = SafeHandler(this, this)
        Handler().postDelayed({
            val intent = Intent(
                this@StartPageActivity,
                HomePageActivity::class.java
            )
            startActivity(intent)
            finish()
        }, 1000)
    }


    override fun handleMessage(msg: Message?) {
        when (msg?.what) {
            1000 -> {
                val intent = Intent(
                    this@StartPageActivity,
                        HomePageActivity::class.java
                )
                startActivity(intent)
                finish()
            }
            else -> {
            }
        }
    }

    /**
     * 动态获取手机信息权限
     */
    private fun getPhoneState() {
        handler.sendEmptyMessageDelayed(1000, DELAY_MILLIS.toLong())
    }


    private fun openFullScreenModel() {
//        requestWindowFeature(Window.FEATURE_NO_TITLE)
        supportRequestWindowFeature(Window.FEATURE_NO_TITLE);
        val lp = window.attributes

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.P) {
            //设置绘图区域可以进入刘海屏区域
            lp.layoutInDisplayCutoutMode =
                WindowManager.LayoutParams.LAYOUT_IN_DISPLAY_CUTOUT_MODE_SHORT_EDGES
        }
        window.attributes = lp
        val decorView = window.decorView
        val uiOptions = (View.SYSTEM_UI_FLAG_FULLSCREEN
                or View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN
                or View.SYSTEM_UI_FLAG_LAYOUT_STABLE
                or View.SYSTEM_UI_FLAG_IMMERSIVE_STICKY)
        decorView.systemUiVisibility = uiOptions
    }

}


interface HandlerCallback {

    fun handleMessage(msg: Message?)

}

class SafeHandler(activity: AppCompatActivity, private val callback: HandlerCallback) : Handler() {

    private var ref: WeakReference<AppCompatActivity>? = null

    init {
        this.ref = WeakReference(activity)
    }

    override fun handleMessage(msg: Message?) {
        val activity = ref?.get()
        if (activity != null) {
            callback.handleMessage(msg)
        }
        super.handleMessage(msg)
    }
}