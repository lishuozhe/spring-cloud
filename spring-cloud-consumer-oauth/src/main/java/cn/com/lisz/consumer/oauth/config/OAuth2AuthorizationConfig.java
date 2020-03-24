package cn.com.lisz.consumer.oauth.config;

import java.util.Arrays;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.oauth2.config.annotation.configurers.ClientDetailsServiceConfigurer;
import org.springframework.security.oauth2.config.annotation.web.configuration.AuthorizationServerConfigurerAdapter;
import org.springframework.security.oauth2.config.annotation.web.configuration.EnableAuthorizationServer;
import org.springframework.security.oauth2.config.annotation.web.configurers.AuthorizationServerEndpointsConfigurer;
import org.springframework.security.oauth2.config.annotation.web.configurers.AuthorizationServerSecurityConfigurer;
import org.springframework.security.oauth2.provider.token.TokenEnhancerChain;
import org.springframework.security.oauth2.provider.token.TokenStore;
import org.springframework.security.oauth2.provider.token.store.JwtAccessTokenConverter;
import org.springframework.security.oauth2.provider.token.store.JwtTokenStore;
import org.springframework.security.provisioning.InMemoryUserDetailsManager;

@Configuration
@EnableAuthorizationServer
public class OAuth2AuthorizationConfig extends AuthorizationServerConfigurerAdapter {

	// accessToken 过期
	private int accessTokenValiditySecond = 60 * 60 * 2; // 2小时
	private int refreshTokenValiditySecond = 60 * 60 * 24 * 7; // 7 天

	@Override
	public void configure(ClientDetailsServiceConfigurer clients) throws Exception {
		// clients.withClientDetails(clientDetailsService);
		clients.inMemory().withClient("yyy_client").secret(passwordEncoder().encode("yyy_secret"))
				.authorizedGrantTypes("password", "client_credentials", "refresh_token").scopes("all") // 设置权限类型,
																										// 用密码，客户端,
																										// 刷新的token
																										// 权限为所有人
				.accessTokenValiditySeconds(accessTokenValiditySecond)
				.refreshTokenValiditySeconds(refreshTokenValiditySecond);

	}

	@Override
	public void configure(AuthorizationServerEndpointsConfigurer endpoints) throws Exception {
		// @formatter:off
		endpoints.tokenStore(getTokenStore()).authenticationManager(authenticationManager())
				.userDetailsService(userDetailsService())
				// 设置客户端可以使用get和post方式提交
				.allowedTokenEndpointRequestMethods(HttpMethod.GET, HttpMethod.POST);
		// token生成方式
		TokenEnhancerChain tokenEnhancerChain = new TokenEnhancerChain();
		tokenEnhancerChain.setTokenEnhancers(Arrays.asList(accessTokenConverter()));
		endpoints.tokenEnhancer(tokenEnhancerChain);
		// @formatter:on
	}

	@Override
	public void configure(AuthorizationServerSecurityConfigurer oauthServer) throws Exception {
		// @formatter:off
		oauthServer
				// 设置一个编码方式
				.passwordEncoder(passwordEncoder())
				// 获取token的请求，不进行拦截
				.tokenKeyAccess("permitAll()")
				// 检查token的请求，要先通过验证
				.checkTokenAccess("isAuthenticated()").allowFormAuthenticationForClients();
		// @formatter:on
	}

	// 设置添加用户信息,正常应该从数据库中读取
	@Bean
	UserDetailsService userDetailsService() {
		InMemoryUserDetailsManager userDetailsService = new InMemoryUserDetailsManager();
		userDetailsService.createUser(User.withUsername("user_1").password(passwordEncoder().encode("123456"))
				.authorities("ROLE_USER").build());
		userDetailsService.createUser(User.withUsername("user_2").password(passwordEncoder().encode("123456"))
				.authorities("ROLE_USER").build());
		return userDetailsService;
	}

	@Bean
	AuthenticationManager authenticationManager() {
		AuthenticationManager authenticationManager = new AuthenticationManager() {
			public Authentication authenticate(Authentication authentication) throws AuthenticationException {
				return daoAuhthenticationProvider().authenticate(authentication);
			}
		};
		return authenticationManager;
	}

	@Bean
	public AuthenticationProvider daoAuhthenticationProvider() {
		DaoAuthenticationProvider daoAuthenticationProvider = new DaoAuthenticationProvider();
		daoAuthenticationProvider.setUserDetailsService(userDetailsService());
		daoAuthenticationProvider.setHideUserNotFoundExceptions(false);
		daoAuthenticationProvider.setPasswordEncoder(passwordEncoder());
		return daoAuthenticationProvider;
	}

	@Bean
	public TokenStore getTokenStore() {
		return new JwtTokenStore(accessTokenConverter());
	}

	@Bean
	public JwtAccessTokenConverter accessTokenConverter() {
		JwtAccessTokenConverter converter = new JwtAccessTokenConverter();
		// 设置设置JWT签名密钥
		converter.setSigningKey("fyk123");
		return converter;
	}

	@Bean
	PasswordEncoder passwordEncoder() {
		// 加密方式
		PasswordEncoder passwordEncoder = new BCryptPasswordEncoder();
		return passwordEncoder;
	}
}
