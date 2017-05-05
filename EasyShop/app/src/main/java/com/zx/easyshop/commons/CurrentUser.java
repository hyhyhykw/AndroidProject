package com.zx.easyshop.commons;//package com.fuicuiedu.xc.easyshop_test_20170206.commons;


import com.hyphenate.easeui.domain.EaseUser;
import com.hyphenate.easeui.utils.EaseCommonUtils;
import com.zx.easyshop.model.User;
import com.zx.easyshop.network.EasyShopApi;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 * 将User转换为EaseUser(环信相关)
 */
public class CurrentUser {

    private CurrentUser() {
    }

    private static List<String> list;

    public static void setList(List<String> list) {
        CurrentUser.list = list;
    }

    public static List<String> getList() {
        return list;
    }

    public static List<EaseUser> convertAll(List<User> users) {
        if (users == null) {
            //返回一个空的集合对象
            return Collections.emptyList();
        }
        ArrayList<EaseUser> easeUsers = new ArrayList<>();
        for (User user : users) {
            easeUsers.add(convert(user));
        }
        return easeUsers;
    }

    public static EaseUser convert(User user) {
        //从user取需要的信息保存到EaseUser中，完成转换
        EaseUser easeUser = new EaseUser(user.getHxId());
        easeUser.setNick(user.getNickname());
        easeUser.setAvatar(EasyShopApi.IMAGE_URL + user.getHeadIcon());
        EaseCommonUtils.setUserInitialLetter(easeUser);
        return easeUser;
    }
}
