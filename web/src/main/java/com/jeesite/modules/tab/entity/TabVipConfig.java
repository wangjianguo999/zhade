package com.jeesite.modules.tab.entity;

import javax.validation.constraints.Size;

import com.jeesite.common.entity.DataEntity;
import com.jeesite.common.mybatis.annotation.Column;
import com.jeesite.common.mybatis.annotation.Table;
import com.jeesite.common.mybatis.mapper.query.QueryType;

/**
 * tab_vip_configEntity
 * @author 1
 * @version 2021-12-11
 */
@Table(name="tab_vip_config", alias="a", label="tab_vip_config信息", columns={
		@Column(name="rowid", attrName="rowid", label="rowid", isPK=true),
		@Column(name="vip", attrName="vip", label="vip"),
		@Column(name="name", attrName="name", label="名称", queryType=QueryType.LIKE),
		@Column(name="currentmoney", attrName="currentmoney", label="当前余额"),
		@Column(name="xiaji", attrName="xiaji", label="下级人数"),
		@Column(name="xiajijine", attrName="xiajijine", label="下级金额"),
		@Column(name="mgrwdyj", attrName="mgrwdyj", label="每个任务的佣金"),
		@Column(name="mrlr", attrName="mrlr", label="每日利润"),
		
		@Column(name="icon", attrName="icon", label="icon"),
		
		@Column(name="maxgoumai", attrName="maxgoumai", label="每日最大做单数量"),

		@Column(name="maxmoney", attrName="maxmoney", label="不需要下级数量的金额"),
		
		@Column(name="tishi", attrName="tishi", label="每日利润"),


	}, orderBy="a.vip ASC"
)
public class TabVipConfig extends DataEntity<TabVipConfig> {
	private String icon ; 
	
	private Double  maxgoumai ;
	
	private String tishi; 
	
	
	
	public String getTishi() {
		return tishi;
	}

	public void setTishi(String tishi) {
		this.tishi = tishi;
	}

	public Double getMaxgoumai() {
		return maxgoumai;
	}

	public void setMaxgoumai(Double maxgoumai) {
		this.maxgoumai = maxgoumai;
	}

	public String getIcon() {
		return icon;
	}

	public void setIcon(String icon) {
		this.icon = icon;
	}

	public static long getSerialversionuid() {
		return serialVersionUID;
	}

	private static final long serialVersionUID = 1L;
	private String rowid;		// rowid
	private String vip;		// vip
	private String name;		// 名称
	private Double currentmoney;		// 当前余额
	private Long xiaji;		// 下级人数
	private Double xiajijine;		// 下级金额
	private Double mgrwdyj;		// 每个任务的佣金
	private Double mrlr;		// 每日利润

	private Double maxmoney;

	public Double getMaxmoney() {
		return maxmoney;
	}

	public void setMaxmoney(Double maxmoney) {
		this.maxmoney = maxmoney;
	}

	public TabVipConfig() {
		this(null);
	}

	public TabVipConfig(String id){
		super(id);
	}
	
	public String getRowid() {
		return rowid;
	}

	public void setRowid(String rowid) {
		this.rowid = rowid;
	}
	
	@Size(min=0, max=45, message="vip长度不能超过 45 个字符")
	public String getVip() {
		return vip;
	}

	public void setVip(String vip) {
		this.vip = vip;
	}
	
	@Size(min=0, max=45, message="名称长度不能超过 45 个字符")
	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}
	
	public Double getCurrentmoney() {
		return currentmoney;
	}

	public void setCurrentmoney(Double currentmoney) {
		this.currentmoney = currentmoney;
	}
	
	public Long getXiaji() {
		return xiaji;
	}

	public void setXiaji(Long xiaji) {
		this.xiaji = xiaji;
	}
	
	public Double getXiajijine() {
		return xiajijine;
	}

	public void setXiajijine(Double xiajijine) {
		this.xiajijine = xiajijine;
	}
	
	public Double getMgrwdyj() {
		return mgrwdyj;
	}

	public void setMgrwdyj(Double mgrwdyj) {
		this.mgrwdyj = mgrwdyj;
	}
	
	public Double getMrlr() {
		return mrlr;
	}

	public void setMrlr(Double mrlr) {
		this.mrlr = mrlr;
	}
	
}