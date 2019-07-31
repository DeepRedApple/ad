package com.lzh.ad.index.keyword;

import com.alibaba.fastjson.JSON;
import com.lzh.ad.index.IndexAware;
import com.lzh.ad.utils.CommonUtils;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.collections4.CollectionUtils;
import org.apache.commons.lang.StringUtils;
import org.springframework.stereotype.Component;

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
public class KeyWordIndex implements IndexAware<String, Set<Long>> {

	private static Map<String, Set<Long>> keyWordMap;

	private static Map<Long, Set<String>> unitMap;

	static {
		keyWordMap = new ConcurrentHashMap<>();
		unitMap = new ConcurrentHashMap<>();
	}

	@Override
	public Set<Long> get(String key) {
		if (StringUtils.isEmpty(key)) {
			return Collections.EMPTY_SET;
		}
		Set<Long> result = keyWordMap.get(key);
		if (result == null) {
			return Collections.EMPTY_SET;
		}
		return result;
	}

	@Override
	public void add(String key, Set<Long> value) {
		log.info("keyword before add:{}", JSON.toJSONString(keyWordMap));

		Set<Long> unitIdSet = CommonUtils.getOrCreate(key, keyWordMap, ConcurrentSkipListSet::new);
		unitIdSet.addAll(value);

		value.forEach(v -> {
			Set<String> keyWordSet = CommonUtils.getOrCreate(v, unitMap, ConcurrentSkipListSet::new);
			keyWordSet.add(key);
		});

		log.info("keyword after add:{}", JSON.toJSONString(keyWordMap));
	}

	@Override
	public void update(String key, Set<Long> value) {
		log.error("keyword index can not support update");
	}

	@Override
	public void delete(String key, Set<Long> value) {
		log.info("keyWord before delete:{}", JSON.toJSONString(keyWordMap));
		Set<Long> unitIds = CommonUtils.getOrCreate(key, keyWordMap, ConcurrentSkipListSet::new);
		unitIds.removeAll(value);

		value.forEach(v -> {
			Set<String> keyWordSet = CommonUtils.getOrCreate(v, unitMap, ConcurrentSkipListSet::new);
			keyWordSet.remove(key);
		});
		log.info("keyWord after delete:{}", JSON.toJSONString(keyWordMap));
	}

	public boolean match(Long unitId, List<String> keywords) {
		if (unitMap.containsKey(unitId) && CollectionUtils.isNotEmpty(unitMap.get(unitId))) {
			Set<String> unitKeyWord = unitMap.get(unitId);

			return CollectionUtils.isSubCollection(keywords, unitKeyWord);
		}
		return false;
	}
}
