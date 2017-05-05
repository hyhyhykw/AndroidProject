package com.zx.myjpush;

import android.app.Application;

import cn.jpush.android.api.JPushInterface;

/**
 * Created Time: 2017/3/3 19:14.
 *
 * @author HY
 */

public class MyApplication extends Application {
    @Override
    public void onCreate() {
        super.onCreate();
        JPushInterface.setDebugMode(true);
        JPushInterface.init(this);
    }
}
