package cn.com.lisz.common.controller;

import java.io.Serializable;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import cn.com.lisz.common.data.entity.BaseEntity;
import cn.com.lisz.common.model.BaseModel;
import cn.com.lisz.common.model.web.PaggingModel;
import cn.com.lisz.common.model.web.RequestCondition;
import cn.com.lisz.common.service.IBaseService;
import cn.com.lisz.common.util.CollectionUtils;

public class BaseController<TEntity extends BaseEntity, ID extends Serializable, TModel extends BaseModel, TService extends IBaseService<TEntity, ID, TModel, ?>> {

	@Autowired
	protected IBaseService<TEntity, ID, TModel, ?> service;

	/**
	 * 新增
	 *
	 * @param model
	 * @return
	 */
	@RequestMapping(value = "/add", method = RequestMethod.POST)
	public ID add(@RequestBody TModel model) {
		return service.add(model);
	}

	/**
	 * 删除
	 *
	 * @param ids
	 * @param userModel,
	 *            传递处理用户创建的数据
	 * @return
	 */
	@RequestMapping(value = "/del", method = RequestMethod.POST)
	public boolean del(@RequestBody List<RequestCondition> conditions) {
		return service.delete(conditions);
	}

	/**
	 * 更新
	 *
	 * @param model
	 * @param userModel,
	 *            传递处理用户创建的数据
	 * @return
	 */
	@RequestMapping(value = "/edit", method = RequestMethod.POST)
	public boolean edit(@RequestBody TModel model) {
		return service.edit(model);
	}

	/**
	 * 查看
	 * 
	 * @param id
	 * @param userModel,
	 *            传递处理用户创建的数据
	 * @return
	 */
	@RequestMapping(value = "/get", method = RequestMethod.GET)
	public TModel get(@RequestParam ID id) {
		return service.get(id);
	}

	/**
	 * 条件查看
	 * 
	 * @param conditions
	 * @param userModel,
	 *            传递处理用户创建的数据
	 * @return
	 */
	@RequestMapping(value = "/findOne", method = RequestMethod.POST)
	public TModel findOne(@RequestBody List<RequestCondition> conditions) {
		if (!CollectionUtils.isEmpty(conditions)) {
			Optional<TModel> viewModel = service.get(conditions);
			return viewModel.orElse(null);
		}
		return null;
	}

	/**
	 * 判断存在
	 * 
	 * @param conditions
	 * @param userModel,
	 *            传递处理用户创建的数据
	 * @return
	 */
	@RequestMapping(value = "/exist", method = RequestMethod.POST)
	public boolean exist(@RequestBody List<RequestCondition> conditions) {
		if (!CollectionUtils.isEmpty(conditions)) {
			return service.exist(conditions);
		}
		return false;
	}

	/**
	 * 条件查询
	 * 
	 * @param conditions
	 * @param userModel,
	 *            传递处理用户创建的数据
	 * @return
	 */
	@RequestMapping(value = "/list", method = RequestMethod.POST)
	public List<TModel> list(@RequestBody List<RequestCondition> conditions) {
		if (!CollectionUtils.isEmpty(conditions)) {
			return service.list(conditions);
		}
		return null;
	}

	/**
	 * 分页查询
	 * 
	 * @param conditions
	 * @param userModel,
	 *            传递处理用户创建的数据
	 * @return
	 */
	@RequestMapping(value = "/page", method = RequestMethod.POST)
	public PaggingModel<TModel> page(@RequestParam(value = "pageSize", defaultValue = "5") Integer size,
			@RequestParam(value = "pageNum", defaultValue = "1") Integer page,
			@RequestBody List<RequestCondition> conditions) {
		if (!CollectionUtils.isEmpty(conditions)) {
			return service.pagging(conditions, page, size);
		}
		return null;
	}
}
