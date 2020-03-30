package cn.com.lisz.consumer.admin.controller;

import cn.com.lisz.common.model.base.DictModel;
import cn.com.lisz.common.model.web.PaggingModel;
import cn.com.lisz.common.model.web.RequestCondition;
import cn.com.lisz.common.model.web.ResultModel;
import cn.com.lisz.consumer.admin.remote.DictRemote;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;

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
@Api(tags = "字典管理", description = "维护字典表数据，可进行增删改查操作。")
@RestController
@RequestMapping("/dict")
public class DictController {

	@Autowired
	DictRemote dictRemote;

	@ApiOperation(value = "新增", notes = "新增字典")
	@RequestMapping(value = "/add", method = RequestMethod.POST)
	public ResultModel<DictModel> add(
			@ApiParam(name = "model", value = "字典模型", required = true) @RequestBody DictModel model) {
		ResultModel<DictModel> result = new ResultModel<DictModel>();
		Long id = dictRemote.add(model);
		if (id != null) {
			return result.success();
		}
		return result.failed();
	}

	@ApiOperation(value = "删除", notes = "删除字典")
	@RequestMapping(value = "/del", method = RequestMethod.DELETE)
	public ResultModel<DictModel> del(
			@ApiParam(name = "ids", value = "字典ID集合", required = true) @RequestBody List<Long> ids) {
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

	@ApiOperation(value = "修改", notes = "修改字典")
	@RequestMapping(value = "/edit", method = RequestMethod.POST)
	public ResultModel<DictModel> edit(
			@ApiParam(name = "model", value = "字典模型", required = true) @RequestBody DictModel model) {
		ResultModel<DictModel> result = new ResultModel<DictModel>();
		boolean b = dictRemote.edit(model);
		if (b) {
			return result.success();
		}
		return result.failed();
	}

	@ApiOperation(value = "查看", notes = "查看字典（无权限过滤）")
	@RequestMapping(value = "/{id}", method = RequestMethod.GET)
	public ResultModel<DictModel> get(
			@ApiParam(name = "id", value = "字典ID", required = true) @PathVariable("id") Long id) {
		ResultModel<DictModel> result = new ResultModel<DictModel>();
		DictModel model = dictRemote.get(id);
		if (model != null) {
			return result.success(model);
		}
		return result.noData();
	}

	@ApiOperation(value = "查询", notes = "查询字典")
	@RequestMapping(value = "/list", method = RequestMethod.POST)
	public ResultModel<List<DictModel>> list(
			@ApiParam(name = "model", value = "字典模型", required = true) @RequestBody DictModel model) {
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

	@ApiOperation(value = "分页查询", notes = "分页查询字典")
	@RequestMapping(value = "/page", method = RequestMethod.POST)
	public ResultModel<PaggingModel<DictModel>> page(
			@ApiParam(name = "size", value = "分页参数，每页数量", required = true) @RequestParam(value = "pageSize", defaultValue = "5") Integer size,
			@ApiParam(name = "page", value = "分页参数，当前页数", required = true) @RequestParam(value = "pageNum", defaultValue = "1") Integer page,
			@ApiParam(name = "model", value = "字典模型", required = true) @RequestBody DictModel model) {
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