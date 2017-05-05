package com.hy.view;


import android.app.Activity;
import android.os.Bundle;

import com.hy.view.view.CircleRingView;

public class CircleRingActivity extends Activity {
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		CircleRingView crv = new CircleRingView(this);
		setContentView(crv);
	}
}
