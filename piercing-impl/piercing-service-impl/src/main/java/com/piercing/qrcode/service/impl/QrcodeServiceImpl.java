package com.piercing.qrcode.service.impl;

import javax.annotation.Resource;

import org.apache.http.HttpException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import com.flash.code.CheckResult;
import com.flash.code.template.CouponTemplate;
import com.flash.code.template.MeetingTemplate;
import com.flash.commons.http.HttpClientUtils;
import com.flash.commons.json.JsonHelper;
import com.flash.commons.md5.MD5Utils;
import com.flash.exception.ServiceException;
import com.piercing.platform.dto.PlatformBaseDto;
import com.piercing.platform.service.PlatformService;
import com.piercing.qrcode.constant.QrcodeConstant;
import com.piercing.qrcode.dto.QrcodeCheckDto;
import com.piercing.qrcode.exception.QrcodeServiceException;
import com.piercing.qrcode.exception.QrcodeServiceExceptionCode;
import com.piercing.qrcode.service.QrcodeService;
import com.piercing.terminal.dto.TerminalDetail;
import com.piercing.terminal.service.TerminalService;

@Service("qrcodeService")
public class QrcodeServiceImpl implements QrcodeService{

	private static final Logger LOGGER = LoggerFactory.getLogger("核销Service");
	@Resource(name = "platformService")
	private PlatformService platformService;
	@Resource(name = "terminalService")
	private TerminalService terminalSerivce;
	
	@Override
	public CheckResult<?> checkOneCode(QrcodeCheckDto checkQrcodeDto) throws ServiceException {
		//进行sign校验
//		this.checkSign(checkQrcodeDto);
		LOGGER.debug("核销请求通过规则校验，参数均正确");
		//获取平台信息、终端信息
		TerminalDetail terminalDetail = this.terminalSerivce.judgePlatformByCodeAndSecret(checkQrcodeDto.getCode(),checkQrcodeDto.getAppid(),checkQrcodeDto.getSecret());
		//核销此券
		CheckResult<?> checkResult = this.checkCode(terminalDetail,checkQrcodeDto.getCode());
		//更新核销次数
		this.terminalSerivce.addCount(terminalDetail.getId());
		return checkResult;
	}

	/**
	 * 校验加密规则
	 * @param checkQrcodeDto
	 * @throws QrcodeServiceException
	 */
	private void checkSign(QrcodeCheckDto checkQrcodeDto) throws QrcodeServiceException {
		//sign = md5(appid+secret+code+time)
		String assertSign = MD5Utils.md5(checkQrcodeDto.getAppid()+checkQrcodeDto.getSecret()+checkQrcodeDto.getCode()+checkQrcodeDto.getTime());
		if(!assertSign.equals(checkQrcodeDto.getSign())){
			LOGGER.error("加密校验失败,请求参数为{}",JsonHelper.transObjToJsonString(checkQrcodeDto));
			throw new QrcodeServiceException(QrcodeServiceExceptionCode.CHECK_SIGN_ERROR);
		}
	}

