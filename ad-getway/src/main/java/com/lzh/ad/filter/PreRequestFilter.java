package com.lzh.ad.filter;

import com.netflix.zuul.ZuulFilter;
import com.netflix.zuul.context.RequestContext;
import com.netflix.zuul.exception.ZuulException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.cloud.netflix.zuul.filters.support.FilterConstants;
import org.springframework.stereotype.Component;

/**
 * @author Li
 **/
@Slf4j
@Component
public class PreRequestFilter extends ZuulFilter {
	/**
	 *
	 * @return
	 */
	@Override
	public String filterType() {
		return FilterConstants.PRE_TYPE;
	}

	/**
	 * 越小越先被调用
	 * @return 0
	 */
	@Override
	public int filterOrder() {
		return 0;
	}

	/**
	 * 是否需要执行这个过滤器
	 * @return true
	 */
	@Override
	public boolean shouldFilter() {
		return false;
	}

	/**
	 * Filter执行的具体操作
	 * @return
	 * @throws ZuulException
	 */
	@Override
	public Object run() throws ZuulException {
		RequestContext context = RequestContext.getCurrentContext();
		context.set("startTime", System.currentTimeMillis());

		return null;
	}
}
