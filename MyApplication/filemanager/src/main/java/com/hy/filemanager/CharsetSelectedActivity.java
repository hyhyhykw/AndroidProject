package com.hy.filemanager;

import java.util.ArrayList;
import java.util.List;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;
import android.os.Bundle;
import android.view.KeyEvent;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ListView;

import com.hy.filemanager.adapter.CodeSelectAdapter;
import com.hy.filemanager.constant.Constant;
import com.hy.filemanager.entity.CodeItemInfo;

public class CharsetSelectedActivity extends BaseActivity {

	protected ListView mLstCodeList;
	protected List<CodeItemInfo> itemInfos = new ArrayList<CodeItemInfo>();
	/** share file data */
	protected SharedPreferences mSharedPre;
	/** editor object */
	protected Editor mEditor;

	protected CodeSelectAdapter adapter;

	protected int resultCode;

	protected boolean isReader;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_charset_selected);

		Intent intent = this.getIntent();
		Bundle bundle = intent.getBundleExtra("bundle");
		if (null!=bundle) {
			isReader = bundle.getBoolean("isReader");
		}

		initView();
		initData();
		adapter = new CodeSelectAdapter(this, itemInfos);
		mLstCodeList.setAdapter(adapter);

		initevent();
	}

	/**
	 * initialize view
	 */
	private void initView() {
		mLstCodeList = (ListView) this.findViewById(R.id.lst_charset_list);
	}

	/**
	 * initialize click event
	 */
	private void initevent() {
		mLstCodeList.setOnItemClickListener(new OnItemClickListener() {
			@Override
			public void onItemClick(AdapterView<?> parent, View view,
					int position, long id) {
				for (int i = 0; i < itemInfos.size(); i++) {
					itemInfos.get(i).setChecked(i == position);
				}
				resultCode = position;
				initData();
				adapter.update(itemInfos);
			}
		});

	}

	/**
	 * initialize data
	 */
	private void initData() {
		// itemInfos.clear();
		mSharedPre = this.getSharedPreferences(Constant.CODE_IS_CHECKED,
				Context.MODE_PRIVATE);
		mEditor = mSharedPre.edit();
		String[] items = this.getResources().getStringArray(
				R.array.text_of_charset);
		boolean firstDefVal = mSharedPre.getBoolean("default", true);
		if (itemInfos.isEmpty()) {
			for (int i = 0; i < items.length; i++) {
				CodeItemInfo itemInfo = new CodeItemInfo();
				boolean isChecked;
				isChecked = mSharedPre.getBoolean("item" + i, false);
				itemInfo.setCharsetCode(items[i]);
				itemInfo.setChecked(isChecked);
				mEditor.putBoolean("item" + i, itemInfo.isChecked());
				if (firstDefVal && i == 0) {
					itemInfo.setChecked(true);
					mEditor.putBoolean("default", false);
					mEditor.commit();
				}
				itemInfos.add(itemInfo);
			}
		} else {
			for (int i = 0; i < itemInfos.size(); i++) {
				CodeItemInfo itemInfo = itemInfos.get(i);
				itemInfo.setChecked(resultCode == i);
				if (!isReader) {
					mEditor.putBoolean("item" + i, itemInfo.isChecked());
					mEditor.commit();
				}
			}
		}

	}

	@Override
	public boolean onKeyDown(int keyCode, KeyEvent event) {
		if (keyCode == KeyEvent.KEYCODE_BACK) {
			resultCode = -1;
			setResult(resultCode);
			finish();
		}
		return super.onKeyDown(keyCode, event);
	}

	public void selectClick(View view) {
		switch (view.getId()) {
		case R.id.txt_set_charset_cancel:
			resultCode = -1;
			break;
		}
		setResult(resultCode);
		finish();
	}
}
