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
 * tab_tyj_jiesuanEntity
 * @author 1
 * @version 2022-04-12
 */
@Table(name="tab_tyj_jiesuan", alias="a", label="tab_tyj_jiesuan信息", columns={
		@Column(name="rowid", attrName="rowid", label="rowid", isPK=true),
		@Column(name="userid", attrName="userid", label="userid"),
		@Column(name="cmd", attrName="cmd", label="cmd"),
		@Column(name="type", attrName="type", label="type"),
		@Column(name="amont", attrName="amont", label="amont"),
		@Column(name="createtime", attrName="createtime", label="createtime"),
		@Column(name="status1", attrName="status1", label="status1"),
		@Column(name="currentmoney", attrName="currentmoney", label="currentmoney"),
		@Column(name="zt", attrName="zt", label="zt"),
		@Column(name="parentid1", attrName="parentid1", label="parentid1"),
		@Column(name="parentid2", attrName="parentid2", label="parentid2"),
		@Column(name="parentid3", attrName="parentid3", label="parentid3"),
		@Column(name="shangjilink", attrName="shangjilink", label="shangjilink",queryType=QueryType.LIKE),
		@Column(name="ygzh", attrName="ygzh", label="ygzh"),
		@Column(name="ygzh2", attrName="ygzh2", label="ygzh2"),
	}, orderBy="a.rowid DESC"
)
public class TabTyjJiesuan extends DataEntity<TabTyjJiesuan> {
	
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
	private String parentid1;		// parentid1
	private String parentid2;		// parentid2
	private String parentid3;		// parentid3
	private String shangjilink;		// shangjilink
	private String ygzh;		// ygzh
	private String ygzh2;		// ygzh2
	
	public TabTyjJiesuan() {
		this(null);
	}

	public TabTyjJiesuan(String id){
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
	
	@Size(min=0, max=100, message="parentid1长度不能超过 100 个字符")
	public String getParentid1() {
		return parentid1;
	}

	public void setParentid1(String parentid1) {
		this.parentid1 = parentid1;
	}
	
	@Size(min=0, max=100, message="parentid2长度不能超过 100 个字符")
	public String getParentid2() {
		return parentid2;
	}

	public void setParentid2(String parentid2) {
		this.parentid2 = parentid2;
	}
	
	@Size(min=0, max=100, message="parentid3长度不能超过 100 个字符")
	public String getParentid3() {
		return parentid3;
	}

	public void setParentid3(String parentid3) {
		this.parentid3 = parentid3;
	}
	
	public String getShangjilink() {
		return shangjilink;
	}

	public void setShangjilink(String shangjilink) {
		this.shangjilink = shangjilink;
	}
	
	@Size(min=0, max=45, message="ygzh长度不能超过 45 个字符")
	public String getYgzh() {
		return ygzh;
	}

	public void setYgzh(String ygzh) {
		this.ygzh = ygzh;
	}
	
	@Size(min=0, max=45, message="ygzh2长度不能超过 45 个字符")
	public String getYgzh2() {
		return ygzh2;
	}

	public void setYgzh2(String ygzh2) {
		this.ygzh2 = ygzh2;
	}
	
}