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
 * tab_taskj_jobEntity
 * @author 1
 * @version 2022-03-21
 */
@Table(name="tab_taskj_job", alias="a", label="tab_taskj_job信息", columns={
		@Column(name="rowid", attrName="rowid", label="rowid", isPK=true),
		@Column(name="userid", attrName="userid", label="用户"),
		@Column(name="shangjis", attrName="shangjis", label="全部上级"),
		@Column(name="status1", attrName="status1", label="状态"),
		@Column(name="createtime", attrName="createtime", label="创建时间"),
	}, orderBy="a.createtime  "
)
public class TabTaskjJob extends DataEntity<TabTaskjJob> {
	
	private static final long serialVersionUID = 1L;
	private String rowid;		// rowid
	private String userid;		// 用户
	private String shangjis;		// 全部上级
	private String status1;		// 状态
	private Date createtime;		// 创建时间
	
	public TabTaskjJob() {
		this(null);
	}

	public TabTaskjJob(String id){
		super(id);
	}
	
	public String getRowid() {
		return rowid;
	}

	public void setRowid(String rowid) {
		this.rowid = rowid;
	}
	
	@Size(min=0, max=111, message="用户长度不能超过 111 个字符")
	public String getUserid() {
		return userid;
	}

	public void setUserid(String userid) {
		this.userid = userid;
	}
	
	public String getShangjis() {
		return shangjis;
	}

	public void setShangjis(String shangjis) {
		this.shangjis = shangjis;
	}
	
	@Size(min=0, max=45, message="状态长度不能超过 45 个字符")
	public String getStatus1() {
		return status1;
	}

	public void setStatus1(String status1) {
		this.status1 = status1;
	}
	
	@JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
	public Date getCreatetime() {
		return createtime;
	}

	public void setCreatetime(Date createtime) {
		this.createtime = createtime;
	}
	
}