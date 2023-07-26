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
 * tab_licai_dataEntity
 * @author 1
 * @version 2021-12-17
 */
@Table(name="tab_licai_data", alias="a", label="tab_licai_data信息", columns={
		@Column(name="rowid", attrName="rowid", label="rowid", isPK=true),
		@Column(name="userid", attrName="userid", label="userid"),
		@Column(name="licaimoney", attrName="licaimoney", label="licaimoney"),
		@Column(name="days", attrName="days", label="days"),
		@Column(name="currentpre", attrName="currentpre", label="currentpre"),
		@Column(name="createtime", attrName="createtime", label="createtime"),
		@Column(name="endtime", attrName="endtime", label="endtime"),
		@Column(name="status1", attrName="status1", label="status1"),
		@Column(name="totalmoney", attrName="totalmoney", label="totalmoney"),
		@Column(name="parentid1", attrName="parentid1", label="返回时间"),
		@Column(name="parentid2", attrName="parentid2", label="返回时间"),
		@Column(name="shangjilink", attrName="shangjilink", label="scdate" ,queryType=QueryType.LIKE),
		@Column(name="parentid3", attrName="parentid3", label="返回时间"),
		@Column(name="ygzh", attrName="ygzh", label="ygzh"),
		@Column(name="ygzh2", attrName="ygzh2", label="ygzh2"),

	}, orderBy="a.createtime DESC"
)
public class TabLicaiData extends DataEntity<TabLicaiData> {
	private String ygzh ;
private String ygzh2; 
	
	
	
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
	private Double licaimoney;		// licaimoney
	private Long days;		// days
	private Double currentpre;		// currentpre
	private Date createtime;		// createtime
	private Date endtime;		// endtime
	private String status1;		// status1
	private Double totalmoney;		// totalmoney

	private String parentid1 ; 
	
	private String  parentid2 ;  
	private   String shangjilink;  
	private String   parentid3  ;
	
	
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

	public TabLicaiData() {
		this(null);
	}

	public TabLicaiData(String id){
		super(id);
	}
	
	public String getRowid() {
		return rowid;
	}

	public void setRowid(String rowid) {
		this.rowid = rowid;
	}
	
	@Size(min=0, max=45, message="userid长度不能超过 45 个字符")
	public String getUserid() {
		return userid;
	}

	public void setUserid(String userid) {
		this.userid = userid;
	}
	
	public Double getLicaimoney() {
		return licaimoney;
	}

	public void setLicaimoney(Double licaimoney) {
		this.licaimoney = licaimoney;
	}
	
	public Long getDays() {
		return days;
	}

	public void setDays(Long days) {
		this.days = days;
	}
	
	public Double getCurrentpre() {
		return currentpre;
	}

	public void setCurrentpre(Double currentpre) {
		this.currentpre = currentpre;
	}
	
	@JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
	public Date getCreatetime() {
		return createtime;
	}

	public void setCreatetime(Date createtime) {
		this.createtime = createtime;
	}
	
	@JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
	public Date getEndtime() {
		return endtime;
	}

	public void setEndtime(Date endtime) {
		this.endtime = endtime;
	}
	
	@Size(min=0, max=4, message="status1长度不能超过 4 个字符")
	public String getStatus1() {
		return status1;
	}

	public void setStatus1(String status1) {
		this.status1 = status1;
	}
	
	public Double getTotalmoney() {
		return totalmoney;
	}

	public void setTotalmoney(Double totalmoney) {
		this.totalmoney = totalmoney;
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
	
	public Date getEndtime_gte() {
		return sqlMap.getWhere().getValue("endtime", QueryType.GTE);
	}

	public void setEndtime_gte(Date endtime) {
		sqlMap.getWhere().and("endtime", QueryType.GTE, endtime);
	}
	
	public Date getEndtime_lte() {
		return sqlMap.getWhere().getValue("endtime", QueryType.LTE);
	}

	public void setEndtime_lte(Date endtime) {
		sqlMap.getWhere().and("endtime", QueryType.LTE, endtime);
	}
	
}