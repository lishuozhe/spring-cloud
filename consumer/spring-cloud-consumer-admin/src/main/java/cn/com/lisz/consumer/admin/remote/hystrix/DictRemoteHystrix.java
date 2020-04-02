package cn.com.lisz.consumer.admin.remote.hystrix;

import java.util.List;

import org.springframework.stereotype.Component;

import cn.com.lisz.common.model.base.DictModel;
import cn.com.lisz.common.model.web.PaggingModel;
import cn.com.lisz.common.model.web.RequestCondition;
import cn.com.lisz.consumer.admin.remote.DictRemote;

@Component
public class DictRemoteHystrix implements DictRemote {

	@Override
	public Long add(DictModel model) {
		return null;
	}

	@Override
	public boolean del(List<RequestCondition> conditions) {
		return false;
	}

	@Override
	public boolean edit(DictModel model) {
		return false;
	}

	@Override
	public DictModel get(Long id) {
		return null;
	}

	@Override
	public DictModel findOne(List<RequestCondition> conditions) {
		return null;
	}

	@Override
	public boolean exist(List<RequestCondition> conditions) {
		return false;
	}

	@Override
	public List<DictModel> list(List<RequestCondition> conditions) {
		return null;
	}

	@Override
	public PaggingModel<DictModel> page(Integer size, Integer page, List<RequestCondition> conditions) {
		return null;
	}

}