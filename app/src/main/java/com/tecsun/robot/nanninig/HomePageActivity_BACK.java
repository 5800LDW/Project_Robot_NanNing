//package com.tecsun.robot.nanninig;
//
//import android.os.Bundle;
//import android.os.Handler;
//import android.os.Message;
//import android.text.TextUtils;
//import android.util.Log;
//import android.view.KeyEvent;
//import android.view.SurfaceHolder;
//import android.view.SurfaceView;
//import android.view.View;
//import android.widget.ImageView;
//import android.widget.TextView;
//
//import androidx.annotation.NonNull;
//
//import com.google.gson.Gson;
//import com.sanbot.opensdk.beans.FuncConstant;
//import com.sanbot.opensdk.beans.OperationResult;
//import com.sanbot.opensdk.function.beans.FaceRecognizeBean;
//import com.sanbot.opensdk.function.beans.StreamOption;
//import com.sanbot.opensdk.function.beans.speech.Grammar;
//import com.sanbot.opensdk.function.beans.speech.RecognizeTextBean;
//import com.sanbot.opensdk.function.unit.HardWareManager;
//import com.sanbot.opensdk.function.unit.interfaces.hardware.InfrareListener;
//import com.sanbot.opensdk.function.unit.interfaces.hardware.PIRListener;
//import com.sanbot.opensdk.function.unit.interfaces.media.FaceRecognizeListener;
//import com.sanbot.opensdk.function.unit.interfaces.media.MediaStreamListener;
//import com.sanbot.opensdk.function.unit.interfaces.speech.RecognizeListener;
//import com.sanbot.opensdk.function.unit.interfaces.speech.WakenListener;
//import com.tecsun.robot.builder.WelcomeSpeakBuilder;
//import com.tecsun.robot.nanning.lib_base.BaseActivity;
//import com.tecsun.robot.nanning.util.ExitUtils;
//import com.tecsun.test.VisionMediaDecoder;
//
//import java.nio.ByteBuffer;
//import java.util.ArrayList;
//import java.util.List;
//
//import static android.view.KeyEvent.KEYCODE_BACK;
//
///**
// * 主页
// *
// * @author liudongwen
// * @date 2020/02/27
// */
//public class HomePageActivity_BACK extends BaseActivity implements SurfaceHolder.Callback {
//
//    private static final String TAG = "TAG";
//    private WelcomeSpeakBuilder wsb = new WelcomeSpeakBuilder(this);
//    HardWareManager hardWareManager;
//    private static final int STATE_FREE = 0;
//    private static final int STATE_WORKING = 1;
//    private static final int STATE_PAUSE = 3;
//    private static int currentState = 0;
//    private long lastUnFreeTime = 0;
//    private static final long CHECK_TIME_INTERNAL = 30 * 1000;
//    private Handler stateHandler = new Handler() {
//        @Override
//        public void handleMessage(@NonNull Message msg) {
//            super.handleMessage(msg);
//            currentState = msg.what;
//            if (msg.what == STATE_FREE) {
//            } else if (msg.what == STATE_WORKING) {
//                lastUnFreeTime = System.currentTimeMillis();
//            } else if (msg.what == STATE_PAUSE) {
//                lastUnFreeTime = System.currentTimeMillis();
//            }
//        }
//    };
//
//    private Runnable checkRunnable = () -> {
//        checkTime();
//    };
//
//    private void checkTime() {
//        //非空闲态(每次讲话都会刷新初始时间)和非讲话
//        if (STATE_FREE != currentState &&
//                STATE_PAUSE != currentState &&
//                (System.currentTimeMillis() - lastUnFreeTime > (CHECK_TIME_INTERNAL - 1)) &&
//                !isSpeaking()
//        ) {
//            stateHandler.sendEmptyMessage(STATE_FREE);
//        }
//
//        stateHandler.postDelayed(checkRunnable, CHECK_TIME_INTERNAL);
//    }
//
//    private VisionMediaDecoder mediaDecoder;
//
//    private int mWidth, mHeight;
//    private int type = 1;
//    private List<Integer> handleList = new ArrayList<>();
//    TextView tvFace;
//    SurfaceView svMedia;
//    TextView tvCapture;
//    ImageView iv_capture;
//
//    int iii = 2;
//
//    @Override
//    protected void onCreate(Bundle savedInstanceState) {
//        super.onCreate(savedInstanceState);
//        setContentView(R.layout.app_activity_home_page);
//        findViewById(R.id.ll01).setOnClickListener(v -> myStartActivity(ConsultationActivity.class));
//        findViewById(R.id.ll02).setOnClickListener(v -> myStartActivity(Mainactivity.class));
//        findViewById(R.id.ll04).setOnClickListener(v -> myStartActivity(EntertainmentActivity.class));
//        findViewById(R.id.flBG).setOnClickListener(v -> speechManagerWakeUp());
//
//
//        svMedia = findViewById(R.id.sv_media);
//        tvCapture = findViewById(R.id.tv_capture);
//        svMedia.getHolder().addCallback(this);
//        iv_capture = findViewById(R.id.iv_capture);
//        tvCapture.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                //toreImage(mediaManager.getVideoImage());
////                iv_capture.setImageBitmap(hdCameraManager.getVideoImage());
//                iii++;
//                if (iii % 2 == 0) {
//                    svMedia.setVisibility(View.INVISIBLE);
//                } else {
//                    svMedia.setVisibility(View.VISIBLE);
//                }
//
//            }
//        });
//        mediaDecoder = new VisionMediaDecoder();
//        tvFace = findViewById(R.id.tv_face);
//
//        initListener();
//
//        //人脸识别回调
//        hdCameraManager.setMediaListener(new FaceRecognizeListener() {
//            @Override
//            public void recognizeResult(List<FaceRecognizeBean> list) {
//                StringBuilder sb = new StringBuilder();
//                for (FaceRecognizeBean bean : list) {
//                    sb.append(new Gson().toJson(bean));
//                    sb.append("\n");
//
//                    if (currentState == STATE_FREE) {
//                        wsb.delaySpeak(bean, new WelcomeSpeakBuilder.StartListener() {
//                            @Override
//                            public void start() {
//                                speechManagerWakeUp();
//                                speakListen();
//                            }
//                        });
//                    }
//                }
//
//                Log.e(TAG, "人脸识别回调 >>>>>>>>>>>>>>>>" + sb.toString());
//            }
//        });
//
//        //设置唤醒，休眠回调
//        speechManager.setOnSpeechListener(new WakenListener() {
//            @Override
//            public void onWakeUp() {
//                Log.e(TAG, "onWakeUp >>>>>>>>>>>>>>>>");
//            }
//
//            @Override
//            public void onSleep() {
//                Log.e(TAG, "onSleep >>>>>>>>>>>>>>>> ");
//            }
//
//            @Override
//            public void onWakeUpStatus(boolean b) {
//                Log.e(TAG, "onWakeUpStatus >>>>>>>>>>>>>>>> = " + b);
//            }
//        });
//
//        stateHandler.sendEmptyMessage(STATE_FREE);
//        checkTime();
//
//        hardWareManager = (HardWareManager) getUnitManager(FuncConstant.HARDWARE_MANAGER);
//        hardWareManager.setOnHareWareListener(new PIRListener() {
//            @Override
//            public void onPIRCheckResult(boolean isCheck,int part) {
////                Log.e("TAGTAG", (part == 1 ? "正面" : "背后") + "PIR被触发");
//            }
//        });
//
//        hardWareManager.setOnHareWareListener(new InfrareListener() {
//            @Override
//            public void infrareDistance(int part, int distance) {
//                if (distance != 0) {
////                    System.out.print("部位" + part + "的距离为" + distance);
//                    Log.e("TAGTAG", "部位" + part + "的距离为" + distance);
//                }
//            }
//        });
//    }
//
//    @Override
//    protected void onResume() {
//        super.onResume();
//        speechManagerWakeUp();
//    }
//
//    @Override
//    protected void onRestart() {
//        super.onRestart();
//        stateHandler.sendEmptyMessage(STATE_WORKING);
//    }
//
//    @Override
//    protected void onPause() {
//        stateHandler.sendEmptyMessage(STATE_PAUSE);
//        super.onPause();
//    }
//
//    private final void speakListen() {
//        //监听语音
//        speechManager.setOnSpeechListener(new RecognizeListener() {
//            //后台返回数据
//            @Override
//            public boolean onRecognizeResult(@NonNull Grammar grammar) {
//
//                stateHandler.sendEmptyMessage(STATE_WORKING);
//
//                //只有在配置了RECOGNIZE_MODE为1，且返回为true的情况下，才会拦截
//                Log.i(TAG, "onRecognizeResult: " + grammar.getText());
//
//                Log.i(TAG,
//                        "EGN_DUMI = " + grammar.EGN_DUMI + "\n" +
//                                "EGN_IFLYTEK = " + grammar.EGN_IFLYTEK + "\n" +
//                                "EGN_QAA = " + grammar.EGN_QAA + "\n" +
//                                "EGN_ZHIYIN = " + grammar.EGN_ZHIYIN + "\n" +
//
//                                "action = " + grammar.getAction() + "\n" +
//                                "engine = " + grammar.getEngine() + "\n" +
//                                "initialData = " + grammar.getInitialData() + "\n" +
//                                "isLast = " + grammar.isLast() + "\n" +
//                                "params = " + grammar.getParams() + "\n" +
//                                "route = " + grammar.getRoute() + "\n" +
//                                "text = " + grammar.getText() + "\n" +
//                                "topic = " + grammar.getTopic() + "\n");
//
//
//                if (grammar.getTopic().equals("test_topic")) {
//                    speechManager.startSpeak("接收到了自定义语义");
//                    return true;
//                }
//                return false;
//            }
//
//            //识别用户语音
//            @Override
//            public void onRecognizeText(@NonNull RecognizeTextBean recognizeTextBean) {
//                if(recognizeTextBean!=null && !TextUtils.isEmpty(recognizeTextBean.getText())){
//                    stateHandler.sendEmptyMessage(STATE_WORKING);
//                }
//                Log.i(TAG, "tvSpeechRecognizeResult: " + recognizeTextBean.getText());
//            }
//
//            @Override
//            public void onRecognizeVolume(int i) {
//                Log.i(TAG, "onRecognizeVolume: " + String.valueOf(i));
//            }
//
//            @Override
//            public void onStartRecognize() {
//                Log.i(TAG, "onStartRecognize: ");
//            }
//
//            @Override
//            public void onStopRecognize() {
//                //TODO 停止识别语音的时候的逻辑处理;
//                Log.i(TAG, "onStopRecognize: ");
//            }
//
//            @Override
//            public void onError(int i, int i1) {
//                Log.i(TAG, "onError: i=" + i + " i1=" + i1);
//            }
//        });
//    }
//
//
//    private long exitTime = 0;
//
//    @Override
//    public boolean onKeyDown(int keyCode, KeyEvent event) {
//        if (keyCode == KEYCODE_BACK && event.getAction() == KeyEvent.ACTION_DOWN) {
//            if (System.currentTimeMillis() - exitTime > 2000) {
//                showToast(getString(R.string.app_drop_out));
//                exitTime = System.currentTimeMillis();
//            } else {
//                finish();
//            }
//            return true;
//        }
//        return super.onKeyDown(keyCode, event);
//    }
//
//    @Override
//    protected void onDestroy() {
//
//
//        Log.i(TAG, "surfaceDestroyed: ");
//        //关闭媒体流
//        if (handleList != null && handleList.size() > 0) {
//            for (int handle : handleList) {
//                Log.i(TAG, "surfaceDestroyed: " + hdCameraManager.closeStream(handle));
//            }
//        }
//        handleList = null;
//        mediaDecoder.stopDecoding();
//
//        super.onDestroy();
//        ExitUtils.INSTANCE.exit();
//    }
//
//
//    /**
//     * 初始化监听器
//     */
//    private void initListener() {
//        hdCameraManager.setMediaListener(new MediaStreamListener() {
//            @Override
//            public void getVideoStream(int handle, byte[] bytes, int width, int height) {
//                if (mediaDecoder != null) {
//                    if (width != mWidth || height != mHeight) {
//                        mediaDecoder.onCreateCodec(width, height);
//                        mWidth = width;
//                        mHeight = height;
//                    }
//                    mediaDecoder.drawVideoSample(ByteBuffer.wrap(bytes));
////                    Log.i(TAG, "getVideoStream: 视频数据:" + bytes.length);
//                }
//            }
//
//            @Override
//            public void getAudioStream(int i, byte[] bytes) {
//
//            }
//        });
//        hdCameraManager.setMediaListener(new FaceRecognizeListener() {
//            @Override
//            public void recognizeResult(List<FaceRecognizeBean> list) {
//                StringBuilder sb = new StringBuilder();
//                for (FaceRecognizeBean bean : list) {
//                    sb.append(new Gson().toJson(bean));
//                    sb.append("\n");
//                }
//                tvFace.setText(sb.toString());
//                Log.e("TAG", sb.toString());
//            }
//        });
//    }
//
//
//    @Override
//    public void surfaceCreated(SurfaceHolder holder) {
//        //设置参数并打开媒体流
//        StreamOption streamOption = new StreamOption();
//        if (type == 1) {
//            streamOption.setChannel(StreamOption.MAIN_STREAM);
//        } else {
//            streamOption.setChannel(StreamOption.SUB_STREAM);
//        }
//        streamOption.setDecodType(StreamOption.HARDWARE_DECODE);
//        streamOption.setJustIframe(false);
//        OperationResult operationResult = hdCameraManager.openStream(streamOption);
//        Log.i(TAG, "surfaceCreated: operationResult=" + operationResult.getResult());
//        int result = Integer.valueOf(operationResult.getResult());
//        if (result != -1) {
//            handleList.add(result);
//        }
//        mediaDecoder.setSurface(holder.getSurface());
//    }
//
//    @Override
//    public void surfaceChanged(SurfaceHolder holder, int format, int width, int height) {
//
//    }
//
//    @Override
//    public void surfaceDestroyed(SurfaceHolder holder) {
//        Log.i(TAG, "surfaceDestroyed: ");
//        //关闭媒体流
//        if (handleList.size() > 0) {
//            for (int handle : handleList) {
//                Log.i(TAG, "surfaceDestroyed: " + hdCameraManager.closeStream(handle));
//            }
//        }
//        handleList = null;
//        mediaDecoder.stopDecoding();
//
//    }
//
//
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
//
//
//
