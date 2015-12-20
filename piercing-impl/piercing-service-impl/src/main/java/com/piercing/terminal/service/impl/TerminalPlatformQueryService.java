package com.piercing.terminal.service.impl;

import java.util.List;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import com.flash.base.tool.page.Page;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.piercing.terminal.domain.TerminalPlatform;
import com.piercing.terminal.mapper.TerminalPlatformMapper;
import com.piercing.terminal.tool.query.TerminalPlatformQuery;

import tk.mybatis.mapper.entity.Example;
import tk.mybatis.mapper.entity.Example.Criteria;

@Service("terminalPlatformQueryService")
public class TerminalPlatformQueryService {
	
	@Resource(name = "terminalPlatformMapper")
	private TerminalPlatformMapper mapper;
	
	public Page<TerminalPlatform> doQuery(TerminalPlatformQuery query){
		//准备分页参数
		query.preparePageInfo();
		Example example = new Example(TerminalPlatform.class);
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
		//terminalId
		if(null != query.getTerminalId() && null == query.getTerminalIds()) criteria.andEqualTo("terminalId", query.getTerminalId());
		//terminalCode
		if(null != query.getTerminalCode()) criteria.andEqualTo("terminalCode", query.getTerminalCode());
		//platformId
		if(null != query.getPlatformId() && null == query.getPlatformIds()) criteria.andEqualTo("platformId", query.getPlatformId());
		//platformCode
		if(null != query.getPlatformCode()) criteria.andEqualTo("platformCode", query.getPlatformCode());
		
		//批量 ids
		if(null != query.getIds() && null == query.getId()) criteria.andIn("id", query.getIds());
		//terminalIds
		if(null != query.getTerminalIds() && null == query.getTerminalId()) criteria.andIn("terminalId", query.getTerminalIds());
		//platformIds
		if(null != query.getPlatformIds() && null == query.getPlatformId()) criteria.andIn("platformid", query.getPlatformIds());
		//设置启示页面
		PageHelper.startPage(query.getPageNum(), query.getPageSize());
		//处理排序逻辑
		List<TerminalPlatform> list = this.mapper.selectByExample(example);
		PageInfo<TerminalPlatform> pageInfo = new PageInfo<>(list);
		Page<TerminalPlatform> page = new Page<>(pageInfo.getPageNum(), pageInfo.getPageSize(), (int) pageInfo.getTotal(), pageInfo.getList());
		return page;
	}
}
