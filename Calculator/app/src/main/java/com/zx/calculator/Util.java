package com.zx.calculator;

/**
 * Created time : 2017/3/15 20:02.
 *
 * @author HY
 */

public class Util {

    private Util() {
        throw new UnsupportedOperationException("This class can't be instantiated!");
    }

    /**
     * 在判断之前先确定最后一位是不是为小数点，如果是，就去掉
     * 判断字符串是否是整数
     *
     * @param numStr 数字字符串
     * @return 结果
     */
    static boolean isInt(String numStr) {
        String temp = numStr;
        //先判断正负
        String firstChar = String.valueOf(temp.charAt(0));
        //第一位是-号的时候去掉判断
        if (firstChar.equals("-")) {
            temp = temp.substring(1);
        }
        //最后一位不是小数点的情况下，正数字符串长度不大于2的都是整数
        if (temp.length() <= 2) {
            return true;
        }

        if (!numStr.contains(".")) {
            return true;
        } else {
            //倒数第二位是小数点，而且倒数第一位是0的情况下，是正数，否则不是
            String descSecChar = String.valueOf(temp.charAt(temp.length() - 2));
            String descFirstChar = String.valueOf(temp.charAt(temp.length() - 1));

            if (descSecChar.equals(".") && descFirstChar.equals("0"))
                return true;
        }
        return false;
    }

    /**
     * 在计算前需要进行的判断，防止出现 NumberFormatException
     * 如果字符串结尾是一个小数点，去掉
     *
     * @param text 数字字符串
     * @return 格式化后的字符串
     * @see java.lang.NumberFormatException
     */
    static String format(String text) {
        if (null == text || text.length() == 0) {
            return "";
        }

        String temp = String.valueOf(text.charAt(text.length() - 1));
        if (temp.equals(".")) {
            text = text.replace(".", "");
        }
        return text.trim();
    }


}
