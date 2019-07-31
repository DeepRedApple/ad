package com.lzh.ad.controller;

import com.alibaba.fastjson.JSON;
import com.lzh.ad.service.ICreativeService;
import com.lzh.ad.vo.CreateCreativeRequest;
import com.lzh.ad.vo.CreateCreativeResponse;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author Li
 **/
@Slf4j
@RestController
public class CreativeController {

	@Autowired
	private ICreativeService creativeService;

	@PostMapping("/create/creative")
	public CreateCreativeResponse createCreative(@RequestBody CreateCreativeRequest request) {
		if (log.isDebugEnabled()) {
			log.debug("/create/creative:{}", JSON.toJSONString(request));
		}
		return creativeService.createCreative(request);
	}
}
