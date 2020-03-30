package com.tecsun.robot.nanninig

import android.annotation.TargetApi
import android.app.Activity
import android.content.Intent
import android.graphics.Color
import android.net.Uri
import android.os.Build
import android.os.Bundle
import android.os.Handler
import android.util.Log
import android.view.KeyEvent
import android.view.View
import android.view.Window
import android.view.WindowManager
import android.view.inputmethod.InputMethodManager
import android.webkit.DownloadListener
import android.webkit.WebChromeClient
import android.webkit.WebView
import android.webkit.WebViewClient
import android.widget.ProgressBar
import android.widget.TextView
import com.sanbot.opensdk.function.beans.speech.Grammar
import com.sanbot.opensdk.function.unit.interfaces.speech.WakenListener
import com.tecsun.jc.base.common.BaseConstant
import com.tecsun.robot.nanning.lib_base.BaseActivity
import com.tecsun.robot.nanning.lib_base.BaseRecognizeListener
import com.tecsun.robot.nanning.util.pinyin.PinYinUtil
import com.tecsun.robot.nanning.widget.TitleBar
import com.tecsun.robot.nanning.widget.TitleBar.hasLollipop

class HtmlActivity : BaseActivity(), DownloadListener {

    private val TAG = HtmlActivity::class.java.simpleName

    private var webView: WebView? = null

    private var progressBar: ProgressBar? = null

    private val url = ""

    override fun onCreate(savedInstanceState: Bundle?) {
//        openFullScreenModel()
        super.onCreate(savedInstanceState)

        setContentView(R.layout.app_activity_html)
        val url = intent.getStringExtra("url") ?: BaseConstant.URL_POLICIES_AND_REGULATIONS
        val mTitle = intent.getStringExtra("title") ?: "政策法规"
        Log.i("url---------", url)
//        initToolbar()
//        toolbar.setNavigationOnClickListener(View.OnClickListener {
//            if (webView!!.canGoBack()) { //当webview不是处于第一页面时，返回上一个页面
//                webView!!.goBack()
//            } else { //当webview处于第一页面时,直接退出程序
//                finish()
//            }
//        })
//        title.setText(mTitle ?: "")

        findViewById<View>(R.id.appItemPageBackIVLeft).visibility = View.INVISIBLE
        findViewById<View>(R.id.appItemPageBackIVRight).visibility = View.INVISIBLE
        findViewById<TextView>(R.id.appItemPageBackTitleTV).text = mTitle
        findViewById<View>(R.id.appItemPageBack).setOnClickListener {
            myFinish()
        }

        webView = findViewById<View>(R.id.webview) as WebView

        val settings = webView!!.settings
        settings.setAppCacheMaxSize(Long.MAX_VALUE)
        settings.setSupportMultipleWindows(false)
        settings.setJavaScriptEnabled(true)
        settings.setDomStorageEnabled(true)
        settings.setJavaScriptCanOpenWindowsAutomatically(true)
        settings.setLoadsImagesAutomatically(true)
        settings.setAllowFileAccess(true)
        settings.setSupportZoom(true)
        settings.setBuiltInZoomControls(true)
        settings.setDisplayZoomControls(false)
        settings.setUseWideViewPort(false)
        settings.setLoadWithOverviewMode(true)


        progressBar = findViewById<View>(R.id.progressBar) as ProgressBar
        setUpWebViewDefaults(webView)
        webView!!.loadUrl(url)
        webView!!.setWebChromeClient(object : WebChromeClient() {
            override fun onProgressChanged(view: WebView, newProgress: Int) {
                if (newProgress == 100) {
                    progressBar!!.visibility = View.GONE
                } else {
                    progressBar!!.visibility = View.VISIBLE
                    progressBar!!.progress = newProgress
                }
            }
        })

        initTitleView(mTitle);

        registerNetWorkMonitor(null, null)
    }

