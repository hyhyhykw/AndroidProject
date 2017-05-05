package com.hy.filemanager.adapter;

import com.hy.filemanager.R;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

public class OperateAdapter extends BaseAdapter {

	private Context mContext;
	private String[] operates;

	public OperateAdapter(Context mContext, String[] operates) {
		this.mContext = mContext;
		this.operates = operates;
	}

	@Override
	public int getCount() {
		return null != operates ? operates.length : 0;
	}

	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		ViewHolder holder;
		if (null == convertView) {
			holder = new ViewHolder();
			convertView = LayoutInflater.from(mContext).inflate(
					R.layout.item_lst_operate, null);
			holder.mTxtOperate = (TextView) convertView
					.findViewById(R.id.txt_operate);
			convertView.setTag(holder);
		} else {
			holder = (ViewHolder) convertView.getTag();
		}
		holder.mTxtOperate.setText(operates[position]);
		return convertView;
	}

	@Override
	public String getItem(int position) {
		return operates[position];
	}

	@Override
	public long getItemId(int position) {
		return position;
	}

	class ViewHolder {
		TextView mTxtOperate;
	}
}
