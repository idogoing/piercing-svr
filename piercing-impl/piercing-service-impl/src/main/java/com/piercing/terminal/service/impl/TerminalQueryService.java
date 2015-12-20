package com.piercing.terminal.service.impl;

import java.util.List;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import com.flash.base.tool.page.Page;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.piercing.terminal.domain.Terminal;
import com.piercing.terminal.mapper.TerminalMapper;
import com.piercing.terminal.tool.query.TerminalQuery;

import tk.mybatis.mapper.entity.Example;
import tk.mybatis.mapper.entity.Example.Criteria;

@Service("terminalQueryService")
public class TerminalQueryService {
	
	@Resource(name = "terminalMapper")
	private TerminalMapper mapper;
	
	public Page<Terminal> doQuery(TerminalQuery query){
		//准备分页参数
		query.preparePageInfo();
		Example example = new Example(Terminal.class);
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
		//appid
		if(null != query.getAppid()) criteria.andEqualTo("appid", query.getAppid());
		//secret
		if(null != query.getSecret()) criteria.andEqualTo("secret", query.getSecret());
		//type
		if(null != query.getType() && null == query.getTypeIds()) criteria.andEqualTo("type", query.getType());

		
		//批量 ids
		if(null != query.getIds() && null == query.getId()) criteria.andIn("id", query.getIds());
		//批量 typeIds
		if(null != query.getTypeIds() && null == query.getType()) criteria.andIn("type", query.getTypeIds());
		
		//设置启示页面
		PageHelper.startPage(query.getPageNum(), query.getPageSize());
		//处理排序逻辑
		List<Terminal> list = this.mapper.selectByExample(example);
		PageInfo<Terminal> pageInfo = new PageInfo<>(list);
		Page<Terminal> page = new Page<>(pageInfo.getPageNum(), pageInfo.getPageSize(), (int) pageInfo.getTotal(), pageInfo.getList());
		return page;
	}
}
