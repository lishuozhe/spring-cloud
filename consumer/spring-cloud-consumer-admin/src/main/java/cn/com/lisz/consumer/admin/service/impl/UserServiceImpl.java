package cn.com.lisz.consumer.admin.service.impl;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import cn.com.lisz.common.model.base.AuthModel;
import cn.com.lisz.common.model.base.RoleModel;
import cn.com.lisz.common.model.base.UserModel;
import cn.com.lisz.common.model.web.PaggingModel;
import cn.com.lisz.common.model.web.RequestCondition;
import cn.com.lisz.common.model.web.ResultModel;
import cn.com.lisz.consumer.admin.remote.AuthRemote;
import cn.com.lisz.consumer.admin.remote.UserRemote;
import cn.com.lisz.consumer.admin.service.IUserService;

@Service
public class UserServiceImpl implements IUserService {

	@Autowired
	UserRemote userRemote;
	@Autowired
	AuthRemote authRemote;

	BCryptPasswordEncoder encoder = new BCryptPasswordEncoder();

	@Override
	public ResultModel<UserModel> add(UserModel model) {
		ResultModel<UserModel> result = new ResultModel<UserModel>();
		// 创建用户
		if (model.getPassword() != null) {
			String rawPassword = model.getPassword();
			model.setPassword(encoder.encode(rawPassword));
		}
		Long id = userRemote.add(model);
		// 授权
		if (id != null) {
			model.setId(id);
			return auth(model);
		}
		return result.failed();
	}

	@Override
	public ResultModel<UserModel> auth(UserModel model) {
		ResultModel<UserModel> result = new ResultModel<UserModel>();
		if (model == null || model.getId() == null) {
			return result.failed("用户ID不能为空");
		}
		// 验证用户是否存在
		UserModel user = userRemote.get(model.getId());
		if (user == null) {
			return result.failed("用户不存在");
		}
		// 验证授权用户是否存在
		AuthModel auth = null;
		if (user.getAuthUsername() != null && user.getAuthPassword() != null) {
			List<RequestCondition> conditions = new ArrayList<RequestCondition>();
			conditions.add(new RequestCondition("username", user.getAuthUsername()));
			conditions.add(new RequestCondition("password", encoder.encode(user.getAuthPassword())));
			auth = authRemote.findOne(conditions);
		}
		// 创建授权用户
		if (auth == null) {
			String authUsername = "U-" + model.getId();
			String authPassword = UUID.randomUUID().toString();
			auth = new AuthModel(authUsername, encoder.encode(authPassword), null);
			Long authId = authRemote.add(auth);
			// 更新用户授权信息
			if (authId != null) {
				user.setAuthUsername(authUsername);
				user.setAuthPassword(authPassword);
				if (!userRemote.edit(user)) {
					return result.failed("更新授权信息失败");
				}
			}
		}
		// 授权
		if (model.getRoleIds() != null) {
			List<RoleModel> roles = new ArrayList<RoleModel>();
			model.getRoleIds().forEach(roleId -> {
				RoleModel role = new RoleModel();
				role.setId(roleId);
				roles.add(role);
			});
			auth.setRoles(roles);
			if (authRemote.edit(auth)) {
				return result.success();
			} else {
				return result.failed();
			}
		}
		return result.success();
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
		if (userRemote.edit(model)) {
			auth(model);
		}
		return false;
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
