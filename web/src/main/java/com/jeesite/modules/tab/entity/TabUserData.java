package com.jeesite.modules.tab.entity;

import javax.validation.constraints.Size;
import java.math.BigDecimal;
import java.util.Date;
import com.jeesite.common.mybatis.annotation.JoinTable;
import com.jeesite.common.mybatis.annotation.JoinTable.Type;
import com.fasterxml.jackson.annotation.JsonFormat;

import com.jeesite.common.entity.DataEntity;
import com.jeesite.common.mybatis.annotation.Column;
import com.jeesite.common.mybatis.annotation.Table;
import com.jeesite.common.mybatis.mapper.query.QueryType;

/**
 * tab_user_dataEntity
 * @author 1
 * @version 2021-12-14
 */
@Table(name="tab_user_data", alias="a", label="tab_user_data信息", columns={
		@Column(name="rowid", attrName="rowid", label="rowid", isPK=true,queryType=QueryType.LIKE),
		@Column(name="username", attrName="username", label="账号"),
		@Column(name="email", attrName="email", label="邮箱"),
		@Column(name="password", attrName="password", label="密码"),
		@Column(name="currentmoney", attrName="currentmoney", label="当前余额"),
		@Column(name="totalmoney", attrName="totalmoney", label="总金额"),
		@Column(name="codes", attrName="codes", label="邀请码"),
		@Column(name="createtime", attrName="createtime", label="创建时间"),
		@Column(name="parentid", attrName="parentid", label="第一级"),
		@Column(name="parentid1", attrName="parentid1", label="第二级"),
		@Column(name="parenti3", attrName="parenti3", label="第三极"),
		@Column(name="huilv", attrName="huilv", label="汇率"),
		@Column(name="sum_balance", attrName="sumBalance", label="sum_balance"),
		@Column(name="sum_member", attrName="sumMember", label="sum_member"),
		@Column(name="sum_rebate", attrName="sumRebate", label="sum_rebate"),
		@Column(name="vip", attrName="vip", label="VIP等级"),
		@Column(name="tycs", attrName="tycs", label="体验次数"),
		@Column(name="accesstoken", attrName="accesstoken", label="accesstoken"),
		@Column(name="etransferaccount", attrName="etransferaccount", label="etransferaccount"),
		@Column(name="etransfername", attrName="etransfername", label="etransfername"),
		@Column(name="digitaltype", attrName="digitaltype", label="digitaltype"),
		@Column(name="digitaladdress", attrName="digitaladdress", label="digitaladdress"),
		@Column(name="sex", attrName="sex", label="sex"),
		@Column(name="imgsrc", attrName="imgsrc", label="imgsrc"),
		@Column(name="birthday", attrName="birthday", label="birthday"),
		
		@Column(name="nationality", attrName="nationality", label="nationality"),
		
		@Column(name="userid", attrName="userid", label="userid"),
		
		
		@Column(name="txpassword", attrName="txpassword", label="userid"),
		@Column(name="shangjilink", attrName="shangjilink", label="scdate" ,queryType=QueryType.LIKE),

		@Column(name="userleval", attrName="userleval", label="userleval"),

		@Column(name="ips", attrName="ips", label="ips"),

		@Column(name="sysuserid", attrName="sysuserid", label="sysuserid"),
		
		
		
		@Column(name="istiyan", attrName="istiyan", label="istiyan"),

		
		@Column(name="validate", attrName="validate", label="validate"),
		
		@Column(name="shangji2s", attrName="shangji2s", label="shangji2s"),

		
		
		@Column(name="status1", attrName="status1", label="status1"),
		
		@Column(name="quanxian", attrName="quanxian", label="quanxian"),
		
		
		@Column(name="usertype", attrName="usertype", label="usertype"),
		@Column(name="lastlogintime", attrName="lastlogintime", label="lastlogintime"),
		@Column(name="ygzh", attrName="ygzh", label="ygzh"),
		@Column(name="tyj", attrName="tyj", label="tyj"),
		@Column(name="ygzh2", attrName="ygzh2", label="ygzh2"),
		
		@Column(name="czje", attrName="czje", label="czje"),
		@Column(name="txje", attrName="txje", label="txje"),
		@Column(name="grsy", attrName="grsy", label="grsy"),
		@Column(name="tdsy", attrName="tdsy", label="tdsy"),
		@Column(name="registerip", attrName="registerip", label="registerip"),
		@Column(name="lastloginip", attrName="lastloginip", label="lastloginip"),
		@Column(name="status2", attrName="status2", label="status2"),
		@Column(name="status3", attrName="status3", label="status3"),
		@Column(name="jianglicishu", attrName="jianglicishu", label="status3"),
		@Column(name="xdzt1", attrName="xdzt1", label="xdzt1"),
		@Column(name="iszuodan", attrName="iszuodan", label="iszuodan"),
	}, orderBy="a.createtime DESC"
)
public class TabUserData extends DataEntity<TabUserData> {
private String ygzh2; 
	
