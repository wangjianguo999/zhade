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
 * tab_shouru_logEntity
 * @author 1
 * @version 2021-12-18
 */
@Table(name="tab_shouru_log", alias="a", label="tab_shouru_log信息", columns={
		@Column(name="rowid", attrName="rowid", label="rowid", isPK=true),
		@Column(name="userid", attrName="userid", label="userid"),
		@Column(name="money", attrName="money", label="money"),
		@Column(name="type", attrName="type", label="type"),
		@Column(name="createtime", attrName="createtime", label="createtime"),
		@Column(name="currentmoney", attrName="currentmoney", label="currentmoney"),
		@Column(name="orderid", attrName="orderid", label="orderid" ,queryType=QueryType.LIKE),
	}, orderBy="a.createtime DESC"
)
public class TabShouruLog extends DataEntity<TabShouruLog> {
	
	private static final long serialVersionUID = 1L;
	private String rowid;		// rowid
	private String userid;		// userid
	private Double money;		// money
	private String type;		// type
	private Date createtime;		// createtime
	private Double currentmoney;		// currentmoney
	private String orderid;		// orderid
	
	public TabShouruLog() {
		this(null);
	}

	public TabShouruLog(String id){
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
	
	public Double getMoney() {
		return money;
	}

	public void setMoney(Double money) {
		this.money = money;
	}
	
	@Size(min=0, max=45, message="type长度不能超过 45 个字符")
	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}
	
	@JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
	public Date getCreatetime() {
		return createtime;
	}

	public void setCreatetime(Date createtime) {
		this.createtime = createtime;
	}
	
	public Double getCurrentmoney() {
		return currentmoney;
	}

	public void setCurrentmoney(Double currentmoney) {
		this.currentmoney = currentmoney;
	}
	
	@Size(min=0, max=451, message="orderid长度不能超过 451 个字符")
	public String getOrderid() {
		return orderid;
	}

	public void setOrderid(String orderid) {
		this.orderid = orderid;
	}
	
}