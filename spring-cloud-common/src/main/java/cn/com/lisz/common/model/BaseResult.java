package cn.com.lisz.common.model;

/**
 * 通用返回对象
 */
public class BaseResult<T> {

	// 操作成功
	public static final int SUCCESS = 200;
	// 操作成功
	public static final int NO_DATA = 204;
	// 参数错误
	public static final int PARAM_ERROR = 400;
	// 未认证
	public static final int UNAUTHORIZED = 401;
	// 未授权
	public static final int FORBIDDEN = 403;
	// 路由出错
	public static final int NOT_FOUND_URL = 404;
	// 处理失败
	public static final int FAILED = 417;
	// 登录名或密码错误
	public static final int USER_NAME_OR_PASSWORD_ERROR = 418;
	// 用户被禁用
	public static final int USER_FORBIDDEN = 419;
	// 已受理
	public static final int ACCEPTED = 420;
	// 操作失败
	public static final int ERROR = 500;

	private int status;

	private String message;

	private T data;

	public BaseResult() {
	}

	public BaseResult(int status) {
		this.status = status;
	}

	public BaseResult(int status, T data) {
		this.status = status;
		this.data = data;
	}

	public BaseResult(int status, String message) {
		this.status = status;
		this.message = message;
	}

	public BaseResult(int status, String message, T data) {
		this.status = status;
		this.message = message;
		this.data = data;
	}

	/**
	 * 普通成功返回
	 *
	 */
	public BaseResult<T> success() {
		this.status = SUCCESS;
		this.message = "操作成功";
		return this;
	}

	/**
	 * 普通成功返回
	 *
	 * @param data
	 *            获取的数据
	 */
	public BaseResult<T> success(T data) {
		this.status = SUCCESS;
		this.message = "操作成功";
		this.data = data;
		return this;
	}

	/**
	 * 无数据提示信息
	 */
	public BaseResult<T> noData() {
		this.status = NO_DATA;
		this.message = "没有数据";
		return this;
	}

	/**
	 * 普通失败提示信息
	 */
	public BaseResult<T> failed() {
		this.status = FAILED;
		this.message = "操作失败";
		return this;
	}

	/**
	 * 普通失败提示信息
	 */
	public BaseResult<T> failed(String message) {
		this.status = FAILED;
		this.message = message;
		return this;
	}

	/**
	 * 参数验证失败使用
	 *
	 * @param message
	 *            错误信息
	 */
	public BaseResult<T> paramError(String message) {
		this.status = PARAM_ERROR;
		this.message = message;
		return this;
	}

	/**
	 * 未登录时使用
	 *
	 * @param message
	 *            错误信息
	 */
	public BaseResult<T> unauthorized() {
		this.status = UNAUTHORIZED;
		this.message = "暂未登录或token已经过期";
		return this;
	}

	/**
	 * 未授权时使用
	 *
	 * @param message
	 *            错误信息
	 */
	public BaseResult<T> forbidden(String message) {
		this.status = FORBIDDEN;
		this.message = "没有相关权限";
		return this;
	}

	public int getStatus() {
		return status;
	}

	public void setStatus(int status) {
		this.status = status;
	}

	public String getMessage() {
		return message;
	}

	public void setMessage(String message) {
		this.message = message;
	}

	public T getData() {
		return data;
	}

	public void setData(T data) {
		this.data = data;
	}
}
