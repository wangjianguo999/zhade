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
 * tab_yaoqing_jiangli_huodong_logEntity
 * @author 3
 * @version 2022-04-21
 */
@Table(name="tab_yaoqing_jiangli_huodong_log", alias="a", label="tab_yaoqing_jiangli_huodong_log信息", columns={
		@Column(name="rowid", attrName="rowid", label="rowid", isPK=true),
		@Column(name="jlbh", attrName="jlbh", label="奖励编号"),
		@Column(name="jlje", attrName="jlje", label="奖励金额"),
		@Column(name="userid", attrName="userid", label="用户编号"),
		@Column(name="createtime", attrName="createtime", label="createtime"),

		@Column(name="parentid1", attrName="parentid1", label="返回时间"),
		@Column(name="parentid2", attrName="parentid2", label="返回时间"),
		@Column(name="shangjilink", attrName="shangjilink", label="scdate" ,queryType=QueryType.LIKE),
		@Column(name="parentid3", attrName="parentid3", label="返回时间"),

		@Column(name="ygzh", attrName="ygzh", label="ygzh"),
		@Column(name="ygzh2", attrName="ygzh2", label="ygzh2"),
	}, orderBy="a.createtime DESC"
)
public class TabYaoqingJiangliHuodongLog extends DataEntity<TabYaoqingJiangliHuodongLog> {
	
	private static final long serialVersionUID = 1L;
	private String rowid;		// rowid
	private String jlbh;		// 奖励编号
	private Double jlje;		// 奖励金额
	private String userid;		// 用户编号
	private Date createtime;		// createtime
	
	private  Long getPre ;
	

	private String parentid1; 
	private String parentid2; 
	private String shangjilink; 
	private String parentid3; 
	private String ygzh; 
	private String ygzh2; 
	
	
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

	public String getYgzh() {
		return ygzh;
	}

	public void setYgzh(String ygzh) {
		this.ygzh = ygzh;
	}

	public String getYgzh2() {
		return ygzh2;
	}

	public void setYgzh2(String ygzh2) {
		this.ygzh2 = ygzh2;
	}

	public Long getGetPre() {
		return getPre;
	}

	public void setGetPre(Long getPre) {
		this.getPre = getPre;
	}

	public static long getSerialversionuid() {
		return serialVersionUID;
	}

	public TabYaoqingJiangliHuodongLog() {
		this(null);
	}

	public TabYaoqingJiangliHuodongLog(String id){
		super(id);
	}
	
	public String getRowid() {
		return rowid;
	}

	public void setRowid(String rowid) {
		this.rowid = rowid;
	}
	
	@Size(min=0, max=451, message="奖励编号长度不能超过 451 个字符")
	public String getJlbh() {
		return jlbh;
	}

	public void setJlbh(String jlbh) {
		this.jlbh = jlbh;
	}
	
	public Double getJlje() {
		return jlje;
	}

	public void setJlje(Double jlje) {
		this.jlje = jlje;
	}
	
	@Size(min=0, max=111, message="用户编号长度不能超过 111 个字符")
	public String getUserid() {
		return userid;
	}

	public void setUserid(String userid) {
		this.userid = userid;
	}
	
	@JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
	public Date getCreatetime() {
		return createtime;
	}

	public void setCreatetime(Date createtime) {
		this.createtime = createtime;
	}
	
}