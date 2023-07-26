package com.jeesite.modules.tab.entity;

import java.util.Date;

import javax.validation.constraints.Size;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.jeesite.common.entity.DataEntity;
import com.jeesite.common.mybatis.annotation.Column;
import com.jeesite.common.mybatis.annotation.Table;
import com.jeesite.common.mybatis.mapper.query.QueryType;

/**
 * tab_active_imgsEntity
 * @author 额
 * @version 2022-04-21
 */
@Table(name="tab_active_imgs", alias="a", label="tab_active_imgs信息", columns={
		@Column(name="rowid", attrName="rowid", label="rowid", isPK=true),
		@Column(name="imgsrc", attrName="imgsrc", label="imgsrc"),
		@Column(name="orderindex", attrName="orderindex", label="orderindex"),
		
		@Column(name="contents", attrName="contents", label="orderindex"),

		@Column(name="yuyan", attrName="yuyan", label="orderindex"),

		@Column(name="endtime", attrName="endtime", label="endtime"),
		@Column(name="imgsrc1", attrName="imgsrc1", label="imgsrc1"),
		@Column(name="starttime", attrName="starttime", label="starttime"),
		@Column(name="rechangestr", attrName="rechangestr", label="rechangestr"),
		@Column(name="isrechange", attrName="isrechange", label="isrechange"),
		@Column(name="rechangereward", attrName="rechangereward", label="rechangereward"),
		@Column(name="rechangerewardscale", attrName="rechangerewardscale", label="rechangerewardscale"),

	}, orderBy="a.orderindex  "
)
public class TabActiveImgs extends DataEntity<TabActiveImgs> {
	
	private static final long serialVersionUID = 1L;
	private String rowid;		// rowid
	private String imgsrc;		// imgsrc
	private Long orderindex;		// orderindex
	private String contents;		// imgsrc
	private String yuyan;		// imgsrc
	private Date endtime;		// imgsrc
	private String imgsrc1;		// imgsrc
	private Date starttime;		// imgsrc
	private Integer isrechange;
	private String rechangestr;
	private String rechangereward;

	public String getRechangerewardscale() {
		return rechangerewardscale;
	}

	public void setRechangerewardscale(String rechangerewardscale) {
		this.rechangerewardscale = rechangerewardscale;
	}

	private String rechangerewardscale;
	public Date getStarttime() {
		return starttime;
	}

	public void setStarttime(Date starttime) {
		this.starttime = starttime;
	}

	public Integer getIsrechange() {
		return isrechange;
	}

	public void setIsrechange(Integer isrechange) {
		this.isrechange = isrechange;
	}

	public String getRechangestr() {
		return rechangestr;
	}

	public void setRechangestr(String rechangestr) {
		this.rechangestr = rechangestr;
	}

	public String getRechangereward() {
		return rechangereward;
	}

	public void setRechangereward(String rechangereward) {
		this.rechangereward = rechangereward;
	}


	public String getYuyan() {
		return yuyan;
	}

	public void setYuyan(String yuyan) {
		this.yuyan = yuyan;
	}
	
	@JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
	public Date getEndtime() {
		return endtime;
	}

	public void setEndtime(Date endtime) {
		this.endtime = endtime;
	}

	public String getContents() {
		return contents;
	}

	public void setContents(String contents) {
		this.contents = contents;
	}

	public static long getSerialversionuid() {
		return serialVersionUID;
	}

	public TabActiveImgs() {
		this(null);
	}

	public TabActiveImgs(String id){
		super(id);
	}
	
	public String getRowid() {
		return rowid;
	}

	public void setRowid(String rowid) {
		this.rowid = rowid;
	}
	
	@Size(min=0, max=200, message="imgsrc长度不能超过 200 个字符")
	public String getImgsrc() {
		return imgsrc;
	}

	public void setImgsrc(String imgsrc) {
		this.imgsrc = imgsrc;
	}


	@Size(min=0, max=200, message="imgsrc长度不能超过 200 个字符")
	public String getImgsrc1() {
		return imgsrc1;
	}

	public void setImgsrc1(String imgsrc1) {
		this.imgsrc1 = imgsrc1;
	}

	public Long getOrderindex() {
		return orderindex;
	}

	public void setOrderindex(Long orderindex) {
		this.orderindex = orderindex;
	}
	
}