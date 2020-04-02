package cn.com.lisz.consumer.oauth.config;

import java.util.Arrays;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
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

import cn.com.lisz.consumer.oauth.custom.CustomWebResponseExceptionTranslator;

@Configuration
@EnableAuthorizationServer
public class OAuth2AuthorizationConfig extends AuthorizationServerConfigurerAdapter {

	@Value("${auth.secret}")
	private String secret;
	@Value("${auth.client_id}")
	private String clientId;
	@Value("${auth.client_secret}")
	private String clientSecret;
	// accessToken 过期
	@Value("${auth.accessTokenValiditySecond}")
	private int accessTokenValiditySecond;
	@Value("${auth.refreshTokenValiditySecond}")
	private int refreshTokenValiditySecond;
	@Autowired
	private UserDetailsService userDetailsService;
	@Autowired
	private CustomWebResponseExceptionTranslator customWebResponseExceptionTranslator;
	
	@Override
	public void configure(ClientDetailsServiceConfigurer clients) throws Exception {
		// clients.withClientDetails(clientDetailsService);
		clients.inMemory().withClient(clientId).secret(passwordEncoder().encode(clientSecret))
				// 设置权限类型, 用密码，客户端, 刷新的token 权限为所有人
				.authorizedGrantTypes("password", "client_credentials", "refresh_token").scopes("all")
				.accessTokenValiditySeconds(accessTokenValiditySecond)
				.refreshTokenValiditySeconds(refreshTokenValiditySecond);
	}

	@Override
	public void configure(AuthorizationServerEndpointsConfigurer endpoints) throws Exception {
		// @formatter:off
		endpoints.tokenStore(getTokenStore()).authenticationManager(authenticationManager())
				.userDetailsService(userDetailsService)
				// 设置客户端可以使用get和post方式提交
				.allowedTokenEndpointRequestMethods(HttpMethod.GET, HttpMethod.POST);
		// token生成方式
		TokenEnhancerChain tokenEnhancerChain = new TokenEnhancerChain();
		tokenEnhancerChain.setTokenEnhancers(Arrays.asList(accessTokenConverter()));
		endpoints.tokenEnhancer(tokenEnhancerChain);
		endpoints.exceptionTranslator(customWebResponseExceptionTranslator);
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
		daoAuthenticationProvider.setUserDetailsService(userDetailsService);
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
		converter.setSigningKey(secret);
		return converter;
	}

	@Bean
	PasswordEncoder passwordEncoder() {
		// 加密方式
		PasswordEncoder passwordEncoder = new BCryptPasswordEncoder();
		return passwordEncoder;
	}
}
