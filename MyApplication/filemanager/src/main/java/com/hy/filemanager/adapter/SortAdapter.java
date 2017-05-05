package com.hy.filemanager.adapter;

import java.util.List;

import com.hy.filemanager.R;
import com.hy.filemanager.entity.SortInfo;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.CheckBox;
import android.widget.TextView;

public class SortAdapter extends BaseAdapter {

	private Context mContext;
	private List<SortInfo> sortInfos;

	public SortAdapter(Context context, List<SortInfo> sortInfos) {
		this.mContext = context;
		this.sortInfos = sortInfos;
	}

	@Override
	public int getCount() {
		return null != sortInfos ? sortInfos.size() : 0;
	}

	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		ViewHolder holder;
		if (null == convertView) {
			holder = new ViewHolder();
			convertView = LayoutInflater.from(mContext).inflate(
					R.layout.item_lst_sort_mode_list, null);
			holder.mTxtSortMode = (TextView) convertView
					.findViewById(R.id.txt_sort_mode);
			holder.mChbSortMode = (CheckBox) convertView
					.findViewById(R.id.chb_sort_mode);
			convertView.setTag(holder);
		} else {
			holder = (ViewHolder) convertView.getTag();
		}
		SortInfo sInfo = getItem(position);
		holder.mTxtSortMode.setText(sInfo.getSortMode());
		holder.mChbSortMode.setChecked(sInfo.isChecked());
		return convertView;
	}

	@Override
	public SortInfo getItem(int position) {
		return sortInfos.get(position);
	}

	@Override
	public long getItemId(int position) {
		return position;
	}

	public void update(List<SortInfo> sortInfos) {
		this.sortInfos = sortInfos;
		notifyDataSetChanged();
	}

	class ViewHolder {
		TextView mTxtSortMode;
		CheckBox mChbSortMode;
	}
}
