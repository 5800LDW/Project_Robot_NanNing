//package com.tecsun.lib_videoplayer2.ui
//
//import android.content.Intent
//import android.os.Bundle
//import android.os.Environment
//import androidx.recyclerview.widget.GridLayoutManager
//import com.shuyu.gsyvideoplayer.GSYVideoManager
//import com.tecsun.lib_videoplayer2.R
//import com.tecsun.lib_videoplayer2.VideoConstant.VIDEO_TITLE
//import com.tecsun.lib_videoplayer2.VideoConstant.VIDEO_URL
//import com.tecsun.lib_videoplayer2.VideoModel
//import com.tecsun.lib_videoplayer2.adapter.VideoListAdapter
//import com.tecsun.lib_videoplayer2.base.VideoBaseActivity
//import com.tecsun.robot.nanning.lib_base.BaseRecognizeListener
//import com.tecsun.robot.nanning.util.log.LogUtil
//import com.tecsun.robot.nanning.util.pinyin.PinYinUtil
//import kotlinx.android.synthetic.main.video_activity_list.*
//
//
//class SimpleListVideoActivityModeRVBackup20200322 : VideoBaseActivity() {
//    private val data = ArrayList<VideoModel>()
//    private val TAG = SimpleListVideoActivityModeRVBackup20200322::class.java.simpleName
//    override fun onCreate(savedInstanceState: Bundle?) {
//        super.onCreate(savedInstanceState)
//        setContentView(R.layout.video_activity_list)
//        videoRecyclerView.layoutManager = GridLayoutManager(this@SimpleListVideoActivityModeRVBackup20200322, 2)
//        data.add(VideoModel("file://" + Environment.getExternalStorageDirectory().path + "/Documents/shebao.mp4"))
//        data.add(VideoModel("file://" + Environment.getExternalStorageDirectory().path + "/Documents/全心出击.mp4"))
//        data.add(VideoModel("file://" + Environment.getExternalStorageDirectory().path + "/Documents/全心出击.mp4"))
//        data.add(VideoModel("file://" + Environment.getExternalStorageDirectory().path + "/Documents/全心出击.mp4"))//sheBaoXian
//        data.add(VideoModel("file://" + Environment.getExternalStorageDirectory().path + "/Documents/sheBaoXian.wmv"))//sheBaoXian
//        val vla = VideoListAdapter(this@SimpleListVideoActivityModeRVBackup20200322, data)
//        vla.setOnItemClickListener { adapter, view, position ->
//            LogUtil.e(TAG, ">>>>>>>>>>>>>>>>>> OnItemClick")
//            myStartActivity(Intent(this@SimpleListVideoActivityModeRVBackup20200322, SimplePlayerK::class.java)
//                    .putExtra(VIDEO_TITLE, data.get(position).title).putExtra(VIDEO_URL, data.get(position).url))
//
//        }
//        videoRecyclerView.adapter = vla;
//    }
//
//    override fun onBackPressed() {
//        if (isBackFromFull()) {
//            return
//        }
//        super.onBackPressed()
//    }
//
//    override fun onPause() {
//        super.onPause()
//        GSYVideoManager.onPause()
//    }
//
//    override fun onResume() {
//        super.onResume()
//        GSYVideoManager.onResume()
//    }
//
//    override fun onDestroy() {
//        super.onDestroy()
//        GSYVideoManager.releaseAllVideos()
//    }
//
//
////    private fun isBackFromFull(): Boolean {
////        val b = GSYVideoManager.backFromWindowFull(this)
////        if (b) {
////            //大于0说明有播放
////            if (GSYVideoManager.instance().playPosition >= 0) { //当前播放的位置
////                GSYVideoManager.releaseAllVideos()
////            }
////        }
////        return b;
////    }
//
//    private fun isBackFromFull(): Boolean =
//            if (GSYVideoManager.backFromWindowFull(this)) {
//                //当前播放的位置
//                if (GSYVideoManager.instance().playPosition >= 0) {
//                    GSYVideoManager.releaseAllVideos()
//                }
//                true
//            } else {
//                false
//            }
//
//    override fun onRobotServiceConnected() {
////        super.onRobotServiceConnected()
//        LogUtil.e(TAG, ">>>>>>>>>>>>>> onRobotServiceConnected()")
//        setSpeakManager()
//        speechManagerSleep()
//    }
//
//    private fun setSpeakManager() {
//        //监听"返回"
//        speechManager.setOnSpeechListener(object : BaseRecognizeListener() {
//            override fun voiceRecognizeText(voiceTXT: String) {
//                if (PinYinUtil.isMatch(voiceTXT, resources.getStringArray(R.array.app_arr_back))) {
//                    when (isBackFromFull()) {
//                        true -> return
//                        false -> myFinish()
//                    }
//                } else {
//                    speechManagerSleep()
//                    myStopSpeak()
//                    LogUtil.i(TAG, "voiceRecognizeText = $voiceTXT")
//                }
//            }
//        })
//    }
//
//
//}