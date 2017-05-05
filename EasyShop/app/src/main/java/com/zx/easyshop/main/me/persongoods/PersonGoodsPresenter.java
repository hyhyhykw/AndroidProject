package com.zx.easyshop.main.me.persongoods;

import com.google.gson.Gson;
import com.hannesdorfmann.mosby.mvp.MvpNullObjectBasePresenter;
import com.zx.easyshop.main.shop.ShopView;
import com.zx.easyshop.model.GoodsInfo;
import com.zx.easyshop.model.GoodsResult;
import com.zx.easyshop.network.EasyShopClient;
import com.zx.easyshop.network.UICallback;

import java.io.IOException;
import java.util.List;

import okhttp3.Call;

/**
 * Created Time: 2017/2/17 10:01.
 *
 * @author HY
 */

public class PersonGoodsPresenter extends MvpNullObjectBasePresenter<ShopView> {

    private Call mCall;
    private int pageNo = 1;

    @Override
    public void detachView(boolean retainInstance) {
        super.detachView(retainInstance);
        if (null != mCall) mCall.cancel();
    }

    /**
     * 刷新个人商品
     *
     * @param type 商品类型
     */
    public void refresh(String type) {
        getView().showRefresh();
        mCall = EasyShopClient.newInstance().getPersonGoods(1, type);
        mCall.enqueue(new UICallback() {
            @Override
            public void onFailureInUi(Call call, IOException e) {
                getView().showRefreshError(e.getMessage());
            }

            @Override
            public void onResponseInUi(Call call, String json) {
                GoodsResult result = new Gson().fromJson(json, GoodsResult.class);
                if (null == result) getView().showMsg("未知错误");
                else {
                    switch (result.getCode()) {
                        case 1:
                            List<GoodsInfo> datas = result.getDatas();
                            if (null == datas || datas.isEmpty()) {
                                getView().showRefreshEnd();
                            } else {
                                getView().addRefreshData(datas);
                                getView().hideRefresh();
                            }
                            pageNo = 2;
                            break;
                        default:
                            getView().showRefreshError(result.getMsg());
                            break;
                    }
                }
            }
        });
    }

    /**
     * 加载个人商品
     *
     * @param type 商品类型
     */
    public void load(String type) {
        getView().showLoadMore();
        mCall = EasyShopClient.newInstance().getPersonGoods(pageNo, type);
        mCall.enqueue(new UICallback() {
            @Override
            public void onFailureInUi(Call call, IOException e) {
                getView().showLoadMoreError(e.getMessage());
            }

            @Override
            public void onResponseInUi(Call call, String json) {
                GoodsResult result = new Gson().fromJson(json, GoodsResult.class);
                if (null == result) getView().showMsg("未知错误");
                else {
                    switch (result.getCode()) {
                        case 1:
                            List<GoodsInfo> datas = result.getDatas();
                            if (null == datas || datas.isEmpty()) {
                                getView().showLoadMoreEnd();
                            } else {
                                getView().addMoreData(datas);
                                getView().hideLodeMore();
                            }
                            pageNo++;
                            break;
                        default:
                            getView().showLoadMoreError(result.getMsg());
                            break;
                    }
                }
            }
        });
    }
}
