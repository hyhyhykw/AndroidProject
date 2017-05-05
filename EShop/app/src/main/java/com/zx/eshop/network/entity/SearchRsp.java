package com.zx.eshop.network.entity;


import com.google.gson.annotations.SerializedName;
import com.zx.eshop.network.core.ResponseEntity;

import java.util.List;

/**
 * 搜索的响应结果
 */
public class SearchRsp extends ResponseEntity {


    @SerializedName("data")
    private List<SimpleGoods> mData;

    @SerializedName("paginated")
    private Paginated mPaginated;

    public List<SimpleGoods> getData() {
        return mData;
    }

    public Paginated getPaginated() {
        return mPaginated;
    }
}
