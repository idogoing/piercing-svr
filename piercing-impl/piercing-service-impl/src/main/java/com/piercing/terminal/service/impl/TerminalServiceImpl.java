package com.piercing.terminal.service.impl;

import java.sql.Timestamp;
import java.util.List;

import javax.annotation.Resource;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import com.flash.base.tool.page.Page;
import com.flash.commons.bean.BeanAndDtoTransfer;
import com.flash.commons.json.JsonHelper;
import com.flash.exception.ServiceException;
import com.flash.service.redis.RedisService;
import com.piercing.platform.dto.PlatformBaseDto;
import com.piercing.platform.service.PlatformService;
import com.piercing.qrcode.o.bo.Qrcode;
import com.piercing.qrcode.tool.utils.QrcodeUtils;
import com.piercing.terminal.constant.TerminalConstant;
import com.piercing.terminal.domain.Terminal;
import com.piercing.terminal.dto.TerminalBaseDto;
import com.piercing.terminal.dto.TerminalDetail;
import com.piercing.terminal.exception.TerminalServiceException;
import com.piercing.terminal.exception.TerminalServiceExceptionCode;
import com.piercing.terminal.mapper.TerminalMapper;
import com.piercing.terminal.o.bo.TerminalRedisCache;
import com.piercing.terminal.service.TerminalPlatformService;
import com.piercing.terminal.service.TerminalService;
import com.piercing.terminal.tool.query.TerminalQuery;

@Service("terminalService")
public class TerminalServiceImpl extends TerminalBaseService implements TerminalService{

	private static final Logger LOGGER = LoggerFactory.getLogger(TerminalServiceImpl.class);
	
	@Resource(name = "terminalMapper")
	private TerminalMapper mapper;
	
	@Resource(name = "platformService")
	private PlatformService platformService;
	
	@Resource(name = "terminalPlatformService")
	private TerminalPlatformService terminalPlatformService;
	
	@Resource(name = "redisService")
	private RedisService redisService;
	

	@Override
	public TerminalDetail judgePlatformByCodeAndSecret(String code, String appid, String secret) throws ServiceException {
		
		TerminalBaseDto terminal = this.getTerminalByAppidAndSecret(appid,secret);
		//查询所有终端
		List<PlatformBaseDto> platformList = this.platformService.getPlatformListByTerminalId(terminal.getId());
		//根据code前缀找到目标的第三方验证平台
		Qrcode qrcode = QrcodeUtils.transCodeToQrcode(code);
		PlatformBaseDto targetPlatform = filterTargetPlatform(platformList, qrcode);
		
		//准备返回数据
		TerminalDetail terminalDetail = new TerminalDetail();
		terminalDetail.setAppid(appid);
		terminalDetail.setSecret(secret);
		terminalDetail.setCount(terminal.getCount());
		terminalDetail.setTerminalCode(terminal.getTerminalCode());
		terminalDetail.setId(terminal.getId());
		terminalDetail.setType(terminal.getType());
		terminalDetail.setPlatformInfo(targetPlatform);
		return terminalDetail;
	}


	private TerminalBaseDto getTerminalByAppidAndSecret(String appid, String secret) throws TerminalServiceException {
		try{
			TerminalRedisCache cache = this.getTerminalByAppidAndSecretFromRedis(appid, secret);
			TerminalBaseDto terminal = BeanAndDtoTransfer.transOneToAnother(cache, TerminalBaseDto.class);
			return terminal;
		}catch(Exception e){
			TerminalBaseDto terminal = getTerminalByAppidAndSecretFromDB(appid, secret);
			this.putTerminalCacheToRedis(terminal);
			return terminal;
		}
	}


	/**
	 * 将一条数据存进redis
	 * @param terminal
	 */
	private void putTerminalCacheToRedis(TerminalBaseDto terminal) {
		TerminalRedisCache cache = new TerminalRedisCache();
		cache.setAppid(terminal.getAppid());
		cache.setSecret(terminal.getSecret());
		cache.setCount(terminal.getCount());
		cache.setTerminalCode(terminal.getTerminalCode());
		cache.setId(terminal.getId());
		cache.setType(terminal.getType());
		cache.setExpireTime(System.currentTimeMillis()+TerminalConstant.REDIS_CACHE_EXPRE_SECONDS);
		this.redisService.hset(TerminalConstant.TERMINAL_HASH_APPID_KEY_BY_APPID, TerminalConstant.TERMINAL_HASH_APPID_KEY_BY_APPID_BASE_FEILD+terminal.getAppid(), cache);
	}