	private String  xdzt ;
	private Integer  xdzt1 ;



	private String  registerip ;
	private String  lastloginip ;
	private Integer  status2 ;
	private String  status3 ;
	private Double   czje;
	private Double   txje;
	private Double   grsy;
	private Double   tdsy;
	private String zpisqidong;
	private Integer iszuodan;

	public Integer getIszuodan() {
		return iszuodan;
	}

	public void setIszuodan(Integer iszuodan) {
		this.iszuodan = iszuodan;
	}

	private Integer jianglicishu;


	public Integer getXdzt1() {
		return xdzt1;
	}
	public void setXdzt1(Integer xdzt1) {
		this.xdzt1 = xdzt1;
	}

	public Integer getJianglicishu() {
		return jianglicishu;
	}
	public void setJianglicishu(Integer jianglicishu) {
		this.jianglicishu = jianglicishu;
	}

	public String getZpisqidong() {
		return zpisqidong;
	}
	public void setZpisqidong(String zpisqidong) {
		this.zpisqidong = zpisqidong;
	}
	public String getLastloginip() {
		return lastloginip;
	}

	public void setLastloginip(String lastloginip) {
		this.lastloginip = lastloginip;
	}

	public Integer getStatus2() {
		return status2;
	}

	public void setStatus2(Integer status2) {
		this.status2 = status2;
	}
	public String getStatus3() {
		return status3;
	}

	public void setStatus3(String status3) {
		this.status3 = status3;
	}


	public String getRegisterip() {
		return registerip;
	}

	public void setRegisterip(String registerip) {
		this.registerip = registerip;
	}



	public Double getCzje() {
		return czje;
	}

	public void setCzje(Double czje) {
		this.czje = czje;
	}

	public Double getGrsy() {
		return grsy;
	}

	public void setGrsy(Double grsy) {
		this.grsy = grsy;
	}

	public Double getTdsy() {
		return tdsy;
	}

	public void setTdsy(Double tdsy) {
		this.tdsy = tdsy;
	}

	public String getXdzt() {
		return xdzt;
	}

	public void setXdzt(String xdzt) {
		this.xdzt = xdzt;
	}

	public String getYgzh2() {
		return ygzh2;
	}

	public void setYgzh2(String ygzh2) {
		this.ygzh2 = ygzh2;
	}
	private static final long serialVersionUID = 1L;
	private String rowid;		// rowid
	private String nationality;		// rowid
	
	private Double jrtdtxje; 
	private Double totalTdtxje;
	private String status1 ;
	private String quanxian ;
	private String ygzh; 
	private String  usertype ; 
	
	private Date  lastlogintime ; 
	
	private Double tyj;  
	
	
	
	
	
	public Double getTyj() {
		return tyj;
	}

	public void setTyj(Double tyj) {
		this.tyj = tyj;
	}

	public String getYgzh() {
		return ygzh;
	}

	public void setYgzh(String ygzh) {
		this.ygzh = ygzh;
	}

	public String getUsertype() {
		return usertype;
	}

