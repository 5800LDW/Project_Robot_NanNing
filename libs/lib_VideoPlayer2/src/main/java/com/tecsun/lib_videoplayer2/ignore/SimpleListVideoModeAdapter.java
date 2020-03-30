//package com.tecsun.lib_videoplayer2;
//
//
//import android.content.Context;
//import android.os.Environment;
//import android.view.LayoutInflater;
//import android.view.View;
//import android.view.ViewGroup;
//import android.widget.BaseAdapter;
//
//import com.shuyu.gsyvideoplayer.utils.OrientationUtils;
//import com.shuyu.gsyvideoplayer.video.StandardGSYVideoPlayer;
//import com.tecsun.robot.nanning.util.log.LogUtil;
//
//import java.util.ArrayList;
//import java.util.List;
//
//public class SimpleListVideoModeAdapter extends BaseAdapter {
//
//    public static final String TAG = "SimpleListVideoModeAdapter";
//
//    private List<VideoModel> list = new ArrayList<>();
//    private LayoutInflater inflater;
//    private Context context;
//
//    private StandardGSYVideoPlayer curPlayer;
//
//    protected OrientationUtils orientationUtils;
//
//    protected boolean isPlay;
//
//    protected boolean isFull;
//
//    public SimpleListVideoModeAdapter(Context context) {
//        super();
//        this.context = context;
//        inflater = LayoutInflater.from(context);
//        for (int i = 0; i < 40; i++) {
//            list.add(new VideoModel());
//        }
//
//    }
//
//    @Override
//    public int getCount() {
//        return list.size();
//    }
//
//    @Override
//    public Object getItem(int position) {
//        return null;
//    }
//
//    @Override
//    public long getItemId(int position) {
//        return 0;
//    }
//
//    @Override
//    public View getView(final int position, View convertView, ViewGroup parent) {
//        final ViewHolder holder;
//        if (convertView == null) {
//            holder = new ViewHolder();
//            convertView = inflater.inflate(R.layout.list_video_simple_mode1, null);
//            holder.gsyVideoPlayer = (SampleCoverVideo) convertView.findViewById(R.id.video_item_player);
//            convertView.setTag(holder);
//        } else {
//            holder = (ViewHolder) convertView.getTag();
//        }
//
//        final String urlH = "file://" + Environment.getExternalStorageDirectory().getPath() + "/Documents/shebao.mp4";;
//        final String urlV = "file://" + Environment.getExternalStorageDirectory().getPath() + "/Documents/全心出击.mp4";;
//        final String url = (position % 2 == 0) ? urlH : urlV;
//
//        if (position % 2 == 0) {
//            holder.gsyVideoPlayer.loadCoverImage(url, R.mipmap.xxx1);
//        } else {
//            holder.gsyVideoPlayer.loadCoverImage(url, R.mipmap.xxx2);
//        }
//
//        holder.gsyVideoPlayer.setUpLazy(url, true, null, null, "这是title");
//        //增加title
//        holder.gsyVideoPlayer.getTitleTextView().setVisibility(View.GONE);
//        //设置返回键
//        holder.gsyVideoPlayer.getBackButton().setVisibility(View.GONE);
//        //设置全屏按键功能
//        holder.gsyVideoPlayer.getFullscreenButton().setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                LogUtil.e(TAG,">>>>>>>>>>>>>>>>>>>> onClick(View v) ");
//                holder.gsyVideoPlayer.startWindowFullscreen(context, false, true);
//            }
//        });
//        //防止错位设置
//        holder.gsyVideoPlayer.setPlayTag(TAG);
//        holder.gsyVideoPlayer.setPlayPosition(position);
//        //是否根据视频尺寸，自动选择竖屏全屏或者横屏全屏
//        holder.gsyVideoPlayer.setAutoFullWithSize(true);
//        //音频焦点冲突时是否释放
//        holder.gsyVideoPlayer.setReleaseWhenLossAudio(false);
//        //全屏动画
//        holder.gsyVideoPlayer.setShowFullAnimation(true);
//        //小屏时不触摸滑动
//        holder.gsyVideoPlayer.setIsTouchWiget(false);
//        //全屏是否需要lock功能
//        return convertView;
//
//    }
//
//    class ViewHolder {
//        SampleCoverVideo gsyVideoPlayer;
//    }
//
//}
