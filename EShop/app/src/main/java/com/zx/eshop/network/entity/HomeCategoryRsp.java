package com.zx.eshop.network.entity;

import com.google.gson.annotations.SerializedName;
import com.zx.eshop.network.core.ResponseEntity;

import java.util.List;

/**
 * 首页商品分类接口响应体.
 */
public class HomeCategoryRsp extends ResponseEntity {

    @SerializedName("data")
    private List<CategoryHome> mData;

    public List<CategoryHome> getData() {
        return mData;
    }

}
