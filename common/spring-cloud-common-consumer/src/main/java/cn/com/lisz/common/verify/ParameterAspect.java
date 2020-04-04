package cn.com.lisz.common.verify;

import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.springframework.stereotype.Component;
import org.springframework.util.CollectionUtils;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import cn.com.lisz.common.model.BaseModel;
import cn.com.lisz.common.model.web.ResultModel;

@Component
@Aspect
public class ParameterAspect {

	/**
	 * 定义切入点:拦截controller层所有方法
	 */
	@Pointcut("execution(* cn.com.lisz.consumer.*.controller..*(..))")
	public void params() {
	}

	/**
	 * 定义环绕通知
	 *
	 * @param joinPoint
	 * @throws Throwable
	 */
	@Around("params()")
	public Object doBefore(ProceedingJoinPoint joinPoint) throws Throwable {
		ServletRequestAttributes attributes = (ServletRequestAttributes) RequestContextHolder.getRequestAttributes();
		HttpServletRequest request = attributes.getRequest();
		Object[] args = joinPoint.getArgs();// 参数
		for (Object arg : args) {
			if (arg instanceof BaseModel) {
				BaseModel model = (BaseModel) arg;
				String[] ps = request.getServletPath().split("/");
				String p = ps[ps.length - 1];
				Map<String, String> messages = null;
				if (!CollectionUtils.isEmpty(messages = ValidateUtil.validate(model, p))) {
					ResultModel<Map<String, String>> r = new ResultModel<Map<String, String>>();
					r.setStatus(ResultModel.PARAM_ERROR);
					r.setMessage("参数错误");
					r.setData(messages);
					return r;
				}
			}
		}
		Object result = joinPoint.proceed();
		return result;
	}
}
