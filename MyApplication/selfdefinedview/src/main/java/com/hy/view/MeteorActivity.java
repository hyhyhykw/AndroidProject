package com.hy.view;

import android.app.Activity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.RelativeLayout;

import com.hy.view.view.MeteorView;

public class MeteorActivity extends Activity implements MeteorView.OnViewStopListener {

    private static final String TAG = MeteorActivity.class.getSimpleName();
    protected RelativeLayout layout;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        layout = new RelativeLayout(this);
        layout.setBackgroundResource(R.mipmap.about_bg);
        initLayout();
        setContentView(layout);
    }

    private void initLayout() {
        for (int i = 0; i < 10; i++) {
            MeteorView view = new MeteorView(this);
            view.setOnViewStopListener(this);
            layout.addView(view);
        }
    }

    @Override
    protected void onPause() {
        super.onPause();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        layout.removeAllViews();
    }

    @Override
    public void viewStop(View view) {
        layout.removeView(view);
        Log.e(TAG, "" + layout.getChildCount());
        if (layout.getChildCount() != 0) {
            MeteorView meteorView = new MeteorView(this);
            meteorView.setOnViewStopListener(this);
            layout.addView(meteorView);
        }
    }
}
