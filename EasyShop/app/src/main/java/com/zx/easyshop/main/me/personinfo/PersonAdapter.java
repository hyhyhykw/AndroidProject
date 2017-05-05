package com.zx.easyshop.main.me.personinfo;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.zx.easyshop.R;
import com.zx.easyshop.model.ItemShow;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created Time: 2017/2/14 17:10.
 *
 * @author HY
 */

public class PersonAdapter extends BaseAdapter {
    private List<ItemShow> mList;

    public PersonAdapter(List<ItemShow> list) {
        mList = list;
    }

    @Override
    public int getCount() {
        return null != mList ? mList.size() : 0;
    }

    @Override
    public ItemShow getItem(int position) {
        return mList.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        ViewHolder holder;
        if (null == convertView) {
            convertView = LayoutInflater.from(parent.getContext()).
                    inflate(R.layout.item_person_info, parent, false);
            holder = new ViewHolder(convertView);
            convertView.setTag(holder);
        } else {
            holder = (ViewHolder) convertView.getTag();
        }
        holder.mTvItemName.setText(mList.get(position).getItem_title());
        holder.mTvPerson.setText(mList.get(position).getItem_content());
        return convertView;
    }


    class ViewHolder {
        @BindView(R.id.tv_item_name)
        TextView mTvItemName;
        @BindView(R.id.tv_person)
        TextView mTvPerson;

        ViewHolder(View view) {
            ButterKnife.bind(this, view);
        }
    }
}
