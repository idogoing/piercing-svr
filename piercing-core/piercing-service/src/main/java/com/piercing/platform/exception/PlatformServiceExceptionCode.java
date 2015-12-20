package com.piercing.platform.exception;

import com.flash.exception.annotation.Desc;
import com.flash.exception.resource.ExceptionCode;

public enum PlatformServiceExceptionCode implements ExceptionCode {
	@Desc(code = 5401, msg = "没有平台与此终端绑定")
	THERE_IS_NO_PLATFORM_BIND_THIS_TERMINAL
}
