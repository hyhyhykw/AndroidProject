package com.example.mvp.utils;

import java.util.Map;

/**
 * Created by HY on 2016/12/21.
 */

public class DataUtils {
    /**
     * map transform to string
     *
     * @param data
     * @return
     */
    public static String map2String(Map<String, String> data) {
        StringBuffer sbf = new StringBuffer();
        for (Map.Entry<String, String> entry : data.entrySet()) {
            sbf.append(entry.getKey()).append("=").append(entry.getValue()).append("&");
        }
        sbf.deleteCharAt(sbf.length() - 1);
        return sbf.toString();
    }
}
