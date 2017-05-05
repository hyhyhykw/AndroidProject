package com.zx.news2.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;

import java.util.List;

/**
 * Created by HY on 2016/12/23.
 * adapter super class
 */

@SuppressWarnings("WeakerAccess")
public abstract class MyBaseAdapter<T> extends BaseAdapter {
    protected Context mContext;
    protected LayoutInflater mInflater;
    protected List<T> datas;

    public MyBaseAdapter(Context context) {
        this.mContext = context;
        mInflater = LayoutInflater.from(context);
    }

    @Override
    public int getCount() {
        return null != datas ? datas.size() : 0;
    }

    @Override
    public T getItem(int position) {
        if ((this.datas == null) || (this.datas.size() <= 0))
            return null;
        return this.datas.get(position);
    }

    @Override
    public long getItemId(int position) {
        return 0;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup container) {
        return getView(position, convertView);
    }

    /**
     * convert layout to view
     *
     * @param position    child item index
     * @param convertView convert view
     * @return target view
     */
    public abstract View getView(int position, View convertView);

    /**
     * add data source
     *
     * @param datas data source
     */
    public void addData(List<T> datas) {
        this.datas = datas;
    }

    /**
     * load more
     *
     * @param newData new datas
     */
    public void appendData(List<T> newData) {
        datas = newData;
        notifyDataSetChanged();
    }

    /**
     * load
     *
     * @param newData new datas
     */
    public void addNewData(List<T> newData) {
        datas.addAll(newData);
        notifyDataSetChanged();
    }

    /**
     * update this adapter
     */
    public void update() {
        notifyDataSetChanged();
    }
}
