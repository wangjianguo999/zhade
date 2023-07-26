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
 * tab_xiadan_success_tongjiEntity
 * @author 1
 * @version 2022-04-05
 */
@Table(name="tab_xiadan_success_tongji", alias="a", label="tab_xiadan_success_tongji信息", columns={
		@Column(name="rowidva", attrName="rowidva", label="rowidva", isPK=true),
		@Column(name="userid", attrName="userid", label="用户编号"),
		@Column(name="money", attrName="money", label="充值总金额"),
		@Column(name="createtime", attrName="createtime", label="生成时间"),
		@Column(name="shangjilink", attrName="shangjilink", label="上级"),
	}, orderBy="a.rowidva DESC"
)
public class TabXiadanSuccessTongji extends DataEntity<TabXiadanSuccessTongji> {
	
	private static final long serialVersionUID = 1L;
	private String rowidva;		// rowidva
	private String userid;		// 用户编号
	private Double money;		// 充值总金额
	private Date createtime;		// 生成时间
	private String shangjilink;		// 上级
	
	public TabXiadanSuccessTongji() {
		this(null);
	}

	public TabXiadanSuccessTongji(String id){
		super(id);
	}
	
	public String getRowidva() {
		return rowidva;
	}

	public void setRowidva(String rowidva) {
		this.rowidva = rowidva;
	}
	
	@Size(min=0, max=11, message="用户编号长度不能超过 11 个字符")
	public String getUserid() {
		return userid;
	}

	public void setUserid(String userid) {
		this.userid = userid;
	}
	
	public Double getMoney() {
		return money;
	}

	public void setMoney(Double money) {
		this.money = money;
	}
	
	@JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
	public Date getCreatetime() {
		return createtime;
	}

	public void setCreatetime(Date createtime) {
		this.createtime = createtime;
	}
	
	public String getShangjilink() {
		return shangjilink;
	}

	public void setShangjilink(String shangjilink) {
		this.shangjilink = shangjilink;
	}
	
}