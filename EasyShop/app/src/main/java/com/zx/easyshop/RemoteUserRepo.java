package com.zx.easyshop;

import com.feicuiedu.apphx.model.repository.IRemoteUsersRepo;
import com.google.gson.Gson;
import com.hyphenate.easeui.domain.EaseUser;
import com.zx.easyshop.commons.CurrentUser;
import com.zx.easyshop.model.GetUsersResult;
import com.zx.easyshop.model.User;
import com.zx.easyshop.network.EasyShopClient;

import java.util.List;

import okhttp3.Call;
import okhttp3.Response;

/**
 * Created Time: 2017/2/20 16:58.
 *
 * @author HY
 */

public class RemoteUserRepo implements IRemoteUsersRepo {
    @Override
    public List<EaseUser> queryByName(String username) throws Exception {
        Call call = EasyShopClient.newInstance().getSearchUser(username);
        Response response = call.execute();
        //抛出异常
        if (!response.isSuccessful()) throw new Exception(response.body().string());
        //解析
        String json = response.body().string();
        GetUsersResult result = new Gson().fromJson(json, GetUsersResult.class);

        if (null == result) throw new NullPointerException("parse error,get user result error");
        else if (result.getCode() != 1) throw new Exception(result.getMessage());
        else {
            //本地用户类转换为环信用户类
            List<User> users = result.getDatas();
            return CurrentUser.convertAll(users);
        }
    }

    @Override
    public List<EaseUser> getUsers(List<String> ids) throws Exception {
        Call call = EasyShopClient.newInstance().getUsers(ids);
        Response response = call.execute();

        if (!response.isSuccessful()) throw new Exception(response.body().string());

        String json = response.body().string();
        GetUsersResult result = new Gson().fromJson(json, GetUsersResult.class);
        if (null == result) throw new NullPointerException("parse error,get user result error");
        else if (result.getCode() != 1) throw new Exception(result.getMessage());
        else {
            //本地用户类转换为环信用户类
            List<User> users = result.getDatas();
            return CurrentUser.convertAll(users);
        }
    }
}
