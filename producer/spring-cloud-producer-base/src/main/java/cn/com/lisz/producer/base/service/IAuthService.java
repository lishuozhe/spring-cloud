package cn.com.lisz.producer.base.service;

import cn.com.lisz.common.model.base.AuthModel;
import cn.com.lisz.common.service.IBaseService;
import cn.com.lisz.producer.base.dao.AuthDao;
import cn.com.lisz.producer.base.entity.Auth;

public interface IAuthService extends IBaseService<Auth, Long, AuthModel, AuthDao> {

}
