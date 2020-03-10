package com.tecsun.robot.bean.sanbao;

import java.util.List;

public class InitiaDataTopic {


    /**
     * answers : [{"answer":"参加考试须交多少费用？"},{"answer":"提交报名信息后多久可以缴费？"},{"answer":"怎么缴纳考试费？"}]
     * question : 请问您咨询的是以下哪个问题？
     * rc : 0
     * session_id : 0
     * type : 0
     * use_num : 0
     */

    private String question;
    private int rc;
    private int session_id;
    private int type;
    private int use_num;
    private List<AnswersBean> answers;

    public String getQuestion() {
        return question;
    }

    public void setQuestion(String question) {
        this.question = question;
    }

    public int getRc() {
        return rc;
    }

    public void setRc(int rc) {
        this.rc = rc;
    }

    public int getSession_id() {
        return session_id;
    }

    public void setSession_id(int session_id) {
        this.session_id = session_id;
    }

    public int getType() {
        return type;
    }

    public void setType(int type) {
        this.type = type;
    }

    public int getUse_num() {
        return use_num;
    }

    public void setUse_num(int use_num) {
        this.use_num = use_num;
    }

    public List<AnswersBean> getAnswers() {
        return answers;
    }

    public void setAnswers(List<AnswersBean> answers) {
        this.answers = answers;
    }

    public static class AnswersBean {
        /**
         * answer : 参加考试须交多少费用？
         */

        private String answer;

        public String getAnswer() {
            return answer;
        }

        public void setAnswer(String answer) {
            this.answer = answer;
        }
    }


    @Override
    public String toString() {
        return "InitiaDataTopic{" +
                "question='" + question + '\'' +
                ", rc=" + rc +
                ", session_id=" + session_id +
                ", type=" + type +
                ", use_num=" + use_num +
                ", answers=" + answers +
                '}';
    }
}
