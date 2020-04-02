package cn.com.lisz.consumer.oauth.custom;

import org.springframework.security.oauth2.common.exceptions.OAuth2Exception;

import com.fasterxml.jackson.databind.annotation.JsonSerialize;

/**
 * 自定义异常 
 * 
 * @author lisz
 *
 */
@JsonSerialize(using = CustomOauthExceptionSerializer.class)
public class CustomOauthException extends OAuth2Exception {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	public CustomOauthException(String msg) {
		super(msg);
	}
}