package com.piercing.terminal.exception;

import com.flash.exception.annotation.Desc;
import com.flash.exception.resource.ExceptionCode;

public enum TerminalServiceExceptionCode implements ExceptionCode {
	@Desc(code = 5400, msg = "终端号appid或者secret错误") DEVICE_CAN_NOT_FOUND, 
	@Desc(code = 5401, msg = "这个平台下面没有绑定终端")
	NONE_TERMINAL_IN_THE_PLATFORM, 
	@Desc(code = 5402, msg = "没有平台绑定这个终端")
	NO_PLATFORM_BIND_THIS_TERMINAL, 
	@Desc(code = 5403, msg = "不是本店二维码")
	CODE_CAN_NOT_MATCHING_ANY_PLATFORM, 
	@Desc(code = 5404, msg = "redis中没有这条数据")
	REDIS_NOT_FOUND_TERMINAL, 
	@Desc(code = 5405, msg = "redis中数据过期")
	REDIS_CACHE_OVER_TIME, 
	@Desc(code = 5405, msg = "终端secret错误")
	TERMINAL_SECRET_ERROR
}
