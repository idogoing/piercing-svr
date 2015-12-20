package com.piercing.qrcode.exception;

import com.flash.exception.annotation.Desc;
import com.flash.exception.resource.ExceptionCode;

/**
 * 所有Qrcode模块的业务异常码
 * 4开头
 * @author lonaking
 */
public enum QrcodeServiceExceptionCode implements ExceptionCode {
	@Desc(code = 44004, msg = "与第三方平台请求发生异常，请检查第三份平台")
	HTTP_ERROR, 
	@Desc(code = 44005, msg = "核销失败")
	CHECK_FAILED, 
	@Desc(code = 44006, msg = "第三方平台未能按照扫码协议返回数据")
	PLATFORM_API_ERROR, 
	@Desc(code = 44007, msg = "加密错误，请联系开发者")
	CHECK_SIGN_ERROR, 
	@Desc(code = 44008, msg = "未知的二维码")
	ERROR_QRCODE_TYPE
}
