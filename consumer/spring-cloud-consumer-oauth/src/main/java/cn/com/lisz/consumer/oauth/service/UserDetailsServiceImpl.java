package cn.com.lisz.consumer.oauth.service;

import java.util.ArrayList;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import cn.com.lisz.common.model.base.UserModel;
import cn.com.lisz.common.model.web.RequestCondition;
import cn.com.lisz.consumer.oauth.remote.UserRemote;

@Service
public class UserDetailsServiceImpl implements UserDetailsService {

	@Bean
	public PasswordEncoder passwordEncoder() {
		return new BCryptPasswordEncoder();
	}

	@Autowired
	UserRemote userRemote;

	private Logger logger = LoggerFactory.getLogger(getClass());

	@Override
	public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
		// System.out.println("收到的账号"+username);
		// if (CheckFormat.isEmail(username)){
		// passwd = userService.selectPasswdByEmail(username);
		// }else if (CheckFormat.isPhone(username)){
		// passwd = userService.selectPasswdByPhone(username);
		// }else {
		// throw new RuntimeException("登录账号不存在");
		// }

		logger.info("登录用户名:" + username);
		UserModel model = findByUsername(username);
		if (model == null) {
			throw new UsernameNotFoundException(String.format("No user found with username '%s'.", username));
		} else {
			return AuthUserFactory.create(model);
		}
	}

	private UserModel findByUsername(String username) {
		List<RequestCondition> conditions = new ArrayList<RequestCondition>();
		conditions.add(new RequestCondition("username", username));
		return userRemote.findOne(conditions);
	}

//	private UserModel findByEmail(String email) {
//		List<RequestCondition> conditions = new ArrayList<RequestCondition>();
//		conditions.add(new RequestCondition("email", email));
//		return userRemote.findOne(conditions);
//	}
//
//	private UserModel findByMobile(String mobile) {
//		List<RequestCondition> conditions = new ArrayList<RequestCondition>();
//		conditions.add(new RequestCondition("mobile", mobile));
//		return userRemote.findOne(conditions);
//	}
}
