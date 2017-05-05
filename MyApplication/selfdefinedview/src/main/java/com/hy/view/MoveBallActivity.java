package com.hy.view;


import android.app.Activity;
import android.os.Bundle;

import com.hy.view.view.MoveBallView;

public class MoveBallActivity extends Activity {
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		MoveBallView mbv = new MoveBallView(this);
		setContentView(mbv);
	}
}
