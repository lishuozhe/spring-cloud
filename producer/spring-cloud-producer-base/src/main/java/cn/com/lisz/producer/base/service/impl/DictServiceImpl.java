package cn.com.lisz.producer.base.service.impl;

import org.springframework.stereotype.Service;

import cn.com.lisz.common.model.base.DictModel;
import cn.com.lisz.common.service.impl.BaseServiceImpl;
import cn.com.lisz.producer.base.dao.DictDao;
import cn.com.lisz.producer.base.entity.Dict;
import cn.com.lisz.producer.base.service.IDictService;

@Service
public class DictServiceImpl extends BaseServiceImpl<Dict, Long, DictModel, DictDao> implements IDictService {

}
