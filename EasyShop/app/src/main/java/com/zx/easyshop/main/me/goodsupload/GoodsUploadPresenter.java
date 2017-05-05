package com.zx.easyshop.main.me.goodsupload;

import com.google.gson.Gson;
import com.hannesdorfmann.mosby.mvp.MvpNullObjectBasePresenter;
import com.zx.easyshop.commons.MyFileUtils;
import com.zx.easyshop.model.GoodsUpload;
import com.zx.easyshop.model.GoodsUploadResult;
import com.zx.easyshop.model.ImageItem;
import com.zx.easyshop.network.EasyShopClient;
import com.zx.easyshop.network.UICallback;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import okhttp3.Call;

/**
 * Created Time: 2017/2/17 14:22.
 *
 * @author HY
 */

public class GoodsUploadPresenter extends MvpNullObjectBasePresenter<GoodsUploadView> {

    private Call mCall;

    @Override
    public void detachView(boolean retainInstance) {
        super.detachView(retainInstance);
        if (null != mCall) mCall.cancel();
    }

    /**
     * 上传商品
     *
     * @param goodsUpload 需要商品的商品信息
     * @param images      商品图片集合
     */
    public void upload(GoodsUpload goodsUpload, List<ImageItem> images) {
        getView().showPrb();
        mCall = EasyShopClient.newInstance().upload(goodsUpload, getFiles(images));
        mCall.enqueue(new UICallback() {
            @Override
            public void onFailureInUi(Call call, IOException e) {
                getView().showMsg(e.getMessage());
                getView().hidePrb();
            }

            @Override
            public void onResponseInUi(Call call, String json) {
                GoodsUploadResult result = new Gson().fromJson(json, GoodsUploadResult.class);
                if (null == result) getView().showMsg("未知错误");
                else {
                    switch (result.getCode()) {
                        case 1:
                            getView().uploadSuccess();
                            break;
                        default:
                            getView().showMsg(result.getMsg());
                            break;
                    }
                }
            }
        });
    }

    /**
     * 根据图片路径获取文件对象
     *
     * @param images 代表图片的实体类
     * @return 文件集合
     */
    private List<File> getFiles(List<ImageItem> images) {
        List<File> files = new ArrayList<>();
        for (ImageItem imageItem : images) {
            File file = new File(MyFileUtils.SD_PATH, imageItem.getImagePath());
            files.add(file);
        }
        return files;
    }
}
