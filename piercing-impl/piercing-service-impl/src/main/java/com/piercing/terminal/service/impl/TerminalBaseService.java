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
import com.piercing.terminal.domain.Terminal;
import com.piercing.terminal.dto.TerminalBaseDto;
import com.piercing.terminal.mapper.TerminalMapper;
import com.piercing.terminal.tool.query.TerminalQuery;

/**
 * 基础的service 提供基础的增删改查
 * @author leon
 *
 */
public class TerminalBaseService {
	private static final Logger LOGGER = LoggerFactory.getLogger(TerminalBaseService.class);
	
	@Resource(name = "terminalMapper")
	protected TerminalMapper mapper;

	@Resource(name = "terminalQueryService")
	private TerminalQueryService queryService;

	/**
	 * 搜索
	 * 
	 * @return
	 */
	public Page<TerminalBaseDto> query(TerminalQuery query) {
		Page<Terminal> pageData = this.queryService.doQuery(query);
		if (null == pageData || pageData.getTotalCount() == 0) {
			LOGGER.info("查询到0条数据,查询对象{}", query.toString());
			Page<TerminalBaseDto> result = new Page<TerminalBaseDto>();
			BeanUtils.copyProperties(pageData, result, new String[] { "pageData" });
			return result;
		}
		LOGGER.info("根据条件查询成功，共有{}条记录符合条件", pageData.getTotalCount());
		Page<TerminalBaseDto> pageResult = new Page<TerminalBaseDto>();
		BeanUtils.copyProperties(pageData, pageResult, new String[] { "pageData" });
		List<TerminalBaseDto> terminalBaseDtoList = BeanAndDtoTransfer.transOneListToAnoterList(pageData.getPageData(), TerminalBaseDto.class);
		pageResult.setPageData(terminalBaseDtoList);
		LOGGER.debug("当前线程名称{}", Thread.currentThread().getName());
		return pageResult;
	}

	/**
	 * 查找方法
	 * 
	 * @param id
	 * @return
	 */
	protected Terminal getByPk(Integer id) {
		LOGGER.debug("查询id为{}的Shop信息开始", id);
		long start = System.currentTimeMillis();
		Terminal data = this.mapper.selectByPrimaryKey(id);
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
	public TerminalBaseDto getById(Integer id) {
		Terminal data = this.getByPk(id);
		return BeanAndDtoTransfer.transOneToAnother(data, TerminalBaseDto.class);
	}

	/**
	 * 根据shopIds批量获取shop信息
	 * 
	 * @param ids
	 * @return
	 */
	public Map<Integer, TerminalBaseDto> getByIds(Collection<Integer> ids) {
		Assert.notEmpty(ids, "非法的调用,传入的id list不能为空");
		TerminalQuery query = new TerminalQuery();
		query.setIds(new ArrayList<Object>(ids));
		Page<TerminalBaseDto> page = this.query(query);
		Map<Integer, TerminalBaseDto> resultMap = new HashMap<Integer, TerminalBaseDto>();
		if (null != page.getPageData() && page.getPageData().size() > 0) {
			List<TerminalBaseDto> list = page.getPageData();
			for (TerminalBaseDto t : list) {
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
	public List<TerminalBaseDto> getListByIds(Collection<Integer> ids){
		Map<Integer, TerminalBaseDto> map = this.getByIds(ids);
		List<TerminalBaseDto> values = (List<TerminalBaseDto>) map.values();
		return values;
	}

	/**
	 * 保存方法
	 * @param data
	 * @return
	 */
	protected Terminal save(Terminal data) {
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
	public Terminal update(Terminal data){
		data.setUpdateTime(new Timestamp(System.currentTimeMillis()));
		this.mapper.updateByPrimaryKey(data);
		return data;
	}
	
	/**
	 * 插入或者更新
	 * @param data
	 * @return
	 */
	protected Terminal saveOrUpdate(Terminal data){
		if(data.getId() != null){//
			LOGGER.debug("id不为空，进行修改操作");
			this.update(data);
			return data;
		}else{
			Terminal tmpTermina = this.save(data);
			return tmpTermina;
		}
	}
}
