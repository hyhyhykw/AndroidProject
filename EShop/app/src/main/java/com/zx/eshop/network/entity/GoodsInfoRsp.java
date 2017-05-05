package com.zx.eshop.network.entity;

import com.google.gson.annotations.SerializedName;
import com.zx.eshop.network.core.ResponseEntity;

/**
 * Created by gqq on 17/2/19.
 * 商品详情的响应体
 */
public class GoodsInfoRsp extends ResponseEntity {

    @SerializedName("data")
    private GoodsInfo mData;

    public GoodsInfo getData() {
        return mData;
    }

}
