package cn.com.lisz.common.service;

import java.io.Serializable;

import cn.com.lisz.common.mapper.IBaseMapper;
import cn.com.lisz.common.model.BaseModel;

/**
 * 通用接口
 * 
 * @author lisz
 *
 * @param <T>
 * @param <ID>
 * @param <TDao>
 */
public interface IBaseService<T extends BaseModel, ID extends Serializable, TDao extends IBaseMapper<T, ID>> {

	/**
	 * 删除
	 * 
	 * @param id
	 * @return
	 */
	int delete(ID id);

	/**
	 * 保存
	 * 
	 * @param record
	 * @return
	 */
	int save(T record);

	/**
	 * 查看
	 * 
	 * @param id
	 * @return
	 */
	T get(ID id);

	/**
	 * 更新
	 * 
	 * @param record
	 * @return
	 */
	int update(T record);
}
