//package com.tecsun.lib_videoplayer2;
//
//
//import android.Manifest;
//import android.content.pm.ActivityInfo;
//import android.os.Bundle;
//import android.view.View;
//import android.widget.ImageView;
//
//import com.sanbot.opensdk.function.unit.interfaces.speech.WakenListener;
//import com.shuyu.gsyvideoplayer.GSYVideoManager;
//import com.shuyu.gsyvideoplayer.utils.OrientationUtils;
//import com.shuyu.gsyvideoplayer.video.StandardGSYVideoPlayer;
//import com.tecsun.robot.nanning.lib_base.BaseActivity;
//import com.tecsun.robot.nanning.lib_base.BaseRecognizeListener;
//import com.tecsun.robot.nanning.util.log.LogUtil;
//import com.tecsun.robot.nanning.util.pinyin.PinYinUtil;
//
//
//public class SimplePlayer extends BaseActivity {
//
//    private String TAG = SimplePlayer.class.getSimpleName();
//
//    final String[] permissions = {Manifest.permission.WRITE_EXTERNAL_STORAGE, Manifest.permission.READ_EXTERNAL_STORAGE};
//
//    StandardGSYVideoPlayer videoPlayer;
//
//    OrientationUtils orientationUtils;
//
//private String url ;
//private String title ;
//    @Override
//    protected void onCreate(Bundle savedInstanceState) {
//        super.onCreate(savedInstanceState);
//        setContentView(R.layout.activity_simple_play);
////        boolean hadPermission = PermissionsUtils.startRequestPermission(this, permissions, 101);
//
//
//        url =  getIntent().getStringExtra(VideoConstant.VIDEO_URL);
//        title =  getIntent().getStringExtra(VideoConstant.VIDEO_TITLE)+"  ";
//
//
//
//        LogUtil.e(TAG, ">>>>>>>>>>>>>> onCreate()");
//        init();
//
//
//    }
//
//
//    private void init() {
//
//        if (videoPlayer != null) {
//            videoPlayer.startPlayLogic();
//            LogUtil.e(TAG, ">>>>>>>>>>>>>> startPlayLogic()");
//            return;
//        }
//
//        LogUtil.e(TAG, ">>>>>>>>>>>>>> init()");
//
//        videoPlayer = (StandardGSYVideoPlayer) findViewById(R.id.video_player);
//
////        String source1 = "file://" + Environment.getExternalStorageDirectory().getPath() + "/Documents/shebao.mp4";
//
////        String url = getIntent().getStringExtra("url");
////        String mTitle = getIntent().getStringExtra("title") + "";
//
//
////        String source1 = "http://9890.vod.myqcloud.com/9890_4e292f9a3dd011e6b4078980237cc3d3.f20.mp4";
//        videoPlayer.setUp(url, true, " ");
//
//        //增加封面
//        ImageView imageView = new ImageView(this);
//        imageView.setScaleType(ImageView.ScaleType.CENTER_CROP);
//        imageView.setImageResource(R.mipmap.xxx1);
//        videoPlayer.setThumbImageView(imageView);
//        //增加title
//        videoPlayer.getTitleTextView().setVisibility(View.VISIBLE);
//        //设置返回键
//        videoPlayer.getBackButton().setVisibility(View.VISIBLE);
//        //设置旋转
//        orientationUtils = new OrientationUtils(this, videoPlayer);
//        //设置全屏按键功能,这是使用的是选择屏幕，而不是全屏
//        videoPlayer.getFullscreenButton().setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                orientationUtils.resolveByClick();
//            }
//        });
//        //是否可以滑动调整
//        videoPlayer.setIsTouchWiget(true);
//        //设置返回按键功能
//        videoPlayer.getBackButton().setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                onBackPressed();
//            }
//        });
//
//        //音频焦点冲突时是否释放
//        videoPlayer.setReleaseWhenLossAudio(false);
//
//        videoPlayer.startPlayLogic();
//    }
//
//
//    @Override
//    protected void onPause() {
//        super.onPause();
//        if (videoPlayer != null) {
//            videoPlayer.onVideoPause();
//        }
//
//    }
//
//    @Override
//    protected void onResume() {
//        super.onResume();
//        if (videoPlayer != null) {
//            videoPlayer.onVideoResume();
//        }
//    }
//
//    @Override
//    protected void onDestroy() {
//        super.onDestroy();
//        GSYVideoManager.releaseAllVideos();
//        if (orientationUtils != null) {
//            orientationUtils.releaseListener();
//        }
//    }
//
//
//    @Override
//    public void onBackPressed() {
//        //先返回正常状态
//        if (orientationUtils.getScreenType() == ActivityInfo.SCREEN_ORIENTATION_LANDSCAPE) {
//            videoPlayer.getFullscreenButton().performClick();
//            return;
//        }
//        //释放所有
//        if (videoPlayer != null) {
//            videoPlayer.setVideoAllCallBack(null);
//        }
//        super.onBackPressed();
//    }
//
//    @Override
//    protected void onRobotServiceConnected() {
//        LogUtil.e(TAG, ">>>>>>>>>>>>>> onRobotServiceConnected()");
//        setSpeakManager();
//        speechManagerSleep();
//    }
//
//    private final void setSpeakManager() {
//        //设置唤醒，休眠回调
//        speechManager.setOnSpeechListener(new WakenListener() {
//            @Override
//            public void onWakeUp() {
//                LogUtil.i(TAG, "onWakeUp()");
//                speechManagerSleep();
////                if(videoPlayer!=null && !videoPlayer.isInPlayingState()){
////                    videoPlayer.startPlayLogic();
////                }
////                videoPlayer.requestFocus();
//            }
//
//            @Override
//            public void onSleep() {
//                LogUtil.i(TAG, "onSleep()");
//                if (videoPlayer != null) {
//                    videoPlayer.startPlayLogic();
//                }
//            }
//
//            @Override
//            public void onWakeUpStatus(boolean b) {
//                LogUtil.i(TAG, "onWakeUpStatus = " + b);
//            }
//        });
//
//        //监听到"返回"就自动结束当前Activity
//        speechManager.setOnSpeechListener(new BaseRecognizeListener() {
//            @Override
//            public void voiceRecognizeText(String voiceTXT) {
//
//                if (PinYinUtil.isMatch(voiceTXT, getResources().getStringArray(R.array.app_arr_back))) {
//                    myFinish();
//                } else {
//                    speechManagerSleep();
//                    myStopSpeak();
//                    LogUtil.i(TAG, "voiceRecognizeText = " + voiceTXT);
//                }
//            }
//        });
//
//    }
//}