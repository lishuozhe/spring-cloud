package cn.com.lisz.producer.base.service;

import cn.com.lisz.common.model.base.PermissionModel;
import cn.com.lisz.common.service.IBaseService;
import cn.com.lisz.producer.base.dao.PermissionDao;
import cn.com.lisz.producer.base.entity.Permission;

public interface IPermissionService extends IBaseService<Permission, Long, PermissionModel, PermissionDao> {

}
