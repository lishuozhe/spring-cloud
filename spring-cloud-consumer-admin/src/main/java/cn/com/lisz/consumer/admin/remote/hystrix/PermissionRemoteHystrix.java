package cn.com.lisz.consumer.admin.remote.hystrix;

import java.util.List;

import org.springframework.stereotype.Component;

import cn.com.lisz.common.model.base.PermissionModel;
import cn.com.lisz.common.model.web.PaggingModel;
import cn.com.lisz.common.model.web.RequestCondition;
import cn.com.lisz.consumer.admin.remote.PermissionRemote;

@Component
public class PermissionRemoteHystrix implements PermissionRemote {

	@Override
	public Long add(PermissionModel model) {
		return null;
	}

	@Override
	public boolean del(List<RequestCondition> conditions) {
		return false;
	}

	@Override
	public boolean edit(PermissionModel model) {
		return false;
	}

	@Override
	public PermissionModel get(Long id) {
		return null;
	}

	@Override
	public PermissionModel findOne(List<RequestCondition> conditions) {
		return null;
	}

	@Override
	public boolean exist(List<RequestCondition> conditions) {
		return false;
	}

	@Override
	public List<PermissionModel> list(List<RequestCondition> conditions) {
		return null;
	}

	@Override
	public PaggingModel<PermissionModel> page(Integer size, Integer page, List<RequestCondition> conditions) {
		return null;
	}

}