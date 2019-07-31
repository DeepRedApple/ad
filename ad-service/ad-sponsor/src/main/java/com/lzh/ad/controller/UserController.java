package com.lzh.ad.controller;

import com.alibaba.fastjson.JSON;
import com.lzh.ad.exception.AdException;
import com.lzh.ad.service.IUserService;
import com.lzh.ad.vo.CreateUserRequest;
import com.lzh.ad.vo.CreateUserResponse;
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
public class UserController {

	@Autowired
	private IUserService userService;

	@PostMapping("/create/user")
	public CreateUserResponse createUser(@RequestBody CreateUserRequest request) throws AdException {
		if (log.isDebugEnabled()) {
			log.debug("/create/user: {}", JSON.toJSONString(request));
		}
		return userService.createUser(request);
	}

}
