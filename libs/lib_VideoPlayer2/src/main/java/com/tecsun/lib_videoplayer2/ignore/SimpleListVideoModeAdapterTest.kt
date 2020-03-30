//package com.tecsun.lib_videoplayer2
//
//import android.content.Context
//import android.os.Environment
//import android.view.LayoutInflater
//import android.view.View
//import android.view.ViewGroup
//import android.widget.BaseAdapter
//import com.shuyu.gsyvideoplayer.utils.OrientationUtils
//import com.shuyu.gsyvideoplayer.video.StandardGSYVideoPlayer
//import java.util.*
//
//class SimpleListVideoModeAdapterTest(private val context: Context) : BaseAdapter() {
//    private val list: MutableList<VideoModel> = ArrayList()
//    private val inflater: LayoutInflater
//    private val curPlayer: StandardGSYVideoPlayer? = null
//    protected var orientationUtils: OrientationUtils? = null
//    protected var isPlay = false
//    protected var isFull = false
//    override fun getCount(): Int {
//        return list.size
//    }
//
//    override fun getItem(position: Int): Any {
//        return null
//    }
//
//    override fun getItemId(position: Int): Long {
//        return 0
//    }
//
//    override fun getView(position: Int, convertView: View, parent: ViewGroup): View {
//        var convertView = convertView
//        val holder: ViewHolder
//        if (convertView == null) {
//            holder = ViewHolder()
//            convertView = inflater.inflate(R.layout.list_video_simple_mode1, null)
//            holder.gsyVideoPlayer = convertView.findViewById<View>(R.id.video_item_player) as SampleCoverVideo
//            convertView.tag = holder
//        } else {
//            holder = convertView.tag as ViewHolder
//        }
//        val urlH = "file://" + Environment.getExternalStorageDirectory().path + "/Documents/shebao.mp4"
//        val urlV = "file://" + Environment.getExternalStorageDirectory().path + "/Documents/全心出击.mp4"
//        val url = if (position % 2 == 0) urlH else urlV
//        if (position % 2 == 0) {
//            holder.gsyVideoPlayer!!.loadCoverImage(url, R.mipmap.xxx1)
//        } else {
//            holder.gsyVideoPlayer!!.loadCoverImage(url, R.mipmap.xxx2)
//        }
//        holder.gsyVideoPlayer!!.setUpLazy(url, true, null, null, "这是title")
//        //增加title
//        holder.gsyVideoPlayer!!.titleTextView.visibility = View.GONE
//        //设置返回键
//        holder.gsyVideoPlayer!!.backButton.visibility = View.GONE
//        //设置全屏按键功能
//        holder.gsyVideoPlayer!!.fullscreenButton.setOnClickListener { holder.gsyVideoPlayer!!.startWindowFullscreen(context, false, true) }
//        //防止错位设置
//        holder.gsyVideoPlayer!!.playTag = TAG
//        holder.gsyVideoPlayer!!.playPosition = position
//        //是否根据视频尺寸，自动选择竖屏全屏或者横屏全屏
//        holder.gsyVideoPlayer!!.isAutoFullWithSize = true
//        //音频焦点冲突时是否释放
//        holder.gsyVideoPlayer!!.isReleaseWhenLossAudio = false
//        //全屏动画
//        holder.gsyVideoPlayer!!.isShowFullAnimation = true
//        //小屏时不触摸滑动
//        holder.gsyVideoPlayer!!.setIsTouchWiget(false)
//        //全屏是否需要lock功能
//        return convertView
//    }
//
//    internal inner class ViewHolder {
//        var gsyVideoPlayer: SampleCoverVideo? = null
//    }
//
//    companion object {
//        const val TAG = "ListNormalAdapter22"
//    }
//
//    init {
//        inflater = LayoutInflater.from(context)
//        for (i in 0..39) {
//            list.add(VideoModel())
//        }
//    }
//}