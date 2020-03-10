//package com.tecsun.robot.nanninig;
//
//import android.os.Bundle;
//import android.os.Handler;
//import android.text.Spannable;
//import android.text.SpannableStringBuilder;
//import android.text.TextUtils;
//import android.text.method.ScrollingMovementMethod;
//import android.text.style.TextAppearanceSpan;
//import android.view.View;
//import android.widget.Button;
//import android.widget.LinearLayout;
//import android.widget.TextView;
//
//import androidx.annotation.NonNull;
//
//import com.google.gson.Gson;
//import com.sanbot.opensdk.function.beans.speech.Grammar;
//import com.sanbot.opensdk.function.beans.speech.RecognizeTextBean;
//import com.sanbot.opensdk.function.unit.interfaces.speech.WakenListener;
//import com.tecsun.robot.bean.sanbao.InitiaDataTopic;
//import com.tecsun.robot.builder.ListeningAnimationBuilder;
//import com.tecsun.robot.nanning.lib_base.BaseActivity;
//import com.tecsun.robot.nanning.lib_base.BaseRecognizeListener;
//import com.tecsun.robot.nanning.util.log.LogUtil;
//import com.tecsun.robot.nanning.util.pinyin.PinYinUtil;
//import com.tecsun.robot.nanning.widget.SingleClickListener;
//
//import org.jetbrains.annotations.NotNull;
//
///**
// * 业务咨询
// *
// * @author liudongwen
// * @date 2020/02/27
// */
//final public class ConsultationActivityBack extends BaseActivity {
//
//    private final static String TAG = ConsultationActivityBack.class.getSimpleName();
//
//    private LinearLayout llQuestion;
//
//    private TextView tvTop1, tvQuestion;
//
//    private Button bt1, bt2, bt3, bt4;
//
//    private ListeningAnimationBuilder lab = new ListeningAnimationBuilder(this);
//
//    @Override
//    protected void onCreate(Bundle savedInstanceState) {
//        super.onCreate(savedInstanceState);
//        setContentView(R.layout.app_activity_consultation);
//        llQuestion = findViewById(R.id.llQuestion);
//        tvTop1 = findViewById(R.id.tvTop1);
//        tvQuestion = findViewById(R.id.tvQuestion);
//        bt1 = findViewById(R.id.bt1);
//        bt2 = findViewById(R.id.bt2);
//        bt3 = findViewById(R.id.bt3);
//        bt4 = findViewById(R.id.bt4);
//        findViewById(R.id.vClose).setOnClickListener(v -> myFinish());
//        findViewById(R.id.vMore).setOnClickListener(v -> {
//
//                }
//        );
//        bt1.setOnClickListener(
//                new SingleClickListener() {
//                    @Override
//                    public void onSingleClick(View v) {
//                        //showToast(bt1.getText().toString());
//                        sendQuestion(bt1.getText().toString());
//                    }
//                }
//        );
//        bt2.setOnClickListener(new SingleClickListener() {
//            @Override
//            public void onSingleClick(View v) {
//                //showToast(bt2.getText().toString());
//                sendQuestion(bt2.getText().toString());
//            }
//
//
//        });
//        bt3.setOnClickListener(new SingleClickListener() {
//            @Override
//            public void onSingleClick(View v) {
//                //showToast(bt3.getText().toString());
//                sendQuestion(bt3.getText().toString());
//            }
//
//        });
//        bt4.setOnClickListener(new SingleClickListener() {
//            @Override
//            public void onSingleClick(View v) {
//                //showToast(bt4.getText().toString());
//                sendQuestion(bt4.getText().toString());
//            }
//
//        });
//        findViewById(R.id.flListener).setOnClickListener(v -> {
//            speechManagerWakeUp();
//        });
//        findViewById(R.id.flListenerLogo).setOnClickListener(v -> speechManagerWakeUp());
//
//        init();
//        setSpeakManager();
//
//        registerNetWorkMonitor(null, null);
//
//    }
//
//    private final void init() {
//        final String text = String.format(getResources().getString(R.string.app_text_consultation_top1), getResources().getString(R.string.app_text_wakeup));
//        SpannableStringBuilder spannable = new SpannableStringBuilder(text);
//        TextAppearanceSpan textAppearanceSpan = new TextAppearanceSpan(this, R.style.style_text_1);
//        int index1 = text.indexOf(getResources().getString(R.string.app_text_wakeup)) - 1;
//        int end1 = index1 + getResources().getString(R.string.app_text_wakeup).length() + 2;
//        spannable.setSpan(textAppearanceSpan, index1, end1, Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);
//        tvTop1.setText(spannable);
//
//        //////
//
//        String[] arr = getResources().getStringArray(R.array.app_arr_consultation_questions);
//        for (int i = 0; i < arr.length; i++) {
//            View view = View.inflate(this, R.layout.item_consultation_question, null);
//            final String tex = arr[i];
//            final TextView tv1 = ((TextView) view.findViewById(R.id.tv_01));
//            tv1.setText(tex);
//            view.setOnClickListener(
//                    new SingleClickListener() {
//                        @Override
//                        public void onSingleClick(View v) {
//                            //showToast(tex);
//                            sendQuestion(tv1.getText().toString().substring(2, tv1.getText().length() - 1));
//                        }
//                    }
//            );
//            llQuestion.addView(view);
//        }
//
//    }
//
//
//    private boolean isFirstShow = true;
//
//    @Override
//    protected void onRobotServiceConnected() {
//        super.onRobotServiceConnected();
//        setSpeakManager();
//
//        if (isFirstShow) {
//            new Handler().postDelayed(new Runnable() {
//                @Override
//                public void run() {
//                    speakAndCheckComplete("您好，请问您要咨询什么问题？您可以对我说“养老金怎么申领”或者点击屏幕向我提问。", new SpeakComplete() {
//                        @Override
//                        public void biz() {
//                            speechManagerWakeUp();
//                        }
//                    });
//                }
//            }, 10);
//            isFirstShow = false;
//        }
//    }
//
//    private final void setSpeakManager() {
//        //设置唤醒，休眠回调
//        speechManager.setOnSpeechListener(new WakenListener() {
//            @Override
//            public void onWakeUp() {
//                lab.myWakeup();
//            }
//
//            @Override
//            public void onSleep() {
//                lab.mySleep();
//            }
//
//            @Override
//            public void onWakeUpStatus(boolean b) {
//            }
//        });
//
//        speechManager.setOnSpeechListener(new BaseRecognizeListener() {
//            @Override
//            public void voiceRecognizeText(String voiceTXT) {
//                if (PinYinUtil.isMatch(voiceTXT, getResources().getStringArray(R.array.app_arr_back))) {
//                    myFinish();
//                }
//                LogUtil.i(TAG, "voiceRecognizeText = " + voiceTXT);
//            }
//
//            @Override
//            public void onRecognizeText(@NonNull RecognizeTextBean recognizeTextBean) {
//                super.onRecognizeText(recognizeTextBean);
//                LogUtil.i(TAG, "tvSpeechRecognizeResult: " + recognizeTextBean.getText());
//            }
//
//            @Override
//            public boolean onRecognizeResult(@NotNull Grammar grammar) {
////                Log.i(TAG, "Grammar = " + grammar.getText());
//
//                LogUtil.i(TAG,
//                        "开始打印Grammar" + "\n" +
//                                "EGN_DUMI = " + grammar.EGN_DUMI + "\n" +
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
//                                "topic = " + grammar.getTopic() + "\n" +
//                                "打印结束" + "\n");
//
//                if (isSpeaking()) {
//                    return true;
//                }
//
//                if (grammar != null && PinYinUtil.isMatch(grammar.getText(), getResources().getStringArray(R.array.app_arr_back))) {
//                    return true;
//                } else {
//
//                    //过滤指令
//                    if (grammar.getText().startsWith("{") && grammar.getText().endsWith("}")) {
//                        return true;
//                    }
//
//                    //是不是小话题
//                    boolean isTopic = false;
//
//                    //是不是小话题
//                    InitiaDataTopic initiaDataTopic = null;
//                    try {
//                        initiaDataTopic = new Gson().fromJson(grammar.getInitialData(), InitiaDataTopic.class);
//                        LogUtil.e(TAG, ">>>>>>>>>>>>>>>>>>> initiaDataTopic = " + initiaDataTopic);
//                        isTopic = true;
//                    } catch (Exception e) {
//                        LogUtil.e(TAG, e);
//                    }
//
//                    //小话题
//                    if (isTopic && initiaDataTopic != null && initiaDataTopic.getSession_id() == 0) {
//                        int size = initiaDataTopic.getAnswers().size();
//
//
//
//
//
//
//                        return false;
//                    }
//
//
//                    //普通对话
//                    return defaultChatBiz(grammar);
//                }
//            }
//
//            @Override
//            public void onStartRecognize() {
//                super.onStartRecognize();
//                lab.myWakeup();
//            }
//
//            @Override
//            public void onStopRecognize() {
//                super.onStopRecognize();
//                lab.mySleep();
//            }
//        });
//    }
//
//
//    private final boolean defaultChatBiz(Grammar grammar) {
//        //显示标题
//        if (grammar != null && !TextUtils.isEmpty(grammar.getText())) {
//            tvQuestion.setText(grammar.getText());
//        }
//
//        //显示内容
//        if (grammar != null && grammar.getParams() != null &&
//                grammar.getParams().containsKey("answer") &&
//                !TextUtils.isEmpty(grammar.getParams().get("answer"))) {
//
//
//            String answer = grammar.getParams().get("answer");
//            llQuestion.removeAllViews();
//
//            runOnUiThread(() -> {
//                View view = View.inflate(ConsultationActivityBack.this, R.layout.app_item_consultation_answer, null);
//                final TextView appItemAnswer = ((TextView) view.findViewById(R.id.appItemAnswer));
//                appItemAnswer.setMovementMethod(ScrollingMovementMethod.getInstance());
//                appItemAnswer.setText(answer);
//                llQuestion.addView(view);
//            });
//        }
//        return false;
//    }
//
//
//    private final boolean topicBiz(Grammar grammar) {
//
//        return false;
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
//
//
//
