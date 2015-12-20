package com.piercing.terminal.tool.query;

import java.util.List;

import com.flash.base.tool.query.BaseQuery;

public class TerminalQuery extends BaseQuery {
	private Integer id;
	private String appid;
	private String secret;
	private Integer type;
	private Integer count;

	// 下面是批量查找的
	private List<Object> ids;
	private List<Object> typeIds;

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public String getAppid() {
		return appid;
	}

	public void setAppid(String appid) {
		this.appid = appid;
	}

	public String getSecret() {
		return secret;
	}

	public void setSecret(String secret) {
		this.secret = secret;
	}

	public Integer getType() {
		return type;
	}

	public void setType(Integer type) {
		this.type = type;
	}

	public Integer getCount() {
		return count;
	}

	public void setCount(Integer count) {
		this.count = count;
	}

	public List<Object> getIds() {
		return ids;
	}

	public void setIds(List<Object> ids) {
		this.ids = ids;
	}

	public List<Object> getTypeIds() {
		return typeIds;
	}

	public void setTypeIds(List<Object> typeIds) {
		this.typeIds = typeIds;
	}

}
