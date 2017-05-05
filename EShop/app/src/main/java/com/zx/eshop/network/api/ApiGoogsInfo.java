package com.zx.eshop.network.api;

import android.support.annotation.NonNull;
import android.support.annotation.Nullable;

import com.zx.eshop.network.core.ApiInterface;
import com.zx.eshop.network.core.ApiPath;
import com.zx.eshop.network.core.RequestParams;
import com.zx.eshop.network.core.ResponseEntity;
import com.zx.eshop.network.entity.GoodsInfoReq;
import com.zx.eshop.network.entity.GoodsInfoRsp;

/**
 * <p>
 * 获取商品详情
 * </p>
 * Created Time: 2017/3/3 17:28.
 *
 * @author HY
 */
public class ApiGoogsInfo implements ApiInterface {

    private GoodsInfoReq mGoodsInfoReq;

    public ApiGoogsInfo(int goodsId) {
        mGoodsInfoReq = new GoodsInfoReq();
        mGoodsInfoReq.setGoodsId(goodsId);
    }

    @NonNull
    @Override
    public String getPath() {
        return ApiPath.GOODS_INFO;
    }

    @Nullable
    @Override
    public RequestParams getRequestParams() {
        return mGoodsInfoReq;
    }

    @NonNull
    @Override
    public Class<? extends ResponseEntity> getResponseEntity() {
        return GoodsInfoRsp.class;
    }
}
