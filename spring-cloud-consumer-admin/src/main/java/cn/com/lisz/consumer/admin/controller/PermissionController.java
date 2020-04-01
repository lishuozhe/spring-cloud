package cn.com.lisz.consumer.admin.controller;

import cn.com.lisz.common.model.base.PermissionModel;
import cn.com.lisz.common.model.web.PaggingModel;
import cn.com.lisz.common.model.web.ResultModel;
import cn.com.lisz.consumer.admin.service.IPermissionService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

/**
 * 权限管理
 * 
 * @author lisz
 *
 */
@Api(tags = "权限管理", description = "维护权限表数据，可进行增删改查操作。")
@RestController
@RequestMapping("/permission")
public class PermissionController {

	@Autowired
	IPermissionService permissionService;

	@PreAuthorize("hasAuthority('admin:permission:add')")
	@ApiOperation(value = "新增", notes = "新增权限")
	@RequestMapping(value = "/add", method = RequestMethod.POST)
	public ResultModel<PermissionModel> add(
			@ApiParam(name = "model", value = "权限模型", required = true) @RequestBody PermissionModel model) {
		ResultModel<PermissionModel> result = new ResultModel<PermissionModel>();
		Long id = permissionService.add(model);
		if (id != null) {
			return result.success();
		}
		return result.failed();
	}

	@PreAuthorize("hasAuthority('admin:permission:del')")
	@ApiOperation(value = "删除", notes = "删除权限")
	@RequestMapping(value = "/del", method = RequestMethod.DELETE)
	public ResultModel<PermissionModel> del(
			@ApiParam(name = "ids", value = "权限ID集合", required = true) @RequestBody List<Long> ids) {
		ResultModel<PermissionModel> result = new ResultModel<PermissionModel>();
		boolean b = permissionService.del(ids);
		if (b) {
			return result.success();
		}
		return result.failed();
	}

	@PreAuthorize("hasAuthority('admin:permission:edit')")
	@ApiOperation(value = "修改", notes = "修改权限")
	@RequestMapping(value = "/edit", method = RequestMethod.POST)
	public ResultModel<PermissionModel> edit(
			@ApiParam(name = "model", value = "权限模型", required = true) @RequestBody PermissionModel model) {
		ResultModel<PermissionModel> result = new ResultModel<PermissionModel>();
		boolean b = permissionService.edit(model);
		if (b) {
			return result.success();
		}
		return result.failed();
	}

	@PreAuthorize("hasAuthority('admin:permission:get')")
	@ApiOperation(value = "查看", notes = "查看权限（无权限过滤）")
	@RequestMapping(value = "/{id}", method = RequestMethod.GET)
	public ResultModel<PermissionModel> get(
			@ApiParam(name = "id", value = "权限ID", required = true) @PathVariable("id") Long id) {
		ResultModel<PermissionModel> result = new ResultModel<PermissionModel>();
		PermissionModel model = permissionService.get(id);
		if (model != null) {
			return result.success(model);
		}
		return result.noData();
	}

	@PreAuthorize("hasAuthority('admin:permission:get')")
	@ApiOperation(value = "查询", notes = "查询权限")
	@RequestMapping(value = "/list", method = RequestMethod.POST)
	public ResultModel<List<PermissionModel>> list(
			@ApiParam(name = "model", value = "权限模型", required = true) @RequestBody PermissionModel model) {
		ResultModel<List<PermissionModel>> result = new ResultModel<List<PermissionModel>>();

		List<PermissionModel> models = permissionService.list(model);
		if (models != null && models.size() > 0) {
			return result.success(models);
		}
		return result.noData();
	}

	@PreAuthorize("hasAuthority('admin:permission:get')")
	@ApiOperation(value = "分页查询", notes = "分页查询权限")
	@RequestMapping(value = "/page", method = RequestMethod.POST)
	public ResultModel<PaggingModel<PermissionModel>> page(
			@ApiParam(name = "size", value = "分页参数，每页数量", required = true) @RequestParam(value = "pageSize", defaultValue = "5") Integer size,
			@ApiParam(name = "page", value = "分页参数，当前页数", required = true) @RequestParam(value = "pageNum", defaultValue = "1") Integer page,
			@ApiParam(name = "model", value = "权限模型", required = true) @RequestBody PermissionModel model) {
		ResultModel<PaggingModel<PermissionModel>> result = new ResultModel<PaggingModel<PermissionModel>>();

		PaggingModel<PermissionModel> pageModel = permissionService.page(size, page, model);
		if (pageModel != null) {
			return result.success(pageModel);
		}
		return result.noData();
	}

}