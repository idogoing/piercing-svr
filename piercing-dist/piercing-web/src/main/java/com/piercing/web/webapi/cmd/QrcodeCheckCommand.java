package com.piercing.web.webapi.cmd;

import java.sql.Timestamp;

import javax.validation.constraints.NotNull;

import org.hibernate.validator.constraints.Email;
import org.hibernate.validator.constraints.NotBlank;

/**
 * 核销使用的command
 * 
 * @author leon
 *
 */
public class QrcodeCheckCommand {
	@NotNull(message = "appid不能为空")
	private String appid;
	@NotBlank(message = "secret不能为空")
	private String secret;

	private Timestamp time;
	@NotBlank(message = "code不能为空")
	private String code;
	@NotBlank(message = "sign不能为空")
	private String sign;
	
	@Email(message = "邮箱地址不正确")
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
