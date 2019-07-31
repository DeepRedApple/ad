package com.lzh.ad.service;

import com.lzh.ad.exception.AdException;
import com.lzh.ad.vo.CreateUserRequest;
import com.lzh.ad.vo.CreateUserResponse;

/**
 * @author Li
 **/
public interface IUserService {
	CreateUserResponse createUser(CreateUserRequest request) throws AdException;
}
