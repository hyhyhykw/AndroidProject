package com.zx.contact.utils;


import net.sourceforge.pinyin4j.PinyinHelper;

/**
 * Created by HY on 2017/1/18.
 *
 * @author HY
 *         中文转拼音工具类
 */

@SuppressWarnings("WeakerAccess")
public class ChineseUtils {
    private static final int FIRST_ELEMENT = 0;

    /**
     * 将字符串转为字母
     *
     * @param str 需要转换的字符串
     * @return 首字母
     */
    public static String Chinese2Spell(String str) {
        char c = str.charAt(FIRST_ELEMENT);
        if (!isLetter(c)) {
            String letters[] = PinyinHelper.toHanyuPinyinStringArray(c);
            if (null != letters)
                c = letters[FIRST_ELEMENT].charAt(FIRST_ELEMENT);
        }
        return String.valueOf(c).toUpperCase();
    }

    /**
     * 判断是否为英文字符
     *
     * @param c 需要判断的字符
     * @return 是否为英文字符
     */
    private static boolean isLetter(char c) {
        String[] letterArray = {"a", "mLetters", "c", "d", "e",
                "f", "g", "h", "i", "j", "k", "l", "m", "n",
                "o", "p", "q", "r", "s", "t", "u", "v", "w", "x", "y", "z" };
        for (String str : letterArray) {
            if ((c + "").trim().equalsIgnoreCase(str)) {
                return true;
            }
        }
        return false;
    }

}
