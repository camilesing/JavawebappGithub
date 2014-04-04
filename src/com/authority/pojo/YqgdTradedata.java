package com.authority.pojo;

import java.io.Serializable;
import java.util.Date;

/**
 * 请求数据封装表
 */
public class YqgdTradedata implements Serializable {
    private static final long serialVersionUID = 1L;

    private String id;

    /**
     * 商户唯一标识
     */
    private String merid;

    /**
     * 标志该笔交易在商户端发生的日期
     */
    private String transdate;

    /**
     * 订单号，同商户日期一起唯一标识一笔交易
     */
    private String orderno;

    /**
     * 0003 代扣
     */
    private String transtype;

    /**
     * 如‘中国工商银行’代号：0102
     */
    private String openbankid;

    /**
     * 表示随后记录为卡号或存折号(0:卡,1:折)
     */
    private String cardtype;

    /**
     * 银行卡号或者存折号码
     */
    private String cardno;

    /**
     * 持卡人姓名
     */
    private String usrname;

    /**
     * 身份证01 , 军官证02 , 护照03 , 户口簿04 , 回乡证05 , 其他06
     */
    private String certtype;

    /**
     * 证件号码
     */
    private String certid;

    /**
     * 156:人民币
     */
    private String curyid;

    /**
     * 整数，以分为单位
     */
    private String transamt;

    /**
     * 用途, 商户专用
     */
    private String purpose;

    /**
     * 私有域
     */
    private String priv1;

    /**
     * 版本号
     */
    private String version;

    /**
     * 网关号
     */
    private String gateid;

    /**
     * 签名值
     */
    private String chkvalue;

    /**
     * 应答信息 00表示成功，其它代表代扣失败
     */
    private String responsecode;

    /**
     * 代扣状态 成功：1001，失败：非1001
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
     * 是否有效 0 无效 1有效
     */
    private String isdisplay;

    /**
     * 请求表ID
     */
    private String requestdataid;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    /**
     * @return 商户唯一标识
     */
    public String getMerid() {
        return merid;
    }

    /**
     * @param merid 
	 *            商户唯一标识
     */
    public void setMerid(String merid) {
        this.merid = merid;
    }

    /**
     * @return 标志该笔交易在商户端发生的日期
     */
    public String getTransdate() {
        return transdate;
    }

    /**
     * @param transdate 
	 *            标志该笔交易在商户端发生的日期
     */
    public void setTransdate(String transdate) {
        this.transdate = transdate;
    }

    /**
     * @return 订单号，同商户日期一起唯一标识一笔交易
     */
    public String getOrderno() {
        return orderno;
    }

    /**
     * @param orderno 
	 *            订单号，同商户日期一起唯一标识一笔交易
     */
    public void setOrderno(String orderno) {
        this.orderno = orderno;
    }

    /**
     * @return 0003 代扣
     */
    public String getTranstype() {
        return transtype;
    }

    /**
     * @param transtype 
	 *            0003 代扣
     */
    public void setTranstype(String transtype) {
        this.transtype = transtype;
    }

    /**
     * @return 如‘中国工商银行’代号：0102
     */
    public String getOpenbankid() {
        return openbankid;
    }

    /**
     * @param openbankid 
	 *            如‘中国工商银行’代号：0102
     */
    public void setOpenbankid(String openbankid) {
        this.openbankid = openbankid;
    }

    /**
     * @return 表示随后记录为卡号或存折号(0:卡,1:折)
     */
    public String getCardtype() {
        return cardtype;
    }

    /**
     * @param cardtype 
	 *            表示随后记录为卡号或存折号(0:卡,1:折)
     */
    public void setCardtype(String cardtype) {
        this.cardtype = cardtype;
    }

    /**
     * @return 银行卡号或者存折号码
     */
    public String getCardno() {
        return cardno;
    }

    /**
     * @param cardno 
	 *            银行卡号或者存折号码
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
     * @return 证件号码
     */
    public String getCertid() {
        return certid;
    }

    /**
     * @param certid 
	 *            证件号码
     */
    public void setCertid(String certid) {
        this.certid = certid;
    }

    /**
     * @return 156:人民币
     */
    public String getCuryid() {
        return curyid;
    }

    /**
     * @param curyid 
	 *            156:人民币
     */
    public void setCuryid(String curyid) {
        this.curyid = curyid;
    }

    /**
     * @return 整数，以分为单位
     */
    public String getTransamt() {
        return transamt;
    }

    /**
     * @param transamt 
	 *            整数，以分为单位
     */
    public void setTransamt(String transamt) {
        this.transamt = transamt;
    }

    /**
     * @return 用途, 商户专用
     */
    public String getPurpose() {
        return purpose;
    }

    /**
     * @param purpose 
	 *            用途, 商户专用
     */
    public void setPurpose(String purpose) {
        this.purpose = purpose;
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
     * @return 版本号
     */
    public String getVersion() {
        return version;
    }

    /**
     * @param version 
	 *            版本号
     */
    public void setVersion(String version) {
        this.version = version;
    }

    /**
     * @return 网关号
     */
    public String getGateid() {
        return gateid;
    }

    /**
     * @param gateid 
	 *            网关号
     */
    public void setGateid(String gateid) {
        this.gateid = gateid;
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
     * @return 应答信息 00表示成功，其它代表代扣失败
     */
    public String getResponsecode() {
        return responsecode;
    }

    /**
     * @param responsecode 
	 *            应答信息 00表示成功，其它代表代扣失败
     */
    public void setResponsecode(String responsecode) {
        this.responsecode = responsecode;
    }

    /**
     * @return 代扣状态 成功：1001，失败：非1001
     */
    public String getTransstat() {
        return transstat;
    }

    /**
     * @param transstat 
	 *            代扣状态 成功：1001，失败：非1001
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
     * @return 是否有效 0 无效 1有效
     */
    public String getIsdisplay() {
        return isdisplay;
    }

    /**
     * @param isdisplay 
	 *            是否有效 0 无效 1有效
     */
    public void setIsdisplay(String isdisplay) {
        this.isdisplay = isdisplay;
    }

    /**
     * @return 请求表ID
     */
    public String getRequestdataid() {
        return requestdataid;
    }

    /**
     * @param requestdataid 
	 *            请求表ID
     */
    public void setRequestdataid(String requestdataid) {
        this.requestdataid = requestdataid;
    }
}