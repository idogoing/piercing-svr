package com.piercing.terminal.service.impl;

import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.BeanUtils;
import org.springframework.util.Assert;

import com.flash.base.tool.page.Page;
import com.flash.commons.bean.BeanAndDtoTransfer;
import com.piercing.terminal.domain.TerminalPlatform;
import com.piercing.terminal.mapper.TerminalPlatformMapper;
import com.piercing.terminal.o.dto.TerminalPlatformBaseDto;
import com.piercing.terminal.tool.query.TerminalPlatformQuery;

public class TerminalPlatformBaseService {
private static final Logger LOGGER = LoggerFactory.getLogger(TerminalBaseService.class);
	
	@Resource(name = "terminalPlatformMapper")
	protected TerminalPlatformMapper mapper;

	@Resource(name = "terminalPlatformQueryService")
	private TerminalPlatformQueryService queryService;

	/**
	 * 搜索
	 * 
	 * @return
	 */
	public Page<TerminalPlatformBaseDto> query(TerminalPlatformQuery query) {
		Page<TerminalPlatform> pageData = this.queryService.doQuery(query);
		if (null == pageData || pageData.getTotalCount() == 0) {
			LOGGER.info("查询到0条数据,查询对象{}", query.toString());
			Page<TerminalPlatformBaseDto> result = new Page<TerminalPlatformBaseDto>();
			BeanUtils.copyProperties(pageData, result, new String[] { "pageData" });
			return result;
		}
		LOGGER.info("根据条件查询成功，共有{}条记录符合条件", pageData.getTotalCount());
		Page<TerminalPlatformBaseDto> pageResult = new Page<TerminalPlatformBaseDto>();
		BeanUtils.copyProperties(pageData, pageResult, new String[] { "pageData" });
		List<TerminalPlatformBaseDto> pageDataList = BeanAndDtoTransfer.transOneListToAnoterList(pageData.getPageData(), TerminalPlatformBaseDto.class);
		pageResult.setPageData(pageDataList);
		return pageResult;
	}

	/**
	 * 查找方法
	 * 
	 * @param id
	 * @return
	 */
	protected TerminalPlatform getByPk(Integer id) {
		LOGGER.debug("查询id为{}的Shop信息开始", id);
		long start = System.currentTimeMillis();
		TerminalPlatform data = this.mapper.selectByPrimaryKey(id);
		long end = System.currentTimeMillis();
		if (null == data) {
			LOGGER.debug("查询id为{}的数据成功,该数据不存在,耗时{}ms", id, end - start);
		} else {
			LOGGER.debug("查询id为{}的数据成功,耗时{}ms", id, end - start);
		}
		return data;
	}

	/**
	 * 对外的根据id获取超市信息
	 * 
	 * @param id
	 * @return
	 */
	public TerminalPlatformBaseDto getById(Integer id) {
		TerminalPlatform data = this.getByPk(id);
		return BeanAndDtoTransfer.transOneToAnother(data, TerminalPlatformBaseDto.class);
	}

	/**
	 * 根据shopIds批量获取shop信息
	 * 
	 * @param ids
	 * @return
	 */
	public Map<Integer, TerminalPlatformBaseDto> getByIds(Collection<Integer> ids) {
		Assert.notEmpty(ids, "非法的调用,传入的id list不能为空");
		TerminalPlatformQuery query = new TerminalPlatformQuery();
		query.setIds(new ArrayList<Object>(ids));
		Page<TerminalPlatformBaseDto> page = this.query(query);
		Map<Integer, TerminalPlatformBaseDto> resultMap = new HashMap<Integer, TerminalPlatformBaseDto>();
		if (null != page.getPageData() && page.getPageData().size() > 0) {
			List<TerminalPlatformBaseDto> list = page.getPageData();
			for (TerminalPlatformBaseDto t : list) {
				resultMap.put(t.getId(), t);
			}
		}
		return resultMap;
	}
	/**
	 * 根据id列表查询数据列表
	 * @param ids
	 * @return
	 */
	public List<TerminalPlatformBaseDto> getListByIds(Collection<Integer> ids){
		Map<Integer, TerminalPlatformBaseDto> map = this.getByIds(ids);
		List<TerminalPlatformBaseDto> values = (List<TerminalPlatformBaseDto>) map.values();
		return values;
	}

	/**
	 * 保存方法
	 * @param data
	 * @return
	 */
	protected TerminalPlatform save(TerminalPlatform data) {
		LOGGER.debug("插入一条数据开始,id={}", data.getId());
		long start = System.currentTimeMillis();
		int selective = this.mapper.insertSelective(data);
		long end = System.currentTimeMillis();
		if (selective < 1) {
			LOGGER.debug("插入数据失败,原因未知,耗时{}ms", end - start);
		} else {
			LOGGER.debug("插入数据到数据库成功,id为{},耗时{}ms", data.getId(), end - start);
		}
		return data;
	}
	
	/**
	 * 更新方法
	 * @param data
	 * @return
	 */
	public TerminalPlatform update(TerminalPlatform data){
		data.setUpdateTime(new Timestamp(System.currentTimeMillis()));
		this.mapper.updateByPrimaryKey(data);
		return data;
	}
	
	/**
	 * 插入或者更新
	 * @param data
	 * @return
	 */
	protected TerminalPlatform saveOrUpdate(TerminalPlatform data){
		if(data.getId() != null){//
			LOGGER.debug("id不为空，进行修改操作");
			this.update(data);
			return data;
		}else{
			TerminalPlatform tmpTermina = this.save(data);
			return tmpTermina;
		}
	}
}
