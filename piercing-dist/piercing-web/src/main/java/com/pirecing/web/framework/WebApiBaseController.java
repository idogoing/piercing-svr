package com.pirecing.web.framework;

import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindException;
import org.springframework.validation.FieldError;
import org.springframework.validation.ObjectError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;

import com.flash.exception.ServiceException;
import com.flash.exception.constant.ExceptionConstant;
import com.flash.web.base.response.BaseResponse;

@Controller
public class WebApiBaseController {
	private final static Logger LOGGER = LoggerFactory.getLogger(WebApiBaseController.class);
	
	/**
	 * 普通业务异常的处理器
	 * @author lonaking
	 * @param request
	 * @param e
	 * @return
	 */
	@ExceptionHandler(ServiceException.class)
	public @ResponseBody BaseResponse<?> handleServiceException(ServiceException e){
		String name = e.getName() ;
		String message = (e.getErrorMessage() == null || "".equals(e.getErrorMessage().trim())) ? "未知业务异常" : e.getErrorMessage();
		Integer code = e.getErrorCode() == 0 ? 400 : e.getErrorCode();
		LOGGER.error("运行中出现业务异常,原因:{},异常码{},异常名称{}",message,code,name);
		return BaseResponse.faild(code, message, name);
	}
	/**
	 * Assert 调用出现异常的异常处理器
	 * @param e
	 * @return
	 */
	@ExceptionHandler(IllegalArgumentException.class)
	public @ResponseBody BaseResponse<?> handleIllegalArgumentException(IllegalArgumentException e){
		LOGGER.error("运行中出现异常,原因:{},可能是因为方法参数中某个字段为空导致",e.getMessage());
		return BaseResponse.faild(ExceptionConstant.CODE_FAILD, e.getMessage(), ExceptionConstant.ASSERT_ILLEGAL_ARGUMENT_EXCEPTION);
	}
	
	/**
	 * validate 的异常处理器
	 * @param e
	 * @return
	 */
	@ExceptionHandler(MethodArgumentNotValidException.class)
	public @ResponseBody BaseResponse<?> handleMethodArgumentNotValidException(MethodArgumentNotValidException e){
		LOGGER.error("运行中出现异常:原因:{},可能是因为前端提交参数后端校验不通过导致，请查看command中的validate",e.getMessage());
		return BaseResponse.faild(ExceptionConstant.CODE_FAILD, e.getMessage(), ExceptionConstant.METHOD_ARGUMENT_NOT_VALID_EXCEPTION);
	}
	
	
	public static class ValidationErrorSimple {
		private String field;
		private String message;

		public String getField() {
			return field;
		}

		public void setField(String field) {
			this.field = field;
		}

		public String getMessage() {
			return message;
		}

		public void setMessage(String message) {
			this.message = message;
		}

		public ValidationErrorSimple(String objectName, String field, String code, String message) {
			super();
			this.field = field;
			this.message = message;
		}

		public ValidationErrorSimple(ObjectError e) {
			this.message = e.getDefaultMessage();
			if (e instanceof FieldError) {
				this.field = ((FieldError) e).getField();
			}
		}
	}

	@ExceptionHandler(BindException.class)
	public @ResponseBody BaseResponse<List<ValidationErrorSimple>> handleBindException(HttpServletRequest req,
			BindException exception) {
		LOGGER.error("Request:{},error_message: {} ", req.getRequestURL(), exception.getMessage());

		List<ValidationErrorSimple> errors = new ArrayList<ValidationErrorSimple>();
		String message = exception.getAllErrors().get(exception.getErrorCount()-1).getDefaultMessage();
		for (ObjectError e : exception.getAllErrors()) {
			errors.add(new ValidationErrorSimple(e));
		}
		return new BaseResponse<List<ValidationErrorSimple>>(ExceptionConstant.CODE_FAILD,message, errors);
	}
	
	/**
	 * validate 的异常处理
	 * @param e
	 * @return
	
	@ExceptionHandler(BindException.class)
	public @ResponseBody BaseResponse<?> handleValidationException(ValidationException e){
		LOGGER.error("运行中出现异常:原因:{},可能是因为前端提交参数后端校验不通过导致，请查看command中的validate",e.getMessage());
		return BaseResponse.faild(ExceptionConstant.CODE_FAILD, e.getMessage(), ExceptionConstant.METHOD_ARGUMENT_NOT_VALID_EXCEPTION);
	} */
}
