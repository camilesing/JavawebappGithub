package com.authority.pojo;

import java.io.Serializable;
import java.util.Date;

/**
 * 银行代码对照表
 */
public class YqgdBankcode implements Serializable {
    private static final long serialVersionUID = 1L;

    private String id;

    /**
     * 代码
     */
    private String code;

    /**
     * 名称
     */
    private String name;

    private Date addtime;

    private String addwho;

    private String addip;

    private Date edittime;

    private String editwho;

    private String editip;

    /**
     * 是有有效 0 无效，1有效
     */
    private String isdisplay;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    /**
     * @return 代码
     */
    public String getCode() {
        return code;
    }

    /**
     * @param code 
	 *            代码
     */
    public void setCode(String code) {
        this.code = code;
    }

    /**
     * @return 名称
     */
    public String getName() {
        return name;
    }

    /**
     * @param name 
	 *            名称
     */
    public void setName(String name) {
        this.name = name;
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
     * @return 是有有效 0 无效，1有效
     */
    public String getIsdisplay() {
        return isdisplay;
    }

    /**
     * @param isdisplay 
	 *            是有有效 0 无效，1有效
     */
    public void setIsdisplay(String isdisplay) {
        this.isdisplay = isdisplay;
    }
}