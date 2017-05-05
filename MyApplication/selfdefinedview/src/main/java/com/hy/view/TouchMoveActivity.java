package com.hy.view;

import android.app.Activity;
import android.os.Bundle;

import com.hy.view.view.TouchMoveView;

public class TouchMoveActivity extends Activity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        TouchMoveView view = new TouchMoveView(this);
        setContentView(view);
    }
}
