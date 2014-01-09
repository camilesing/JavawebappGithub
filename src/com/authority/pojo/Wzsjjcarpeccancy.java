package com.authority.pojo;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;

import org.codehaus.jackson.map.annotate.JsonSerialize;

import com.authority.common.jackjson.CustomDateTimeSerializer;

public class Wzsjjcarpeccancy implements Serializable {
    private static final long serialVersionUID = 1L;

    private String id;

    /**
     * 通知书编号
     */
    private String tongzsbh;

    /**
     * 违法信息
     */
    private String weifxx;

    /**
     * 罚款金额
     */
    private BigDecimal fakje;

    /**
     * 车辆编码
     */
    private String chelbm;
    
    private String chep ;

    private Date addtime;

    private String addwho;

    private String addip;

    private Date edittime;

    private String editwho;

    private String editip;
    
    private String suoyr ;
    
    private String lianxfs;
    
    private String shenfzh;
    
    private String fromid;

    /**
     * 处理状态 0 未缴费 1 已缴费
     */
    private String chulzt;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    /**
     * @return 通知书编号
     */
    public String getTongzsbh() {
        return tongzsbh;
    }

    /**
     * @param tongzsbh 
	 *            通知书编号
     */
    public void setTongzsbh(String tongzsbh) {
        this.tongzsbh = tongzsbh;
    }

    /**
     * @return 违法信息
     */
    public String getWeifxx() {
        return weifxx;
    }

    /**
     * @param weifxx 
	 *            违法信息
     */
    public void setWeifxx(String weifxx) {
        this.weifxx = weifxx;
    }

    /**
     * @return 罚款金额
     */
    public BigDecimal getFakje() {
        return fakje;
    }

    /**
     * @param fakje 
	 *            罚款金额
     */
    public void setFakje(BigDecimal fakje) {
        this.fakje = fakje;
    }

    /**
     * @return 车辆编码
     */
    public String getChelbm() {
        return chelbm;
    }

    /**
     * @param chelbm 
	 *            车辆编码
     */
    public void setChelbm(String chelbm) {
        this.chelbm = chelbm;
    }
    
    /**
     * @return 车牌
     */
    public String getChep() {
		return chep;
	}

	public void setChep(String chep) {
		this.chep = chep;
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
     * @return 处理状态 0 未缴费 1 已缴费
     */
    public String getChulzt() {
        return chulzt;
    }

    /**
     * @param chulzt 
	 *            处理状态 0 未缴费 1 已缴费
     */
    public void setChulzt(String chulzt) {
        this.chulzt = chulzt;
    }

	public String getSuoyr() {
		return suoyr;
	}

	public void setSuoyr(String suoyr) {
		this.suoyr = suoyr;
	}

	public String getLianxfs() {
		return lianxfs;
	}

	public void setLianxfs(String lianxfs) {
		this.lianxfs = lianxfs;
	}

	public String getShenfzh() {
		return shenfzh;
	}

	public void setShenfzh(String shenfzh) {
		this.shenfzh = shenfzh;
	}

	public String getFromid() {
		return fromid;
	}

	public void setFromid(String fromid) {
		this.fromid = fromid;
	}
    
    
}