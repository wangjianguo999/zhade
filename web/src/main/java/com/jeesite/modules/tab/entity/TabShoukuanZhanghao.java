package com.jeesite.modules.tab.entity;

import javax.validation.constraints.Size;

import com.jeesite.common.entity.DataEntity;
import com.jeesite.common.mybatis.annotation.Column;
import com.jeesite.common.mybatis.annotation.Table;
import com.jeesite.common.mybatis.mapper.query.QueryType;

/**
 * tab_shoukuan_zhanghaoEntity
 * @author 1
 * @version 2022-03-16
 */
@Table(name="tab_shoukuan_zhanghao", alias="a", label="tab_shoukuan_zhanghao信息", columns={
		@Column(name="rowid", attrName="rowid", label="rowid", isPK=true),
		@Column(name="trcaddress", attrName="trcaddress", label="trcaddress"),
		@Column(name="ercaddress", attrName="ercaddress", label="ercaddress"),
		@Column(name="img1", attrName="img1", label="img1"),
		@Column(name="img2", attrName="img2", label="img2"),
	}, orderBy="a.rowid DESC"
)
public class TabShoukuanZhanghao extends DataEntity<TabShoukuanZhanghao> {
	
	private static final long serialVersionUID = 1L;
	private String rowid;		// rowid
	private String trcaddress;		// trcaddress
	private String ercaddress;		// ercaddress
	private String img1;		// img1
	private String img2;		// img2
	
	public TabShoukuanZhanghao() {
		this(null);
	}

	public TabShoukuanZhanghao(String id){
		super(id);
	}
	
	public String getRowid() {
		return rowid;
	}

	public void setRowid(String rowid) {
		this.rowid = rowid;
	}
	
	@Size(min=0, max=200, message="trcaddress长度不能超过 200 个字符")
	public String getTrcaddress() {
		return trcaddress;
	}

	public void setTrcaddress(String trcaddress) {
		this.trcaddress = trcaddress;
	}
	
	@Size(min=0, max=200, message="ercaddress长度不能超过 200 个字符")
	public String getErcaddress() {
		return ercaddress;
	}

	public void setErcaddress(String ercaddress) {
		this.ercaddress = ercaddress;
	}
	
	@Size(min=0, max=300, message="img1长度不能超过 300 个字符")
	public String getImg1() {
		return img1;
	}

	public void setImg1(String img1) {
		this.img1 = img1;
	}
	
	@Size(min=0, max=300, message="img2长度不能超过 300 个字符")
	public String getImg2() {
		return img2;
	}

	public void setImg2(String img2) {
		this.img2 = img2;
	}
	
}