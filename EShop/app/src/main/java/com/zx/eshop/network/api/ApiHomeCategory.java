package com.zx.eshop.network.api;

import android.support.annotation.NonNull;
import android.support.annotation.Nullable;

import com.zx.eshop.network.core.ApiInterface;
import com.zx.eshop.network.core.ApiPath;
import com.zx.eshop.network.core.RequestParams;
import com.zx.eshop.network.core.ResponseEntity;
import com.zx.eshop.network.entity.HomeCategoryRsp;

/**
 * <p>
 * 主页推荐商品的API
 * </p>
 * Created Time: 2017/3/3 15:15.
 *
 * @author HY
 */

public class ApiHomeCategory implements ApiInterface {
    @NonNull
    @Override
    public String getPath() {
        return ApiPath.HOME_CATEGORY;
    }

    @Nullable
    @Override
    public RequestParams getRequestParams() {
        return null;
    }

    @NonNull
    @Override
    public Class<? extends ResponseEntity> getResponseEntity() {
        return HomeCategoryRsp.class;
    }
}
