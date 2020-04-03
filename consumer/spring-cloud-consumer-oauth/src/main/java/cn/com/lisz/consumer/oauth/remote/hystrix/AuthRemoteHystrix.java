package cn.com.lisz.consumer.oauth.remote.hystrix;

import java.util.List;

import org.springframework.stereotype.Component;

import cn.com.lisz.common.model.base.AuthModel;
import cn.com.lisz.common.model.web.PaggingModel;
import cn.com.lisz.common.model.web.RequestCondition;
import cn.com.lisz.consumer.oauth.remote.AuthRemote;

@Component
public class AuthRemoteHystrix implements AuthRemote {

	@Override
	public Long add(AuthModel model) {
		return null;
	}

	@Override
	public boolean del(List<RequestCondition> conditions) {
		return false;
	}

	@Override
	public boolean edit(AuthModel model) {
		return false;
	}

	@Override
	public AuthModel get(Long id) {
		return null;
	}

	@Override
	public AuthModel findOne(List<RequestCondition> conditions) {
		return null;
	}

	@Override
	public boolean exist(List<RequestCondition> conditions) {
		return false;
	}

	@Override
	public List<AuthModel> list(List<RequestCondition> conditions) {
		return null;
	}

	@Override
	public PaggingModel<AuthModel> page(Integer size, Integer page, List<RequestCondition> conditions) {
		return null;
	}

}