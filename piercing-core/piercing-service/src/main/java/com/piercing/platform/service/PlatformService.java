package com.piercing.platform.service;

import java.util.List;

import com.flash.exception.ServiceException;
import com.piercing.platform.dto.PlatformBaseDto;

/**
 * 跟平台有关的接口
 * 
 * @author leon
 *
 */
public interface PlatformService {

	/**
	 * 聪明的获取一个appid、secret对应的所有的platform
	 * @param appid
	 * @param secret
	 * @return
	 */
	public List<PlatformBaseDto> smartListBySecret(String appid, String secret) throws ServiceException;

	public List<PlatformBaseDto> getPlatformListByTerminalId(Integer terminalId) throws ServiceException;

}
