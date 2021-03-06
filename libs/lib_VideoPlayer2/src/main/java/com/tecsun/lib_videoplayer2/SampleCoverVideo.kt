package com.tecsun.lib_videoplayer2

import android.content.Context
import android.graphics.Point
import android.media.AudioManager
import android.util.AttributeSet
import android.view.Surface
import android.view.View
import android.view.ViewGroup
import android.view.Window
import android.widget.ImageView
import android.widget.SeekBar
import com.bumptech.glide.Glide
import com.bumptech.glide.request.RequestOptions
import com.shuyu.gsyvideoplayer.utils.CommonUtil
import com.shuyu.gsyvideoplayer.utils.Debuger
import com.shuyu.gsyvideoplayer.utils.GSYVideoType
import com.shuyu.gsyvideoplayer.video.StandardGSYVideoPlayer
import com.shuyu.gsyvideoplayer.video.base.GSYBaseVideoPlayer
import com.shuyu.gsyvideoplayer.video.base.GSYVideoView

/**
 * 带封面
 * Created by guoshuyu on 2017/9/3.
 */
class SampleCoverVideo : StandardGSYVideoPlayer {
    var mCoverImage: ImageView? = null
    var mCoverOriginUrl: String? = null
    var mDefaultRes = 0

    constructor(context: Context?, fullFlag: Boolean?) : super(context, fullFlag) {}
    constructor(context: Context?) : super(context) {}
    constructor(context: Context?, attrs: AttributeSet?) : super(context, attrs) {}

    override fun init(context: Context) {
        super.init(context)
        mCoverImage = findViewById<View>(R.id.thumbImage) as ImageView
        if (mThumbImageViewLayout != null &&
                (mCurrentState == -1 || mCurrentState == GSYVideoView.CURRENT_STATE_NORMAL || mCurrentState == GSYVideoView.CURRENT_STATE_ERROR)) {
            mThumbImageViewLayout.visibility = View.VISIBLE
        }
        onAudioFocusChangeListener = AudioManager.OnAudioFocusChangeListener { }
    }

    override fun getLayoutId(): Int {
        return R.layout.video_layout_cover
    }

    fun loadCoverImage(url: String?, res: Int) {
        mCoverOriginUrl = url
        mDefaultRes = res
        Glide.with(context.applicationContext)
                .setDefaultRequestOptions(
                        RequestOptions()
                                .frame(1000000) //.transforms(new CenterCrop(), new RoundedCorners(ScreenUtil.dip2px(InitContentProvider.getStaticContext(), 50f)))
                                .centerCrop()
                                .error(res) //.skipMemoryCache(true)
                                //.diskCacheStrategy(DiskCacheStrategy.ALL)
                                .placeholder(res))
                .load(url)
                .into(mCoverImage!!)
    }

    override fun startWindowFullscreen(context: Context, actionBar: Boolean, statusBar: Boolean): GSYBaseVideoPlayer {
        val gsyBaseVideoPlayer = super.startWindowFullscreen(context, actionBar, statusBar)
        val sampleCoverVideo = gsyBaseVideoPlayer as SampleCoverVideo
        sampleCoverVideo.loadCoverImage(mCoverOriginUrl, mDefaultRes)
        return gsyBaseVideoPlayer
    }

    override fun showSmallVideo(size: Point, actionBar: Boolean, statusBar: Boolean): GSYBaseVideoPlayer { //下面这里替换成你自己的强制转化
        val sampleCoverVideo = super.showSmallVideo(size, actionBar, statusBar) as SampleCoverVideo
        sampleCoverVideo.mStartButton.visibility = View.GONE
        sampleCoverVideo.mStartButton = null
        return sampleCoverVideo
    }

    override fun cloneParams(from: GSYBaseVideoPlayer, to: GSYBaseVideoPlayer) {
        super.cloneParams(from, to)
        val sf = from as SampleCoverVideo
        val st = to as SampleCoverVideo
        st.mShowFullAnimation = sf.mShowFullAnimation
    }

