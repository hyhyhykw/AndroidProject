package com.feicuiedu.eshop_0221.network.entity;

import com.feicuiedu.eshop_0221.network.core.RequestParam;
import com.google.gson.annotations.SerializedName;

// 商品详情请求体
public class GoodsInfoReq extends RequestParam {

    @SerializedName("goods_id")
    private int mGoodsId;

    public int getGoodsId() {
        return mGoodsId;
    }

    public void setGoodsId(int goodsId) {
        mGoodsId = goodsId;
    }
}
