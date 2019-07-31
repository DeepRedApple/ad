package com.lzh.ad.sender.index;

import com.alibaba.fastjson.JSON;
import com.lzh.ad.dump.table.CreativeTable;
import com.lzh.ad.dump.table.CreativeUnitTable;
import com.lzh.ad.dump.table.DistrictTable;
import com.lzh.ad.dump.table.ItTable;
import com.lzh.ad.dump.table.KeyWordTable;
import com.lzh.ad.dump.table.PlanTable;
import com.lzh.ad.dump.table.UnitTable;
import com.lzh.ad.handler.LevelDataHanlder;
import com.lzh.ad.index.DataLevel;
import com.lzh.ad.mysql.constant.Constant;
import com.lzh.ad.mysql.dto.MySqlRowData;
import com.lzh.ad.sender.ISender;
import com.lzh.ad.utils.CommonUtils;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

/**
 * @author Li
 **/
@Slf4j
@Component("indexSender")
public class IndexSender implements ISender {


	@Override
	public void sender(MySqlRowData rowData) {

		String level = rowData.getLevel();
		if (DataLevel.LEVEL2.getLevel().equals(level)) {
			Level2RowData(rowData);
		} else if (DataLevel.LEVEL3.getLevel().equals(level)) {
			Level3RowData(rowData);
		} else if (DataLevel.LEVEL4.getLevel().equals(level)) {
			Level4RowData(rowData);
		} else {
			log.error("MysqlRowData ERROR:{}", JSON.toJSONString(rowData));
		}
	}

	private void Level2RowData(MySqlRowData rowData) {
		switch (rowData.getTableName()) {
			case Constant.PLAN_INFO.TABLE_NAME:
				List<PlanTable> planTables = new ArrayList<>();

				rowData.getFieldValueMap().forEach(map -> {
					PlanTable planTable = new PlanTable();
					map.forEach((k, v) -> {
						switch (k) {
							case Constant.PLAN_INFO.COLUMN_ID:
								planTable.setId(Long.valueOf(v));
								break;
							case Constant.PLAN_INFO.COLUMN_USER_ID:
								planTable.setUserId(Long.valueOf(v));
								break;
							case Constant.PLAN_INFO.COLUMN_STATUS:
								planTable.setStatus(Integer.valueOf(v));
								break;
							case Constant.PLAN_INFO.COLUMN_START_DATE:
								planTable.setStartDate(CommonUtils.parseStringDate(v));
								break;
							case Constant.PLAN_INFO.COLUMN_END_DATE:
								planTable.setEndDate(CommonUtils.parseStringDate(v));
								break;
						}
					});
					planTables.add(planTable);
				});
				planTables.forEach(p -> LevelDataHanlder.handleLevelPlan(p, rowData.getType()));
				break;
			case Constant.CREATIVE_INFO.TABLE_NAME:
				List<CreativeTable> creativeTables = new ArrayList<>();

				rowData.getFieldValueMap().forEach(map -> {
					CreativeTable creativeTable = new CreativeTable();
					map.forEach((k, v) -> {
						switch (k) {
							case Constant.CREATIVE_INFO.COLUMN_ID:
								creativeTable.setAdId(Long.valueOf(v));
								break;
							case Constant.CREATIVE_INFO.COLUMN_TYPE:
								creativeTable.setType(Integer.valueOf(v));
								break;
							case Constant.CREATIVE_INFO.COLUMN_MATERIAL_TYPE:
								creativeTable.setMaterialType(Integer.valueOf(v));
								break;
							case Constant.CREATIVE_INFO.COLUMN_AUDIT_STATUS:
								creativeTable.setAuditStatus(Integer.valueOf(v));
								break;
							case Constant.CREATIVE_INFO.COLUMN_HEIGHT:
								creativeTable.setHeight(Integer.valueOf(v));
								break;
							case Constant.CREATIVE_INFO.COLUMN_WIDTH:
								creativeTable.setWidth(Integer.valueOf(v));
								break;
							case Constant.CREATIVE_INFO.COLUMN_URL:
								creativeTable.setUrl(v);
								break;
						}
					});
					creativeTables.add(creativeTable);
				});
				creativeTables.forEach(c -> LevelDataHanlder.handleLevelCreative(c, rowData.getType()));
				break;
		}
	}

