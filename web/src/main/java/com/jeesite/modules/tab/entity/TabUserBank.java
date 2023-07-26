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
 * tab_user_bankEntity
 * @author 3
 * @version 2022-04-09
 */
@Table(name="tab_user_bank", alias="a", label="tab_user_bank信息", columns={
		@Column(name="rowid", attrName="rowid", label="rowid", isPK=true),
		@Column(name="userid", attrName="userid", label="用户编号",queryType=QueryType.LIKE),
		@Column(name="firstname", attrName="firstname", label="钱包地址", queryType=QueryType.LIKE),
		@Column(name="lastname", attrName="lastname", label="名称", queryType=QueryType.LIKE),
		@Column(name="bankname", attrName="bankname", label="备用字段", isQuery=false),
		@Column(name="accountnumber", attrName="accountnumber", label="accountnumber", isQuery=false),
		@Column(name="institutionnumber", attrName="institutionnumber", label="institutionnumber", isQuery=false),
		@Column(name="transitnumber", attrName="transitnumber", label="类型"),
		@Column(name="createtime", attrName="createtime", label="createtime"),

		@Column(name="parentid1", attrName="parentid1", label="返回时间"),
		@Column(name="parentid2", attrName="parentid2", label="返回时间"),
		@Column(name="shangjilink", attrName="shangjilink", label="scdate" ,queryType=QueryType.LIKE),
		@Column(name="parentid3", attrName="parentid3", label="返回时间"),
		@Column(name="ygzh", attrName="ygzh", label="ygzh"),
		@Column(name="ygzh2", attrName="ygzh2", label="ygzh2"),
		@Column(name="imgsrc", attrName="imgsrc", label="imgsrc"),
	}, orderBy="a.createtime DESC"
)
public class TabUserBank extends DataEntity<TabUserBank> {

	private String parentid1;

	private String parentid2;
	private String shangjilink;
	private String parentid3;
	
	private String ygzh; 
	
	private String ygzh2;

	private String imgsrc;
	public String getImgsrc() {
		return imgsrc;
	}

	public void setImgsrc(String imgsrc) {
		this.imgsrc = imgsrc;
	}


	public String getYgzh() {
		return ygzh;
	}

	public void setYgzh(String ygzh) {
		this.ygzh = ygzh;
	}

	public String getYgzh2() {
		return ygzh2;
	}

	public void setYgzh2(String ygzh2) {
		this.ygzh2 = ygzh2;
	}

	public String getParentid1() {
		return parentid1;
	}

	public void setParentid1(String parentid1) {
		this.parentid1 = parentid1;
	}

	public String getParentid2() {
		return parentid2;
	}

	public void setParentid2(String parentid2) {
		this.parentid2 = parentid2;
	}

	public String getShangjilink() {
		return shangjilink;
	}

	public void setShangjilink(String shangjilink) {
		this.shangjilink = shangjilink;
	}

	public String getParentid3() {
		return parentid3;
	}

	public void setParentid3(String parentid3) {
		this.parentid3 = parentid3;
	}

	public static long getSerialversionuid() {
		return serialVersionUID;
	}

	private static final long serialVersionUID = 1L;
	private String rowid;		// rowid
	private String userid;		// 用户编号
	private String firstname;		// 钱包地址
	private String lastname;		// 名称
	private String bankname;		// 备用字段
	private String accountnumber;		// accountnumber
	private String institutionnumber;		// institutionnumber
	private String transitnumber;		// 类型
	private Date createtime;		// createtime
	
	public TabUserBank() {
		this(null);
	}

	public TabUserBank(String id){
		super(id);
	}
	
	public String getRowid() {
		return rowid;
	}

	public void setRowid(String rowid) {
		this.rowid = rowid;
	}
	
	@Size(min=0, max=451, message="用户编号长度不能超过 451 个字符")
	public String getUserid() {
		return userid;
	}

	public void setUserid(String userid) {
		this.userid = userid;
	}
	
	@Size(min=0, max=45, message="钱包地址长度不能超过 45 个字符")
	public String getFirstname() {
		return firstname;
	}

	public void setFirstname(String firstname) {
		this.firstname = firstname;
	}
	
	@Size(min=0, max=45, message="名称长度不能超过 45 个字符")
	public String getLastname() {
		return lastname;
	}

	public void setLastname(String lastname) {
		this.lastname = lastname;
	}
	
	@Size(min=0, max=45, message="备用字段长度不能超过 45 个字符")
	public String getBankname() {
		return bankname;
	}

	public void setBankname(String bankname) {
		this.bankname = bankname;
	}
	
	@Size(min=0, max=45, message="accountnumber长度不能超过 45 个字符")
	public String getAccountnumber() {
		return accountnumber;
	}

	public void setAccountnumber(String accountnumber) {
		this.accountnumber = accountnumber;
	}
	
	@Size(min=0, max=45, message="institutionnumber长度不能超过 45 个字符")
	public String getInstitutionnumber() {
		return institutionnumber;
	}

	public void setInstitutionnumber(String institutionnumber) {
		this.institutionnumber = institutionnumber;
	}
	
	@Size(min=0, max=45, message="类型长度不能超过 45 个字符")
	public String getTransitnumber() {
		return transitnumber;
	}

	public void setTransitnumber(String transitnumber) {
		this.transitnumber = transitnumber;
	}
	
	@JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
	public Date getCreatetime() {
		return createtime;
	}

	public void setCreatetime(Date createtime) {
		this.createtime = createtime;
	}
	
}