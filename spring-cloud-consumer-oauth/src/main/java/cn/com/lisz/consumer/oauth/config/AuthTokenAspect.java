package cn.com.lisz.consumer.oauth.config;

import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.ResponseEntity;
import org.springframework.security.oauth2.common.OAuth2AccessToken;
import org.springframework.stereotype.Component;

@Component
@Aspect
public class AuthTokenAspect {

	private Logger logger = LoggerFactory.getLogger(getClass());

	/// @Around是可以改变controller返回值的
	@SuppressWarnings("unchecked")
	@Around("(execution(* org.springframework.security.oauth2.provider.endpoint.TokenEndpoint.postAccessToken(..))) || (execution(* org.springframework.security.oauth2.provider.endpoint.TokenEndpoint.getAccessToken(..)))")
	public Object handleControllerMethod(ProceedingJoinPoint pjp) throws Throwable {
		// 放行
		Response response = new Response();
		Object proceed = pjp.proceed();
		if (proceed != null) {
			ResponseEntity<OAuth2AccessToken> responseEntity = (ResponseEntity<OAuth2AccessToken>) proceed;
			OAuth2AccessToken body = responseEntity.getBody();
			if (responseEntity.getStatusCode().is2xxSuccessful()) {
				response.setStatus(200);
				response.setMessage("操作成功");
				response.setData(body);
			} else {
				logger.error("error:{}", responseEntity.getStatusCode().toString());
				response.setStatus(400);
				response.setMessage("获取授权码失败");
			}
		}
		return ResponseEntity.status(200).body(response);
	}
}
