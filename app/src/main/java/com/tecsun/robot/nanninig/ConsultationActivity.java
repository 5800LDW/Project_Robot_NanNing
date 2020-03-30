package com.tecsun.robot.nanninig;

import android.content.Context;
import android.os.Bundle;
import android.os.Handler;
import android.text.Spannable;
import android.text.SpannableStringBuilder;
import android.text.TextUtils;
import android.text.method.ScrollingMovementMethod;
import android.text.style.TextAppearanceSpan;
import android.view.Gravity;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatDialog;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.google.gson.Gson;
import com.sanbot.opensdk.function.beans.speech.Grammar;
import com.sanbot.opensdk.function.beans.speech.RecognizeTextBean;
import com.sanbot.opensdk.function.unit.interfaces.speech.WakenListener;
import com.tecsun.robot.adapter.ConsultationAdapter;
import com.tecsun.robot.bean.sanbao.InitiaDataTopic;
import com.tecsun.robot.bean.sanbao.InitialDataAnswer;
import com.tecsun.robot.builder.ListeningAnimationBuilder;
import com.tecsun.robot.nanning.lib_base.BaseActivity;
import com.tecsun.robot.nanning.lib_base.BaseRecognizeListener;
import com.tecsun.robot.nanning.util.log.LogUtil;
import com.tecsun.robot.nanning.util.pinyin.PinYinUtil;
import com.tecsun.robot.nanning.widget.SingleClickListener;

import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;

import static android.view.KeyEvent.KEYCODE_BACK;
import static com.tecsun.robot.nanning.builder.TimeBuilder.TIME_1;
import static com.tecsun.robot.nanning.builder.TimeBuilder.TIME_2;

/**
 * 业务咨询
 *
 * @author liudongwen
 * @date 2020/02/27
 */
final public class ConsultationActivity extends BaseActivity {

    private final static String TAG = ConsultationActivity.class.getSimpleName();

    private TextView tvTop1, tvQuestion, tvAppItemAnswer;

    private Button bt1, bt2, bt3, bt4, btQuitTopic;

    private RecyclerView rvConsultation;

    private View flAppItemAnswer, llBottomQuestions;

    private ListeningAnimationBuilder lab = new ListeningAnimationBuilder(this);

    private final ArrayList<InitiaDataTopic.AnswersBean> answerBeans = new ArrayList<>(5);

    private ConsultationAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.app_activity_consultation);
        //点击间隔改为700毫秒
        SingleClickListener.setTimeInterval(1200L);
        llBottomQuestions = findViewById(R.id.llBottomQuestions);
        tvAppItemAnswer = findViewById(R.id.tvAppItemAnswer);
        flAppItemAnswer = findViewById(R.id.flAppItemAnswer);
        rvConsultation = findViewById(R.id.rvConsultation);
        tvTop1 = findViewById(R.id.tvTop1);
        tvQuestion = findViewById(R.id.tvQuestion);
        bt1 = findViewById(R.id.bt1);
        bt2 = findViewById(R.id.bt2);
        bt3 = findViewById(R.id.bt3);
        bt4 = findViewById(R.id.bt4);
        //这个是退出话题按钮, 退出话题的办法:说退出，或者等一会。或者连续三次交互就会结束掉;
        //进入话题之后对其他问题是没法处理的;所以加了个按钮
        btQuitTopic = findViewById(R.id.btQuitTopic);
//        findViewById(R.id.vClose).setOnClickListener(v -> myFinish());
        findViewById(R.id.vMore).setOnClickListener(v -> {

                }
        );

        View vClose = findViewById(R.id.vClose);
        vClose.setOnClickListener(new SingleClickListener() {
            @Override
            public void onSingleClick(View v) {
                myFinish();
            }
        });

        bt1.setOnClickListener(
                new SingleClickListener() {
                    @Override
                    public void onSingleClick(View v) {
                        //showToast(bt1.getText().toString());
                        sendQuestion(bt1.getText().toString());
                    }
                }
        );
        bt2.setOnClickListener(new SingleClickListener() {
            @Override
            public void onSingleClick(View v) {
                //showToast(bt2.getText().toString());
                sendQuestion(bt2.getText().toString());
            }


        });
        bt3.setOnClickListener(new SingleClickListener() {
            @Override
            public void onSingleClick(View v) {
                //showToast(bt3.getText().toString());
                sendQuestion(bt3.getText().toString());
            }

        });
        bt4.setOnClickListener(new SingleClickListener() {
            @Override
            public void onSingleClick(View v) {
                //showToast(bt4.getText().toString());
                sendQuestion(bt4.getText().toString());
            }

        });
        btQuitTopic.setOnClickListener(new SingleClickListener() {
            @Override
            public void onSingleClick(View v) {
                sendQuestion("退出");
            }
        });

        findViewById(R.id.appItemPageBackTitle).setVisibility(View.GONE);
        findViewById(R.id.appItemPageBack).setOnClickListener(v -> vClose.performClick());

