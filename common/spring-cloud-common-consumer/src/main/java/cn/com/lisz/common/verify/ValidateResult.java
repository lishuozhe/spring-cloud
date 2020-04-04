package cn.com.lisz.common.verify;

import java.util.Map;

/**
 * 校验结果
 *
 */
public class ValidateResult {
	/**
	 * 是否校验通过
	 */
	private boolean validated;
	/**
	 * 校验结果信息
	 */
	private Map<String, String> messages;

	public ValidateResult(boolean validated) {
		this.validated = validated;
	}

	public ValidateResult(boolean validated, Map<String, String> messages) {
		this.validated = validated;
		this.messages = messages;
	}

	public boolean isValidated() {
		return validated;
	}

	public void setValidated(boolean validated) {
		this.validated = validated;
	}

	public Map<String, String> getMessages() {
		return messages;
	}

	public void setMessages(Map<String, String> messages) {
		this.messages = messages;
	}
}
