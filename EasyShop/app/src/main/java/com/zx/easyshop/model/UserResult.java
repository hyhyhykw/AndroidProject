package com.zx.easyshop.model;


/**
 * Created Time: 2017/2/10 14:55.
 *
 * @author HY
 */

public class UserResult {

    /**
     * code : 1
     * msg : succeed
     * data :{...}
     */

    private int code;//结果码
    private String msg;//消息
    private User data; //环信相关


    public int getCode() {
        return code;
    }

    public String getMsg() {
        return msg;
    }

    public User getData() {
        return data;
    }


}
