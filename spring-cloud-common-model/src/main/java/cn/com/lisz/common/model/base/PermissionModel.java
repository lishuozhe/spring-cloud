package cn.com.lisz.common.model.base;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import cn.com.lisz.common.model.BaseModel;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

@ApiModel(value = "权限", description = "字典模型")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class PermissionModel extends BaseModel {

	@ApiModelProperty(value = "父ID", example = "0")
	private Long pid;

	@ApiModelProperty(value = "权限编码", required = true, example = "admin:user:add")
	private String code;

	@ApiModelProperty(value = "权限名称", required = true, example = "添加用户")
	private String name;

	@ApiModelProperty(value = "权限类型: 0-系统, 1-模块, 2-接口", example = "0")
	private String type;

	@ApiModelProperty(value = "排序字段", example = "0")
	private Integer sort;

}