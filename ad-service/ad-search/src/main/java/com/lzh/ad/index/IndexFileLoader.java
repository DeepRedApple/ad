package com.lzh.ad.index;

import com.alibaba.fastjson.JSON;
import com.lzh.ad.dump.Dconstant;
import com.lzh.ad.dump.table.CreativeTable;
import com.lzh.ad.dump.table.CreativeUnitTable;
import com.lzh.ad.dump.table.DistrictTable;
import com.lzh.ad.dump.table.ItTable;
import com.lzh.ad.dump.table.KeyWordTable;
import com.lzh.ad.dump.table.PlanTable;
import com.lzh.ad.dump.table.UnitTable;
import com.lzh.ad.handler.LevelDataHanlder;
import com.lzh.ad.mysql.constant.OpType;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.annotation.DependsOn;

import javax.annotation.PostConstruct;
import java.io.BufferedReader;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.List;
import java.util.stream.Collectors;

/**
 * @author Li
 **/
@Slf4j
@DependsOn("dataTable")
public class IndexFileLoader {

	@PostConstruct
	public void init() {
		List<String> planStrings = loadDumpData(String.format("%s%s", Dconstant.DATA_ROOT_DIR, Dconstant.PLAN));
		planStrings.forEach(s ->
			LevelDataHanlder.handleLevelPlan(JSON.parseObject(s, PlanTable.class), OpType.ADD)
		);

		List<String> creativeStrings = loadDumpData(String.format("%s%s", Dconstant.DATA_ROOT_DIR, Dconstant.CREATIVE));
		creativeStrings.forEach(c ->
			LevelDataHanlder.handleLevelCreative(JSON.parseObject(c, CreativeTable.class), OpType.ADD)
		);

		List<String> unitStrings = loadDumpData(String.format("%s%s", Dconstant.DATA_ROOT_DIR, Dconstant.UNIT));
		unitStrings.forEach(u ->
			LevelDataHanlder.handleLevelUnit(JSON.parseObject(u, UnitTable.class), OpType.ADD)
		);

		List<String> creativeUnitStrings = loadDumpData(String.format("%s%s", Dconstant.DATA_ROOT_DIR, Dconstant.CREATIVE_UNIT));
		creativeUnitStrings.forEach(c ->
			LevelDataHanlder.handleLevelCreativeUnit(JSON.parseObject(c, CreativeUnitTable.class), OpType.ADD)
		);

		List<String> districtStrings = loadDumpData(String.format("%s%s", Dconstant.DATA_ROOT_DIR, Dconstant.DISTRICT));
		districtStrings.forEach(d ->
			LevelDataHanlder.handleLevelDistrict(JSON.parseObject(d, DistrictTable.class), OpType.ADD)
		);

		List<String> itStrings = loadDumpData(String.format("%s%s", Dconstant.DATA_ROOT_DIR, Dconstant.IT));
		itStrings.forEach(i ->
			LevelDataHanlder.handleLevelIt(JSON.parseObject(i, ItTable.class), OpType.ADD)
		);

		List<String> keyWordStrings = loadDumpData(String.format("%s%s", Dconstant.DATA_ROOT_DIR, Dconstant.KEYWORD));
		keyWordStrings.forEach(k ->
			LevelDataHanlder.handleLevelKeyWord(JSON.parseObject(k, KeyWordTable.class), OpType.ADD)
		);
	}

	private List<String> loadDumpData(String fileName) {
		try (BufferedReader reader = Files.newBufferedReader(Paths.get(fileName))) {
			return reader.lines().collect(Collectors.toList());
		} catch (IOException e) {
			throw new RuntimeException(e.getMessage());
		}
	}

}
