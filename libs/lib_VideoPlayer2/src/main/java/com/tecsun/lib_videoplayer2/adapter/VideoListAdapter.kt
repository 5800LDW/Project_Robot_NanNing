package com.tecsun.lib_videoplayer2.adapter

import android.view.View
import android.widget.FrameLayout
import android.widget.TextView
import com.chad.library.adapter.base.BaseQuickAdapter
import com.chad.library.adapter.base.BaseViewHolder
import com.tecsun.lib_videoplayer2.R
import com.tecsun.lib_videoplayer2.SampleCoverVideo
import com.tecsun.lib_videoplayer2.VideoModel
import com.tecsun.lib_videoplayer2.listener.MyVideoAllCallBack
import com.tecsun.lib_videoplayer2.ui.SimpleListVideoActivityModeRV
import com.tecsun.robot.nanning.util.log.LogUtil
import java.util.*


class VideoListAdapter(private val activity: SimpleListVideoActivityModeRV, data: List<VideoModel>?) :
        BaseQuickAdapter<VideoModel, BaseViewHolder>(
                R.layout.list_video_simple_mode1, data) {

    companion object {
         val TAG = VideoListAdapter::class.java.simpleName
    }

    private val hashMap = HashMap<Int?, BaseViewHolder?>()

    override fun convert(holder: BaseViewHolder?, item: VideoModel) {
        with(item) {
            holder?.getView<SampleCoverVideo>(R.id.video_item_player)?.apply {
                loadCoverImage(item.url, R.drawable.video_player2_normal_bg);
                setUpLazy(url, true, null, null, title)
                //title
                titleTextView.visibility = View.VISIBLE
                titleTextView.text = item.title ?: ""
                //返回键
                backButton.visibility = View.GONE
                //全屏
                fullscreenButton.setOnClickListener {
                    startWindowFullscreen(activity, false, true);
                }
                //防止错位设置
//                playTag = "$TAG${holder.layoutPosition}"
                playTag = TAG
                playPosition = holder.layoutPosition
                //是否根据视频尺寸，自动选择竖屏全屏或者横屏全屏
                isAutoFullWithSize = true;
                //音频焦点冲突
                isReleaseWhenLossAudio = false;
                //全屏动画
                isShowFullAnimation = true;
                //小屏时不触摸滑动
                setIsTouchWiget(false)

                setVideoAllCallBack(videoAllCallBack)
            }

            holder?.getView<TextView>(R.id.listVideoModeTV)?.text = item.title ?: ""
        }

        holder?.apply {
            getView<View>(R.id.flContentInCover)?.setOnClickListener {
                holder?.getView<View>(R.id.start).performClick()
                holder?.getView<SampleCoverVideo>(R.id.video_item_player)?.startWindowFullscreen(mContext, true, true);

            }

            getView<View>(R.id.llListVideoContent)?.setOnClickListener {
                holder?.getView<FrameLayout>(R.id.flContentInCover)?.performClick()
            }

            getView<TextView>(R.id.listVideoSimpleTVNum)?.text = holder?.layoutPosition?.inc().toString()

            hashMap[layoutPosition] = holder

        }
    }

    fun onVoiceClick(p: Int) {
        LogUtil.e("TAG!!!", "hashMap.size" + hashMap.size)
        if (hashMap.containsKey(p) && hashMap[p] != null) {
            hashMap[p]?.getView<View>(R.id.llListVideoContent)?.performClick()
        }
    }

    fun onReleaseCallBack(){
        //释放所有
        for( (key,value) in hashMap){
            value?.getView<SampleCoverVideo>(R.id.video_item_player)?.setVideoAllCallBack(null)
        }
        hashMap.clear()
    }

    private var videoAllCallBack = MyVideoAllCallBack(activity);

}
















