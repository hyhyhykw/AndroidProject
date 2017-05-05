package com.zx.news2.entity;

import java.io.Serializable;

/**
 * Created by HY on 2016/12/22.
 * news detail info
 *
 * @author HY
 */
public class NewsDetail implements Serializable {
    /*news summary*/
    public String summary;
    /*news icon link*/
    public String icon;
    /*news time*/
    public String stamp;
    /*news title*/
    public String title;
    /*news link*/
    public String link;
    /*news id*/
    public String nid;
    /*news type*/
    public int type;

    /**
     * get rid of last two char
     *
     * @param stamp original stamp
     */
    public void setStamp(String stamp) {
        this.stamp = stamp.substring(0, stamp.length() - 2);
    }

}
