package com.hy.filemanager;

import android.os.Bundle;
import android.view.View;

public class CreateActivity extends BaseActivity {

	protected String parentPath;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_create);
		setFinishOnTouchOutside(false);// 在dialog 外不可点击
		parentPath = this.getIntent().getBundleExtra("bundle")
				.getString("parentPath");

	}

	public void createClick(View view) {
		Bundle bundle = new Bundle();
		bundle.putString("parentPath", parentPath);
		// Log.e("Tag", ""+parentPath);
		switch (view.getId()) {
		case R.id.layout_create_file:
			bundle.putString("title", "文件");
			bundle.putBoolean("isFile", true);
			toActivity(InputNameActivity.class, bundle);
			finish();
			break;
		case R.id.layout_create_folder:
			bundle.putString("title", "文件夹");
			bundle.putBoolean("isFile", false);
			toActivity(InputNameActivity.class, bundle);
			finish();
			break;
		}
	}
}
