package com.zx.easyshop.user.register;


import com.feicuiedu.apphx.model.HxUserManager;
import com.feicuiedu.apphx.model.event.HxErrorEvent;
import com.feicuiedu.apphx.model.event.HxEventType;
import com.feicuiedu.apphx.model.event.HxSimpleEvent;
import com.google.gson.Gson;
import com.hannesdorfmann.mosby.mvp.MvpNullObjectBasePresenter;
import com.hyphenate.easeui.domain.EaseUser;
import com.zx.easyshop.commons.CurrentUser;
import com.zx.easyshop.model.CachePreferences;
import com.zx.easyshop.network.UICallback;
import com.zx.easyshop.model.User;
import com.zx.easyshop.model.UserResult;
import com.zx.easyshop.network.EasyShopClient;

import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import java.io.IOException;

import okhttp3.Call;

/**
 * Created Time: 2017/2/14 11:50.
 *
 * @author HY
 */

@SuppressWarnings("WeakerAccess")
public class RegisterPresenter extends MvpNullObjectBasePresenter<RegisterView> {

    private Call mCall;
    private String hxPassword;

    @Override
    public void attachView(RegisterView view) {
        super.attachView(view);
        //初始化代码
    }

    @Override
    public void detachView(boolean retainInstance) {
        super.detachView(retainInstance);
        //最后触发的方法
        if (null != mCall) mCall.cancel();
    }

    public void register(String username, String password) {
        hxPassword = password;
        getView().showPrb();
        mCall = EasyShopClient.newInstance().register(username, password);
        mCall.enqueue(new UICallback() {
            @Override
            public void onFailureInUi(Call call, IOException e) {
                getView().showMsg("网络连接失败");
                getView().hidePrb();
            }

            @Override
            public void onResponseInUi(Call call, String json) {
                UserResult result = new Gson().fromJson(json, UserResult.class);
                if (result.getCode() == 1) {
                    //用户实体类
                    User user = result.getData();
                    //保存配置
                    CachePreferences.setUser(user);
                    //登陆环信相关，会通过EventBus返回值
                    EaseUser easeUser = CurrentUser.convert(user);
                    HxUserManager.getInstance().asyncLogin(easeUser, hxPassword);
                } else if (result.getCode() == 2) {
                    getView().showMsg(result.getMsg());
                    getView().registerFailed();
                } else {
                    getView().showMsg("未知错误");
                }
                getView().hidePrb();
            }
        });
    }

    @Subscribe(threadMode = ThreadMode.MAIN)
    public void onEvent(HxSimpleEvent event) {
        if (event.type == HxEventType.LOGIN) {
            hxPassword = null;
            getView().showMsg("注册成功");
            getView().registerSuccess();
        }
    }

    @Subscribe(threadMode = ThreadMode.MAIN)
    public void onEvent(HxErrorEvent event) {
        if (event.type == HxEventType.LOGIN) {
            hxPassword = null;
            getView().hidePrb();
            getView().showMsg(event.toString());
        }
    }
}