	private void Level3RowData(MySqlRowData rowData) {

		switch (rowData.getTableName()) {
			case Constant.UNIT_INFO.TABLE_NAME:
				List<UnitTable> unitTables = new ArrayList<>();

				rowData.getFieldValueMap().forEach(map -> {
					UnitTable unitTable = new UnitTable();

					map.forEach((k, v) -> {
						switch (k) {
							case Constant.UNIT_INFO.COLUMN_ID:
								unitTable.setUnitId(Long.valueOf(v));
								break;
							case Constant.UNIT_INFO.COLUMN_PLAN_ID:
								unitTable.setPlanId(Long.valueOf(v));
								break;
							case Constant.UNIT_INFO.COLUMN_POSITION_TYPE:
								unitTable.setPositionType(Integer.valueOf(v));
								break;
							case Constant.UNIT_INFO.COLUMN_STATUS:
								unitTable.setStatus(Integer.valueOf(v));
								break;
						}
					});
					unitTables.add(unitTable);
				});
				unitTables.forEach(u -> LevelDataHanlder.handleLevelUnit(u, rowData.getType()));
				break;
			case Constant.CREATIVE_UNIT_INFO.TABLE_NAME:
				List<CreativeUnitTable> creativeUnitTables = new ArrayList<>();

				rowData.getFieldValueMap().forEach(map -> {
					CreativeUnitTable creativeUnitTable = new CreativeUnitTable();

					map.forEach((k, v) -> {
						switch (k) {
							case Constant.CREATIVE_UNIT_INFO.COLUMN_UNIT_ID:
								creativeUnitTable.setUnitId(Long.valueOf(v));
								break;
							case Constant.CREATIVE_UNIT_INFO.COLUMN_CREATIVE_ID:
								creativeUnitTable.setAdId(Long.valueOf(v));
								break;
						}
					});
					creativeUnitTables.add(creativeUnitTable);
				});
				creativeUnitTables.forEach(c -> LevelDataHanlder.handleLevelCreativeUnit(c, rowData.getType()));
				break;
		}
	}

	private void Level4RowData(MySqlRowData rowData) {

		switch (rowData.getTableName()) {
			case Constant.IT_INFO.TABLE_NAME:
				List<ItTable> itTables = new ArrayList<>();

				rowData.getFieldValueMap().forEach(map -> {
					ItTable itTable = new ItTable();

					map.forEach((k, v) -> {
						switch (k) {
							case Constant.IT_INFO.COLUMN_UNIT_ID:
								itTable.setUnitId(Long.valueOf(v));
								break;
							case Constant.IT_INFO.COLUMN_TAG:
								itTable.setTag(v);
								break;
						}
					});
					itTables.add(itTable);
				});
				itTables.forEach(i -> LevelDataHanlder.handleLevelIt(i, rowData.getType()));
				break;
			case Constant.KEYWORD_INFO.TABLE_NAME:
				List<KeyWordTable> keyWordTables = new ArrayList<>();

				rowData.getFieldValueMap().forEach(map -> {
					KeyWordTable keyWordTable = new KeyWordTable();

					map.forEach((k, v) -> {
						switch (k) {
							case Constant.KEYWORD_INFO.COLUMN_ID:
								keyWordTable.setUnitId(Long.valueOf(v));
								break;
							case Constant.KEYWORD_INFO.COLUMN_KEYWORD:
								keyWordTable.setKeyword(v);
								break;
						}
					});
					keyWordTables.add(keyWordTable);
				});
				keyWordTables.forEach(k -> LevelDataHanlder.handleLevelKeyWord(k, rowData.getType()));
				break;
			case Constant.DISTRICT_INFO.TABLE_NAME:
				List<DistrictTable> districtTables = new ArrayList<>();

				rowData.getFieldValueMap().forEach(map -> {
					DistrictTable districtTable = new DistrictTable();

					map.forEach((k, v) -> {
						switch (k) {
							case Constant.DISTRICT_INFO.COLUMN_UNIT_ID:
								districtTable.setUnitId(Long.valueOf(v));
								break;
							case Constant.DISTRICT_INFO.COLUMN_PROVINCE:
								districtTable.setProvince(v);
								break;
							case Constant.DISTRICT_INFO.COLUMN_CITY:
								districtTable.setCity(v);
								break;
						}
					});
					districtTables.add(districtTable);
				});
				districtTables.forEach(d -> LevelDataHanlder.handleLevelDistrict(d, rowData.getType()));
				break;

		}
	}
}
