package cn.com.lisz.producer.base.service.impl;

import org.springframework.stereotype.Service;

import cn.com.lisz.common.model.base.AuthModel;
import cn.com.lisz.common.service.impl.BaseServiceImpl;
import cn.com.lisz.producer.base.dao.AuthDao;
import cn.com.lisz.producer.base.entity.Auth;
import cn.com.lisz.producer.base.service.IAuthService;

@Service
public class AuthServiceImpl extends BaseServiceImpl<Auth, Long, AuthModel, AuthDao> implements IAuthService {

}
