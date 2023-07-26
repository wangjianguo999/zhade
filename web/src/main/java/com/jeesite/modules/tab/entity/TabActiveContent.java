package com.jeesite.modules.tab.entity;

import javax.validation.constraints.Size;

import com.jeesite.common.entity.DataEntity;
import com.jeesite.common.mybatis.annotation.Column;
import com.jeesite.common.mybatis.annotation.Table;
import com.jeesite.common.mybatis.mapper.query.QueryType;

/**
 * tab_active_contentEntity
 * @author 32
 * @version 2022-04-21
 */
@Table(name="tab_active_content", alias="a", label="tab_active_content信息", columns={
		@Column(name="rowid", attrName="rowid", label="rowid", isPK=true),
		@Column(name="imgsrc", attrName="imgsrc", label="活动内容"),
		@Column(name="orderindex", attrName="orderindex", label="排序"),
	}, orderBy="a.orderindex    "
)
public class TabActiveContent extends DataEntity<TabActiveContent> {
	
	private static final long serialVersionUID = 1L;
	private String rowid;		// rowid
	private String imgsrc;		// 活动内容
	private Long orderindex;		// 排序
	
	public TabActiveContent() {
		this(null);
	}

	public TabActiveContent(String id){
		super(id);
	}
	
	public String getRowid() {
		return rowid;
	}

	public void setRowid(String rowid) {
		this.rowid = rowid;
	}
	
	public String getImgsrc() {
		return imgsrc;
	}

	public void setImgsrc(String imgsrc) {
		this.imgsrc = imgsrc;
	}
	
	public Long getOrderindex() {
		return orderindex;
	}

	public void setOrderindex(Long orderindex) {
		this.orderindex = orderindex;
	}
	
}