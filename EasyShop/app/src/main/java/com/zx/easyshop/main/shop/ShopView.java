package com.zx.easyshop.main.shop;

import com.hannesdorfmann.mosby.mvp.MvpView;
import com.zx.easyshop.model.GoodsInfo;

import java.util.List;

/**
 * Created Time: 2017/2/16 9:55.
 *
 * @author HY
 */

public interface ShopView extends MvpView{
    //刷新 处理中
    void showRefresh();

    //刷新 失败
    void showRefreshError(String msg);

    //刷新 结束
    void showRefreshEnd();

    //刷新 隐藏视图
    void hideRefresh();

    //加载 处理中
    void showLoadMore();

    //加载 失败
    void showLoadMoreError(String msg);

    //加载 结束
    void showLoadMoreEnd();

    //加载 隐藏视图
    void hideLodeMore();

    //添加数据 刷新
    void addRefreshData(List<GoodsInfo> datas);

    //添加数据 加载
    void addMoreData(List<GoodsInfo> datas);

    //显示消息
    void showMsg(String msg);
}
