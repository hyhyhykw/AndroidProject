package com.hy.filemanager;

import java.io.File;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.text.method.ScrollingMovementMethod;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.ImageView;
import android.widget.TextView;

import com.hy.filemanager.constant.Constant;
import com.hy.filemanager.utils.FileUitls;

public class TextReadActivity extends BaseActivity {

	protected TextView mTxtEditArea;

	protected File file;

	protected TextView mTxtFileName;

	protected ImageView mImgChangeCode;

	protected StringBuffer mSbfFileContent;

	protected String mCharset;

	protected SharedPreferences mShared;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_text_read);
		initView();

		mShared = this.getSharedPreferences(Constant.CODE_IS_CHANGED,
				Context.MODE_PRIVATE);
		mCharset = mShared
				.getString("defaultCode", Constant.CHARSET_CODE_UTF_8);

		Intent intent = this.getIntent();
		Bundle bundle = intent.getBundleExtra("bundle");
		file = (File) bundle.getSerializable("file");

		initEvent();
	}

	@Override
	protected void onResume() {
		super.onResume();
		initData();
	}

	private void initData() {
		mTxtFileName.setText(file.getName());
		mTxtEditArea.setText(FileUitls.readText(file, mCharset));

	}

	private void initView() {
		mImgChangeCode = (ImageView) this.findViewById(R.id.img_change_code);
		mTxtFileName = (TextView) this.findViewById(R.id.txt_current_file_name);
		mTxtEditArea = (TextView) this.findViewById(R.id.txt_read_area);
		mTxtEditArea.setMovementMethod(ScrollingMovementMethod.getInstance());

	}

	private void initEvent() {
		mImgChangeCode.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				Bundle bundle = new Bundle();
				bundle.putBoolean("isReader", true);
				toActivity(CharsetSelectedActivity.class,
						Constant.REQUEST_CODE_CHARSET_SELECT, bundle);
			}
		});
	}

	@Override
	protected void onActivityResult(int requestCode, int resultCode, Intent data) {
		super.onActivityResult(requestCode, resultCode, data);
		if (requestCode == Constant.REQUEST_CODE_CHARSET_SELECT) {
			switch (resultCode) {
			case Constant.RESULT_CODE_UTF_8:// utf-8
				mCharset = Constant.CHARSET_CODE_UTF_8;
				break;
			case Constant.RESULT_CODE_GBK:// gbk
				mCharset = Constant.CHARSET_CODE_GBK;
				break;
			case Constant.RESULT_CODE_GB2312:// gb2312
				mCharset = Constant.CHARSET_CODE_GB2312;
				break;
			case Constant.RESULT_CODE_UTF_16:// utf-16
				mCharset = Constant.CHARSET_CODE_UTF_16;
				break;
			case Constant.RESULT_CODE_US_ASCII:// us-ascii
				mCharset = Constant.CHARSET_CODE_US_ASCII;
				break;
			case Constant.RESULT_CODE_ISO_8859:// iso-8859-1
				mCharset = Constant.CHARSET_CODE_ISO_8859;
				break;
			}
			initData();
		}

	}

}
