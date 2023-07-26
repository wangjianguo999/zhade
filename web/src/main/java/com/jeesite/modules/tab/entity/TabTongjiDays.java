package com.jeesite.modules.tab.entity;

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
 * tab_tongji_daysEntity
 * @author 2
 * @version 2022-04-11
 */
@Table(name="tab_tongji_days", alias="a", label="tab_tongji_days信息", columns={
		@Column(name="rowid", attrName="rowid", label="rowid", isPK=true),
		@Column(name="shangjilink", attrName="shangjilink", label="商家 s"   ,queryType = QueryType.LIKE),
		@Column(name="days", attrName="days", label="生成日期"),
		@Column(name="zcrs", attrName="zcrs", label="注册人数", isQuery=false),
		@Column(name="zdrs", attrName="zdrs", label="做单人数", isQuery=false),
		@Column(name="czje", attrName="czje", label="充值金额", isQuery=false),
		@Column(name="czrs", attrName="czrs", label="充值人数", isQuery=false),
		@Column(name="scje", attrName="scje", label="首冲金额", isQuery=false),
		@Column(name="scrs", attrName="scrs", label="首冲人数", isQuery=false),
		@Column(name="txrs", attrName="txrs", label="提现人数", isQuery=false),
		@Column(name="txje", attrName="txje", label="提现金额", isQuery=false),
		@Column(name="cqc", attrName="cqc", label="存取查", isQuery=false),
		@Column(name="userid", attrName="userid", label="用户编号", isQuery=true),
		@Column(name="ygbh", attrName="ygbh", label="用户编号", isQuery=false),
		@Column(name="ygbh2", attrName="ygbh2", label="用户编号2", isQuery=false),
		@Column(name="parentid1", attrName="parentid1", label="一级", isQuery=false),
		@Column(name="parentid2", attrName="parentid2", label="二级", isQuery=false),
		@Column(name="parentid3", attrName="parentid3", label="三级", isQuery=false),
	}, orderBy="a.days DESC"
)
public class TabTongjiDays extends DataEntity<TabTongjiDays> {
	
	private static final long serialVersionUID = 1L;
	private String rowid;		// rowid
	private String shangjilink;		// 商家 s
	private Date days;		// 生成日期
	private Long zcrs;		// 注册人数
	private Long zdrs;		// 做单人数
	private Double czje;		// 充值金额
	private Long czrs;		// 充值人数
	private Double scje;		// 首冲金额
	private Long scrs;		// 首冲人数
	private Long txrs;		// 提现人数
	private Double txje;		// 提现金额
	private Double cqc;		// 存取查
	private String userid;		// 用户编号
	private String ygbh;		// 用户编号
	private String ygbh2;		// 用户编号2
	private String parentid1;		// 一级
	private String parentid2;		// 二级
	private String parentid3;		// 三级
	
	public TabTongjiDays() {
		this(null);
	}

	public TabTongjiDays(String id){
		super(id);
	}
	
	public String getRowid() {
		return rowid;
	}

	public void setRowid(String rowid) {
		this.rowid = rowid;
	}
	
	public String getShangjilink() {
		return shangjilink;
	}

	public void setShangjilink(String shangjilink) {
		this.shangjilink = shangjilink;
	}
	
	@JsonFormat(pattern = "yyyy-MM-dd")
	public Date getDays() {
		return days;
	}

	public void setDays(Date days) {
		this.days = days;
	}
	
	public Long getZcrs() {
		return zcrs;
	}

	public void setZcrs(Long zcrs) {
		this.zcrs = zcrs;
	}
	
	public Long getZdrs() {
		return zdrs;
	}

	public void setZdrs(Long zdrs) {
		this.zdrs = zdrs;
	}
	
	public Double getCzje() {
		return czje;
	}

	public void setCzje(Double czje) {
		this.czje = czje;
	}
	
	public Long getCzrs() {
		return czrs;
	}

	public void setCzrs(Long czrs) {
		this.czrs = czrs;
	}
	
	public Double getScje() {
		return scje;
	}

	public void setScje(Double scje) {
		this.scje = scje;
	}
	
	public Long getScrs() {
		return scrs;
	}

	public void setScrs(Long scrs) {
		this.scrs = scrs;
	}
	
	public Long getTxrs() {
		return txrs;
	}

	public void setTxrs(Long txrs) {
		this.txrs = txrs;
	}
	
	public Double getTxje() {
		return txje;
	}

	public void setTxje(Double txje) {
		this.txje = txje;
	}
	
	public Double getCqc() {
		return cqc;
	}

	public void setCqc(Double cqc) {
		this.cqc = cqc;
	}
	
	@Size(min=0, max=45, message="用户编号长度不能超过 45 个字符")
	public String getUserid() {
		return userid;
	}

	public void setUserid(String userid) {
		this.userid = userid;
	}
	
	@Size(min=0, max=45, message="用户编号长度不能超过 45 个字符")
	public String getYgbh() {
		return ygbh;
	}

	public void setYgbh(String ygbh) {
		this.ygbh = ygbh;
	}
	
	@Size(min=0, max=45, message="用户编号2长度不能超过 45 个字符")
	public String getYgbh2() {
		return ygbh2;
	}

	public void setYgbh2(String ygbh2) {
		this.ygbh2 = ygbh2;
	}
	
	@Size(min=0, max=45, message="一级长度不能超过 45 个字符")
	public String getParentid1() {
		return parentid1;
	}

	public void setParentid1(String parentid1) {
		this.parentid1 = parentid1;
	}
	
	@Size(min=0, max=45, message="二级长度不能超过 45 个字符")
	public String getParentid2() {
		return parentid2;
	}

	public void setParentid2(String parentid2) {
		this.parentid2 = parentid2;
	}
	
	@Size(min=0, max=45, message="三级长度不能超过 45 个字符")
	public String getParentid3() {
		return parentid3;
	}

	public void setParentid3(String parentid3) {
		this.parentid3 = parentid3;
	}
	
	public Date getDays_gte() {
		return sqlMap.getWhere().getValue("days", QueryType.GTE);
	}

	public void setDays_gte(Date days) {
		sqlMap.getWhere().and("days", QueryType.GTE, days);
	}
	
	public Date getDays_lte() {
		return sqlMap.getWhere().getValue("days", QueryType.LTE);
	}

	public void setDays_lte(Date days) {
		sqlMap.getWhere().and("days", QueryType.LTE, days);
	}
	
}