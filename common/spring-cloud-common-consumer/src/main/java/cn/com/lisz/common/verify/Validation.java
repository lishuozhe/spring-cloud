package cn.com.lisz.common.verify;

import java.lang.annotation.*;

/**
 * 数据模型验证
 */
@Target(ElementType.FIELD)
@Retention(RetentionPolicy.RUNTIME)
@Documented
@Repeatable(Validations.class)
public @interface Validation {
	/**
	 * 策略
	 *
	 * @return
	 */
	String policy() default "";

	/**
	 * 是否必须
	 *
	 * @return
	 */
	boolean require() default true;

	/**
	 * 消息名
	 *
	 * @return
	 */
	String name() default "";

	/**
	 * 验证错误信息
	 *
	 * @return
	 */
	String message() default "";

	/**
	 * 规则
	 *
	 * @return
	 */
	ValidationRule rule() default ValidationRule.None;

	/**
	 * 自定义校验规则
	 *
	 * @return
	 */
	String regex() default "";

}
