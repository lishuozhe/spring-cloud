package cn.com.lisz.common.oauth.custom;

import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.BeanWrapper;
import org.springframework.beans.BeanWrapperImpl;
import org.springframework.core.annotation.Order;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;

/**
 * 添加统一处理切面
 * 
 * @author lisz
 */
@Aspect
@Component
@Order(1)
public class AddAspect {

	private static final Logger LOGGER = LoggerFactory.getLogger(AddAspect.class);

	private static final String CREATEBY = "createBy";
	
	private static final String UPDATEBY = "updateBy";

	@Pointcut("(execution(public * cn.com.lisz.consumer.*.service.impl.*.add*(..))) || (execution(public * cn.com.lisz.consumer.*.service.impl.*.save*(..))) || (execution(public * cn.com.lisz.consumer.*.service.impl.*.insert*(..)))")
	public void add() {
	}

	@Before("add()")
	public void doAddBefore(JoinPoint joinPoint) throws Throwable {
		String username = getUsername();
		LOGGER.info("切面-注入创建人-" + username);
		Object[] args = joinPoint.getArgs();
		if (args != null && args.length > 0) {
			Object argument = args[0];
			BeanWrapper beanWrapper = new BeanWrapperImpl(argument);
			// 设置创建人
			if (beanWrapper.isWritableProperty(CREATEBY)) {
				beanWrapper.setPropertyValue(CREATEBY, username);
			}
		}
	}
	
	@Pointcut("(execution(public * cn.com.lisz.consumer.*.service.impl.*.edit*(..))) || (execution(public * cn.com.lisz.consumer.*.service.impl.*.update*(..))) || (execution(public * cn.com.lisz.consumer.*.service.impl.*.modify*(..)))")
	public void edit() {
	}

	@Before("edit()")
	public void doEditBefore(JoinPoint joinPoint) throws Throwable {
		String username = getUsername();
		LOGGER.info("切面-注入更新人-" + username);
		Object[] args = joinPoint.getArgs();
		if (args != null && args.length > 0) {
			Object argument = args[0];
			BeanWrapper beanWrapper = new BeanWrapperImpl(argument);
			// 设置创建人
			if (beanWrapper.isWritableProperty(UPDATEBY)) {
				beanWrapper.setPropertyValue(UPDATEBY, username);
			}
		}
	}

	public String getUsername() {
		return (String) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
	}
}
