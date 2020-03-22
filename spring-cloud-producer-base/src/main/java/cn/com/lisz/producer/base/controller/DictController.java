package cn.com.lisz.producer.base.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.context.config.annotation.RefreshScope;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.github.pagehelper.PageInfo;

import cn.com.lisz.common.controller.BaseController;
import cn.com.lisz.producer.base.model.Dict;
import cn.com.lisz.producer.base.service.IDictService;

@RestController
@RefreshScope
@RequestMapping("/dict")
public class DictController extends BaseController<Dict, Long, IDictService> {

	@Autowired
	private IDictService dictService;

	/**
	 * 查询一条
	 * 
	 * @param record
	 * @return
	 */
	@RequestMapping(value = "/listOne", method = RequestMethod.POST)
	public Dict listOne(@RequestBody Dict record) {
		return dictService.listOne(record);
	}

	/**
	 * 查询
	 * 
	 * @param record
	 * @return
	 */
	@RequestMapping(value = "/list", method = RequestMethod.POST)
	public List<Dict> list(@RequestBody Dict record) {
		return dictService.list(record);
	}

	/**
	 * 分页
	 * 
	 * @param record
	 * @return
	 */
	@RequestMapping(value = "/page", method = RequestMethod.POST)
	public PageInfo<Dict> page(@RequestParam(value = "pageSize", defaultValue = "5") Integer pageSize,
			@RequestParam(value = "pageNum", defaultValue = "1") Integer pageNum, @RequestBody Dict record) {
		return dictService.page(pageSize, pageNum, record);
	}

}