	/**
	 * 核销一个请求
	 * @param <T>
	 * @param terminalDetail
	 * @param code
	 * @throws QrcodeServiceException
	 */
	private CheckResult<?> checkCode(TerminalDetail terminalDetail, String code) throws QrcodeServiceException {
		PlatformBaseDto platformInfo = terminalDetail.getPlatformInfo();
		//获取核销使用的url
		String checkUrl = this.prepareCheckUrl(terminalDetail,code);
		try {
			long checkStart = System.currentTimeMillis();
			String checkResult = HttpClientUtils.get(checkUrl);
			if(!checkResult.startsWith("{") || !checkResult.endsWith("}")){
				LOGGER.error("请求第三方平台出现异常，第三方平台未能按照api协议定义返回数据，返回数据并非json数据，异常平台名称为:[{}],返回数据{}",terminalDetail.getPlatformInfo().getName(),checkResult);
				throw new QrcodeServiceException(QrcodeServiceExceptionCode.PLATFORM_API_ERROR);
			}
			//TODO check log
			LOGGER.debug("核销请求发送成功，返回结果:{},耗时[{}秒]",checkResult,System.currentTimeMillis() - checkStart);
			CheckResult slapdashCheckResult = JsonHelper.transJsonStringToObj(checkResult, CheckResult.class);
			String checkMsg = slapdashCheckResult.getMsg();
			int checkStatus = slapdashCheckResult.getCode();
			int type = slapdashCheckResult.getType();
			if(checkStatus == 200){//核销成功
				//TODO 根据不同情况 去返回数据
				if(type == QrcodeConstant.CODE_TYPE_ON_SELL_COUPON){//优惠券
					CheckResult<CouponTemplate> result = JsonHelper.transJsonStringToObj(checkResult, CheckResult.class,CouponTemplate.class);
					LOGGER.debug("[核销成功]第三方平台返回成功信息,msg={}，类型:优惠券",checkMsg);
					return result;
				}else if (type == QrcodeConstant.CODE_TYPE_MEETING) {//会议签到
					CheckResult<MeetingTemplate> result = JsonHelper.transJsonStringToObj(checkResult, CheckResult.class,MeetingTemplate.class);
					LOGGER.debug("[核销成功]第三方平台返回成功信息,msg={}，类型:会议",checkMsg);
					return result;
				}else if(type == QrcodeConstant.CODE_TYPE_GROUP_BUY_COUPON){//团购券
					LOGGER.info("未知的二维码");
					throw new QrcodeServiceException(QrcodeServiceExceptionCode.ERROR_QRCODE_TYPE);
				}else if(type == QrcodeConstant.CODE_TYPE_MOVIE_TICKET){//电影票
					LOGGER.info("未知的二维码");
					throw new QrcodeServiceException(QrcodeServiceExceptionCode.ERROR_QRCODE_TYPE);
				}else{
					LOGGER.info("未知的二维码");
					throw new QrcodeServiceException(QrcodeServiceExceptionCode.ERROR_QRCODE_TYPE);
				}
			}else{//失败的卡券
				if(type == QrcodeConstant.CODE_TYPE_ON_SELL_COUPON){//优惠券
					CheckResult<CouponTemplate> result = JsonHelper.transJsonStringToObj(checkResult, CheckResult.class,CouponTemplate.class);
					LOGGER.error("[核销失败]，第三方返回核销失败的信息,原因:{},类型:优惠券",checkMsg);
					return result;
				}else if (type == QrcodeConstant.CODE_TYPE_MEETING) {//会议签到
					CheckResult<MeetingTemplate> result = JsonHelper.transJsonStringToObj(checkResult,CheckResult.class, MeetingTemplate.class);
					LOGGER.debug("[核销失败]第三方平台返回成功信息,msg={}，类型:会议",checkMsg);
					return result;
				}else if(type == QrcodeConstant.CODE_TYPE_GROUP_BUY_COUPON){//团购券
					LOGGER.info("未知的二维码");
					throw new QrcodeServiceException(QrcodeServiceExceptionCode.ERROR_QRCODE_TYPE);
				}else if(type == QrcodeConstant.CODE_TYPE_MOVIE_TICKET){//电影票
					LOGGER.info("未知的二维码");
					throw new QrcodeServiceException(QrcodeServiceExceptionCode.ERROR_QRCODE_TYPE);
				}else{
					LOGGER.info("未知的二维码");
					throw new QrcodeServiceException(QrcodeServiceExceptionCode.ERROR_QRCODE_TYPE);
				}
			}
		} catch (HttpException e) {
			LOGGER.error("向第三方发送核销请求中遇到http请求异常,{}",e.getMessage());
			throw new QrcodeServiceException(QrcodeServiceExceptionCode.HTTP_ERROR);
		}
		
	}

	/**
	 * 准备请求的url
	 * @param terminalDetail
	 * @param code
	 * @return
	 */
	private String prepareCheckUrl(TerminalDetail terminalDetail, String code) {
		String tmpUrl = terminalDetail.getPlatformInfo().getUrl();
		String baseUrl = null;
		if(tmpUrl.contains("?")){
			baseUrl = tmpUrl+"&";
		}else{
			baseUrl = tmpUrl+"?";
		}
		//计算加密值
		String sign = "";//TODO 加密
		
		String resultUrl = baseUrl+"appid="+terminalDetail.getAppid()+"&secret="+terminalDetail.getSecret()+"&code="+code+"&time="+System.currentTimeMillis()+"&sign="+sign;
		return resultUrl;
	}

}
