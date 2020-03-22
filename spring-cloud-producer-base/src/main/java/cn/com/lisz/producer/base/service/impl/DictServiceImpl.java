package cn.com.lisz.producer.base.service.impl;

import java.util.List;

import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;

import cn.com.lisz.common.service.BaseService;
import cn.com.lisz.producer.base.mapper.DictMapper;
import cn.com.lisz.producer.base.model.Dict;
import cn.com.lisz.producer.base.model.DictExample;
import cn.com.lisz.producer.base.service.IDictService;

@Service
public class DictServiceImpl extends BaseService<Dict, Long, DictMapper> implements IDictService {

	@Autowired
	private DictMapper dictMapper;

	@Override
	public Dict listOne(Dict record) {
		List<Dict> list = list(record);
		if (list != null && list.size() > 0) {
			return list.get(0);
		}
		return null;
	}

	@Override
	public List<Dict> list(Dict record) {
		DictExample example = new DictExample();
		setCriteria(example, record);
		return dictMapper.selectByExample(example);
	}

	@Override
	public PageInfo<Dict> page(Integer pageSize, Integer pageNum, Dict record) {
		PageHelper.startPage(pageNum, pageSize);
		return new PageInfo<Dict>(list(record));
	}

	/**
	 * 设置查询条件
	 * 
	 * @param example
	 * @param record
	 */
	private void setCriteria(DictExample example, Dict record) {
		DictExample.Criteria criteria = example.createCriteria();
		if (null != record.getId()) {
			criteria.andIdEqualTo(record.getId());
		}
		if (null != record.getPid()) {
			criteria.andPidEqualTo(record.getPid());
		}
		if (StringUtils.isNotEmpty(record.getValue())) {
			criteria.andValueEqualTo(record.getValue());
		}
		if (StringUtils.isNotEmpty(record.getLabel())) {
			criteria.andLabelLike("%" + record.getLabel() + "%");
		}
		if (StringUtils.isNotEmpty(record.getType())) {
			criteria.andTypeEqualTo(record.getType());
		}
		if (StringUtils.isNotEmpty(record.getDescription())) {
			criteria.andDescriptionLike("%" + record.getDescription() + "%");
		}
		criteria.andDelFlagEqualTo("0");
	}

}
