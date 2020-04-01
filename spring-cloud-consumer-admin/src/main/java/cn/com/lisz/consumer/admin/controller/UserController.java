package cn.com.lisz.consumer.admin.controller;

import cn.com.lisz.common.model.base.UserModel;
import cn.com.lisz.common.model.web.PaggingModel;
import cn.com.lisz.common.model.web.ResultModel;
import cn.com.lisz.consumer.admin.service.IUserService;
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
 * 用户管理
 * 
 * @author lisz
 *
 */
@Api(tags = "用户管理", description = "维护用户表数据，可进行增删改查操作。")
@RestController
@RequestMapping("/user")
public class UserController {

	@Autowired
	IUserService userService;

	@PreAuthorize("hasAuthority('admin:user:add')")
	@ApiOperation(value = "新增", notes = "新增用户")
	@RequestMapping(value = "/add", method = RequestMethod.POST)
	public ResultModel<UserModel> add(
			@ApiParam(name = "model", value = "用户模型", required = true) @RequestBody UserModel model) {
		ResultModel<UserModel> result = new ResultModel<UserModel>();
		Long id = userService.add(model);
		if (id != null) {
			return result.success();
		}
		return result.failed();
	}

	@PreAuthorize("hasAuthority('admin:user:del')")
	@ApiOperation(value = "删除", notes = "删除用户")
	@RequestMapping(value = "/del", method = RequestMethod.DELETE)
	public ResultModel<UserModel> del(
			@ApiParam(name = "ids", value = "用户ID集合", required = true) @RequestBody List<Long> ids) {
		ResultModel<UserModel> result = new ResultModel<UserModel>();
		boolean b = userService.del(ids);
		if (b) {
			return result.success();
		}
		return result.failed();
	}

	@PreAuthorize("hasAuthority('admin:user:edit')")
	@ApiOperation(value = "修改", notes = "修改用户")
	@RequestMapping(value = "/edit", method = RequestMethod.POST)
	public ResultModel<UserModel> edit(
			@ApiParam(name = "model", value = "用户模型", required = true) @RequestBody UserModel model) {
		ResultModel<UserModel> result = new ResultModel<UserModel>();
		boolean b = userService.edit(model);
		if (b) {
			return result.success();
		}
		return result.failed();
	}

	@PreAuthorize("hasAuthority('admin:user:get')")
	@ApiOperation(value = "查看", notes = "查看用户（无权限过滤）")
	@RequestMapping(value = "/{id}", method = RequestMethod.GET)
	public ResultModel<UserModel> get(
			@ApiParam(name = "id", value = "用户ID", required = true) @PathVariable("id") Long id) {
		ResultModel<UserModel> result = new ResultModel<UserModel>();
		UserModel model = userService.get(id);
		if (model != null) {
			return result.success(model);
		}
		return result.noData();
	}

	@PreAuthorize("hasAuthority('admin:user:get')")
	@ApiOperation(value = "查询", notes = "查询用户")
	@RequestMapping(value = "/list", method = RequestMethod.POST)
	public ResultModel<List<UserModel>> list(
			@ApiParam(name = "model", value = "用户模型", required = true) @RequestBody UserModel model) {
		ResultModel<List<UserModel>> result = new ResultModel<List<UserModel>>();

		List<UserModel> models = userService.list(model);
		if (models != null && models.size() > 0) {
			return result.success(models);
		}
		return result.noData();
	}

	@PreAuthorize("hasAuthority('admin:user:get')")
	@ApiOperation(value = "分页查询", notes = "分页查询用户")
	@RequestMapping(value = "/page", method = RequestMethod.POST)
	public ResultModel<PaggingModel<UserModel>> page(
			@ApiParam(name = "size", value = "分页参数，每页数量", required = true) @RequestParam(value = "pageSize", defaultValue = "5") Integer size,
			@ApiParam(name = "page", value = "分页参数，当前页数", required = true) @RequestParam(value = "pageNum", defaultValue = "1") Integer page,
			@ApiParam(name = "model", value = "用户模型", required = true) @RequestBody UserModel model) {
		ResultModel<PaggingModel<UserModel>> result = new ResultModel<PaggingModel<UserModel>>();

		PaggingModel<UserModel> pageModel = userService.page(size, page, model);
		if (pageModel != null) {
			return result.success(pageModel);
		}
		return result.noData();
	}

}