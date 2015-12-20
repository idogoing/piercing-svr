package com.piercing.terminal.dto;

import com.piercing.platform.dto.PlatformBaseDto;

public class TerminalDetail {
	private Integer id;
	private String terminalCode;
	private String appid;
	private String secret;
	private Integer count;
	private Integer type;
	private PlatformBaseDto platformInfo;

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public String getTerminalCode() {
		return terminalCode;
	}

	public void setTerminalCode(String terminalCode) {
		this.terminalCode = terminalCode;
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

	public Integer getCount() {
		return count;
	}

	public void setCount(Integer count) {
		this.count = count;
	}

	public Integer getType() {
		return type;
	}

	public void setType(Integer type) {
		this.type = type;
	}

	public PlatformBaseDto getPlatformInfo() {
		return platformInfo;
	}

	public void setPlatformInfo(PlatformBaseDto platformInfo) {
		this.platformInfo = platformInfo;
	}

}
