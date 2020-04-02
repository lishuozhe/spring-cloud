package cn.com.lisz.producer.base.controller;

import org.springframework.cloud.context.config.annotation.RefreshScope;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import cn.com.lisz.common.controller.BaseController;
import cn.com.lisz.common.model.base.PermissionModel;
import cn.com.lisz.producer.base.entity.Permission;
import cn.com.lisz.producer.base.service.IPermissionService;

@RestController
@RefreshScope
@RequestMapping("/permission")
public class PermissionController extends BaseController<Permission, Long, PermissionModel, IPermissionService> {

}