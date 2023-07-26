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
 * tab_sider_dataEntity
 * @author 3333
 * @version 2021-12-11
 */
@Table(name="tab_sider_data", alias="a", label="tab_sider_data信息", columns={
		@Column(name="rowid", attrName="rowid", label="rowid", isPK=true),
		@Column(name="title", attrName="title", label="标题", queryType=QueryType.LIKE),
		@Column(name="imgsrc", attrName="imgsrc", label="图片"),
		@Column(name="content", attrName="content", label="内容"),
		@Column(name="createtime", attrName="createtime", label="创建时间"),
		
		@Column(name="yuyan", attrName="yuyan", label="创建时间"),
		@Column(name="jump", attrName="jump", label="jump"),
		@Column(name="url", attrName="url", label="url"),
	}, orderBy="a.createtime DESC"
)
public class TabSiderData extends DataEntity<TabSiderData> {
	
	private static final long serialVersionUID = 1L;
	private String rowid;		// rowid
	private String title;		// 标题
	private String imgsrc;		// 图片
	private String content;		// 内容
	private Date createtime;		// 创建时间
	private String  yuyan ;
	private Integer  jump ;
	private String  url;

	public String getUrl() {
		return url;
	}

	public void setUrl(String url) {
		this.url = url;
	}

	public Integer getJump() {
		return jump;
	}

	public void setJump(Integer jump) {
		this.jump = jump;
	}

	public String getYuyan() {
		return yuyan;
	}

	public void setYuyan(String yuyan) {
		this.yuyan = yuyan;
	}

	public static long getSerialversionuid() {
		return serialVersionUID;
	}

	public TabSiderData() {
		this(null);
	}

	public TabSiderData(String id){
		super(id);
	}
	
	public String getRowid() {
		return rowid;
	}

	public void setRowid(String rowid) {
		this.rowid = rowid;
	}
	
	@Size(min=0, max=451, message="标题长度不能超过 451 个字符")
	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}
	
	@Size(min=0, max=451, message="图片长度不能超过 451 个字符")
	public String getImgsrc() {
		return imgsrc;
	}

	public void setImgsrc(String imgsrc) {
		this.imgsrc = imgsrc;
	}
	
	public String getContent() {
		return content;
	}

	public void setContent(String content) {
		this.content = content;
	}
	
	@JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
	public Date getCreatetime() {
		return createtime;
	}

	public void setCreatetime(Date createtime) {
		this.createtime = createtime;
	}
	
}