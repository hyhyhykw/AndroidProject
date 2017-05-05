package com.zx.news2.entity;

/**
 * Created by HY on 2016/12/26.
 * left menu content
 *
 * @author HY
 */
public class LeftMenu {
    /*lset menu item icon*/
    public int icon;
    /*lset menu item title chinese*/
    public String titleCN;
    /*lset menu item title english*/
    public String titleEN;
    /*lset menu item is or not checked*/
    public boolean isChecked;

    public LeftMenu(int icon, String titleCN, String titleEN, boolean isChecked) {
        this.icon = icon;
        this.titleCN = titleCN;
        this.titleEN = titleEN;
        this.isChecked = isChecked;
    }
}
