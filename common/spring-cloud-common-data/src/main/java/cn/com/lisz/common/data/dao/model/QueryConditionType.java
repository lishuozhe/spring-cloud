package cn.com.lisz.common.data.dao.model;

public enum QueryConditionType {
	/**
	 * 
	 */
	And("and", "并且"), Or("or", "或者");

	String name;
	String memo;

	QueryConditionType(String name, String memo) {
		this.name = name;
		this.memo = memo;
	}

	public String getName() {
		return name;
	}

	public String getMemo() {
		return memo;
	}
}
