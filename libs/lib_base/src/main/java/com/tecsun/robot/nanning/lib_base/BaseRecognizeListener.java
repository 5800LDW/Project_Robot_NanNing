package com.tecsun.robot.nanning.lib_base;

import android.os.Handler;
import android.os.Looper;

import androidx.annotation.NonNull;

import com.sanbot.opensdk.function.beans.speech.Grammar;
import com.sanbot.opensdk.function.beans.speech.RecognizeTextBean;
import com.sanbot.opensdk.function.unit.interfaces.speech.RecognizeListener;
import com.tecsun.jc.base.utils.ToastUtils;
import com.tecsun.robot.nanning.pinyin.InitContentProvider;
import com.tecsun.robot.nanning.util.log.LogUtil;

import org.jetbrains.annotations.NotNull;

public class BaseRecognizeListener implements RecognizeListener {
    private static final String TAG = BaseRecognizeListener.class.getSimpleName();

    @Override
    public void onRecognizeText(@NonNull RecognizeTextBean recognizeTextBean) {
        LogUtil.i(TAG, "tvSpeechRecognizeResult: " + recognizeTextBean.getText());

        if (recognizeTextBean != null && recognizeTextBean.getText() != null) {
            voiceRecognizeText(recognizeTextBean.getText());
        }
    }

    //声音大小
    @Override
    public void onRecognizeVolume(int i) {
//        LogUtil.i(TAG, "onRecognizeVolume: " + String.valueOf(i));
    }

    @Override
    public void onStartRecognize() {

        LogUtil.i(TAG, "onStartRecognize: ");
    }

    @Override
    public void onStopRecognize() {
        LogUtil.i(TAG, "onStopRecognize: ");
    }

    @Override
    public void onError(int i, int i1) {
        LogUtil.i(TAG, "onError: i=" + i + " i1=" + i1);
        if (i1 == 10114) {
            new Handler(Looper.myLooper()).post(() -> ToastUtils.INSTANCE.showGravityShortToast(
                    InitContentProvider.getStaticContext(),
                    "无法连接服务器"));
        }
    }

    @Override
    public boolean onRecognizeResult(@NotNull Grammar grammar) {
        LogUtil.i(TAG, "Grammar = " + grammar.getText());
        return true;
    }

    public void voiceRecognizeText(String voiceResultText) {

    }
}











