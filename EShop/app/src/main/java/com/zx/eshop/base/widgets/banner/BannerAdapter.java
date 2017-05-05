package com.zx.eshop.base.widgets.banner;

import android.support.annotation.NonNull;
import android.support.v4.view.PagerAdapter;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.zx.eshop.R;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created Time: 2017/2/27 11:35.
 *
 * @author HY
 *         自动轮播控件的适配器，为了更加广泛应用，可以写成通用的
 *         数据不确定，但是视图是确定的
 */
public abstract class BannerAdapter<T> extends PagerAdapter {

    private List<T> mDatas = new ArrayList<>();


    public void reset(@NonNull List<T> views) {
        mDatas.clear();
        mDatas.addAll(views);
        notifyDataSetChanged();
    }

    @Override
    public int getCount() {
        return mDatas.size();
    }

    @Override
    public boolean isViewFromObject(View view, Object object) {
        ViewHolder viewholder = (ViewHolder) object;
        return view == viewholder.mItemView;
    }

    @Override
    public Object instantiateItem(ViewGroup container, int position) {
        View view = LayoutInflater.from(container.getContext())
                .inflate(R.layout.item_banner, container, false);
        container.addView(view);
        ViewHolder viewHolder = new ViewHolder(view);

        bind(viewHolder, mDatas.get(position));
        return viewHolder;
    }

    @Override
    public void destroyItem(ViewGroup container, int position, Object object) {
        ViewHolder holder = (ViewHolder) object;
        container.removeView(holder.mItemView);
    }

    protected abstract void bind(ViewHolder holder, T t);

    //########################         ViewHolder      #############################
    public static class ViewHolder {

        @BindView(R.id.image_banner_item)
        public ImageView mImgBanner;

        private View mItemView;

        public ViewHolder(View itemView) {
            mItemView = itemView;
            ButterKnife.bind(this, itemView);
        }
    }
}
