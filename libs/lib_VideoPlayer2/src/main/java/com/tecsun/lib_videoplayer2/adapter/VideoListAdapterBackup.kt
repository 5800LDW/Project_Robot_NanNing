//package com.tecsun.lib_videoplayer2.adapter
//
//import android.view.View
//import android.widget.FrameLayout
//import com.chad.library.adapter.base.BaseQuickAdapter
//import com.chad.library.adapter.base.BaseViewHolder
//import com.tecsun.lib_videoplayer2.R
//import com.tecsun.lib_videoplayer2.SampleCoverVideo
//import com.tecsun.lib_videoplayer2.SimpleListVideoModeAdapter
//import com.tecsun.lib_videoplayer2.VideoModel
//import com.tecsun.robot.nanning.lib_base.BaseActivity
//import com.tecsun.robot.nanning.util.log.LogUtil
//
//class VideoListAdapterBackup(private val activity: BaseActivity, data: List<VideoModel>?) :
//        BaseQuickAdapter<VideoModel, BaseViewHolder>(
//                R.layout.list_video_simple_mode1, data) {
//
//    private val TAG = VideoListAdapterBackup::class.java.simpleName
//
//    override fun convert(holder: BaseViewHolder?, item: VideoModel) {
//        with(item) {
//            holder?.getView<SampleCoverVideo>(R.id.video_item_player)?.apply {
//                loadCoverImage(item.url, R.mipmap.xxx1);
//                setUpLazy(url, true, null, null, title)
//                //增加title
//                titleTextView.visibility = View.GONE
//                //设置返回键
//                backButton.visibility = View.GONE
//                //设置全屏按键功能
//                fullscreenButton.setOnClickListener {
//                    startWindowFullscreen(activity, false, true);
//                    LogUtil.e(TAG,">>>>>>>>>>>>>>>>>> fullscreenButton OnClick")
////                    activity.myStartActivity(Intent(activity, SimplePlayerK::class.java).putExtra(VIDEO_TITLE,item.title).putExtra(VIDEO_URL,item.url))
//                }
//                //防止错位设置
//                playTag = SimpleListVideoModeAdapter.TAG
//                playPosition = holder.layoutPosition
//                //是否根据视频尺寸，自动选择竖屏全屏或者横屏全屏
//                isAutoFullWithSize = true;
//                //音频焦点冲突时是否释放
//                isReleaseWhenLossAudio = false;
//                //全屏动画
//                isShowFullAnimation = true;
//                //小屏时不触摸滑动
//                setIsTouchWiget(false)
//            }
//        }
//
////        holder!!.getView<SampleCoverVideo>(R.id.video_item_player)!!.isClickable = false;
//
//        holder!!.getView<FrameLayout>(R.id.flContentInCover)!!.setOnClickListener{
//            LogUtil.e(TAG,">>>>>>>>>>>>>>>>>> OnClickListener")
//            holder?.getView<View>(R.id.start).performClick()
//            holder?.getView<SampleCoverVideo>(R.id.video_item_player)?.startWindowFullscreen(mContext, false, true);
////          activity.myStartActivity(Intent(activity, SimplePlayerK::class.java).putExtra(VIDEO_TITLE,item.title).putExtra(VIDEO_URL,item.url))
//        }
//    }
//}
//
//
//
//
//
//
//
//
//
//
//
//
//
//
//
//
