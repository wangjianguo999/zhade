package com.jeesite.modules.tab.entity;

import javax.validation.constraints.Size;

import com.jeesite.common.entity.DataEntity;
import com.jeesite.common.mybatis.annotation.Column;
import com.jeesite.common.mybatis.annotation.Table;
import com.jeesite.common.mybatis.mapper.query.QueryType;

/**
 * tab_user_addressEntity
 * @author 1
 * @version 2021-12-13
 */
@Table(name="tab_user_address", alias="a", label="tab_user_address信息", columns={
		@Column(name="userid", attrName="userid", label="userid", isPK=true),
		@Column(name="address", attrName="address", label="address"),
		@Column(name="city", attrName="city", label="city"),
		@Column(name="country", attrName="country", label="country"),
		@Column(name="nikename", attrName="nikename", label="nikename"),
		@Column(name="tel", attrName="tel", label="tel"),
		@Column(name="username", attrName="username", label="username"),
		@Column(name="uid", attrName="uid", label="uid"),
		@Column(name="postalcode", attrName="postalcode", label="postalcode"),
	}, orderBy="a.userid DESC"
)
public class TabUserAddress extends DataEntity<TabUserAddress> {
	
	private static final long serialVersionUID = 1L;
	private String userid;		// userid
	private String token    ; 
	private String address;		// address
	private String city;		// city
	private String country;		// country
	private String nikename;		// nikename
	private String tel;		// tel
	private String username;		// username
	private String uid;		// uid
	private String postalcode;		// postalcode
	
	
	
	public String getToken() {
		return token;
	}

	public void setToken(String token) {
		this.token = token;
	}

	public static long getSerialversionuid() {
		return serialVersionUID;
	}

	public TabUserAddress() {
		this(null);
	}

	public TabUserAddress(String id){
		super(id);
	}
	
	public String getUserid() {
		return userid;
	}

	public void setUserid(String userid) {
		this.userid = userid;
	}
	
	@Size(min=0, max=45, message="address长度不能超过 45 个字符")
	public String getAddress() {
		return address;
	}

	public void setAddress(String address) {
		this.address = address;
	}
	
	@Size(min=0, max=45, message="city长度不能超过 45 个字符")
	public String getCity() {
		return city;
	}

	public void setCity(String city) {
		this.city = city;
	}
	
	@Size(min=0, max=45, message="country长度不能超过 45 个字符")
	public String getCountry() {
		return country;
	}

	public void setCountry(String country) {
		this.country = country;
	}
	
	@Size(min=0, max=45, message="nikename长度不能超过 45 个字符")
	public String getNikename() {
		return nikename;
	}

	public void setNikename(String nikename) {
		this.nikename = nikename;
	}
	
	@Size(min=0, max=45, message="tel长度不能超过 45 个字符")
	public String getTel() {
		return tel;
	}

	public void setTel(String tel) {
		this.tel = tel;
	}
	
	@Size(min=0, max=45, message="username长度不能超过 45 个字符")
	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}
	
	@Size(min=0, max=45, message="uid长度不能超过 45 个字符")
	public String getUid() {
		return uid;
	}

	public void setUid(String uid) {
		this.uid = uid;
	}
	
	@Size(min=0, max=451, message="postalcode长度不能超过 451 个字符")
	public String getPostalcode() {
		return postalcode;
	}

	public void setPostalcode(String postalcode) {
		this.postalcode = postalcode;
	}
	
}