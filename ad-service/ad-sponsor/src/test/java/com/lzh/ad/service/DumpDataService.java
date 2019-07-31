package com.lzh.ad.service;

import com.alibaba.fastjson.JSON;
import com.lzh.ad.Application;
import com.lzh.ad.constant.CommonStatus;
import com.lzh.ad.dao.CreativeRepository;
import com.lzh.ad.dao.CreativeUnitRepository;
import com.lzh.ad.dao.PlanRepository;
import com.lzh.ad.dao.UnitRepository;
import com.lzh.ad.dao.condition.DistrictRepository;
import com.lzh.ad.dao.condition.ItRepository;
import com.lzh.ad.dao.condition.KeyWordRepository;
import com.lzh.ad.dump.Dconstant;
import com.lzh.ad.dump.table.CreativeTable;
import com.lzh.ad.dump.table.CreativeUnitTable;
import com.lzh.ad.dump.table.DistrictTable;
import com.lzh.ad.dump.table.ItTable;
import com.lzh.ad.dump.table.KeyWordTable;
import com.lzh.ad.dump.table.PlanTable;
import com.lzh.ad.dump.table.UnitTable;
import com.lzh.ad.entity.Creative;
import com.lzh.ad.entity.CreativeUnit;
import com.lzh.ad.entity.Plan;
import com.lzh.ad.entity.Unit;
import com.lzh.ad.entity.condition.District;
import com.lzh.ad.entity.condition.It;
import com.lzh.ad.entity.condition.KeyWord;
import lombok.extern.slf4j.Slf4j;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.util.CollectionUtils;

import java.io.BufferedWriter;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;

/**
 * @author Li
 **/
@Slf4j
@RunWith(SpringRunner.class)
@SpringBootTest(classes = Application.class, webEnvironment = SpringBootTest.WebEnvironment.NONE)
public class DumpDataService {

	@Autowired
	private PlanRepository planRepository;

	@Autowired
	private UnitRepository unitRepository;

	@Autowired
	private CreativeRepository creativeRepository;

	@Autowired
	private CreativeUnitRepository creativeUnitRepository;

	@Autowired
	private DistrictRepository districtRepository;

	@Autowired
	private KeyWordRepository keyWordRepository;

	@Autowired
	private ItRepository itRepository;

	@Test
	public void dumpTableData() {
		dumpPlanTable(String.format("%s%s", Dconstant.DATA_ROOT_DIR, Dconstant.PLAN));
		dumpUnitTable(String.format("%s%s", Dconstant.DATA_ROOT_DIR, Dconstant.UNIT));
		dumpCreativeTable(String.format("%s%s", Dconstant.DATA_ROOT_DIR, Dconstant.CREATIVE));
		dumpCreativeUnitTable(String.format("%s%s", Dconstant.DATA_ROOT_DIR, Dconstant.CREATIVE_UNIT));
		dumpItTable(String.format("%s%s", Dconstant.DATA_ROOT_DIR, Dconstant.IT));
		dumpDistrictTable(String.format("%s%s", Dconstant.DATA_ROOT_DIR, Dconstant.DISTRICT));
		dumpKeyWordTable(String.format("%s%s", Dconstant.DATA_ROOT_DIR, Dconstant.KEYWORD));

	}

	private void dumpPlanTable(String fileName) {
		List<Plan> plans = planRepository.findAllByStatus(CommonStatus.VALID.getStatus());
		if (CollectionUtils.isEmpty(plans)) {
			return;
		}
		List<PlanTable> planTables = new ArrayList<>(plans.size());
		plans.forEach(plan ->
			planTables.add(new PlanTable(plan.getId(), plan.getUserId(), plan.getStatus(), plan.getStartDate(),
				plan.getEndDate()))
		);
		Path path = Paths.get(fileName);
		try (BufferedWriter writer = Files.newBufferedWriter(path)) {
			planTables.forEach(planTable -> {
				try {
					writer.write(JSON.toJSONString(planTable));
					writer.newLine();
				} catch (IOException e) {
					log.error("dumpPlanTableWrite error", e);
				}
			});
		} catch (IOException e) {
			log.error("dumpPlanTable error:", e);
		}
	}

	private void dumpUnitTable(String fileName) {
		List<Unit> units = unitRepository.findAllByStatus(CommonStatus.VALID.getStatus());
		if (CollectionUtils.isEmpty(units)) {
			return;
		}
		List<UnitTable> unitTables = new ArrayList<>(units.size());
		units.forEach(unit -> unitTables.add(new UnitTable(unit.getId(), unit.getStatus(), unit.getType(), unit.getPlanId())));
		Path path = Paths.get(fileName);
		try (BufferedWriter writer = Files.newBufferedWriter(path)){
			unitTables.forEach(unitTable -> {
				try {
					writer.write(JSON.toJSONString(unitTable));
					writer.newLine();
				} catch (IOException e) {
					log.error("dumpUnitTableWrite error", e);
				}
			});
		} catch (IOException e) {
			log.error("dumpUnitTable error:", e);
		}
	}

