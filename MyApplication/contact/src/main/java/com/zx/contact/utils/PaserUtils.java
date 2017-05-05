package com.zx.contact.utils;

import com.zx.contact.entity.User;

/**
 * Created Time: 2017/1/24 21:13.
 *
 * @author HY
 */

public class PaserUtils {

    public static User str2User(String qrStr) {
        User user = new User();
        try {
            String[] userStr = qrStr.split(",", 4);
            user.setName(userStr[0]);
            user.setPhone(userStr[1]);
            user.setEmail(userStr[2]);
            user.setLable(userStr[3]);
            return user;
        } catch (Exception e) {
            return null;
        }

    }
}
