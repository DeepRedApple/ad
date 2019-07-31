package com.lzh.ad.index.plan;

import com.alibaba.fastjson.JSON;
import com.lzh.ad.index.IndexAware;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

/**
 * @author Li
 **/
@Slf4j
@Component
public class PlanIndex implements IndexAware<Long, PlanObject> {

	private static Map<Long, PlanObject> objectMap;

	static {
		objectMap = new ConcurrentHashMap<>();
	}

	@Override
	public PlanObject get(Long key) {
		return objectMap.get(key);
	}

	@Override
	public void add(Long key, PlanObject value) {
		log.info("before add:{}", JSON.toJSONString(objectMap));
		objectMap.put(key, value);
		log.info("after add:{}", JSON.toJSONString(objectMap));
	}

	@Override
	public void update(Long key, PlanObject value) {
		log.info("before update:{}", JSON.toJSONString(objectMap));
		PlanObject object = objectMap.get(key);
		if (null == object) {
			objectMap.put(key, value);
		} else {
			object.update(value);
		}
		log.info("after update:{}", JSON.toJSONString(objectMap));
	}

	@Override
	public void delete(Long key, PlanObject value) {
		log.info("before delete:{}", JSON.toJSONString(objectMap));
		objectMap.remove(key);
		log.info("after delete:{}", JSON.toJSONString(objectMap));
	}
}
