package cn.com.lisz.common.util;

import java.math.BigDecimal;
import java.text.DecimalFormat;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Random;
import java.util.UUID;
import java.util.regex.Pattern;
import java.util.stream.Stream;

public class StringUtils {
	/**
	 * 比较
	 *
	 * @param v1
	 * @param v2
	 * @return
	 */
	public static int compare(String v1, String v2) {
		if (isEmpty(v1) && isEmpty(v2)) {
			return 0;
		} else if (isEmpty(v1)) {
			return -1;
		} else if (isEmpty(v2)) {
			return 1;
		}
		return v1.compareTo(v1);
	}

	/**
	 * 判断字符串是否在数组中出现
	 *
	 * @param src
	 *            原始字符串
	 * @param dests
	 *            目标数组
	 * @return
	 */
	public static boolean equals(String src, String... dests) {
		if (!isEmpty(src) && !CollectionUtils.isEmpty(dests)) {
			return Arrays.stream(dests).anyMatch(a -> src.equals(a));
		}
		return false;
	}

	/**
	 * 判断字符串是否在数组中出现(忽略大小写)
	 *
	 * @param src
	 *            原始字符串
	 * @param dests
	 *            目标数组
	 * @return
	 */
	public static boolean equalsIgnoreCase(String src, String... dests) {
		if (!isEmpty(src) && !CollectionUtils.isEmpty(dests)) {
			return Arrays.stream(dests).anyMatch(src::equalsIgnoreCase);
		}
		return false;
	}

	/**
	 * 判断字符串是否在数组中出现
	 *
	 * @param src
	 *            原始字符串
	 * @param dests
	 *            目标数组
	 * @return
	 */
	public static boolean neq(String src, String... dests) {
		if (!isEmpty(src) && !CollectionUtils.isEmpty(dests)) {
			return !Stream.of(dests).allMatch(src::equals);
		}
		return false;
	}

	/**
	 * 判断字符串是否在数组中出现(忽略大小写)
	 *
	 * @param src
	 *            原始字符串
	 * @param dests
	 *            目标数组
	 * @return
	 */
	public static boolean neqIgnoreCase(String src, String... dests) {
		if (!isEmpty(src) && !CollectionUtils.isEmpty(dests)) {
			return !Stream.of(dests).allMatch(src::equalsIgnoreCase);
		}
		return false;
	}

	/**
	 * 字符串是否为空
	 *
	 * @param value
	 * @return
	 */
	public static boolean isEmpty(String value) {
		return value == null || value.isEmpty() || value.trim().isEmpty();
	}

	/**
	 * 对象是否为空
	 * 
	 * @param str
	 * @return
	 */
	public static boolean isEmpty(Object str) {
		return (str == null || "".equals(str));
	}

	/**
	 * 是否为手机号
	 *
	 * @param value
	 * @return
	 */
	public static boolean isMobile(String value) {
		return !isEmpty(value) && Pattern.matches("^1\\d{10}$", value);
	}

	/**
	 * 取得字符串最后一部分
	 *
	 * @param origin
	 *            原始值
	 * @param spliter
	 *            分隔符
	 * @return
	 */
	public static String getLastPart(String origin, String spliter) {
		int index = 0;

		if (!isEmpty(origin) || !isEmpty(spliter)) {
			return (index = origin.lastIndexOf(spliter)) == -1 ? origin : origin.substring(index + 1);
		}

		return null;
	}

	public static void main(String[] args) {
		System.out.print(equals("src", "dest1", "src"));
	}

	/**
	 * 字符串匹配
	 *
	 * @param value
	 * @param regex
	 * @return
	 */
	public static boolean matches(Object value, String regex) {
		if (null == value) {
			return false;
		}
		return value.toString().matches(regex);
	}

	/**
	 * 随即编号
	 *
	 * @return
	 */
	public static String uuId() {
		return UUID.randomUUID().toString().replace("-", "");
	}

	public static String getRandomNumberByLength(int length) {
		String base = "0123456789";
		Random random = new Random();
		StringBuffer sb = new StringBuffer();
		for (int i = 0; i < length; i++) {
			int number = random.nextInt(base.length());
			sb.append(base.charAt(number));
		}
		return sb.toString();
	}

	/**
	 * 生成订单号
	 *
	 * @param orderType
	 * @return
	 */
	public static String getOrderNo(String orderType) {

		int machineId = 1;// 最大支持1-9个集群机器部署
		// 0 代表前面补充0
		// 10 代表长度为10
		// d 代表参数为正数型new SimpleDateFormat("dd-MMM-yyyy HH:mm:ss);
		synchronized ("lockerOrder") {
			return orderType + DateUtils.getCurrentDate("yyMMddHHmmss") + machineId
					+ StringUtils.getRandomNumberByLength(4);
		}

	}

	/**
	 * 使用分隔字符串连接字符串列表
	 *
	 * @param collection
	 *            字符串列表
	 * @param interval
	 *            分隔字符串
	 * @return
	 */
	public static String concat(List<String> collection, String interval) {
		if (!CollectionUtils.isEmpty(collection)) {
			boolean isBlank = isEmpty(interval);
			StringBuilder sb = new StringBuilder();

			collection.forEach(a -> {
				if (!isEmpty(a)) {
					sb.append(sb.length() == 0 || isBlank ? a : interval.concat(a));
				}
			});

			return sb.toString();
		}

		return null;
	}

