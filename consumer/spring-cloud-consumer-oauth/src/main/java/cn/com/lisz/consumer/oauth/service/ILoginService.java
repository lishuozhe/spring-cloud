package cn.com.lisz.consumer.oauth.service;

import cn.com.lisz.common.model.base.UserModel;
import cn.com.lisz.common.model.web.ResultModel;

public interface ILoginService {

	/**
	 * 用户
	 *
	 * @param model
	 * @return
	 */
	public ResultModel<?> user(UserModel model);

	/**
	 * 刷新token
	 * 
	 * @param token
	 * @return
	 */
	public ResultModel<?> token(String token);

}
