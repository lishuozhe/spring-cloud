package cn.com.lisz.consumer.admin.service.impl;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import cn.com.lisz.common.model.base.DictModel;
import cn.com.lisz.common.model.web.PaggingModel;
import cn.com.lisz.common.model.web.RequestCondition;
import cn.com.lisz.consumer.admin.remote.DictRemote;
import cn.com.lisz.consumer.admin.service.IDictService;

@Service
public class DictServiceImpl implements IDictService {

	@Autowired
	DictRemote dictRemote;

	@Override
	public Long add(DictModel model) {
		return dictRemote.add(model);
	}

	@Override
	public boolean del(List<Long> ids) {
		List<RequestCondition> conditions = new ArrayList<RequestCondition>();
		for (Long id : ids) {
			conditions.add(new RequestCondition("in_id", id));
		}
		return dictRemote.del(conditions);
	}

	@Override
	public boolean edit(DictModel model) {
		return dictRemote.edit(model);
	}

	@Override
	public DictModel get(Long id) {
		return dictRemote.get(id);
	}

	@Override
	public DictModel findOne(DictModel model) {
		List<RequestCondition> conditions = new ArrayList<RequestCondition>();
		if (model.getLabel() != null) {
			conditions.add(new RequestCondition("label", model.getLabel()));
		}

		return dictRemote.findOne(conditions);
	}

	@Override
	public boolean exist(DictModel model) {
		List<RequestCondition> conditions = new ArrayList<RequestCondition>();
		if (model.getLabel() != null) {
			conditions.add(new RequestCondition("label", model.getLabel()));
		}
		return dictRemote.exist(conditions);
	}

	@Override
	public List<DictModel> list(DictModel model) {
		List<RequestCondition> conditions = new ArrayList<RequestCondition>();
		if (model.getLabel() != null) {
			conditions.add(new RequestCondition("like_label", model.getLabel()));
		}

		return dictRemote.list(conditions);
	}

	@Override
	public PaggingModel<DictModel> page(Integer size, Integer page, DictModel model) {
		List<RequestCondition> conditions = new ArrayList<RequestCondition>();
		if (model.getLabel() != null) {
			conditions.add(new RequestCondition("like_label", model.getLabel()));
		}
		return dictRemote.page(size, page, conditions);
	}

}
