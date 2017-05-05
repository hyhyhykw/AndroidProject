package com.feicuiedu.eshop_0221.feature.home;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.feicuiedu.eshop_0221.R;
import com.feicuiedu.eshop_0221.base.BaseFragment;
import com.feicuiedu.eshop_0221.base.widgets.banner.BannerAdapter;
import com.feicuiedu.eshop_0221.base.widgets.banner.BannerLayout;
import com.feicuiedu.eshop_0221.base.wrapper.PtrWrapper;
import com.feicuiedu.eshop_0221.base.wrapper.ToastWrapper;
import com.feicuiedu.eshop_0221.base.wrapper.ToolbarWrapper;
import com.feicuiedu.eshop_0221.feature.goods.GoodsActivity;
import com.feicuiedu.eshop_0221.network.EShopClient;
import com.feicuiedu.eshop_0221.network.api.ApiHomeBanner;
import com.feicuiedu.eshop_0221.network.api.ApiHomeCategory;
import com.feicuiedu.eshop_0221.network.core.ApiPath;
import com.feicuiedu.eshop_0221.network.core.ResponseEntity;
import com.feicuiedu.eshop_0221.network.core.UICallback;
import com.feicuiedu.eshop_0221.network.entity.Banner;
import com.feicuiedu.eshop_0221.network.entity.HomeBannerRsp;
import com.feicuiedu.eshop_0221.network.entity.HomeCategoryRsp;
import com.feicuiedu.eshop_0221.network.entity.Picture;
import com.feicuiedu.eshop_0221.network.entity.SimpleGoods;
import com.google.gson.Gson;
import com.squareup.picasso.Picasso;

import java.io.IOException;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import in.srain.cube.views.ptr.PtrClassicDefaultHeader;
import in.srain.cube.views.ptr.PtrDefaultHandler2;
import in.srain.cube.views.ptr.PtrFrameLayout;
import jp.wasabeef.picasso.transformations.CropCircleTransformation;
import jp.wasabeef.picasso.transformations.GrayscaleTransformation;
import okhttp3.Call;
import okhttp3.Response;

/**
 * Created by gqq on 2017/2/27.
 */

public class HomeFragment extends BaseFragment {


    @BindView(R.id.standard_toolbar_title)
    TextView mToolbarTitle;
    @BindView(R.id.standard_toolbar)
    Toolbar mToolbar;
    @BindView(R.id.list_home_goods)
    ListView mListHomeGoods;

    private ImageView[] mIvPromotes = new ImageView[4];
    private TextView mMTvPromoteGoods;
    private BannerAdapter<Banner> mBannerAdapter;
    private HomeGoodsAdapter mGoodsAdapter;
    private PtrWrapper mPtrWrapper;

    private boolean mBannerRefreshed = false;
    private boolean mCategoryRefreshed = false;

    public static HomeFragment newInstance() {
        return new HomeFragment();
    }

    @Override
    protected int getContentViewLayout() {
        return R.layout.fragment_home;
    }

    @Override
    protected void initView() {
        // 利用Toolbar的包装类
        new ToolbarWrapper(this).setCustomTitle(R.string.home_title);

        // 利用刷新的包装类
        mPtrWrapper = new PtrWrapper(this,false) {
            @Override
            protected void onRefresh() {

                mBannerRefreshed = false;
                mCategoryRefreshed = false;

                // 轮播图的数据获取
                enqueue(new ApiHomeBanner());
                // 分类及推荐商品数据
                enqueue(new ApiHomeCategory());
            }

            @Override
            protected void onLoadMore() {

            }
        };
        mPtrWrapper.postRefreshDelayed(50);


        // ListView的头布局
        View view = LayoutInflater.from(getContext()).inflate(R.layout.partial_home_header,mListHomeGoods,false);

        // 找到头布局里面的控件
        BannerLayout bannerLayout = ButterKnife.findById(view,R.id.layout_banner);
        // 数据和视图的绑定
        mBannerAdapter = new BannerAdapter<Banner>() {
            @Override
            protected void bind(ViewHolder holder, Banner data) {
                // 数据和视图的绑定
//                holder.mImageView.setImageResource(R.drawable.image_holder_banner);
                Picasso.with(getContext()).load(data.getPicture().getLarge()).into(holder.mImageView);
            }
        };
        bannerLayout.setAdapter(mBannerAdapter);

        // 促销商品
        mIvPromotes[0] = ButterKnife.findById(view,R.id.image_promote_one);
        mIvPromotes[1] = ButterKnife.findById(view,R.id.image_promote_two);
        mIvPromotes[2] = ButterKnife.findById(view,R.id.image_promote_three);
        mIvPromotes[3] = ButterKnife.findById(view,R.id.image_promote_four);

        // 促销单品的TextView
        mMTvPromoteGoods = ButterKnife.findById(view, R.id.text_promote_goods);

        mListHomeGoods.addHeaderView(view);

        // 设置适配器
        mGoodsAdapter = new HomeGoodsAdapter();
        mListHomeGoods.setAdapter(mGoodsAdapter);
    }

