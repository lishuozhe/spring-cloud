package cn.com.lisz.common.data.dao.model;

import cn.com.lisz.common.data.util.QueryUtils;

/**
 * 查询条件项目
 * 
 * @author lisz
 *
 */
public class QueryConditionItem {

	/**
	 * 关系
	 */
	private QueryRelationship relationship;

	/**
	 * 字段名
	 */
	private String field;

	/**
	 * 数值
	 */
	private Object value;

	public QueryConditionItem(QueryRelationship relationship, String field, Object value) {
		this.relationship = relationship;
		this.field = field;
		this.value = value;
	}

	public QueryRelationship getRelationship() {
		return relationship;
	}

	public void setRelationship(QueryRelationship relationship) {
		this.relationship = relationship;
	}

	public String getField() {
		return field;
	}

	public void setField(String field) {
		this.field = field;
	}

	public Object getValue() {
		value = QueryUtils.escapeMysqlParamValue(value);
		return value;
	}

	public void setValue(Object value) {
		this.value = value;
	}
}
