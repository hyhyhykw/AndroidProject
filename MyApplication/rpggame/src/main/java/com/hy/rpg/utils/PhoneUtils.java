package com.hy.rpg.utils;

import android.content.res.Resources;
import android.util.DisplayMetrics;

import java.util.Random;

/**
 * Created by HY on 2016/12/27.
 * get phone info
 */

public class PhoneUtils {
    /**
     * get window width
     *
     * @param res resource
     * @return window width
     */
    public static int getWidth(Resources res) {
        DisplayMetrics outMetrics = res.getDisplayMetrics();
        return outMetrics.widthPixels;
    }

    /**
     * get window height
     *
     * @param res resource
     * @return window height
     */
    public static int getHeight(Resources res) {
        DisplayMetrics outMetrics = res.getDisplayMetrics();
        return outMetrics.heightPixels;
    }

    /**
     * get window dpi
     *
     * @param res resource
     * @return window dpi
     */
    private static int getDensity(Resources res) {
        DisplayMetrics outMetrics = res.getDisplayMetrics();
        return outMetrics.densityDpi;
    }

    /**
     * get dp size
     *
     * @param pixelSize pixel size
     * @param res       resource
     * @return dp size
     */
    public static int getDpSize(int pixelSize, Resources res) {
        return pixelSize * getDensity(res) / 160;
    }

}
