package com.zx.news2.entity;

import java.util.List;

/**
 * Created by HY on 2016/12/28.
 * user detail
 *
 * @author HY
 */
public class UserDetail<T> {
    /*user id*/
    public String uid;
    /*user head icon*/
    public String portrait;
    /*user summary point*/
    public int integration;
    /*number of comment*/
    public int comnum;
    /*login log*/
    public List<T> loginlog;


}
