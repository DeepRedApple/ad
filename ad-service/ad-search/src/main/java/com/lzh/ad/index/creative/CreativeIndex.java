package com.lzh.ad.index.creative;

import com.alibaba.fastjson.JSON;
import com.lzh.ad.index.IndexAware;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.collections4.CollectionUtils;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

/**
 * @author Li
 **/
@Slf4j
@Component
public class CreativeIndex implements IndexAware<Long, CreativeObject> {

	private static Map<Long, CreativeObject> map;

	static {
		map = new ConcurrentHashMap<>();
	}

	@Override
	public CreativeObject get(Long key) {
		return map.get(key);
	}

	@Override
	public void add(Long key, CreativeObject value) {
		log.info("creative before add:{}", JSON.toJSONString(map));
		map.put(key, value);
		log.info("creative after add:{}", JSON.toJSONString(map));
	}

	@Override
	public void update(Long key, CreativeObject value) {
		CreativeObject creativeObject = map.get(key);
		if (null == creativeObject) {
			map.put(key, value);
		} else {
			creativeObject.update(value);
		}
	}

	@Override
	public void delete(Long key, CreativeObject value) {
		map.remove(key);
	}

	public List<CreativeObject> fetch(Collection<Long> adIds) {
		if (CollectionUtils.isEmpty(adIds)) {
			return Collections.emptyList();
		}

		List<CreativeObject> result = new ArrayList<>();
		adIds.forEach(u -> {
			CreativeObject object = get(u);
			if (null == object) {
				log.error("CreativeObject not found: {}", u);
				return;
			}
			result.add(object);
		});
		return result;
	}
}
