package com.hy.filemanager;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;
import android.os.Bundle;
import android.view.View;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.CompoundButton.OnCheckedChangeListener;
import android.widget.TextView;

import com.hy.filemanager.constant.Constant;

public class SettingActivity extends BaseActivity implements
		OnCheckedChangeListener {

	protected SharedPreferences mShared;

	protected Editor mEditor;

	protected TextView mTxtDefCode;

	protected CheckBox mChbFolderUp;
	protected CheckBox mChbShowHidden;

	protected TextView mTxtFolderUpDetail;
	protected TextView mTxtShowHidDetail;

	protected boolean isFolderUp;
	protected boolean isShowHid;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_setting);
		initView();
		initEvent();
	}

	@Override
	protected void onResume() {
		super.onResume();
		initData();
		String text = mShared.getString("defaultCode",
				Constant.CHARSET_CODE_UTF_8);
		mTxtDefCode.setText("(当前默认:" + text + ")");
	}

	private void initView() {
		mTxtFolderUpDetail = (TextView) this
				.findViewById(R.id.txt_folder_up_detail);
		mTxtShowHidDetail = (TextView) this
				.findViewById(R.id.txt_show_hidden_detail);
		mTxtDefCode = (TextView) this.findViewById(R.id.txt_code_def);
		mChbFolderUp = (CheckBox) this.findViewById(R.id.chb_folder_up);
		mChbShowHidden = (CheckBox) this.findViewById(R.id.chb_show_hidden);
	}

	private void initData() {
		mShared = this.getSharedPreferences(Constant.CODE_IS_CHANGED,
				Context.MODE_PRIVATE);
		mEditor = mShared.edit();
		isFolderUp = mShared.getBoolean("isFolderUp", true);
		isShowHid = mShared.getBoolean("isShowHid", true);
		mChbFolderUp.setChecked(isFolderUp);
		mChbShowHidden.setChecked(isShowHid);
	}

	public void selectCode(View view) {
		toActivity(CharsetSelectedActivity.class,
				Constant.REQUEST_CODE_CHARSET_SET);
	}

	private void initEvent() {
		mChbFolderUp.setOnCheckedChangeListener(this);
		mChbShowHidden.setOnCheckedChangeListener(this);
	}

	public void folderUp(View view) {
		if (mChbFolderUp.isChecked()) {
			mChbFolderUp.setChecked(false);
		} else {
			mChbFolderUp.setChecked(true);
		}
	}

	public void showHidden(View view) {
		if (mChbShowHidden.isChecked()) {
			mChbShowHidden.setChecked(false);
		} else {
			mChbShowHidden.setChecked(true);
		}
	}

	@Override
	protected void onActivityResult(int requestCode, int resultCode, Intent data) {
		super.onActivityResult(requestCode, resultCode, data);
		if (requestCode == Constant.REQUEST_CODE_CHARSET_SET) {
			switch (resultCode) {
			case Constant.RESULT_CODE_UTF_8:// utf-8
				mEditor.putString("defaultCode", Constant.CHARSET_CODE_UTF_8);
				break;
			case Constant.RESULT_CODE_GBK:// gbk
				mEditor.putString("defaultCode", Constant.CHARSET_CODE_GBK);
				break;
			case Constant.RESULT_CODE_GB2312:// gb2312
				mEditor.putString("defaultCode", Constant.CHARSET_CODE_GB2312);
				break;
			case Constant.RESULT_CODE_UTF_16:// utf-16
				mEditor.putString("defaultCode", Constant.CHARSET_CODE_UTF_16);
				break;
			case Constant.RESULT_CODE_US_ASCII:// us-ascii
				mEditor.putString("defaultCode", Constant.CHARSET_CODE_US_ASCII);
				break;
			case Constant.RESULT_CODE_ISO_8859:// iso-8859-1
				mEditor.putString("defaultCode", Constant.CHARSET_CODE_ISO_8859);
				break;
			}
			mEditor.commit();
		}
	}

	@Override
	public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
		switch (buttonView.getId()) {
		case R.id.chb_folder_up:
			if (isChecked) {
				mEditor.putBoolean("isFolderUp", true);
				mTxtFolderUpDetail.setText(R.string.txt_folder_up_detail_1);
			} else {
				mEditor.putBoolean("isFolderUp", false);
				mTxtFolderUpDetail.setText(R.string.txt_folder_up_detail_2);
			}
			mEditor.commit();
			break;
		case R.id.chb_show_hidden:
			if (isChecked) {
				mTxtShowHidDetail.setText(R.string.txt_folder_show_hidden_1);
				mEditor.putBoolean("isShowHid", true);
			} else {
				mTxtShowHidDetail.setText(R.string.txt_folder_show_hidden_2);
				mEditor.putBoolean("isShowHid", false);
			}
			mEditor.commit();
			break;
		}

	}
}
