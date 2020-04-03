package cn.com.lisz.consumer.admin.service;

import java.util.List;

import cn.com.lisz.common.model.base.UserModel;
import cn.com.lisz.common.model.web.PaggingModel;
import cn.com.lisz.common.model.web.ResultModel;

public interface IUserService {

	/**
	 * 新增
	 *
	 * @param model
	 * @return
	 */
	public ResultModel<UserModel> add(UserModel model);

	/**
	 * 授权
	 *
	 * @param model
	 * @return
	 */
	public ResultModel<UserModel> auth(UserModel model);

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
	public boolean edit(UserModel model);

	/**
	 * 查看
	 * 
	 * @param id
	 * @return
	 */
	public UserModel get(Long id);

	/**
	 * 条件查看
	 * 
	 * @param conditions
	 * @return
	 */
	public UserModel findOne(UserModel model);

	/**
	 * 判断存在
	 * 
	 * @param conditions
	 * @return
	 */
	public boolean exist(UserModel model);

	/**
	 * 条件查询
	 * 
	 * @param conditions
	 * @return
	 */
	public List<UserModel> list(UserModel model);

	/**
	 * 分页查询
	 * 
	 * @param conditions
	 * @return
	 */
	public PaggingModel<UserModel> page(Integer size, Integer page, UserModel model);
}
