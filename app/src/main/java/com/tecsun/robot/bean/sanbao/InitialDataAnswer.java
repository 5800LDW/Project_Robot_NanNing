package com.tecsun.robot.bean.sanbao;

public class InitialDataAnswer {


    /**
     * rc : 0
     * result : {"ans_text":"可在\u201c广西人事考试网\u201d考务通知栏目查看具体考试考务通知中的\u201c收费标准\u201d。查看报名表中的报考科目及金额。"}
     * session_id : 0
     * type : 1
     * use_num : 0
     */

    private int rc;
    private ResultBean result;
    private int session_id;
    private int type;
    private int use_num;

    public int getRc() {
        return rc;
    }

    public void setRc(int rc) {
        this.rc = rc;
    }

    public ResultBean getResult() {
        return result;
    }

    public void setResult(ResultBean result) {
        this.result = result;
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

    public static class ResultBean {
        /**
         * ans_text : 可在“广西人事考试网”考务通知栏目查看具体考试考务通知中的“收费标准”。查看报名表中的报考科目及金额。
         */

        private String ans_text;

        public String getAns_text() {
            return ans_text;
        }

        public void setAns_text(String ans_text) {
            this.ans_text = ans_text;
        }
    }
}


