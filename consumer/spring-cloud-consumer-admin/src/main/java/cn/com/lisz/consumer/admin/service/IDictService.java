package cn.com.lisz.consumer.admin.service;

import java.util.List;


import cn.com.lisz.common.model.base.DictModel;
import cn.com.lisz.common.model.web.PaggingModel;

public interface IDictService {

	/**
	 * 新增
	 *
	 * @param model
	 * @return
	 */
	public Long add(DictModel model);

	/**
	 * 删除
	 *
	 * @param ids
	 * @return
	 */
	public boolean del(List<Long> ids);

	/**
	 * 更新
	 *
	 * @param model
	 * @return
	 */
	public boolean edit(DictModel model);

	/**
	 * 查看
	 * 
	 * @param id
	 * @return
	 */
	public DictModel get(Long id);

	/**
	 * 条件查看
	 * 
	 * @param conditions
	 * @return
	 */
	public DictModel findOne(DictModel model);

	/**
	 * 判断存在
	 * 
	 * @param conditions
	 * @return
	 */
	public boolean exist(DictModel model);

	/**
	 * 条件查询
	 * 
	 * @param conditions
	 * @return
	 */
	public List<DictModel> list(DictModel model);

	/**
	 * 分页查询
	 * 
	 * @param conditions
	 * @return
	 */
	public PaggingModel<DictModel> page(Integer size, Integer page, DictModel model);
}
