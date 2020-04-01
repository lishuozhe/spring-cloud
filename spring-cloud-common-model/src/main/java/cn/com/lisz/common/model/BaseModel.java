package cn.com.lisz.common.model;


import java.util.Date;

import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class BaseModel {

	@ApiModelProperty(value = "自增主键，新增时忽略")
	private Long id;
	
	@ApiModelProperty(value = "平台ID", hidden = true)
	private Long platformId;

	@ApiModelProperty(value = "创建人",hidden = true)
	private String createBy;

	@ApiModelProperty(value = "创建时间",hidden = true)
	private Date createTime;

	@ApiModelProperty(value = "修改人",hidden = true)
	private String updateBy;

	@ApiModelProperty(value = "修改时间",hidden = true)
	private Date updateTime;

	@ApiModelProperty(value = "备注", example = "备注")
	private String remarks;
	
	@ApiModelProperty(value = "删除状态",hidden = true)
	private String delFlag;

}
