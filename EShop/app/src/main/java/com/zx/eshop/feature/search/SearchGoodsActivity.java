package com.zx.eshop.feature.search;

import android.content.Context;
import android.content.Intent;
import android.support.annotation.Nullable;
import android.view.View;
import android.widget.ListView;
import android.widget.TextView;

import com.google.gson.Gson;
import com.zx.eshop.R;
import com.zx.eshop.base.BaseActivity;
import com.zx.eshop.base.widgets.SimpleSearchView;
import com.zx.eshop.base.widgets.SimpleSearchView.OnSearchLisenter;
import com.zx.eshop.base.wrapper.PtrWrapper;
import com.zx.eshop.base.wrapper.ToastWrapper;
import com.zx.eshop.base.wrapper.ToolbarWrapper;
import com.zx.eshop.feature.goods.GoodsActivity;
import com.zx.eshop.network.api.ApiSearch;
import com.zx.eshop.network.core.ApiPath;
import com.zx.eshop.network.core.ResponseEntity;
import com.zx.eshop.network.entity.Filter;
import com.zx.eshop.network.entity.Paginated;
import com.zx.eshop.network.entity.Pagination;
import com.zx.eshop.network.entity.SearchRsp;
import com.zx.eshop.network.entity.SimpleGoods;

import java.util.List;

import butterknife.BindView;
import butterknife.BindViews;
import butterknife.OnClick;
import butterknife.OnItemClick;
import okhttp3.Call;

public class SearchGoodsActivity extends BaseActivity {

    private static final String EXTRA_SEARCH_FILTER = "EXTRA_SEARCH_FILTER";

    @BindView(R.id.search_view)
    protected SimpleSearchView mSearchView;//搜索视图

    @BindViews({R.id.text_is_hot, R.id.text_most_expensive, R.id.text_cheapest})
    protected List<TextView> mTvOrders;//商品排序

    @BindView(R.id.list_goods)
    protected ListView mListGoods;//商品列表
    private PtrWrapper mPtrWrapper;
    private Filter mFilter;
    private SearchGoodsAdapter mAdapter;
    private Pagination mPagination = new Pagination();//分页参数
    private Call mSearchCall;
    private Paginated mPaginated;
    private long mRefreshTime;


    @Override
    protected void initView() {
        //初始化Toolbar
        new ToolbarWrapper(this)
                .setShowBack(true)
                .setShowTitle(false);
        //刚进来将第一项设置为选中状态
        mTvOrders.get(0).setActivated(true);
        String filterJson = getIntent().getStringExtra(EXTRA_SEARCH_FILTER);
        mFilter = new Gson().fromJson(filterJson, Filter.class);

        mAdapter = new SearchGoodsAdapter();
        mListGoods.setAdapter(mAdapter);
        //初始化下拉刷新控件
        mPtrWrapper = new PtrWrapper(this, true) {
            @Override
            protected void onRefresh() {
                //刷新
                searchGoods(true);
            }

            @Override
            protected void onLoadMore() {
                //加载
                if (mPaginated.hasMore()) {
                    searchGoods(false);
                } else {
                    mPtrWrapper.stopRefresh();
                    ToastWrapper.show(R.string.msg_load_more_complete);
                }
            }
        };

        //搜索按钮点击
        mSearchView.setOnSearchLisenter(new OnSearchLisenter() {
            @Override
            public void onSearch(String text) {
                mFilter.setKeywords(text);
                mPtrWrapper.autoRefresh();
            }
        });

        mPtrWrapper.postDelayed(50);
    }

    //搜索商品，加载数据
    private void searchGoods(boolean isRefresh) {
        if (null != mSearchCall) {
            mSearchCall.cancel();
        }
        if (isRefresh) {
            //上一次刷新的时间
            mRefreshTime = System.currentTimeMillis();
            mPagination.reset();
            mListGoods.setSelection(0);
        } else {
            mPagination.next();
        }
        //搜索请求
        enqueue(new ApiSearch(mFilter, mPagination));
    }


    @Override
    protected int getContentViewLayout() {
        return R.layout.activity_search_goods;
    }

    @Override
    protected void onBusinessResponse(String path, boolean isSuccess, ResponseEntity responseEntity) {
        mPtrWrapper.stopRefresh();
        mSearchCall = null;
        if (!ApiPath.SEARCH.equals(path))
            throw new UnsupportedOperationException(path);

        if (isSuccess) {
            SearchRsp searchRsp = (SearchRsp) responseEntity;
            //将分页结果拿出来，判断下次是否需要进行加载
            mPaginated = searchRsp.getPaginated();
            //为适配器设置数据
            List<SimpleGoods> datas = searchRsp.getData();
            if (mPagination.isFirst()) {
                //刷新
                mAdapter.reset(datas);
            } else {
                //加载
                mAdapter.addAll(datas);
            }
        }
    }

    //获取实例
    public static Intent getStartIntent(Context context, @Nullable Filter filter) {
        if (filter == null)
            filter = new Filter();
        Intent intent = new Intent(context, SearchGoodsActivity.class);
        intent.putExtra(EXTRA_SEARCH_FILTER, new Gson().toJson(filter));
        return intent;
    }

    //list点击事件
    @OnItemClick(R.id.list_goods)
    public void onItemClick(int position) {
        Intent intent = GoodsActivity.getStartIntent(this, mAdapter.getItem(position).getId());
        startActivity(intent);
    }

    //排序方式
    @OnClick({R.id.text_is_hot, R.id.text_most_expensive, R.id.text_cheapest})
    public void onClick(View view) {
        if (view.isActivated()) return;
        if (mPtrWrapper.isRefreshing()) return;
        for (TextView textView : mTvOrders) {
            textView.setActivated(false);
        }
        view.setActivated(true);
        String sortBy;
        switch (view.getId()) {
            case R.id.text_is_hot:
                sortBy = Filter.SORT_IS_HOT;
                break;
            case R.id.text_most_expensive:
                sortBy = Filter.SORT_PRICE_DESC;
                break;
            case R.id.text_cheapest:
                sortBy = Filter.SORT_PRICE_ASC;
                break;
            default:
                throw new UnsupportedOperationException("unsupport");
        }
        mFilter.setSortBy(sortBy);

        long time = 1500 + mRefreshTime - System.currentTimeMillis();
        time = time > 0 ? time : 0;

        mPtrWrapper.postDelayed(time);
    }
}
