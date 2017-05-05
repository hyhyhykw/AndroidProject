package com.zx.easyshop.model;

/**
 * Created Time: 2017/2/16 15:24.
 *
 * @author HY
 */

public class GoodDetailResult {
    private int code;
    private String msg;
    private GoodDetail datas;//商品详情
    private User user;//发布者详情

    public int getCode() {
        return code;
    }

    public void setCode(int code) {
        this.code = code;
    }

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }

    public GoodDetail getDatas() {
        return datas;
    }

    public void setDatas(GoodDetail datas) {
        this.datas = datas;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }
}
