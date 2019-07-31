package com.lzh.ad.service.impl;

import com.lzh.ad.constant.Constants;
import com.lzh.ad.dao.UserRepository;
import com.lzh.ad.entity.User;
import com.lzh.ad.exception.AdException;
import com.lzh.ad.service.IUserService;
import com.lzh.ad.utils.CommonUtils;
import com.lzh.ad.vo.CreateUserRequest;
import com.lzh.ad.vo.CreateUserResponse;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;


/**
 * @author Li
 **/
@Slf4j
@Service
public class UserServiceImpl implements IUserService {

	@Autowired
	private UserRepository userRepository;

	@Override
	@Transactional
	public CreateUserResponse createUser(CreateUserRequest request) throws AdException {
		if (!request.validate()) {
			throw new AdException(Constants.ErrorMsg.REQUEST_PARAM_MESSAGE);
		}
		User oldUser = userRepository.findByName(request.getName());
		if (oldUser != null) {
			throw new AdException(Constants.ErrorMsg.SAME_NAME_ERROR);
		}

		User newUser = userRepository.save(new User(request.getName(), CommonUtils.md5(request.getName())));

		return new CreateUserResponse(newUser.getId(), newUser.getName(), newUser.getToken(), newUser.getCreateTime()
			, newUser.getUpdateTime());
	}
}
