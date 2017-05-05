package com.hy.filemanager;

import java.io.File;

import com.hy.filemanager.utils.FileUitls;

import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

public class InputNameActivity extends BaseActivity {

	protected TextView mTxtTitle;
	protected EditText mEdtInputName;

	protected boolean isFile;

	protected String parentPath;

	protected int state;

	protected File file;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_input_name);
		setFinishOnTouchOutside(false);
		initView();
		initData();
	}

	private void initData() {
		Bundle bundle = this.getIntent().getBundleExtra("bundle");
		state = bundle.getInt("state");
		switch (state) {
		case 0:
			String title = bundle.getString("title");
			isFile = bundle.getBoolean("isFile", false);
			// Log.e("isFile", "" + isFile);
			mTxtTitle.setText(title);
			parentPath = bundle.getString("parentPath");
			break;
		case 1:
			file = (File) bundle.getSerializable("file");
			break;
		}
	}

	private void initView() {
		mEdtInputName = (EditText) this.findViewById(R.id.edt_input_name);
		mTxtTitle = (TextView) this.findViewById(R.id.txt_input_title);
	}

	public void selectClick(View view) {
		switch (view.getId()) {
		case R.id.txt_input_cancel:
			finish();
			break;
		case R.id.txt_input_ok:
			String fileName = mEdtInputName.getText().toString();
			boolean isSuccess = false;
			switch (state) {
			case 0:
				File file = new File(parentPath + fileName);
				if (fileName.equals("")) {
					Toast.makeText(this, R.string.empty_file_name,
							Toast.LENGTH_LONG).show();
				} else {
					isSuccess = FileUitls.createFile(file, isFile);
				}

				break;
			case 1:
				if (fileName.equals("")) {
					Toast.makeText(this, R.string.empty_file_name,
							Toast.LENGTH_LONG).show();
				} else {
					isSuccess = FileUitls.rename(this.file, fileName);
				}
				break;
			}
			if (isSuccess) {
				Toast.makeText(this, R.string.operate_success,
						Toast.LENGTH_SHORT).show();
			} else {
				Toast.makeText(this, R.string.operate_failed,
						Toast.LENGTH_SHORT).show();
			}
			finish();
			break;

		}
	}
}
