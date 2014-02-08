package com.authority.pojo;

import java.io.Serializable;
import java.util.Date;

/**
 * 温州广电-客户基本信息
 */
public class WzgdKehjbxx implements Serializable {
    private static final long serialVersionUID = 1L;

    private String id;

    /**
     * 用户姓名
     */
    private String yonghxm;

    /**
     * 用户编码
     */
    private String yonghbm;

    /**
     * 证件号
     */
    private String zhengjh;

    /**
     * 固定电话
     */
    private String guddh;

    /**
     * 移动电话
     */
    private String yiddh;

    /**
     * 受理日期
     */
    private Date shoulrq;

    /**
     * 详细地址
     */
    private String xiangxdd;

    private Date addtime;

    private String addwho;

    private String addip;

    private Date edittime;

    private String editwho;

    private String editip;

    private String isdisplay;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    /**
     * @return 用户姓名
     */
    public String getYonghxm() {
        return yonghxm;
    }

    /**
     * @param yonghxm 
	 *            用户姓名
     */
    public void setYonghxm(String yonghxm) {
        this.yonghxm = yonghxm;
    }

    /**
     * @return 用户编码
     */
    public String getYonghbm() {
        return yonghbm;
    }

    /**
     * @param yonghbm 
	 *            用户编码
     */
    public void setYonghbm(String yonghbm) {
        this.yonghbm = yonghbm;
    }

    /**
     * @return 证件号
     */
    public String getZhengjh() {
        return zhengjh;
    }

    /**
     * @param zhengjh 
	 *            证件号
     */
    public void setZhengjh(String zhengjh) {
        this.zhengjh = zhengjh;
    }

    /**
     * @return 固定电话
     */
    public String getGuddh() {
        return guddh;
    }

    /**
     * @param guddh 
	 *            固定电话
     */
    public void setGuddh(String guddh) {
        this.guddh = guddh;
    }

    /**
     * @return 移动电话
     */
    public String getYiddh() {
        return yiddh;
    }

    /**
     * @param yiddh 
	 *            移动电话
     */
    public void setYiddh(String yiddh) {
        this.yiddh = yiddh;
    }

    /**
     * @return 受理日期
     */
    public Date getShoulrq() {
        return shoulrq;
    }

    /**
     * @param shoulrq 
	 *            受理日期
     */
    public void setShoulrq(Date shoulrq) {
        this.shoulrq = shoulrq;
    }

    /**
     * @return 详细地址
     */
    public String getXiangxdd() {
        return xiangxdd;
    }

    /**
     * @param xiangxdd 
	 *            详细地址
     */
    public void setXiangxdd(String xiangxdd) {
        this.xiangxdd = xiangxdd;
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

    public String getIsdisplay() {
        return isdisplay;
    }

    public void setIsdisplay(String isdisplay) {
        this.isdisplay = isdisplay;
    }
}