//        findViewById(R.id.flListener).setOnClickListener(v -> {
//            speechManagerWakeUp();
//        });
//        findViewById(R.id.flListenerLogo).setOnClickListener(v -> speechManagerWakeUp());

        rvConsultation.setLayoutManager(new LinearLayoutManager(this));
        adapter = new ConsultationAdapter(answerBeans, true);
        rvConsultation.setAdapter(adapter);
        adapter.setOnItemClickListener(answersItemClickListener);
        adapter.loadMoreComplete();


        init();

        setSpeakManager();

        registerNetWorkMonitor(null, null);

        registerTimeMonitor(s -> {
            if (!isFinishing() && isSpeaking() && timeBuilder != null) {
                timeBuilder.initTime();
            } else if (s == TIME_2) {
                myFinish();
            } else if (s == TIME_1) {
                init();
            }
        }).startBiz();


    }

    private final void init() {
        final String text = String.format(getResources().getString(R.string.app_text_consultation_top1), getResources().getString(R.string.app_text_wakeup));
        SpannableStringBuilder spannable = new SpannableStringBuilder(text);
        TextAppearanceSpan textAppearanceSpan = new TextAppearanceSpan(this, R.style.style_text_1);
        int index1 = text.indexOf(getResources().getString(R.string.app_text_wakeup)) - 1;
        int end1 = index1 + getResources().getString(R.string.app_text_wakeup).length() + 2;
        spannable.setSpan(textAppearanceSpan, index1, end1, Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);
        tvTop1.setText(spannable);

        tvQuestion.setText(getResources().getString(R.string.app_text_may_i_help_you));
        showDefaultQuestions();
    }

    private void showDefaultQuestions() {
        String[] arr = getResources().getStringArray(R.array.app_arr_consultation_questions);
        answerBeans.clear();
        for (int i = 0; i < arr.length; i++) {
            InitiaDataTopic.AnswersBean bean = new InitiaDataTopic.AnswersBean();
            bean.setAnswer(arr[i]);
            answerBeans.add(bean);
        }
        runOnUiThread(() -> {
            rvConsultation.setVisibility(View.VISIBLE);
            flAppItemAnswer.setVisibility(View.GONE);
            llBottomQuestions.setVisibility(View.VISIBLE);
            btQuitTopic.setVisibility(View.GONE);
        });
        adapter.setHideLine(true).setNewData(answerBeans);
        adapter.notifyDataSetChanged();
    }


    private boolean isFirstShow = true;

    @Override
    protected void onRobotServiceConnected() {
        super.onRobotServiceConnected();
        setSpeakManager();
        setHardWareManager();

        if (isFirstShow) {
            new Handler().postDelayed(new Runnable() {
                @Override
                public void run() {
                    speakAndCheckComplete("您好，请问您要咨询什么问题？您可以对我说“养老金怎么申领”或者点击屏幕向我提问。", new SpeakComplete() {
                        @Override
                        public void biz() {
                            speechManagerWakeUp();
                        }
                    });
                }
            }, 10);
            isFirstShow = false;
        }
    }

    private final void setSpeakManager() {
        //设置唤醒，休眠回调
        speechManager.setOnSpeechListener(new WakenListener() {
            @Override
            public void onWakeUp() {
                lab.myWakeup();
            }

            @Override
            public void onSleep() {
                lab.mySleep();
            }

            @Override
            public void onWakeUpStatus(boolean b) {
            }
        });

        speechManager.setOnSpeechListener(new BaseRecognizeListener() {
            @Override
            public void voiceRecognizeText(String voiceTXT) {

                LogUtil.i(TAG, "voiceRecognizeText = " + voiceTXT);

                //计时归零
                if (timeBuilder != null) {
                    timeBuilder.initTime();
                }

                if (isFinishing()) {
                    myStopSpeak();
                    return;
                }

                if (PinYinUtil.isMatch(voiceTXT, getResources().getStringArray(R.array.app_arr_back))) {
                    myFinish();
                }
            }

            @Override
            public void onRecognizeText(@NonNull RecognizeTextBean recognizeTextBean) {
                super.onRecognizeText(recognizeTextBean);
                LogUtil.i(TAG, "tvSpeechRecognizeResult: " + recognizeTextBean.getText());
            }

            @Override
            public boolean onRecognizeResult(@NotNull Grammar grammar) {
//                Log.i(TAG, "Grammar = " + grammar.getText());

                LogUtil.i(TAG,
                        "开始打印Grammar" + "\n" +
                                "EGN_DUMI = " + grammar.EGN_DUMI + "\n" +
                                "EGN_IFLYTEK = " + grammar.EGN_IFLYTEK + "\n" +
                                "EGN_QAA = " + grammar.EGN_QAA + "\n" +
                                "EGN_ZHIYIN = " + grammar.EGN_ZHIYIN + "\n" +

                                "action = " + grammar.getAction() + "\n" +
                                "engine = " + grammar.getEngine() + "\n" +
                                "initialData = " + grammar.getInitialData() + "\n" +
                                "isLast = " + grammar.isLast() + "\n" +
                                "params = " + grammar.getParams() + "\n" +
                                "route = " + grammar.getRoute() + "\n" +
                                "text = " + grammar.getText() + "\n" +
                                "topic = " + grammar.getTopic() + "\n" +
                                "打印结束" + "\n");

                if (isSpeaking() || isFinishing()) {
                    return true;
                }

                if (grammar != null && PinYinUtil.isMatch(grammar.getText(), getResources().getStringArray(R.array.app_arr_back))) {
                    return true;
                } else {

                    //不过滤关灯指令
                    if (grammar.getText().startsWith("{") && grammar.getText().endsWith("}") && grammar.getText().contains("关灯")) {
                        return false;
                    }
                    //过滤指令
                    else if (grammar.getText().startsWith("{") && grammar.getText().endsWith("}")) {
                        return true;
                    }

                    //小话题
                    try {
                        InitiaDataTopic initiaDataTopic = new Gson().fromJson(grammar.getInitialData(), InitiaDataTopic.class);
                        LogUtil.e(TAG, ">>>>>>>>>>>>>>>>>>> initiaDataTopic = " + initiaDataTopic);
                        if (initiaDataTopic != null && initiaDataTopic.getAnswers() != null && initiaDataTopic.getAnswers().size() != 0) {
                            return topicBiz(grammar, initiaDataTopic);
                        }
                    } catch (Exception e) {
                        LogUtil.e(TAG, e);
                    }

                    //小话题的子答案
                    try {
                        InitialDataAnswer initialDataAnswer = new Gson().fromJson(grammar.getInitialData(), InitialDataAnswer.class);
                        LogUtil.e(TAG, ">>>>>>>>>>>>>>>>>>> initialDataAnswer = " + initialDataAnswer);
                        if (initialDataAnswer != null && initialDataAnswer.getResult() != null && initialDataAnswer.getResult().getAns_text() != null) {

                            return defaultChatBiz(grammar, initialDataAnswer.getResult().getAns_text(), initialDataAnswer.getType());
                        }
                    } catch (Exception e) {
                        LogUtil.e(TAG, e);
                    }


                    String answer = "";
                    //显示内容
                    if (grammar != null && grammar.getParams() != null &&
                            grammar.getParams().containsKey("answer") &&
                            !TextUtils.isEmpty(grammar.getParams().get("answer"))) {

                        answer = grammar.getParams().get("answer") + "";
                    }
                    //普通对话
                    return defaultChatBiz(grammar, answer, -1);
                }
            }

            @Override
            public void onStartRecognize() {
                super.onStartRecognize();
                lab.myWakeup();
            }

            @Override
            public void onStopRecognize() {
                super.onStopRecognize();
                lab.mySleep();
            }
        });
    }


    private final boolean defaultChatBiz(Grammar grammar, String speakContent, int type) {

        runOnUiThread(() -> {
//            String answer = "";
//            //显示内容
//            if (grammar != null && grammar.getParams() != null &&
//                    grammar.getParams().containsKey("answer") &&
//                    !TextUtils.isEmpty(grammar.getParams().get("answer"))) {
//
//                answer = grammar.getParams().get("answer") + "";
//            }

            //显示标题
            if (grammar != null && !TextUtils.isEmpty(grammar.getText())) {
                tvQuestion.setText(grammar.getText());
//            speakAndCheckComplete(grammar.getText(),()->speechManagerWakeUp());
                speakAndCheckComplete(speakContent, () -> speechManagerWakeUp());
            }

            flAppItemAnswer.setVisibility(View.VISIBLE);
            rvConsultation.setVisibility(View.GONE);
            llBottomQuestions.setVisibility(View.VISIBLE);
            btQuitTopic.setVisibility(View.GONE);
            tvAppItemAnswer.setMovementMethod(ScrollingMovementMethod.getInstance());
//            tvAppItemAnswer.setText(answer);
            tvAppItemAnswer.setText(speakContent);


            if (type == 1 &&
                    speakContent.contains("好的") &&
                    speakContent.contains("退出小话题")) {
                init();
            }

        });

        return true;
    }


    private final boolean topicBiz(Grammar grammar, InitiaDataTopic initiaDataTopic) {

        runOnUiThread(() -> {
            //显示标题
            if (grammar != null && !TextUtils.isEmpty(initiaDataTopic.getQuestion())) {
                tvQuestion.setText(initiaDataTopic.getQuestion());
                speakAndCheckComplete(initiaDataTopic.getQuestion(), () -> speechManagerWakeUp());
            }
            rvConsultation.setVisibility(View.VISIBLE);
            flAppItemAnswer.setVisibility(View.GONE);
            llBottomQuestions.setVisibility(View.GONE);
            btQuitTopic.setVisibility(View.VISIBLE);

            answerBeans.clear();
            answerBeans.addAll(initiaDataTopic.getAnswers());
            adapter.setHideLine(false).setNewData(answerBeans);
            adapter.notifyDataSetChanged();
        });


        return true;
    }

    private Long timeInternal = 0L;

    private final BaseQuickAdapter.OnItemClickListener answersItemClickListener = (adapter, view, position) -> {
        if (System.currentTimeMillis() - timeInternal > SingleClickListener.getTimeInterval()) {
            timeInternal = System.currentTimeMillis();
            if (position < answerBeans.size()) {
                sendQuestion(answerBeans.get(position).getAnswer());
            }
        }
    };

    private final void setHardWareManager() {
        if (hardWareManager != null) {
//            //人脸识别回调
//            hdCameraManager.setMediaListener(new FaceRecognizeListener() {
//                @Override
//                public void recognizeResult(List<FaceRecognizeBean> list) {
//                    runOnUiThread(() -> {
//                        speechManagerWakeUp();
//
//                    });
//                }
//            });

            //关闭白光灯
            hardWareManager.switchWhiteLight(false);
        }
    }


    private long lastTimeLog = 0;

    @Override
    public void myFinish() {
        if (timeBuilder != null) {
            timeBuilder.removeBiz();
        }
        //        super.myFinish();

        if (System.currentTimeMillis() - lastTimeLog > SingleClickListener.getTimeInterval()) {
            lastTimeLog = System.currentTimeMillis();
            new Handler().postDelayed(new Runnable() {
                @Override
                public void run() {
                    ConsultationActivity.super.myFinish();
                }
            }, 200);
        }

    }

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if (keyCode == KEYCODE_BACK && event.getAction() == KeyEvent.ACTION_DOWN) {
            myFinish();
        }
        return super.onKeyDown(keyCode, event);
    }

