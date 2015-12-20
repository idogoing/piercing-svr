package com.piercing.web.webapi.controller.check;

import javax.annotation.Resource;
import javax.validation.Valid;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.flash.code.CheckResult;
import com.flash.commons.bean.BeanAndDtoTransfer;
import com.flash.exception.ServiceException;
import com.piercing.qrcode.dto.QrcodeCheckDto;
import com.piercing.qrcode.service.QrcodeService;
import com.piercing.web.framework.WebApiBaseController;
import com.piercing.web.webapi.cmd.QrcodeCheckCommand;

@Controller
@RequestMapping(value = "/code")
public class QrcodeController extends WebApiBaseController{
	
	private static final Logger LOGGER = LoggerFactory.getLogger(QrcodeController.class);
	
	@Resource(name = "qrcodeService")
	private QrcodeService qrcodeService;
	
	@RequestMapping(value = "/ping", method = RequestMethod.GET)
	public @ResponseBody CheckResult<?> ping(){
		LOGGER.debug("通了");
		return new CheckResult<>(200, "通信检测成功");
	}
	
	@RequestMapping(value = "/check", method = RequestMethod.GET)
	public @ResponseBody CheckResult<?> checkQrcode(@Valid QrcodeCheckCommand checkQrcode) throws ServiceException {
		LOGGER.debug("核销请求触发，code={}",checkQrcode.getCode());
		QrcodeCheckDto checkQrcodeDto = BeanAndDtoTransfer.transOneToAnother(checkQrcode, QrcodeCheckDto.class);
		CheckResult<?> checkResult = this.qrcodeService.checkOneCode(checkQrcodeDto);
		return checkResult;
	}
}
