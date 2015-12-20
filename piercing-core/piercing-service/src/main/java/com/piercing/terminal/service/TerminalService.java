package com.piercing.terminal.service;

import com.flash.base.tool.page.Page;
import com.flash.exception.ServiceException;
import com.piercing.terminal.dto.TerminalDetail;
import com.piercing.terminal.tool.query.TerminalQuery;

/**
 * 跟平台有关的接口
 * @author leon
 *
 */
public interface TerminalService {

	/**
	 * 根据终端的appid secret code 判断该code来自哪个平台
	 * @param code
	 * @param appid
	 * @param secret
	 * @return
	 * @throws ServiceException
	 */
	public TerminalDetail judgePlatformByCodeAndSecret(String code, String appid, String secret) throws ServiceException;
	
	/**
	 * 增加Terminal的核销次数
	 * @param terminalId
	 */
	public void addCount(Integer terminalId);
	
	/**
	 * 高级查询接口
	 * @param query
	 * @return
	 * @throws ServiceException
	 */
	public Page<?> query(TerminalQuery  query) throws ServiceException;
}
