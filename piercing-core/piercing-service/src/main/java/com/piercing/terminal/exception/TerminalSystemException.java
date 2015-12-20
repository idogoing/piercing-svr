package com.piercing.terminal.exception;

import com.flash.exception.SystemException;

/**
 * ucenter模块系统异常，无需抛出
 * @author lonaking
 */
public class TerminalSystemException extends SystemException {

	private static final long serialVersionUID = 1L;

	public TerminalSystemException(TerminalServiceExceptionCode code, String message,
			Throwable cause, boolean enableSuppression,
			boolean writableStackTrace) {
		super(code, message, cause, enableSuppression, writableStackTrace);
	}

	public TerminalSystemException(TerminalServiceExceptionCode code, String message, Throwable cause) {
		super(code, message, cause);
	}

	public TerminalSystemException(TerminalServiceExceptionCode code, String message) {
		super(code, message);
	}

	public TerminalSystemException(TerminalServiceExceptionCode code, Throwable cause) {
		super(code, cause);
	}

	public TerminalSystemException(TerminalServiceExceptionCode code) {
		super(code);
	}

}
