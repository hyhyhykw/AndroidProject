package com.zx.news2;

import android.app.Application;

import cn.sharesdk.framework.ShareSDK;

/**
 * Created by HY on 2017/1/4.
 * luanch share sdk
 *
 * @author HY
 */
public class MyApplication extends Application {
    @Override
    public void onCreate() {
        super.onCreate();
        ShareSDK.initSDK(getApplicationContext());
    }
}