    /**
     * 退出window层播放全屏效果
     */
    override fun clearFullscreenLayout() {
        if (!mFullAnimEnd) {
            return
        }
        mIfCurrentIsFullscreen = false
        var delay = 0
        if (mOrientationUtils != null) {
            delay = mOrientationUtils.backToProtVideo()
            mOrientationUtils.isEnable = false
            if (mOrientationUtils != null) {
                mOrientationUtils.releaseListener()
                mOrientationUtils = null
            }
        }
        if (!mShowFullAnimation) {
            delay = 0
        }
        val vp = CommonUtil.scanForActivity(context).findViewById<ViewGroup>(Window.ID_ANDROID_CONTENT)
        val oldF = vp.findViewById<View>(fullId)
        if (oldF != null) { //此处fix bug#265，推出全屏的时候，虚拟按键问题
            val gsyVideoPlayer = oldF as SampleCoverVideo
            gsyVideoPlayer.mIfCurrentIsFullscreen = false
        }
        if (delay == 0) {
            backToNormal()
        } else {
            postDelayed({ backToNormal() }, delay.toLong())
        }
    }

    /******************* 下方两个重载方法，在播放开始前不屏蔽封面，不需要可屏蔽  */
    override fun onSurfaceUpdated(surface: Surface) {
        super.onSurfaceUpdated(surface)
        if (mThumbImageViewLayout != null && mThumbImageViewLayout.visibility == View.VISIBLE) {
            mThumbImageViewLayout.visibility = View.INVISIBLE
        }
    }

    override fun setViewShowState(view: View, visibility: Int) {
        if (view === mThumbImageViewLayout && visibility != View.VISIBLE) {
            return
        }
        super.setViewShowState(view, visibility)
    }

    override fun onSurfaceAvailable(surface: Surface) {
        super.onSurfaceAvailable(surface)
        if (GSYVideoType.getRenderType() != GSYVideoType.TEXTURE) {
            if (mThumbImageViewLayout != null && mThumbImageViewLayout.visibility == View.VISIBLE) {
                mThumbImageViewLayout.visibility = View.INVISIBLE
            }
        }
    }

    /******************* 下方重载方法，在播放开始不显示底部进度和按键，不需要可屏蔽  */
    protected var byStartedClick = false

    override fun onClickUiToggle() {
        if (mIfCurrentIsFullscreen && mLockCurScreen && mNeedLockFull) {
            setViewShowState(mLockScreen, View.VISIBLE)
            return
        }
        byStartedClick = true
        super.onClickUiToggle()
    }

    override fun changeUiToNormal() {
        super.changeUiToNormal()
        byStartedClick = false
    }

    override fun changeUiToPreparingShow() {
        super.changeUiToPreparingShow()
        Debuger.printfLog("Sample changeUiToPreparingShow")
        setViewShowState(mBottomContainer, View.INVISIBLE)
        setViewShowState(mStartButton, View.INVISIBLE)
    }

    override fun changeUiToPlayingBufferingShow() {
        super.changeUiToPlayingBufferingShow()
        Debuger.printfLog("Sample changeUiToPlayingBufferingShow")
        if (!byStartedClick) {
            setViewShowState(mBottomContainer, View.INVISIBLE)
            setViewShowState(mStartButton, View.INVISIBLE)
        }
    }

    override fun changeUiToPlayingShow() {
        super.changeUiToPlayingShow()
        Debuger.printfLog("Sample changeUiToPlayingShow")
        if (!byStartedClick) {
            setViewShowState(mBottomContainer, View.INVISIBLE)
            setViewShowState(mStartButton, View.INVISIBLE)
        }
    }

    override fun startAfterPrepared() {
        super.startAfterPrepared()
        Debuger.printfLog("Sample startAfterPrepared")
        setViewShowState(mBottomContainer, View.INVISIBLE)
        setViewShowState(mStartButton, View.INVISIBLE)
        setViewShowState(mBottomProgressBar, View.VISIBLE)
    }

    override fun onStartTrackingTouch(seekBar: SeekBar) {
        byStartedClick = true
        super.onStartTrackingTouch(seekBar)
    }
}