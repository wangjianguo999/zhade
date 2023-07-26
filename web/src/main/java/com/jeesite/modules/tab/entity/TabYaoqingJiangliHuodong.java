package com.jeesite.modules.tab.entity;

import javax.validation.constraints.Size;

import com.jeesite.common.entity.DataEntity;
import com.jeesite.common.mybatis.annotation.Column;
import com.jeesite.common.mybatis.annotation.Table;
import com.jeesite.common.mybatis.mapper.query.QueryType;

/**
 * tab_yaoqing_jiangli_huodongEntity
 * @author 3
 * @version 2022-04-21
 */
@Table(name="tab_yaoqing_jiangli_huodong", alias="a", label="tab_yaoqing_jiangli_huodong信息", columns={
		@Column(name="rowid", attrName="rowid", label="rowid", isPK=true),
		@Column(name="jlmc", attrName="jlmc", label="奖励名称"),
		@Column(name="jlbl", attrName="jlbl", label="奖励比例"),
		@Column(name="jlje", attrName="jlje", label="奖励金额"),
	}, orderBy="a.jlje ASC"
)
public class TabYaoqingJiangliHuodong extends DataEntity<TabYaoqingJiangliHuodong> {
	
	private static final long serialVersionUID = 1L;
	private String rowid;		// rowid
	private String jlmc;		// 奖励名称
	private Double jlbl;		// 奖励比例
	private Double jlje;		// 奖励金额
	
	
	
	public TabYaoqingJiangliHuodong() {
		this(null);
	}

	public TabYaoqingJiangliHuodong(String id){
		super(id);
	}
	
	public String getRowid() {
		return rowid;
	}

	public void setRowid(String rowid) {
		this.rowid = rowid;
	}
	
	@Size(min=0, max=200, message="奖励名称长度不能超过 200 个字符")
	public String getJlmc() {
		return jlmc;
	}

	public void setJlmc(String jlmc) {
		this.jlmc = jlmc;
	}
	
	public Double getJlbl() {
		return jlbl;
	}

	public void setJlbl(Double jlbl) {
		this.jlbl = jlbl;
	}
	
	public Double getJlje() {
		return jlje;
	}

	public void setJlje(Double jlje) {
		this.jlje = jlje;
	}
	
}