package cn.com.lisz.zuul;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.netflix.zuul.EnableZuulProxy;
import org.springframework.context.annotation.Primary;
import org.springframework.stereotype.Component;

import com.netflix.zuul.ZuulFilter;
import com.netflix.zuul.context.RequestContext;
import com.netflix.zuul.exception.ZuulException;
import com.spring4all.swagger.EnableSwagger2Doc;

import springfox.documentation.swagger.web.SwaggerResource;
import springfox.documentation.swagger.web.SwaggerResourcesProvider;

@EnableSwagger2Doc
@SpringBootApplication
@EnableZuulProxy
public class SpringCloudZuulApplication {

	private final String GRANT_TYPE_PASSWORD = "password";

	private final String GRANT_TYPE_PASSWORD_REFRESH_TOKEN = "refresh_token";

	private final String CLIENT_ID_KEY = "client_id";

	private final String CLIENT_SECRET_KEY = "client_secret";

	private final String GRANT_TYPE_KEY = "grant_type";

	private final String REFRESH_TOKEN_KEY = "refresh_token";

	@Value("${auth.client_id}")
	private String clientId;
	
	@Value("${auth.client_secret}")
	private String clientSecret;

	public static void main(String[] args) {
		SpringApplication.run(SpringCloudZuulApplication.class, args);
	}

	@Component
	@Primary
	class DocumentationConfig implements SwaggerResourcesProvider {
		@Override
		public List<SwaggerResource> get() {
			List<SwaggerResource> resources = new ArrayList<SwaggerResource>();
			resources.add(swaggerResource("admin", "/admin/v2/api-docs", "2.0"));
			return resources;
		}

		private SwaggerResource swaggerResource(String name, String location, String version) {
			SwaggerResource swaggerResource = new SwaggerResource();
			swaggerResource.setName(name);
			swaggerResource.setLocation(location);
			swaggerResource.setSwaggerVersion(version);
			return swaggerResource;
		}
	}

	@Component
	public class ModifyHeaderFilter extends ZuulFilter {
		@Override
		public Object run() throws ZuulException {
			String grantType = GRANT_TYPE_PASSWORD;
			RequestContext requestContext = RequestContext.getCurrentContext();
			HttpServletRequest request = requestContext.getRequest();
			// 1、判断是刷新还是认证
			String refreshToken = request.getParameter(REFRESH_TOKEN_KEY);
			if (refreshToken != null) {
				grantType = GRANT_TYPE_PASSWORD_REFRESH_TOKEN;
			}
			Map<String, List<String>> requestQueryParams = requestContext.getRequestQueryParams();
			if (requestQueryParams == null) {
				requestQueryParams = new HashMap<>();
			}
			// 4、将要新增的参数添加进去,被调用的微服务可以直接去取,就想普通的一样,框架会直接注入进去
			ArrayList<String> paramsList = new ArrayList<>();
			paramsList.add(grantType);
			requestQueryParams.put(GRANT_TYPE_KEY, paramsList);
			paramsList = new ArrayList<>();
			paramsList.add(clientId);
			requestQueryParams.put(CLIENT_ID_KEY, paramsList);
			paramsList = new ArrayList<>();
			paramsList.add(clientSecret);
			requestQueryParams.put(CLIENT_SECRET_KEY, paramsList);
			requestContext.setRequestQueryParams(requestQueryParams);
			return null;
		}

		@Override
		public boolean shouldFilter() {
			// 共享RequestContext，上下文对象
			RequestContext requestContext = RequestContext.getCurrentContext();
			HttpServletRequest request = requestContext.getRequest();
			// 需要拦截的URL
			if ("/security/oauth/token".equalsIgnoreCase(request.getRequestURI())) {
				return true;
			}
			return false;
		}

		@Override
		public int filterOrder() {
			return 0;
		}

		@Override
		public String filterType() {
			return "pre";
		}
	}
}