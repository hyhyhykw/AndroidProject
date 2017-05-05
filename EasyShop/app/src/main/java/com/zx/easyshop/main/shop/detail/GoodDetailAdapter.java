package com.zx.easyshop.main.shop.detail;

import android.support.v4.view.PagerAdapter;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import java.util.ArrayList;

/**
 * Created Time: 2017/2/16 16:26.
 *
 * @author HY
 */

public class GoodDetailAdapter extends PagerAdapter {
    private ArrayList<ImageView> mImageViews;
    private OnItemClickListener mOnItemClickListener;


    public GoodDetailAdapter(ArrayList<ImageView> imageViews) {
        mImageViews = imageViews;
    }

    //设置图片点击事件
    public void setOnItemClickListener(OnItemClickListener onItemClickListener) {
        mOnItemClickListener = onItemClickListener;
    }

    @Override
    public int getCount() {
        return null != mImageViews ? mImageViews.size() : 0;
    }

    @Override
    public boolean isViewFromObject(View view, Object object) {
        return view == object;
    }

    @Override
    public Object instantiateItem(ViewGroup container, final int position) {
        ImageView imageView = mImageViews.get(position);

        imageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (null != mOnItemClickListener)
                    mOnItemClickListener.onItemClick(position);
            }
        });

        container.addView(imageView);
        return imageView;
    }

    @Override
    public void destroyItem(ViewGroup container, int position, Object object) {
        container.removeView((View) object);
    }


    //#######################     Image点击         ############

    public interface OnItemClickListener {
        void onItemClick(int position);
    }
}
