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
 * tab_rechange_logEntity
 * @author 3
 * @version 2021-12-12
 */
@Table(name="tab_rechange_log", alias="a", label="tab_rechange_log信息", columns={
		@Column(name="rowid", attrName="rowid", label="rowid", isPK=true),
		@Column(name="userid", attrName="userid", label="用户编号",queryType=QueryType.LIKE),
		@Column(name="amont", attrName="amont", label="充值金额"),
		@Column(name="orderid", attrName="orderid", label="充值订单"),
		@Column(name="createtime", attrName="createtime", label="创建时间"),
		@Column(name="status1", attrName="status1", label="充值状态"),
		@Column(name="updatetime", attrName="updatetime", label="返回时间"),
		
		@Column(name="paytype", attrName="paytype", label="返回时间"),
		@Column(name="type", attrName="type", label="返回时间"),
		@Column(name="hzhb", attrName="hzhb", label="返回时间"),
		@Column(name="username", attrName="username", label="返回时间"),
		@Column(name="phones", attrName="phones", label="返回时间"),
		@Column(name="pingzheng", attrName="pingzheng", label="返回时间" ,queryType=QueryType.LIKE),
		@Column(name="beizhu", attrName="beizhu", label="返回时间"),
		
		
		@Column(name="parentid1", attrName="parentid1", label="返回时间"),
		@Column(name="parentid2", attrName="parentid2", label="返回时间"),
		@Column(name="shangjilink", attrName="shangjilink", label="scdate" ,queryType=QueryType.LIKE),
		@Column(name="parentid3", attrName="parentid3", label="返回时间"),

		@Column(name="ygzh", attrName="ygzh", label="ygzh"),
		@Column(name="ygzh2", attrName="ygzh2", label="ygzh2"),
		@Column(name="userid1", attrName="userid1", label="userid1"),
		@Column(name="orderType", attrName="orderType", label="orderType"),
		@Column(name="refundtime", attrName="refundtime", label="refundtime"),
		@Column(name="isrefund", attrName="isrefund", label="isrefund"),
		@Column(name="istongji", attrName="istongji", label="istongji"),
	}, orderBy="a.createtime DESC"
)
public class TabRechangeLog extends DataEntity<TabRechangeLog> {
private String ygzh; 

private String ygzh2; 
private String userid1;

	public String getUserid1() {
		return userid1;
	}

	public void setUserid1(String userid1) {
		this.userid1 = userid1;
	}

	public Integer getOrderType() {
		return orderType;
	}

	public void setOrderType(Integer orderType) {
		this.orderType = orderType;
	}

	private Integer orderType;

	public Integer getIstongji() {
		return istongji;
	}

	public void setIstongji(Integer istongji) {
		this.istongji = istongji;
	}

	private Integer istongji;
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

	public Date getCreatetime_gte() {
		return sqlMap.getWhere().getValue("createtime", QueryType.GTE);
	}

	public void setCreatetime_gte(Date createtime) {
		sqlMap.getWhere().and("createtime", QueryType.GTE, createtime);
	}
	
	public Date getUpdatetime_gte() {
		return sqlMap.getWhere().getValue("updatetime", QueryType.GTE);
	}

	public void setUpdatetime_gte(Date updatetime) {
		sqlMap.getWhere().and("updatetime", QueryType.GTE, updatetime);
	}

	public Date getCreatetime_lte() {
		return sqlMap.getWhere().getValue("createtime", QueryType.LTE);
	}

	public void setCreatetime_lte(Date createtime) {
		sqlMap.getWhere().and("createtime", QueryType.LTE, createtime);
	}

	public void setUpdatetime_lte(Date updatetime) {
		sqlMap.getWhere().and("updatetime", QueryType.LTE, updatetime);
	}
	
	
	
	private static final long serialVersionUID = 1L;
	private String rowid;		// rowid
	private String userid;		// 用户编号
	private Double amont;		// 充值金额
	private String orderid;		// 充值订单
	private Date createtime;		// 创建时间
	private String status1;		// 充值状态
	private Date updatetime;		// 返回时间
	private String paytype; 
	private String type; 
	
	private String parentid1 ; 
	
	private String  parentid2 ;  
	private   String shangjilink;  
	private String   parentid3  ;
	
	
	
	

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

	private Double  hzhb ; 
	private String username; 
	private String phones; 
	private String pingzheng; 
 	private String beizhu;

	public Date getRefundtime() {
		return refundtime;
	}

	public void setRefundtime(Date refundtime) {
		this.refundtime = refundtime;
	}

	private Date refundtime;

	public Integer getIsrefund() {
		return isrefund;
	}

	public void setIsrefund(Integer isrefund) {
		this.isrefund = isrefund;
	}

	private Integer isrefund;
	public Double getHzhb() {
		return hzhb;
	}

	public void setHzhb(Double hzhb) {
		this.hzhb = hzhb;
	}

	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	public String getPhones() {
		return phones;
	}

	public void setPhones(String phones) {
		this.phones = phones;
	}

	public String getPingzheng() {
		return pingzheng;
	}

	public void setPingzheng(String pingzheng) {
		this.pingzheng = pingzheng;
	}

	public String getBeizhu() {
		return beizhu;
	}

	public void setBeizhu(String beizhu) {
		this.beizhu = beizhu;
	}

	public String getPaytype() {
		return paytype;
	}

	public void setPaytype(String paytype) {
		this.paytype = paytype;
	}

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}

	public static long getSerialversionuid() {
		return serialVersionUID;
	}

	public TabRechangeLog() {
		this(null);
	}

	public TabRechangeLog(String id){
		super(id);
	}
	
	public String getRowid() {
		return rowid;
	}

	public void setRowid(String rowid) {
		this.rowid = rowid;
	}
	
	@Size(min=0, max=45, message="用户编号长度不能超过 45 个字符")
	public String getUserid() {
		return userid;
	}

	public void setUserid(String userid) {
		this.userid = userid;
	}
	
	public Double getAmont() {
		return amont;
	}

	public void setAmont(Double amont) {
		this.amont = amont;
	}
	
	@Size(min=0, max=451, message="充值订单长度不能超过 451 个字符")
	public String getOrderid() {
		return orderid;
	}

	public void setOrderid(String orderid) {
		this.orderid = orderid;
	}
	
	@JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
	public Date getCreatetime() {
		return createtime;
	}

	public void setCreatetime(Date createtime) {
		this.createtime = createtime;
	}
	
	@Size(min=0, max=45, message="充值状态长度不能超过 45 个字符")
	public String getStatus1() {
		return status1;
	}

	public void setStatus1(String status1) {
		this.status1 = status1;
	}
	
	@JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
	public Date getUpdatetime() {
		return updatetime;
	}

	public void setUpdatetime(Date updatetime) {
		this.updatetime = updatetime;
	}
	
}