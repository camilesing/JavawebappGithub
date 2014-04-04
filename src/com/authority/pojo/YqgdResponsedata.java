package com.authority.pojo;

import java.io.Serializable;
import java.util.Date;

/**
 * 银联代扣响应表
 */
public class YqgdResponsedata implements Serializable {
    private static final long serialVersionUID = 1L;

    private String id;

    /**
     * 商户号
     */
    private String merid;

    /**
     * 商户日期
     */
    private String transdate;

    /**
     * 订单号
     */
    private String orderno;

    /**
     * 交易类型
     */
    private String transtype;

    /**
     * 卡折标志
     */
    private String cardtype;

    /**
     * 卡号/折号
     */
    private String cardno;

    /**
     * 持卡人姓名
     */
    private String usrname;

    /**
     * 证件类型
     */
    private String certtype;

    /**
     * 证件号
     */
    private String certid;

    /**
     * 币种 156:人民币
     */
    private String curyid;

    /**
     * 金额
     */
    private String transamt;

    /**
     * 私有域
     */
    private String priv1;

    /**
     * 签名值
     */
    private String chkvalue;

    /**
     * 应答信息
     */
    private String responsecode;

    /**
     * 代扣状态
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
     * 是否有效  0无效，1有效
     */
    private String isdisplay;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    /**
     * @return 商户号
     */
    public String getMerid() {
        return merid;
    }

    /**
     * @param merid 
	 *            商户号
     */
    public void setMerid(String merid) {
        this.merid = merid;
    }

    /**
     * @return 商户日期
     */
    public String getTransdate() {
        return transdate;
    }

    /**
     * @param transdate 
	 *            商户日期
     */
    public void setTransdate(String transdate) {
        this.transdate = transdate;
    }

    /**
     * @return 订单号
     */
    public String getOrderno() {
        return orderno;
    }

    /**
     * @param orderno 
	 *            订单号
     */
    public void setOrderno(String orderno) {
        this.orderno = orderno;
    }

    /**
     * @return 交易类型
     */
    public String getTranstype() {
        return transtype;
    }

    /**
     * @param transtype 
	 *            交易类型
     */
    public void setTranstype(String transtype) {
        this.transtype = transtype;
    }

    /**
     * @return 卡折标志
     */
    public String getCardtype() {
        return cardtype;
    }

    /**
     * @param cardtype 
	 *            卡折标志
     */
    public void setCardtype(String cardtype) {
        this.cardtype = cardtype;
    }

    /**
     * @return 卡号/折号
     */
    public String getCardno() {
        return cardno;
    }

    /**
     * @param cardno 
	 *            卡号/折号
     */
    public void setCardno(String cardno) {
        this.cardno = cardno;
    }

    /**
     * @return 持卡人姓名
     */
    public String getUsrname() {
        return usrname;
    }

    /**
     * @param usrname 
	 *            持卡人姓名
     */
    public void setUsrname(String usrname) {
        this.usrname = usrname;
    }

    /**
     * @return 证件类型
     */
    public String getCerttype() {
        return certtype;
    }

    /**
     * @param certtype 
	 *            证件类型
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
     * @return 币种 156:人民币
     */
    public String getCuryid() {
        return curyid;
    }

    /**
     * @param curyid 
	 *            币种 156:人民币
     */
    public void setCuryid(String curyid) {
        this.curyid = curyid;
    }

    /**
     * @return 金额
     */
    public String getTransamt() {
        return transamt;
    }

    /**
     * @param transamt 
	 *            金额
     */
    public void setTransamt(String transamt) {
        this.transamt = transamt;
    }

    /**
     * @return 私有域
     */
    public String getPriv1() {
        return priv1;
    }

    /**
     * @param priv1 
	 *            私有域
     */
    public void setPriv1(String priv1) {
        this.priv1 = priv1;
    }

    /**
     * @return 签名值
     */
    public String getChkvalue() {
        return chkvalue;
    }

    /**
     * @param chkvalue 
	 *            签名值
     */
    public void setChkvalue(String chkvalue) {
        this.chkvalue = chkvalue;
    }

    /**
     * @return 应答信息
     */
    public String getResponsecode() {
        return responsecode;
    }

    /**
     * @param responsecode 
	 *            应答信息
     */
    public void setResponsecode(String responsecode) {
        this.responsecode = responsecode;
    }

    /**
     * @return 代扣状态
     */
    public String getTransstat() {
        return transstat;
    }

    /**
     * @param transstat 
	 *            代扣状态
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
     * @return 是否有效  0无效，1有效
     */
    public String getIsdisplay() {
        return isdisplay;
    }

    /**
     * @param isdisplay 
	 *            是否有效  0无效，1有效
     */
    public void setIsdisplay(String isdisplay) {
        this.isdisplay = isdisplay;
    }
}