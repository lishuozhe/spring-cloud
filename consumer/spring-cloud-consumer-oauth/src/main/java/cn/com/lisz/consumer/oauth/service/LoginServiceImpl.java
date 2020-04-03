package cn.com.lisz.consumer.oauth.service;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import cn.com.lisz.common.model.base.UserModel;
import cn.com.lisz.common.model.web.RequestCondition;
import cn.com.lisz.common.model.web.ResultModel;
import cn.com.lisz.consumer.oauth.remote.UserRemote;

@Service
public class LoginServiceImpl implements ILoginService {

	@Value("${auth.url}")
	private String url;

	@Value("${auth.client_id}")
	private String clientId;

	@Value("${auth.client_secret}")
	private String clientSecret;

	@Autowired
	UserRemote userRemote;
	@Autowired
	RestTemplate restTemplate;

	BCryptPasswordEncoder encoder = new BCryptPasswordEncoder();

	@Override
	public Object user(UserModel model) {
		ResultModel<UserModel> result = new ResultModel<UserModel>();
		if (model == null || model.getUsername() == null || model.getPassword() == null) {
			return result.failed("用户名、密码不能为空");
		}
		List<RequestCondition> conditions = new ArrayList<RequestCondition>();
		conditions.add(new RequestCondition("username", model.getUsername()));
		UserModel user = userRemote.findOne(conditions);
		if (user == null) {
			return result.failed("用户名不存在");
		}
		if (!encoder.matches(model.getPassword(), user.getPassword())) {
			return result.failed("密码不正确");
		}
		return loginAuth(user.getAuthUsername(), user.getAuthPassword());
	}

	@Override
	public Object token(String token) {
		String path = String.format("%s?grant_type=refresh_token&refresh_token=%s&client_id=%s&client_secret=%s", url,
				token, clientId, clientSecret);
		// 进行请求，并返回数据
		ResponseEntity<String> authInfo = restTemplate.getForEntity(path, String.class);
		return authInfo.getBody();
	}

	private String loginAuth(String username, String password) {
		String path = String.format("%s?grant_type=password&username=%s&password=%s&client_id=%s&client_secret=%s", url,
				username, password, clientId, clientSecret);
		// 进行请求，并返回数据
		ResponseEntity<String> authInfo = restTemplate.getForEntity(path, String.class);
		return authInfo.getBody();
	}

}
