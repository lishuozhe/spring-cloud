package cn.com.lisz.consumer.oauth.service;

import cn.com.lisz.common.model.base.UserModel;

public interface ILoginService {
	
	/**
	 * 用户
	 *
	 * @param model
	 * @return
	 */
	public Object user(UserModel model);
	
	public Object token(String token);

}
