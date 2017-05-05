package com.hy.liferpg.utils;

import android.util.Log;

import com.hy.liferpg.BuildConfig;

/**
 * Created by HY on 2017/1/4.
 * this class wrap log
 *
 * @author HY
 */
public class LogWrapper {
    private static final boolean isVerbose = true;
    private static final boolean isDebug = true;
    private static final boolean isInfo = true;
    private static final boolean isWarn = true;
    private static final boolean isError = true;

    /**
     * print verbose message
     *
     * @param tag tag
     * @param msg verbose message
     */
    public static void v(String tag, String msg) {
        if (BuildConfig.DEBUG && isVerbose)
            Log.v(tag, msg);
    }

    /**
     * print debug message
     *
     * @param tag tag
     * @param msg debug message
     */
    public static void d(String tag, String msg) {
        if (BuildConfig.DEBUG && isDebug)
            Log.d(tag, msg);
    }

    /**
     * print info message
     *
     * @param tag tag
     * @param msg info message
     */
    public static void i(String tag, String msg) {
        if (BuildConfig.DEBUG && isInfo)
            Log.i(tag, msg);
    }

    /**
     * print wran message
     *
     * @param tag tag
     * @param msg wran message
     */
    public static void w(String tag, String msg) {
        if (BuildConfig.DEBUG && isWarn)
            Log.w(tag, msg);
    }

    /**
     * print error message
     *
     * @param tag tag
     * @param msg error message
     */
    public static void e(String tag, String msg) {
        if (BuildConfig.DEBUG && isError)
            Log.e(tag, msg);
    }

}
