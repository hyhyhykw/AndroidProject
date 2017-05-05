package com.example.androidmap;

import android.app.Application;

import com.baidu.mapapi.SDKInitializer;

/**
 * Created time : 2017/1/11 11:40.
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
