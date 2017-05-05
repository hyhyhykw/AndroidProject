package com.feicuiedu.eshop_0221.feature.goods;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

import com.feicuiedu.eshop_0221.base.TestFragment;
import com.feicuiedu.eshop_0221.network.entity.GoodsInfo;

/**
 * Created by gqq on 2017/3/6.
 */

// 商品页面的适配器
public class GoodsPagerAdapter extends FragmentPagerAdapter {

    // 数据的传递
    private GoodsInfo mGoodsInfo;

    public GoodsPagerAdapter(FragmentManager fm,GoodsInfo goodsInfo) {
        super(fm);
        this.mGoodsInfo = goodsInfo;
    }

    @Override
    public Fragment getItem(int position) {
        switch (position) {
            case 0:
                return TestFragment.newInstance(mGoodsInfo.getName());
            case 1:
                return TestFragment.newInstance("goods details");
            case 2:
                return TestFragment.newInstance("goods comments");
            default:
                throw new UnsupportedOperationException("Position" + position);
        }
    }

    @Override
    public int getCount() {
        return 3;
    }
}
