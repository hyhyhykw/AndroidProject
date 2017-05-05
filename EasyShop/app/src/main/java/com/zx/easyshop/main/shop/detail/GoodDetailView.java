package com.zx.easyshop.main.shop.detail;

import com.hannesdorfmann.mosby.mvp.MvpView;
import com.zx.easyshop.model.GoodDetail;
import com.zx.easyshop.model.User;

import java.util.ArrayList;

/**
 * Created Time: 2017/2/16 15:13.
 *
 * @author HY
 */

public interface GoodDetailView extends MvpView {
    //显示加载动画
    void showPrb();

    //隐藏加载动画
    void hidePrb();

    //设置图片路径
    void setImageData(ArrayList<String> paths);

    //设置商品信息
    void setData(GoodDetail detail, User goodUser);

    //商品不存在了
    void showError();

    //提示信息
    void showMsg(String msg);

    //删除商品
    void deleteEnd();
}
