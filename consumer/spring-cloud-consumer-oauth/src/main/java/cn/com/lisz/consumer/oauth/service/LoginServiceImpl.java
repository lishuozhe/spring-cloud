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
import cn.com.lisz.consumer.oauth.model.LoginModel;
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
	public ResultModel<?> user(LoginModel model) {
		ResultModel<UserModel> result = new ResultModel<UserModel>();
		List<RequestCondition> conditions = new ArrayList<RequestCondition>();
		conditions.add(new RequestCondition("username", model.getUsername()));
		UserModel user = userRemote.findOne(conditions);
		if (user == null) {
			return result.failed("用户不存在");
		}
		if (!encoder.matches(model.getPassword(), user.getPassword())) {
			return result.failed("密码不正确");
		}
		return loginAuth(user.getAuthUsername(), user.getAuthPassword());
	}

	@SuppressWarnings({ "rawtypes", "unchecked" })
	@Override
	public ResultModel token(String token) {
		String path = String.format("%s?grant_type=refresh_token&refresh_token=%s&client_id=%s&client_secret=%s", url,
				token, clientId, clientSecret);
		// 进行请求，并返回数据
		ResponseEntity<ResultModel> authInfo = restTemplate.getForEntity(path, ResultModel.class);
		return authInfo.getBody();
	}

	@SuppressWarnings("rawtypes")
	private ResultModel loginAuth(String username, String password) {
		String path = String.format("%s?grant_type=password&username=%s&password=%s&client_id=%s&client_secret=%s", url,
				username, password, clientId, clientSecret);
		// 进行请求，并返回数据
		ResponseEntity<ResultModel> authInfo = restTemplate.getForEntity(path, ResultModel.class);
		return authInfo.getBody();
	}

}
