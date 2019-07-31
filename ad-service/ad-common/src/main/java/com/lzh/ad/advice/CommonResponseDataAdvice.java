package com.lzh.ad.advice;

import com.lzh.ad.annotation.IgnoreResponseAdvice;
import com.lzh.ad.vo.CommonResponse;
import org.springframework.core.MethodParameter;
import org.springframework.http.MediaType;
import org.springframework.http.converter.HttpMessageConverter;
import org.springframework.http.server.ServerHttpRequest;
import org.springframework.http.server.ServerHttpResponse;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.servlet.mvc.method.annotation.ResponseBodyAdvice;

/**
 * @author Li
 **/
@RestControllerAdvice
public class CommonResponseDataAdvice implements ResponseBodyAdvice<Object> {

	@Override
	@SuppressWarnings("all")
	public boolean supports(MethodParameter methodParameter, Class<? extends HttpMessageConverter<?>> aClass) {
		if (methodParameter.getDeclaringClass().isAnnotationPresent(IgnoreResponseAdvice.class)) {
			return false;
		}

		if (methodParameter.getMethod().isAnnotationPresent(IgnoreResponseAdvice.class)) {
			return false;
		}
		return true;
	}

	@Override
	@SuppressWarnings("all")
	public Object beforeBodyWrite(Object o,
	                              MethodParameter methodParameter,
	                              MediaType mediaType,
	                              Class<? extends HttpMessageConverter<?>> aClass,
	                              ServerHttpRequest serverHttpRequest,
	                              ServerHttpResponse serverHttpResponse) {

		CommonResponse<Object> response = new CommonResponse<>(0, "");
		if (null == o) {
			return response;
		} else if (o instanceof CommonResponse) {
			response = (CommonResponse<Object>) o;
		} else {
			response.setData(o);
		}

		return response;
	}
}
