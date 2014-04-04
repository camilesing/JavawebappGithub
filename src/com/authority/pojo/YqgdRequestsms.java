package com.authority.pojo;

import java.io.Serializable;
import java.util.Date;

/**
 * 短信请求一栏表
 */
public class YqgdRequestsms implements Serializable {
    private static final long serialVersionUID = 1L;

    private String id;

    /**
     * 联系电话
     */
    private String phone;

    /**
     * 短信内容
     */
    private String message;

    /**
     * 处理状态
     */
    private String chulzt;

    private String addwho;

    private Date addtime;

    private String addip;

    private String editwho;

    private Date edittime;

    private String editip;

    /**
     * 是否有效
     */
    private String isdisplay;

    /**
     * 短信发送唯一值
     */
    private String msgId;

    /**
     * 短信回复值
     */
    private String response;

    /**
     * 批次号
     */
    private String batchnumber;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    /**
     * @return 联系电话
     */
    public String getPhone() {
        return phone;
    }

    /**
     * @param phone 
	 *            联系电话
     */
    public void setPhone(String phone) {
        this.phone = phone;
    }

    /**
     * @return 短信内容
     */
    public String getMessage() {
        return message;
    }

    /**
     * @param message 
	 *            短信内容
     */
    public void setMessage(String message) {
        this.message = message;
    }

    /**
     * @return 处理状态
     */
    public String getChulzt() {
        return chulzt;
    }

    /**
     * @param chulzt 
	 *            处理状态
     */
    public void setChulzt(String chulzt) {
        this.chulzt = chulzt;
    }

    public String getAddwho() {
        return addwho;
    }

    public void setAddwho(String addwho) {
        this.addwho = addwho;
    }

    public Date getAddtime() {
        return addtime;
    }

    public void setAddtime(Date addtime) {
        this.addtime = addtime;
    }

    public String getAddip() {
        return addip;
    }

    public void setAddip(String addip) {
        this.addip = addip;
    }

    public String getEditwho() {
        return editwho;
    }

    public void setEditwho(String editwho) {
        this.editwho = editwho;
    }

    public Date getEdittime() {
        return edittime;
    }

    public void setEdittime(Date edittime) {
        this.edittime = edittime;
    }

    public String getEditip() {
        return editip;
    }

    public void setEditip(String editip) {
        this.editip = editip;
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

    /**
     * @return 短信发送唯一值
     */
    public String getMsgId() {
        return msgId;
    }

    /**
     * @param msgId 
	 *            短信发送唯一值
     */
    public void setMsgId(String msgId) {
        this.msgId = msgId;
    }

    /**
     * @return 短信回复值
     */
    public String getResponse() {
        return response;
    }

    /**
     * @param response 
	 *            短信回复值
     */
    public void setResponse(String response) {
        this.response = response;
    }

    /**
     * @return 批次号
     */
    public String getBatchnumber() {
        return batchnumber;
    }

    /**
     * @param batchnumber 
	 *            批次号
     */
    public void setBatchnumber(String batchnumber) {
        this.batchnumber = batchnumber;
    }
}