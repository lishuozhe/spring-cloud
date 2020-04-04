package cn.com.lisz.common.verify;

import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.regex.Pattern;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.util.StringUtils;

import com.alibaba.fastjson.JSON;

import cn.com.lisz.common.model.BaseModel;
import io.swagger.annotations.ApiModelProperty;

import static java.util.stream.Collectors.toList;

import java.lang.reflect.Field;

public class ValidateUtil {

	private static final String FORMAT_VALID_FAILED = "%s不合法";
	private static Logger logger;
	static {
		logger = LoggerFactory.getLogger(ValidateUtil.class);
	}

	public static <TModel extends BaseModel> Map<String, String> validate(TModel model, String policy) {
		if (model != null) {
			boolean isPolicy = !StringUtils.isEmpty(policy);
			Map<String, String> result = new HashMap<>();
			List<Field> fields = Arrays.stream(model.getClass().getDeclaredFields())
					.filter(a -> a.isAnnotationPresent(Validation.class) || a.isAnnotationPresent(Validations.class))
					.collect(toList());
			fields.forEach(a -> {
				List<Validation> validations = a.isAnnotationPresent(Validation.class)
						? Arrays.asList(a.getAnnotation(Validation.class))
						: Arrays.asList(a.getAnnotation(Validations.class).value());
				validations.forEach(i -> {
					try {
						if ((!isPolicy && StringUtils.isEmpty(i.policy())) || (isPolicy && policy.equals(i.policy()))) {
							a.setAccessible(true);
							Object fieldValue = a.get(model);

							if (i.require() && (fieldValue == null
									|| (fieldValue instanceof String && StringUtils.isEmpty((String) fieldValue)))) {
								result.put(a.getName(), getMessage(a, i));
							} else {
								String message = getValidationMessage(fieldValue, a, i);
								if (!StringUtils.isEmpty(message)) {
									result.put(a.getName(), message);
								}
							}
						}
					} catch (Exception e) {
						logger.warn(String.format("数据校验异常, model:%s", getEntryInfo(model)), e);
					}
				});
			});

			return result;
		}
		return null;
	}

	/**
	 * 数据对象描述信息取得
	 *
	 * @param entry
	 *            数据对象实例
	 * @return
	 */
	public static <TEntry> String getEntryInfo(TEntry entry) {
		try {
			return JSON.toJSONString(entry);
		} catch (Exception ignore) {
		}

		return null;
	}

	private static String getMessage(Field field, Validation validation) {

		if (!StringUtils.isEmpty(validation.message())) {
			return validation.message();
		}

		if (field.isAnnotationPresent(ApiModelProperty.class)) {
			ApiModelProperty apiModelProperty = field.getAnnotation(ApiModelProperty.class);
			return String.format(FORMAT_VALID_FAILED,
					!StringUtils.isEmpty(apiModelProperty.value()) ? apiModelProperty.value() : field.getName());
		}

		return String.format(FORMAT_VALID_FAILED, field.getName());
	}

