//package com.tecsun.lib_videoplayer2.ui
//
//import android.os.Bundle
//import androidx.recyclerview.widget.GridLayoutManager
//import com.shuyu.gsyvideoplayer.GSYVideoManager
//import com.tecsun.lib_videoplayer2.R
//import com.tecsun.lib_videoplayer2.VideoModel
//import com.tecsun.lib_videoplayer2.adapter.VideoListAdapter
//import com.tecsun.lib_videoplayer2.builder.ToLeadBuilder
//import com.tecsun.lib_videoplayer2.util.FileUtil
//import com.tecsun.robot.nanning.lib_base.BaseActivity
//import com.tecsun.robot.nanning.lib_base.BaseRecognizeListener
//import com.tecsun.robot.nanning.util.log.LogUtil
//import com.tecsun.robot.nanning.util.pinyin.PinYinUtil
//import kotlinx.android.synthetic.main.video_activity_list.*
//
//
//class SimpleListVideoActivityModeRVBack20200322_09 : BaseActivity() {
//
//    private val data = ArrayList<VideoModel>()
//
//    private val TAG = SimpleListVideoActivityModeRVBack20200322_09::class.java.simpleName
//
//    override fun onCreate(savedInstanceState: Bundle?) {
//        super.onCreate(savedInstanceState)
//        setContentView(R.layout.video_activity_list)
//        videoRecyclerView.layoutManager = GridLayoutManager(this@SimpleListVideoActivityModeRVBack20200322_09, 1)
//
////        data.add(VideoModel("file://" + Environment.getExternalStorageDirectory().path + "/Documents/shebao.mp4"))
////        data.add(VideoModel("file://" + Environment.getExternalStorageDirectory().path + "/Documents/全心出击.mp4"))
////        data.add(VideoModel("file://" + Environment.getExternalStorageDirectory().path + "/Documents/全心出击.mp4"))
////        data.add(VideoModel("file://" + Environment.getExternalStorageDirectory().path + "/Documents/全心出击.mp4"))//sheBaoXian
//
//        data.clear()
//        var files = ToLeadBuilder.toLead()
//        for (f in files) {
//            f?.let {
//                LogUtil.e(TAG,">>>>>>>>>>>>>>>>> " + "file://${f.absolutePath}")
//                data.add(VideoModel("file://${f.absolutePath}", FileUtil.getName(f)))
//            }
//        }
//        val vla = VideoListAdapter(this@SimpleListVideoActivityModeRVBack20200322_09, data)
//        videoRecyclerView.adapter = vla
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
//        setSpeakManager()
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