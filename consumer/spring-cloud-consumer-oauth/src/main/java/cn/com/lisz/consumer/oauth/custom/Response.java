package cn.com.lisz.consumer.oauth.custom;

import com.fasterxml.jackson.databind.annotation.JsonSerialize;

import lombok.Data;

@JsonSerialize(using = ResponseSerializer.class)
@Data
public class Response {

	private int status;

	private String message;

	private Object data;
}
