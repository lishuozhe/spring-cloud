package cn.com.lisz.producer.base.service.impl;

import org.springframework.stereotype.Service;

import cn.com.lisz.common.model.base.PermissionModel;
import cn.com.lisz.common.service.impl.BaseServiceImpl;
import cn.com.lisz.producer.base.dao.PermissionDao;
import cn.com.lisz.producer.base.entity.Permission;
import cn.com.lisz.producer.base.service.IPermissionService;

@Service
public class PermissionServiceImpl extends BaseServiceImpl<Permission, Long, PermissionModel, PermissionDao>
		implements IPermissionService {

}
