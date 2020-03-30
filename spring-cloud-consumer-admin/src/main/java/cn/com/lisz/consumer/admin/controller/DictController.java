package cn.com.lisz.consumer.admin.controller;

import cn.com.lisz.common.model.base.DictModel;
import cn.com.lisz.common.model.web.PaggingModel;
import cn.com.lisz.common.model.web.RequestCondition;
import cn.com.lisz.common.model.web.ResultModel;
import cn.com.lisz.consumer.admin.remote.DictRemote;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
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

	@RequestMapping(value = "/add", method = RequestMethod.POST)
	public ResultModel<DictModel> add(@RequestBody DictModel model) {
		ResultModel<DictModel> result = new ResultModel<DictModel>();
		Long id = dictRemote.add(model);
		if (id != null) {
			return result.success();
		}
		return result.failed();
	}

	@RequestMapping(value = "/del", method = RequestMethod.DELETE)
	public ResultModel<DictModel> delete(@RequestBody List<Long> ids) {
		ResultModel<DictModel> result = new ResultModel<DictModel>();
		List<RequestCondition> conditions = new ArrayList<RequestCondition>();
		for (Long id : ids) {
			conditions.add(new RequestCondition("in_id", id));
		}
		boolean b = dictRemote.del(conditions);
		if (b) {
			return result.success();
		}
		return result.failed();
	}

	@RequestMapping(value = "/edit", method = RequestMethod.POST)
	public ResultModel<DictModel> edit(@RequestBody DictModel model) {
		ResultModel<DictModel> result = new ResultModel<DictModel>();
		boolean b = dictRemote.edit(model);
		if (b) {
			return result.success();
		}
		return result.failed();
	}

	@RequestMapping(value = "/{id}", method = RequestMethod.GET)
	public ResultModel<DictModel> get(@PathVariable("id") Long id) {
		ResultModel<DictModel> result = new ResultModel<DictModel>();
		DictModel model = dictRemote.get(id);
		if (model != null) {
			return result.success(model);
		}
		return result.noData();
	}

	@RequestMapping(value = "/list", method = RequestMethod.POST)
	public ResultModel<List<DictModel>> list(@RequestBody DictModel model) {
		ResultModel<List<DictModel>> result = new ResultModel<List<DictModel>>();
		List<RequestCondition> conditions = new ArrayList<RequestCondition>();
		if (model.getLabel() != null) {
			conditions.add(new RequestCondition("label", model.getLabel()));
		}

		List<DictModel> models = dictRemote.list(conditions);
		if (models != null && models.size() > 0) {
			return result.success(models);
		}
		return result.noData();
	}

	@RequestMapping(value = "/page", method = RequestMethod.POST)
	public ResultModel<PaggingModel<DictModel>> page(@RequestParam(value = "pageSize", defaultValue = "5") Integer size,
			@RequestParam(value = "pageNum", defaultValue = "1") Integer page, @RequestBody DictModel model) {
		ResultModel<PaggingModel<DictModel>> result = new ResultModel<PaggingModel<DictModel>>();
		List<RequestCondition> conditions = new ArrayList<RequestCondition>();
		if (model.getLabel() != null) {
			conditions.add(new RequestCondition("label", model.getLabel()));
		}
		PaggingModel<DictModel> pageModel = dictRemote.page(size, page, conditions);
		if (pageModel != null) {
			return result.success(pageModel);
		}
		return result.noData();
	}

}