	private void dumpCreativeTable(String fileName) {
		List<Creative> creatives = creativeRepository.findAll();
		if (CollectionUtils.isEmpty(creatives)) {
			return;
		}
		List<CreativeTable> creativeTables = new ArrayList<>(creatives.size());
		creatives.forEach(creative -> creativeTables.add(new CreativeTable(creative.getId(), creative.getName(),
			creative.getType(), creative.getMaterialType(), creative.getHeight(), creative.getWidth(),
			creative.getAuditStatus(), creative.getUrl())));
		Path path = Paths.get(fileName);
		try (BufferedWriter writer = Files.newBufferedWriter(path)) {
			creativeTables.forEach(creativeTable -> {
				try {
					writer.write(JSON.toJSONString(creativeTable));
					writer.newLine();
				} catch (IOException e) {
					log.error("dumpUnitTableWrite error", e);
				}
			});
		} catch (IOException e) {
			log.error("dumpUnitTable error:", e);
		}
	}

	private void dumpCreativeUnitTable(String fileName) {
		List<CreativeUnit> creativeUnits = creativeUnitRepository.findAll();
		if (CollectionUtils.isEmpty(creativeUnits)) {
			return;
		}
		List<CreativeUnitTable> creativeUnitTables = new ArrayList<>(creativeUnits.size());
		creativeUnits.forEach(creativeUnit -> creativeUnitTables.add(new CreativeUnitTable(creativeUnit.getCreativeId(),
			creativeUnit.getUnitId())));
		Path path = Paths.get(fileName);
		try (BufferedWriter writer = Files.newBufferedWriter(path)) {
			creativeUnitTables.forEach(creativeUnitTable -> {
				try {
					writer.write(JSON.toJSONString(creativeUnitTable));
					writer.newLine();
				} catch (IOException e) {
					log.error("dumpUnitTableWrite error", e);
				}
			});
		} catch (IOException e) {
			log.error("dumpUnitTable error:", e);
		}
	}

	private void dumpItTable(String fileName) {
		List<It> its = itRepository.findAll();
		if (CollectionUtils.isEmpty(its)) {
			return;
		}
		List<ItTable> itTables = new ArrayList<>(its.size());
		its.forEach(it -> itTables.add(new ItTable(it.getUnitId(), it.getTag())));
		Path path = Paths.get(fileName);
		try (BufferedWriter writer = Files.newBufferedWriter(path)) {
			itTables.forEach(itTable -> {
				try {
					writer.write(JSON.toJSONString(itTable));
					writer.newLine();
				} catch (IOException e) {
					log.error("dumpItTableWrite error", e);
				}
			});
		} catch (IOException e) {
			log.error("dumpItTable error:", e);
		}
	}

	private void dumpDistrictTable(String fileName) {
		List<District> districts = districtRepository.findAll();
		if (CollectionUtils.isEmpty(districts)) {
			return;
		}
		List<DistrictTable> districtTables = new ArrayList<>(districts.size());
		districts.forEach(district -> districtTables.add(new DistrictTable(district.getUnitId(),
			district.getProvince(), district.getCity())));
		Path path = Paths.get(fileName);
		try (BufferedWriter writer = Files.newBufferedWriter(path)) {
			districtTables.forEach(districtTable -> {
				try {
					writer.write(JSON.toJSONString(districtTable));
					writer.newLine();
				} catch (IOException e) {
					log.error("dumpDistrictTableWrite error", e);
				}
			});
		} catch (IOException e) {
			log.error("dumpDistrictTable error:", e);
		}
	}

	private void dumpKeyWordTable(String fileName) {
		List<KeyWord> keyWords = keyWordRepository.findAll();
		if (CollectionUtils.isEmpty(keyWords)) {
			return;
		}
		List<KeyWordTable> keyWordTables = new ArrayList<>(keyWords.size());
		keyWords.forEach(keyWord -> keyWordTables.add(new KeyWordTable(keyWord.getUnitId(), keyWord.getKeyword())));
		Path path = Paths.get(fileName);
		try (BufferedWriter writer = Files.newBufferedWriter(path)) {
			keyWordTables.forEach(keyWordTable -> {
				try {
					writer.write(JSON.toJSONString(keyWordTable));
					writer.newLine();
				} catch (IOException e) {
					log.error("dumpKeyWordTableWrite error", e);
				}
			});
		} catch (IOException e) {
			log.error("dumpKeyWordTable error:", e);
		}
	}

}
