package com.zx.easyshop.user.register;

import com.hannesdorfmann.mosby.mvp.MvpView;

/**
 * Created Time: 2017/2/14 11:50.
 *
 * @author HY
 */

public interface RegisterView extends MvpView{
    //显示加载动画
    void showPrb();

    //隐藏加载动画
    void hidePrb();

    //注册成功
    void registerSuccess();

    //注册失败
    void registerFailed();

    //Toast
    void showMsg(String msg);

    void showUserPwdError(String msg);
}
