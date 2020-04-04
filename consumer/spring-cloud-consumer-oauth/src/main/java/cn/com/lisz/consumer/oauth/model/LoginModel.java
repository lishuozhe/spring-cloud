package cn.com.lisz.consumer.oauth.model;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import cn.com.lisz.common.model.BaseModel;
import cn.com.lisz.common.verify.Validation;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

@ApiModel(value = "用户登录", description = "用户登录模型")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class LoginModel extends BaseModel {

	@Validation(policy = "user", require = true, message = "用户名不能为空")
	@ApiModelProperty(value = "用户名", required = true)
	private String username;

	@Validation(policy = "user", require = true, message = "密码不能为空")
	@ApiModelProperty(value = "密码", required = true)
	private String password;

}