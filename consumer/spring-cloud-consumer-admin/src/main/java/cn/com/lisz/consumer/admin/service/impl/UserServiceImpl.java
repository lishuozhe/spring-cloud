package cn.com.lisz.consumer.admin.service.impl;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import cn.com.lisz.common.model.base.UserModel;
import cn.com.lisz.common.model.web.PaggingModel;
import cn.com.lisz.common.model.web.RequestCondition;
import cn.com.lisz.consumer.admin.remote.UserRemote;
import cn.com.lisz.consumer.admin.service.IUserService;

@Service
public class UserServiceImpl implements IUserService {

	@Autowired
	UserRemote userRemote;

	@Override
	public Long add(UserModel model) {
		if(model.getPassword() != null) {
			BCryptPasswordEncoder encoder = new BCryptPasswordEncoder();
			String rawPassword = model.getPassword();
			model.setPassword(encoder.encode(rawPassword));
		}
		return userRemote.add(model);
	}

	@Override
	public boolean del(List<Long> ids) {
		List<RequestCondition> conditions = new ArrayList<RequestCondition>();
		for (Long id : ids) {
			conditions.add(new RequestCondition("in_id", id));
		}
		return userRemote.del(conditions);
	}

	@Override
	public boolean edit(UserModel model) {
		return userRemote.edit(model);
	}

	@Override
	public UserModel get(Long id) {
		return userRemote.get(id);
	}

	@Override
	public UserModel findOne(UserModel model) {
		List<RequestCondition> conditions = new ArrayList<RequestCondition>();
		if (model.getUsername() != null) {
			conditions.add(new RequestCondition("username", model.getUsername()));
		}
		if (model.getMobile() != null) {
			conditions.add(new RequestCondition("mobile", model.getMobile()));
		}
		if (model.getEmail() != null) {
			conditions.add(new RequestCondition("email", model.getEmail()));
		}
		return userRemote.findOne(conditions);
	}

	@Override
	public boolean exist(UserModel model) {
		List<RequestCondition> conditions = new ArrayList<RequestCondition>();
		if (model.getUsername() != null) {
			conditions.add(new RequestCondition("username", model.getUsername()));
		}
		if (model.getMobile() != null) {
			conditions.add(new RequestCondition("mobile", model.getMobile()));
		}
		if (model.getEmail() != null) {
			conditions.add(new RequestCondition("email", model.getEmail()));
		}
		return userRemote.exist(conditions);
	}

	@Override
	public List<UserModel> list(UserModel model) {
		List<RequestCondition> conditions = new ArrayList<RequestCondition>();
		if (model.getUsername() != null) {
			conditions.add(new RequestCondition("username", model.getUsername()));
		}
		if (model.getMobile() != null) {
			conditions.add(new RequestCondition("mobile", model.getMobile()));
		}
		if (model.getEmail() != null) {
			conditions.add(new RequestCondition("email", model.getEmail()));
		}
		return userRemote.list(conditions);
	}

	@Override
	public PaggingModel<UserModel> page(Integer size, Integer page, UserModel model) {
		List<RequestCondition> conditions = new ArrayList<RequestCondition>();
		if (model.getUsername() != null) {
			conditions.add(new RequestCondition("username", model.getUsername()));
		}
		if (model.getMobile() != null) {
			conditions.add(new RequestCondition("mobile", model.getMobile()));
		}
		if (model.getEmail() != null) {
			conditions.add(new RequestCondition("email", model.getEmail()));
		}
		return userRemote.page(size, page, conditions);
	}

}
