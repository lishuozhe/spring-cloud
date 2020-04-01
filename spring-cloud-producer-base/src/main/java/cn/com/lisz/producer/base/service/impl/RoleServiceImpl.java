package cn.com.lisz.producer.base.service.impl;

import org.springframework.stereotype.Service;

import cn.com.lisz.common.model.base.RoleModel;
import cn.com.lisz.common.service.impl.BaseServiceImpl;
import cn.com.lisz.producer.base.dao.RoleDao;
import cn.com.lisz.producer.base.entity.Role;
import cn.com.lisz.producer.base.service.IRoleService;

@Service
public class RoleServiceImpl extends BaseServiceImpl<Role, Long, RoleModel, RoleDao> implements IRoleService {

}