	public void setUsertype(String usertype) {
		this.usertype = usertype;
	}
	
	
	@JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
	public Date getLastlogintime() {
		return lastlogintime;
	}

	public void setLastlogintime(Date lastlogintime) {
		this.lastlogintime = lastlogintime;
	}

	public String getQuanxian() {
		return quanxian;
	}

	public void setQuanxian(String quanxian) {
		this.quanxian = quanxian;
	}

	public String getStatus1() {
		return status1;
	}

	public void setStatus1(String status1) {
		this.status1 = status1;
	}


	private Long  jrxjczrs; 

	private String istiyan; 
	
	private  Date validate ;
	
	private String  shangji2s; 
	
	
	
	public String getShangji2s() {
		return shangji2s;
	}

	public void setShangji2s(String shangji2s) {
		this.shangji2s = shangji2s;
	}

	@JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
	public Date getValidate() {
		return validate;
	}

	public void setValidate(Date validate) {
		this.validate = validate;
	}

	public String getIstiyan() {
		return istiyan;
	}

	public void setIstiyan(String istiyan) {
		this.istiyan = istiyan;
	}


	private String ips; 
	
	private String sysuserid; 
	
	
	

public String getIps() {
		return ips;
	}

	public void setIps(String ips) {
		this.ips = ips;
	}

	public String getSysuserid() {
		return sysuserid;
	}

	public void setSysuserid(String sysuserid) {
		this.sysuserid = sysuserid;
	}


private Long totalXjczrs; 


	public Long getTotalXjczrs() {
	return totalXjczrs;
}

public void setTotalXjczrs(Long totalXjczrs) {
	this.totalXjczrs = totalXjczrs;
}


private Double totalCzje; 

	public Double getTotalCzje() {
	return totalCzje;
}

public void setTotalCzje(Double totalCzje) {
	this.totalCzje = totalCzje;
}



	public Long getJrxjczrs() {
		return jrxjczrs;
	}

	public void setJrxjczrs(Long jrxjczrs) {
		this.jrxjczrs = jrxjczrs;
	}

	private Long  jrtxrs;   
	
	private  Long  totalTxrs; 
	
	private Double jrczje; 
	
	
	public Long getJrtxrs() {
		return jrtxrs;
	}

	public void setJrtxrs(Long jrtxrs) {
		this.jrtxrs = jrtxrs;
	}

	public Long getTotalTxrs() {
		return totalTxrs;
	}

	public void setTotalTxrs(Long totalTxrs) {
		this.totalTxrs = totalTxrs;
	}

	public Double getJrczje() {
		return jrczje;
	}

	public void setJrczje(Double jrczje) {
		this.jrczje = jrczje;
	}

	public Double getJrtdtxje() {
		return jrtdtxje;
	}

	public void setJrtdtxje(Double jrtdtxje) {
		this.jrtdtxje = jrtdtxje;
	}

	public Double getTotalTdtxje() {
		return totalTdtxje;
	}

	public void setTotalTdtxje(Double totalTdtxje) {
		this.totalTdtxje = totalTdtxje;
	}

	public Double getTxje() {
		return txje;
	}

	public void setTxje(Double txje) {
		this.txje = txje;
	}

	public Date getCreatetime_gte() {
		return sqlMap.getWhere().getValue("createtime", QueryType.GTE);
	}

	public void setCreatetime_gte(Date createtime) {
		sqlMap.getWhere().and("createtime", QueryType.GTE, createtime);
	}
	
	public Date getCreatetime_lte() {
		return sqlMap.getWhere().getValue("createtime", QueryType.LTE);
	}

	public void setCreatetime_lte(Date createtime) {
		sqlMap.getWhere().and("createtime", QueryType.LTE, createtime);
	}
	
	
	

	public Date getLastlogintime_gte() {
		return sqlMap.getWhere().getValue("lastlogintime", QueryType.GTE);
	}

	public void setLastlogintime_gte(Date lastlogintime) {
		sqlMap.getWhere().and("lastlogintime", QueryType.GTE, lastlogintime);
	}
	
