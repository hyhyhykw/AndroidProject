package com.zx.eshop.network.entity;

import com.google.gson.annotations.SerializedName;
import com.zx.eshop.network.core.ResponseEntity;

import java.util.List;

/**
 * Created by gqq on 2017/2/10.
 * 暂时使用的商品分类的响应体
 */
public class CategoryRsp extends ResponseEntity{

    @SerializedName("data")
    private List<CategoryPrimary> mData;

    public List<CategoryPrimary> getData() {
        return mData;
    }
}
