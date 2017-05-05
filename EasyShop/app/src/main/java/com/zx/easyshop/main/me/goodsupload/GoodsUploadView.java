package com.zx.easyshop.main.me.goodsupload;

import com.hannesdorfmann.mosby.mvp.MvpView;

/**
 * Created Time: 2017/2/17 14:21.
 *
 * @author HY
 */

public interface GoodsUploadView extends MvpView {

    //显示加载动画
    void showPrb();

    //隐藏加载动画
    void hidePrb();

    //上传成功
    void uploadSuccess();

    //显示消息
    void showMsg(String msg);
}
