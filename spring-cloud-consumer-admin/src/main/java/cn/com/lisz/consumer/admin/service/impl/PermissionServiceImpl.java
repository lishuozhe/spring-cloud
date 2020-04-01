package cn.com.lisz.consumer.admin.service.impl;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import cn.com.lisz.common.model.base.PermissionModel;
import cn.com.lisz.common.model.web.PaggingModel;
import cn.com.lisz.common.model.web.RequestCondition;
import cn.com.lisz.consumer.admin.remote.PermissionRemote;
import cn.com.lisz.consumer.admin.service.IPermissionService;

@Service
public class PermissionServiceImpl implements IPermissionService {

	@Autowired
	PermissionRemote permissionRemote;

	@Override
	public Long add(PermissionModel model) {
		
		return permissionRemote.add(model);
	}

	@Override
	public boolean del(List<Long> ids) {
		List<RequestCondition> conditions = new ArrayList<RequestCondition>();
		for (Long id : ids) {
			conditions.add(new RequestCondition("in_id", id));
		}
		return permissionRemote.del(conditions);
	}

	@Override
	public boolean edit(PermissionModel model) {
		return permissionRemote.edit(model);
	}

	@Override
	public PermissionModel get(Long id) {
		return permissionRemote.get(id);
	}

	@Override
	public PermissionModel findOne(PermissionModel model) {
		List<RequestCondition> conditions = new ArrayList<RequestCondition>();
		if (model.getName() != null) {
			conditions.add(new RequestCondition("code", model.getCode()));
		}

		return permissionRemote.findOne(conditions);
	}

	@Override
	public boolean exist(PermissionModel model) {
		List<RequestCondition> conditions = new ArrayList<RequestCondition>();
		if (model.getName() != null) {
			conditions.add(new RequestCondition("code", model.getCode()));
		}
		return permissionRemote.exist(conditions);
	}

	@Override
	public List<PermissionModel> list(PermissionModel model) {
		List<RequestCondition> conditions = new ArrayList<RequestCondition>();
		if (model.getName() != null) {
			conditions.add(new RequestCondition("like_name", model.getName()));
		}

		return permissionRemote.list(conditions);
	}

	@Override
	public PaggingModel<PermissionModel> page(Integer size, Integer page, PermissionModel model) {
		List<RequestCondition> conditions = new ArrayList<RequestCondition>();
		if (model.getName() != null) {
			conditions.add(new RequestCondition("like_name", model.getName()));
		}
		return permissionRemote.page(size, page, conditions);
	}

}
