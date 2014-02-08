package com.authority.pojo;

import java.io.Serializable;
import java.util.Date;

public class WzgdSmsreturn implements Serializable {
    private static final long serialVersionUID = 1L;

    private String id;

    private String msgId;

    private String srcmsgr;

    private String destmsgr;

    private String msg;

    private String time;

    private Date addtime;

    private String addwho;

    private String addip;

    private String isdisplay;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getMsgId() {
        return msgId;
    }

    public void setMsgId(String msgId) {
        this.msgId = msgId;
    }

    public String getSrcmsgr() {
        return srcmsgr;
    }

    public void setSrcmsgr(String srcmsgr) {
        this.srcmsgr = srcmsgr;
    }

    public String getDestmsgr() {
        return destmsgr;
    }

    public void setDestmsgr(String destmsgr) {
        this.destmsgr = destmsgr;
    }

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }

    public Date getAddtime() {
        return addtime;
    }

    public void setAddtime(Date addtime) {
        this.addtime = addtime;
    }

    public String getAddwho() {
        return addwho;
    }

    public void setAddwho(String addwho) {
        this.addwho = addwho;
    }

    public String getAddip() {
        return addip;
    }

    public void setAddip(String addip) {
        this.addip = addip;
    }

    public String getIsdisplay() {
        return isdisplay;
    }

    public void setIsdisplay(String isdisplay) {
        this.isdisplay = isdisplay;
    }
}