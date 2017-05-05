package com.zx.eshop.network.core;

import android.support.annotation.NonNull;
import android.support.annotation.Nullable;

/**
 * <p>
 * api的抽象化接口，请求参数相响应的数据做一个统一的管理
 * </p>
 * Created Time: 2017/3/3 14:38.
 *
 * @author HY
 */
public interface ApiInterface {
    //分类的接口、搜索商品的接口、首页banner、首页分类和推荐
    //接口路径
    @NonNull
    String getPath();

    //请求的参数
    @Nullable
    RequestParams getRequestParams();

    //响应的数据类型
    @NonNull
    Class<? extends ResponseEntity> getResponseEntity();

}
