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
 * tab_jiesuan_logEntity
 * @author 23
 * @version 2022-02-03
 */
@Table(name="tab_jiesuan_log", alias="a", label="交易明细信息", columns={
		@Column(name="rowid", attrName="rowid", label="rowid", isPK=true),
		@Column(name="userid", attrName="userid", label="userid"),
		@Column(name="cmd", attrName="cmd", label="cmd",queryType = QueryType.LIKE),
		@Column(name="type", attrName="type", label="type"),
		@Column(name="amont", attrName="amont", label="amont"),
		@Column(name="createtime", attrName="createtime", label="createtime"),
		@Column(name="status1", attrName="status1", label="status1"),
		@Column(name="currentmoney", attrName="currentmoney", label="currentmoney"),
		@Column(name="zt", attrName="zt", label="zt"),

		@Column(name="parentid1", attrName="parentid1", label="返回时间"),
		@Column(name="parentid2", attrName="parentid2", label="返回时间"),
		@Column(name="shangjilink", attrName="shangjilink", label="scdate" ,queryType=QueryType.LIKE),
		@Column(name="parentid3", attrName="parentid3", label="返回时间"),
		@Column(name="ygzh", attrName="ygzh", label="ygzh"),
		
		@Column(name="ygzh2", attrName="ygzh2", label="ygzh2"),
		@Column(name="msg", attrName="msg", label="msg"),

		@Column(name="subordinate", attrName="subordinate", label="subordinate"),
	}, orderBy="a.createtime DESC"
)
public class TabJiesuanLog extends DataEntity<TabJiesuanLog> {
private String  ygzh; 
	private String ygzh2;


	public String getSubordinate() {
		return subordinate;
	}

	public void setSubordinate(String subordinate) {
		this.subordinate = subordinate;
	}

	private String subordinate;
	
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

	private static final long serialVersionUID = 1L;
	private String rowid;		// rowid
	private String userid;		// userid
	private String cmd;		// cmd
	private String type;		// type
	private Double amont;		// amont
	private Date createtime;		// createtime
	private String status1;		// status1
	private Double currentmoney;		// currentmoney
	private String zt;		// zt

	private String parentid1 ; 
	
	private String  parentid2 ;  
	private   String shangjilink;  
	private String   parentid3  ;

	private String   msg;

	public String getMsg() {
		return msg;
	}

	public void setMsg(String msg) {
		this.msg = msg;
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

	public static long getSerialversionuid() {
		return serialVersionUID;
	}

	public TabJiesuanLog() {
		this(null);
	}

	public TabJiesuanLog(String id){
		super(id);
	}
	
	public String getRowid() {
		return rowid;
	}

	public void setRowid(String rowid) {
		this.rowid = rowid;
	}
	
	@Size(min=0, max=451, message="userid长度不能超过 451 个字符")
	public String getUserid() {
		return userid;
	}

	public void setUserid(String userid) {
		this.userid = userid;
	}
	
	@Size(min=0, max=451, message="cmd长度不能超过 451 个字符")
	public String getCmd() {
		return cmd;
	}

	public void setCmd(String cmd) {
		this.cmd = cmd;
	}
	
	@Size(min=0, max=451, message="type长度不能超过 451 个字符")
	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}
	
	public Double getAmont() {
		return amont;
	}

	public void setAmont(Double amont) {
		this.amont = amont;
	}
	
	@JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
	public Date getCreatetime() {
		return createtime;
	}

	public void setCreatetime(Date createtime) {
		this.createtime = createtime;
	}
	
	@Size(min=0, max=411, message="status1长度不能超过 411 个字符")
	public String getStatus1() {
		return status1;
	}

	public void setStatus1(String status1) {
		this.status1 = status1;
	}
	
	public Double getCurrentmoney() {
		return currentmoney;
	}

	public void setCurrentmoney(Double currentmoney) {
		this.currentmoney = currentmoney;
	}
	
	@Size(min=0, max=451, message="zt长度不能超过 451 个字符")
	public String getZt() {
		return zt;
	}

	public void setZt(String zt) {
		this.zt = zt;
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