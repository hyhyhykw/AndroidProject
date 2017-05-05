package com.zx.anew;

/**
 * Created time : 2017/3/8 19:35.
 *
 * @author HY
 */

public class MessageInfo {

    private String msg;
    private MsgType mMsgType = MsgType.MSG_A;


    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }

    public MsgType getMsgType() {
        return mMsgType;
    }

    public void setMsgType(MsgType msgType) {
        mMsgType = msgType;
    }
}
