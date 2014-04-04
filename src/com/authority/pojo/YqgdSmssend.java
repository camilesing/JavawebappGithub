package com.authority.pojo;

import java.io.Serializable;
import java.util.Date;

public class YqgdSmssend implements Serializable {
    private static final long serialVersionUID = 1L;

    private String id;

    private String msgId;

    private String password;

    private String srcTeleNum;

    private String destTeleNum;

    private Object msg;

    private Date addtime;

    private String addwho;

    private String addip;

    private String isdisplay;

    private String fromtablename;

    private String fromrowid;

    private String state;

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

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getSrcTeleNum() {
        return srcTeleNum;
    }

    public void setSrcTeleNum(String srcTeleNum) {
        this.srcTeleNum = srcTeleNum;
    }

    public String getDestTeleNum() {
        return destTeleNum;
    }

    public void setDestTeleNum(String destTeleNum) {
        this.destTeleNum = destTeleNum;
    }

    public Object getMsg() {
        return msg;
    }

    public void setMsg(Object msg) {
        this.msg = msg;
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

    public String getFromtablename() {
        return fromtablename;
    }

    public void setFromtablename(String fromtablename) {
        this.fromtablename = fromtablename;
    }

    public String getFromrowid() {
        return fromrowid;
    }

    public void setFromrowid(String fromrowid) {
        this.fromrowid = fromrowid;
    }

    public String getState() {
        return state;
    }

    public void setState(String state) {
        this.state = state;
    }
}