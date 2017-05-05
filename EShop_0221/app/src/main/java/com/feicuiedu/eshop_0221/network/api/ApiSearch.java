package com.feicuiedu.eshop_0221.network.api;

import android.support.annotation.NonNull;
import android.support.annotation.Nullable;

import com.feicuiedu.eshop_0221.network.core.ApiInterface;
import com.feicuiedu.eshop_0221.network.core.ApiPath;
import com.feicuiedu.eshop_0221.network.core.RequestParam;
import com.feicuiedu.eshop_0221.network.core.ResponseEntity;
import com.feicuiedu.eshop_0221.network.entity.Filter;
import com.feicuiedu.eshop_0221.network.entity.Pagination;
import com.feicuiedu.eshop_0221.network.entity.SearchReq;
import com.feicuiedu.eshop_0221.network.entity.SearchRsp;

/**
 * Created by gqq on 2017/3/3.
 */
// 服务器接口：搜索商品，POST请求，有请求体
public class ApiSearch implements ApiInterface{

    private SearchReq mSearchReq;

    // 根据传入的参数构建请求体数据
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
    public RequestParam getRequestParam() {
        return mSearchReq;
    }

    @NonNull
    @Override
    public Class<? extends ResponseEntity> getResponseEntity() {
        return SearchRsp.class;
    }
}
