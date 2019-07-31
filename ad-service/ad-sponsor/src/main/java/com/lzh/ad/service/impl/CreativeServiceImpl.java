package com.lzh.ad.service.impl;

import com.lzh.ad.dao.CreativeRepository;
import com.lzh.ad.entity.Creative;
import com.lzh.ad.service.ICreativeService;
import com.lzh.ad.vo.CreateCreativeRequest;
import com.lzh.ad.vo.CreateCreativeResponse;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * @author Li
 **/
@Slf4j
@Service
public class CreativeServiceImpl implements ICreativeService {

	@Autowired
	private CreativeRepository creativeRepository;

	@Override
	public CreateCreativeResponse createCreative(CreateCreativeRequest request) {

		Creative creative = creativeRepository.save(request.convertToEntity());

		return new CreateCreativeResponse(creative.getId(), creative.getName());
	}
}
