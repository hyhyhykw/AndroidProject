package com.zx.adaptation;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.DisplayMetrics;
import android.util.Log;
import android.widget.RelativeLayout.LayoutParams;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity {

    private TextView mTxt;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        mTxt = (TextView) findViewById(R.id.txt);
        //找到当前的父控件
        DisplayMetrics metrics = getResources().getDisplayMetrics();
        Log.e("TAG", "宽 = " + metrics.widthPixels);
        Log.e("TAG", "高 = " + metrics.heightPixels);

        LayoutParams params = new LayoutParams(metrics.widthPixels/2, metrics.heightPixels/2);

        mTxt.setLayoutParams(params);
    }
}
