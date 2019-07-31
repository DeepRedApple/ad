package com.lzh.ad.advice;

import com.lzh.ad.exception.AdException;
import com.lzh.ad.vo.CommonResponse;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import javax.servlet.http.HttpServletRequest;

/**
 * @author Li
 **/
@RestControllerAdvice
public class GlobalExceptionAdvice {

	@ExceptionHandler(value = AdException.class)
	public CommonResponse<String> handlerAdException(HttpServletRequest request, AdException e) {
		CommonResponse<String> response = new CommonResponse<>(-1, "business error");
		response.setData(e.getMessage());
		return response;
	}
}
