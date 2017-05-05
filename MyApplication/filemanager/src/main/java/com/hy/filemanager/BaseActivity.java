package com.hy.filemanager;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;

public class BaseActivity extends Activity {

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
	}

	/**
	 * skip to other activity
	 * 
	 * @param cla
	 *            activity object of where you will skip
	 */
	public void toActivity(Class<?> cla) {
		toActivity(cla, null);
	}

	/**
	 * 
	 * @param cla
	 *            activity object of where you will skip
	 * @param bundle
	 *            extra data
	 */
	public void toActivity(Class<?> cla, Bundle bundle) {
		Intent intent = new Intent(this, cla);
		if (null != bundle)
			intent.putExtra("bundle", bundle);
		startActivity(intent);
	}

	/**
	 * skip to other activity for result
	 * 
	 * @param cla
	 * @param requestCode
	 */
	public void toActivity(Class<?> cla, int requestCode) {
		this.toActivity(cla, requestCode, null);
	}

	/**
	 * 
	 * @param cla
	 * @param requestCode
	 * @param bundle
	 */
	public void toActivity(Class<?> cla, int requestCode, Bundle bundle) {
		Intent intent = new Intent(this, cla);
		if (null != bundle)
			intent.putExtra("bundle", bundle);
		startActivityForResult(intent, requestCode);
	}

}
