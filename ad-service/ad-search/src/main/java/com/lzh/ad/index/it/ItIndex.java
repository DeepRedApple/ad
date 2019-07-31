package com.lzh.ad.index.it;

import com.alibaba.fastjson.JSON;
import com.lzh.ad.index.IndexAware;
import com.lzh.ad.utils.CommonUtils;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.collections4.CollectionUtils;
import org.springframework.stereotype.Component;

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
public class ItIndex implements IndexAware<String, Set<Long>> {

	private static Map<String, Set<Long>> unitMap;
	private static Map<Long, Set<String>> itMap;

	static {
		unitMap = new ConcurrentHashMap<>();
		itMap = new ConcurrentHashMap<>();
	}

	@Override
	public Set<Long> get(String key) {
		return unitMap.get(key);
	}

	@Override
	public void add(String key, Set<Long> value) {
		log.info("It before add:{}", JSON.toJSONString(itMap));
		Set<Long> unitIds = CommonUtils.getOrCreate(key, unitMap, ConcurrentSkipListSet::new);
		unitIds.addAll(value);

		value.forEach(v -> {
			Set<String> its = CommonUtils.getOrCreate(v, itMap, ConcurrentSkipListSet::new);
			its.add(key);
		});
		log.info("It after add:{}", JSON.toJSONString(itMap));
	}

	@Override
	public void update(String key, Set<Long> value) {
		log.error("it index can not support update");
	}

	@Override
	public void delete(String key, Set<Long> value) {
		log.info("It before delete:{}", JSON.toJSONString(itMap));

		Set<Long> unitIds = CommonUtils.getOrCreate(key, unitMap, ConcurrentSkipListSet::new);
		unitIds.removeAll(value);

		value.forEach(v -> {
			Set<String> tags = CommonUtils.getOrCreate(v, itMap, ConcurrentSkipListSet::new);
			tags.remove(key);
		});

		log.info("It after delete:{}", JSON.toJSONString(itMap));
	}

	public boolean match(Long unitId, List<String> tags) {
		if (itMap.containsKey(unitId) && CollectionUtils.isNotEmpty(tags)) {
			Set<String> unitKeyWords = itMap.get(unitId);
			return CollectionUtils.isSubCollection(tags, unitKeyWords);
		}
		return false;
	}
}
