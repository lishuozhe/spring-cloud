package cn.com.lisz.common.util;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Objects;
import java.util.TimeZone;

public class DateUtils {
	public static final String FORMAT_LONG_TIME_SECOND = "yyyyMMddHHmmss";
	private static final String FORMAT = "yyyy-MM-dd HH:mm:ss";

	private static final long ONE_MINUTE = 60;
	private static final long ONE_HOUR = 3600;
	private static final long ONE_DAY = 86400;
	private static final long ONE_MONTH = 2592000;
	private static final long ONE_YEAR = 31104000;

	/**
	 * Date转String
	 *
	 * @param date
	 *            Date
	 * @param format
	 *            时间格式
	 * @return String
	 * @comment
	 * @version 1.0
	 */
	public static String getDateToString(Date date, String format) {
		if (StringUtils.isEmpty(format)) {
			format = FORMAT;
		}
		// 设置要获取到什么样的时间
		SimpleDateFormat sdf = new SimpleDateFormat(format);
		return sdf.format(date);
	}

	/**
	 * Date转String
	 *
	 * @param date
	 *            Date
	 * @return String
	 * @comment
	 * @version 1.0
	 */
	public static String getDateToString(Date date) {
		// 设置要获取到什么样的时间
		SimpleDateFormat sdf = new SimpleDateFormat(FORMAT);
		return sdf.format(date);
	}

	/**
	 * String转Date
	 *
	 * @param date
	 * @param format
	 * @return
	 * @throws ParseException
	 * @comment
	 * @version 1.0
	 */
	public static Date getStringToDate(String date, String format) throws ParseException {
		if (StringUtils.isEmpty(format)) {
			format = FORMAT;
		}
		// 设置要获取到什么样的时间
		SimpleDateFormat sdf = new SimpleDateFormat(format);
		return sdf.parse(date);
	}

	/**
	 * 获取当前日期
	 *
	 * @param format
	 * @return
	 */
	public static String getCurrentDate(String format) {
		return new SimpleDateFormat(format).format(Calendar.getInstance(TimeZone.getTimeZone("GMT+8")).getTime());
	}

	/**
	 * 日期增加月
	 *
	 * @param date
	 *            日期
	 * @param val
	 *            月数
	 * @return 日期
	 */
	public static Date addMonths(Date date, int val) {
		Objects.requireNonNull(date);

		Calendar cal = Calendar.getInstance();
		cal.setTime(date);
		cal.add(Calendar.MONTH, val);

		return cal.getTime();
	}

	/**
	 * 日期增加秒数
	 *
	 * @param time
	 *            日期
	 * @param val
	 *            秒数
	 * @return
	 */
	public static Date addSeconds(Date time, int val) {
		Objects.requireNonNull(time);

		Calendar cal = Calendar.getInstance();
		cal.setTime(time);
		cal.add(Calendar.SECOND, val);

		return cal.getTime();
	}

	/**
	 * 距离今天多久
	 *
	 * @param date
	 * @return
	 *
	 */
	public static String fromToday(Date date) {
		Calendar calendar = Calendar.getInstance();
		calendar.setTime(date);

		long time = date.getTime() / 1000;
		long now = System.currentTimeMillis() / 1000;
		long ago = now - time;
		if (ago == 0) {
			return "刚刚";
		} else if (ago <= ONE_HOUR) {
			return ago / ONE_MINUTE + "分钟前";
		} else if (ago <= ONE_DAY) {
			return ago / ONE_HOUR + "小时前";
		} else if (ago <= ONE_MONTH) {
			long day = ago / ONE_DAY;
			return day + "天前";
		} else if (ago <= ONE_YEAR) {
			long month = ago / ONE_MONTH;
			return month + "个月前";
		} else {
			long year = ago / ONE_YEAR;
			return year + "年前";
		}

	}
}