	/**
	 * 从数据库中获取appid和secret指定的数据
	 * @param appid
	 * @param secret
	 * @return
	 * @throws TerminalServiceException
	 */
	private TerminalBaseDto getTerminalByAppidAndSecretFromDB(String appid, String secret)
			throws TerminalServiceException {
		TerminalQuery query = new TerminalQuery();
		query.setAppid(appid);
		query.setSecret(secret);
		Page<TerminalBaseDto> pageResult = this.query(query);
		if(pageResult.getTotalCount() > 0 && pageResult.getPageData() != null && pageResult.getPageData().size() > 0 ){
			TerminalBaseDto terminalBaseDto = pageResult.getPageData().get(0);
			LOGGER.info("找到appid={}和secret={}的核销机具，这是{}次使用",appid,secret,terminalBaseDto.getCount());
			return terminalBaseDto;
		}else{
			LOGGER.error("appid={}和secret={}的机具未找到",appid,secret);
			throw new TerminalServiceException(TerminalServiceExceptionCode.DEVICE_CAN_NOT_FOUND);
		}
	}


	/**
	 * 从redis种获取
	 * @param appid
	 * @param secret
	 * @return
	 * @throws TerminalServiceException 
	 */
	private TerminalRedisCache getTerminalByAppidAndSecretFromRedis(String appid, String secret) throws TerminalServiceException {
		String string = this.redisService.hget(TerminalConstant.TERMINAL_HASH_APPID_KEY_BY_APPID, TerminalConstant.TERMINAL_HASH_APPID_KEY_BY_APPID_BASE_FEILD+appid);
		if(StringUtils.isEmpty(string)){
			LOGGER.debug("从redis中取出终端appid={}的数据失败，开始尝试从数据库中取出,",appid);
			throw new TerminalServiceException(TerminalServiceExceptionCode.REDIS_NOT_FOUND_TERMINAL);
		}
		TerminalRedisCache cache = JsonHelper.transJsonStringToObj(string, TerminalRedisCache.class);
		
		if(cache.getExpireTime() < System.currentTimeMillis()){
			LOGGER.debug("redis中的终端appid＝{}的数据已经过期，删除旧缓存数据",appid);
			this.redisService.hdel(TerminalConstant.TERMINAL_HASH_APPID_KEY_BY_APPID, TerminalConstant.TERMINAL_HASH_APPID_KEY_BY_APPID_BASE_FEILD+appid);
			throw new TerminalServiceException(TerminalServiceExceptionCode.REDIS_CACHE_OVER_TIME);
		}
		if(!cache.getSecret().equals(secret)){
			LOGGER.error("从redis中取出的appid为{}的终端号，secret错误，正确secret={}",cache.getSecret());
			throw new TerminalServiceException(TerminalServiceExceptionCode.TERMINAL_SECRET_ERROR);
		}
		LOGGER.debug("从redis中取出平台缓存成功，取出平台appid={},secret={}",appid,secret);
		return cache;
	}


	private PlatformBaseDto filterTargetPlatform(List<PlatformBaseDto> platformList, Qrcode qrcode) throws TerminalServiceException {
		PlatformBaseDto targetPlatform = null;
		boolean exists = false;
		for (PlatformBaseDto platformBaseDto : platformList) {
			if(platformBaseDto.getPrefix().equals(qrcode.getPrefix())){
				targetPlatform = platformBaseDto;
				exists = true;
				break;
			}
		}
		if(exists){
			LOGGER.debug("找到第三方平台，平台名称为{},网址为{}",targetPlatform.getName(),targetPlatform.getWebsite());
			return targetPlatform;
		}else{
			LOGGER.error("现有平台无一与该码的前缀匹配,码为{},前缀为{}",qrcode.getValue(),qrcode.getPrefix());
			throw new TerminalServiceException(TerminalServiceExceptionCode.CODE_CAN_NOT_MATCHING_ANY_PLATFORM);
		}
		
	}


	@Override
	public void addCount(Integer terminalId) {
		Terminal terminal = this.getByPk(terminalId);
		terminal.setCount(terminal.getCount()+1);
		terminal.setUpdateTime(new Timestamp(System.currentTimeMillis()));
		this.update(terminal);
	}
}
