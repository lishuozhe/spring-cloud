package cn.com.lisz.producer.base.controller;

import org.springframework.cloud.context.config.annotation.RefreshScope;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import cn.com.lisz.common.controller.BaseController;
import cn.com.lisz.common.model.base.RoleModel;
import cn.com.lisz.producer.base.entity.Role;
import cn.com.lisz.producer.base.service.IRoleService;

@RestController
@RefreshScope
@RequestMapping("/role")
public class RoleController extends BaseController<Role, Long, RoleModel, IRoleService> {

}