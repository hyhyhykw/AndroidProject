package com.zx.eshop.network.api;

import android.support.annotation.NonNull;
import android.support.annotation.Nullable;

import com.zx.eshop.network.core.ApiInterface;
import com.zx.eshop.network.core.ApiPath;
import com.zx.eshop.network.core.RequestParams;
import com.zx.eshop.network.core.ResponseEntity;
import com.zx.eshop.network.entity.CategoryRsp;

/**
 * <p>
 * 代表服务器接口，获取商品的分类信息
 * </p>
 * Created Time: 2017/3/3 14:43.
 *
 * @author HY
 */
public class ApiCategory implements ApiInterface {
    @NonNull
    @Override
    public String getPath() {
        return ApiPath.CATEGORY;
    }

    @Nullable
    @Override
    public RequestParams getRequestParams() {
        return null;
    }

    @NonNull
    @Override
    public Class<? extends ResponseEntity> getResponseEntity() {
        return CategoryRsp.class;
    }
}
