package com.tecsun.jc.base.utils

import android.graphics.Color
import android.text.Spannable
import android.text.SpannableStringBuilder
import android.text.method.LinkMovementMethod
import android.text.style.ForegroundColorSpan
import android.widget.TextView

/**
 * @author liudongwen
 * @date 2019/9/26
 */
object TextViewUtil {
    private const val TAG = "TextViewUtil"
    private const val textForChangeColor = "(建设中)"
    fun setPartTextChangeColor(tv: TextView, str1: String) {
        if (str1 != null && str1.contains(textForChangeColor)) {
            val foregroundColorSpan = ForegroundColorSpan(Color.parseColor("#e60012"))
            val index = str1.indexOf(textForChangeColor)
            if (index != -1 && tv != null) {
                var spannable = SpannableStringBuilder(str1)
                spannable.setSpan(
                    foregroundColorSpan,
                    index,
                    str1.length,
                    Spannable.SPAN_EXCLUSIVE_EXCLUSIVE
                )
                tv.movementMethod = LinkMovementMethod.getInstance()
                tv.text = spannable
            }
        }
    }
}

