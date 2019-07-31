package com.lzh.ad.controller;

import com.alibaba.fastjson.JSON;
import com.lzh.ad.exception.AdException;
import com.lzh.ad.service.IUnitService;
import com.lzh.ad.vo.CreativeUnitRequest;
import com.lzh.ad.vo.CreativeUnitResponse;
import com.lzh.ad.vo.UnitDistrictRequest;
import com.lzh.ad.vo.UnitDistrictResponse;
import com.lzh.ad.vo.UnitItRequest;
import com.lzh.ad.vo.UnitItResponse;
import com.lzh.ad.vo.UnitKeyWordRequest;
import com.lzh.ad.vo.UnitKeyWordResponse;
import com.lzh.ad.vo.UnitRequest;
import com.lzh.ad.vo.UnitResponse;
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
public class UnitController {

	@Autowired
	private IUnitService unitService;

	@PostMapping("/create/unit")
	public UnitResponse createUnit(@RequestBody UnitRequest request) throws AdException {
		if (log.isDebugEnabled()) {
			log.debug("/create/unit:{}", JSON.toJSONString(request));
		}
		return unitService.createUnit(request);
	}

	@PostMapping("/create/keyword")
	public UnitKeyWordResponse createKeyWord(@RequestBody UnitKeyWordRequest request) throws AdException {
		if (log.isDebugEnabled()) {
			log.debug("/create/keyword:{}", JSON.toJSONString(request));
		}
		return unitService.createUnitKeyWord(request);
	}

	@PostMapping("/create/it")
	public UnitItResponse createIt(@RequestBody UnitItRequest request) throws AdException {
		if (log.isDebugEnabled()) {
			log.debug("/create/it:{}", JSON.toJSONString(request));
		}
		return unitService.createUnitIt(request);
	}

	@PostMapping("/create/district")
	public UnitDistrictResponse createDistrict(@RequestBody UnitDistrictRequest request) throws AdException {
		if (log.isDebugEnabled()) {
			log.debug("/create/district:{}", JSON.toJSONString(request));
		}
		return unitService.createUnitDistrict(request);
	}

	@PostMapping("/create/creativeUnit")
	public CreativeUnitResponse creativeUnit(@RequestBody CreativeUnitRequest request) throws AdException {
		if (log.isDebugEnabled()) {
			log.debug("/create/creativeUnit:{}", JSON.toJSONString(request));
		}
		return unitService.createCreativeUnit(request);
	}

}
