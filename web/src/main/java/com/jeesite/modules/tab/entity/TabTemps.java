package com.jeesite.modules.tab.entity;

import javax.validation.constraints.Size;

import com.jeesite.common.entity.DataEntity;
import com.jeesite.common.mybatis.annotation.Column;
import com.jeesite.common.mybatis.annotation.Table;
import com.jeesite.common.mybatis.mapper.query.QueryType;

/**
 * tab_tempsEntity
 * @author 32
 * @version 2022-04-21
 */
@Table(name="tab_temps", alias="a", label="tab_temps信息", columns={
		@Column(name="rowid", attrName="rowid", label="rowid", isPK=true),
		@Column(name="uuu", attrName="uuu", label="uuu"),
	}, orderBy="a.rowid DESC"
)
public class TabTemps extends DataEntity<TabTemps> {
	
	private static final long serialVersionUID = 1L;
	private String rowid;		// rowid
	private String uuu;		// uuu
	
	public TabTemps() {
		this(null);
	}

	public TabTemps(String id){
		super(id);
	}
	
	public String getRowid() {
		return rowid;
	}

	public void setRowid(String rowid) {
		this.rowid = rowid;
	}
	
	@Size(min=0, max=45, message="uuu长度不能超过 45 个字符")
	public String getUuu() {
		return uuu;
	}

	public void setUuu(String uuu) {
		this.uuu = uuu;
	}
	
}