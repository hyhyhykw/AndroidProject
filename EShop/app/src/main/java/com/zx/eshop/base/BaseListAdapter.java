package com.zx.eshop.base;

import android.content.Context;
import android.support.annotation.LayoutRes;
import android.support.annotation.NonNull;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;

import com.zx.eshop.base.BaseListAdapter.ViewHolder;

import java.util.ArrayList;
import java.util.List;

import butterknife.ButterKnife;

/**
 * Created Time: 2017/2/24 13:58.
 *
 * @param <T> 数据源
 * @param <V> ViewHolder
 * @author HY
 */
@SuppressWarnings("unchecked,unused")
public abstract class BaseListAdapter<T, V extends ViewHolder> extends BaseAdapter {
    //数据
    private List<T> mDatas = new ArrayList<>();
    //上下文对象
    private Context mContext;

    //获取适配器中的数据
    public List<T> getDatas() {
        return mDatas;
    }

    /**
     * 获取上下文对象
     *
     * @return 上下文对象
     */
    public final Context getContext() {
        return mContext;
    }

    @Override
    public int getCount() {
        return mDatas.size();
    }

    /**
     * 重置数据源
     *
     * @param datas 替换的数据
     */
    public void reset(@NonNull List<T> datas) {
        mDatas.clear();
        mDatas.addAll(datas);
        notifyDataSetChanged();
    }

    /**
     * 添加数据
     *
     * @param datas 新增的数据
     */
    public void addAll(@NonNull List<T> datas) {
        mDatas.addAll(datas);
        notifyDataSetChanged();
    }


    @Override
    public T getItem(int position) {
        return mDatas.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        mContext = parent.getContext();
        ViewHolder viewHolder;
        if (null == convertView) {
            convertView = LayoutInflater.from(mContext).inflate(getItemViewLayout(), parent, false);
            convertView.setTag(getItemViewHolder(convertView));
        }
        viewHolder = (ViewHolder) convertView.getTag();
        viewHolder.bind(position);
        return convertView;
    }

    /**
     * 获取布局id
     *
     * @return 布局id
     */
    @LayoutRes
    protected abstract int getItemViewLayout();

    /**
     * 获取Viewholder对象
     *
     * @param view 视图
     * @return viewHolder
     */
    protected abstract V getItemViewHolder(View view);


    //#########################         ViewHolder        ########################
    public abstract class ViewHolder {
        private View itemView;

        public ViewHolder(View itemView) {
            ButterKnife.bind(this, itemView);
            this.itemView = itemView;
        }

        public View getItemView() {
            return itemView;
        }

        // 让数据和视图绑定
        protected abstract void bind(int position);
    }
}
