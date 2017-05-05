package com.hy.weather;

import android.app.ProgressDialog;
import android.os.Handler;
import android.os.Message;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;

import com.hy.weather.biz.FileUtils;
import com.hy.weather.biz.PoiParser;
import com.hy.weather.biz.dao.DBWrapper;
import com.hy.weather.entity.CityInfo;

import java.util.List;

public class MainActivity extends AppCompatActivity {

    private ProgressDialog mPgdLoad;

    private PoiParser mPoiParser;
    private Handler mHandler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            Log.e("TAG", "msg=" + msg.what);
        }
    };
    private List<CityInfo> mCityInfos;
    private DBWrapper mDBWrapper;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        mPgdLoad = new ProgressDialog(this);
        mPgdLoad.show();
        mPoiParser = new PoiParser(mHandler);
        mDBWrapper = DBWrapper.newInstance(this);
        new Thread() {
            @Override
            public void run() {
                FileUtils.unZip(MainActivity.this);
                mCityInfos = mPoiParser.readExcel(MainActivity.this);
                mDBWrapper.insertCity(mCityInfos);
                mPgdLoad.dismiss();
            }
        }.start();
    }
}
