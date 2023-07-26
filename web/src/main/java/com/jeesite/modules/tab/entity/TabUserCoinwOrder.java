package com.jeesite.modules.tab.entity;

import com.jeesite.common.entity.DataEntity;
import com.jeesite.common.mybatis.annotation.Column;
import com.jeesite.common.mybatis.annotation.Table;
import com.jeesite.common.mybatis.mapper.query.QueryType;

/**
 * tab_user_dataEntity
 * @author 1
 * @version 2021-12-14
 */
@Table(name="tab_user_coinw_order", alias="a", label="tab_user_coinw_order信息", columns={
		@Column(name="rowid", attrName="rowid", label="rowid", isPK=true,queryType=QueryType.LIKE),
		@Column(name="orderid", attrName="orderid", label="账号名称"),
		@Column(name="userid", attrName="userid", label="账号名称"),
		@Column(name="username", attrName="username", label="账号名称"),
		@Column(name="instrument", attrName="instrument", label="账号名称"),
		@Column(name="followProfit", attrName="followProfit", label="账号名称"),
		@Column(name="quantity", attrName="quantity", label="账号名称"),
		@Column(name="leverage", attrName="leverage", label="账号名称"),
		@Column(name="profitRate", attrName="profitRate", label="账号名称"),
		@Column(name="profit", attrName="profit", label="账号名称"),
		@Column(name="followNum", attrName="followNum", label="账号名称"),
		@Column(name="openPrice", attrName="openPrice", label="账号名称"),
		@Column(name="closePrice", attrName="closePrice", label="账号名称"),
		@Column(name="openTime", attrName="openTime", label="账号名称"),
		@Column(name="closeTime", attrName="closeTime", label="账号名称"),
		@Column(name="openTimenumber", attrName="openTimenumber", label="账号名称"),
		@Column(name="closeTimenumber", attrName="closeTimenumber", label="账号名称"),
	}
)
public class TabUserCoinwOrder extends DataEntity<TabUserCoinwOrder> {

	private String rowid;

	private String orderid;

	public String getOrderid() {
		return orderid;
	}

	public void setOrderid(String orderid) {
		this.orderid = orderid;
	}
	private Long closeTimenumber;

	public Long getCloseTimenumber() {
		return closeTimenumber;
	}

	public void setCloseTimenumber(Long closeTimenumber) {
		this.closeTimenumber = closeTimenumber;
	}

	public Long getOpenTimenumber() {
		return openTimenumber;
	}

	public void setOpenTimenumber(Long openTimenumber) {
		this.openTimenumber = openTimenumber;
	}

	private Long openTimenumber;
	private String userid;

	private String username;

	private String instrument;

	private Double followProfit;

	private Double quantity;

	private Integer leverage;

	private Double profitRate;

	private Double profit;

	private Integer followNum;

	private Double openPrice;

	private Double closePrice;

	private String openTime;

	private String closeTime;

	public String getRowid() {
		return rowid;
	}

	public void setRowid(String rowid) {
		this.rowid = rowid;
	}

	public String getUserid() {
		return userid;
	}

	public void setUserid(String userid) {
		this.userid = userid;
	}

	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	public String getInstrument() {
		return instrument;
	}

	public void setInstrument(String instrument) {
		this.instrument = instrument;
	}

	public Double getFollowProfit() {
		return followProfit;
	}

	public void setFollowProfit(Double followProfit) {
		this.followProfit = followProfit;
	}

	public Double getQuantity() {
		return quantity;
	}

	public void setQuantity(Double quantity) {
		this.quantity = quantity;
	}

	public Integer getLeverage() {
		return leverage;
	}

	public void setLeverage(Integer leverage) {
		this.leverage = leverage;
	}

	public Double getProfitRate() {
		return profitRate;
	}

	public void setProfitRate(Double profitRate) {
		this.profitRate = profitRate;
	}

	public Double getProfit() {
		return profit;
	}

	public void setProfit(Double profit) {
		this.profit = profit;
	}

	public Integer getFollowNum() {
		return followNum;
	}

	public void setFollowNum(Integer followNum) {
		this.followNum = followNum;
	}

	public Double getOpenPrice() {
		return openPrice;
	}

	public void setOpenPrice(Double openPrice) {
		this.openPrice = openPrice;
	}

	public Double getClosePrice() {
		return closePrice;
	}

	public void setClosePrice(Double closePrice) {
		this.closePrice = closePrice;
	}

	public String getOpenTime() {
		return openTime;
	}

	public void setOpenTime(String openTime) {
		this.openTime = openTime;
	}

	public String getCloseTime() {
		return closeTime;
	}

	public void setCloseTime(String closeTime) {
		this.closeTime = closeTime;
	}






}