package cn.com.lisz.common.oauth;

import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.oauth2.config.annotation.web.configuration.EnableResourceServer;
import org.springframework.security.oauth2.config.annotation.web.configuration.ResourceServerConfigurerAdapter;
import org.springframework.security.oauth2.config.annotation.web.configurers.ResourceServerSecurityConfigurer;
import org.springframework.security.oauth2.provider.token.DefaultTokenServices;
import org.springframework.security.oauth2.provider.token.TokenStore;
import org.springframework.security.oauth2.provider.token.store.JwtAccessTokenConverter;
import org.springframework.security.oauth2.provider.token.store.JwtTokenStore;

import cn.com.lisz.common.oauth.custom.RestAuthenticationEntryPoint;
import cn.com.lisz.common.oauth.custom.RestfulAccessDeniedHandler;

@Configuration
@EnableResourceServer
@EnableGlobalMethodSecurity(prePostEnabled = true)
public class OAuth2ResourceConfig extends ResourceServerConfigurerAdapter {

	@Value("${auth.secret}")
	private String secret;

	@Autowired
	private RestfulAccessDeniedHandler restfulAccessDeniedHandler;
	@Autowired
	private RestAuthenticationEntryPoint restAuthenticationEntryPoint;

	@Override
	public void configure(HttpSecurity http) throws Exception {
		// @formatter:off
		http.authorizeRequests()
				// 允许一些资源可以访问
				.antMatchers(HttpMethod.GET, "/", "/swagger-ui.html", "/swagger-resources/**", "/webjars/**",
						"/v2/api-docs/**", "/actuator/hystrix.stream", "/oauth/**")
				.permitAll()
				// 允许一些URL可以访问
				 .antMatchers("/login/**").permitAll()
				// 跨站请求伪造，这是一个放置跨站请求伪造的攻击的策略设置
				.and().csrf().disable().sessionManagement().sessionCreationPolicy(SessionCreationPolicy.NEVER)
				// 设置一个拒绝访问的提示
				.and().exceptionHandling()
				.authenticationEntryPoint(
						(request, response, authException) -> response.sendError(HttpServletResponse.SC_UNAUTHORIZED))
				.and().authorizeRequests().anyRequest().authenticated();
		// 添加自定义未授权和未登录结果返回
		http.exceptionHandling().accessDeniedHandler(restfulAccessDeniedHandler)
				.authenticationEntryPoint(restAuthenticationEntryPoint);
		// @formatter:on
	}

	@Override
	public void configure(ResourceServerSecurityConfigurer resources) throws Exception {
		DefaultTokenServices defaultTokenServices = new DefaultTokenServices();
		defaultTokenServices.setTokenStore(this.tokenStore());
		resources.tokenServices(defaultTokenServices);
	}

	/**
	 * 资源服务中，token的存储方式（只有jwt方式的时候，才需要配置）
	 * 
	 * @author FYK
	 * @return
	 */
	@Bean
	public TokenStore tokenStore() {
		return new JwtTokenStore(this.accessTokenConverter());
	}

	/**
	 * jwt中，token的编码
	 * 
	 * @author FYK
	 * @return
	 */
	@Bean
	public JwtAccessTokenConverter accessTokenConverter() {
		JwtAccessTokenConverter converter = new JwtAccessTokenConverter();
		converter.setSigningKey(secret);
		return converter;
	}

}