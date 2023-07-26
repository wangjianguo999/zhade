package com.jeesite.modules.tab.entity;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.jeesite.common.entity.DataEntity;
import com.jeesite.common.mybatis.annotation.Column;
import com.jeesite.common.mybatis.annotation.Table;
import com.jeesite.common.mybatis.mapper.query.QueryType;

import javax.validation.constraints.Size;
import java.util.Date;

/**
 * tab_user_dataEntity
 * @author 1
 * @version 2021-12-14
 */
@Table(name="tab_user_coinw", alias="a", label="tab_user_coinw信息", columns={
		@Column(name="rowid", attrName="rowid", label="rowid", isPK=true,queryType=QueryType.LIKE),
		@Column(name="username", attrName="username", label="账号名称"),
		@Column(name="followercount", attrName="followercount", label="当前跟单人数"),
		@Column(name="followtotalcount", attrName="followtotalcount", label="累计跟单人数"),
		@Column(name="profitamount", attrName="profitamount", label="总收益"),
		@Column(name="totalprofitrate", attrName="totalprofitrate", label="总收益率"),
		@Column(name="totalfollowerbalance", attrName="totalfollowerbalance", label="总跟单资金"),
		@Column(name="settleintime", attrName="settleintime", label="交易天数"),
		@Column(name="minquantity", attrName="minquantity", label="交易员最小本金"),
		@Column(name="maxquantity", attrName="maxquantity", label="交易员最大本金"),
		@Column(name="avgquantity", attrName="avgquantity", label="交易员平均本金"),
		@Column(name="avgprofitrate", attrName="avgprofitrate", label="每单平均收益率"),
		@Column(name="mingprofitrate", attrName="mingprofitrate", label="单笔最小收益率"),
		@Column(name="maxgprofitrate", attrName="maxgprofitrate", label="单笔最大收益率"),
		@Column(name="avgprofit", attrName="avgprofit", label="平均收益"),
		@Column(name="maxgprofit", attrName="maxgprofit", label="最大收益"),
		@Column(name="mingprofit", attrName="mingprofit", label="最小收益"),
		@Column(name="ordercount", attrName="ordercount", label="交易笔数"),
		@Column(name="losscount", attrName="losscount", label="亏损笔数"),
		@Column(name="profitcount", attrName="profitcount", label="盈利笔数"),
		@Column(name="profitCountrate", attrName="profitCountrate", label="盈利率"),
		@Column(name="avgtime", attrName="avgtime", label="盈利率"),
		@Column(name="mintime", attrName="mintime", label="盈利率"),
		@Column(name="maxtime", attrName="maxtime", label="盈利率"),
	}
)
public class TabUserCoinw extends DataEntity<TabUserCoinw> {

	private String rowid;

	private String username;
	//当前跟单人数
	private Integer followercount;
	//累计跟单人数
	private Integer followtotalcount;
	//总收益
	private Double profitamount;
	//总收益率
	private Double totalprofitrate;
	//总跟单资金
	private Double totalfollowerbalance;
	//交易天数
	private Integer settleintime;
	//交易员最小本金
	private Double minquantity;
	//交易员最大本金
	private Double maxquantity;
	//交易员平均本金
	private Double avgquantity;
	//每单平均收益率
	private Double avgprofitrate;
	//单笔最小收益率
	private Double mingprofitrate;
	//单笔最大收益率
	private Double maxgprofitrate;
	//平均收益
	private Double avgprofit;
	//最大收益
	private Double maxgprofit;
	//最小收益
	private Double mingprofit;
	//交易笔数
	private Integer ordercount;
	//亏损笔数
	private Integer losscount;
	//盈利笔数
	private Integer profitcount;
	//盈利率
	private Double profitCountrate;

	private Double avgtime;

	private Double mintime;

	private Double maxtime;

	public Double getAvgtime() {
		return avgtime;
	}

	public void setAvgtime(Double avgtime) {
		this.avgtime = avgtime;
	}

	public Double getMintime() {
		return mintime;
	}

	public void setMintime(Double mintime) {
		this.mintime = mintime;
	}

	public Double getMaxtime() {
		return maxtime;
	}

	public void setMaxtime(Double maxtime) {
		this.maxtime = maxtime;
	}

	public String getRowid() {
		return rowid;
	}

	public void setRowid(String rowid) {
		this.rowid = rowid;
	}

	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	public Integer getFollowercount() {
		return followercount;
	}

	public void setFollowercount(Integer followercount) {
		this.followercount = followercount;
	}

	public Integer getFollowtotalcount() {
		return followtotalcount;
	}

	public void setFollowtotalcount(Integer followtotalcount) {
		this.followtotalcount = followtotalcount;
	}

	public Double getProfitamount() {
		return profitamount;
	}

	public void setProfitamount(Double profitamount) {
		this.profitamount = profitamount;
	}

	public Double getTotalprofitrate() {
		return totalprofitrate;
	}

	public void setTotalprofitrate(Double totalprofitrate) {
		this.totalprofitrate = totalprofitrate;
	}

	public Double getTotalfollowerbalance() {
		return totalfollowerbalance;
	}

	public void setTotalfollowerbalance(Double totalfollowerbalance) {
		this.totalfollowerbalance = totalfollowerbalance;
	}

	public Integer getSettleintime() {
		return settleintime;
	}

	public void setSettleintime(Integer settleintime) {
		this.settleintime = settleintime;
	}

	public Double getMinquantity() {
		return minquantity;
	}

	public void setMinquantity(Double minquantity) {
		this.minquantity = minquantity;
	}

	public Double getMaxquantity() {
		return maxquantity;
	}

	public void setMaxquantity(Double maxquantity) {
		this.maxquantity = maxquantity;
	}

	public Double getAvgquantity() {
		return avgquantity;
	}

	public void setAvgquantity(Double avgquantity) {
		this.avgquantity = avgquantity;
	}

	public Double getAvgprofitrate() {
		return avgprofitrate;
	}

	public void setAvgprofitrate(Double avgprofitrate) {
		this.avgprofitrate = avgprofitrate;
	}

	public Double getMingprofitrate() {
		return mingprofitrate;
	}

	public void setMingprofitrate(Double mingprofitrate) {
		this.mingprofitrate = mingprofitrate;
	}

	public Double getMaxgprofitrate() {
		return maxgprofitrate;
	}

	public void setMaxgprofitrate(Double maxgprofitrate) {
		this.maxgprofitrate = maxgprofitrate;
	}

	public Double getAvgprofit() {
		return avgprofit;
	}

	public void setAvgprofit(Double avgprofit) {
		this.avgprofit = avgprofit;
	}

	public Double getMaxgprofit() {
		return maxgprofit;
	}

	public void setMaxgprofit(Double maxgprofit) {
		this.maxgprofit = maxgprofit;
	}

	public Double getMingprofit() {
		return mingprofit;
	}

	public void setMingprofit(Double mingprofit) {
		this.mingprofit = mingprofit;
	}

	public Integer getOrdercount() {
		return ordercount;
	}

	public void setOrdercount(Integer ordercount) {
		this.ordercount = ordercount;
	}

	public Integer getLosscount() {
		return losscount;
	}

	public void setLosscount(Integer losscount) {
		this.losscount = losscount;
	}

	public Integer getProfitcount() {
		return profitcount;
	}

	public void setProfitcount(Integer profitcount) {
		this.profitcount = profitcount;
	}

	public Double getProfitCountrate() {
		return profitCountrate;
	}

	public void setProfitCountrate(Double profitCountrate) {
		this.profitCountrate = profitCountrate;
	}

}