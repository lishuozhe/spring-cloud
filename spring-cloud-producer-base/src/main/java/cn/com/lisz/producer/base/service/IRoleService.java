package cn.com.lisz.producer.base.service;

import cn.com.lisz.common.model.base.RoleModel;
import cn.com.lisz.common.service.IBaseService;
import cn.com.lisz.producer.base.dao.RoleDao;
import cn.com.lisz.producer.base.entity.Role;

public interface IRoleService extends IBaseService<Role, Long, RoleModel, RoleDao> {

}
