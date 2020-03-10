package com.tecsun.robot.nanning.util.pinyin;

import net.sourceforge.pinyin4j.PinyinHelper;
import net.sourceforge.pinyin4j.format.HanyuPinyinCaseType;
import net.sourceforge.pinyin4j.format.HanyuPinyinOutputFormat;
import net.sourceforge.pinyin4j.format.HanyuPinyinToneType;

/**
 * 汉字转拼音工具类
 * Created by futg on 2017/10/12 14:07.
 * Email:
 */

public class Cn2Spell {
    private static StringBuffer sb = new StringBuffer();

    /**
     * 获取汉字字符串的首字母，英文字符不变
     * 例如：阿飞→af
     */
    public static String getPinYinHeadChar(String chines) {
        sb.setLength(0);
        char[] chars = chines.toCharArray();
        HanyuPinyinOutputFormat defaultFormat = new HanyuPinyinOutputFormat();
        defaultFormat.setCaseType(HanyuPinyinCaseType.LOWERCASE);
        defaultFormat.setToneType(HanyuPinyinToneType.WITHOUT_TONE);
        for (int i = 0; i < chars.length; i++) {
            if (chars[i] > 128) {
                try {
                    sb.append(PinyinHelper.toHanyuPinyinStringArray(chars[i], defaultFormat)[0].charAt(0));
                } catch (Exception e) {
                    e.printStackTrace();
                }
            } else {
                sb.append(chars[i]);
            }
        }
        return sb.toString();
    }

    /**
     * 获取汉字字符串的第一个字母
     */
    public static String getPinYinFirstLetter(String str) {
        sb.setLength(0);
        char c = str.charAt(0);
        String[] pinyinArray = PinyinHelper.toHanyuPinyinStringArray(c);
        if (pinyinArray != null) {
            sb.append(pinyinArray[0].charAt(0));
        } else {
            sb.append(c);
        }
        return sb.toString();
    }

    /**
     * 获取汉字字符串的汉语拼音，英文字符不变
     */
    public static String getPinYin(String chines) {
        return getPinYinWithSpace(chines, "");
    }

    /**
     * 获取汉字字符串的汉语拼音，英文字符不变
     */
    public static String getPinYinWithSpace(String chines, String space) {
        sb.setLength(0);
        char[] nameChar = chines.toCharArray();
        HanyuPinyinOutputFormat defaultFormat = new HanyuPinyinOutputFormat();
        defaultFormat.setCaseType(HanyuPinyinCaseType.LOWERCASE);
        defaultFormat.setToneType(HanyuPinyinToneType.WITHOUT_TONE);
        for (int i = 0; i < nameChar.length; i++) {
            if (nameChar[i] > 128) {
                try {
                    sb.append(PinyinHelper.toHanyuPinyinStringArray(nameChar[i], defaultFormat)[0] + space);
                } catch (Exception e) {
//                    e.printStackTrace();
                }
            } else {
                sb.append(nameChar[i]);
            }
        }
        return sb.toString();
    }

    /**
     * 获取汉字字符串的模糊汉语拼音，英文字符不变
     * 模糊音是专为对某些音节容易混淆的人所设计的。当启用了模糊音后，例如sh<-->s，输入“si”也可以出来“十”，输入“shi”也可以出来“四”。
     * 参考搜狗支持的模糊音有：
     * 声母模糊音：s <--> sh，c<-->ch，z <-->zh，l<-->n，f<-->h，r<-->l，
     * 韵母模糊音：an<-->ang，en<-->eng，in<-->ing，ian<-->iang，uan<-->uang。
     */
    public static String[] getFuzzyPinYinWithSpace(String chines, String space) {
        String allPinyinString = getPinYinWithSpace(chines, space).toLowerCase();
        String[] allPinyins = allPinyinString.trim().split(space);
        int pinyinLen = allPinyins.length;
        if (pinyinLen <= 0) {
            return null;
        }
        for (int i = 0; i < pinyinLen; i++) {
            String py = allPinyins[i];
            py = py.replace("zh", "z");
            py = py.replace("sh", "s");
            py = py.replace("ch", "c");
            py = py.replace("l", "n");
            py = py.replace("f", "h");
            py = py.replace("r", "l");
            py = py.replace("ng", "n");
            allPinyins[i] = py;
        }
        return allPinyins;
    }

}