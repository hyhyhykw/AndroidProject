package com.feicuiedu.okhttpdemo_0223.entity;

import com.google.gson.annotations.SerializedName;

// 商品详情请求体
public class GoodsInfoReq{

    @SerializedName("goods_id")
    private int mGoodsId;

    public int getGoodsId() {
        return mGoodsId;
    }

    public void setGoodsId(int goodsId) {
        mGoodsId = goodsId;
    }
}