package com.authority.pojo;

import java.io.Serializable;
import java.util.Date;

/**
 * 温州市交警-短信回复记录表
 */
public class WzsjjSmsreturn implements Serializable {
    private static final long serialVersionUID = 1L;

    private String id;

    /**
     * 消息编号
     */
    private String msgId;

    /**
     * 接入号即服务代码 如106573999999
     */
    private String srcmsgr;

    /**
     * 用户手机号码
     */
    private String destmsgr;

    /**
     * 消息内容
     */
    private String msg;

    /**
     * 收到短信时间
     */
    private String time;

    private Date addtime;

    private String addwho;

    private String addip;

    /**
     * 是否有效
     */
    private String isdisplay;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    /**
     * @return 消息编号
     */
    public String getMsgId() {
        return msgId;
    }

    /**
     * @param msgId 
	 *            消息编号
     */
    public void setMsgId(String msgId) {
        this.msgId = msgId;
    }

    /**
     * @return 接入号即服务代码 如106573999999
     */
    public String getSrcmsgr() {
        return srcmsgr;
    }

    /**
     * @param srcmsgr 
	 *            接入号即服务代码 如106573999999
     */
    public void setSrcmsgr(String srcmsgr) {
        this.srcmsgr = srcmsgr;
    }

    /**
     * @return 用户手机号码
     */
    public String getDestmsgr() {
        return destmsgr;
    }

    /**
     * @param destmsgr 
	 *            用户手机号码
     */
    public void setDestmsgr(String destmsgr) {
        this.destmsgr = destmsgr;
    }

    /**
     * @return 消息内容
     */
    public String getMsg() {
        return msg;
    }

    /**
     * @param msg 
	 *            消息内容
     */
    public void setMsg(String msg) {
        this.msg = msg;
    }

    /**
     * @return 收到短信时间
     */
    public String getTime() {
        return time;
    }

    /**
     * @param time 
	 *            收到短信时间
     */
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

    /**
     * @return 是否有效
     */
    public String getIsdisplay() {
        return isdisplay;
    }

    /**
     * @param isdisplay 
	 *            是否有效
     */
    public void setIsdisplay(String isdisplay) {
        this.isdisplay = isdisplay;
    }
}