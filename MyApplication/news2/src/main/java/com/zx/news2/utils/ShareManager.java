package com.zx.news2.utils;

import cn.sharesdk.framework.Platform;
import cn.sharesdk.framework.ShareSDK;

/**
 * Created by HY on 2017/1/5.
 * this class help share to other platform
 *
 * @author HY
 */
public class ShareManager {
    public static void share(String name, String text, Platform.ShareParams params) {
        params.setText(text);
        params.setImageUrl("http://118.244.212.82:9092/Images/20170104034425.jpg");
        Platform platform = ShareSDK.getPlatform(name);
        platform.share(params);
    }
}
