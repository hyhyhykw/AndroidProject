package com.hy.view;


import android.app.Activity;
import android.os.Bundle;

import com.hy.view.view.CacheView;

public class CacheActivity extends Activity {
    CacheView view;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        view = new CacheView(this);
        setContentView(view);
    }

    @Override
    protected void onPause() {
        super.onPause();
        view.stop(true);
    }
}
