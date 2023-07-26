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
 * tab_order_dataEntity
 * @author 32
 * @version 2021-12-18
 */
@Table(name="tab_order_data", alias="a", label="tab_order_data信息", columns={
		@Column(name="rowid", attrName="rowid", label="rowid", isPK=true),
		@Column(name="userid", attrName="userid", label="userid",queryType=QueryType.LIKE),
		@Column(name="orderid", attrName="orderid", label="orderid"),
		@Column(name="productid", attrName="productid", label="productid"),
		@Column(name="ordermonry", attrName="ordermonry", label="ordermonry"),
		@Column(name="rebatemoney", attrName="rebatemoney", label="rebatemoney"),
		@Column(name="status1", attrName="status1", label="status1"),
		@Column(name="pjnr", attrName="pjnr", label="pjnr"),
		@Column(name="servicestart", attrName="servicestart", label="servicestart"),
		@Column(name="fenleicontent", attrName="fenleicontent", label="fenleicontent"),
		@Column(name="createtime", attrName="createtime", label="createtime"),
		
		
		@Column(name="parentid1", attrName="parentid1", label="返回时间"),
		@Column(name="parentid2", attrName="parentid2", label="返回时间"),
		@Column(name="shangjilink", attrName="shangjilink", label="scdate" ,queryType=QueryType.LIKE),
		@Column(name="parentid3", attrName="parentid3", label="返回时间"),
		
		@Column(name="istiyan", attrName="istiyan", label="返回时间"),

		@Column(name="ygzh", attrName="ygzh", label="ygzh"),

		@Column(name="ygzh2", attrName="ygzh2", label="ygzh2"),

	}, orderBy="a.createtime DESC"
)
public class TabOrderData extends DataEntity<TabOrderData> {
	private String  ygzh; 
	
	
	public String getYgzh() {
		return ygzh;
	}
	
	
private String ygzh2;



	public String getYgzh2() {
		return ygzh2;
	}

	public void setYgzh2(String ygzh2) {
		this.ygzh2 = ygzh2;
	}

	public void setYgzh(String ygzh) {
		this.ygzh = ygzh;
	}

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
	
	private  TabProductData tabProductData;
	private  String parentid3 ; 
	private String shangjilink ;
	
	private String istiyan ;
	private String imgsrc;
	public String getImgsrc() {
		return imgsrc;
	}

	public void setImgsrc(String imgsrc) {
		this.imgsrc = imgsrc;
	}

	public String getIstiyan() {
		return istiyan;
	}

	public void setIstiyan(String istiyan) {
		this.istiyan = istiyan;
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

	private String parentid1 ; 
	
	private String  parentid2 ;  
	
	
	
	
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

	public TabProductData getTabProductData() {
		return tabProductData;
	}

	public void setTabProductData(TabProductData tabProductData) {
		this.tabProductData = tabProductData;
	}

	public static long getSerialversionuid() {
		return serialVersionUID;
	}

	public TabOrderData() {
		this(null);
	}

	public TabOrderData(String id){
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