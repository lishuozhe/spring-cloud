package cn.com.lisz.producer.base.service;

import cn.com.lisz.common.model.base.DictModel;
import cn.com.lisz.common.service.IBaseService;
import cn.com.lisz.producer.base.dao.DictDao;
import cn.com.lisz.producer.base.entity.Dict;

public interface IDictService extends IBaseService<Dict, Long, DictModel, DictDao> {

}
