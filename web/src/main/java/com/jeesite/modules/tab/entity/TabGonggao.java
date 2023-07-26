package com.jeesite.modules.tab.entity;

import javax.validation.constraints.Size;

import com.jeesite.common.entity.DataEntity;
import com.jeesite.common.mybatis.annotation.Column;
import com.jeesite.common.mybatis.annotation.Table;
import com.jeesite.common.mybatis.mapper.query.QueryType;

/**
 * tab_gonggaoEntity
 * @author 1
 * @version 2022-04-10
 */
@Table(name="tab_gonggao", alias="a", label="tab_gonggao信息", columns={
		@Column(name="rowid", attrName="rowid", label="rowid", isPK=true),
		@Column(name="content", attrName="content", label="公告"),
 		@Column(name="yuyan", attrName="yuyan", label="yuyan"),
		@Column(name="imgsrc", attrName="imgsrc", label="imgsrc"),
	}, orderBy="a.rowid DESC"
)
public class TabGonggao extends DataEntity<TabGonggao> {
	
	private static final long serialVersionUID = 1L;
	private String rowid;		// rowid
	private String content;		// 公告
	private String yuyan;
	private String imgsrc;
	public String getImgsrc() {
		return imgsrc;
	}

	public void setImgsrc(String imgsrc) {
		this.imgsrc = imgsrc;
	}

	public String getYuyan() {
		return yuyan;
	}

	public void setYuyan(String yuyan) {
		this.yuyan = yuyan;
	}

	public static long getSerialversionuid() {
		return serialVersionUID;
	}

	public TabGonggao() {
		this(null);
	}

	public TabGonggao(String id){
		super(id);
	}
	
	public String getRowid() {
		return rowid;
	}

	public void setRowid(String rowid) {
		this.rowid = rowid;
	}
	
 	public String getContent() {
		return content;
	}

	public void setContent(String content) {
		this.content = content;
	}
	
}