    private fun setUpWebViewDefaults(webView: WebView?) {
        webView!!.webViewClient = object : WebViewClient() {
            //覆写shouldOverrideUrlLoading实现内部显示网页
            override fun shouldOverrideUrlLoading(view: WebView, url: String): Boolean { //view.loadUrl(url);
                return false
            }
        }
        val settings = webView.settings
        settings.javaScriptEnabled = true
        settings.useWideViewPort = true
        settings.loadWithOverviewMode = true
        settings.builtInZoomControls = true
        settings.domStorageEnabled = true
        settings.defaultTextEncodingName = "UTF-8"
        if (Build.VERSION.SDK_INT > Build.VERSION_CODES.HONEYCOMB) {
            settings.displayZoomControls = false
        }
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
            WebView.setWebContentsDebuggingEnabled(true)
        }
        webView.setDownloadListener(this) //设置下载链接监听
    }

    override fun onKeyDown(keyCode: Int, event: KeyEvent): Boolean {
        if (webView!!.canGoBack()) {
            webView!!.goBack()
            return true
        } else {
            myFinish()
        }
        return super.onKeyDown(keyCode, event)
    }

    override fun onDestroy() {
        if (webView != null) {
            webView!!.removeAllViews()
            webView!!.destroy()
        }
        super.onDestroy()
    }

    override fun onDownloadStart(url: String, userAgent: String, contentDisposition: String, mimetype: String, contentLength: Long) {
        startDownload(url)
    }

    private fun startDownload(url: String) {
        val uri = Uri.parse(url) // url为你要链接的地址
        val intent = Intent(Intent.ACTION_VIEW, uri)
        startActivity(intent)
    }

    /**
     * 初始化titlebar控件，可重写
     */
    private fun initTitleView(text: String) {
        setImmersiveStatusBar()
        val titleBar = findViewById<TitleBar>(R.id.title_bar)
        if (titleBar == null) {
            return
        }
//        if (hasKitKat()) {
        titleBar.setImmersive(false)
//        }
        titleBar.setBackgroundColor(resources.getColor(R.color.c_2358ff))
//        titleBar.setLeftImageResource(R.drawable.ic_title_back)
        titleBar.setLeftTextColor(Color.WHITE)
        titleBar.setLeftClickListener {
            hideSoftKeyboard(this)
            // 处理返回按钮事件
            this.myFinish()
        }

        titleBar.setTitleColor(Color.WHITE)
        titleBar.setSubTitleColor(Color.WHITE)
        titleBar.setTitle(text)
        setTitleBar(titleBar)

    }

    private fun hasKitKat(): Boolean {
        return Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT
    }

    @TargetApi(Build.VERSION_CODES.LOLLIPOP)
    private fun setImmersiveStatusBar() {
        if (hasKitKat() && !hasLollipop()) {
            // 透明状态栏
            window.addFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS)
            // 透明导航栏
            // getWindow().addFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_NAVIGATION);
        } else if (hasLollipop()) {
            val window = window
            window.clearFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS or WindowManager.LayoutParams.FLAG_TRANSLUCENT_NAVIGATION)
            window.decorView.systemUiVisibility = (View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN
                    // | View.SYSTEM_UI_FLAG_LAYOUT_HIDE_NAVIGATION
                    or View.SYSTEM_UI_FLAG_LAYOUT_STABLE)
            window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS)
            window.statusBarColor = Color.TRANSPARENT
        }
    }

    /**
     * 设置页面标题栏 可重新设置返回按钮、页面标题和菜单项(主要用于设置标题)
     *
     * @param titleBar
     */
    open fun setTitleBar(titleBar: TitleBar) {
//        titleBar.setTitle(titleName ?: "")
    }


    private fun hideSoftKeyboard(activity: Activity) {
        val view = activity.currentFocus
        if (view != null) {
            val inputMethodManager = activity.getSystemService(Activity.INPUT_METHOD_SERVICE) as InputMethodManager
            inputMethodManager.hideSoftInputFromWindow(view.windowToken, InputMethodManager.HIDE_NOT_ALWAYS)
        }
    }

    private var isFirstShow = true
    override fun onRobotServiceConnected() {
        super.onRobotServiceConnected()
        setSpeakManager()
        setHardWareManager()

        if (isFirstShow) {
            Handler().postDelayed({ speakAndCheckComplete("请点击屏幕选择需要查询的政策法规") { speechManagerWakeUp() } }, 10)
            isFirstShow = false
        }
    }

    private fun setSpeakManager() {
        //设置唤醒，休眠回调
        //设置唤醒，休眠回调
        speechManager.setOnSpeechListener(object : WakenListener {
            override fun onWakeUp() {
                Log.e(TAG, "onWakeUp >>>>>>>>>>>>>>>>")
            }

            override fun onSleep() {
                Log.e(TAG, "onSleep >>>>>>>>>>>>>>>> ")
                if (!isFinishing) {
//                    speechManager.doWakeUp();
                }
            }

            override fun onWakeUpStatus(b: Boolean) {
                Log.e(TAG, "onWakeUpStatus >>>>>>>>>>>>>>>> = $b")
            }
        })


        speechManager.setOnSpeechListener(object : BaseRecognizeListener() {
            override fun voiceRecognizeText(voiceTXT: String) {
                if (PinYinUtil.isMatch(voiceTXT, resources.getStringArray(R.array.app_arr_back))) {
                    myFinish()
                }
            }

            override fun onRecognizeResult(grammar: Grammar): Boolean {
                return true
            }

        })
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

    override fun onWindowFocusChanged(hasFocus: Boolean) {
        super.onWindowFocusChanged(hasFocus)
        if (hasFocus && Build.VERSION.SDK_INT >= 19) {
            var decorView = window.decorView;
            decorView.setSystemUiVisibility(
                    View.SYSTEM_UI_FLAG_LAYOUT_STABLE
                            or View.SYSTEM_UI_FLAG_LAYOUT_HIDE_NAVIGATION
                            or View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN
                            or View.SYSTEM_UI_FLAG_HIDE_NAVIGATION
                            or View.SYSTEM_UI_FLAG_FULLSCREEN
                            or View.SYSTEM_UI_FLAG_IMMERSIVE_STICKY);
        }
    }

    private fun setHardWareManager() {
        if (hardWareManager != null) { //人脸识别回调
//            hdCameraManager.setMediaListener(object : FaceRecognizeListener {
//                override fun recognizeResult(list: List<FaceRecognizeBean>) {
//                    runOnUiThread {
//                        speechManagerWakeUp()
//                    }
//                }
//            })

            //关闭白光灯
            hardWareManager.switchWhiteLight(false)
        }
    }
}