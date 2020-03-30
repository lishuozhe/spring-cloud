package cn.com.lisz.common.model.base;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import cn.com.lisz.common.model.BaseModel;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

@ApiModel(value = "字典", description = "字典模型")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class DictModel extends BaseModel {

	@ApiModelProperty(value = "父ID", example = "0")
	private Long pid;

	@ApiModelProperty(value = "字典值", required = true, example = "coal")
	private String value;

	@ApiModelProperty(value = "字典标签", required = true, example = "煤")
	private String label;

	@ApiModelProperty(value = "编码", example = "goods_type")
	private String code;

	@ApiModelProperty(value = "编码名称", example = "货物类型")
	private String codeName;

	@ApiModelProperty(value = "排序字段", example = "0")
	private Integer sort;

}