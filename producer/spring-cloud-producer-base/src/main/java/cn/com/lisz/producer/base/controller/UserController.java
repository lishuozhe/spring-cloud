package cn.com.lisz.producer.base.controller;


import org.springframework.cloud.context.config.annotation.RefreshScope;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import cn.com.lisz.common.controller.BaseController;
import cn.com.lisz.common.model.base.UserModel;
import cn.com.lisz.producer.base.entity.User;
import cn.com.lisz.producer.base.service.IUserService;

@RestController
@RefreshScope
@RequestMapping("/user")
public class UserController extends BaseController<User, Long, UserModel, IUserService> {

}