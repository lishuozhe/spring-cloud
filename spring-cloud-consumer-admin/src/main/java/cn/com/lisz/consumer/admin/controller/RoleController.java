package cn.com.lisz.consumer.admin.controller;

import cn.com.lisz.common.model.base.RoleModel;
import cn.com.lisz.common.model.web.PaggingModel;
import cn.com.lisz.common.model.web.ResultModel;
import cn.com.lisz.consumer.admin.service.IRoleService;
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
 * 角色管理
 * 
 * @author lisz
 *
 */
@Api(tags = "角色管理", description = "维护角色表数据，可进行增删改查操作。")
@RestController
@RequestMapping("/role")
public class RoleController {

	@Autowired
	IRoleService roleService;

	@PreAuthorize("hasAuthority('admin:role:add')")
	@ApiOperation(value = "新增", notes = "新增角色")
	@RequestMapping(value = "/add", method = RequestMethod.POST)
	public ResultModel<RoleModel> add(
			@ApiParam(name = "model", value = "角色模型", required = true) @RequestBody RoleModel model) {
		ResultModel<RoleModel> result = new ResultModel<RoleModel>();
		Long id = roleService.add(model);
		if (id != null) {
			return result.success();
		}
		return result.failed();
	}

	@PreAuthorize("hasAuthority('admin:role:del')")
	@ApiOperation(value = "删除", notes = "删除角色")
	@RequestMapping(value = "/del", method = RequestMethod.DELETE)
	public ResultModel<RoleModel> del(
			@ApiParam(name = "ids", value = "角色ID集合", required = true) @RequestBody List<Long> ids) {
		ResultModel<RoleModel> result = new ResultModel<RoleModel>();
		boolean b = roleService.del(ids);
		if (b) {
			return result.success();
		}
		return result.failed();
	}

	@PreAuthorize("hasAuthority('admin:role:edit')")
	@ApiOperation(value = "修改", notes = "修改角色")
	@RequestMapping(value = "/edit", method = RequestMethod.POST)
	public ResultModel<RoleModel> edit(
			@ApiParam(name = "model", value = "角色模型", required = true) @RequestBody RoleModel model) {
		ResultModel<RoleModel> result = new ResultModel<RoleModel>();
		boolean b = roleService.edit(model);
		if (b) {
			return result.success();
		}
		return result.failed();
	}

	@PreAuthorize("hasAuthority('admin:role:get')")
	@ApiOperation(value = "查看", notes = "查看角色（无权限过滤）")
	@RequestMapping(value = "/{id}", method = RequestMethod.GET)
	public ResultModel<RoleModel> get(
			@ApiParam(name = "id", value = "角色ID", required = true) @PathVariable("id") Long id) {
		ResultModel<RoleModel> result = new ResultModel<RoleModel>();
		RoleModel model = roleService.get(id);
		if (model != null) {
			return result.success(model);
		}
		return result.noData();
	}

	@PreAuthorize("hasAuthority('admin:role:get')")
	@ApiOperation(value = "查询", notes = "查询角色")
	@RequestMapping(value = "/list", method = RequestMethod.POST)
	public ResultModel<List<RoleModel>> list(
			@ApiParam(name = "model", value = "角色模型", required = true) @RequestBody RoleModel model) {
		ResultModel<List<RoleModel>> result = new ResultModel<List<RoleModel>>();

		List<RoleModel> models = roleService.list(model);
		if (models != null && models.size() > 0) {
			return result.success(models);
		}
		return result.noData();
	}

	@PreAuthorize("hasAuthority('admin:role:get')")
	@ApiOperation(value = "分页查询", notes = "分页查询角色")
	@RequestMapping(value = "/page", method = RequestMethod.POST)
	public ResultModel<PaggingModel<RoleModel>> page(
			@ApiParam(name = "size", value = "分页参数，每页数量", required = true) @RequestParam(value = "pageSize", defaultValue = "5") Integer size,
			@ApiParam(name = "page", value = "分页参数，当前页数", required = true) @RequestParam(value = "pageNum", defaultValue = "1") Integer page,
			@ApiParam(name = "model", value = "角色模型", required = true) @RequestBody RoleModel model) {
		ResultModel<PaggingModel<RoleModel>> result = new ResultModel<PaggingModel<RoleModel>>();

		PaggingModel<RoleModel> pageModel = roleService.page(size, page, model);
		if (pageModel != null) {
			return result.success(pageModel);
		}
		return result.noData();
	}

}