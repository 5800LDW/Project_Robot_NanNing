package com.tecsun.robot.bean;

import android.graphics.Bitmap;

public class IDCardBean {
        private String name;
        private String sex;
        private String nation;
        private String birthday;
        private String address;
        private String idCardNo;
        private String grantDept;
        private String userLifeBegin;
        private String userLifeEnd;
        private Bitmap photo;
        private byte[] wlt;
        private static final String[] nationCodeTable = new String[]{"解码错", "汉", "蒙古", "回", "藏", "维吾尔", "苗", "彝", "壮", "布依", "朝鲜", "满", "侗", "瑶", "白", "土家", "哈尼", "哈萨克", "傣", "黎", "傈僳", "佤", "畲", "高山", "拉祜", "水", "东乡", "纳西", "景颇", "柯尔克孜", "土", "达斡尔", "仫佬", "羌", "布朗", "撒拉", "毛南", "仡佬", "锡伯", "阿昌", "普米", "塔吉克", "怒", "乌孜别克", "俄罗斯", "鄂温克", "德昴", "保安", "裕固", "京", "塔塔尔", "独龙", "鄂伦春", "赫哲", "门巴", "珞巴", "基诺", "编码错", "其他", "外国血统"};

        public IDCardBean() {
        }

    public Bitmap getPhoto() {
        return photo;
    }

    public void setPhoto(Bitmap photo) {
        this.photo = photo;
    }

    public String getSex() {
            return getSex(sex);
        }

        public void setSex(String sex) {
            this.sex = sex;
        }

        public String getNationName(String nation) {
            int index;
            if ((index = Integer.parseInt(nation)) > 0 && index <= 56) {
                this.nation = nationCodeTable[index];
            } else if (index == 97) {
                this.nation = "其他";
            } else if (index == 98) {
                this.nation = "外国血统";
            } else {
                this.nation = "编码错";
            }

            return this.nation;
        }

        public String getSex(String sex){
            if (sex.equals("0")){
                return "未知";
            }
            else if(sex.equals("1")){
                return  "男";
            }
            else if (sex.equals("2")){
                return  "女";
            }
            else{
                return  "未说明";
            }
        }

        public String getNation() {
            return getNationName(nation);
        }

        public void setNation(String nation) {
            this.nation = nation;
        }

        public String getBirthday() {
            return this.birthday;
        }

        public void setBirthday(String birthday) {
            this.birthday = birthday;
        }

        public String getAddress() {
            return this.address;
        }

        public void setAddress(String address) {
            this.address = address;
        }

        public String getIdCardNo() {
            return this.idCardNo;
        }

        public void setIdCardNo(String idCardNo) {
            this.idCardNo = idCardNo;
        }

        public String getGrantDept() {
            return this.grantDept;
        }

        public void setGrantDept(String grantDept) {
            this.grantDept = grantDept;
        }

        public String getUserLifeBegin() {
            return this.userLifeBegin;
        }

        public void setUserLifeBegin(String userLifeBegin) {
            this.userLifeBegin = userLifeBegin;
        }

        public String getUserLifeEnd() {
            return this.userLifeEnd;
        }

        public void setUserLifeEnd(String userLifeEnd) {
            this.userLifeEnd = userLifeEnd;
        }

        public byte[] getWlt() {
            return this.wlt;
        }

        public void setWlt(byte[] wlt) {
            this.wlt = wlt;
        }

        public String getName() {
            return this.name;
        }

        public void setName(String name) {
            this.name = name;
        }

        public static class Builder {
            private String name;
            private String sex;
            private String nation;
            private String birthday;
            private String address;
            private String idCardNo;
            private String grantDept;
            private String userLifeBegin;
            private String userLifeEnd;
            private byte[] wlt;

            public Builder() {
            }


        }


}
