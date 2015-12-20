package com.piercing.platform.exception;

import com.flash.exception.SystemException;

/**
 * ucenter模块系统异常，无需抛出
 * @author lonaking
 */
public class PlatformSystemException extends SystemException {

	private static final long serialVersionUID = 1L;

	public PlatformSystemException(PlatformServiceExceptionCode code, String message,
			Throwable cause, boolean enableSuppression,
			boolean writableStackTrace) {
		super(code, message, cause, enableSuppression, writableStackTrace);
	}

	public PlatformSystemException(PlatformServiceExceptionCode code, String message, Throwable cause) {
		super(code, message, cause);
	}

	public PlatformSystemException(PlatformServiceExceptionCode code, String message) {
		super(code, message);
	}

	public PlatformSystemException(PlatformServiceExceptionCode code, Throwable cause) {
		super(code, cause);
	}

	public PlatformSystemException(PlatformServiceExceptionCode code) {
		super(code);
	}

}
