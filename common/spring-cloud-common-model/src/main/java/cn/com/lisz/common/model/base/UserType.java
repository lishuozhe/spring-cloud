package cn.com.lisz.common.model.base;

public enum UserType {
	/**
	 * 
	 */
	U("U-", "用户"), M("M-", "会员");

	String name;
	String memo;

	UserType(String name, String memo) {
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
