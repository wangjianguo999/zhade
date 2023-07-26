package com.jeesite.modules.tab.entity;

import javax.validation.constraints.Size;

import com.jeesite.common.entity.DataEntity;
import com.jeesite.common.mybatis.annotation.Column;
import com.jeesite.common.mybatis.annotation.Table;
import com.jeesite.common.mybatis.mapper.query.QueryType;

/**
 * tab_sys_skfsEntity
 * @author 1
 * @version 2021-12-23
 */
@Table(name="tab_sys_skfs", alias="a", label="tab_sys_skfs信息", columns={
		@Column(name="rowid", attrName="rowid", label="rowid", isPK=true),
		@Column(name="btc", attrName="btc", label="btc"),
		@Column(name="eth", attrName="eth", label="eth"),
		@Column(name="usdt", attrName="usdt", label="usdt"),
		
		@Column(name="txsfx", attrName="txsfx", label="txsfx"),

	}, orderBy="a.rowid DESC"
)
public class TabSysSkfs extends DataEntity<TabSysSkfs> {
	
	private static final long serialVersionUID = 1L;
	private String rowid;		// rowid
	private String btc;		// btc
	private String eth;		// eth
	private String usdt;		// usdt
	private Double  txsfx  ; 
	
	
	
	
	public Double getTxsfx() {
		return txsfx;
	}

	public void setTxsfx(Double txsfx) {
		this.txsfx = txsfx;
	}

	public static long getSerialversionuid() {
		return serialVersionUID;
	}

	public TabSysSkfs() {
		this(null);
	}

	public TabSysSkfs(String id){
		super(id);
	}
	
	public String getRowid() {
		return rowid;
	}

	public void setRowid(String rowid) {
		this.rowid = rowid;
	}
	
	@Size(min=0, max=451, message="btc长度不能超过 451 个字符")
	public String getBtc() {
		return btc;
	}

	public void setBtc(String btc) {
		this.btc = btc;
	}
	
	@Size(min=0, max=451, message="eth长度不能超过 451 个字符")
	public String getEth() {
		return eth;
	}

	public void setEth(String eth) {
		this.eth = eth;
	}
	
	@Size(min=0, max=451, message="usdt长度不能超过 451 个字符")
	public String getUsdt() {
		return usdt;
	}

	public void setUsdt(String usdt) {
		this.usdt = usdt;
	}
	
}