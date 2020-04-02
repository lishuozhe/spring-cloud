package cn.com.lisz.consumer.admin.remote.hystrix;

import java.util.List;

import org.springframework.stereotype.Component;

import cn.com.lisz.common.model.base.UserModel;
import cn.com.lisz.common.model.web.PaggingModel;
import cn.com.lisz.common.model.web.RequestCondition;
import cn.com.lisz.consumer.admin.remote.UserRemote;

@Component
public class UserRemoteHystrix implements UserRemote {

	@Override
	public Long add(UserModel model) {
		return null;
	}

	@Override
	public boolean del(List<RequestCondition> conditions) {
		return false;
	}

	@Override
	public boolean edit(UserModel model) {
		return false;
	}

	@Override
	public UserModel get(Long id) {
		return null;
	}

	@Override
	public UserModel findOne(List<RequestCondition> conditions) {
		return null;
	}

	@Override
	public boolean exist(List<RequestCondition> conditions) {
		return false;
	}

	@Override
	public List<UserModel> list(List<RequestCondition> conditions) {
		return null;
	}

	@Override
	public PaggingModel<UserModel> page(Integer size, Integer page, List<RequestCondition> conditions) {
		return null;
	}

}