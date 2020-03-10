package com.tecsun.robot.utils;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Environment;
import android.util.Log;

import com.dev.SerialApi.SerialApi;
import com.tecsun.robot.bean.IDCardBean;

import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.util.Locale;

public class IdCardUtils {

    /**
     * 把十六进制表示的字节数组字符串，转换成十六进制字节数组
     *
     * @param
     * @return byte[]
     */
    public static byte[] hexStr2bytes(String hex) {
        int len = (hex.length() / 2);
        byte[] result = new byte[len];
        char[] achar = hex.toUpperCase().toCharArray();
        for (int i = 0; i < len; i++) {
            int pos = i * 2;
            result[i] = (byte) (hexChar2byte(achar[pos]) << 4 | hexChar2byte(achar[pos + 1]));
        }
        return result;
    }

    /**
     * 把16进制字符[0123456789abcde]（含大小写）转成字节
     *
     * @param c
     * @return
     */
    private static int hexChar2byte(char c) {
        switch (c) {
            case '0':
                return 0;
            case '1':
                return 1;
            case '2':
                return 2;
            case '3':
                return 3;
            case '4':
                return 4;
            case '5':
                return 5;
            case '6':
                return 6;
            case '7':
                return 7;
            case '8':
                return 8;
            case '9':
                return 9;
            case 'a':
            case 'A':
                return 10;
            case 'b':
            case 'B':
                return 11;
            case 'c':
            case 'C':
                return 12;
            case 'd':
            case 'D':
                return 13;
            case 'e':
            case 'E':
                return 14;
            case 'f':
            case 'F':
                return 15;
            default:
                return -1;
        }
    }


    private static final String DIR = "DCIM";
    public static final String IDCARDPHOTO_DIR = Environment.getExternalStorageDirectory().getAbsolutePath() + File.separator + DIR +File.separator ;
    public synchronized IDCardBean getCard(String receivemsg3, long delaytime,Context context){

        IDCardBean info = null;

        if (receivemsg3.length() > 600) {
            Log.e("getCard",""+receivemsg3);
            receivemsg3 = "AAAAAA9669050800009001000400"+receivemsg3;
            Log.e("getCard",""+receivemsg3);
            Log.e("getCard",""+receivemsg3.substring(89,receivemsg3.length()-1));

            Bitmap bitmap = null;
            byte[] buffer = new byte[2400];
            buffer = hexStr2Bytes(receivemsg3);
            byte[] wlt = new byte[1024];

            System.arraycopy(buffer, (10 + 4 + 256), wlt, 0, 1024);

            info = getUserMsg(receivemsg3);
            info.setWlt(wlt);
            //读取身份证照片异常
//            bitmap = decodeIdCardBitmap(context,wlt);
//            info.setPhoto(bitmap);
//            Log.d("输出返回的图片",bitmap+"");
//            saveBitmapFile(bitmap, IDCARDPHOTO_DIR+"000000000.jpg");

        }else{
            return null;
        }
//            }else{
//                return null;
//            }
//        }else{
//            return null;
//        }
        return info;
    }

    public static byte[] hexStr2Bytes(String src) {
        src = src.trim().replace(" ", "").toUpperCase(Locale.US);
        int m = 0;
        int n = 0;
        int iLen = src.length() / 2;
        byte[] ret = new byte[iLen];

        for(int i = 0; i < iLen; ++i) {
             m = i * 2 + 1;
             n = m + 1;
            ret[i] = (byte)(Integer.decode("0x" + src.substring(i * 2, m) + src.substring(m, n)) & 255);
        }

        return ret;
    }

