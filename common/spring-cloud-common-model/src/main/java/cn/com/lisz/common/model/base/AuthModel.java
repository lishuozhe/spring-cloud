package cn.com.lisz.common.model.base;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;

import cn.com.lisz.common.model.BaseModel;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

@ApiModel(value = "认证", description = "认证模型")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class AuthModel extends BaseModel {
	
	@ApiModelProperty(value = "用户名")
	private String username;
	
	@ApiModelProperty(value = "密码")
	private String password;


	@ApiModelProperty(value = "角色")
	private List<RoleModel> roles;

}