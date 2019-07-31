package com.lzh.ad.service;

import com.lzh.ad.exception.AdException;
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

/**
 * @author Li
 **/
public interface IUnitService {

	UnitResponse createUnit(UnitRequest request) throws AdException;

	UnitKeyWordResponse createUnitKeyWord(UnitKeyWordRequest request) throws AdException;

	UnitItResponse createUnitIt(UnitItRequest request) throws AdException;

	UnitDistrictResponse createUnitDistrict(UnitDistrictRequest request) throws AdException;

	CreativeUnitResponse createCreativeUnit(CreativeUnitRequest request) throws AdException;
}