    public static Bitmap decodeIdCardBitmap(Context ctx, byte[] wlt)
    {
        Bitmap bmp=null;
        FileOutputStream fos=null;
//        String bmpPath = ctx.getFileStreamPath("photo.bmp").getAbsolutePath();
        String bmpPath = IDCARDPHOTO_DIR + "photo0.bmp";

        try {

        File file = new File(IDCARDPHOTO_DIR);
        if (!file.exists()){
            file.createNewFile();
        }
//        String wltPath = ctx.getFileStreamPath("photo.bmp").getAbsolutePath();
        String wltPath = IDCARDPHOTO_DIR + "photo1.bmp";


            if((wlt == null))
                return null;

            File wltFile = new File(wltPath);
            //if(wltFile.exists())
            fos = new FileOutputStream(wltFile);
            fos.write(wlt);
            fos.close();
            fos = null;
            File ft = ctx.getApplicationContext().getFilesDir();
            String s= ft.getParentFile().getAbsolutePath();
            File appLibdexso = new File(s+"/lib/libDecodeWlt.so");
            if(!appLibdexso.exists()){
                s="/system";
            }
            int result = SerialApi.DecodeIdBitmap(s, wltPath, bmpPath);
            if (result == 1) {
                File f = new File(bmpPath);
                if (f.exists()) {
                    bmp = BitmapFactory.decodeFile(bmpPath);
                }
            }
        }catch (Exception e) {
            Log.d("图片异常",e.getMessage()+"");
            e.printStackTrace();
            return null;
        }

        return bmp;
    }

    public static void saveBitmapFile(Bitmap bitmap,String path) {
        File file = new File(path);//将要保存图片的路径
        if (file.exists()) file.delete();
        try {
            BufferedOutputStream bos = new BufferedOutputStream(new FileOutputStream(file));
            bitmap.compress(Bitmap.CompressFormat.JPEG, 100, bos);
            bos.flush();
            bos.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public IDCardBean getUserMsg(String photodata) {
        IDCardBean info = new IDCardBean();
        byte[] buffer = new byte[2400];
        buffer = hexStr2bytes(photodata);
        byte sw1, sw2, sw3;
        sw1 = buffer[7];
        sw2 = buffer[8];
        sw3 = buffer[9];

        if (!(sw1 == 0x00 && sw2 == 0x00 && sw3 == (byte) 0x90)) {
            return null;
        }

        byte[] wlt = new byte[1024];

        byte[] id = new byte[36];
//        Log.e("buffer====>",bcd2Str(buffer));
        if (buffer.length<((188+14)*2+90))
            return null;

        int orgadd = 0;
        info.setName(getChineseMsg(buffer,orgadd,30,"姓名"));

        orgadd = 158;
        info.setGrantDept(getChineseMsg(buffer,orgadd,30,"签发机关"));

        orgadd = 52;
        info.setAddress(getChineseMsg(buffer,orgadd,70,"家庭住址"));

        String sec = photodata.substring(89,90);
        Log.e("usermsg-"+"性别代码"+"：",sec);
        info.setSex(sec);

        String nation = photodata.substring(90,98).replaceAll("003","");
        Log.e("usermsg-"+"民族代码"+"：",nation);
        info.setNation(nation);

        String idNo = photodata.substring(136*2+1,136*2+70).replaceAll("003","").replaceAll("0058","X");
        Log.e("usermsg-"+"身份证号"+"：",idNo);
        info.setIdCardNo(idNo);

        String birthDate = photodata.substring(51*2-1,51*2+28).replaceAll("003","");
        Log.e("usermsg-"+"出生年月"+"：",birthDate);
        info.setBirthday(birthDate);

        String certdate = photodata.substring((187+14)*2+3,(187+14)*2+64).replaceAll("003","");
        Log.e("usermsg-"+"证件有效期"+"：",certdate);
        info.setUserLifeBegin(certdate.substring(0,8));
        info.setUserLifeEnd(certdate.substring(8,16));

        return info;
    }


    private String getChineseMsg(byte[] buffer, int orgadd, int length, String optionName){
        byte[] name = new byte[length];
        System.arraycopy(buffer, 14+orgadd , name, 0, length);
        String str = "";
        try {
//            byte[] unibyte = getName_Bytearr(name);
//
//            if (unibyte == null) {
//                return str;
//            }
            str = new String(name,"UTF-16LE");
//            str = new String(unibyte, "unicode");
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }
        Log.e("usermsg-"+optionName+"：",str);
        return str;
    }

}
