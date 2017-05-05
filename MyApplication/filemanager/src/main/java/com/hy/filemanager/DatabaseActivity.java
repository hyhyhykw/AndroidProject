package com.hy.filemanager;

import java.io.File;
import java.util.List;

import com.hy.filemanager.adapter.TableListAdapter;
import com.hy.filemanager.db.DBHelper;

import android.os.Bundle;
import android.util.Log;
import android.widget.ListView;

public class DatabaseActivity extends BaseActivity {
	private static final String TAG = DatabaseActivity.class.getSimpleName();

	protected ListView mLstTableList;

	protected List<String> tableList;

	protected File dbFile;
	TableListAdapter adapter;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_database);
		mLstTableList = (ListView) this.findViewById(R.id.lst_table_list);
		dbFile = (File) getIntent().getBundleExtra("bundle").getSerializable(
				"dbFile");

		Log.e(TAG, dbFile.getName());

		DBHelper helper = new DBHelper(dbFile);
		tableList = helper.readTableList();
		adapter = new TableListAdapter(tableList, DatabaseActivity.this);
		mLstTableList.setAdapter(adapter);
	}
}
