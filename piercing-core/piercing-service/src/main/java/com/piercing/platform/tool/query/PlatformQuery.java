package com.piercing.platform.tool.query;

import java.util.List;

import com.flash.base.tool.query.BaseQuery;

public class PlatformQuery extends BaseQuery {

	private Integer id;
	private String platformCode;
	private String name;// 平台名称
	private String website;// 客户公司网址
	private String mobile;
	private String url;// 基础网址

	private String nameLike;
	private String websiteLike;
	private List<Object> ids;

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public String getPlatformCode() {
		return platformCode;
	}

	public void setPlatformCode(String platformCode) {
		this.platformCode = platformCode;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getWebsite() {
		return website;
	}

	public void setWebsite(String website) {
		this.website = website;
	}

	public String getMobile() {
		return mobile;
	}

	public void setMobile(String mobile) {
		this.mobile = mobile;
	}

	public String getUrl() {
		return url;
	}

	public void setUrl(String url) {
		this.url = url;
	}

	public String getNameLike() {
		return nameLike;
	}

	public void setNameLike(String nameLike) {
		this.nameLike = nameLike;
	}

	public String getWebsiteLike() {
		return websiteLike;
	}

	public void setWebsiteLike(String websiteLike) {
		this.websiteLike = websiteLike;
	}

	public List<Object> getIds() {
		return ids;
	}

	public void setIds(List<Object> ids) {
		this.ids = ids;
	}

}
