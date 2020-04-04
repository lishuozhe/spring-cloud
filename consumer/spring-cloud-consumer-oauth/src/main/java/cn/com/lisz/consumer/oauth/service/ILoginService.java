package cn.com.lisz.consumer.oauth.service;

import cn.com.lisz.common.model.web.ResultModel;
import cn.com.lisz.consumer.oauth.model.LoginModel;

public interface ILoginService {

	/**
	 * 用户
	 *
	 * @param model
	 * @return
	 */
	public ResultModel<?> user(LoginModel model);

	/**
	 * 刷新token
	 * 
	 * @param token
	 * @return
	 */
	public ResultModel<?> token(String token);

}
