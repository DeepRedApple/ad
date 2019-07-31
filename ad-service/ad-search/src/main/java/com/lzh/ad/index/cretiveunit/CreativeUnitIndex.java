package com.lzh.ad.index.cretiveunit;

import com.alibaba.fastjson.JSON;
import com.lzh.ad.index.IndexAware;
import com.lzh.ad.index.unit.UnitObject;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.collections4.CollectionUtils;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentSkipListSet;

/**
 * @author Li
 **/
@Slf4j
@Component
public class CreativeUnitIndex implements IndexAware<String, CreativeUnitObject> {

	private static Map<String, CreativeUnitObject> objectMap;
	private static Map<Long, Set<Long>> creativeUnitMap;
	private static Map<Long, Set<Long>> unitCreativeMap;

	static {
		objectMap = new ConcurrentHashMap<>();
		creativeUnitMap = new ConcurrentHashMap<>();
		unitCreativeMap = new ConcurrentHashMap<>();
	}

	@Override
	public CreativeUnitObject get(String key) {
		return objectMap.get(key);
	}

	@Override
	public void add(String key, CreativeUnitObject value) {
		log.info("district before add:{}", JSON.toJSONString(objectMap));

		objectMap.put(key, value);

		Set<Long> unitSet = creativeUnitMap.get(value.getAdId());
		if (CollectionUtils.isEmpty(unitSet)) {
			unitSet = new ConcurrentSkipListSet<>();
			creativeUnitMap.put(value.getAdId(), unitSet);
		}

		unitSet.add(value.getUnitId());

		Set<Long> creativeSet = unitCreativeMap.get(value.getUnitId());
		if (CollectionUtils.isEmpty(creativeSet)) {
			creativeSet = new ConcurrentSkipListSet<>();
			unitCreativeMap.put(value.getUnitId(), creativeSet);
		}
		creativeSet.add(value.getAdId());

		log.info("district after add:{}", JSON.toJSONString(objectMap));
	}

	@Override
	public void update(String key, CreativeUnitObject value) {
		log.error("CreativeUnitIndex can not support update");
	}

	@Override
	public void delete(String key, CreativeUnitObject value) {
		log.info("district after delete:{}", JSON.toJSONString(objectMap));
		objectMap.remove(key);

		Set<Long> unitSet = creativeUnitMap.get(value.getAdId());
		if (CollectionUtils.isNotEmpty(unitSet)) {
			unitSet.remove(value.getUnitId());
		}

		Set<Long> creativeSet = unitCreativeMap.get(value.getUnitId());
		if (CollectionUtils.isNotEmpty(creativeSet)) {
			creativeSet.remove(value.getAdId());
		}
		log.info("district after delete:{}", JSON.toJSONString(objectMap));
	}

	public List<Long> selectAds(List<UnitObject> unitObjects) {
		if (CollectionUtils.isEmpty(unitObjects)) {
			return Collections.emptyList();
		}

		List<Long> result = new ArrayList<>();
		unitObjects.forEach(o -> {
			Set<Long> adIds = unitCreativeMap.get(o.getUnitId());
			if (CollectionUtils.isNotEmpty(adIds)) {
				result.addAll(adIds);
			}
		});
		return result;
	}
}
