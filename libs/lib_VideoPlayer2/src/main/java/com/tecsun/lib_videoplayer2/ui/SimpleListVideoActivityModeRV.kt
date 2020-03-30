package com.tecsun.lib_videoplayer2.ui

import android.os.Build
import android.os.Build.VERSION_CODES.KITKAT
import android.os.Bundle
import android.os.Handler
import android.transition.Explode
import android.view.View
import android.view.Window
import android.widget.TextView
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.shuyu.gsyvideoplayer.GSYVideoManager
import com.tecsun.lib_videoplayer2.R
import com.tecsun.lib_videoplayer2.VideoModel
import com.tecsun.lib_videoplayer2.adapter.VideoListAdapter
import com.tecsun.lib_videoplayer2.builder.ToLeadBuilder
import com.tecsun.lib_videoplayer2.util.FileUtil
import com.tecsun.robot.nanning.builder.TimeBuilder
import com.tecsun.robot.nanning.builder.TimeBuilder.TIME_2
import com.tecsun.robot.nanning.lib_base.BaseActivity
import com.tecsun.robot.nanning.lib_base.BaseRecognizeListener
import com.tecsun.robot.nanning.util.log.LogUtil
import com.tecsun.robot.nanning.util.pinyin.PinYinUtil
import kotlinx.android.synthetic.main.video_activity_list.*

/**
 * 视频列表页面, 放入根目录下的Documents, 只检测mp4文件
 *
 * @author liudongwen
 * @date 2020/03/22
 */
class SimpleListVideoActivityModeRV : BaseActivity() {

    private val data = ArrayList<VideoModel>()

    private val TAG = SimpleListVideoActivityModeRV::class.java.simpleName

    private var mAdapter: VideoListAdapter? = null

    private val mHandler = Handler();

    private var lastOpenTime = 0L;

    private var gridLayoutManager: GridLayoutManager? = null

    override fun onCreate(savedInstanceState: Bundle?) {

        // 设置一个exit transition
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            window.requestFeature(Window.FEATURE_CONTENT_TRANSITIONS)
            window.enterTransition = Explode()
            window.exitTransition = Explode()
        }

        super.onCreate(savedInstanceState)
        setContentView(R.layout.video_activity_list)

        (findViewById<View>(R.id.appItemPageBackTitleTV) as TextView).text = getString(R.string.base_text_movie)

        findViewById<View>(R.id.appItemPageBack).setOnClickListener {
            myFinish()
        }

        gridLayoutManager = GridLayoutManager(this@SimpleListVideoActivityModeRV, 1)

        videoRecyclerView.layoutManager = gridLayoutManager

        data.clear()
        var files = ToLeadBuilder.toLead()
        for (f in files) {
            f?.let {
                data.add(VideoModel("file://${f.absolutePath}", FileUtil.getName(f)))
            }
        }
        mAdapter = VideoListAdapter(this@SimpleListVideoActivityModeRV, data)
        videoRecyclerView.adapter = mAdapter

        //超时监听
        registerTimeMonitor(object : TimeBuilder.TimeListener {
            override fun biz(second: Int) {
                if (!isFinishing && timeBuilder != null && GSYVideoManager.instance().isPlaying) {
                    timeBuilder.initTime()
                } else if (second == TIME_2) {
                    myFinish()
                }
            }
        }).startBiz()

        //空数据显示布局
        if (data.isEmpty()) {
            emptyView.inflate()
        }

