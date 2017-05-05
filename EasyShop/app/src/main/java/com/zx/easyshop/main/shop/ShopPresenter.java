package com.zx.easyshop.main.shop;

import com.google.gson.Gson;
import com.hannesdorfmann.mosby.mvp.MvpNullObjectBasePresenter;
import com.zx.easyshop.model.GoodsInfo;
import com.zx.easyshop.model.GoodsResult;
import com.zx.easyshop.network.EasyShopClient;
import com.zx.easyshop.network.UICallback;

import java.io.IOException;
import java.util.List;

import okhttp3.Call;

/**
 * Created Time: 2017/2/16 10:17.
 *
 * @author HY
 */

public class ShopPresenter extends MvpNullObjectBasePresenter<ShopView> {
    private Call mCall;
    private int pageInt = 1;

    @Override
    public void detachView(boolean retainInstance) {
        super.detachView(retainInstance);
        if (null != mCall) mCall.cancel();
    }

    /**
     * 刷新数据
     *
     * @param type 商品类型
     */
    public void refresh(String type) {
        getView().showRefresh();
        //刷新数据 永远是最新数据，永远请求第一页
        mCall = EasyShopClient.newInstance().getGoods(1, type);
        mCall.enqueue(new UICallback() {
            @Override
            public void onFailureInUi(Call call, IOException e) {
                getView().showRefreshError("网络连接失败");
                getView().hideRefresh();
            }

            @Override
            public void onResponseInUi(Call call, String json) {
                GoodsResult result = new Gson().fromJson(json, GoodsResult.class);
                if (null == result) getView().showMsg("未知错误");
                else {
                    switch (result.getCode()) {
                        case 1://请求成功
                            //还没有商品
                            List<GoodsInfo> datas = result.getDatas();
                            if (datas.isEmpty()) {
                                getView().showRefreshEnd();
                            } else {
                                getView().addRefreshData(datas);
                                getView().hideRefresh();
                            }
                            //分页改为2，之后要加载更多数据了
                            pageInt = 2;
                            break;
                        default:
                            getView().showLoadMoreError(result.getMsg());
                            break;
                    }
                }
                getView().hideRefresh();
            }
        });
    }

    /**
     * 加载数据
     *
     * @param type 商品类型
     */
    public void load(String type) {
        getView().showLoadMore();
        mCall = EasyShopClient.newInstance().getGoods(pageInt, type);
        mCall.enqueue(new UICallback() {
            @Override
            public void onFailureInUi(Call call, IOException e) {
                getView().showLoadMoreError("网络连接失败");
                getView().hideLodeMore();
            }

            @Override
            public void onResponseInUi(Call call, String json) {
                GoodsResult result = new Gson().fromJson(json, GoodsResult.class);
                if (null == result) getView().showMsg("未知错误");
                else {
                    switch (result.getCode()) {
                        case 1:
                            List<GoodsInfo> datas = result.getDatas();
                            if (datas.isEmpty()) {
                                getView().showLoadMoreEnd();
                            } else {
                                getView().addMoreData(datas);
                            }
                            //分页加载，下一次加载下一页
                            pageInt++;
                            break;
                        default:
                            break;
                    }
                }
                getView().hideLodeMore();
            }
        });
    }
}
