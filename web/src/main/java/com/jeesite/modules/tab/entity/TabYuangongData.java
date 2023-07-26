package com.jeesite.modules.tab.entity;

import javax.validation.constraints.Size;

import com.jeesite.common.entity.DataEntity;
import com.jeesite.common.mybatis.annotation.Column;
import com.jeesite.common.mybatis.annotation.Table;
import com.jeesite.common.mybatis.mapper.query.QueryType;

/**
 * tab_yuangong_dataEntity
 * @author 1
 * @version 2022-03-22
 */
@Table(name="tab_yuangong_data", alias="a", label="tab_yuangong_data信息", columns={
		@Column(name="rowid", attrName="rowid", label="rowid", isPK=true),
		@Column(name="acccount", attrName="acccount", label="用户账号"),
		@Column(name="password", attrName="password", label="登录密码"),
		@Column(name="qtzh", attrName="qtzh", label="前台账号"),
		@Column(name="qtmm", attrName="qtmm", label="前台密码"),
		@Column(name="status1", attrName="status1", label="当前状态"),
		@Column(name="daili", attrName="daili", label="代理账号"),
		
		@Column(name="quanxian", attrName="quanxian", label="代理账号"),
		
	}, orderBy="a.rowid DESC"
)
public class TabYuangongData extends DataEntity<TabYuangongData> {
	
	private static final long serialVersionUID = 1L;
	private String rowid;		// rowid
	private String acccount;		// 用户账号
	private String password;		// 登录密码
	private String qtzh;		// 前台账号
	private String qtmm;		// 前台密码
	private String status1;		// 当前状态
	private String daili;		// 代理账号
	private String quanxian ;
	
	
	
	public String getQuanxian() {
		return quanxian;
	}

	public void setQuanxian(String quanxian) {
		this.quanxian = quanxian;
	}

	public static long getSerialversionuid() {
		return serialVersionUID;
	}

	public TabYuangongData() {
		this(null);
	}

	public TabYuangongData(String id){
		super(id);
	}
	
	public String getRowid() {
		return rowid;
	}

	public void setRowid(String rowid) {
		this.rowid = rowid;
	}
	
	@Size(min=0, max=45, message="用户账号长度不能超过 45 个字符")
	public String getAcccount() {
		return acccount;
	}

	public void setAcccount(String acccount) {
		this.acccount = acccount;
	}
	
	@Size(min=0, max=451, message="登录密码长度不能超过 451 个字符")
	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}
	
	@Size(min=0, max=451, message="前台账号长度不能超过 451 个字符")
	public String getQtzh() {
		return qtzh;
	}

	public void setQtzh(String qtzh) {
		this.qtzh = qtzh;
	}
	
	@Size(min=0, max=451, message="前台密码长度不能超过 451 个字符")
	public String getQtmm() {
		return qtmm;
	}

	public void setQtmm(String qtmm) {
		this.qtmm = qtmm;
	}
	
	@Size(min=0, max=45, message="当前状态长度不能超过 45 个字符")
	public String getStatus1() {
		return status1;
	}

	public void setStatus1(String status1) {
		this.status1 = status1;
	}
	
	@Size(min=0, max=45, message="代理账号长度不能超过 45 个字符")
	public String getDaili() {
		return daili;
	}

	public void setDaili(String daili) {
		this.daili = daili;
	}
	
}