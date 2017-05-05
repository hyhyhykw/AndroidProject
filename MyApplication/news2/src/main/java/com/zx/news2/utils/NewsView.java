package com.zx.news2.utils;


import com.zx.news2.entity.NewsDetail;

/**
 * Created by HY on 2016/12/23.
 * this class declaration the method of ui contorller
 *
 * @author HY
 */
public interface NewsView {
    /**
     * show loading dialog
     */
    void showDialog();

    /**
     * cancel loading dialog
     */
    void cancelDialog();

    /**
     * according state to decision
     *
     * @param state login state
     */
    void toUserPage(int state);

    /**
     * show regist fragment
     */
    void showRegist();

    /**
     * show forget fragment
     */
    void showForget();

    /**
     * show item by left menu position
     *
     * @param position left menu item position
     */
    void showView(int position);

    /**
     * skip to next page take url path
     *
     * @param newsDetail news detail
     */
    void skip(NewsDetail newsDetail);

    /**
     * to download activity
     *
     * @param link download url
     */
    void download(String link);
}