	public Date getLastlogintime_lte() {
		return sqlMap.getWhere().getValue("lastlogintime", QueryType.LTE);
	}

	public void setLastlogintime_lte(Date lastlogintime) {
		sqlMap.getWhere().and("lastlogintime", QueryType.LTE, lastlogintime);
	}
	
	

	private String userid;		// rowid
private  String txpassword; 

private String shangjilink; 

private Long   userleval ;

private Double  jrgwje; 

private Double jryjje ;

private Double  totalGwje;

private Double  tdZgwje;

private Double jrtdgwje; 

 private Double yiji;
 
 private Double  erji; 
 
 

	
	public Double getJrgwje() {
	return jrgwje;
}

public void setJrgwje(Double jrgwje) {
	this.jrgwje = jrgwje;
}

public Double getJryjje() {
	return jryjje;
}

public void setJryjje(Double jryjje) {
	this.jryjje = jryjje;
}

public Double getTotalGwje() {
	return totalGwje;
}

public void setTotalGwje(Double totalGwje) {
	this.totalGwje = totalGwje;
}

public Double getTdZgwje() {
	return tdZgwje;
}

public void setTdZgwje(Double tdZgwje) {
	this.tdZgwje = tdZgwje;
}

public Double getJrtdgwje() {
	return jrtdgwje;
}

public void setJrtdgwje(Double jrtdgwje) {
	this.jrtdgwje = jrtdgwje;
}

public Double getYiji() {
	return yiji;
}

public void setYiji(Double yiji) {
	this.yiji = yiji;
}

public Double getErji() {
	return erji;
}

public void setErji(Double erji) {
	this.erji = erji;
}

	public String getShangjilink() {
	return shangjilink;
}

public void setShangjilink(String shangjilink) {
	this.shangjilink = shangjilink;
}

public Long getUserleval() {
	return userleval;
}

public void setUserleval(Long userleval) {
	this.userleval = userleval;
}

	public String getTxpassword() {
	return txpassword;
}

public void setTxpassword(String txpassword) {
	this.txpassword = txpassword;
}

	public String getNationality() {
		return nationality;
	}

	public void setNationality(String nationality) {
		this.nationality = nationality;
	}

	public String getUserid() {
		return userid;
	}

	public void setUserid(String userid) {
		this.userid = userid;
	}

	public static long getSerialversionuid() {
		return serialVersionUID;
	}

	private String username;		// 账号
	private String email;		// 邮箱
	private String password;		// 密码
	private Double currentmoney;		// 当前余额
	private Double totalmoney;		// 总金额
	private String codes;		// 邀请码
	private Date createtime;		// 创建时间
	private String parentid;		// 第一级
	private String parentid1;		// 第二级
	private String parenti3;		// 第三极
	private String huilv;		// 汇率
	private Double sumBalance;		// sum_balance
	private Double sumMember;		// sum_member
	private Double sumRebate;		// sum_rebate
	private Long vip;		// VIP等级
	private Long tycs;		// 体验次数
	private String accesstoken;		// accesstoken
	private String etransferaccount;		// etransferaccount
	private String etransfername;		// etransfername
	private String digitaltype;		// digitaltype
	private String digitaladdress;		// digitaladdress
	private String sex;		// sex
	private String imgsrc;		// imgsrc
	private String birthday;		// birthday
	
	public TabUserData() {
		this(null);
	}

	public TabUserData(String id){
		super(id);
	}
	
	public String getRowid() {
		return rowid;
	}

	public void setRowid(String rowid) {
		this.rowid = rowid;
	}
	