	private static String getValidationMessage(Object value, Field field, Validation validation) {
		boolean isValidated = true;
		String regex, strValue;
		double doubleValue;
		ValidationRule rule = validation.rule();

		switch (rule) {
		case UserName:
			if (value instanceof String && !StringUtils.isEmpty(strValue = (String) value)) {
				isValidated = Pattern.matches("^[a-zA-Z][a-zA-Z0-9]{2,20}$", strValue);
			}
			break;
		case Password:
			if (value instanceof String && !StringUtils.isEmpty(strValue = (String) value)) {
				isValidated = Pattern.matches("^[a-zA-Z0-9]{6,20}$", strValue);
			}
			break;
		case CorporationName:
			if (value instanceof String && !StringUtils.isEmpty(strValue = (String) value)) {
				isValidated = Pattern.matches("^\\S{5,50}$", strValue);
			}
			break;
		case CorporationShort:
			if (value instanceof String && !StringUtils.isEmpty(strValue = (String) value)) {
				isValidated = Pattern.matches("^[\\u0391-\\uFFE5]{2,5}$", strValue);
			}
			break;
		case PersonalName:
			if (value instanceof String && !StringUtils.isEmpty(strValue = (String) value)) {
				isValidated = Pattern.matches("^\\S{2,10}$", strValue);
			}
			break;
		case Contacts:
			if (value instanceof String && !StringUtils.isEmpty(strValue = (String) value)) {
				isValidated = Pattern.matches("^\\S{2,10}$", strValue);
			}
			break;
		case Mobile:
			if (value instanceof String && !StringUtils.isEmpty(strValue = (String) value)) {
				isValidated = Pattern.matches("^1\\d{10}$", strValue);
			}
			break;
		case Captcha:
			if (value instanceof String && !StringUtils.isEmpty(strValue = (String) value)) {
				isValidated = Pattern.matches("^[0-9]{6}$", strValue);
			}
			break;
		case IC:
			if (value instanceof String && !StringUtils.isEmpty(strValue = (String) value)) {
				isValidated = Pattern.matches("^[a-zA-Z0-9]{18}$", strValue);
			}
			break;
		case TIR:
			if (value instanceof String && !StringUtils.isEmpty(strValue = (String) value)) {
				isValidated = Pattern.matches("^[\\u0391-\\uFFE5]{0,5}\\d{11,12}(-\\d{1,2})?$", strValue);
			}
			break;
		case IdCard:
			if (value instanceof String && !StringUtils.isEmpty(strValue = (String) value)) {
				isValidated = Pattern.matches("^(\\d{6})(18|19|20)?(\\d{2})([01]\\d)([0123]\\d)(\\d{3})(\\d|X|x)?$",
						strValue);
			}
			break;
		case DetailedAddress:
			if (value instanceof String && !StringUtils.isEmpty(strValue = (String) value)) {
				isValidated = Pattern.matches("^\\S{5,50}$", strValue);
			}
			break;
		case CompanyProfile:
			if (value instanceof String && !StringUtils.isEmpty(strValue = (String) value)) {
				isValidated = Pattern.matches("^[a-zA-Z0-9\\u0391-\\uFFE5]{0,200}$", strValue);
			}
			break;
		case CustomerName:
			if (value instanceof String && !StringUtils.isEmpty(strValue = (String) value)) {
				isValidated = Pattern.matches("^\\S{2,20}$", strValue);
			}
			break;
		case CarNumber:
			if (value instanceof String && !StringUtils.isEmpty(strValue = (String) value)) {
				isValidated = Pattern.matches(
						"^[京津沪渝冀豫云辽黑湘皖鲁新苏浙赣鄂桂甘晋蒙陕吉闽贵粤青藏川宁琼使领A-Z]{1}[A-Z]{1}[A-Z0-9]{4}[A-Z0-9挂学警港澳]{1}$", strValue);
			}
			break;
		case DriverLicenseNumber:
			if (value instanceof String && !StringUtils.isEmpty(strValue = (String) value)) {
				isValidated = Pattern.matches("^[1-9]\\d{14}$", strValue);
			}
			break;
		case Remark:
			if (value instanceof String && !StringUtils.isEmpty(strValue = (String) value)) {
				isValidated = Pattern.matches("^[\\S]{0,200}$", strValue);
			}
			break;
		case Amount:
			if ((value instanceof Float && (doubleValue = (float) value) != 0)
					|| (value instanceof Double && (doubleValue = (double) value) != 0)) {
				isValidated = 0 < doubleValue && doubleValue < 9999999999.99;
			}
			break;
		case Number:
			if ((value instanceof Float && (doubleValue = (float) value) != 0)
					|| (value instanceof Double && (doubleValue = (double) value) != 0)) {
				isValidated = 0 < doubleValue && doubleValue < 9999999999.99;
			}
			break;
		case TaxRate:
			if ((value instanceof Float && (doubleValue = (float) value) != 0)
					|| (value instanceof Double && (doubleValue = (double) value) != 0)) {
				isValidated = 0 < doubleValue && doubleValue < 1;
			}
			break;
		case BankName:
			if (value instanceof String && !StringUtils.isEmpty(strValue = (String) value)) {
				isValidated = Pattern.matches("^[\\u0391-\\uFFE5]{5,50}$", strValue);
			}
			break;
		case MobileOrPhone:
			if (value instanceof String && !StringUtils.isEmpty(strValue = (String) value)) {
				isValidated = Pattern.matches(
						"^(0?(13[0-9]|15[012356789]|17[013678]|18[0-9]|14[57])[0-9]{8})|(400|800)([0-9\\\\-]{7,10})|(([0-9]{4}|[0-9]{3})(-| )?)?([0-9]{7,8})((-| |转)*([0-9]{1,4}))?$",
						strValue);
			}
			break;
		case Telephone:
			if (value instanceof String && !StringUtils.isEmpty(strValue = (String) value)) {
				isValidated = Pattern.matches("^((\\d{3,4}-?)?\\d{7,8})|(1\\d{10})$", strValue);
			}
			break;
		case BankAccount:
			if (value instanceof String && !StringUtils.isEmpty(strValue = (String) value)) {
				isValidated = Pattern.matches("^([1-9]{1})(\\d{14}|\\d{18})$", strValue);
			}
			break;
		case MailAddress:
			if (value instanceof String && !StringUtils.isEmpty(strValue = (String) value)) {
				isValidated = Pattern.matches("^\\S{5,50}$", strValue);
			}
			break;
		case GoodsName:
			if (value instanceof String && !StringUtils.isEmpty(strValue = (String) value)) {
				isValidated = Pattern.matches("^[a-zA-Z0-9\\u0391-\\uFFE5]{2,10}$", strValue);
			}
			break;
		case GoodsNumber:
			if ((value instanceof Float && (doubleValue = (float) value) != 0)
					|| (value instanceof Double && (doubleValue = (double) value) != 0)) {
				isValidated = 0 < doubleValue && doubleValue < 9999999999.99;
			}
			break;
		case GoodsWorth:
			if ((value instanceof Float && (doubleValue = (float) value) != 0)
					|| (value instanceof Double && (doubleValue = (double) value) != 0)) {
				isValidated = 0 < doubleValue && doubleValue < 9999999999.99;
			}
			break;
		case PrepayRatio:
			if ((value instanceof Float && (doubleValue = (float) value) != 0)
					|| (value instanceof Double && (doubleValue = (double) value) != 0)) {
				isValidated = 0 < doubleValue && doubleValue < 99.99;
			}
			break;
		case PrepayAmount:
			if ((value instanceof Float && (doubleValue = (float) value) != 0)
					|| (value instanceof Double && (doubleValue = (double) value) != 0)) {
				isValidated = 0 < doubleValue && doubleValue < 9999999999.99;
			}
			break;
		case payPass:
			if (value instanceof String && !StringUtils.isEmpty(strValue = (String) value)) {
				isValidated = Pattern.matches("^[0-9]{6}$", strValue);
			}
			break;
		case mail:
			if (value instanceof String && !StringUtils.isEmpty(strValue = (String) value)) {
				isValidated = Pattern.matches("^[a-zA-Z0-9_-]+@[a-zA-Z0-9_-]+(\\.[a-zA-Z0-9_-]+)+$", strValue);
			}
			break;
		case None:
			if (!StringUtils.isEmpty(regex = validation.regex()) && value instanceof String
					&& !StringUtils.isEmpty(strValue = (String) value)) {
				isValidated = Pattern.matches(regex, strValue);
			}

			break;
		default:
			break;
		}

		return isValidated ? null : getMessage(field, validation);
	}
}
