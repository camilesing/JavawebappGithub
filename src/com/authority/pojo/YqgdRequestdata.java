package com.authority.pojo;

import java.io.Serializable;
import java.util.Date;

import org.codehaus.jackson.map.annotate.JsonSerialize;

import com.authority.common.jackjson.CustomDateTimeSerializer;

/**
 * 广电原始数据请求
 */
public class YqgdRequestdata implements Serializable {
    private static final long serialVersionUID = 1L;

    private String id;

    /**
     * 开户银行
     */
    private String openbank;

    /**
     * 0:卡,1:折
     */
    private String cardtype;

    /**
     * 卡号
     */
    private String cardno;

    /**
     * 持卡人
     */
    private String usrname;

    /**
     * 身份证01 , 军官证02 , 护照03 , 户口簿04 , 回乡证05 , 其他06
     */
    private String certtype;

    /**
     * 证件号
     */
    private String certid;

    /**
     * 交易金额
     */
    private String transamt;

    /**
     * 用途
     */
    private String purpose;

    /**
     * 处理状态 1 初始化, 21 待返回(未缴费) 22 已返回(银联端未缴费) 2 已缴费
     */
    private String chulzt;

    /**
     * 应答信息 00 成功
     */
    private String responsecode;

    /**
     * 交易状态
     */
    private String transstat;

    /**
     * 应答信息
     */
    private String message;

    private Date addtime;

    private String addwho;

    private String addip;

    private Date edittime;

    private String editwho;

    private String editip;

    /**
     * 是否有效 1有效 0无效
     */
    private String isdisplay;

    /**
     * 移动电话
     */
    private String mobilephone;

    /**
     * 批次
     */
    private String batchnumber;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    /**
     * @return 开户银行
     */
    public String getOpenbank() {
        return openbank;
    }

    /**
     * @param openbank 
	 *            开户银行
     */
    public void setOpenbank(String openbank) {
        this.openbank = openbank;
    }

    /**
     * @return 0:卡,1:折
     */
    public String getCardtype() {
        return cardtype;
    }

    /**
     * @param cardtype 
	 *            0:卡,1:折
     */
    public void setCardtype(String cardtype) {
        this.cardtype = cardtype;
    }

    /**
     * @return 卡号
     */
    public String getCardno() {
        return cardno;
    }

    /**
     * @param cardno 
	 *            卡号
     */
    public void setCardno(String cardno) {
        this.cardno = cardno;
    }

    /**
     * @return 持卡人
     */
    public String getUsrname() {
        return usrname;
    }

    /**
     * @param usrname 
	 *            持卡人
     */
    public void setUsrname(String usrname) {
        this.usrname = usrname;
    }

    /**
     * @return 身份证01 , 军官证02 , 护照03 , 户口簿04 , 回乡证05 , 其他06
     */
    public String getCerttype() {
        return certtype;
    }

    /**
     * @param certtype 
	 *            身份证01 , 军官证02 , 护照03 , 户口簿04 , 回乡证05 , 其他06
     */
    public void setCerttype(String certtype) {
        this.certtype = certtype;
    }

    /**
     * @return 证件号
     */
    public String getCertid() {
        return certid;
    }

    /**
     * @param certid 
	 *            证件号
     */
    public void setCertid(String certid) {
        this.certid = certid;
    }

    /**
     * @return 交易金额
     */
    public String getTransamt() {
        return transamt;
    }

    /**
     * @param transamt 
	 *            交易金额
     */
    public void setTransamt(String transamt) {
        this.transamt = transamt;
    }

    /**
     * @return 用途
     */
    public String getPurpose() {
        return purpose;
    }

    /**
     * @param purpose 
	 *            用途
     */
    public void setPurpose(String purpose) {
        this.purpose = purpose;
    }

    /**
     * @return 处理状态 1 初始化, 21 待返回(未缴费) 22 已返回(银联端未缴费) 2 已缴费
     */
    public String getChulzt() {
        return chulzt;
    }

    /**
     * @param chulzt 
	 *            处理状态 1 初始化, 21 待返回(未缴费) 22 已返回(银联端未缴费) 2 已缴费
     */
    public void setChulzt(String chulzt) {
        this.chulzt = chulzt;
    }

    /**
     * @return 应答信息 00 成功
     */
    public String getResponsecode() {
        return responsecode;
    }

    /**
     * @param responsecode 
	 *            应答信息 00 成功
     */
    public void setResponsecode(String responsecode) {
        this.responsecode = responsecode;
    }

    /**
     * @return 交易状态
     */
    public String getTransstat() {
        return transstat;
    }

    /**
     * @param transstat 
	 *            交易状态
     */
    public void setTransstat(String transstat) {
        this.transstat = transstat;
    }

    /**
     * @return 应答信息
     */
    public String getMessage() {
        return message;
    }

    /**
     * @param message 
	 *            应答信息
     */
    public void setMessage(String message) {
        this.message = message;
    }

    @JsonSerialize(using = CustomDateTimeSerializer.class)
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

    @JsonSerialize(using = CustomDateTimeSerializer.class)
    public Date getEdittime() {
        return edittime;
    }

    public void setEdittime(Date edittime) {
        this.edittime = edittime;
    }

    public String getEditwho() {
        return editwho;
    }

    public void setEditwho(String editwho) {
        this.editwho = editwho;
    }

    public String getEditip() {
        return editip;
    }

    public void setEditip(String editip) {
        this.editip = editip;
    }

    /**
     * @return 是否有效 1有效 0无效
     */
    public String getIsdisplay() {
        return isdisplay;
    }

    /**
     * @param isdisplay 
	 *            是否有效 1有效 0无效
     */
    public void setIsdisplay(String isdisplay) {
        this.isdisplay = isdisplay;
    }

    /**
     * @return 移动电话
     */
    public String getMobilephone() {
        return mobilephone;
    }

    /**
     * @param mobilephone 
	 *            移动电话
     */
    public void setMobilephone(String mobilephone) {
        this.mobilephone = mobilephone;
    }

    /**
     * @return 批次
     */
    public String getBatchnumber() {
        return batchnumber;
    }

    /**
     * @param batchnumber 
	 *            批次
     */
    public void setBatchnumber(String batchnumber) {
        this.batchnumber = batchnumber;
    }
}