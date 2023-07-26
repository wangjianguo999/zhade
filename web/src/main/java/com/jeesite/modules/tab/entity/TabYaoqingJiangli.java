package com.jeesite.modules.tab.entity;

import javax.validation.constraints.Size;

import com.jeesite.common.entity.DataEntity;
import com.jeesite.common.mybatis.annotation.Column;
import com.jeesite.common.mybatis.annotation.Table;
import com.jeesite.common.mybatis.mapper.query.QueryType;

/**
 * tab_yaoqing_jiangliEntity
 * @author 1
 * @version 2022-04-06
 */
@Table(name="tab_yaoqing_jiangli", alias="a", label="tab_yaoqing_jiangli信息", columns={
		@Column(name="rowid", attrName="rowid", label="rowid", isPK=true),
		@Column(name="jlbh", attrName="jlbh", label="奖励编号"),
		@Column(name="xjrs", attrName="xjrs", label="下级人数"),
		@Column(name="jlje", attrName="jlje", label="奖励金额"),
	}, orderBy="a.xjrs ASC"
)
public class TabYaoqingJiangli extends DataEntity<TabYaoqingJiangli> {
	
	private static final long serialVersionUID = 1L;
	private String rowid;		// rowid
	private String jlbh;		// 奖励编号
	private Long xjrs;		// 下级人数
	private Double jlje;		// 奖励金额
	
	public TabYaoqingJiangli() {
		this(null);
	}

	public TabYaoqingJiangli(String id){
		super(id);
	}
	
	public String getRowid() {
		return rowid;
	}

	public void setRowid(String rowid) {
		this.rowid = rowid;
	}
	
	@Size(min=0, max=451, message="奖励编号长度不能超过 451 个字符")
	public String getJlbh() {
		return jlbh;
	}

	public void setJlbh(String jlbh) {
		this.jlbh = jlbh;
	}
	
	public Long getXjrs() {
		return xjrs;
	}

	public void setXjrs(Long xjrs) {
		this.xjrs = xjrs;
	}
	
	public Double getJlje() {
		return jlje;
	}

	public void setJlje(Double jlje) {
		this.jlje = jlje;
	}
	
}