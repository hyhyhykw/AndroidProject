package com.hy.patrol_leader.model;

/**
 * Created time : 2017/4/28 14:30.
 *
 * @author HY
 */

public class MessageInfo {
    private int status;//状态 是否已读
    private String title;//标题
    private String explain;//消息说明
    private String charge;//负责人
    private String stamp;//时间
    private String msg;//详细消息 消息的详细内容

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getExplain() {
        return explain;
    }

    public void setExplain(String explain) {
        this.explain = explain;
    }

    public String getStamp() {
        return stamp;
    }

    public void setStamp(String stamp) {
        this.stamp = stamp;
    }

    public String getCharge() {
        return charge;
    }

    public void setCharge(String charge) {
        this.charge = charge;
    }
}
