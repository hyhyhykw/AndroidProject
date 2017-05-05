package com.zx.news2.utils;

import android.util.Log;

import com.zx.news2.BuildConfig;

/**
 * Created by HY on 2016/12/23.
 * this class wrap the Log
 *
 * @author HY
 */
public class LogWrapper {
    /**
     * is or not debug
     */
    private static final boolean isDeBug = true;

    /**
     * print verbose message
     *
     * @param tag tag
     * @param msg message
     */
    public static void v(String tag, String msg) {
        if (BuildConfig.DEBUG && isDeBug)
            Log.v(tag, msg);
    }

    /**
     * print debug message
     *
     * @param tag tag
     * @param msg message
     */
    public static void d(String tag, String msg) {
        if (BuildConfig.DEBUG && isDeBug)
            Log.d(tag, msg);
    }

    /**
     * print info message
     *
     * @param tag tag
     * @param msg message
     */
    public static void i(String tag, String msg) {
        if (BuildConfig.DEBUG && isDeBug)
            Log.i(tag, msg);
    }

    /**
     * print warn message
     *
     * @param tag tag
     * @param msg message
     */
    public static void w(String tag, String msg) {
        if (BuildConfig.DEBUG && isDeBug)
            Log.w(tag, msg);
    }

    /**
     * print error message
     *
     * @param tag tag
     * @param msg message
     */
    public static void e(String tag, String msg) {
        if (BuildConfig.DEBUG && isDeBug)
            Log.e(tag, msg);
    }
}
