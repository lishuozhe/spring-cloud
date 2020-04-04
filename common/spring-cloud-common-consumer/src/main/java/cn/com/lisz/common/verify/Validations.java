package cn.com.lisz.common.verify;

import java.lang.annotation.*;

/**
 * 数据模型验证
 */
@Target(ElementType.FIELD)
@Retention(RetentionPolicy.RUNTIME)
@Documented
public @interface Validations {
	Validation[] value();
}