    // 去请求数据
//    public void getHomeData() {
//
//        // 轮播图和促销单品的数据
//        UICallback bannerCallback = new UICallback() {
//            @Override
//            public void onBusinessResponse(boolean isSucces, ResponseEntity responseEntity) {
//                mBannerRefreshed = true;
//                if (isSucces){
//                    // 数据拿到了，首先给bannerAdapter,另外是给促销单品
//                    HomeBannerRsp bannerRsp = (HomeBannerRsp) responseEntity;
//                    mBannerAdapter.reset(bannerRsp.getData().getBanners());
//                    setPromoteGoods(bannerRsp.getData().getGoodsList());
//                }
//                if (mBannerRefreshed && mCategoryRefreshed){
//                    //两个接口都拿到数据之后，停止刷新
//                    mPtrWrapper.stopRefresh();
//                }
//            }
//        };
//
//        // 首页分类商品和推荐
//        UICallback categoryCallback = new UICallback() {
//            @Override
//            public void onBusinessResponse(boolean isSucces, ResponseEntity responseEntity) {
//                mCategoryRefreshed = true;
//                if(isSucces){
//                    HomeCategoryRsp categoryRsp = (HomeCategoryRsp) responseEntity;
//                    mGoodsAdapter.reset(categoryRsp.getData());
//                }
//                if (mBannerRefreshed && mCategoryRefreshed){
//                    //两个接口都拿到数据之后，停止刷新
//                    mPtrWrapper.stopRefresh();
//                }
//            }
//        };
//        EShopClient.getInstance().enqueue(ApiPath.HOME_DATA,null,HomeBannerRsp.class,bannerCallback);
//        EShopClient.getInstance().enqueue(ApiPath.HOME_CATEGORY,null,HomeCategoryRsp.class,categoryCallback);
//    }

    // 设置促销单品的展示
    private void setPromoteGoods(List<SimpleGoods> goodsList) {
        mMTvPromoteGoods.setVisibility(View.VISIBLE);
        for (int i = 0; i < mIvPromotes.length; i++) {
            mIvPromotes[i].setVisibility(View.VISIBLE);
            final SimpleGoods simpleGoods = goodsList.get(i);
            Picture picture = simpleGoods.getImg();
//            mIvPromotes[i].setImageResource(R.drawable.image_holder_goods);

            // 圆形、灰度
            Picasso.with(getContext()).load(picture.getSmall())
                    .transform(new CropCircleTransformation())// 圆形
                    .transform(new GrayscaleTransformation())// 灰度
                    .into(mIvPromotes[i]);

            mIvPromotes[i].setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {

                    // 点击促销单品跳转到商品详情页
                    int simpleGoodsId = simpleGoods.getId();
                    Intent intent = GoodsActivity.getStartIntent(getContext(), simpleGoodsId);
                    getActivity().startActivity(intent);
                }
            });
        }
    }

    // 拿到响应数据处理
    @Override
    protected void onBusinessResponse(String path, boolean isSucces, ResponseEntity responseEntity) {
        switch (path){
            // 轮播图请求数据
            case ApiPath.HOME_DATA:

                mBannerRefreshed = true;
                if (isSucces){
                    // 数据拿到了，首先给bannerAdapter,另外是给促销单品
                    HomeBannerRsp bannerRsp = (HomeBannerRsp) responseEntity;
                    mBannerAdapter.reset(bannerRsp.getData().getBanners());
                    setPromoteGoods(bannerRsp.getData().getGoodsList());
                }

                break;

            // 分类和推荐的请求数据
            case ApiPath.HOME_CATEGORY:

                mCategoryRefreshed = true;
                if(isSucces){
                    HomeCategoryRsp categoryRsp = (HomeCategoryRsp) responseEntity;
                    mGoodsAdapter.reset(categoryRsp.getData());
                }

                break;
            default:
                throw new UnsupportedOperationException(path);
        }

        if (mBannerRefreshed && mCategoryRefreshed){
            //两个接口都拿到数据之后，停止刷新
            mPtrWrapper.stopRefresh();
        }
    }
}
