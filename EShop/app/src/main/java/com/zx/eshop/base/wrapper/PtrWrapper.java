package com.zx.eshop.base.wrapper;

import android.app.Activity;
import android.support.v4.app.Fragment;

import com.zx.eshop.R;

import butterknife.ButterKnife;
import in.srain.cube.views.ptr.PtrClassicDefaultFooter;
import in.srain.cube.views.ptr.PtrClassicDefaultHeader;
import in.srain.cube.views.ptr.PtrDefaultHandler2;
import in.srain.cube.views.ptr.PtrFrameLayout;

/**
 * <p>
 * 1.找到刷新控件：构造方法
 * 2.设置刷新的头部局样式，加载的尾部布局
 * 3.刷新的处理，获取数据
 * 4.自动刷新，延时自动刷新
 * 5.停止刷新
 * 6.判断是否正在刷新
 * </p>
 * Created Time: 2017/2/28 16:35.
 *
 * @author HY
 *         下拉刷新和上拉加载的封装类
 */
@SuppressWarnings("ConstantConditions")
public abstract class PtrWrapper {

    private PtrFrameLayout mRefreshLayout;

    /**
     * 在activity中使用
     *
     * @param activity activity
     */
    public PtrWrapper(Activity activity, boolean isLoad) {
        mRefreshLayout = ButterKnife.findById(activity, R.id.standard_refresh_layout);
        initPtr(isLoad);
    }

    /**
     * 在Fragment中使用
     *
     * @param fragment fragment
     */
    public PtrWrapper(Fragment fragment, boolean isLoad) {
        mRefreshLayout = ButterKnife.findById(fragment.getView(), R.id.standard_refresh_layout);
        initPtr(isLoad);
    }

    /**
     * 初始化刷新加载
     *
     * @param isLoad 判断是否需要加载
     */
    private void initPtr(boolean isLoad) {
        if (null != mRefreshLayout) {
            mRefreshLayout.disableWhenHorizontalMove(true);
        }

        //设置刷新的布局
        initPtrHeader();
        //设置加载的布局
        if (isLoad)
            initPtrFooter();

        mRefreshLayout.setPtrHandler(mHandler);
    }

    //初始化头部布局
    private void initPtrHeader() {
        PtrClassicDefaultHeader header = new PtrClassicDefaultHeader(mRefreshLayout.getContext());
        mRefreshLayout.setHeaderView(header);
        mRefreshLayout.addPtrUIHandler(header);
    }

    //初始化尾部布局
    private void initPtrFooter() {
        PtrClassicDefaultFooter footer = new PtrClassicDefaultFooter(mRefreshLayout.getContext());
        mRefreshLayout.setFooterView(footer);
        mRefreshLayout.addPtrUIHandler(footer);
    }

    //刷新
    protected abstract void onRefresh();

    //加载
    protected abstract void onLoadMore();

    //自动刷新
    public void autoRefresh() {
        mRefreshLayout.autoRefresh();
    }

    //延时刷新
    public void postDelayed(long delay) {
        mRefreshLayout.postDelayed(new Runnable() {
            @Override
            public void run() {
                autoRefresh();
            }
        }, delay);
    }

    //判断是否正在刷新
    public boolean isRefreshing() {
        return mRefreshLayout.isRefreshing();
    }

    //停止刷新
    public void stopRefresh() {
        if (isRefreshing())
            mRefreshLayout.refreshComplete();
    }

    private PtrDefaultHandler2 mHandler = new PtrDefaultHandler2() {
        @Override
        public void onLoadMoreBegin(PtrFrameLayout frame) {
            onLoadMore();
        }

        @Override
        public void onRefreshBegin(PtrFrameLayout frame) {
            onRefresh();
        }
    };

}
