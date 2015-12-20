package com.piercing.qrcode.dto;

import java.sql.Timestamp;

/**
 * 核销使用的command
 * 
 * @author leon
 *
 */
public class QrcodeCheckDto {
	
	private String appid;
	private String secret;

	private Timestamp time;
	private String code;
	private String sign;
	
	private String email;

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

	public Timestamp getTime() {
		return time;
	}

	public void setTime(Timestamp time) {
		this.time = time;
	}

	public String getCode() {
		return code;
	}

	public void setCode(String code) {
		this.code = code;
	}

	public String getSign() {
		return sign;
	}

	public void setSign(String sign) {
		this.sign = sign;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

}
