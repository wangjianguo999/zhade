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
 * tab_huilv_configEntity
 * @author 11
 * @version 2021-12-24
 */
@Table(name="tab_huilv_config", alias="a", label="tab_huilv_config信息", columns={
		@Column(name="rowid", attrName="rowid", label="rowid", isPK=true),
		@Column(name="types", attrName="types", label="类型"),
		@Column(name="cad", attrName="cad", label="加元"),
		@Column(name="updatetime", attrName="updatetime", label="更新时间"),
	}, orderBy="a.rowid DESC"
)
public class TabHuilvConfig extends DataEntity<TabHuilvConfig> {
	
	private static final long serialVersionUID = 1L;
	private String rowid;		// rowid
	private String types;		// 类型
	private String cad;		// 加元
	private Date updatetime;		// 更新时间
	
	public TabHuilvConfig() {
		this(null);
	}

	public TabHuilvConfig(String id){
		super(id);
	}
	
	public String getRowid() {
		return rowid;
	}

	public void setRowid(String rowid) {
		this.rowid = rowid;
	}
	
	@Size(min=0, max=100, message="类型长度不能超过 100 个字符")
	public String getTypes() {
		return types;
	}

	public void setTypes(String types) {
		this.types = types;
	}
	
	@Size(min=0, max=451, message="加元长度不能超过 451 个字符")
	public String getCad() {
		return cad;
	}

	public void setCad(String cad) {
		this.cad = cad;
	}
	
	@JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
	public Date getUpdatetime() {
		return updatetime;
	}

	public void setUpdatetime(Date updatetime) {
		this.updatetime = updatetime;
	}
	
}