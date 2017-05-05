package com.zx.eshop.network.api;

import android.support.annotation.NonNull;
import android.support.annotation.Nullable;

import com.zx.eshop.network.core.ApiInterface;
import com.zx.eshop.network.core.ApiPath;
import com.zx.eshop.network.core.RequestParams;
import com.zx.eshop.network.core.ResponseEntity;
import com.zx.eshop.network.entity.HomeBannerRsp;

/**
 * <p>
 * 首页轮播图的API
 * </p>
 * Created Time: 2017/3/3 15:12.
 *
 * @author HY
 */
public class ApiHomeBanner implements ApiInterface {
    @NonNull
    @Override
    public String getPath() {
        return ApiPath.HOME_DATA;
    }

    @Nullable
    @Override
    public RequestParams getRequestParams() {
        return null;
    }

    @NonNull
    @Override
    public Class<? extends ResponseEntity> getResponseEntity() {
        return HomeBannerRsp.class;
    }
}
