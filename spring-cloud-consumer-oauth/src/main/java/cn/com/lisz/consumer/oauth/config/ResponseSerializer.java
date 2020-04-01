package cn.com.lisz.consumer.oauth.config;

import java.io.IOException;

import org.springframework.security.oauth2.common.OAuth2AccessToken;

import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.databind.SerializerProvider;
import com.fasterxml.jackson.databind.ser.std.StdSerializer;

public class ResponseSerializer extends StdSerializer<Response> {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	public ResponseSerializer() {
		super(Response.class);
	}

	@Override
	public void serialize(Response value, JsonGenerator gen, SerializerProvider provider) throws IOException {
		OAuth2AccessToken oAuth2AccessToken = (OAuth2AccessToken) value.getData();
		gen.writeStartObject();
		gen.writeNumberField("status", value.getStatus());
		gen.writeStringField("message", value.getMessage());

		gen.writeObjectFieldStart("data");
		gen.writeStringField("access_token", oAuth2AccessToken.getValue());
		gen.writeStringField("token_type", oAuth2AccessToken.getTokenType());
		gen.writeStringField("refresh_token", oAuth2AccessToken.getRefreshToken().getValue());
		gen.writeNumberField("expires_in", oAuth2AccessToken.getExpiresIn());
		gen.writeStringField("scope", oAuth2AccessToken.getScope().toString());
		gen.writeStringField("jti", oAuth2AccessToken.getAdditionalInformation().get("jti").toString());
		gen.writeEndObject();

		gen.writeEndObject();
	}
}