package com.zx.eshop.feature.goods.info;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.view.ViewPager;
import android.text.SpannableString;
import android.text.Spanned;
import android.text.style.StrikethroughSpan;
import android.view.View;
import android.widget.ImageButton;
import android.widget.TextView;

import com.google.gson.Gson;
import com.squareup.picasso.Picasso;
import com.zx.eshop.R;
import com.zx.eshop.base.BaseFragment;
import com.zx.eshop.base.widgets.banner.BannerAdapter;
import com.zx.eshop.feature.goods.GoodsActivity;
import com.zx.eshop.network.core.ResponseEntity;
import com.zx.eshop.network.entity.GoodsInfo;
import com.zx.eshop.network.entity.Picture;

import butterknife.BindView;
import butterknife.OnClick;
import me.relex.circleindicator.CircleIndicator;

/**
 * <p>
 * 商品详情页面
 * </p>
 * Created by HY on 2017/3/7.
 */
public class GoodsInfoFragment extends BaseFragment {

    private static final String ARGUMENT_GOODS_INFO = "goods_info";

    @BindView(R.id.pager_goods_pictures)
    ViewPager mPagerPictures;//商品图片显示
    @BindView(R.id.indicator)
    CircleIndicator mIndicator;//圆点指示器
    @BindView(R.id.button_favorite)
    ImageButton mBtnFavorite;//收藏
    @BindView(R.id.text_goods_name)
    TextView mTvGoodsName;//商品名称
    @BindView(R.id.text_goods_price)
    TextView mTvGoodsPrice;//商品价格
    @BindView(R.id.text_market_price)
    TextView mTvMarketPrice;//市场价格

    public static GoodsInfoFragment newInstance(@NonNull GoodsInfo goodsInfo) {
        Bundle bundle = new Bundle();
        GoodsInfoFragment goodsInfoFragment = new GoodsInfoFragment();
        bundle.putString(ARGUMENT_GOODS_INFO, new Gson().toJson(goodsInfo));
        goodsInfoFragment.setArguments(bundle);
        return goodsInfoFragment;
    }

    @Override
    protected void initView() {
        //拿到传递的数据
        String json = getArguments().getString(ARGUMENT_GOODS_INFO);
        GoodsInfo goodsInfo = new Gson().fromJson(json, GoodsInfo.class);
        //ViewPager
        BannerAdapter<Picture> adapter = new BannerAdapter<Picture>() {
            @Override
            protected void bind(ViewHolder holder, Picture picture) {
                Picasso.with(getContext()).load(picture.getLarge()).into(holder.mImgBanner);
            }
        };
        //给VIewPager设置适配器和圆点指示器
        adapter.reset(goodsInfo.getPictures());
        mPagerPictures.setAdapter(adapter);

        mIndicator.setViewPager(mPagerPictures);
        adapter.registerDataSetObserver(mIndicator.getDataSetObserver());
        //商品详情：名称，价格
        mTvGoodsName.setText(goodsInfo.getName());
        mTvGoodsPrice.setText(goodsInfo.getShopPrice());
        //带划线的字体样式
        SpannableString marketPrice = new SpannableString(goodsInfo.getMarketPrice());
        marketPrice.setSpan(new StrikethroughSpan(), 0, marketPrice.length(), Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
        mTvMarketPrice.setText(marketPrice);

    }

    @Override
    protected int getContentLayout() {
        return R.layout.fragment_goods_info;
    }

    @Override
    protected void onBusinessResponse(String path, boolean isSuccess, ResponseEntity responseEntity) {
        switch (path) {

        }
    }

    //商品详情和评价
    @OnClick({R.id.text_goods_details, R.id.text_goods_comments, R.id.button_favorite})
    public void onClick(View view) {
        GoodsActivity activity = (GoodsActivity) getActivity();
        switch (view.getId()) {
            case R.id.text_goods_details:
                activity.selectPage(1);
                break;
            case R.id.text_goods_comments:
                activity.selectPage(2);
                break;
            case R.id.button_favorite:
                break;
            default:
                throw new UnsupportedOperationException("Unsupport View");
        }
    }
}
