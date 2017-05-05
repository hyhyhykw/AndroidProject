package com.zx.easyshop.main.me.personinfo;

import com.feicuiedu.apphx.model.HxMessageManager;
import com.feicuiedu.apphx.model.HxUserManager;
import com.google.gson.Gson;
import com.hannesdorfmann.mosby.mvp.MvpNullObjectBasePresenter;
import com.zx.easyshop.model.CachePreferences;
import com.zx.easyshop.model.UserResult;
import com.zx.easyshop.network.EasyShopApi;
import com.zx.easyshop.network.EasyShopClient;
import com.zx.easyshop.network.UICallback;

import java.io.File;
import java.io.IOException;

import okhttp3.Call;

/**
 * Created Time: 2017/2/14 16:08.
 *
 * @author HY
 */

public class PersonPresenter extends MvpNullObjectBasePresenter<PersonView> {

    private Call mCall;

    @Override
    public void detachView(boolean retainInstance) {
        super.detachView(retainInstance);
        if (null != mCall) mCall.cancel();
    }

    public void updateAvater(File file) {
        getView().showPrb();
        mCall = EasyShopClient.newInstance().unloadImage(file);
        mCall.enqueue(new UICallback() {
            @Override
            public void onFailureInUi(Call call, IOException e) {
                getView().hidePrb();
                getView().showMsg("网络连接失败");
            }

            @Override
            public void onResponseInUi(Call call, String json) {
                UserResult result = new Gson().fromJson(json, UserResult.class);
                if (null == result) getView().showMsg("未知错误");
                else
                    switch (result.getCode()) {
                        case 1:
                            getView().updateAvater(result.getData().getHeadIcon());
                            CachePreferences.setUser(result.getData());
                            getView().showMsg("上传成功");

                            // 环信更新头像
                            HxUserManager.getInstance().updateAvatar(EasyShopApi.IMAGE_URL + result.getData().getHeadIcon());
                            HxMessageManager.getInstance().sendAvatarUpdateMessage(EasyShopApi.IMAGE_URL + result.getData().getHeadIcon());

                            break;
                        case 2:
                            getView().showMsg(result.getMsg());
                            break;
                    }
                getView().hidePrb();
            }
        });
    }
}
