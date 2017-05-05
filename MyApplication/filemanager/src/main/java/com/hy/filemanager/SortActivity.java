package com.hy.filemanager;

import java.util.ArrayList;
import java.util.List;

import com.hy.filemanager.adapter.SortAdapter;
import com.hy.filemanager.constant.Constant;
import com.hy.filemanager.entity.SortInfo;

import android.content.Context;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ListView;

public class SortActivity extends BaseActivity {

	protected ListView mLstSortMode;
	protected String[] sortModes;
	protected SortAdapter adapter;

	/** share file data */
	protected SharedPreferences mSharedPre;
	/** editor object */
	protected Editor mEditor;

	protected List<SortInfo> sortInfos = new ArrayList<>();

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_sort);
		setFinishOnTouchOutside(false);
		initData();
		initView();
		initEvent();
	}

	private void initView() {
		mLstSortMode = (ListView) this.findViewById(R.id.lst_sort_mode);
		mLstSortMode.setAdapter(adapter);
	}

	private void initData() {
		mSharedPre = this.getSharedPreferences(Constant.CODE_IS_CHANGED,
				Context.MODE_PRIVATE);
		mEditor = mSharedPre.edit();

		sortModes = this.getResources().getStringArray(R.array.text_sort_mode);

		for (int i = 0; i < sortModes.length; i++) {
			boolean isChecked = mSharedPre.getBoolean("sortMode" + i, false);
			SortInfo sortInfo = new SortInfo(sortModes[i]);
			sortInfo.setChecked(isChecked);
			sortInfos.add(sortInfo);
		}
		adapter = new SortAdapter(this, sortInfos);
	}

	private void initEvent() {
		mLstSortMode.setOnItemClickListener(new OnItemClickListener() {
			@Override
			public void onItemClick(AdapterView<?> parent, View view,
					int position, long id) {
				for (int i = 0; i < sortInfos.size(); i++) {
					SortInfo sInfo = sortInfos.get(i);
					sInfo.setChecked(i == position);
					mEditor.putBoolean("sortMode" + i, sInfo.isChecked());
					mEditor.commit();
				}
				adapter.update(sortInfos);
				setResult(position);
				finish();
			}
		});
	}
}
