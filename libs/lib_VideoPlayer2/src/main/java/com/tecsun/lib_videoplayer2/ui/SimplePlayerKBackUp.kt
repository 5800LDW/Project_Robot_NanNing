//package com.tecsun.lib_videoplayer2.ui
//
//import android.Manifest
//import android.content.pm.ActivityInfo
//import android.os.Bundle
//import android.view.View
//import android.widget.ImageView
//import com.sanbot.opensdk.function.unit.interfaces.speech.WakenListener
//import com.shuyu.gsyvideoplayer.GSYVideoManager
//import com.shuyu.gsyvideoplayer.utils.OrientationUtils
//import com.shuyu.gsyvideoplayer.video.StandardGSYVideoPlayer
//import com.tecsun.lib_videoplayer2.R
//import com.tecsun.lib_videoplayer2.VideoConstant
//import com.tecsun.robot.nanning.lib_base.BaseActivity
//import com.tecsun.robot.nanning.lib_base.BaseRecognizeListener
//import com.tecsun.robot.nanning.util.log.LogUtil
//import com.tecsun.robot.nanning.util.pinyin.PinYinUtil
//
//class SimplePlayerKBackUp : BaseActivity() {
//    private val TAG = SimplePlayerKBackUp::class.java.simpleName
//    val permissions = arrayOf(Manifest.permission.WRITE_EXTERNAL_STORAGE, Manifest.permission.READ_EXTERNAL_STORAGE)
//    var videoPlayer: StandardGSYVideoPlayer? = null
//    var orientationUtils: OrientationUtils? = null
//    private var url: String? = null
//    private var title: String? = null
//    override fun onCreate(savedInstanceState: Bundle?) {
//        super.onCreate(savedInstanceState)
//        setContentView(R.layout.activity_simple_play)
//        //        boolean hadPermission = PermissionsUtils.startRequestPermission(this, permissions, 101);
//        url = intent.getStringExtra(VideoConstant.VIDEO_URL)
//        title = intent.getStringExtra(VideoConstant.VIDEO_TITLE) + "  "
//        LogUtil.e(TAG, ">>>>>>>>>>>>>> onCreate()")
//        init()
//    }
//
//    private fun init() {
//        if (videoPlayer != null) {
//            videoPlayer!!.startPlayLogic()
//            LogUtil.e(TAG, ">>>>>>>>>>>>>> startPlayLogic()")
//            return
//        }
//        LogUtil.e(TAG, ">>>>>>>>>>>>>> init()")
//        videoPlayer = findViewById<View>(R.id.video_player) as StandardGSYVideoPlayer
//        //        String source1 = "file://" + Environment.getExternalStorageDirectory().getPath() + "/Documents/shebao.mp4";
////        String url = getIntent().getStringExtra("url");
////        String mTitle = getIntent().getStringExtra("title") + "";
////        String source1 = "http://9890.vod.myqcloud.com/9890_4e292f9a3dd011e6b4078980237cc3d3.f20.mp4";
//        videoPlayer!!.setUp(url, true, " ")
//        //增加封面
//        val imageView = ImageView(this)
//        imageView.scaleType = ImageView.ScaleType.CENTER_CROP
//        imageView.setImageResource(R.mipmap.xxx1)
//        videoPlayer!!.thumbImageView = imageView
//        //增加title
//        videoPlayer!!.titleTextView.visibility = View.VISIBLE
//        //设置返回键
//        videoPlayer!!.backButton.visibility = View.VISIBLE
//        //设置旋转
//        orientationUtils = OrientationUtils(this, videoPlayer)
//        //设置全屏按键功能,这是使用的是选择屏幕，而不是全屏
//        videoPlayer!!.fullscreenButton.setOnClickListener { orientationUtils!!.resolveByClick() }
//        //是否可以滑动调整
//        videoPlayer!!.setIsTouchWiget(true)
//        //设置返回按键功能
//        videoPlayer!!.backButton.setOnClickListener { onBackPressed() }
//        //音频焦点冲突时是否释放
//        videoPlayer!!.isReleaseWhenLossAudio = false
//        videoPlayer!!.startPlayLogic()
//    }
//
//    override fun onPause() {
//        super.onPause()
//        if (videoPlayer != null) {
//            videoPlayer!!.onVideoPause()
//        }
//    }
//
//    override fun onResume() {
//        super.onResume()
//        if (videoPlayer != null) {
//            videoPlayer!!.onVideoResume()
//        }
//    }
//
//    override fun onDestroy() {
//        super.onDestroy()
//        GSYVideoManager.releaseAllVideos()
//        if (orientationUtils != null) {
//            orientationUtils!!.releaseListener()
//        }
//    }
//
//    override fun onBackPressed() { //先返回正常状态
//        if (orientationUtils!!.screenType == ActivityInfo.SCREEN_ORIENTATION_LANDSCAPE) {
//            videoPlayer!!.fullscreenButton.performClick()
//            return
//        }
//        //释放所有
//        if (videoPlayer != null) {
//            videoPlayer!!.setVideoAllCallBack(null)
//        }
//        super.onBackPressed()
//    }
//
//    override fun onRobotServiceConnected() {
//        LogUtil.e(TAG, ">>>>>>>>>>>>>> onRobotServiceConnected()")
//        setSpeakManager()
//        speechManagerSleep()
//    }
//
//    private fun setSpeakManager() { //设置唤醒，休眠回调
//        speechManager.setOnSpeechListener(object : WakenListener {
//            override fun onWakeUp() {
//                LogUtil.i(TAG, "onWakeUp()")
//                speechManagerSleep()
//            }
//
//            override fun onSleep() {
//                LogUtil.i(TAG, "onSleep()")
//                if (videoPlayer != null) {
//                    videoPlayer!!.startPlayLogic()
//                }
//            }
//
//            override fun onWakeUpStatus(b: Boolean) {
//                LogUtil.i(TAG, "onWakeUpStatus = $b")
//            }
//        })
//        //监听到"返回"就自动结束当前Activity
//        speechManager.setOnSpeechListener(object : BaseRecognizeListener() {
//            override fun voiceRecognizeText(voiceTXT: String) {
//                if (PinYinUtil.isMatch(voiceTXT, resources.getStringArray(R.array.app_arr_back))) {
//                    myFinish()
//                } else {
//                    speechManagerSleep()
//                    myStopSpeak()
//                    LogUtil.i(TAG, "voiceRecognizeText = $voiceTXT")
//                }
//            }
//        })
//    }
//}