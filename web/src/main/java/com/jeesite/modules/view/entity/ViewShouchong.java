package com.jeesite.modules.view.entity;

import java.util.Date;
import com.jeesite.common.mybatis.annotation.JoinTable;
import com.jeesite.common.mybatis.annotation.JoinTable.Type;
import com.fasterxml.jackson.annotation.JsonFormat;
import javax.validation.constraints.Size;

import com.jeesite.common.entity.DataEntity;
import com.jeesite.common.mybatis.annotation.Column;
import com.jeesite.common.mybatis.annotation.Table;
import com.jeesite.common.mybatis.mapper.query.QueryType;

/**
 * VIEWEntity
 * @author 22
 * @version 2022-03-20
 */
@Table(name="view_shouchong", alias="a", label="VIEW信息", columns={
		@Column(name="updatetime", attrName="updatetime", label="返回时间", isPK=true),
		@Column(name="userid", attrName="userid", label="用户编号"),
		@Column(name="shangjilink", attrName="shangjilink", label="shangjilink"),
		@Column(name="amont", attrName="amont", label="充值金额"),
	}, orderBy="a.updatetime DESC"
)
public class ViewShouchong extends DataEntity<ViewShouchong> {
	
	private static final long serialVersionUID = 1L;
	private Date updatetime;		// 返回时间
	private String userid;		// 用户编号
	private String shangjilink;		// shangjilink
	private Double amont;		// 充值金额
	
	public ViewShouchong() {
		this(null);
	}

	public ViewShouchong(String id){
		super(id);
	}
	
	@JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
	public Date getUpdatetime() {
		return updatetime;
	}

	public void setUpdatetime(Date updatetime) {
		this.updatetime = updatetime;
	}
	
	@Size(min=0, max=45, message="用户编号长度不能超过 45 个字符")
	public String getUserid() {
		return userid;
	}

	public void setUserid(String userid) {
		this.userid = userid;
	}
	
	public String getShangjilink() {
		return shangjilink;
	}

	public void setShangjilink(String shangjilink) {
		this.shangjilink = shangjilink;
	}
	
	public Double getAmont() {
		return amont;
	}

	public void setAmont(Double amont) {
		this.amont = amont;
	}
	
}