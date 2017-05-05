package com.zx.eshop.feature.home;

import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

import com.squareup.picasso.Picasso;
import com.zx.eshop.R;
import com.zx.eshop.base.BaseFragment;
import com.zx.eshop.base.widgets.banner.BannerAdapter;
import com.zx.eshop.base.widgets.banner.BannerLayout;
import com.zx.eshop.base.wrapper.PtrWrapper;
import com.zx.eshop.base.wrapper.ToolbarWrapper;
import com.zx.eshop.feature.goods.GoodsActivity;
import com.zx.eshop.network.api.ApiHomeBanner;
import com.zx.eshop.network.api.ApiHomeCategory;
import com.zx.eshop.network.core.ApiPath;
import com.zx.eshop.network.core.ResponseEntity;
import com.zx.eshop.network.entity.Banner;
import com.zx.eshop.network.entity.HomeBannerRsp;
import com.zx.eshop.network.entity.HomeCategoryRsp;
import com.zx.eshop.network.entity.Picture;
import com.zx.eshop.network.entity.SimpleGoods;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import jp.wasabeef.picasso.transformations.CropCircleTransformation;
import jp.wasabeef.picasso.transformations.GrayscaleTransformation;

/**
 * Created Time: 2017/2/24 9:34.
 *
 * @author HY
 */

public class HomeFragment extends BaseFragment {

    @BindView(R.id.standard_toolbar_title)
    protected TextView mTitle;//标题
    @BindView(R.id.list_home_goods)
    protected ListView mListHomeGoods;//列表

    private HomeGoodsAdapter mGoodsAdapter;

    private ImageView[] mIvPromotes = new ImageView[4];
    private BannerAdapter<Banner> mBannerAdapter;
    private TextView mTvPromote;

    private PtrWrapper mPtrWrapper;

    private boolean mBannerRefreshed;
    private boolean mCategoryRefreshed;

    @Override //初始化视图
    protected void initView() {
        //初始化Toolbar
        new ToolbarWrapper(this)
                .setShowBack(false)
                .setShowTitle(false)
                .setCustomTitle(R.string.home_title);

        //初始化下拉刷新
        mPtrWrapper = new PtrWrapper(this, false) {
            @Override
            protected void onRefresh() {
                mBannerRefreshed = false;
                mCategoryRefreshed = false;

                getHomeData();
            }

            @Override
            protected void onLoadMore() {
            }
        };
        //延时刷新
        mPtrWrapper.postDelayed(50);

        //商品列表适配器
        mGoodsAdapter = new HomeGoodsAdapter();
        mListHomeGoods.setAdapter(mGoodsAdapter);
        //ListView的头布局
        View view = LayoutInflater.from(getContext()).inflate(R.layout.partial_home_header, mListHomeGoods, false);
        //轮播图
        BannerLayout bannerLayout = ButterKnife.findById(view, R.id.layout_banner);
        //设置适配器
        mBannerAdapter = new BannerAdapter<Banner>() {
            @Override
            protected void bind(ViewHolder holder, Banner banner) {
                //数据和视图的绑定
                holder.mImgBanner.setImageResource(R.drawable.image_holder_banner);
                //Picasso加载图片
                Picasso.with(getContext())
                        .load(banner.getPicture().getLarge())
                        .into(holder.mImgBanner);
            }
        };
        bannerLayout.setAdapter(mBannerAdapter);

        //促销商品
        mIvPromotes[0] = ButterKnife.findById(view, R.id.image_promote_one);
        mIvPromotes[1] = ButterKnife.findById(view, R.id.image_promote_two);
        mIvPromotes[2] = ButterKnife.findById(view, R.id.image_promote_three);
        mIvPromotes[3] = ButterKnife.findById(view, R.id.image_promote_four);
        //促销商品的TextView
        mTvPromote = ButterKnife.findById(view, R.id.text_promote_goods);
        mListHomeGoods.addHeaderView(view);
    }


    //请求数据
    private void getHomeData() {
        //轮播图请求
        enqueue(new ApiHomeBanner());
        //主页分类
        enqueue(new ApiHomeCategory());
    }

    //设置促销单品展示
    private void setPromoteGoods(List<SimpleGoods> goodsList) {
        mTvPromote.setVisibility(View.VISIBLE);
        for (int i = 0; i < mIvPromotes.length; i++) {
            mIvPromotes[i].setVisibility(View.VISIBLE);
            final SimpleGoods simpleGoods = goodsList.get(i);
            Picture picture = simpleGoods.getImg();
            //Picasso加载图片
            Picasso.with(getContext())
                    .load(picture.getLarge())
                    .transform(new CropCircleTransformation()) //圆形
                    .transform(new GrayscaleTransformation()) //灰度
                    .into(mIvPromotes[i]);

            mIvPromotes[i].setOnClickListener(new OnClickListener() {
                @Override
                public void onClick(View v) {
                    //点击促销商品跳转商品详情页
                    Intent intent = GoodsActivity.getStartIntent(getContext(), simpleGoods.getId());
                    startActivity(intent);
                }
            });
        }

    }

    @Override
    protected int getContentLayout() {
        return R.layout.fragment_home;
    }

    @Override
    protected void onBusinessResponse(String path, boolean isSuccess, ResponseEntity responseEntity) {
        switch (path) {
            case ApiPath.HOME_DATA://轮播图数据
                mBannerRefreshed = true;

                if (isSuccess) {
                    HomeBannerRsp homeBannerRsp = (HomeBannerRsp) responseEntity;
                    //拿到数据，首先给bannerAdapter，另外是给促销单品
                    mBannerAdapter.reset(homeBannerRsp.getData().getBanners());
                    setPromoteGoods(homeBannerRsp.getData().getGoodsList());
                }

                break;
            case ApiPath.HOME_CATEGORY://分类数据

                mCategoryRefreshed = true;
                if (isSuccess) {
                    //拿到推荐分类数据
                    HomeCategoryRsp homeCategoryRsp = (HomeCategoryRsp) responseEntity;
                    mGoodsAdapter.reset(homeCategoryRsp.getData());
                }

                break;
            default:
                throw new UnsupportedOperationException(path);
        }

        //获取到数据之后，停止刷新
        if (mBannerRefreshed && mCategoryRefreshed)
            mPtrWrapper.stopRefresh();
    }

    public static HomeFragment newInstance() {
        return new HomeFragment();
    }
}
