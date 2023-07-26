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
 * tab_valid_codeEntity
 * @author 3
 * @version 2021-12-10
 */
@Table(name="tab_valid_code", alias="a", label="tab_valid_code信息", columns={
		@Column(name="rowid", attrName="rowid", label="rowid", isPK=true),
		@Column(name="phone", attrName="phone", label="phone"),
		@Column(name="createtime", attrName="createtime", label="createtime"),
		@Column(name="validate_time", attrName="validateTime", label="validate_time"),
		@Column(name="flag", attrName="flag", label="flag"),
		@Column(name="code", attrName="code", label="code"),
	}, orderBy="a.createtime DESC"
)
public class TabValidCode extends DataEntity<TabValidCode> {
	
	private static final long serialVersionUID = 1L;
	private String rowid;		// rowid
	private String phone;		// phone
	private Date createtime;		// createtime
	private Date validateTime;		// validate_time
	private String flag;		// flag
	private String code;		// code
	
	public TabValidCode() {
		this(null);
	}

	public TabValidCode(String id){
		super(id);
	}
	
	public String getRowid() {
		return rowid;
	}

	public void setRowid(String rowid) {
		this.rowid = rowid;
	}
	
	@Size(min=0, max=45, message="phone长度不能超过 45 个字符")
	public String getPhone() {
		return phone;
	}

	public void setPhone(String phone) {
		this.phone = phone;
	}
	
	@JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
	public Date getCreatetime() {
		return createtime;
	}

	public void setCreatetime(Date createtime) {
		this.createtime = createtime;
	}
	
	@JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
	public Date getValidateTime() {
		return validateTime;
	}

	public void setValidateTime(Date validateTime) {
		this.validateTime = validateTime;
	}
	
	@Size(min=0, max=45, message="flag长度不能超过 45 个字符")
	public String getFlag() {
		return flag;
	}

	public void setFlag(String flag) {
		this.flag = flag;
	}
	
	@Size(min=0, max=45, message="code长度不能超过 45 个字符")
	public String getCode() {
		return code;
	}

	public void setCode(String code) {
		this.code = code;
	}
	
}