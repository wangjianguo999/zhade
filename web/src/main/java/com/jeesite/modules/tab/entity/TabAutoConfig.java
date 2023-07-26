package com.jeesite.modules.tab.entity;

import javax.validation.constraints.Size;
import javax.validation.constraints.NotNull;

import com.jeesite.common.entity.DataEntity;
import com.jeesite.common.mybatis.annotation.Column;
import com.jeesite.common.mybatis.annotation.Table;
import com.jeesite.common.mybatis.mapper.query.QueryType;

/**
 * tab_auto_configEntity
 * @author 1
 * @version 2022-03-14
 */
@Table(name="tab_auto_config", alias="a", label="tab_auto_config信息", columns={
		@Column(name="rowid", attrName="rowid", label="rowid", isPK=true),
		@Column(name="zhucejl", attrName="zhucejl", label="注册奖励"),
		@Column(name="tyj", attrName="tyj", label="体验金"),
		@Column(name="tianshu", attrName="tianshu", label="天数"),
		@Column(name="sctx", attrName="sctx", label="首次提现限制"),
		@Column(name="sctxhuilv", attrName="sctxhuilv", label="首次提现汇率"),
		@Column(name="txje", attrName="txje", label="提现金额限制"),
		@Column(name="txhuilv", attrName="txhuilv", label="提现汇率"),
		@Column(name="czzdje", attrName="czzdje", label="充值最低金额"),
		@Column(name="isauto", attrName="isauto", label="是否自动"),
		@Column(name="isqidong", attrName="isqidong", label="是否启用"),
		@Column(name="zpisqidong", attrName="zpisqidong", label="是否启用"),
		@Column(name="rechangeId", attrName="rechangeId", label="是否启用"),
		@Column(name="huanka", attrName="huanka", label="是否启用"),
		@Column(name="jiesuankey", attrName="jiesuankey", label="结算队列key"),
		@Column(name="shijianlunhuan", attrName="shijianlunhuan", label="shijianlunhuan"),
		@Column(name="minutes", attrName="minutes", label="minutes"),
		@Column(name="edu", attrName="edu", label="edu"),
	}, orderBy="a.isqidong DESC"
)
public class TabAutoConfig extends DataEntity<TabAutoConfig> {
	
	private static final long serialVersionUID = 1L;
	private String rowid;		// rowid
	private Double zhucejl;		// 注册奖励
	private Double tyj;		// 体验金
	private Integer tianshu;		// 天数
	private Double sctx;		// 首次提现限制
	private String sctxhuilv;		// 首次提现汇率
	private Double txje;		// 提现金额限制
	private String txhuilv;		// 提现汇率
	private Integer czzdje;		// 充值最低金额
	private Integer isauto;		// 是否自动
	private String isqidong;		// 是否启用
	private String jiesuankey;

	public String getShijianlunhuan() {
		return shijianlunhuan;
	}

	public void setShijianlunhuan(String shijianlunhuan) {
		this.shijianlunhuan = shijianlunhuan;
	}

	private String shijianlunhuan;

	public Integer getMinutes() {
		return minutes;
	}

	public void setMinutes(Integer minutes) {
		this.minutes = minutes;
	}

	private Integer minutes;

	public Integer getEdu() {
		return edu;
	}

	public void setEdu(Integer edu) {
		this.edu = edu;
	}

	private Integer edu;
	public String getJiesuankey() {
		return jiesuankey;
	}

	public void setJiesuankey(String jiesuankey) {
		this.jiesuankey = jiesuankey;
	}



	public String getHuanka() {
		return huanka;
	}

	public void setHuanka(String huanka) {
		this.huanka = huanka;
	}

	private String huanka;		// 天数
	private String zpisqidong;
	private String rechangeId;


	public String getRechangeId() {
		return rechangeId;
	}

	public void setRechangeId(String rechangeId) {
		this.rechangeId = rechangeId;
	}
	public String getZpisqidong() {
		return zpisqidong;
	}

	public void setZpisqidong(String zpisqidong) {
		this.zpisqidong = zpisqidong;
	}

	public TabAutoConfig() {
		this(null);
	}

	public TabAutoConfig(String id){
		super(id);
	}
	
	public String getRowid() {
		return rowid;
	}

	public void setRowid(String rowid) {
		this.rowid = rowid;
	}
	
	public Double getZhucejl() {
		return zhucejl;
	}

	public void setZhucejl(Double zhucejl) {
		this.zhucejl = zhucejl;
	}
	
	public Double getTyj() {
		return tyj;
	}

	public void setTyj(Double tyj) {
		this.tyj = tyj;
	}
	
	public Integer getTianshu() {
		return tianshu;
	}

	public void setTianshu(Integer tianshu) {
		this.tianshu = tianshu;
	}
	
	public Double getSctx() {
		return sctx;
	}

	public void setSctx(Double sctx) {
		this.sctx = sctx;
	}
	
	@Size(min=0, max=13, message="首次提现汇率长度不能超过 13 个字符")
	public String getSctxhuilv() {
		return sctxhuilv;
	}

	public void setSctxhuilv(String sctxhuilv) {
		this.sctxhuilv = sctxhuilv;
	}
	
	public Double getTxje() {
		return txje;
	}

	public void setTxje(Double txje) {
		this.txje = txje;
	}
	
	@Size(min=0, max=20, message="提现汇率长度不能超过 20 个字符")
	public String getTxhuilv() {
		return txhuilv;
	}

	public void setTxhuilv(String txhuilv) {
		this.txhuilv = txhuilv;
	}
	
	@NotNull(message="充值最低金额不能为空")
	public Integer getCzzdje() {
		return czzdje;
	}

	public void setCzzdje(Integer czzdje) {
		this.czzdje = czzdje;
	}
	
	@NotNull(message="是否自动不能为空")
	public Integer getIsauto() {
		return isauto;
	}

	public void setIsauto(Integer isauto) {
		this.isauto = isauto;
	}
	
	@Size(min=0, max=10, message="是否启用长度不能超过 10 个字符")
	public String getIsqidong() {
		return isqidong;
	}

	public void setIsqidong(String isqidong) {
		this.isqidong = isqidong;
	}
	
}