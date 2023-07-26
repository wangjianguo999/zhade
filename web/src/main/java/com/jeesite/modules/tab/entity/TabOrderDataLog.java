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
 * tab_order_data_logEntity
 * @author 1
 * @version 2022-05-10
 */
@Table(name="tab_order_data_log", alias="a", label="tab_order_data_log信息", columns={
		@Column(name="rowid", attrName="rowid", label="rowid", isPK=true),
		@Column(name="userid", attrName="userid", label="userid"),
		@Column(name="orderid", attrName="orderid", label="orderid"),
		@Column(name="productid", attrName="productid", label="productid"),
		@Column(name="ordermonry", attrName="ordermonry", label="ordermonry"),
		@Column(name="rebatemoney", attrName="rebatemoney", label="rebatemoney"),
		@Column(name="status1", attrName="status1", label="status1"),
		@Column(name="pjnr", attrName="pjnr", label="pjnr"),
		@Column(name="servicestart", attrName="servicestart", label="servicestart"),
		@Column(name="fenleicontent", attrName="fenleicontent", label="fenleicontent"),
		@Column(name="createtime", attrName="createtime", label="createtime"),
		@Column(name="parentid1", attrName="parentid1", label="parentid1"),
		@Column(name="parentid2", attrName="parentid2", label="parentid2"),
		@Column(name="parentid3", attrName="parentid3", label="parentid3"),
		@Column(name="shangjilink", attrName="shangjilink", label="shangjilink"),
		@Column(name="istiyan", attrName="istiyan", label="istiyan"),
		@Column(name="ygzh", attrName="ygzh", label="ygzh"),
		@Column(name="ygzh2", attrName="ygzh2", label="ygzh2"),
	}, orderBy="a.rowid DESC"
)
public class TabOrderDataLog extends DataEntity<TabOrderDataLog> {
	
	private static final long serialVersionUID = 1L;
	private String rowid;		// rowid
	private String userid;		// userid
	private String orderid;		// orderid
	private String productid;		// productid
	private Double ordermonry;		// ordermonry
	private Double rebatemoney;		// rebatemoney
	private String status1;		// status1
	private String pjnr;		// pjnr
	private String servicestart;		// servicestart
	private String fenleicontent;		// fenleicontent
	private Date createtime;		// createtime
	private String parentid1;		// parentid1
	private String parentid2;		// parentid2
	private String parentid3;		// parentid3
	private String shangjilink;		// shangjilink
	private String istiyan;		// istiyan
	private String ygzh;		// ygzh
	private String ygzh2;		// ygzh2
	
	public TabOrderDataLog() {
		this(null);
	}

	public TabOrderDataLog(String id){
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
	
	@Size(min=0, max=451, message="orderid长度不能超过 451 个字符")
	public String getOrderid() {
		return orderid;
	}

	public void setOrderid(String orderid) {
		this.orderid = orderid;
	}
	
	@Size(min=0, max=45, message="productid长度不能超过 45 个字符")
	public String getProductid() {
		return productid;
	}

	public void setProductid(String productid) {
		this.productid = productid;
	}
	
	public Double getOrdermonry() {
		return ordermonry;
	}

	public void setOrdermonry(Double ordermonry) {
		this.ordermonry = ordermonry;
	}
	
	public Double getRebatemoney() {
		return rebatemoney;
	}

	public void setRebatemoney(Double rebatemoney) {
		this.rebatemoney = rebatemoney;
	}
	
	@Size(min=0, max=45, message="status1长度不能超过 45 个字符")
	public String getStatus1() {
		return status1;
	}

	public void setStatus1(String status1) {
		this.status1 = status1;
	}
	
	@Size(min=0, max=451, message="pjnr长度不能超过 451 个字符")
	public String getPjnr() {
		return pjnr;
	}

	public void setPjnr(String pjnr) {
		this.pjnr = pjnr;
	}
	
	@Size(min=0, max=45, message="servicestart长度不能超过 45 个字符")
	public String getServicestart() {
		return servicestart;
	}

	public void setServicestart(String servicestart) {
		this.servicestart = servicestart;
	}
	
	@Size(min=0, max=451, message="fenleicontent长度不能超过 451 个字符")
	public String getFenleicontent() {
		return fenleicontent;
	}

	public void setFenleicontent(String fenleicontent) {
		this.fenleicontent = fenleicontent;
	}
	
	@JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
	public Date getCreatetime() {
		return createtime;
	}

	public void setCreatetime(Date createtime) {
		this.createtime = createtime;
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
	
	@Size(min=0, max=1, message="istiyan长度不能超过 1 个字符")
	public String getIstiyan() {
		return istiyan;
	}

	public void setIstiyan(String istiyan) {
		this.istiyan = istiyan;
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