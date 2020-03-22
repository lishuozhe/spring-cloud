package cn.com.lisz.common.service;

import java.io.Serializable;

import org.springframework.beans.factory.annotation.Autowired;

import cn.com.lisz.common.mapper.IBaseMapper;
import cn.com.lisz.common.model.BaseModel;

/**
 * 通用接口实现类
 * 
 * @author lisz
 *
 * @param <T>
 * @param <ID>
 * @param <TDao>
 */
public abstract class BaseService<T extends BaseModel, ID extends Serializable, TDao extends IBaseMapper<T, ID>>
		implements IBaseService<T, ID, TDao> {

	@Autowired
	protected IBaseMapper<T, ID> baseMapper;

	@Override
	public int delete(ID id) {
		T t = get(id);
		t.setDelFlag(T.FINAL_DELETE);
		return update(t);
	}

	@Override
	public int save(T record) {
		return baseMapper.insertSelective(record);
	}

	@Override
	public T get(ID id) {
		return baseMapper.selectByPrimaryKey(id);
	}

	@Override
	public int update(T record) {
		return baseMapper.updateByPrimaryKeySelective(record);
	}

}
