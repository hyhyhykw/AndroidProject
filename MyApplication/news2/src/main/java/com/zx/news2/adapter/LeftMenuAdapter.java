package com.zx.news2.adapter;

import android.content.Context;
import android.graphics.Color;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.zx.news2.R;
import com.zx.news2.entity.LeftMenu;

import butterknife.Bind;
import butterknife.ButterKnife;

/**
 * Created by HY on 2016/12/26.
 * left menu adapter
 */

public class LeftMenuAdapter extends MyBaseAdapter<LeftMenu> {


    public LeftMenuAdapter(Context context) {
        super(context);
    }

    @Override
    public View getView(int position, View convertView) {
        ViewHolder holder;
        if (null == convertView) {
            convertView = mInflater.inflate(R.layout.item_lst_left_menu, null);
            holder = new ViewHolder(convertView);
            convertView.setTag(holder);
        } else {
            holder= (ViewHolder) convertView.getTag();
        }
        LeftMenu leftMenu=getItem(position);
        holder.mImgIcon.setImageResource(leftMenu.icon);
        holder.mTxtTitleCN.setText(leftMenu.titleCN);
        holder.mTxtTitleEN.setText(leftMenu.titleEN);
        convertView.setBackgroundColor(leftMenu.isChecked?
                Color.parseColor("#56ffbfbf") : Color.parseColor("#00000000"));
        return convertView;
    }

    class ViewHolder {

        @Bind(R.id.img_menu_icon)
        ImageView mImgIcon;
        @Bind(R.id.txt_menu_title_cn)
        TextView mTxtTitleCN;
        @Bind(R.id.txt_menu_title_en)
        TextView mTxtTitleEN;

        ViewHolder(View view) {
            ButterKnife.bind(this, view);
        }
    }
}
