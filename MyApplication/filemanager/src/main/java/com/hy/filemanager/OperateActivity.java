package com.hy.filemanager;

import java.io.File;

import com.hy.filemanager.adapter.OperateAdapter;
import com.hy.filemanager.constant.Constant;
import com.hy.filemanager.utils.FileUitls;

import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ListView;
import android.widget.Toast;

public class OperateActivity extends BaseActivity {

	protected ListView mLstOperate;

	protected String[] operates;

	protected File file;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_operate);
		initView();
		initData();
		initevent();
	}

	private void initevent() {
		mLstOperate.setOnItemClickListener(new OnItemClickListener() {
			@Override
			public void onItemClick(AdapterView<?> parent, View view,
					int position, long id) {
				switch (position) {
				case Constant.OPERATE_MOVE_FILE:
					finish();
					break;
				case Constant.OPERATE_COPY_FILE:
					finish();
					break;
				case Constant.OPERATE_DELETE_FILE:
					boolean isSuccess = FileUitls.delFile(file);
					if (isSuccess) {
						Toast.makeText(OperateActivity.this,
								R.string.delete_success, Toast.LENGTH_SHORT)
								.show();
					} else {
						Toast.makeText(OperateActivity.this,
								R.string.delete_failed, Toast.LENGTH_SHORT)
								.show();
					}
					finish();
					break;
				case Constant.OPERATE_RENAME_FILE:
					Bundle bundle = new Bundle();
					bundle.putSerializable("file", file);
					bundle.putInt("state", 1);
					toActivity(InputNameActivity.class, bundle);
					finish();
					break;
				case Constant.OPERATE_FILE_DETAIL:
					finish();
					break;
				}
			}
		});
	}

	private void initView() {
		mLstOperate = (ListView) this.findViewById(R.id.lst_operate);
	}

	private void initData() {
		Bundle bundle = this.getIntent().getBundleExtra("bundle");
		file = (File) bundle.getSerializable("file");
		operates = this.getResources().getStringArray(R.array.texts_of_operate);
		OperateAdapter adapter = new OperateAdapter(this, operates);
		mLstOperate.setAdapter(adapter);
	}

}