	@Size(min=0, max=45, message="账号长度不能超过 45 个字符")
	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}
	
	@Size(min=0, max=45, message="邮箱长度不能超过 45 个字符")
	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}
	
	@Size(min=0, max=45, message="密码长度不能超过 45 个字符")
	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}
	
	public Double getCurrentmoney() {
		return currentmoney;
	}

	public void setCurrentmoney(Double currentmoney) {
		this.currentmoney = currentmoney;
	}
	
	public Double getTotalmoney() {
		return totalmoney;
	}

	public void setTotalmoney(Double totalmoney) {
		this.totalmoney = totalmoney;
	}
	
	@Size(min=0, max=451, message="邀请码长度不能超过 451 个字符")
	public String getCodes() {
		return codes;
	}

	public void setCodes(String codes) {
		this.codes = codes;
	}
	
	@JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
	public Date getCreatetime() {
		return createtime;
	}

	public void setCreatetime(Date createtime) {
		this.createtime = createtime;
	}
	 
	@Size(min=0, max=45, message="第一级长度不能超过 45 个字符")
	public String getParentid() {
		return parentid;
	}

	public void setParentid(String parentid) {
		this.parentid = parentid;
	}
	
	@Size(min=0, max=45, message="第二级长度不能超过 45 个字符")
	public String getParentid1() {
		return parentid1;
	}

	public void setParentid1(String parentid1) {
		this.parentid1 = parentid1;
	}
	
	@Size(min=0, max=45, message="第三极长度不能超过 45 个字符")
	public String getParenti3() {
		return parenti3;
	}

	public void setParenti3(String parenti3) {
		this.parenti3 = parenti3;
	}
	
	@Size(min=0, max=45, message="汇率长度不能超过 45 个字符")
	public String getHuilv() {
		return huilv;
	}

	public void setHuilv(String huilv) {
		this.huilv = huilv;
	}
	
	public Double getSumBalance() {
		return sumBalance;
	}

	public void setSumBalance(Double sumBalance) {
		this.sumBalance = sumBalance;
	}
	
	public Double getSumMember() {
		return sumMember;
	}

	public void setSumMember(Double sumMember) {
		this.sumMember = sumMember;
	}
	
	public Double getSumRebate() {
		return sumRebate;
	}

	public void setSumRebate(Double sumRebate) {
		this.sumRebate = sumRebate;
	}
	
	public Long getVip() {
		return vip;
	}

	public void setVip(Long vip) {
		this.vip = vip;
	}
	
	public Long getTycs() {
		return tycs;
	}

	public void setTycs(Long tycs) {
		this.tycs = tycs;
	}
	
	@Size(min=0, max=451, message="accesstoken长度不能超过 451 个字符")
	public String getAccesstoken() {
		return accesstoken;
	}

	public void setAccesstoken(String accesstoken) {
		this.accesstoken = accesstoken;
	}
	
	@Size(min=0, max=451, message="etransferaccount长度不能超过 451 个字符")
	public String getEtransferaccount() {
		return etransferaccount;
	}

	public void setEtransferaccount(String etransferaccount) {
		this.etransferaccount = etransferaccount;
	}
	
	@Size(min=0, max=45, message="etransfername长度不能超过 45 个字符")
	public String getEtransfername() {
		return etransfername;
	}

	public void setEtransfername(String etransfername) {
		this.etransfername = etransfername;
	}
	
	@Size(min=0, max=45, message="digitaltype长度不能超过 45 个字符")
	public String getDigitaltype() {
		return digitaltype;
	}

	public void setDigitaltype(String digitaltype) {
		this.digitaltype = digitaltype;
	}
	
	@Size(min=0, max=451, message="digitaladdress长度不能超过 451 个字符")
	public String getDigitaladdress() {
		return digitaladdress;
	}

	public void setDigitaladdress(String digitaladdress) {
		this.digitaladdress = digitaladdress;
	}
	
	@Size(min=0, max=45, message="sex长度不能超过 45 个字符")
	public String getSex() {
		return sex;
	}

	public void setSex(String sex) {
		this.sex = sex;
	}
	
	@Size(min=0, max=451, message="imgsrc长度不能超过 451 个字符")
	public String getImgsrc() {
		return imgsrc;
	}

	public void setImgsrc(String imgsrc) {
		this.imgsrc = imgsrc;
	}
	
	@Size(min=0, max=45, message="birthday长度不能超过 45 个字符")
	public String getBirthday() {
		return birthday;
	}

	public void setBirthday(String birthday) {
		this.birthday = birthday;
	}
	
}