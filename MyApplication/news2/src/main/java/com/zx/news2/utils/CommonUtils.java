package com.zx.news2.utils;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.telephony.TelephonyManager;

import com.zx.news2.utils.constant.Constant;

import java.text.DecimalFormat;
import java.text.SimpleDateFormat;
import java.util.Map;
import java.util.regex.Pattern;

/**
 * Created by HY on 2016/12/28.
 * map to string
 */

public class CommonUtils {
    private static final String TAG = CommonUtils.class.getSimpleName();

    /**
     * map to string
     *
     * @param data data
     * @return url suffix
     */
    public static String map2String(Map<String, String> data) {
        StringBuilder sbl = new StringBuilder();
        if (null != data && !data.isEmpty()) {
            for (Map.Entry<String, String> entry : data.entrySet()) {
                sbl.append(entry.getKey()).append("=").append(entry.getValue()).append("&");
            }
            sbl.deleteCharAt(sbl.length() - 1);
        }
        return sbl.toString();
    }


    /**
     * @param context context object
     * @return phone imei
     */
    @SuppressLint("HardwareIds")
    public static String getImei(Context context) {
        try {
            TelephonyManager tm = (TelephonyManager) context.getSystemService(Context.TELEPHONY_SERVICE);
            StringBuilder sbl = new StringBuilder("000000000000000");
            return sbl.append(tm.getDeviceId()).substring(sbl.length() - 15, sbl.length());
        } catch (Exception e) {
            return "0";
        }

    }

    /**
     * get login type
     *
     * @param device device flag
     * @return login type
     */
    public static String getLoginType(int device) {
        return device == 0 ? "手机登陆" : "网页登陆";
    }

    /**
     * get current datatime
     *
     * @return current datatime
     */
    @SuppressLint("SimpleDateFormat")
    public static String getDateTime() {
        SimpleDateFormat sdf = new SimpleDateFormat(Constant.FORMAT);
        return sdf.format(getMills());
    }

    /**
     * get login time
     *
     * @param time detail time
     * @return login time
     */
    public static String getLoginDate(String time) {
        return time.substring(0, 11);
    }


    /**
     * get current mills
     *
     * @return current mills
     */
    private static long getMills() {
        return System.currentTimeMillis();
    }


    private static final String PATTERN = "\\w+(@)\\w+(\\.com)";

    /**
     * judge such string is or not email
     *
     * @param email email text
     * @return such string is or not email
     */
    public static boolean isEmail(String email) {
        return Pattern.matches(PATTERN, email);
    }

    /**
     * judge password length is or not match
     *
     * @param length password length
     * @return password length is or not match
     */
    public static boolean isLength(int length) {
        return length >= 6;
    }

    /**
     * format file length
     *
     * @param length 一个long类型的数，表示文件的长度，单位是：B
     * @return 返回一个带有合适单位的，表示文件长度的小于1024的字符串类型的数
     */
    public static String formatLength(long length) {
        DecimalFormat format = new DecimalFormat(".0");

        if (length >= 1024 && length < 1024 * 1024) {
            double len = length * 1.0 / 1024;
            String string = format.format(len);
            return string + "K";
        } else if (length >= 1024 * 1024 && length < 1024 * 1024 * 1024) {
            double len = length * 1.0 / (1024 * 1024);
            String string = format.format(len);
            return string + "M";
        } else if (length >= 1024 * 1024 * 1024) {
            double len = length * 1.0 / (1024 * 1024 * 1024);
            String string = format.format(len);
            return string + "G";
        }
        return length + "B";
    }

    /**
     * get current version code
     *
     * @param context used to get package manager
     * @return current version code
     */
    static int getCurrVer(Context context) {
        try {
            PackageInfo pkgInfo = context.getPackageManager().getPackageInfo(context.getPackageName(), 0);
            return pkgInfo.versionCode;
        } catch (PackageManager.NameNotFoundException e) {
            LogWrapper.e(TAG, "Package Name Not Found");
            return -1;
        }
    }

    /**
     * get current package name
     *
     * @param context used to get current package name
     * @return current package name
     */
    static String getPkgName(Context context) {
        return context.getPackageName();
    }
}
