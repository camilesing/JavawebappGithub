package com.authority.pojo;

import java.io.Serializable;
import java.util.Date;

import org.codehaus.jackson.map.annotate.JsonSerialize;

import com.authority.common.jackjson.CustomDateTimeSerializer;

/**
 * 温州广电-受理业务内容
 */
public class WzgdShoulywnr implements Serializable {
    private static final long serialVersionUID = 1L;

    private String id;

    /**
     * 用户编码
     */
    private String yonghbm;
    
    /**
     * 用户姓名
     */
    private String yonghxm;
    
    /**
     * 移动电话
     */
    
    private String yiddh;

    /**
     * 产品购买
     */
    private String chanpgm;

    /**
     * 开始日期
     */
    private Date kaisrq;

    /**
     * 结束日期
     */
    private Date jiesrq;

    private Date addtime;

    private String addwho;

    private String addip;

    private Date edittime;

    private String editwho;

    private String editip;

    /**
     * 是否有效,逻辑删除标志
     */
    private String isdisplay;

    /**
     * 处理状态
     */
    private String chulzt;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
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

    
    public String getYonghxm() {
		return yonghxm;
	}

	public void setYonghxm(String yonghxm) {
		this.yonghxm = yonghxm;
	}

	
	public String getYiddh() {
		return yiddh;
	}

	public void setYiddh(String yiddh) {
		this.yiddh = yiddh;
	}

	/**
     * @return 产品购买
     */
    public String getChanpgm() {
        return chanpgm;
    }

    /**
     * @param chanpgm 
	 *            产品购买
     */
    public void setChanpgm(String chanpgm) {
        this.chanpgm = chanpgm;
    }

    /**
     * @return 开始日期
     */
    @JsonSerialize(using = CustomDateTimeSerializer.class)
    public Date getKaisrq() {
        return kaisrq;
    }

    /**
     * @param kaisrq 
	 *            开始日期
     */
    public void setKaisrq(Date kaisrq) {
        this.kaisrq = kaisrq;
    }

    /**
     * @return 结束日期
     */
    @JsonSerialize(using = CustomDateTimeSerializer.class)
    public Date getJiesrq() {
        return jiesrq;
    }

    /**
     * @param jiesrq 
	 *            结束日期
     */
    public void setJiesrq(Date jiesrq) {
        this.jiesrq = jiesrq;
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
     * @return 是否有效,逻辑删除标志
     */
    public String getIsdisplay() {
        return isdisplay;
    }

    /**
     * @param isdisplay 
	 *            是否有效,逻辑删除标志
     */
    public void setIsdisplay(String isdisplay) {
        this.isdisplay = isdisplay;
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
}