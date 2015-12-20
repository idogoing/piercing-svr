package com.piercing.web.webapi.controller.check;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.piercing.web.framework.WebApiBaseController;
import com.piercing.web.response.BaseResponse;

@Controller
@RequestMapping(value = "/check")
public class CheckController extends WebApiBaseController {
	
	@RequestMapping(value = "/{appid}/{secret}")
	public @ResponseBody BaseResponse<?> onsaleProducts(@PathVariable String appId, @PathVariable String secret){
		
		return BaseResponse.success();
	}
	@RequestMapping(value = "/ping")
	public @ResponseBody BaseResponse<?> ping(){
		
		return BaseResponse.success("success",null);
	}
	
}
