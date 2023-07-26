package com.jeesite.modules.tab.entity;

import javax.validation.constraints.Size;

import com.jeesite.common.entity.DataEntity;
import com.jeesite.common.mybatis.annotation.Column;
import com.jeesite.common.mybatis.annotation.Table;
import com.jeesite.common.mybatis.mapper.query.QueryType;

/**
 * tab_product_dataEntity
 * @author 1
 * @version 2021-12-18
 */
@Table(name="tab_product_data", alias="a", label="tab_product_data信息", columns={
		@Column(name="rowid", attrName="rowid", label="rowid", isPK=true),
		@Column(name="leval", attrName="leval", label="leval"),
		@Column(name="imgsrc", attrName="imgsrc", label="imgsrc"),
		@Column(name="price", attrName="price", label="price"),
		@Column(name="rebate", attrName="rebate", label="rebate"),
		@Column(name="buytime", attrName="buytime", label="buytime"),
		@Column(name="buymoney", attrName="buymoney", label="buymoney"),
		@Column(name="totalrebate", attrName="totalrebate", label="totalrebate"),
		@Column(name="name", attrName="name", label="name" ,queryType=QueryType.LIKE),
		@Column(name="namedesc", attrName="namedesc", label="namedesc"),
		
		@Column(name="sdsj", attrName="sdsj", label="sdsj"),

		

	}, orderBy="a.leval ASC"
)
public class TabProductData extends DataEntity<TabProductData> {
	
	private static final long serialVersionUID = 1L;
	private String rowid;		// rowid
	private String leval;		// leval
	private String imgsrc;		// imgsrc
	private Double price;		// price
	private Double rebate;		// rebate
	private Long buytime;		// buytime
	private Double buymoney;		// buymoney
	private Double totalrebate;		// totalrebate
	
	private String name; 
	
	private String namedesc;

	private Long  sdsj;  
	
	
	
	

	 
	public Long getSdsj() {
		return sdsj;
	}

	public void setSdsj(Long sdsj) {
		this.sdsj = sdsj;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getNamedesc() {
		return namedesc;
	}

	public void setNamedesc(String namedesc) {
		this.namedesc = namedesc;
	}

	public static long getSerialversionuid() {
		return serialVersionUID;
	}

	public TabProductData() {
		this(null);
	}

	public TabProductData(String id){
		super(id);
	}
	
	public String getRowid() {
		return rowid;
	}

	public void setRowid(String rowid) {
		this.rowid = rowid;
	}
	
	@Size(min=0, max=45, message="leval长度不能超过 45 个字符")
	public String getLeval() {
		return leval;
	}

	public void setLeval(String leval) {
		this.leval = leval;
	}
	
	@Size(min=0, max=200, message="imgsrc长度不能超过 200 个字符")
	public String getImgsrc() {
		return imgsrc;
	}

	public void setImgsrc(String imgsrc) {
		this.imgsrc = imgsrc;
	}
	
	public Double getPrice() {
		return price;
	}

	public void setPrice(Double price) {
		this.price = price;
	}
	
	public Double getRebate() {
		return rebate;
	}

	public void setRebate(Double rebate) {
		this.rebate = rebate;
	}
	
	public Long getBuytime() {
		return buytime;
	}

	public void setBuytime(Long buytime) {
		this.buytime = buytime;
	}
	
	public Double getBuymoney() {
		return buymoney;
	}

	public void setBuymoney(Double buymoney) {
		this.buymoney = buymoney;
	}
	
	public Double getTotalrebate() {
		return totalrebate;
	}

	public void setTotalrebate(Double totalrebate) {
		this.totalrebate = totalrebate;
	}
	
}