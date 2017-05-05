package com.zx.eshop.feature.goods;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

import com.zx.eshop.base.TestFragment;
import com.zx.eshop.feature.goods.info.GoodsInfoFragment;
import com.zx.eshop.network.entity.GoodsInfo;


/**
 * 商品页面的适配器
 * Created Time: 2017/3/6 14:32.
 *
 * @author HY
 */
@SuppressWarnings("WeakerAccess")
public class GoodsPagerAdapter extends FragmentPagerAdapter {
    //商品数据
    private GoodsInfo mGoodsInfo;

    public GoodsPagerAdapter(FragmentManager fm, GoodsInfo goodsInfo) {
        super(fm);
        this.mGoodsInfo = goodsInfo;
    }

    @Override
    public Fragment getItem(int position) {
        switch (position) {
            case 0:
                return GoodsInfoFragment.newInstance(mGoodsInfo);
            case 1:
                return TestFragment.newInstance("goods details");
            case 2:
                return TestFragment.newInstance("goods comment");
            default:
                throw new UnsupportedOperationException("Position " + position);
        }
    }

    @Override
    public int getCount() {
        return 3;
    }
}
