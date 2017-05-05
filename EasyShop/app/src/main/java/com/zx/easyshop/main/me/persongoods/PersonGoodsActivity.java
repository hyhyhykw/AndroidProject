package com.zx.easyshop.main.me.persongoods;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;

import com.hannesdorfmann.mosby.mvp.MvpActivity;
import com.zx.easyshop.R;
import com.zx.easyshop.commons.ActivityUtils;
import com.zx.easyshop.main.shop.ShopAdapter;
import com.zx.easyshop.main.shop.ShopView;
import com.zx.easyshop.main.shop.detail.GoodDetailActivity;
import com.zx.easyshop.model.GoodsInfo;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindString;
import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;
import in.srain.cube.views.ptr.PtrClassicFrameLayout;
import in.srain.cube.views.ptr.PtrDefaultHandler2;
import in.srain.cube.views.ptr.PtrFrameLayout;

@SuppressWarnings("ConstantConditions")
public class PersonGoodsActivity extends MvpActivity<ShopView, PersonGoodsPresenter> implements ShopView {

    @BindView(R.id.toolbar)
    protected Toolbar mToolbar;
    @BindView(R.id.recyclerView)
    protected RecyclerView mRecyclerView;
    @BindView(R.id.refreshLayout)
    protected PtrClassicFrameLayout mRefreshLayout;
    @BindView(R.id.tv_load_error)
    protected TextView mTvLoadError;
    @BindView(R.id.tv_load_empty)
    protected TextView mTvLoadEmpty;

    @BindString(R.string.load_more_end)
    protected String loadMoreEnd;

    private ActivityUtils mActivityUtils;
    private Unbinder mUnbinder;
    private String type = "";//空字符串显示全部数据
    private ShopAdapter mShopAdapter;//适配器
    private List<GoodsInfo> mDatas = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_person_goods);
        mUnbinder = ButterKnife.bind(this);
        mActivityUtils = new ActivityUtils(this);

        mShopAdapter = new ShopAdapter(mDatas);
        //左上角返回按钮
        setSupportActionBar(mToolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        //右上角菜单按钮 设置toolbar监听事件
        mToolbar.setOnMenuItemClickListener(mMenuItemClickListener);

        //初始化视图
        initView();
    }

    private void initView() {
        //初始化recyclerView
        mRecyclerView.setLayoutManager(new GridLayoutManager(this, 2));
        mShopAdapter.setOnItemClickListener(new ShopAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(GoodsInfo goodsInfo) {
                // 商品详情页面
                Intent intent = GoodDetailActivity.getStartIntent(PersonGoodsActivity.this, goodsInfo.getTableId(), 1);
                startActivity(intent);
            }
        });
        mRecyclerView.setAdapter(mShopAdapter);
        //初始化下拉刷新控件
        mRefreshLayout.setLastUpdateTimeRelateObject(this);
        mRefreshLayout.setBackgroundResource(R.color.recycler_bg);
        mRefreshLayout.setDurationToCloseHeader(1500);
        //回调
        mRefreshLayout.setPtrHandler(new PtrDefaultHandler2() {
            @Override
            public void onLoadMoreBegin(PtrFrameLayout frame) {
                presenter.load(type);
            }

            @Override
            public void onRefreshBegin(PtrFrameLayout frame) {
                presenter.refresh(type);
            }
        });
    }

    // 创建菜单选项
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        //创建菜单选项
        getMenuInflater().inflate(R.menu.menu_goods_type, menu);
        return true;
    }

    //toolbar监听事件
    private Toolbar.OnMenuItemClickListener mMenuItemClickListener = new Toolbar.OnMenuItemClickListener() {
        @Override
        public boolean onMenuItemClick(MenuItem item) {
            switch (item.getItemId()) {
                case R.id.menu_household:
                    presenter.refresh("household");
                    break;
                case R.id.menu_electron:
                    presenter.refresh("electron");
                    break;
                case R.id.menu_dress:
                    presenter.refresh("dress");
                    break;
                case R.id.menu_toy:
                    presenter.refresh("toy");
                    break;
                case R.id.menu_book:
                    presenter.refresh("book");
                    break;
                case R.id.menu_gift:
                    presenter.refresh("gift");
                    break;
                case R.id.menu_other:
                    presenter.refresh("other");
                    break;
            }
            return false;
        }
    };

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == android.R.id.home) finish();
        return super.onOptionsItemSelected(item);
    }

    //自动刷新
    @Override
    protected void onStart() {
        super.onStart();
        mRefreshLayout.autoRefresh();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        mUnbinder.unbind();
    }

    //################################       视图操作实现          #######################
    @NonNull
    @Override
    public PersonGoodsPresenter createPresenter() {
        return new PersonGoodsPresenter();
    }

    @Override
    public void showRefresh() {
        mTvLoadEmpty.setVisibility(View.GONE);
        mTvLoadError.setVisibility(View.GONE);
    }

    @Override
    public void showRefreshError(String msg) {
        if (mShopAdapter.getItemCount() > 0) {
            mActivityUtils.showToast(msg);
        } else {
            mTvLoadError.setVisibility(View.VISIBLE);
        }
    }

    @Override
    public void showRefreshEnd() {
        mActivityUtils.showToast(loadMoreEnd);
        mRefreshLayout.refreshComplete();
        mTvLoadEmpty.setVisibility(View.VISIBLE);

    }

    @Override
    public void hideRefresh() {
        mRefreshLayout.refreshComplete();
    }

    @Override
    public void showLoadMore() {
        mTvLoadEmpty.setVisibility(View.GONE);
        mTvLoadError.setVisibility(View.GONE);
    }

    @Override
    public void showLoadMoreError(String msg) {
        if (mShopAdapter.getItemCount() > 0) {
            mActivityUtils.showToast(msg);
        } else {
            mTvLoadError.setVisibility(View.VISIBLE);
        }
    }

    @Override
    public void showLoadMoreEnd() {
        if (mShopAdapter.getItemCount() > 0) {
            mActivityUtils.showToast(loadMoreEnd);
            mRefreshLayout.refreshComplete();
        } else
            mTvLoadEmpty.setVisibility(View.VISIBLE);

    }

    @Override
    public void hideLodeMore() {
        mRefreshLayout.refreshComplete();
    }

    @Override
    public void addRefreshData(List<GoodsInfo> datas) {
        mShopAdapter.clear();
        mShopAdapter.addData(datas);
    }

    @Override
    public void addMoreData(List<GoodsInfo> datas) {
        mShopAdapter.addData(datas);
    }

    @Override
    public void showMsg(String msg) {
        mActivityUtils.showToast(msg);
    }
}
