package com.lzh.ad.index.unit;

import com.alibaba.fastjson.JSON;
import com.lzh.ad.index.IndexAware;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import org.springframework.util.CollectionUtils;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;

/**
 * @author Li
 **/
@Slf4j
@Component
public class UnitIndex implements IndexAware<Long, UnitObject> {

	private static Map<Long, UnitObject> map;

	static {
		map = new ConcurrentHashMap<>();
	}

	public Set<Long> match(Integer positionType) {
		Set<Long> unitIds = new HashSet<>();
		map.forEach((k, v) -> {
			if (UnitObject.isSlotTypeOk(positionType, v.getPositionType())) {
				unitIds.add(k);
			}
		});
		return unitIds;
	}

	public List<UnitObject> fetch(Collection<Long> unitIds) {
		if (CollectionUtils.isEmpty(unitIds)) {
			return Collections.emptyList();
		}

		List<UnitObject> result = new ArrayList<>();
		unitIds.forEach(u -> {
			UnitObject object = get(u);
			if (object == null) {
				log.error("unitObject not found: {}", u);
			}
			result.add(object);
		});
		return result;
	}

	@Override
	public UnitObject get(Long key) {
		return map.get(key);
	}

	@Override
	public void add(Long key, UnitObject value) {
		log.info("before add:{}", JSON.toJSONString(map));
		map.put(key, value);
		log.info("after add:{}", JSON.toJSONString(map));
	}

	@Override
	public void update(Long key, UnitObject value) {
		log.info("before update:{}", JSON.toJSONString(map));
		UnitObject object = map.get(key);
		if (null == object) {
			map.put(key, value);
		} else {
			object.update(value);
		}
		log.info("after update:{}", JSON.toJSONString(map));
	}

	@Override
	public void delete(Long key, UnitObject value) {
		log.info("before delete:{}", JSON.toJSONString(map));
		map.remove(key);
		log.info("after delete:{}", JSON.toJSONString(map));
	}
}
