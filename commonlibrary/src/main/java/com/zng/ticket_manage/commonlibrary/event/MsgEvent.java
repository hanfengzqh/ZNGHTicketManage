package com.zng.ticket_manage.commonlibrary.event;

import java.io.Serializable;

/**
 * Created by zqh on 2017/11/29.
 */

public class MsgEvent implements Serializable {
    private int msgType;
    private String msgContent;

    public MsgEvent(int msgType, String msgContent) {
        this.msgType = msgType;
        this.msgContent = msgContent;
    }

    public int getMsgType() {
        return msgType;
    }

    public void setMsgType(int msgType) {
        this.msgType = msgType;
    }

    public String getMsgContent() {
        return msgContent;
    }

    public void setMsgContent(String msgContent) {
        this.msgContent = msgContent;
    }
}
