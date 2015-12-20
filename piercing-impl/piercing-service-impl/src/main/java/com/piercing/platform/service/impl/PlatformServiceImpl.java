package com.piercing.platform.service.impl;

import java.util.List;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import com.flash.exception.ServiceException;
import com.piercing.platform.dto.PlatformBaseDto;
import com.piercing.platform.exception.PlatformServiceException;
import com.piercing.platform.exception.PlatformServiceExceptionCode;
import com.piercing.platform.service.PlatformService;
import com.piercing.terminal.service.TerminalPlatformService;
import com.piercing.terminal.service.TerminalService;

@Service("platformService")
public class PlatformServiceImpl extends PlatformBaseService implements PlatformService{

	@Resource(name = "terminalService")
	private TerminalService terminalService;
	
	@Resource(name = "terminalPlatformService")
	private TerminalPlatformService terminalPlatformService;
	
	@Override
	public List<PlatformBaseDto> smartListBySecret(String appid, String secret) {
				
		
		return null;
	}

	@Override
	public List<PlatformBaseDto> getPlatformListByTerminalId(Integer terminalId) throws ServiceException {
		List<Integer> platformIdList = this.terminalPlatformService.getPlatformIdListByTerminalId(terminalId);
		List<PlatformBaseDto> platformList = this.getListByIds(platformIdList);
		if(platformList == null || platformList.size() == 0){
			
			throw new PlatformServiceException(PlatformServiceExceptionCode.THERE_IS_NO_PLATFORM_BIND_THIS_TERMINAL);
		}
		return platformList;
	}
}
