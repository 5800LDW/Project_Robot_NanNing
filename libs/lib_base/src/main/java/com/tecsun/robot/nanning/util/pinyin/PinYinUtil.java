package com.tecsun.robot.nanning.util.pinyin;

import com.github.promeg.pinyinhelper.Pinyin;
import com.sanbot.dailyinventory.utils.PinyinUtils;

final public class PinYinUtil {
    private static final String TAG = PinYinUtil.class.getSimpleName();

    /***
     * 返回大写字母
     * 例如:传入"中国" , 返回的是"ZHONGGUO"
     */
    public final static String toPinyin(String Chinese) {
        return Pinyin.toPinyin(Chinese, "");
    }

    public final static String toPinyin(String Chinese, String separator) {
        return Pinyin.toPinyin(Chinese, separator);
    }


    public final static boolean isMatch(String voiceTxt, String[] arr) {
//        String voiceTxtPY = toPinyin(voiceTxt);
//        if (voiceTxtPY.endsWith("。") && voiceTxtPY.length() > 1) {
//            voiceTxtPY = voiceTxtPY.substring(0,voiceTxtPY.length() - 1);
//        }
//        Log.e(TAG, "voiceTxtPY = " + voiceTxtPY);
//        boolean isMatch = false;
//        for (int i = 0; i < arr.length; i++) {
//            Log.e(TAG, "toPinyin(arr[" + i + "] = " + toPinyin(arr[i]));
//            if (toPinyin(arr[i]).contains(voiceTxtPY)) {
//                isMatch = true;
//                break;
//            }
//        }
//        return isMatch;

        return PinyinUtils.INSTANCE.matchVoiceAnswer(voiceTxt, arr, false, false);
    }

//
//    /***
//     * 特殊处理关键词和业务功能音色相近的问题;
//     * @param voiceTxt
//     * @return
//     */
//    public final static boolean isMatchWakeupKey(String voiceTxt) {
//        if (TextUtils.isEmpty(voiceTxt)) {
//            return false;
//        }
//        LogUtil.i(TAG, "voiceTxt = " + voiceTxt);
//        String voiceTxtPY = toPinyin(voiceTxt);
//        if (voiceTxtPY.endsWith("。") && voiceTxtPY.length() > 1) {
//            voiceTxtPY = voiceTxtPY.substring(0, voiceTxtPY.length() - 1);
//        }
//        LogUtil.i(TAG, "voiceTxtPY = " + voiceTxtPY);
//        boolean isMatch = false;
//
//        if (voiceTxtPY.contains(toPinyin(InitContentProvider.getStaticContext().getResources().getString(R.string.base_text_wakeup)))||
//                voiceTxtPY.contains("JINBANGJINBANG")||
//                voiceTxtPY.contains("JINBAOJINBANG") ||
//                voiceTxtPY.contains("JINBANGJINBAO")
//
//
//        ) {
//            LogUtil.i(TAG, "isMatch = true;");
//            isMatch = true;
//        }
//
//        return isMatch;
//    }
}
