package cn.com.lisz.producer.base.service;

import cn.com.lisz.common.model.base.UserModel;
import cn.com.lisz.common.service.IBaseService;
import cn.com.lisz.producer.base.dao.UserDao;
import cn.com.lisz.producer.base.entity.User;

public interface IUserService extends IBaseService<User, Long, UserModel, UserDao> {

}
