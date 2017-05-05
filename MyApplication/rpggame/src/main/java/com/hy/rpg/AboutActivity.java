package com.hy.rpg;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.RelativeLayout;

import com.hy.rpg.view.MeteorView;
import com.hy.rpg.view.MeteorView.OnViewStopListener;


public class AboutActivity extends BaseActivity implements OnViewStopListener {

    private static final String TAG = AboutActivity.class.getSimpleName();
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
