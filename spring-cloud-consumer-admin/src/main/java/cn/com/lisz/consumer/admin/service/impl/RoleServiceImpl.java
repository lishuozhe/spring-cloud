package cn.com.lisz.consumer.admin.service.impl;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import cn.com.lisz.common.model.base.RoleModel;
import cn.com.lisz.common.model.web.PaggingModel;
import cn.com.lisz.common.model.web.RequestCondition;
import cn.com.lisz.consumer.admin.remote.RoleRemote;
import cn.com.lisz.consumer.admin.service.IRoleService;

@Service
public class RoleServiceImpl implements IRoleService {

	@Autowired
	RoleRemote roleRemote;

	@Override
	public Long add(RoleModel model) {
		return roleRemote.add(model);
	}

	@Override
	public boolean del(List<Long> ids) {
		List<RequestCondition> conditions = new ArrayList<RequestCondition>();
		for (Long id : ids) {
			conditions.add(new RequestCondition("in_id", id));
		}
		return roleRemote.del(conditions);
	}

	@Override
	public boolean edit(RoleModel model) {
		return roleRemote.edit(model);
	}

	@Override
	public RoleModel get(Long id) {
		return roleRemote.get(id);
	}

	@Override
	public RoleModel findOne(RoleModel model) {
		List<RequestCondition> conditions = new ArrayList<RequestCondition>();
		if (model.getName() != null) {
			conditions.add(new RequestCondition("code", model.getCode()));
		}

		return roleRemote.findOne(conditions);
	}

	@Override
	public boolean exist(RoleModel model) {
		List<RequestCondition> conditions = new ArrayList<RequestCondition>();
		if (model.getName() != null) {
			conditions.add(new RequestCondition("code", model.getCode()));
		}
		return roleRemote.exist(conditions);
	}

	@Override
	public List<RoleModel> list(RoleModel model) {
		List<RequestCondition> conditions = new ArrayList<RequestCondition>();
		if (model.getName() != null) {
			conditions.add(new RequestCondition("like_name", model.getName()));
		}

		return roleRemote.list(conditions);
	}

	@Override
	public PaggingModel<RoleModel> page(Integer size, Integer page, RoleModel model) {
		List<RequestCondition> conditions = new ArrayList<RequestCondition>();
		if (model.getName() != null) {
			conditions.add(new RequestCondition("like_name", model.getName()));
		}
		return roleRemote.page(size, page, conditions);
	}

}
