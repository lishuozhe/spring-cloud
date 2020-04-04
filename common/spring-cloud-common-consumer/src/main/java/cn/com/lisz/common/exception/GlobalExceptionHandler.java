package cn.com.lisz.common.exception;

import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;

import cn.com.lisz.common.model.web.ResultModel;

@ControllerAdvice
public class GlobalExceptionHandler {

	@ExceptionHandler(value = CustomException.class)
	@ResponseBody
	public ResultModel<Object> baseErrorHandler(CustomException e) throws Exception {
		ResultModel<Object> r = new ResultModel<>();
		r.setMessage(e.getMessage());
		r.setStatus(e.getStatus());
		return r;
	}

	@ExceptionHandler(value = Exception.class)
	@ResponseBody
	public ResultModel<Object> exceptionErrorHandler(Exception e) throws Exception {
		ResultModel<Object> r = new ResultModel<>();
		return r.failed(e.getMessage());
	}
}
