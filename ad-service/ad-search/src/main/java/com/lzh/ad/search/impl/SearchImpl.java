package com.lzh.ad.search.impl;

import com.alibaba.fastjson.JSON;
import com.lzh.ad.index.CommonStatus;
import com.lzh.ad.index.DataTable;
import com.lzh.ad.index.creative.CreativeIndex;
import com.lzh.ad.index.creative.CreativeObject;
import com.lzh.ad.index.cretiveunit.CreativeUnitIndex;
import com.lzh.ad.index.district.DistrictIndex;
import com.lzh.ad.index.it.ItIndex;
import com.lzh.ad.index.keyword.KeyWordIndex;
import com.lzh.ad.index.unit.UnitIndex;
import com.lzh.ad.index.unit.UnitObject;
import com.lzh.ad.search.ISearch;
import com.lzh.ad.search.vo.SearchRequest;
import com.lzh.ad.search.vo.SearchResponse;
import com.lzh.ad.search.vo.feature.DistrictFeature;
import com.lzh.ad.search.vo.feature.FeatureRelation;
import com.lzh.ad.search.vo.feature.ItFeature;
import com.lzh.ad.search.vo.feature.KeyWordFeature;
import com.lzh.ad.search.vo.media.Slot;
import com.netflix.hystrix.contrib.javanica.annotation.HystrixCommand;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.collections4.CollectionUtils;
import org.springframework.stereotype.Service;

import java.util.Collection;
import java.util.Collections;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Random;
import java.util.Set;

/**
 * @author Li
 **/
@Slf4j
@Service
public class SearchImpl implements ISearch {

	public SearchResponse fallback(SearchRequest request, Throwable t) {
		return null;
	}

	@Override
	@HystrixCommand(fallbackMethod = "fallback")
	public SearchResponse fetchAds(SearchRequest request) {

		List<Slot> slots = request.getRequestInfo().getSlots();

		KeyWordFeature keyWordFeature = request.getFeatureInfo().getKeyWordFeature();
		ItFeature itFeature = request.getFeatureInfo().getItFeature();
		DistrictFeature districtFeature = request.getFeatureInfo().getDistrictFeature();

		FeatureRelation relation = request.getFeatureInfo().getFeatureRelation();

		SearchResponse response = new SearchResponse();
		Map<String, List<SearchResponse.Creative>> adSlot2Ads = response.getAdSlot2Ads();
		slots.forEach(slot -> {
			Set<Long> targetUnitIdSet;

			Set<Long> unitIdSet = DataTable.of(UnitIndex.class).match(slot.getPositionType());

			if (relation == FeatureRelation.AND) {
				filterKeyWordFeature(unitIdSet, keyWordFeature);
				filterDistrictFeature(unitIdSet, districtFeature);
				filterItFeature(unitIdSet, itFeature);

				targetUnitIdSet = unitIdSet;
			} else {
				targetUnitIdSet = getOrRelationUnitIds(unitIdSet, keyWordFeature, districtFeature, itFeature);
			}

			List<UnitObject> unitObjects = DataTable.of(UnitIndex.class).fetch(targetUnitIdSet);
			filterUnitAndPlanStatus(unitObjects, CommonStatus.VALID);

			List<Long> adIds = DataTable.of(CreativeUnitIndex.class).selectAds(unitObjects);

			List<CreativeObject> creativeObjects = DataTable.of(CreativeIndex.class).fetch(adIds);

			filterCreativeBySlot(creativeObjects, slot.getWidth(), slot.getHeight(), slot.getType());

			adSlot2Ads.put(slot.getCode(), buildCreativeResponse(creativeObjects));
		});

		log.info("fetchAds:{}-{}", JSON.toJSONString(request), JSON.toJSONString(response));
		return response;
	}

	private Set<Long> getOrRelationUnitIds(Set<Long> unitIds, KeyWordFeature keyWordFeature,
	                                       DistrictFeature districtFeature, ItFeature itFeature) {
		if (CollectionUtils.isEmpty(unitIds)) {
			return Collections.emptySet();
		}

		Set<Long> keyWordUnitIdSet = new HashSet<>(unitIds);
		Set<Long> itUnitIdSet = new HashSet<>(unitIds);
		Set<Long> districtUnitIdSet = new HashSet<>(unitIds);

		filterKeyWordFeature(keyWordUnitIdSet, keyWordFeature);
		filterDistrictFeature(districtUnitIdSet, districtFeature);
		filterItFeature(itUnitIdSet, itFeature);

		return new HashSet<>(CollectionUtils.union(CollectionUtils.union(keyWordUnitIdSet, districtUnitIdSet), itUnitIdSet));
	}

	private void filterKeyWordFeature(Collection<Long> unitIds, KeyWordFeature feature) {
		if (CollectionUtils.isEmpty(unitIds)) {
			return;
		}

		if (CollectionUtils.isNotEmpty(feature.getKeywords())) {
			CollectionUtils.filter(unitIds, unitId -> DataTable.of(KeyWordIndex.class).match(unitId, feature.getKeywords()));
		}
	}

	private void filterItFeature(Collection<Long> unitIds, ItFeature feature) {
		if (CollectionUtils.isEmpty(unitIds)) {
			return;
		}

		if (CollectionUtils.isNotEmpty(feature.getIts())) {
			CollectionUtils.filter(unitIds, unitId -> DataTable.of(ItIndex.class).match(unitId, feature.getIts()));
		}
	}

	private void filterDistrictFeature(Collection<Long> unitIds, DistrictFeature feature)  {
		if (CollectionUtils.isEmpty(unitIds)) {
			return;
		}

		if (CollectionUtils.isNotEmpty(feature.getProvinceAndCities())) {
			CollectionUtils.filter(unitIds, unitId -> DataTable.of(DistrictIndex.class).match(unitId,
				feature.getProvinceAndCities()));
		}
	}

	private void filterUnitAndPlanStatus(List<UnitObject> unitObjects, CommonStatus status) {
		if (CollectionUtils.isEmpty(unitObjects)) {
			return;
		}

		CollectionUtils.filter(unitObjects, unitObject -> unitObject.getStatus().equals(status.getStatus())
			&& unitObject.getPlanObject().getStatus().equals(status.getStatus())
		);
	}

	private void filterCreativeBySlot(List<CreativeObject> creativeObjects, Integer width, Integer height,
	                                  List<Integer> type) {
		if (CollectionUtils.isEmpty(creativeObjects)) {
			return;
		}

		CollectionUtils.filter(
			creativeObjects,
			creativeObject -> creativeObject.getAuditStatus().equals(CommonStatus.VALID.getStatus())
				&& creativeObject.getWidth().equals(width)
				&& creativeObject.getHeight().equals(height)
				&& type.contains(creativeObject.getType())
		);
	}

	private List<SearchResponse.Creative> buildCreativeResponse(List<CreativeObject> creativeObjects) {
		if (CollectionUtils.isEmpty(creativeObjects)) {
			return Collections.emptyList();
		}

		CreativeObject randomObject = creativeObjects.get(Math.abs(new Random().nextInt()) % creativeObjects.size());
		return Collections.singletonList(SearchResponse.convert(randomObject));
	}
}
