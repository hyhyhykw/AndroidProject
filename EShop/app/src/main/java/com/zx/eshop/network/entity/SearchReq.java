package com.zx.eshop.network.entity;


import com.google.gson.annotations.SerializedName;
import com.zx.eshop.network.core.RequestParams;

public class SearchReq extends RequestParams {

    @SerializedName("filter") private Filter mFilter;

    @SerializedName("pagination") private Pagination mPagination;

    public void setFilter(Filter filter) {
        mFilter = filter;
    }

    public void setPagination(Pagination pagination) {
        mPagination = pagination;
    }
}
