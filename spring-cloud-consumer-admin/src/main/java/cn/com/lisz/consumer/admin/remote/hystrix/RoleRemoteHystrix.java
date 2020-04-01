package cn.com.lisz.consumer.admin.remote.hystrix;

import java.util.List;

import org.springframework.stereotype.Component;

import cn.com.lisz.common.model.base.RoleModel;
import cn.com.lisz.common.model.web.PaggingModel;
import cn.com.lisz.common.model.web.RequestCondition;
import cn.com.lisz.consumer.admin.remote.RoleRemote;

@Component
public class RoleRemoteHystrix implements RoleRemote {

	@Override
	public Long add(RoleModel model) {
		return null;
	}

	@Override
	public boolean del(List<RequestCondition> conditions) {
		return false;
	}

	@Override
	public boolean edit(RoleModel model) {
		return false;
	}

	@Override
	public RoleModel get(Long id) {
		return null;
	}

	@Override
	public RoleModel findOne(List<RequestCondition> conditions) {
		return null;
	}

	@Override
	public boolean exist(List<RequestCondition> conditions) {
		return false;
	}

	@Override
	public List<RoleModel> list(List<RequestCondition> conditions) {
		return null;
	}

	@Override
	public PaggingModel<RoleModel> page(Integer size, Integer page, List<RequestCondition> conditions) {
		return null;
	}

}