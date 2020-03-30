//package com.tecsun.lib_videoplayer2.ui
//
//import android.Manifest
//import android.content.pm.ActivityInfo
//import android.os.Bundle
//import android.view.View
//import android.widget.ImageView
//import com.shuyu.gsyvideoplayer.GSYVideoManager
//import com.shuyu.gsyvideoplayer.utils.OrientationUtils
//import com.shuyu.gsyvideoplayer.video.StandardGSYVideoPlayer
//import com.tecsun.lib_videoplayer2.R
//import com.tecsun.lib_videoplayer2.VideoConstant
//import com.tecsun.lib_videoplayer2.base.VideoBaseActivity
//import com.tecsun.robot.nanning.util.log.LogUtil
//
//class SimplePlayerK : VideoBaseActivity() {
//
//    private val TAG = SimplePlayerK::class.java.simpleName
//
//    val permissions = arrayOf(Manifest.permission.WRITE_EXTERNAL_STORAGE, Manifest.permission.READ_EXTERNAL_STORAGE)
//
//    var videoPlayer: StandardGSYVideoPlayer? = null
//
//    var orientationUtils: OrientationUtils? = null
//
//    private var url: String? = null
//
//    private var title: String? = null
//
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
//    override fun onBackPressed() {
//        //先返回正常状态
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
//}