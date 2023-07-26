package com.jeesite.modules.tab.entity;

import javax.validation.constraints.Size;
import java.util.Date;
import com.jeesite.common.mybatis.annotation.JoinTable;
import com.jeesite.common.mybatis.annotation.JoinTable.Type;
import com.fasterxml.jackson.annotation.JsonFormat;

import com.jeesite.common.entity.DataEntity;
import com.jeesite.common.mybatis.annotation.Column;
import com.jeesite.common.mybatis.annotation.Table;
import com.jeesite.common.mybatis.mapper.query.QueryType;

/**
 * tab_tixian_logEntity
 * 
 * @author 1
 * @version 2021-12-17
 */
@Table(name = "tab_tixian_log", alias = "a", label = "tab_tixian_log信息", columns = {
		@Column(name = "rowid", attrName = "rowid", label = "rowid", isPK = true),
		@Column(name = "userid", attrName = "userid", label = "userid",queryType=QueryType.LIKE),
		@Column(name = "money", attrName = "money", label = "money"),
		@Column(name = "status1", attrName = "status1", label = "status1"),
		@Column(name = "createtime", attrName = "createtime", label = "createtime"),
		@Column(name = "note", attrName = "note", label = "note"),
		@Column(name = "currentmoeny", attrName = "currentmoeny", label = "currentmoeny"),
		@Column(name = "type", attrName = "type", label = "type"),
		@Column(name = "cardtype", attrName = "cardtype", label = "cardtype"),	
		
		@Column(name = "updatetime", attrName = "updatetime", label = "updatetime"),	

		@Column(name = "parentid1", attrName = "parentid1", label = "返回时间"),
		@Column(name = "parentid2", attrName = "parentid2", label = "返回时间"),
		@Column(name = "shangjilink", attrName = "shangjilink", label = "scdate", queryType = QueryType.LIKE),
		@Column(name = "parentid3", attrName = "parentid3", label = "返回时间"),

		@Column(name = "dzje", attrName = "dzje", label = "dzje"),
		
		
		@Column(name = "orderid", attrName = "orderid", label = "orderid"),
		
		
		@Column(name = "zftd", attrName = "zftd", label = "zftd"),

		@Column(name="ygzh", attrName="ygzh", label="ygzh"),
		@Column(name="ygzh2", attrName="ygzh2", label="ygzh2"),
		@Column(name="xdzt1", attrName="xdzt1", label="xdzt1"),
		@Column(name="code", attrName="code", label="code"),
		
}, orderBy = "a.createtime DESC")
public class TabTixianLog extends DataEntity<TabTixianLog> {
private String zftd; 
private String  ygzh; 

private String ygzh2; 

private String qbmc;

	public String getCode() {
		return code;
	}

	public void setCode(String code) {
		this.code = code;
	}

	private String code;
	public Integer getXdzt1() {
		return xdzt1;
	}

	public void setXdzt1(Integer xdzt1) {
		this.xdzt1 = xdzt1;
	}

	private Integer xdzt1;



	public String getQbmc() {
	return qbmc;
}

public void setQbmc(String qbmc) {
	this.qbmc = qbmc;
}

public String getYgzh2() {
	return ygzh2;
}

public void setYgzh2(String ygzh2) {
	this.ygzh2 = ygzh2;
}
public String getYgzh() {
	return ygzh;
}

public void setYgzh(String ygzh) {
	this.ygzh = ygzh;
}


public Date getUpdatetime_lte() {
	return sqlMap.getWhere().getValue("updatetime", QueryType.LTE);
}

public void setUpdatetime_lte(Date updatetime) {
	sqlMap.getWhere().and("updatetime", QueryType.LTE, updatetime);
}



public Date getUpdatetime_gte() {
	return sqlMap.getWhere().getValue("updatetime", QueryType.GTE);
}

public void setUpdatetime_gte(Date updatetime) {
	sqlMap.getWhere().and("updatetime", QueryType.GTE, updatetime);
}


