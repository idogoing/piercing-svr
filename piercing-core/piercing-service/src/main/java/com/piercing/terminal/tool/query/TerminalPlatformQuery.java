package com.piercing.terminal.tool.query;

import java.util.List;

import com.flash.base.tool.query.BaseQuery;

public class TerminalPlatformQuery extends BaseQuery{
	private Integer id;
	private Integer terminalId;
	private String terminalCode;
	private Integer platformId;
	private String platformCode;

	private List<Object> ids;
	private List<Object> terminalIds;
	private List<Object> platformIds;

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public Integer getTerminalId() {
		return terminalId;
	}

	public void setTerminalId(Integer terminalId) {
		this.terminalId = terminalId;
	}

	public String getTerminalCode() {
		return terminalCode;
	}

	public void setTerminalCode(String terminalCode) {
		this.terminalCode = terminalCode;
	}

	public Integer getPlatformId() {
		return platformId;
	}

	public void setPlatformId(Integer platformId) {
		this.platformId = platformId;
	}

	public String getPlatformCode() {
		return platformCode;
	}

	public void setPlatformCode(String platformCode) {
		this.platformCode = platformCode;
	}

	public List<Object> getIds() {
		return ids;
	}

	public void setIds(List<Object> ids) {
		this.ids = ids;
	}

	public List<Object> getTerminalIds() {
		return terminalIds;
	}

	public void setTerminalIds(List<Object> terminalIds) {
		this.terminalIds = terminalIds;
	}

	public List<Object> getPlatformIds() {
		return platformIds;
	}

	public void setPlatformIds(List<Object> platformIds) {
		this.platformIds = platformIds;
	}

}