	/**
	 * 验证身份证号
	 *
	 * @param idCard
	 * @return
	 */
	public static boolean isChinaId(String idCard) {
		if (null == idCard || idCard.isEmpty()) {
			return false;
		}
		Map<String, String> idCardArea = new HashMap<String, String>();
		idCardArea.put("11", "北京");
		idCardArea.put("12", "天津");
		idCardArea.put("13", "河北");
		idCardArea.put("14", "山西");
		idCardArea.put("15", "内蒙古");

		idCardArea.put("21", "辽宁");
		idCardArea.put("22", "吉林");
		idCardArea.put("23", "黑龙江");

		idCardArea.put("31", "上海");
		idCardArea.put("32", "江苏");
		idCardArea.put("33", "浙江");
		idCardArea.put("34", "安徽");
		idCardArea.put("35", "福建");
		idCardArea.put("36", "江西");
		idCardArea.put("37", "山东");

		idCardArea.put("41", "河南");
		idCardArea.put("42", "湖北");
		idCardArea.put("43", "湖南");
		idCardArea.put("44", "广东");
		idCardArea.put("45", "广西");
		idCardArea.put("46", "海南");

		idCardArea.put("50", "重庆");
		idCardArea.put("51", "四川");
		idCardArea.put("52", "贵州");
		idCardArea.put("53", "云南");
		idCardArea.put("54", "西藏");

		idCardArea.put("61", "陕西");
		idCardArea.put("62", "甘肃");
		idCardArea.put("63", "青海");
		idCardArea.put("64", "宁夏");
		idCardArea.put("65", "新疆");

		// 地区检验
		if (!idCardArea.containsKey(idCard.substring(0, (idCard.length() > 2) ? 2 : idCard.length()))) {
			return false;
		}
		String reg;
		boolean b = false;
		switch (idCard.length()) {
		case 18:
			if ((toInt(idCard.substring(6, 10)) % 4 == 0)
					|| ((toInt(idCard.substring(6, 10)) % 100 == 0) && (toInt(idCard.substring(6, 10)) % 4 == 0))) {
				reg = "^[1-9][0-9]{5}19[0-9]{2}((01|03|05|07|08|10|12)(0[1-9]|[1-2][0-9]|3[0-1])|(04|06|09|11)(0[1-9]|[1-2][0-9]|30)|02(0[1-9]|[1-2][0-9]))[0-9]{3}[0-9Xx]$";
			} else {
				reg = "^[1-9][0-9]{5}19[0-9]{2}((01|03|05|07|08|10|12)(0[1-9]|[1-2][0-9]|3[0-1])|(04|06|09|11)(0[1-9]|[1-2][0-9]|30)|02(0[1-9]|1[0-9]|2[0-8]))[0-9]{3}[0-9Xx]$";
			}

			if (idCard.matches(reg)) {
				char[] idCardChar = idCard.toCharArray();
				// 计算校验位
				int y = ((toInt(idCardChar[0]) + toInt(idCardChar[10])) * 7
						+ (toInt(idCardChar[1]) + toInt(idCardChar[11])) * 9
						+ (toInt(idCardChar[2]) + toInt(idCardChar[12])) * 10
						+ (toInt(idCardChar[3]) + toInt(idCardChar[13])) * 5
						+ (toInt(idCardChar[4]) + toInt(idCardChar[14])) * 8
						+ (toInt(idCardChar[5]) + toInt(idCardChar[15])) * 4
						+ (toInt(idCardChar[6]) + toInt(idCardChar[16])) * 2 + (toInt(idCardChar[7]) * 1)
						+ (toInt(idCardChar[8]) * 6) + (toInt(idCardChar[9]) * 3)) % 11;
				// 判断校验位
				if ("10X98765432".charAt(y) == idCardChar[17]) {
					b = true;
				}
			}
			break;
		case 15:
			if (((toInt(idCard.substring(6, 8)) + 1900) % 4 == 0)
					|| (((toInt(idCard.substring(6, 8)) + 1900) % 100 == 0)
							&& ((toInt(idCard.substring(6, 8)) + 1900) % 4 == 0))) {
				reg = "^[1-9][0-9]{5}[0-9]{2}((01|03|05|07|08|10|12)(0[1-9]|[1-2][0-9]|3[0-1])|(04|06|09|11)(0[1-9]|[1-2][0-9]|30)|02(0[1-9]|[1-2][0-9]))[0-9]{3}$";
			} else {
				reg = "^[1-9][0-9]{5}[0-9]{2}((01|03|05|07|08|10|12)(0[1-9]|[1-2][0-9]|3[0-1])|(04|06|09|11)(0[1-9]|[1-2][0-9]|30)|02(0[1-9]|1[0-9]|2[0-8]))[0-9]{3}$";
			}
			if (idCard.matches(reg)) {
				b = true;
			}
			break;
		default:
			break;
		}
		return b;
	}

	public static int toInt(String s) {
		if (s != null && !"".equals(s.trim())) {
			try {
				return Integer.parseInt(s);
			} catch (Exception e) {
				return 0;
			}
		}
		return 0;
	}

	public static int toInt(Object obj) {
		if (null == obj) {
			return 0;
		}
		return toInt(obj.toString());
	}

	public static String toString(BigDecimal value, int scale) {
		StringBuilder sb = new StringBuilder("0");

		if (0 < scale) {
			sb.append(".");
			for (int i = 0; i < scale; i++) {
				sb.append("0");
			}
		}

		DecimalFormat format = new DecimalFormat(sb.toString());
		return format.format(value);
	}

	public static String toString(double value, int scale) {
		StringBuilder sb = new StringBuilder("0");

		if (0 < scale) {
			sb.append(".");
			for (int i = 0; i < scale; i++) {
				sb.append("0");
			}
		}

		DecimalFormat format = new DecimalFormat(sb.toString());
		return format.format(value);
	}

	public static String toString(long value) {
		return Long.toString(value);
	}
}
