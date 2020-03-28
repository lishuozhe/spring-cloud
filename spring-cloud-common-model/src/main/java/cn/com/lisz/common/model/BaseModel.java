package cn.com.lisz.common.model;


import java.util.Date;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class BaseModel {

	private Long id;
	
	private Long platformId;

	private Long createBy;

	private Date createTime;

	private Long updateBy;

	private Date updateTime;

	private String remarks;
	
	private String delFlag;

}
