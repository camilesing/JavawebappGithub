package com.authority.pojo;

import java.io.Serializable;
import java.util.Date;

/**
 * 温州市交警-短信发送记录表
 */
public class WzsjjSmssend implements Serializable {
    private static final long serialVersionUID = 1L;

    private String id;

    /**
     * 消息ID
     */
    private String msgId;

    /**
     * 消息验证码 手机号码末四位*3+579
     */
    private String password;

    /**
     * 服务代码如 106573999999
     */
    private String srcTeleNum;

    /**
     * 用户手机号码
     */
    private String destTeleNum;

    /**
     * 消息内容
     */
    private Object msg;

    private Date addtime;

    private String addwho;

    private String addip;

    /**
     * 是否有效
     */
    private String isdisplay;

    private String fromtablename;

    private String fromrowid;

    /**
     * 发送状态
     */
    private String state;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    /**
     * @return 消息ID
     */
    public String getMsgId() {
        return msgId;
    }

    /**
     * @param msgId 
	 *            消息ID
     */
    public void setMsgId(String msgId) {
        this.msgId = msgId;
    }

    /**
     * @return 消息验证码 手机号码末四位*3+579
     */
    public String getPassword() {
        return password;
    }

    /**
     * @param password 
	 *            消息验证码 手机号码末四位*3+579
     */
    public void setPassword(String password) {
        this.password = password;
    }

    /**
     * @return 服务代码如 106573999999
     */
    public String getSrcTeleNum() {
        return srcTeleNum;
    }

    /**
     * @param srcTeleNum 
	 *            服务代码如 106573999999
     */
    public void setSrcTeleNum(String srcTeleNum) {
        this.srcTeleNum = srcTeleNum;
    }

    /**
     * @return 用户手机号码
     */
    public String getDestTeleNum() {
        return destTeleNum;
    }

    /**
     * @param destTeleNum 
	 *            用户手机号码
     */
    public void setDestTeleNum(String destTeleNum) {
        this.destTeleNum = destTeleNum;
    }

    /**
     * @return 消息内容
     */
    public Object getMsg() {
        return msg;
    }

    /**
     * @param msg 
	 *            消息内容
     */
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

    /**
     * @return 发送状态
     */
    public String getState() {
        return state;
    }

    /**
     * @param state 
	 *            发送状态
     */
    public void setState(String state) {
        this.state = state;
    }
}