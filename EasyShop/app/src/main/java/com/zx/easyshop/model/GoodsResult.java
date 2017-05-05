package com.zx.easyshop.model;

import java.util.List;

/**
 * Created Time: 2017/2/16 10:29.
 *
 * @author HY
 *         获取商品的结果
 */

public class GoodsResult {

    private int code;//结果码
    private String msg;//消息
    private List<GoodsInfo> datas;//商品详情

    public int getCode() {
        return code;
    }

    public String getMsg() {
        return msg;
    }

    public List<GoodsInfo> getDatas() {
        return datas;
    }
}
