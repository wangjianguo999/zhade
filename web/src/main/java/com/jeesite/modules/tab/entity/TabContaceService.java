package com.jeesite.modules.tab.entity;

import javax.validation.constraints.Size;

import com.jeesite.common.entity.DataEntity;
import com.jeesite.common.mybatis.annotation.Column;
import com.jeesite.common.mybatis.annotation.Table;
import com.jeesite.common.mybatis.mapper.query.QueryType;

/**
 * tab_contace_serviceEntity
 * @author 3
 * @version 2021-12-10
 */
@Table(name="tab_contace_service", alias="a", label="tab_contace_service信息", columns={
		@Column(name="rowid", attrName="rowid", label="rowid", isPK=true),
		@Column(name="whatsapp", attrName="whatsapp", label="whatsapp"),
	}, orderBy="a.rowid DESC"
)
public class TabContaceService extends DataEntity<TabContaceService> {
	
	private static final long serialVersionUID = 1L;
	private String rowid;		// rowid
	private String whatsapp;		// whatsapp
	
	public TabContaceService() {
		this(null);
	}

	public TabContaceService(String id){
		super(id);
	}
	
	public String getRowid() {
		return rowid;
	}

	public void setRowid(String rowid) {
		this.rowid = rowid;
	}
	
	@Size(min=0, max=451, message="whatsapp长度不能超过 451 个字符")
	public String getWhatsapp() {
		return whatsapp;
	}

	public void setWhatsapp(String whatsapp) {
		this.whatsapp = whatsapp;
	}
	
}