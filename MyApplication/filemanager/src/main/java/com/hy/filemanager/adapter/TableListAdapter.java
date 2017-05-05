package com.hy.filemanager.adapter;

import java.util.List;

import com.hy.filemanager.R;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

public class TableListAdapter extends BaseAdapter {

	private List<String> tableList;
	private Context mContext;

	public TableListAdapter(List<String> tableList, Context mContext) {
		this.tableList = tableList;
		this.mContext = mContext;
	}

	@Override
	public int getCount() {
		return null != tableList ? tableList.size() : 0;
	}

	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		ViewHolder holder;
		if (null == convertView) {
			holder = new ViewHolder();
			convertView = LayoutInflater.from(mContext).inflate(
					R.layout.item_lst_table_list, null);
			holder.mTxtTableName = (TextView) convertView
					.findViewById(R.id.txt_table_name);
			convertView.setTag(holder);
		} else {
			holder = (ViewHolder) convertView.getTag();
		}
		holder.mTxtTableName.setText(getItem(position));
		return convertView;
	}

	@Override
	public String getItem(int position) {
		return tableList.get(position);
	}

	@Override
	public long getItemId(int position) {
		return 0;
	}

	public void update(List<String> tableList) {
		this.tableList = tableList;
		notifyDataSetChanged();
	}

	class ViewHolder {
		TextView mTxtTableName;
	}

}
