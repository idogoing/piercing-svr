package com.piercing.terminal.service.impl;

import java.util.List;
import java.util.stream.Collectors;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import com.flash.base.tool.page.Page;
import com.flash.exception.ServiceException;
import com.piercing.terminal.exception.TerminalServiceException;
import com.piercing.terminal.exception.TerminalServiceExceptionCode;
import com.piercing.terminal.o.dto.TerminalPlatformBaseDto;
import com.piercing.terminal.service.TerminalPlatformService;
import com.piercing.terminal.tool.query.TerminalPlatformQuery;

@Service("terminalPlatformService")
public class TerminalPlatformServiceImpl extends TerminalPlatformBaseService implements TerminalPlatformService {

	private static final Logger LOGGER = LoggerFactory.getLogger(TerminalPlatformServiceImpl.class);
	
	@Override
	public List<Integer> getPlatformIdListByTerminalId(Integer terminalId) throws ServiceException {
		TerminalPlatformQuery query = new TerminalPlatformQuery();
		query.setTerminalId(terminalId);
		Page<TerminalPlatformBaseDto> pageResult = this.query(query);
		if(null == pageResult || pageResult.getTotalCount() == 0 || pageResult.getPageData() == null || pageResult.getPageData().size() == 0){
			LOGGER.error("获取terminalId为{}的平台Id列表时查询出空的结果",terminalId);
			throw new TerminalServiceException(TerminalServiceExceptionCode.NO_PLATFORM_BIND_THIS_TERMINAL);
		}
		List<TerminalPlatformBaseDto> terminalPlatformList = pageResult.getPageData();
		List<Integer> platformIdList = terminalPlatformList.stream().map(TerminalPlatformBaseDto::getPlatformId).collect(Collectors.toList());
		return platformIdList;
	}

	@Override
	public List<Integer> getTerminalIdListByPlatformId(Integer platformId) throws ServiceException {
		TerminalPlatformQuery query = new TerminalPlatformQuery();
		query.setPlatformId(platformId);
		Page<TerminalPlatformBaseDto> pageResult = this.query(query);
		if(null == pageResult || pageResult.getTotalCount() == 0 || pageResult.getPageData() == null || pageResult.getPageData().size() == 0){
			LOGGER.error("获取platforId为{}的终端Id列表时查询出空的结果",platformId);
			throw new TerminalServiceException(TerminalServiceExceptionCode.NONE_TERMINAL_IN_THE_PLATFORM);
		}
		List<TerminalPlatformBaseDto> terminalPlatformList = pageResult.getPageData();
		List<Integer> terminalIdList = terminalPlatformList.stream().map(TerminalPlatformBaseDto::getTerminalId).collect(Collectors.toList());
		return terminalIdList;
	}

}
