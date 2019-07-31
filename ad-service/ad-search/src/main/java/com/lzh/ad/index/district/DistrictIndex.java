package com.lzh.ad.index.district;

import com.alibaba.fastjson.JSON;
import com.lzh.ad.index.IndexAware;
import com.lzh.ad.search.vo.feature.DistrictFeature;
import com.lzh.ad.utils.CommonUtils;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.collections4.CollectionUtils;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentSkipListSet;
import java.util.stream.Collectors;

/**
 * @author Li
 **/
@Slf4j
@Component
public class DistrictIndex implements IndexAware<String, Set<Long>> {

	private static Map<String, Set<Long>> unitMap;
	private static Map<Long, Set<String>> districtMap;

	static {
		unitMap = new ConcurrentHashMap<>();
		districtMap = new ConcurrentHashMap<>();
	}

	@Override
	public Set<Long> get(String key) {
		return unitMap.get(key);
	}

	@Override
	public void add(String key, Set<Long> value) {
		log.info("district before add:{}", JSON.toJSONString(districtMap));
		Set<Long> unitIds = CommonUtils.getOrCreate(key, unitMap, ConcurrentSkipListSet::new);
		unitIds.addAll(value);

		value.forEach(v -> {
			Set<String> districts = CommonUtils.getOrCreate(v, districtMap, ConcurrentSkipListSet::new);
			districts.add(key);
		});
		log.info("district after add:{}", JSON.toJSONString(districtMap));
	}

	@Override
	public void update(String key, Set<Long> value) {
		log.error("district index can not support update");
	}

	@Override
	public void delete(String key, Set<Long> value) {
		log.info("district before delete:{}", JSON.toJSONString(districtMap));

		Set<Long> unitIds = CommonUtils.getOrCreate(key, unitMap, ConcurrentSkipListSet::new);
		unitIds.addAll(value);

		value.forEach(v -> {
			Set<String> districts = CommonUtils.getOrCreate(v, districtMap, ConcurrentSkipListSet::new);
			districts.remove(key);
		});
		log.info("district after delete:{}", JSON.toJSONString(districtMap));
	}

	public boolean match(Long unitId, List<DistrictFeature.ProvinceAndCity> district) {
		if (districtMap.containsKey(unitId) && CollectionUtils.isNotEmpty(districtMap.get(unitId))) {
			Set<String> unitDistrict = districtMap.get(unitId);

			List<String> targetDistrict = district.stream()
				.map(d -> CommonUtils.stringConcat(d.getProvince(), d.getCity())).collect(Collectors.toList());

			return CollectionUtils.isSubCollection(targetDistrict, unitDistrict);
		}
		return false;
	}
}
