package cn.com.lisz.consumer.admin.service;

import java.util.List;


import cn.com.lisz.common.model.base.PermissionModel;
import cn.com.lisz.common.model.web.PaggingModel;

public interface IPermissionService {

	/**
	 * 新增
	 *
	 * @param model
	 * @return
	 */
	public Long add(PermissionModel model);

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
	public boolean edit(PermissionModel model);

	/**
	 * 查看
	 * 
	 * @param id
	 * @return
	 */
	public PermissionModel get(Long id);

	/**
	 * 条件查看
	 * 
	 * @param conditions
	 * @return
	 */
	public PermissionModel findOne(PermissionModel model);

	/**
	 * 判断存在
	 * 
	 * @param conditions
	 * @return
	 */
	public boolean exist(PermissionModel model);

	/**
	 * 条件查询
	 * 
	 * @param conditions
	 * @return
	 */
	public List<PermissionModel> list(PermissionModel model);

	/**
	 * 分页查询
	 * 
	 * @param conditions
	 * @return
	 */
	public PaggingModel<PermissionModel> page(Integer size, Integer page, PermissionModel model);
}
