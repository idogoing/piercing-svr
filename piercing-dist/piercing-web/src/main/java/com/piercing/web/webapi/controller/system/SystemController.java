package com.piercing.web.webapi.controller.system;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.flash.response.CheckUpdateTemplate;
import com.piercing.web.framework.WebApiBaseController;
import com.piercing.web.response.BaseResponse;

@Controller
@RequestMapping(value = "/system")
public class SystemController extends WebApiBaseController{

	private static final Logger LOGGER = LoggerFactory.getLogger(SystemController.class);
	
	@RequestMapping(value = "/check_update", method = RequestMethod.GET)
	public @ResponseBody BaseResponse<?> checkUpdate(){
		LOGGER.debug("检测更新");
		CheckUpdateTemplate result =  new CheckUpdateTemplate();
		result.setForceUpdate(0);
		result.setRedirect(null);
		result.setVersion("1.0");
		return BaseResponse.success(result);
	}
}
