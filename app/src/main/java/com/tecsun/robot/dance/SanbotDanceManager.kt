package com.tecsun.robot.dance

import android.util.Log
import com.sanbot.dance_play.DanceBean
import com.sanbot.dance_play.DanceInterface
import com.sanbot.dance_play.DanceManager
import com.tecsun.robot.MainApp


/**
 * 创建者     彭猛
 * 创建时间   2018/7/19 17:26
 * 描述	     TODO
 * 更新者     Author
 * 更新时间   Date
 * 更新描述   TODO
 */
class SanbotDanceManager {
    var currentPlayState: Int = 0
        private set
    private val isSetDanceId: Boolean = false
    private var mDanceList: List<DanceBean>? = null
    private var mCurPlayIndex: Int = 0//当前播放列表中的某一首
    private var mPlaySum: Int = 0
    private var isConnectSuccess: Boolean = false


    val isPaused: Boolean
        get() = currentPlayState == DanceManager.DANCE_STATUS_PAUSE

    val isError: Boolean
        get() = currentPlayState < DanceManager.DANCE_STATUS_STAR || currentPlayState > DanceManager.DANCE_STATUS_COMPLETE

    val isCompleted: Boolean
        get() = currentPlayState == DanceManager.DANCE_STATUS_COMPLETE
    val isCompletedOrError: Boolean
        get() = currentPlayState == DanceManager.DANCE_STATUS_COMPLETE || currentPlayState == DanceManager.DANCE_TYPE_CONNECTION_FAULT

    val maxVolume: Int
        get() = 0

    val volume: Int
        get() = 0

    //总时长
    val duration: Long
        get() = DanceManager.getSelf().danceTimeLength.toLong()

    //当前进度
    val currentPosition: Long
        get() = DanceManager.getSelf().currentLength.toLong()
    val isNotConnectedService: Boolean
        get() {
            if (!isConnectSuccess) {
                Log.d("====>","is not connect dance service!");
                return true
            }
            return false
        }
    val danceList: List<DanceBean>?
        get() {
            try {
                mDanceList = DanceManager.getDanceList(MainApp.getContext()!!)
            } catch (e: Exception) {
                e.printStackTrace()
            }

            if (isNotConnectedService) {
                return mDanceList
            }
            if (isNotConnectedService) {
                return mDanceList
            }
//            if (BuildConfig.DEBUG) {
//                for (i in mDanceList!!.indices) {
//                    LogUtils.Logd(TAG, "danceList:" + mDanceList!![i].toString())
//                }
//            }
            if (mDanceList == null || mDanceList!!.size == 0) {
                currentPlayState = STATE_DATA_IS_NULL
                mPlaySum = 0
                if (mOnMediaPlayerStateListener != null) {
                    mOnMediaPlayerStateListener!!.dancePlayerState(currentPlayState)
                }
            } else {
                mPlaySum = mDanceList!!.size
            }
            return mDanceList
        }
    val isStarting: Boolean
        get() = if (isNotConnectedService) {
            false
        } else {
            try {
                DanceManager.getSelf().isStarting
            } catch (e: Exception) {
                false
            }
        }

    private var mOnMediaPlayerStateListener: OnDancePlayerStateListener? = null
//    private var mOnExtraPlayStateListener: OnExtraPlayStateListener? = null


    fun startFromList(index: Int) {//播放当前列表中的第index首
        if (isNotConnectedService) {
            Log.i(TAG, "startFromList failed")
            return
        }
        var id = -1
        if (mDanceList != null && mDanceList!!.size > 0) {
            id = mDanceList!![index].id
        } else {
            danceList
            if (mDanceList == null || mDanceList!!.size == 0) {
//                ToastUtil.showText2(MyApplication.context, MyApplication.context!!.getResources().getString(R.string.not_player_date))
                return
            }
            id = mDanceList!![index].id
        }
        if (id != -1) {
            mCurPlayIndex = index
            try {
                instance.setDanceById(id)
                instance.start()
            } catch (e: Exception) {
                Log.d(TAG,"setDanceById Exception:$e")
            }
            //切换歌曲的UI实现
//            if (mOnExtraPlayStateListener != null) {
//                mOnExtraPlayStateListener!!.startPlayFromList(index)
//            }
        } else {
//            ToastUtil.showText2(MyApplication.context, MyApplication.context!!.getResources().getString(R.string.not_player_date))
        }
    }

    fun nextOne(): Int {
        //下一首的逻辑实现，调用这个方法时，要确保Controller的标题等UI也更新了
        if (isNotConnectedService) {
            return -1
        }
        if (mPlaySum == 1) {
//            ToastUtil.showText2(MyApplication.context, MyApplication.context!!.getResources().getString(R.string.not_have_more))
            return -1
        } else if (mPlaySum > 1) {
            if (mCurPlayIndex < mPlaySum - 1) {
                mCurPlayIndex = mCurPlayIndex + 1
            } else {
                mCurPlayIndex = 0
            }
        } else if (mPlaySum == 0) {

            return -1
        }
        startFromList(mCurPlayIndex)
        return mCurPlayIndex
    }

