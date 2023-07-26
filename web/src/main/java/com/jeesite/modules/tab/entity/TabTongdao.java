package com.jeesite.modules.tab.entity;

import javax.validation.constraints.Size;

import com.jeesite.common.entity.DataEntity;
import com.jeesite.common.mybatis.annotation.Column;
import com.jeesite.common.mybatis.annotation.Table;
import com.jeesite.common.mybatis.mapper.query.QueryType;

import java.util.Date;

/**
 * 通道Entity
 * @author 1
 * @version 2022-03-29
 */
@Table(name="tab_tongdao", alias="a", label="通道信息", columns={
		@Column(name="rowid", attrName="rowid", label="rowid", isPK=true),
		@Column(name="tongdao_name", attrName="tongdaoName", label="通道名称", queryType=QueryType.LIKE),
		@Column(name="daishou", attrName="daishou", label="代收状态"  ),
		@Column(name="daifu", attrName="daifu", label="代付状态" ),
		@Column(name="orderindex", attrName="orderindex", label="排序"),
		@Column(name="neworderindex", attrName="neworderindex", label="排序"),
		@Column(name="type", attrName="type", label="分类"),
		@Column(name="number", attrName="number", label="号码", queryType=QueryType.LIKE),
		@Column(name="imgsrc", attrName="imgsrc", label="logo"),
		@Column(name="shelftime", attrName="shelftime", label="shelftime"),
		@Column(name="offshelftime", attrName="offshelftime", label="offshelftime"),
		@Column(name="status1", attrName="status1", label="status1"),
		@Column(name="idss", attrName="idss", label="idss", queryType=QueryType.LIKE),
		@Column(name="kahao", attrName="kahao", label="kahao"),
		@Column(name="edu", attrName="edu", label="edu"),
		@Column(name="shijianlunhuan", attrName="shijianlunhuan", label="shijianlunhuan"),
	}, orderBy="a.rowid DESC"
)
public class TabTongdao extends DataEntity<TabTongdao> {
	
	private static final long serialVersionUID = 1L;
	private String rowid;		// rowid
	private String tongdaoName;		// 通道名称
	private String daishou;		// 代收状态
	private String daifu;		// 代付状态
	private Long orderindex;		// 排序
	private String edu;

	public String getShijianlunhuan() {
		return shijianlunhuan;
	}

	public void setShijianlunhuan(String shijianlunhuan) {
		this.shijianlunhuan = shijianlunhuan;
	}

	private String shijianlunhuan;
	public String getEdu() {
		return edu;
	}

	public void setEdu(String edu) {
		this.edu = edu;
	}

	public Long getNeworderindex() {
		return neworderindex;
	}

	public void setNeworderindex(Long neworderindex) {
		this.neworderindex = neworderindex;
	}

	private Long neworderindex;		// 排序
	private String type;		// 分类
	private String number;
	private String imgsrc;

	public String getKahao() {
		return kahao;
	}

	public void setKahao(String kahao) {
		this.kahao = kahao;
	}

	private String kahao;
	public String getIdss() {
		return idss;
	}

	public void setIdss(String idss) {
		this.idss = idss;
	}

	private String idss;
	private Date shelftime;

	private Date offshelftime;

	private Integer status1;

	public Date getShelftime() {
		return shelftime;
	}

	public void setShelftime(Date shelftime) {
		this.shelftime = shelftime;
	}

	public Date getOffshelftime() {
		return offshelftime;
	}

	public void setOffshelftime(Date offshelftime) {
		this.offshelftime = offshelftime;
	}

	public Integer getStatus1() {
		return status1;
	}

	public void setStatus1(Integer status1) {
		this.status1 = status1;
	}

	public String getImgsrc() {
		return imgsrc;
	}

	public void setImgsrc(String imgsrc) {
		this.imgsrc = imgsrc;
	}

	public String getNumber() {
		return number;
	}

	public void setNumber(String number) {
		this.number = number;
	}
	public TabTongdao() {
		this(null);
	}

	public TabTongdao(String id){
		super(id);
	}
	
	public String getRowid() {
		return rowid;
	}

	public void setRowid(String rowid) {
		this.rowid = rowid;
	}
	
	@Size(min=0, max=100, message="通道名称长度不能超过 100 个字符")
	public String getTongdaoName() {
		return tongdaoName;
	}

	public void setTongdaoName(String tongdaoName) {
		this.tongdaoName = tongdaoName;
	}
	
	@Size(min=0, max=10, message="代收状态长度不能超过 10 个字符")
	public String getDaishou() {
		return daishou;
	}

	public void setDaishou(String daishou) {
		this.daishou = daishou;
	}
	
	@Size(min=0, max=10, message="代付状态长度不能超过 10 个字符")
	public String getDaifu() {
		return daifu;
	}

	public void setDaifu(String daifu) {
		this.daifu = daifu;
	}
	
	public Long getOrderindex() {
		return orderindex;
	}

	public void setOrderindex(Long orderindex) {
		this.orderindex = orderindex;
	}
	
	@Size(min=0, max=45, message="分类长度不能超过 45 个字符")
	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}
	
}