package cn.com.lisz.producer.base.controller;


import org.springframework.cloud.context.config.annotation.RefreshScope;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import cn.com.lisz.common.controller.BaseController;
import cn.com.lisz.common.model.base.DictModel;
import cn.com.lisz.producer.base.entity.Dict;
import cn.com.lisz.producer.base.service.IDictService;

@RestController
@RefreshScope
@RequestMapping("/dict")
public class DictController extends BaseController<Dict, Long, DictModel, IDictService> {

}