        videoRecyclerView.addOnScrollListener(object : RecyclerView.OnScrollListener() {
            var firstVisibleItem = 0
            var lastVisibleItem = 0
            override fun onScrollStateChanged(recyclerView: RecyclerView, newState: Int) {
                super.onScrollStateChanged(recyclerView, newState)
            }

            override fun onScrolled(recyclerView: RecyclerView, dx: Int, dy: Int) {
                super.onScrolled(recyclerView, dx, dy)
                try {
                    firstVisibleItem = gridLayoutManager!!.findFirstVisibleItemPosition()
                    lastVisibleItem = gridLayoutManager!!.findLastVisibleItemPosition()
                    //大于0说明有播放
                    if (GSYVideoManager.instance().playPosition >= 0) { //当前播放的位置
                        val position = GSYVideoManager.instance().playPosition
                        //对应的播放列表TAG
                        if (GSYVideoManager.instance().playTag == VideoListAdapter.TAG && (position < firstVisibleItem || position > lastVisibleItem)) { //如果滑出去了上面和下面就是否，和今日头条一样
                            //是否全屏
                            if (!GSYVideoManager.isFullState(this@SimpleListVideoActivityModeRV)) {
                                GSYVideoManager.releaseAllVideos()
                                mAdapter?.notifyDataSetChanged()
                            }
                        }
                    }
                }catch (e:Exception){
                    LogUtil.e(TAG,e)
                }
            }
        })
    }

    override fun onBackPressed() {
        if (isBackFromFull()) {
            return
        }
        LogUtil.e(TAG, ">>>>>>>>>>>>>>> onBackPressed()")
        releaseResource()
        super.onBackPressed()
    }

    override fun onPause() {
        super.onPause()
        GSYVideoManager.onPause()
    }

    override fun onResume() {
        super.onResume()
        GSYVideoManager.onResume()
    }

    override fun onDestroy() {
        super.onDestroy()
        releaseResource()
    }

    fun isBackFromFull(): Boolean =
            if (GSYVideoManager.backFromWindowFull(this)) {
                list2Normal()
                true
            } else {
                false
            }

    override fun onRobotServiceConnected() {
        setSpeakManager()
    }


    fun list2Normal() {
        //当前播放的位置
        if (GSYVideoManager.instance().playPosition >= 0) {
            mHandler.post(runnable)
        }
    }

    private fun setSpeakManager() {
        speechManager.setOnSpeechListener(object : BaseRecognizeListener() {
            override fun voiceRecognizeText(voiceTXT: String) {
                LogUtil.i(TAG, "voiceRecognizeText = $voiceTXT")

                if (isFinishing) {
                    myStopSpeak()
                    return
                }

                //监听"返回"
                if (PinYinUtil.isMatch(voiceTXT, resources.getStringArray(R.array.app_arr_back))) {
                    when (isBackFromFull()) {
                        true -> return
                        false -> {
                            myFinish()
                            return
                        }
                    }
                }

                LogUtil.i("TAG!!!!", "voiceRecognizeText = $voiceTXT")

                var userChoice = false;
                if (System.currentTimeMillis() - lastOpenTime > 2000 && !isFinishing && !GSYVideoManager.instance().isPlaying) {
                    lastOpenTime = System.currentTimeMillis()
                    for (index in data.indices) {
                        if (PinYinUtil.isMatch(voiceTXT, arrayOf(index.inc().toString(), "第${index.inc()}个", "第${index.inc()}"))) {
                            LogUtil.e(TAG, index)
                            videoRecyclerView.scrollToPosition(index)
                            mHandler.postDelayed({ mAdapter?.onVoiceClick(index) }, 100)
                            userChoice = true
                            speak("好的")
                            break
                        }
                    }
                }

                LogUtil.i("TAG!!!!", "userChoice = $userChoice")

                if (!userChoice) {
                    speechManagerSleep()
                    myStopSpeak()
                }
            }
        })
    }

    override fun onWindowFocusChanged(hasFocus: Boolean) {
        super.onWindowFocusChanged(hasFocus)
        if (hasFocus && Build.VERSION.SDK_INT >= KITKAT) {
            window.decorView.systemUiVisibility = (View.SYSTEM_UI_FLAG_LAYOUT_STABLE
                    or View.SYSTEM_UI_FLAG_LAYOUT_HIDE_NAVIGATION
                    or View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN
                    or View.SYSTEM_UI_FLAG_HIDE_NAVIGATION
                    or View.SYSTEM_UI_FLAG_FULLSCREEN
                    or View.SYSTEM_UI_FLAG_IMMERSIVE_STICKY);
        }
    }

    private val runnable = Runnable {
        GSYVideoManager.releaseAllVideos()
    }

    override fun myFinish() {
        super.myFinish()
        releaseResource()
    }

    private fun releaseResource() {
        mHandler.removeCallbacks(runnable)
        mHandler.removeCallbacksAndMessages(null)
        timeBuilder?.removeBiz()
        mAdapter?.onReleaseCallBack()
        GSYVideoManager.releaseAllVideos()
    }


}