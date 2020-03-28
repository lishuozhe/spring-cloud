package cn.com.lisz.common.util;

import java.text.ParseException;
import java.text.SimpleDateFormat;

public class TypeUtils {

	/**
	 * 原始值类型转换
	 *
	 * @param type
	 * @param object
	 * @param <T>
	 * @return
	 */
	@SuppressWarnings("unchecked")
	public static <T> T getValue(Class<T> type, Object object) {
		if (type != null && object != null) {
			if (type.isInstance(object)) {
				return (T) object;
			}

			try {
				String typeName = type.getName(), objectContent = object.toString();
				if ("boolean".equals(typeName) || "java.lang.Boolean".equals(typeName)) {
					return (T) Boolean.valueOf(objectContent);
				} else if ("char".equals(typeName) || "java.lang.Character".equals(typeName)) {
					if (0 < objectContent.length()) {
						char[] chars = new char[] { ' ' };
						objectContent.getChars(0, 1, chars, 0);
						return chars.length == 0 ? null : (T) Character.valueOf(chars[0]);
					}
				} else if ("byte".equals(typeName) || "java.lang.Byte".equals(typeName)) {
					return (T) Byte.valueOf(objectContent);
				} else if ("short".equals(typeName) || "java.lang.Short".equals(typeName)) {
					return (T) Short.valueOf(objectContent);
				} else if ("int".equals(typeName) || "java.lang.Integer".equals(typeName)) {
					return (T) Integer.valueOf(objectContent);
				} else if ("long".equals(typeName) || "java.lang.Long".equals(typeName)) {
					return (T) Long.valueOf(objectContent);
				} else if ("float".equals(typeName) || "java.lang.Float".equals(typeName)) {
					return (T) Float.valueOf(objectContent);
				} else if ("double".equals(typeName) || "java.lang.Double".equals(typeName)) {
					return (T) Double.valueOf(objectContent);
				} else if ("java.lang.String".equals(typeName)) {
					return (T) objectContent;
				} else if ("java.util.Date".equals(typeName)) {
					try {
						return (T) new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").parse(objectContent);
					} catch (ParseException e) {
						return (T) new SimpleDateFormat("yyyy-MM-dd").parse(objectContent);
					}
				} else {
					return (T) objectContent;
				}
			} catch (Exception e) {
				e.printStackTrace();
			}
		}

		return null;
	}
}
