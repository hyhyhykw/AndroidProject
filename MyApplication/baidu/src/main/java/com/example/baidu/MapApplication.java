package com.example.baidu;

import android.app.Application;

import com.baidu.mapapi.SDKInitializer;

/**
 * Created time : 2017/1/12 13:51.
 *
 * @author HY
 */

public class MapApplication extends Application {
    @Override
    public void onCreate() {
        super.onCreate();
        SDKInitializer.initialize(getApplicationContext());
    }
}
