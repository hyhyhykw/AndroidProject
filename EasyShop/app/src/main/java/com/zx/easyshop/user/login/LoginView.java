package com.zx.easyshop.user.login;

import com.hannesdorfmann.mosby.mvp.MvpView;

/**
 * Created Time: 2017/2/14 13:27.
 *
 * @author HY
 */

public interface LoginView extends MvpView {
    void showPrb();

    void hidePrb();

    void showMsg(String msg);

    void loginSuccess();

    void loginFailed();

}
