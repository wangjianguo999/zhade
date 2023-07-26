package com.jeesite.modules.tab.entity;


import com.jeesite.common.entity.DataEntity;
import com.jeesite.common.mybatis.annotation.Column;
import com.jeesite.common.mybatis.annotation.Table;
import com.jeesite.common.mybatis.mapper.query.QueryType;

/**
 * tab_fenxiao_configEntity
 * @author 32
 * @version 2022-03-18
 */
@Table(name="tab_fenxiao_config", alias="a", label="tab_fenxiao_config信息", columns={
		@Column(name="rowid", attrName="rowid", label="rowid", isPK=true),
		@Column(name="leval1", attrName="leval1", label="一级利润"),
		@Column(name="leval2", attrName="leval2", label="二级利润"),
		@Column(name="leval3", attrName="leval3", label="三级利润"),
	}, orderBy="a.rowid DESC"
)
public class TabFenxiaoConfig extends DataEntity<TabFenxiaoConfig> {
	
	private static final long serialVersionUID = 1L;
	private String rowid;		// rowid
	private Integer leval1;		// 一级利润
	private Integer leval2;		// 二级利润
	private Integer leval3;		// 三级利润
	
	public TabFenxiaoConfig() {
		this(null);
	}

	public TabFenxiaoConfig(String id){
		super(id);
	}
	
	public String getRowid() {
		return rowid;
	}

	public void setRowid(String rowid) {
		this.rowid = rowid;
	}
	
	public Integer getLeval1() {
		return leval1;
	}

	public void setLeval1(Integer leval1) {
		this.leval1 = leval1;
	}
	
	public Integer getLeval2() {
		return leval2;
	}

	public void setLeval2(Integer leval2) {
		this.leval2 = leval2;
	}
	
	public Integer getLeval3() {
		return leval3;
	}

	public void setLeval3(Integer leval3) {
		this.leval3 = leval3;
	}
	
}