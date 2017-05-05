package com.hy.view;


import android.app.Activity;
import android.os.Bundle;

import com.hy.view.view.RingBallView;

public class RingBallActivity extends Activity {
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		RingBallView rbv = new RingBallView(this);
		setContentView(rbv);
	}
}
