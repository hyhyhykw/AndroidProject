package com.zx.easyshop.main.me.personinfo;

import com.hannesdorfmann.mosby.mvp.MvpView;

/**
 * Created Time: 2017/2/14 16:07.
 *
 * @author HY
 */

public interface PersonView extends MvpView {

    void showPrb();

    void hidePrb();

    void showMsg(String msg);

    void updateAvater(String url);
}
