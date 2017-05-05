package com.zx.utils;

import android.Manifest.permission;

/**
 * Created Time: 2017/3/1 19:24.
 *
 * @author HY
 */

public interface Permissions {

    //通讯录相关
    /* 写入通讯录 */
    String WRITE_CONTACTS = permission.WRITE_CONTACTS;
    /* 读取手机账户 */
    String GET_ACCOUNTS = permission.GET_ACCOUNTS;
    /* 读取通讯录 */
    String READ_CONTACTS = permission.READ_CONTACTS;


    //日历相关
    String READ_CALENDAR = permission.READ_CALENDAR;
    String WRITE_CALENDAR = permission.WRITE_CALENDAR;

    //相机
    String CAMERA = permission.CAMERA;

    //定位
    String LOCATION = permission.ACCESS_COARSE_LOCATION;
}
