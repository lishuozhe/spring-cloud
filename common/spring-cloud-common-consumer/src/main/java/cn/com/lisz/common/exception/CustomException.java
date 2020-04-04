package cn.com.lisz.common.exception;

import lombok.Getter;

@Getter
public class CustomException extends RuntimeException {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private int status;
	private String message;

	public CustomException(int status, String message) {
		this.status = status;
		this.message = message;
	}

}