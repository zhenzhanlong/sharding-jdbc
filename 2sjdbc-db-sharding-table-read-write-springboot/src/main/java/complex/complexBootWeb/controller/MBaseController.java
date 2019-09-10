package complex.complexBootWeb.controller;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.propertyeditors.CustomDateEditor;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.validation.ObjectError;
import org.springframework.validation.Validator;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.InitBinder;

import com.sx.cache.Cache;

import complex.complexBootWeb.model.form.BaseForm;
import complex.complexBootWeb.model.vo.login.MConsumerLoginVO;

/**
 * spring 基础控制器
 * 
 * @author lenovo
 *
 */
public class MBaseController {
	/**
	 * 日志
	 */
	private Logger log = LoggerFactory.getLogger(this.getClass());
	/**
	 * token超时时间
	 */
	private static final Integer LOGIN_TOKEN_VOER_TIME = 604800;

	@Resource(name = "redisCache")
	protected Cache cache;

	@Autowired
	protected HttpServletRequest request;

	@Autowired
	protected HttpServletResponse response;

	// 主动校验 前台传入的数据格式
	@Autowired
	protected Validator validator;

	/**
	 * 使用spring 自带的类封装时，日期格式需要特殊处理
	 * 
	 * @param binder
	 */
	@InitBinder
	protected void initBinder(WebDataBinder binder) {
		binder.registerCustomEditor(Date.class, new CustomDateEditor(new SimpleDateFormat("yyyy-MM-dd"), true));
	}

	/**
	 * 获取当前用户信息
	 * 
	 * @param request
	 * @return
	 */
	public MConsumerLoginVO loginConsumer() {
		return new MConsumerLoginVO();
	}

	/*** 字段格式验证 ***/
	public String fieldValidate(BaseForm form, BindingResult errors) {
		// 数据检验(第一种方式)
		validator.validate(form, errors);
		if (errors.hasErrors()) {
			String error = errorsMessage(errors);
			log.error(error);
			return error;
		}
		return null;
	}

	/*** 错误信息提示处理 ***/
	public String errorsMessage(org.springframework.validation.Errors errors) {
		if (errors.hasErrors() || errors.hasGlobalErrors()) {
			StringBuilder errInfo = new StringBuilder();
			List<FieldError> ferrors = errors.getFieldErrors();
			if (!ferrors.isEmpty()) {
				for (FieldError error : ferrors) {
					errInfo.append(error.getField() + ":" + error.getDefaultMessage() + ";");
					log.error(error.getDefaultMessage());
				}
			}
			List<ObjectError> gerrors = errors.getGlobalErrors();
			if (!gerrors.isEmpty()) {
				for (ObjectError error : gerrors) {
					errInfo.append(error.getDefaultMessage() + ";;");
				}
			}
			errors.reject(null, errInfo.toString());
			return errInfo.toString();
		}
		return "";
	}

	public Cache getCache() {
		return cache;
	}

	public void setCache(Cache cache) {
		this.cache = cache;
	}

	public HttpServletRequest getRequest() {
		return request;
	}

	public void setRequest(HttpServletRequest request) {
		this.request = request;
	}

	public HttpServletResponse getResponse() {
		return response;
	}

	public void setResponse(HttpServletResponse response) {
		this.response = response;
	}

	public Validator getValidator() {
		return validator;
	}

	public void setValidator(Validator validator) {
		this.validator = validator;
	}

	public static Integer getLoginTokenVoerTime() {
		return LOGIN_TOKEN_VOER_TIME;
	}
}
