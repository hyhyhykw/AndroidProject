package com.feicuiedu.eshop_0221.network.api;

import android.support.annotation.NonNull;
import android.support.annotation.Nullable;

import com.feicuiedu.eshop_0221.network.core.ApiInterface;
import com.feicuiedu.eshop_0221.network.core.ApiPath;
import com.feicuiedu.eshop_0221.network.core.RequestParam;
import com.feicuiedu.eshop_0221.network.core.ResponseEntity;
import com.feicuiedu.eshop_0221.network.entity.HomeCategoryRsp;

/**
 * Created by gqq on 2017/3/3.
 */
// 服务器接口：首页的分类和推荐商品
public class ApiHomeCategory implements ApiInterface {

    @NonNull
    @Override
    public String getPath() {
        return ApiPath.HOME_CATEGORY;
    }

    @Nullable
    @Override
    public RequestParam getRequestParam() {
        return null;
    }

    @NonNull
    @Override
    public Class<? extends ResponseEntity> getResponseEntity() {
        return HomeCategoryRsp.class;
    }
}
