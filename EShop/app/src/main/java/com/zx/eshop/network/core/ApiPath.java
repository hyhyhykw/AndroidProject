package com.zx.eshop.network.core;

/**
 * Created Time: 2017/3/1 17:15.
 *
 * @author HY
 *         网络请求接口路径
 */
public interface ApiPath {

    //分类请求接口
    String CATEGORY = "/category";

    //主页轮播图
    String HOME_DATA = "/home/data";

    //主页分类
    String HOME_CATEGORY = "/home" + CATEGORY;

    //搜索
    String SEARCH = "/search";

    String GOODS_INFO = "/goods";
}
