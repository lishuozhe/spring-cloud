package cn.com.lisz.common.model.base;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;

import cn.com.lisz.common.model.BaseModel;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

@ApiModel(value = "角色", description = "角色模型")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class RoleModel extends BaseModel {

	@ApiModelProperty(value = "角色编码", required = true, example = "ADMIN")
	private String code;

	@ApiModelProperty(value = "角色名称", required = true, example = "管理员")
	private String name;

	@ApiModelProperty(value = "角色权限")
	private List<PermissionModel> permissions;

}