//    @Override
//    protected void onResume() {
//        super.onResume();
//        new Handler().postDelayed(new Runnable() {
//            @Override
//            public void run() {
//                showLoadingDialog();
//            }
//        },2000);
//
//    }

    private void showLoadingDialog() {
        LayoutInflater inflater = (LayoutInflater) getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        AppCompatDialog dialog = new AppCompatDialog(this, R.style.TransparentDialogStyle);
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        View layout = inflater.inflate(R.layout.app_dialog_loading, null);
        dialog.addContentView(layout, new ViewGroup.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT));
        dialog.show();

        View appDialogLoadingFL = dialog.findViewById(R.id.appDialogLoadingFL);
        appDialogLoadingFL.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog.dismiss();
            }
        });

        Window window = dialog.getWindow();
        window.setGravity(Gravity.TOP);
//        window.setWindowAnimations(R.style.dialog_animation);
        window.getDecorView().setPadding(0, 0, 0, 0);

        WindowManager.LayoutParams lp = window.getAttributes();
        lp.width = WindowManager.LayoutParams.MATCH_PARENT;
        lp.height = WindowManager.LayoutParams.MATCH_PARENT;
//        window.setAttributes(lp);

        // 前2 个flag设置dialog 显示到状态栏    第三个设置点击dialog以外的蒙层 不抢夺焦点  响应点击事件
        lp.flags = WindowManager.LayoutParams.FLAG_FULLSCREEN | WindowManager.LayoutParams.FLAG_LAYOUT_IN_SCREEN | WindowManager.LayoutParams.FLAG_NOT_FOCUSABLE;
        lp.dimAmount = 0.0f;
        window.setAttributes(lp);

    }

}
















