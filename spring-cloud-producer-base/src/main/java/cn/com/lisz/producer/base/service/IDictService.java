package cn.com.lisz.producer.base.service;

import java.util.List;

import com.github.pagehelper.PageInfo;

import cn.com.lisz.common.service.IBaseService;
import cn.com.lisz.producer.base.mapper.DictMapper;
import cn.com.lisz.producer.base.model.Dict;

public interface IDictService extends IBaseService<Dict, Long, DictMapper> {

	/**
	 * 查询一条数据
	 * 
	 * @param record
	 * @return
	 */
	Dict listOne(Dict record);

	/**
	 * 查询
	 * 
	 * @param record
	 * @return
	 */
	List<Dict> list(Dict record);

	/**
	 * 分页
	 * 
	 * @param record
	 * @return
	 */
	PageInfo<Dict> page(Integer pageSize, Integer pageNum, Dict record);
}