    fun previousOne(): Int {
        if (isNotConnectedService) {
            return -1
        }
        //上一首的逻辑实现，调用这个方法时，要确保Controller的标题等UI也更新了
        if (mPlaySum == 1) {
//            LogUtils.Logw(TAG, "netOne,playSum is 1")
//            ToastUtil.showText2(MyApplication.context, MyApplication.context!!.getResources().getString(R.string.not_have_more))
            return -1
        } else if (mPlaySum > 1) {
            if (mCurPlayIndex > 0) {
                mCurPlayIndex = mCurPlayIndex - 1
            } else {
                mCurPlayIndex = mPlaySum - 1
            }
        } else if (mPlaySum == 0) {

            return -1
        }
        startFromList(mCurPlayIndex)
        start()
        return mCurPlayIndex
    }

    private object SanbotDanceManagerHolder {
        internal val holder = SanbotDanceManager()
    }

    fun init() {
//        if (!FunctionConfig.FUN_DANCE) {
//            LogUtils.Logd(TAG, "SanbotDanceManager cancle init,the dance of function is not opened")
//            return
//        }
        DanceManager.init(MainApp.getContext())
        DanceManager.getSelf().setCallBack(object : DanceInterface {
            override fun onErrorListener(code: Int, msg: String) {
                Log.d(TAG, "DanceManager onErrorListener:code=$code,msg=$msg")
                when (code) {
                    DanceManager.DANCE_TYPE_CONNECTION_SUC -> isConnectSuccess = true
                    DanceManager.DANCE_TYPE_CONNECTION_FAULT -> isConnectSuccess = false
                    else -> {
                    }
                }
            }

            override fun onStateCallBack(state: Int) {
                Log.d(TAG, "DanceManager onStateCallBack:$state")
                currentPlayState = state
                if (mOnMediaPlayerStateListener != null) {
                    mOnMediaPlayerStateListener!!.dancePlayerState(state)
                }
//                if (mOnExtraPlayStateListener != null) {
//                    mOnExtraPlayStateListener!!.playerStateChange(state)
//                }
            }
        })
    }

    private fun setDanceById(id: Int) {
        if (isNotConnectedService) {
            return
        }
        DanceManager.getSelf().setDanceById(id)
    }

    fun start() {
        if (isNotConnectedService) {
            return
        }
        DanceManager.getSelf().start()
    }

    fun reStart() {
        if (isNotConnectedService) {
            return
        }
        startFromList(mCurPlayIndex)
    }

    fun pause() {
        if (isNotConnectedService) {
            return
        }
        DanceManager.getSelf().pasuceOrRestore()
    }

    fun stop() {
        if (isNotConnectedService) {
            return
        }
        Log.d(TAG, "SanbotDanceManager stop")
        try {
            DanceManager.getSelf().stop()
        } catch (e: Exception) {
            Log.w(TAG, "SanbotDanceManager stop Exception:" + e.toString())
            e.printStackTrace()
        }

    }

    fun resume() {
        if (isNotConnectedService) {
            return
        }
        DanceManager.getSelf().start()
    }

    fun setCurrentScreenMode(screenMode: Int) {
//        if (mOnExtraPlayStateListener != null) {
//            mOnExtraPlayStateListener!!.screenModeChange(screenMode)
//        }
    }

    fun release() {
        Log.d(TAG, "SanbotDanceManager release")
//        if (Constants.ZHIYIN_DESK == BuildConfig.FLAVOR || !MyApplication.sIsJinGangRobot) {
        if (true) {
            try {
                DanceManager.getSelf().destroy()
            } catch (e: Exception) {
                e.printStackTrace()
            }

        }
    }

    fun setDancePlayerStateListener(listener: OnDancePlayerStateListener) {
        //和MediaPlayer状态相关的监听,用于刷新DancePlayerController的UI
        this.mOnMediaPlayerStateListener = listener
    }

//    fun setOnExtraPlayStateListener(listener: OnExtraPlayStateListener?) {
//        //和MediaPlayer状态相关的监听,用于刷新MainActivity的UI状态
//        this.mOnExtraPlayStateListener = listener
//    }

    interface OnDancePlayerStateListener {
        //播放器的状态更新给UI控制器，如视频和音乐的UI控制器Controller
        fun dancePlayerState(currentState: Int)
    }

    companion object {
        private val TAG = "======>"

        val DANCE_STATUS_STAR = 1
        val DANCE_STATUS_PLAYING = 2
        val DANCE_STATUS_PAUSE = 3
        val DANCE_STATUS_STOP = 4
        val DANCE_STATUS_COMPLETE = 5
        val STATE_DATA_IS_NULL = 6

        val instance: SanbotDanceManager
            get() = SanbotDanceManagerHolder.holder
    }

}
