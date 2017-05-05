package com.zx.eshop.network;

import com.zx.eshop.network.api.ApiCategory;
import com.zx.eshop.network.api.ApiGoogsInfo;
import com.zx.eshop.network.api.ApiHomeBanner;
import com.zx.eshop.network.api.ApiHomeCategory;
import com.zx.eshop.network.api.ApiSearch;
import com.zx.eshop.network.entity.CategoryRsp;
import com.zx.eshop.network.entity.Filter;
import com.zx.eshop.network.entity.GoodsInfoRsp;
import com.zx.eshop.network.entity.HomeBannerRsp;
import com.zx.eshop.network.entity.HomeCategoryRsp;
import com.zx.eshop.network.entity.Pagination;
import com.zx.eshop.network.entity.SearchRsp;

import org.junit.Test;

import static org.junit.Assert.*;

/**
 * Created Time: 2017/2/27 14:39.
 *
 * @author HY
 */
public class EShopClientTest {
    @Test
    public void getCategory() throws Exception {
        CategoryRsp categoryRsp = EShopClient.getInstance().execute(new ApiCategory());

        assertNotNull(categoryRsp);
    }

    @Test
    public void getHomeBanner() throws Exception {

        HomeBannerRsp homeBannerRsp = EShopClient.getInstance().execute(new ApiHomeBanner());
        assertNotNull(homeBannerRsp);
    }

    @Test
    public void getHomeCategory() throws Exception {
        HomeCategoryRsp homeCategoryRsp = EShopClient.getInstance().execute(new ApiHomeCategory());
        assertNotNull(homeCategoryRsp);
    }

    @Test
    public void getSearch() throws Exception {
        SearchRsp searchRsp = EShopClient.getInstance().execute(new ApiSearch(new Filter(), new Pagination()));
        assertNotNull(searchRsp);
    }

    @Test
    public void getGoodsInfo() throws Exception {
        ApiGoogsInfo apiGoogsInfo = new ApiGoogsInfo(78);

        GoodsInfoRsp entity = EShopClient.getInstance().execute(apiGoogsInfo);

        assertNotNull(entity);
    }
}