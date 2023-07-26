package com.jeesite.modules.tab.entity;

import java.util.Date;

import javax.validation.constraints.Size;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.jeesite.common.entity.DataEntity;
import com.jeesite.common.mybatis.annotation.Column;
import com.jeesite.common.mybatis.annotation.Table;
import com.jeesite.common.mybatis.mapper.query.QueryType;

/**
 * tab_licai_listEntity
 * @author 32
 * @version 2022-06-15
 */
@Table(name="tab_licai_list", alias="a", label="tab_licai_list信息", columns={
		@Column(name="rowid", attrName="rowid", label="rowid", isPK=true),
		@Column(name="days", attrName="days", label="天数"),
		@Column(name="bfb", attrName="bfb", label="百分比"),
		@Column(name="zxje", attrName="zxje", label="最小金额"),
		@Column(name="orderindex", attrName="orderindex", label="排序"),
	}, orderBy="a.orderindex"
)
public class TabLicaiList extends DataEntity<TabLicaiList> {
	
	private static final long serialVersionUID = 1L;
	private String rowid;		// rowid
	private String days;		// 天数
	private String bfb;		// 百分比
	private Double zxje;		// 最小金额
	private Integer orderindex;		// 排序
	private Date date; 
	
	
	@JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
	public Date getDate() {
		return date;
	}

	public void setDate(Date date) {
		this.date = date;
	}

	public static long getSerialversionuid() {
		return serialVersionUID;
	}

	public TabLicaiList() {
		this(null);
	}

	public TabLicaiList(String id){
		super(id);
	}
	
	public String getRowid() {
		return rowid;
	}

	public void setRowid(String rowid) {
		this.rowid = rowid;
	}
	
	@Size(min=0, max=100, message="天数长度不能超过 100 个字符")
	public String getDays() {
		return days;
	}

	public void setDays(String days) {
		this.days = days;
	}
	
	@Size(min=0, max=100, message="百分比长度不能超过 100 个字符")
	public String getBfb() {
		return bfb;
	}

	public void setBfb(String bfb) {
		this.bfb = bfb;
	}
	
	public Double getZxje() {
		return zxje;
	}

	public void setZxje(Double zxje) {
		this.zxje = zxje;
	}
	
	public Integer getOrderindex() {
		return orderindex;
	}

	public void setOrderindex(Integer orderindex) {
		this.orderindex = orderindex;
	}
	
}