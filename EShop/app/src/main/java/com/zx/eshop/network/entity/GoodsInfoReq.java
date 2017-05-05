package com.zx.eshop.network.entity;

import com.google.gson.annotations.SerializedName;
import com.zx.eshop.network.core.RequestParams;

// 商品详情请求体
public class GoodsInfoReq extends RequestParams{

    @SerializedName("goods_id")
    private int mGoodsId;

    public int getGoodsId() {
        return mGoodsId;
    }

    public void setGoodsId(int goodsId) {
        mGoodsId = goodsId;
    }
}