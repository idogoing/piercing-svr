package com.piercing.platform.service.impl;

import java.util.List;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import com.flash.base.tool.page.Page;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.piercing.platform.domain.Platform;
import com.piercing.platform.mapper.PlatformMapper;
import com.piercing.platform.tool.query.PlatformQuery;

import tk.mybatis.mapper.entity.Example;
import tk.mybatis.mapper.entity.Example.Criteria;

@Service("platformQueryService")
public class PlatformQueryService {
	
	@Resource(name = "platformMapper")
	private PlatformMapper mapper;
	
	public Page<Platform> doQuery(PlatformQuery query){
		//准备分页参数
		query.preparePageInfo();
		Example example = new Example(Platform.class);
		Criteria criteria = example.createCriteria();
		//createTime区间
		if(null != query.getCreateTimeStart() && null != query.getCreateTimeEnd()){
			criteria.andBetween("createTime", query.getCreateTimeStart(), query.getCreateTimeEnd());
		}else if(null != query.getCreateTimeStart() && null == query.getCreateTimeEnd()){
			criteria.andGreaterThan("createTime", query.getCreateTimeStart());//大于某个时间点
		}else if(null != query.getCreateTimeEnd() && null == query.getCreateTimeStart()){
			criteria.andLessThan("createTime", query.getCreateTimeEnd());
		}else{
			//不根据时间来查询
		}
		//准备sql语句 真正的搜索业务开始
		//id
		if(null != query.getId() && null == query.getIds()) criteria.andEqualTo("id", query.getId());
		//platformCode
		if(null != query.getPlatformCode() && null == query.getPlatformCode()) criteria.andEqualTo("platformCode", query.getPlatformCode());
		//name
		if(null != query.getName() && null == query.getNameLike()) criteria.andEqualTo("name", query.getName());
		//website
		if(null != query.getWebsite() && null == query.getWebsiteLike()) criteria.andEqualTo("website", query.getWebsite());
		//mobile
		if(null != query.getMobile()) criteria.andEqualTo("mobile", query.getMobile());
		//url
		if(null != query.getUrl()) criteria.andEqualTo("url", query.getUrl());
		
		//批量 ids
		if(null != query.getIds() && null == query.getId()) criteria.andIn("id", query.getIds());
		//nameLike
		if(null != query.getNameLike() && null == query.getName()) criteria.andLike("name", query.getNameLike());
		//wbesiteLike
		if(null != query.getWebsiteLike() && null == query.getWebsiteLike()) criteria.andLike("website", query.getWebsiteLike());
		//设置启示页面
		PageHelper.startPage(query.getPageNum(), query.getPageSize());
		//处理排序逻辑
		List<Platform> list = this.mapper.selectByExample(example);
		PageInfo<Platform> pageInfo = new PageInfo<>(list);
		Page<Platform> page = new Page<>(pageInfo.getPageNum(), pageInfo.getPageSize(), (int) pageInfo.getTotal(), pageInfo.getList());
		return page;
	}
}
