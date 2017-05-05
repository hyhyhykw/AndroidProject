package com.zx.news2.adapter;

import android.support.v4.view.PagerAdapter;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import java.util.List;

/**
 * Created by HY on 2016/12/26.
 * lead adapter
 *
 * @author HY
 */
public class LeadAdapter extends PagerAdapter {


    private List<ImageView> icons;

    public LeadAdapter(List<ImageView> icons) {
        this.icons = icons;
    }

    @Override
    public int getCount() {
        return null != icons ? icons.size() : 0;
    }

    @Override
    public boolean isViewFromObject(View view, Object object) {
        return view == object;
    }

    @Override
    public Object instantiateItem(ViewGroup container, int position) {
        ImageView view = icons.get(position);
        view.setScaleType(ImageView.ScaleType.FIT_XY);//set image size
        container.addView(view);
        return view;
    }

    @Override
    public void destroyItem(ViewGroup container, int position, Object object) {
        container.removeView(icons.get(position));
    }
}
