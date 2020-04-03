package cn.com.lisz.common.model.base;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;

import cn.com.lisz.common.model.BaseModel;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

@ApiModel(value = "用户", description = "用户模型")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class UserModel extends BaseModel {

	@ApiModelProperty(value = "用户名", required = true)
	private String username;

	@ApiModelProperty(value = "邮箱")
	private String email;

	@ApiModelProperty(value = "手机")
	private String mobile;

	@ApiModelProperty(value = "密码", required = true)
	private String password;

	@ApiModelProperty(value = "认证用户名", hidden = true)
	private String authUsername;

	@ApiModelProperty(value = "认证密码", hidden = true)
	private String authPassword;

	@ApiModelProperty(value = "角色ID集合")
	private List<Long> roleIds;
}