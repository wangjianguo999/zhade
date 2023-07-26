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
 * tab_ordersEntity
 * @author 1
 * @version 2022-04-10
 */
@Table(name="tab_orders", alias="a", label="tab_orders信息", columns={
		@Column(name="rowid", attrName="rowid", label="rowid", isPK=true),
		@Column(name="userid", attrName="userid", label="用户编号",queryType=QueryType.LIKE),
		@Column(name="orderid", attrName="orderid", label="订单编号"),
		@Column(name="ordermoney", attrName="ordermoney", label="订单金额"),
		@Column(name="lirunmonry", attrName="lirunmonry", label="利润金额"),
		@Column(name="ordercont", attrName="ordercont", label="订单数"),
		@Column(name="paycount", attrName="paycount", label="已支付订单"),
		@Column(name="status1", attrName="status1", label="订单状态"),
		@Column(name="createtime", attrName="createtime", label="创建时间"),
		@Column(name="paytime", attrName="paytime", label="最后更新时间"),
		
		@Column(name="ygzh", attrName="ygzh", label="最后更新时间"),

		
		@Column(name="ygzh2", attrName="ygzh2", label="最后更新时间"),

		@Column(name="vip", attrName="vip", label="vip"),

		
		@Column(name="parentid1", attrName="parentid1", label="最后更新时间"),

		
		@Column(name="parentid2", attrName="parentid2", label="最后更新时间"),

		
		@Column(name="parentid3", attrName="parentid3", label="最后更新时间"),

		@Column(name="istiyan", attrName="istiyan", label="最后更新时间"),

		@Column(name="shangjilink", attrName="shangjilink", label="最后更新时间" ,queryType =  QueryType.LIKE),

		
	}, orderBy="a.createtime DESC"
)
public class TabOrders extends DataEntity<TabOrders> {
	
	private String ygzh; 

	private String ygzh2; 

	private String parentid1; 

	private String parentid2; 

	private String parentid3; 

	private String shangjilink;

	private String vip;
	public String getIstiyan() {
		return istiyan;
	}

	public void setIstiyan(String istiyan) {
		this.istiyan = istiyan;
	}
	public String getVip() {
		return vip;
	}

	public void setVip(String vip) {
		this.vip = vip;
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

	public static long getSerialversionuid() {
		return serialVersionUID;
	}

	private static final long serialVersionUID = 1L;
	private String rowid;		// rowid
	private String userid;		// 用户编号
	private String orderid;		// 订单编号
	private Double ordermoney;		// 订单金额
	private Double lirunmonry;		// 利润金额
	private Long ordercont;		// 订单数
	private Long paycount;		// 已支付订单
	private String status1;		// 订单状态
	private Date createtime;		// 创建时间
	private Date paytime;		// 最后更新时间
	private String istiyan;
	public TabOrders() {
		this(null);
	}

	public TabOrders(String id){
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
	
	@Size(min=0, max=45, message="订单编号长度不能超过 45 个字符")
	public String getOrderid() {
		return orderid;
	}

	public void setOrderid(String orderid) {
		this.orderid = orderid;
	}
	
	public Double getOrdermoney() {
		return ordermoney;
	}

	public void setOrdermoney(Double ordermoney) {
		this.ordermoney = ordermoney;
	}
	
	public Double getLirunmonry() {
		return lirunmonry;
	}

	public void setLirunmonry(Double lirunmonry) {
		this.lirunmonry = lirunmonry;
	}
	
	public Long getOrdercont() {
		return ordercont;
	}

	public void setOrdercont(Long ordercont) {
		this.ordercont = ordercont;
	}
	
	public Long getPaycount() {
		return paycount;
	}

	public void setPaycount(Long paycount) {
		this.paycount = paycount;
	}
	
	@Size(min=0, max=45, message="订单状态长度不能超过 45 个字符")
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
	
	@JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
	public Date getPaytime() {
		return paytime;
	}

	public void setPaytime(Date paytime) {
		this.paytime = paytime;
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
	
	

	public Date getCreatetime1_gte() {
		return sqlMap.getWhere().getValue("paytime", QueryType.GTE);
	}

	public void setCreatetime1_gte(Date createtime1) {
		sqlMap.getWhere().and("paytime", QueryType.GTE, createtime1);
	}
	
	public Date getCreatetime1_lte() {
		return sqlMap.getWhere().getValue("paytime", QueryType.LTE);
	}

	public void setCreatetime1_lte(Date createtime1) {
		sqlMap.getWhere().and("paytime", QueryType.LTE, createtime1);
	}
	
}