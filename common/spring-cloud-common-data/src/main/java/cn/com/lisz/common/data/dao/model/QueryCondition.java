package cn.com.lisz.common.data.dao.model;

import java.util.List;

/**
 * 查询条件
 * 
 * @author lisz
 *
 */

public class QueryCondition {

	private QueryConditionType conditionType;
	private List<QueryConditionItem> conditionItems;

	public QueryCondition() {
	}

	public QueryCondition(QueryConditionType conditionType, List<QueryConditionItem> conditionItems) {
		this.conditionType = conditionType;
		this.conditionItems = conditionItems;
	}

	public QueryConditionType getConditionType() {
		return conditionType;
	}

	public void setConditionType(QueryConditionType conditionType) {
		this.conditionType = conditionType;
	}

	public List<QueryConditionItem> getConditionItems() {
		return conditionItems;
	}

	public void setConditionItems(List<QueryConditionItem> conditionItems) {
		this.conditionItems = conditionItems;
	}
}
