package com.lzh.ad.service.impl;

import com.lzh.ad.constant.Constants;
import com.lzh.ad.dao.CreativeRepository;
import com.lzh.ad.dao.CreativeUnitRepository;
import com.lzh.ad.dao.PlanRepository;
import com.lzh.ad.dao.UnitRepository;
import com.lzh.ad.dao.condition.DistrictRepository;
import com.lzh.ad.dao.condition.ItRepository;
import com.lzh.ad.dao.condition.KeyWordRepository;
import com.lzh.ad.entity.CreativeUnit;
import com.lzh.ad.entity.Plan;
import com.lzh.ad.entity.Unit;
import com.lzh.ad.entity.condition.District;
import com.lzh.ad.entity.condition.It;
import com.lzh.ad.entity.condition.KeyWord;
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
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashSet;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import static java.util.stream.Collectors.toList;

/**
 * @author Li
 **/
@Slf4j
@Service
public class UnitServiceImpl implements IUnitService {

	@Autowired
	private UnitRepository unitRepository;

	@Autowired
	private PlanRepository planRepository;

	@Autowired
	private KeyWordRepository keyWordRepository;

	@Autowired
	private ItRepository itRepository;

	@Autowired
	private DistrictRepository districtRepository;

	@Autowired
	private CreativeRepository creativeRepository;

	@Autowired
	private CreativeUnitRepository creativeUnitRepository;

	@Override
	public UnitResponse createUnit(UnitRequest request) throws AdException {
		if (!request.createValidate()) {
			throw new AdException(Constants.ErrorMsg.REQUEST_PARAM_MESSAGE);
		}

		Optional<Plan> plan = planRepository.findById(request.getPlanId());
		if (!plan.isPresent()) {
			throw new AdException(Constants.ErrorMsg.CAN_NOT_FIND_RECORD);
		}
		Unit oldUnit = unitRepository.findByPlanIdAndName(request.getPlanId(), request.getName());
		if (oldUnit != null) {
			throw new AdException(Constants.ErrorMsg.SAME_NAME_UNIT_ERROR);
		}
		Unit unit = unitRepository.save(new Unit(request.getPlanId(), request.getName(),
			request.getPositionType(), request.getBudget()));

		return new UnitResponse(unit.getId(), unit.getName());
	}

	@Override
	public UnitKeyWordResponse createUnitKeyWord(UnitKeyWordRequest request) throws AdException {
		List<Long> unitIds = request.getUnitKeyWords()
			.stream().map(UnitKeyWordRequest.UnitKeyWord::getUnitId).collect(toList());
		if (!isRelatedUnitExits(unitIds)) {
			throw new AdException(Constants.ErrorMsg.REQUEST_PARAM_MESSAGE);
		}

		List<Long> ids = Collections.emptyList();
		List<KeyWord> keyWords = new ArrayList<>();
		if (!CollectionUtils.isEmpty(request.getUnitKeyWords())) {
			request.getUnitKeyWords().forEach(
				keyWord -> keyWords.add(new KeyWord(keyWord.getUnitId(), keyWord.getKeyword()))
			);
			ids = keyWordRepository.saveAll(keyWords).stream().map(KeyWord::getId).collect(toList());
		}
		return new UnitKeyWordResponse(ids);
	}

	@Override
	public UnitItResponse createUnitIt(UnitItRequest request) throws AdException {
		List<Long> unitIds = request.getIts().stream().map(UnitItRequest.UnitIt::getUnitId).collect(toList());
		if (!isRelatedUnitExits(unitIds)) {
			throw new AdException(Constants.ErrorMsg.REQUEST_PARAM_MESSAGE);
		}

		List<Long> ids = Collections.emptyList();
		List<It> its = new ArrayList<>();
		if (!CollectionUtils.isEmpty(request.getIts())) {
			request.getIts().forEach(unitIt -> its.add(new It(unitIt.getUnitId(), unitIt.getTag())));
			ids = itRepository.saveAll(its).stream().map(It::getId).collect(toList());
		}
		return new UnitItResponse(ids);
	}

	@Override
	public UnitDistrictResponse createUnitDistrict(UnitDistrictRequest request) throws AdException {
		List<Long> unitIds = request.getDistricts().stream().map(UnitDistrictRequest.District::getUnitId).collect(toList());
		if (!isRelatedUnitExits(unitIds)) {
			throw new AdException(Constants.ErrorMsg.REQUEST_PARAM_MESSAGE);
		}

		List<Long> ids = Collections.emptyList();
		List<District> districts = new ArrayList<>();
		if (!CollectionUtils.isEmpty(request.getDistricts())) {
			request.getDistricts().forEach(district -> districts.add(new District(district.getUnitId(),
				district.getProvince(), district.getCity())));
			ids = districtRepository.saveAll(districts).stream().map(District::getId).collect(toList());
		}

		return new UnitDistrictResponse(ids);
	}


	@Override
	public CreativeUnitResponse createCreativeUnit(CreativeUnitRequest request) throws AdException {
		List<Long> unitIds = request.getUnitItems()
			.stream().map(CreativeUnitRequest.CreativeUnitItem::getUnitId).collect(Collectors.toList());
		List<Long> creativeIds = request.getUnitItems()
			.stream().map(CreativeUnitRequest.CreativeUnitItem::getCreativeId).collect(toList());

		if (!(isRelatedUnitExits(unitIds) && isRelatedCreativeExits(creativeIds))) {
			throw new AdException(Constants.ErrorMsg.REQUEST_PARAM_MESSAGE);
		}

		List<CreativeUnit> units = new ArrayList<>();
		request.getUnitItems().forEach(creativeUnitItem -> units.add(new CreativeUnit(creativeUnitItem.getCreativeId(),
			creativeUnitItem.getUnitId())));
		List<Long> ids = creativeUnitRepository.saveAll(units).stream().map(CreativeUnit::getId).collect(toList());

		return new CreativeUnitResponse(ids);
	}

	private boolean isRelatedUnitExits(List<Long> unitIds) {
		if (CollectionUtils.isEmpty(unitIds)) {
			return false;
		}
		return unitRepository.findAllById(unitIds).size() ==
			new HashSet<>(unitIds).size();
	}

	private boolean isRelatedCreativeExits(List<Long> creativeIds) {
		if (CollectionUtils.isEmpty(creativeIds)) {
			return false;
		}
		return creativeRepository.findAllById(creativeIds).size() == new HashSet<>(creativeIds).size();
	}
}
