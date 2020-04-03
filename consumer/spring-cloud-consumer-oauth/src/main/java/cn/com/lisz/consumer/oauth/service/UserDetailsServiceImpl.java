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

import cn.com.lisz.common.model.base.AuthModel;
import cn.com.lisz.common.model.web.RequestCondition;
import cn.com.lisz.consumer.oauth.remote.AuthRemote;

@Service
public class UserDetailsServiceImpl implements UserDetailsService {

	@Bean
	public PasswordEncoder passwordEncoder() {
		return new BCryptPasswordEncoder();
	}

	@Autowired
	AuthRemote authRemote;

	private Logger logger = LoggerFactory.getLogger(getClass());

	@Override
	public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
		logger.info("登录用户名:" + username);
		AuthModel model = findByUsername(username);
		if (model == null) {
			throw new UsernameNotFoundException(String.format("No user found with username '%s'.", username));
		} else {
			return AuthUserFactory.create(model);
		}
	}

	private AuthModel findByUsername(String username) {
		List<RequestCondition> conditions = new ArrayList<RequestCondition>();
		conditions.add(new RequestCondition("username", username));
		return authRemote.findOne(conditions);
	}

}
