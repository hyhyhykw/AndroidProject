package com.zx.easyshop.user.login;

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

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import java.io.IOException;

import okhttp3.Call;

/**
 * Created Time: 2017/2/14 13:31.
 *
 * @author HY
 */

public class LoginPresenter extends MvpNullObjectBasePresenter<LoginView> {

    //环信相关
    private Call mCall;
    private String hxPassword;

    public void login(String username, final String password) {
        getView().showPrb();
        mCall = EasyShopClient.newInstance().login(username, password);

        mCall.enqueue(new UICallback() {
            @Override
            public void onFailureInUi(Call call, IOException e) {
                getView().showMsg("网络连接错误");
                getView().hidePrb();
            }

            @Override
            public void onResponseInUi(Call call, String json) {
                hxPassword=password;
                UserResult result = new Gson().fromJson(json, UserResult.class);
                if (result.getCode() == 1) {

                    User user = result.getData();
                    // 保存登陆配置
                    CachePreferences.setUser(user);
                    //环信登陆
                    EaseUser easeUser= CurrentUser.convert(user);
                    HxUserManager.getInstance().asyncLogin(easeUser,hxPassword);

                } else if (result.getCode() == 2) {
                    getView().showMsg(result.getMsg());
                    getView().loginFailed();
                } else {
                    getView().showMsg("未知错误");
                }
                getView().hidePrb();
            }
        });

    }

    @Override
    public void detachView(boolean retainInstance) {
        super.detachView(retainInstance);
        if (null != mCall) mCall.cancel();
    }

    @Subscribe(threadMode = ThreadMode.MAIN)
    public void onEvent(HxSimpleEvent event){
        //判断是否为登录事件
        if (event.type== HxEventType.LOGIN){
            hxPassword=null;
            //调用登陆成功的方法
            getView().showMsg("登录成功");
            getView().loginSuccess();
            EventBus.getDefault().post(new UserResult());
        }
    }

    @Subscribe(threadMode = ThreadMode.MAIN)
    public  void onEvent(HxErrorEvent event){
        if (event.type==HxEventType.LOGIN){
            hxPassword=null;
            getView().hidePrb();
            getView().showMsg(event.toString());
        }
    }

}
