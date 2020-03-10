package com.sanbot.dailyinventory.utils

import com.tecsun.robot.nanning.util.log.LogUtil
import com.tecsun.robot.nanning.util.pinyin.Cn2Spell

/**
 *  Created by jixiaoyong1995@gmail.com
 *  Data: 2020/2/27.
 *  Description:
 */
internal object PinyinUtils {


    /* 命令词匹配百分比
    * 越大，匹配准确性越高，匹配灵敏度越低*/
    const val COMMAND_MATCH_PERCENTAGE = 0.75F

    const val answer = "这个是要匹配的文字"
     val arr = arrayOf("这","个","是","预置","的","命令词等等")

    @JvmStatic
    fun main(args: Array<String>) {
        // isMatch是否匹配上了
        val isMatch = matchVoiceAnswer(answer, arr)
    }


    /**
     * 根据语音识别结果，是否匹配指定项
     *
     * @param msg 语音识别结果
     * @param targetContext 待匹配项目
     * @param isCompleteMath 是否要完全匹配
     * @param isPrefix 是否只匹配开头
     * @return 是否匹配指定项
     */
    fun matchVoiceAnswer(
        msg: String?,
        targetContext: Array<String>,
        isCompleteMath: Boolean = false,
        isPrefix: Boolean = false
    ): Boolean {
        if (msg.isNullOrBlank() || targetContext.isNullOrEmpty()) {
            return false
        }

        val mSpace = " "
        val msgPinyins = Cn2Spell.getFuzzyPinYinWithSpace(msg, mSpace)
        targetContext.filter {
            !it.isBlank()
        }.map {
            Cn2Spell.getFuzzyPinYinWithSpace(it, mSpace)
        }.forEachIndexed { index, target ->
            // target zhè gè shì dài pǐ pèi de zì zǔ
            // msgPinyins zhè gè shì yǔ yīn shí bié de jié guǒ
            LogUtil.e(
                "target:${target?.joinToString(mSpace)}" +
                        ",msgPinyins:${msgPinyins?.joinToString(mSpace)}"
            )

            if (target.isNullOrEmpty() || msgPinyins.isNullOrEmpty()) {
                return@forEachIndexed
            }

            //如果是全匹配，则对比每个拼音是否完全一致
            if (isCompleteMath) {
                if (msgPinyins.contentToString() == target.contentToString()) {
                    return true
                } else {
                    return@forEachIndexed
                }
            }

            //如果是前缀，则匹配是否是以‘前缀’开头
            if (isPrefix) {
                if (msgPinyins[0] == target[0]) {
                    return true
                } else {
                    return@forEachIndexed
                }
            }

            //模糊匹配，以匹配的拼音个数计分
            var score = 0
            val targetCopy = target.toMutableList()
            msgPinyins.forEach msgForEach@{ p ->
                var equalIndex = -1
                targetCopy.forEachIndexed targetForEach@{ i, t ->
                    if (t == p) {
                        score++
                        equalIndex = i
                        return@targetForEach
                    }
                }
                if (equalIndex != -1) {
                    targetCopy.removeAt(equalIndex)
                    if (targetCopy.isNullOrEmpty()) {
                        return@msgForEach
                    }
                }
            }

            // 命令词匹配的百分比 对短命令词影响较大
            // score 对长命令词影响比较大
            val matchPercentage = score.toFloat().div(target.size.toFloat())
            if (score >= 3 || matchPercentage >= COMMAND_MATCH_PERCENTAGE) {
                LogUtil.e("match result:${targetContext[index]} == msg:$msg score:$score matchPercentage:$matchPercentage")
                return true
            }
        }

        return false
    }
}