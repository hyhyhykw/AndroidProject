package com.zx.eshop.network.api;

import android.support.annotation.NonNull;
import android.support.annotation.Nullable;

import com.zx.eshop.network.core.ApiInterface;
import com.zx.eshop.network.core.ApiPath;
import com.zx.eshop.network.core.RequestParams;
import com.zx.eshop.network.core.ResponseEntity;
import com.zx.eshop.network.entity.Filter;
import com.zx.eshop.network.entity.Pagination;
import com.zx.eshop.network.entity.SearchReq;
import com.zx.eshop.network.entity.SearchRsp;

/**
 * <p>
 * 商品搜索的API
 * </p>
 * Created Time: 2017/3/3 15:17.
 *
 * @author HY
 */
public class ApiSearch implements ApiInterface {
    private SearchReq mSearchReq;

    public ApiSearch(Filter filter, Pagination pagination) {
        mSearchReq = new SearchReq();
        mSearchReq.setFilter(filter);
        mSearchReq.setPagination(pagination);
    }

    @NonNull
    @Override
    public String getPath() {
        return ApiPath.SEARCH;
    }

    @Nullable
    @Override
    public RequestParams getRequestParams() {
        return mSearchReq;
    }

    @NonNull
    @Override
    public Class<? extends ResponseEntity> getResponseEntity() {
        return SearchRsp.class;
    }
}
