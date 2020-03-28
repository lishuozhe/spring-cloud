package cn.com.lisz.consumer.admin.remote.hystrix;

import java.util.List;

import org.springframework.stereotype.Component;

import cn.com.lisz.common.model.base.DictModel;
import cn.com.lisz.common.model.web.DelModel;
import cn.com.lisz.common.model.web.EditModel;
import cn.com.lisz.common.model.web.FindModel;
import cn.com.lisz.common.model.web.GetModel;
import cn.com.lisz.common.model.web.PaggingModel;
import cn.com.lisz.consumer.admin.remote.DictRemote;

@Component
public class DictRemoteHystrix implements DictRemote {

	@Override
	public Long add(DictModel model) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public boolean del(DelModel<Long> model) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean edit(EditModel<DictModel> model) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public DictModel get(GetModel<Long> model) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public DictModel findOne(FindModel model) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public boolean exist(FindModel model) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public List<DictModel> list(FindModel model) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public PaggingModel<DictModel> page(Integer size, Integer page, FindModel model) {
		// TODO Auto-generated method stub
		return null;
	}

	

}