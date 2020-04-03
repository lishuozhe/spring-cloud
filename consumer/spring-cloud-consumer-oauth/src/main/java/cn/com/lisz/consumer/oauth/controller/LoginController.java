package cn.com.lisz.consumer.oauth.controller;

import cn.com.lisz.common.model.base.UserModel;
import cn.com.lisz.common.model.web.ResultModel;
import cn.com.lisz.consumer.oauth.service.ILoginService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

/**
 * 登录管理
 * 
 * @author lisz
 *
 */
@Api(tags = "登录管理", description = "")
@RestController
@RequestMapping("/login")
public class LoginController {

	@Autowired
	ILoginService loginService;

	@ApiOperation(value = "登录", notes = "用户登录")
	@RequestMapping(value = "/user", method = RequestMethod.POST)
	public ResultModel<?> user(
			@ApiParam(name = "model", value = "用户模型", required = true) @RequestBody UserModel model) {
		return loginService.user(model);
	}

	@ApiOperation(value = "刷新", notes = "刷新token")
	@RequestMapping(value = "/token", method = RequestMethod.POST)
	public ResultModel<?> token(
			@ApiParam(name = "token", value = "token", required = true) @RequestParam(value = "token") String token) {
		return loginService.token(token);
	}

}