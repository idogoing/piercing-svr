package com.piercing.qrcode.service;

import com.flash.code.CheckResult;
import com.flash.exception.ServiceException;
import com.piercing.qrcode.dto.QrcodeCheckDto;

/**
 * 二维码相关接口
 * @author leon
 *
 */
public interface QrcodeService {

	public CheckResult<?> checkOneCode(QrcodeCheckDto checkQrcodeDto) throws ServiceException;

}
