package cn.com.lisz.consumer.controller;

import cn.com.lisz.common.model.BasePage;
import cn.com.lisz.common.model.BaseResult;
import cn.com.lisz.consumer.entity.Dict;
import cn.com.lisz.consumer.remote.DictRemote;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

/**
 * 字典管理
 * 
 * @author lisz
 *
 */
@RestController
@RequestMapping("/dict")
public class DictController {

	@Autowired
	DictRemote dictRemote;

	@RequestMapping(value = "/save", method = RequestMethod.POST)
	public BaseResult<Dict> save(@Validated @RequestBody Dict record) {
		BaseResult<Dict> result = new BaseResult<Dict>();
		int n = dictRemote.save(record);
		if (n == 1) {
			return result.success();
		}
		return result.failed();
	}

	@RequestMapping(value = "/{id}", method = RequestMethod.DELETE)
	public BaseResult<Dict> delete(@PathVariable("id") Long id) {
		BaseResult<Dict> result = new BaseResult<Dict>();
		int n = dictRemote.delete(id);
		if (n == 1) {
			return result.success();
		}
		return result.failed();
	}

	@RequestMapping(value = "/update", method = RequestMethod.POST)
	public BaseResult<Dict> update(@Validated @RequestBody Dict record) {
		BaseResult<Dict> result = new BaseResult<Dict>();
		int n = dictRemote.update(record);
		if (n == 1) {
			return result.success();
		}
		return result.failed();
	}

	@RequestMapping(value = "/page", method = RequestMethod.POST)
	public BaseResult<BasePage<Dict>> page(@RequestParam(value = "pageSize", defaultValue = "5") Integer pageSize,
			@RequestParam(value = "pageNum", defaultValue = "1") Integer pageNum, @RequestBody Dict record) {
		BaseResult<BasePage<Dict>> result = new BaseResult<BasePage<Dict>>();
		BasePage<Dict> page = dictRemote.page(pageSize, pageNum, record);
		if (record != null) {
			return result.success(page);
		}
		return result.noData();
	}

	@RequestMapping(value = "/{id}", method = RequestMethod.GET)
	public BaseResult<Dict> get(@PathVariable("id") Long id) {
		BaseResult<Dict> result = new BaseResult<Dict>();
		Dict record = new Dict();
		record.setId(id);
		record.setDelFlag("0");
		record = dictRemote.listOne(record);
		if (record != null) {
			return result.success(record);
		}
		return result.noData();
	}

}