package com.tecsun.robot.adapter

import android.view.View
import android.widget.TextView
import com.chad.library.adapter.base.BaseQuickAdapter
import com.chad.library.adapter.base.BaseViewHolder
import com.tecsun.robot.bean.sanbao.InitiaDataTopic
import com.tecsun.robot.nanning.pinyin.InitContentProvider
import com.tecsun.robot.nanninig.R

class ConsultationAdapter(data: List<InitiaDataTopic.AnswersBean>?, private var hideLine: Boolean = false) :
        BaseQuickAdapter<InitiaDataTopic.AnswersBean, BaseViewHolder>(
                R.layout.app_item_consultation_answer, data
        ) {

    override fun convert(helper: BaseViewHolder?, item: InitiaDataTopic.AnswersBean?) {
        with(item) {

            if (item?.answer ?: " " == (getString(R.string.app_text_consultation_question1)) ||
                    item?.answer ?: " " == (getString(R.string.app_text_consultation_question2)) ||
                    item?.answer ?: " " == (getString(R.string.app_text_consultation_question3)) ||
                    item?.answer ?: " " == (getString(R.string.app_text_consultation_question4))
            ) {
                helper?.getView<TextView>(R.id.tvAppItemAnswer)?.text = "${(helper?.layoutPosition
                        ?: 0).inc()}.${item?.answer
                        ?: ""}?"
            } else {
                helper?.getView<TextView>(R.id.tvAppItemAnswer)?.text = item?.answer ?: ""
            }

            if (!hideLine) {
                helper?.getView<View>(R.id.vAppLine)?.visibility = View.VISIBLE
            } else {
                helper?.getView<View>(R.id.vAppLine)?.visibility = View.INVISIBLE
            }

        }
    }

    fun setHideLine(boolean: Boolean): ConsultationAdapter {
        hideLine = boolean;
        return this;
    }

    private fun getString(i: Int): String {
        return InitContentProvider.getStaticContext().resources.getString(i) ?: ""

    }
}