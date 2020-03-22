package cn.com.lisz.common.mapper;

import java.io.Serializable;

import cn.com.lisz.common.model.BaseModel;

/**
 * 通用映射类
 * 
 * @author lisz
 *
 * @param <T>
 * @param <ID>
 */
public interface IBaseMapper<T extends BaseModel, ID extends Serializable> {

	/**
	 * 根据id删除
	 * 
	 * @param id
	 * @return
	 */
	int deleteByPrimaryKey(ID id);

	/**
	 * 插入数据
	 * 
	 * @param record
	 * @return
	 */
	int insertSelective(T record);

	/**
	 * 根据id查询
	 * 
	 * @param id
	 * @return
	 */
	T selectByPrimaryKey(ID id);

	/**
	 * 根据id更新
	 * 
	 * @param record
	 * @return
	 */
	int updateByPrimaryKeySelective(T record);

}
