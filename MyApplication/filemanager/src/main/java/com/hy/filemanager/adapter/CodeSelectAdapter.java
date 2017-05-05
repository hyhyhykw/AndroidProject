package com.hy.filemanager.adapter;

import java.util.List;

import com.hy.filemanager.R;
import com.hy.filemanager.entity.CodeItemInfo;
import com.hy.filemanager.utils.FileUitls;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewGroup.LayoutParams;
import android.widget.BaseAdapter;
import android.widget.TextView;

public class CodeSelectAdapter extends BaseAdapter {

	private Context mContext;
	private List<CodeItemInfo> itemInfos;

	public CodeSelectAdapter(Context context, List<CodeItemInfo> itemInfos) {
		this.mContext = context;
		this.itemInfos = itemInfos;
	}

	@Override
	public int getCount() {
		return null != itemInfos ? itemInfos.size() : 0;
	}

	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		ViewHolder holder;
		if (null == convertView) {
			holder = new ViewHolder();
			convertView = LayoutInflater.from(mContext).inflate(
					R.layout.item_lst_charset_list, null);
			holder.mTxtCode = (TextView) convertView
					.findViewById(R.id.txt_charset_code);
			holder.mTxtSelect = (TextView) convertView
					.findViewById(R.id.txt_charset_default);
			convertView.setTag(holder);
		} else {
			holder = (ViewHolder) convertView.getTag();
		}
		CodeItemInfo itemInfo = getItem(position);
		holder.mTxtCode.setText(itemInfo.getCharsetCode());
		boolean isChecked = itemInfo.isChecked();
		convertView.setBackgroundResource(isChecked ? R.drawable.ic_click
				: R.drawable.ic_transparent);
		LayoutParams params = new LayoutParams(LayoutParams.MATCH_PARENT,
				35 * FileUitls.getDensity(mContext) / 160);
		// px = dip * density/ 160
		convertView.setLayoutParams(params);
		holder.mTxtSelect.setVisibility(isChecked ? View.VISIBLE
				: View.INVISIBLE);
		return convertView;
	}

	@Override
	public CodeItemInfo getItem(int position) {
		return itemInfos.get(position);
	}

	@Override
	public long getItemId(int position) {
		return position;
	}

	public void update(List<CodeItemInfo> itemInfos) {
		this.itemInfos = itemInfos;
		notifyDataSetChanged();
	}

	/**
	 * 
	 * @author HY
	 * 
	 */
	class ViewHolder {
		TextView mTxtCode;
		TextView mTxtSelect;
	}

}
