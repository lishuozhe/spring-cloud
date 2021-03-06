package cn.com.lisz.consumer.admin.service;

import java.util.List;


import cn.com.lisz.common.model.base.RoleModel;
import cn.com.lisz.common.model.web.PaggingModel;

public interface IRoleService {

	/**
	 * 新增
	 *
	 * @param model
	 * @return
	 */
	public Long add(RoleModel model);

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
	public boolean edit(RoleModel model);

	/**
	 * 查看
	 * 
	 * @param id
	 * @return
	 */
	public RoleModel get(Long id);

	/**
	 * 条件查看
	 * 
	 * @param conditions
	 * @return
	 */
	public RoleModel findOne(RoleModel model);

	/**
	 * 判断存在
	 * 
	 * @param conditions
	 * @return
	 */
	public boolean exist(RoleModel model);

	/**
	 * 条件查询
	 * 
	 * @param conditions
	 * @return
	 */
	public List<RoleModel> list(RoleModel model);

	/**
	 * 分页查询
	 * 
	 * @param conditions
	 * @return
	 */
	public PaggingModel<RoleModel> page(Integer size, Integer page, RoleModel model);
}
