package cn.com.lisz.common.model.web;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

/**
 * 接口请求查询条件
 * 
 * @author lisz
 *
 */
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class RequestCondition {

	/**
	 * 查询类型
	 */
	String type;
	/**
	 * 查询数值
	 */
	Object value;

	public String toString() {
		return type + "=" + value;
	}
}
