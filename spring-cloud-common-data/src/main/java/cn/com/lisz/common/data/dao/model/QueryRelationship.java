package cn.com.lisz.common.data.dao.model;

import cn.com.lisz.common.util.StringUtils;

/**
 * 查询条件关系
 * @author lisz
 *
 */
public enum QueryRelationship {
	/**
	 * 
	 */
	Equals("Equals", "相等"),
    NotEquals("NotEquals", "不相等"),
    Great("Great", "大于"),
    Less("Less", "小于"),
    Like("Like", "包含"),
    In("In", "包含"),
    Null("Null", "Null"),
    NotNull("NotNull", "Not Null"),
    Start("Start", "Start With"),
    End("End", "End With"),
    Empty("Empty", "Field is empty"),
    NotEmpty("NotEmpty", "Field is not empty");

    private String name;
    
    private String meno;

    QueryRelationship(String name, String meno) {
        this.name = name;
        this.meno = meno;
    }

    public static boolean isValid(String name) {
        return StringUtils.equalsIgnoreCase(name, Equals.name, Great.name, Less.name, Like.name);
    }

    public String getName() {
        return name;
    }

    public String getMeno() {
        return meno;
    }
}