	public String getZftd() {
	return zftd;
}

public void setZftd(String zftd) {
	this.zftd = zftd;
}

private String useraddress;

private Date updatetime ; 




	public Date getUpdatetime() {
	return updatetime;
}

public void setUpdatetime(Date updatetime) {
	this.updatetime = updatetime;
}

	public String getUseraddress() {
	return useraddress;
}

public void setUseraddress(String useraddress) {
	this.useraddress = useraddress;
}

	private static final long serialVersionUID = 1L;
	private String rowid; // rowid
	private String userid; // userid
	private Double money; // money
	private String status1; // status1
	private Date createtime; // createtime
	private String note; // note
	private Double currentmoeny; // currentmoeny
	private String type; // status1
	private String cardtype; // status1

	private Double dzje;
	
private String orderid; 



	private String parentid1;

	private String parentid2;
	private String shangjilink;
	private String parentid3;

	
	
	
	public String getOrderid() {
		return orderid;
	}

	public void setOrderid(String orderid) {
		this.orderid = orderid;
	}

	public Double getDzje() {
		return dzje;
	}

	public void setDzje(Double dzje) {
		this.dzje = dzje;
	}

	public String getParentid1() {
		return parentid1;
	}

	public void setParentid1(String parentid1) {
		this.parentid1 = parentid1;
	}

	public String getParentid2() {
		return parentid2;
	}

	public void setParentid2(String parentid2) {
		this.parentid2 = parentid2;
	}

	public String getShangjilink() {
		return shangjilink;
	}

	public void setShangjilink(String shangjilink) {
		this.shangjilink = shangjilink;
	}

	public String getParentid3() {
		return parentid3;
	}

	public void setParentid3(String parentid3) {
		this.parentid3 = parentid3;
	}

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}

	public String getCardtype() {
		return cardtype;
	}

	public void setCardtype(String cardtype) {
		this.cardtype = cardtype;
	}

	public static long getSerialversionuid() {
		return serialVersionUID;
	}

	public TabTixianLog() {
		this(null);
	}

	public TabTixianLog(String id) {
		super(id);
	}

	public String getRowid() {
		return rowid;
	}

	public void setRowid(String rowid) {
		this.rowid = rowid;
	}

	@Size(min = 0, max = 45, message = "userid长度不能超过 45 个字符")
	public String getUserid() {
		return userid;
	}

	public void setUserid(String userid) {
		this.userid = userid;
	}

	public Double getMoney() {
		return money;
	}

	public void setMoney(Double money) {
		this.money = money;
	}

	@Size(min = 0, max = 4, message = "status1长度不能超过 4 个字符")
	public String getStatus1() {
		return status1;
	}

	public void setStatus1(String status1) {
		this.status1 = status1;
	}

	@JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
	public Date getCreatetime() {
		return createtime;
	}

	public void setCreatetime(Date createtime) {
		this.createtime = createtime;
	}

	@Size(min = 0, max = 45, message = "note长度不能超过 45 个字符")
	public String getNote() {
		return note;
	}

	public void setNote(String note) {
		this.note = note;
	}

	public Double getCurrentmoeny() {
		return currentmoeny;
	}

	public void setCurrentmoeny(Double currentmoeny) {
		this.currentmoeny = currentmoeny;
	}

	public Date getCreatetime_gte() {
		return sqlMap.getWhere().getValue("createtime", QueryType.GTE);
	}

	public void setCreatetime_gte(Date createtime) {
		sqlMap.getWhere().and("createtime", QueryType.GTE, createtime);
	}

	public Date getCreatetime_lte() {
		return sqlMap.getWhere().getValue("createtime", QueryType.LTE);
	}

	public void setCreatetime_lte(Date createtime) {
		sqlMap.getWhere().and("createtime", QueryType.LTE, createtime);
	}

}