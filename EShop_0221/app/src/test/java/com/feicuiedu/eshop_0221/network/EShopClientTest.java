package com.feicuiedu.eshop_0221.network;

import android.test.AndroidTestCase;

import com.feicuiedu.eshop_0221.network.api.ApiCategory;
import com.feicuiedu.eshop_0221.network.api.ApiGoodsInfo;
import com.feicuiedu.eshop_0221.network.api.ApiHomeBanner;
import com.feicuiedu.eshop_0221.network.api.ApiHomeCategory;
import com.feicuiedu.eshop_0221.network.api.ApiSearch;
import com.feicuiedu.eshop_0221.network.core.ApiInterface;
import com.feicuiedu.eshop_0221.network.core.ResponseEntity;
import com.feicuiedu.eshop_0221.network.entity.CategoryRsp;
import com.feicuiedu.eshop_0221.network.entity.GoodsInfoRsp;
import com.feicuiedu.eshop_0221.network.entity.HomeBannerRsp;
import com.feicuiedu.eshop_0221.network.entity.HomeCategoryRsp;
import com.feicuiedu.eshop_0221.network.entity.SearchReq;
import com.feicuiedu.eshop_0221.network.entity.SearchRsp;
import com.google.gson.Gson;

import org.junit.Test;

import okhttp3.Call;
import okhttp3.Response;

import static org.junit.Assert.*;

/**
 * Created by gqq on 2017/2/23.
 * 单元测试：测试接口
 */
public class EShopClientTest {

    // 分类页面
    @Test
    public void getCategory() throws Exception {

        CategoryRsp categoryRsp = EShopClient.getInstance().execute(new ApiCategory());
        // 断言方法：为我们做一个判断
        assertTrue(categoryRsp.getStatus().isSucceed());
    }

    // 首页：banner
    @Test
    public void getHomeBanner() throws Exception{
        HomeBannerRsp bannerRsp = EShopClient.getInstance().execute(new ApiHomeBanner());
        assertTrue(bannerRsp.getStatus().isSucceed());
    }

    // 首页：分类和推荐商品
    @Test
    public void getHomeCategory() throws Exception{
        HomeCategoryRsp categoryRsp = EShopClient.getInstance().execute(new ApiHomeCategory());
        assertTrue(categoryRsp.getStatus().isSucceed());
    }

    // 搜索页面
    @Test
    public void getSearch() throws Exception{
        ApiSearch apiSearch = new ApiSearch(null,null);
        SearchRsp searchRsp = EShopClient.getInstance().execute(apiSearch);
        assertTrue(searchRsp.getStatus().isSucceed());
    }

    // 商品详情的请求
    @Test
    public void getGoodsInfo() throws Exception{
        ApiGoodsInfo apiGoodsInfo = new ApiGoodsInfo(78);
        GoodsInfoRsp goodsInfoRsp = EShopClient.getInstance().execute(apiGoodsInfo);
        assertTrue(goodsInfoRsp.getStatus().isSucceed());
    }
}