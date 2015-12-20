package com.piercing.terminal.service;

import java.util.List;

import com.flash.exception.ServiceException;

/**
 * 终端、平台关系接口
 * @author leon
 *
 */
public interface TerminalPlatformService {
	
	/**
	 * 根据terminlaId查询所有平台id列表
	 * @param terminalId
	 * @return
	 * @throws ServiceException
	 */
	public List<Integer> getPlatformIdListByTerminalId(Integer terminalId) throws ServiceException;
	
	/**
	 * 根据platformId查询所有终端id列表
	 * @param platformId
	 * @return
	 * @throws ServiceException
	 */
	public List<Integer> getTerminalIdListByPlatformId(Integer platformId) throws ServiceException;
}
