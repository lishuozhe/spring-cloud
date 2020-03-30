package cn.com.lisz.common.model.base;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import cn.com.lisz.common.model.BaseModel;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class DictModel extends BaseModel {

	private Long pid;

	private String value;

	private String label;

	private String code;

	private String codeName;

	private Integer sort;


}