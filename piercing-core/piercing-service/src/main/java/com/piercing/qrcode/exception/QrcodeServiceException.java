package com.piercing.qrcode.exception;

import com.flash.exception.ServiceException;
import com.flash.exception.resource.ExceptionCode;

/**
 * shop 模块业务异常 shop模块所有的interface都应该抛出此异常
 * @author lonaking
 */
public class QrcodeServiceException extends ServiceException {

	private static final long serialVersionUID = 1L;

	public QrcodeServiceException(ExceptionCode code, String message,
			Throwable cause, boolean enableSuppression,
			boolean writableStackTrace) {
		super(code, message, cause, enableSuppression, writableStackTrace);
	}

	public QrcodeServiceException(ExceptionCode code, String message, Throwable cause) {
		super(code, message, cause);
	}

	public QrcodeServiceException(ExceptionCode code, String message) {
		super(code, message);
	}

	public QrcodeServiceException(ExceptionCode code, Throwable cause) {
		super(code, cause);
	}

	public QrcodeServiceException(ExceptionCode code) {
		super(code);
	}

}
