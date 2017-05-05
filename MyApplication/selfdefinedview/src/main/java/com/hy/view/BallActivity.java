package com.hy.view;


import android.app.Activity;
import android.os.Bundle;

import com.hy.view.view.BallView;

public class BallActivity extends Activity {
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		BallView view = new BallView(this);
		setContentView(view);
	}
}
