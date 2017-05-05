package com.hy.rpg.utils;

import java.util.Random;

/**
 * Created by HY on 2017/1/5.
 * provide common utils
 *
 * @author HY
 */
public class CommonUtils {
    /**
     * get random
     *
     * @param max random number max value
     * @return random number
     */
    public static int getRanNum(int max) {
        Random random = new Random();
        return max > 0 ? random.nextInt(max) : random.nextInt();
    }
}
