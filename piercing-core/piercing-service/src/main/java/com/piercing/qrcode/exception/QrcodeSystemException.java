package com.piercing.qrcode.exception;

import com.flash.exception.SystemException;

/**
 * ucenter模块系统异常，无需抛出
 * @author lonaking
 */
public class QrcodeSystemException extends SystemException {

	private static final long serialVersionUID = 1L;

	public QrcodeSystemException(QrcodeServiceExceptionCode code, String message,
			Throwable cause, boolean enableSuppression,
			boolean writableStackTrace) {
		super(code, message, cause, enableSuppression, writableStackTrace);
	}

	public QrcodeSystemException(QrcodeServiceExceptionCode code, String message, Throwable cause) {
		super(code, message, cause);
	}

	public QrcodeSystemException(QrcodeServiceExceptionCode code, String message) {
		super(code, message);
	}

	public QrcodeSystemException(QrcodeServiceExceptionCode code, Throwable cause) {
		super(code, cause);
	}

	public QrcodeSystemException(QrcodeServiceExceptionCode code) {
		super(code);
	}

}
