package com.jeesite.modules.tab.entity;

import javax.validation.constraints.Size;

import com.jeesite.common.entity.DataEntity;
import com.jeesite.common.mybatis.annotation.Column;
import com.jeesite.common.mybatis.annotation.Table;
import com.jeesite.common.mybatis.mapper.query.QueryType;

/**
 * tab_yaoqing_peizhiEntity
 * @author 1
 * @version 2022-04-23
 */
@Table(name="tab_yaoqing_peizhi", alias="a", label="tab_yaoqing_peizhi信息", columns={
		@Column(name="rowid", attrName="rowid", label="rowid", isPK=true),
		@Column(name="content", attrName="content", label="奖励内容"),
		@Column(name="orderindex", attrName="orderindex", label="排序"),
	}, orderBy="a.orderindex  "
)
public class TabYaoqingPeizhi extends DataEntity<TabYaoqingPeizhi> {
	
	private static final long serialVersionUID = 1L;
	private String rowid;		// rowid
	private String content;		// 奖励内容
	private Long orderindex;		// 排序
	
	public TabYaoqingPeizhi() {
		this(null);
	}

	public TabYaoqingPeizhi(String id){
		super(id);
	}
	
	public String getRowid() {
		return rowid;
	}

	public void setRowid(String rowid) {
		this.rowid = rowid;
	}
	
	@Size(min=0, max=1000, message="奖励内容长度不能超过 1000 个字符")
	public String getContent() {
		return content;
	}

	public void setContent(String content) {
		this.content = content;
	}
	
	public Long getOrderindex() {
		return orderindex;
	}

	public void setOrderindex(Long orderindex) {
		this.orderindex = orderindex;
	}
	
}