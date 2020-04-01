package cn.com.lisz.producer.base.service.impl;

import org.springframework.stereotype.Service;

import cn.com.lisz.common.model.base.UserModel;
import cn.com.lisz.common.service.impl.BaseServiceImpl;
import cn.com.lisz.producer.base.dao.UserDao;
import cn.com.lisz.producer.base.entity.User;
import cn.com.lisz.producer.base.service.IUserService;

@Service
public class UserServiceImpl extends BaseServiceImpl<User, Long, UserModel, UserDao> implements IUserService {

}
