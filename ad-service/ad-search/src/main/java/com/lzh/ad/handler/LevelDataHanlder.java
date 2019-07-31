package com.lzh.ad.handler;

import com.lzh.ad.dump.table.CreativeTable;
import com.lzh.ad.dump.table.CreativeUnitTable;
import com.lzh.ad.dump.table.DistrictTable;
import com.lzh.ad.dump.table.ItTable;
import com.lzh.ad.dump.table.KeyWordTable;
import com.lzh.ad.dump.table.PlanTable;
import com.lzh.ad.dump.table.UnitTable;
import com.lzh.ad.index.DataTable;
import com.lzh.ad.index.IndexAware;
import com.lzh.ad.index.creative.CreativeIndex;
import com.lzh.ad.index.creative.CreativeObject;
import com.lzh.ad.index.cretiveunit.CreativeUnitIndex;
import com.lzh.ad.index.cretiveunit.CreativeUnitObject;
import com.lzh.ad.index.district.DistrictIndex;
import com.lzh.ad.index.it.ItIndex;
import com.lzh.ad.index.keyword.KeyWordIndex;
import com.lzh.ad.index.plan.PlanIndex;
import com.lzh.ad.index.plan.PlanObject;
import com.lzh.ad.index.unit.UnitIndex;
import com.lzh.ad.index.unit.UnitObject;
import com.lzh.ad.mysql.constant.OpType;
import com.lzh.ad.utils.CommonUtils;
import lombok.extern.slf4j.Slf4j;

import java.util.Collections;
import java.util.HashSet;
import java.util.Set;

/**
 * @author Li
 **/
@Slf4j
public class LevelDataHanlder {

	public static void handleLevelPlan(PlanTable planTable, OpType type) {
		PlanObject planObject = new PlanObject(planTable.getId(), planTable.getUserId(), planTable.getStatus(),
			planTable.getStartDate(), planTable.getEndDate());
		handleBinlogEvent(DataTable.of(PlanIndex.class), planObject.getPlanId(), planObject, type);

	}

	public static void handleLevelCreative(CreativeTable creativeTable, OpType type) {
		CreativeObject creativeObject = new CreativeObject(creativeTable.getAdId(), creativeTable.getName(),
			creativeTable.getType(), creativeTable.getMaterialType(), creativeTable.getHeight(),
			creativeTable.getWidth(), creativeTable.getAuditStatus(), creativeTable.getUrl());
		handleBinlogEvent(DataTable.of(CreativeIndex.class), creativeObject.getAdId(), creativeObject, type);
	}

	public static void handleLevelUnit(UnitTable unitTable, OpType type) {
		PlanObject planObject = DataTable.of(PlanIndex.class).get(unitTable.getPlanId());
		if (null == planObject) {
			log.error("handleLevelUnit found planObject error :{}", unitTable.getPlanId());
			return;
		}

		UnitObject unitObject = new UnitObject(unitTable.getUnitId(), unitTable.getStatus(),
			unitTable.getPositionType(), unitTable.getPlanId(), planObject);
		handleBinlogEvent(DataTable.of(UnitIndex.class), unitObject.getUnitId(), unitObject, type);
	}

	public static void handleLevelCreativeUnit(CreativeUnitTable creativeUnitTable, OpType type) {
		if (type == OpType.UPDATE) {
			log.error("CreativeUnitIndex not support update");
			return;
		}
		UnitObject unitObject = DataTable.of(UnitIndex.class).get(creativeUnitTable.getUnitId());
		CreativeObject creativeObject = DataTable.of(CreativeIndex.class).get(creativeUnitTable.getAdId());
		if (null == unitObject || null == creativeObject) {
			log.error("handleLevelUnit found planObject error :{}, {}", creativeUnitTable.getAdId(),
				creativeUnitTable.getUnitId());
			return;
		}

		CreativeUnitObject creativeUnitObject = new CreativeUnitObject(creativeObject.getAdId(),
			creativeUnitTable.getUnitId());
		handleBinlogEvent(DataTable.of(CreativeUnitIndex.class),
			CommonUtils.stringConcat(creativeUnitObject.getAdId().toString(), creativeUnitObject.getUnitId().toString()),
			creativeUnitObject, type);
	}

	public static void handleLevelDistrict(DistrictTable districtTable, OpType type) {
		if (type == OpType.UPDATE) {
			log.error("district not support update");
			return;
		}
		UnitObject unitObject = DataTable.of(UnitIndex.class).get(districtTable.getUnitId());
		if (null == unitObject) {
			log.error("handleLevelDistrict found unitObject error :{}", districtTable.getUnitId());
			return;
		}
		String key = CommonUtils.stringConcat(districtTable.getProvince(), districtTable.getCity());
		Set<Long> value = new HashSet<>(Collections.singleton(districtTable.getUnitId()));
		handleBinlogEvent(DataTable.of(DistrictIndex.class), key, value, type);
	}

	public static void handleLevelIt(ItTable itTable, OpType type) {
		if (type == OpType.UPDATE) {
			log.error("it not support update");
			return;
		}
		UnitObject unitObject = DataTable.of(UnitIndex.class).get(itTable.getUnitId());
		if (null == unitObject) {
			log.error("handleLevelIt found unitObject error :{}", itTable.getUnitId());
			return;
		}
		Set<Long> value = new HashSet<>(Collections.singleton(itTable.getUnitId()));
		handleBinlogEvent(DataTable.of(ItIndex.class), itTable.getTag(), value, type);
	}

	public static void handleLevelKeyWord(KeyWordTable keywordTable, OpType type) {
		if (type == OpType.UPDATE) {
			log.error("keyword not support update");
			return;
		}
		UnitObject unitObject = DataTable.of(UnitIndex.class).get(keywordTable.getUnitId());
		if (null == unitObject) {
			log.error("handleLevelKeyWord found unitObject error :{}", keywordTable.getUnitId());
			return;
		}
		Set<Long> value = new HashSet<>(Collections.singleton(keywordTable.getUnitId()));
		handleBinlogEvent(DataTable.of(KeyWordIndex.class), keywordTable.getKeyword(), value, type);
	}

	private static <K, V> void handleBinlogEvent(IndexAware<K, V> index, K key, V value, OpType type) {
		switch (type) {
			case ADD:
				index.add(key, value);
				break;
			case UPDATE:
				index.update(key, value);
				break;
			case DELETE:
				index.delete(key, value);
				break;
			default:
				break;
		}
	}
}
