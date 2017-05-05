package com.zx.imageloader;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;


import com.zx.imageloader.loader.ImageLoader;

/**
 * Created time : 2017/3/9 15:11.
 *
 * @author HY
 */

class ImageAdapter extends BaseAdapter {

    private String[] mList;


    public void setData(String[] list) {
        mList = list;
    }

    @Override
    public int getCount() {
        return null != mList ? mList.length : 0;
    }

    @Override
    public String getItem(int position) {
        return mList[position];
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        ViewHolder holder;
        if (null==convertView){
            convertView= LayoutInflater.from(parent.getContext())
                    .inflate(R.layout.item,parent,false);
            holder=new ViewHolder(convertView);
            convertView.setTag(holder);
        }else{
             holder= (ViewHolder) convertView.getTag();
        }
        ImageLoader.getInstance().loadImage(getItem(position),holder.mImageView,true);
        return convertView;
    }

    private class ViewHolder {
        ImageView mImageView;

        ViewHolder(View view) {
            mImageView = (ImageView) view.findViewById(R.id.img);
        }
    }
}
