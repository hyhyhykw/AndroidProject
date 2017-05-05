package com.zx.easyshop.main.shop.detail;

import com.google.gson.Gson;
import com.hannesdorfmann.mosby.mvp.MvpNullObjectBasePresenter;
import com.zx.easyshop.model.GoodDetail;
import com.zx.easyshop.model.GoodDetailResult;
import com.zx.easyshop.model.UserResult;
import com.zx.easyshop.network.EasyShopClient;
import com.zx.easyshop.network.UICallback;

import java.io.IOException;
import java.util.ArrayList;

import okhttp3.Call;

/**
 * Created Time: 2017/2/16 15:17.
 *
 * @author HY
 */

public class GoodDetailPresenter extends MvpNullObjectBasePresenter<GoodDetailView> {

    private Call mCall;

    @Override
    public void detachView(boolean retainInstance) {
        super.detachView(retainInstance);
        if (null != mCall) mCall.cancel();
    }

    /**
     * 获取商品详情
     *
     * @param uuid 商品表主键
     */
    public void getData(String uuid) {
        getView().showPrb();
        mCall = EasyShopClient.newInstance().getGoodDetail(uuid);
        mCall.enqueue(new UICallback() {
            @Override
            public void onFailureInUi(Call call, IOException e) {
                getView().showMsg("网络连接失败");
                getView().hidePrb();
            }

            @Override
            public void onResponseInUi(Call call, String json) {
                GoodDetailResult result = new Gson().fromJson(json, GoodDetailResult.class);
                if (null == result) getView().showMsg("未知错误");
                else {
                    switch (result.getCode()) {
                        case 1://成功
                            //商品详情
                            GoodDetail detail = result.getDatas();
                            //图片路径
                            ArrayList<String> paths = new ArrayList<>();
                            for (int i = 0; i < detail.getPages().size(); i++) {
                                paths.add(detail.getPages().get(i).getUri());
                            }
                            getView().setImageData(paths);
                            //设置商品信息
                            getView().setData(detail, result.getUser());
                            break;
                        default:
                            getView().showError();
                            break;
                    }
                }
                getView().hidePrb();
            }
        });
    }

    //删除商品
    public void delete(String uuid) {
        getView().showPrb();
        mCall = EasyShopClient.newInstance().delete(uuid);
        mCall.enqueue(new UICallback() {
            @Override
            public void onFailureInUi(Call call, IOException e) {
                getView().showMsg("网络连接失败");
                getView().hidePrb();
            }

            @Override
            public void onResponseInUi(Call call, String json) {
                UserResult result = new Gson().fromJson(json, UserResult.class);
                if (null == result) getView().showMsg("未知错误");
                else {
                    switch (result.getCode()) {
                        case 1:
                            //删除成功
                            getView().deleteEnd();
                            break;
                        default:
                            getView().showMsg(result.getMsg());
                            break;
                    }
                }
                getView().hidePrb();
            }
        });
    }
}
