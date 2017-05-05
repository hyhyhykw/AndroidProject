package com.zx.easyshop.main.shop;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.hannesdorfmann.mosby.mvp.MvpFragment;
import com.zx.easyshop.R;
import com.zx.easyshop.commons.ActivityUtils;
import com.zx.easyshop.main.shop.detail.GoodDetailActivity;
import com.zx.easyshop.model.GoodsInfo;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import butterknife.Unbinder;
import in.srain.cube.views.ptr.PtrClassicFrameLayout;
import in.srain.cube.views.ptr.PtrDefaultHandler2;
import in.srain.cube.views.ptr.PtrFrameLayout;

/**
 * Created Time: 2017/2/7 18:49.
 *
 * @author HY
 */
public class ShopFragment extends MvpFragment<ShopView, ShopPresenter> implements ShopView {
    @BindView(R.id.recyclerView)
    protected RecyclerView mRecyclerView;
    @BindView(R.id.refreshLayout)
    protected PtrClassicFrameLayout mRefreshLayout;
    @BindView(R.id.tv_load_error)
    protected TextView mTvLoadError;

    private ActivityUtils mActivityUtils;
    private ShopAdapter mShopAdapter;
    private List<GoodsInfo> mDatas = new ArrayList<>();
    private String pageType = "";
    private Unbinder mUnbinder;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_shop, container, false);
        mUnbinder = ButterKnife.bind(this, view);
        return view;
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        init();

    }

    //初始化
    private void init() {
        mActivityUtils = new ActivityUtils(this);
        mShopAdapter = new ShopAdapter(mDatas);
        //初始化RecyclerView
        mRecyclerView.setLayoutManager(new GridLayoutManager(getContext(), 2));
        //设置点击事件
        mShopAdapter.setOnItemClickListener(new ShopAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(GoodsInfo goodsInfo) {
                // 跳转到详情页
                Intent intent = GoodDetailActivity.getStartIntent(getContext(), goodsInfo.getTableId(), 0);
                startActivity(intent);
            }
        });
        //设置适配器
        mRecyclerView.setAdapter(mShopAdapter);

        //####################      初始化下拉刷新控件   ####################
        //使用本对象作为key，用来记录上一次刷新的事件
        mRefreshLayout.setLastUpdateTimeRelateObject(this);
        //显示下拉刷新的背景色
        mRefreshLayout.setBackgroundResource(R.color.recycler_bg);
        //设置关闭head所需要的事件
        mRefreshLayout.setDurationToCloseHeader(1500);
        //实现刷新、加载回调
        mRefreshLayout.setPtrHandler(new PtrDefaultHandler2() {
            @Override
            public void onLoadMoreBegin(PtrFrameLayout frame) {
                //开始加载
                presenter.load(pageType);
            }

            @Override
            public void onRefreshBegin(PtrFrameLayout frame) {
                //开始刷新
                presenter.refresh(pageType);
            }
        });

    }

    @Override
    public void onStart() {
        super.onStart();
        //自动刷新
        if (mShopAdapter.getItemCount()<=0){
            mRefreshLayout.autoRefresh();
        }
    }

    //点击加载错误、自动刷新
    @OnClick(R.id.tv_load_error)
    public void onClick() {
        mRefreshLayout.autoRefresh();
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        mUnbinder.unbind();
    }

    //###############################     视图实现        ####################################
    @Override
    public void showRefresh() {
        mTvLoadError.setVisibility(View.GONE);
    }

    //刷新错误
    @Override
    public void showRefreshError(String msg) {
        //停止刷新
        mRefreshLayout.refreshComplete();
        //判断是否拿到数据
        if (mShopAdapter.getItemCount() > 0)
            mActivityUtils.showToast(msg);
        else
            mTvLoadError.setVisibility(View.VISIBLE);
    }

    @Override//刷新结束
    public void showRefreshEnd() {
        mActivityUtils.showToast(R.string.refresh_more_end);
    }

    @Override
    public void hideRefresh() {
        //停止刷新
        mRefreshLayout.refreshComplete();
    }

    @Override
    public void showLoadMore() {
        mTvLoadError.setVisibility(View.GONE);
    }

    @Override//加载错误
    public void showLoadMoreError(String msg) {
        //停止刷新
        mRefreshLayout.refreshComplete();
        //判断是否拿到数据
        if (mShopAdapter.getItemCount() > 0)
            mActivityUtils.showToast(msg);
        else
            mTvLoadError.setVisibility(View.VISIBLE);
    }

    @Override
    public void showLoadMoreEnd() {
        mActivityUtils.showToast(R.string.load_more_end);
        //结束刷新
        mRefreshLayout.refreshComplete();
    }

    @Override
    public void hideLodeMore() {
        //结束刷新
        mRefreshLayout.refreshComplete();
    }

    @Override
    public void addRefreshData(List<GoodsInfo> datas) {
        mShopAdapter.clear();
        mShopAdapter.addData(datas);
    }

    @Override
    public void addMoreData(List<GoodsInfo> data) {
        mShopAdapter.addData(data);
    }

    @Override
    public void showMsg(String msg) {
        mActivityUtils.showToast(msg);
    }

    //创建操作类对象
    @NonNull
    @Override
    public ShopPresenter createPresenter() {
        return new ShopPresenter();
    }
}
