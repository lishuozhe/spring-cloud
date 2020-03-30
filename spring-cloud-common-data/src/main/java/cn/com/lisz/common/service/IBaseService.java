package cn.com.lisz.common.service;

import java.io.Serializable;
import java.util.List;
import java.util.Optional;

import cn.com.lisz.common.data.dao.IBaseDao;
import cn.com.lisz.common.data.entity.BaseEntity;
import cn.com.lisz.common.model.BaseModel;
import cn.com.lisz.common.model.web.PaggingModel;
import cn.com.lisz.common.model.web.RequestCondition;

/**
 * 通用接口
 * 
 * @author lisz
 *
 * @param <T>
 * @param <ID>
 * @param <TDao>
 */
public interface IBaseService<TEntity extends BaseEntity, ID extends Serializable, TModel extends BaseModel, TDao extends IBaseDao<TEntity, ID>> {

	/**
	 * 添加数据
	 *
	 * @param model
	 *            模型类对象
	 * @return
	 */
	ID add(TModel model);

	/**
	 * 删除数据
	 *
	 * @param ids
	 *            主键值
	 * @param UserModel
	 *            登录用户
	 */
	boolean delete(List<RequestCondition> conditions);

	/**
	 * 编辑数据
	 *
	 * @param model
	 *            模型类对象
	 * @param UserModel
	 *            登录用户
	 * @return
	 */
	boolean edit(TModel model);

	/**
	 * 查看数据
	 *
	 * @param id
	 *            主键值
	 * @param UserModel
	 *            登录用户
	 * @return
	 */
	TModel get(ID id);

	/**
	 * 条件查询数据
	 *
	 * @param conditions
	 *            查询条件
	 * @param UserModel
	 *            登录用户
	 * @return
	 */
	Optional<TModel> get(List<RequestCondition> conditions);

	/**
	 * 数据是否存在
	 *
	 * @param conditions
	 *            查询条件
	 * @param UserModel
	 *            登录用户
	 * @return
	 */
	boolean exist(List<RequestCondition> conditions);

	/**
	 * 查询集合
	 *
	 * @param conditions
	 *            查询条件
	 * @param UserModel
	 *            登录用户
	 * @return
	 */
	List<TModel> list(List<RequestCondition> conditions);

	/**
	 * 数据分页取得
	 *
	 * @param conditions
	 *            查询条件
	 * @param page
	 *            页码
	 * @param size
	 *            分页尺寸
	 * @param UserModel
	 *            登录用户
	 * @return
	 */
	PaggingModel<TModel> pagging(List<RequestCondition> conditions, int page, int size);

}
