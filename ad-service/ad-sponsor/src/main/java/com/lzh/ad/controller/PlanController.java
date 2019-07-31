package com.lzh.ad.controller;

import com.alibaba.fastjson.JSON;
import com.lzh.ad.entity.Plan;
import com.lzh.ad.exception.AdException;
import com.lzh.ad.service.IPlanService;
import com.lzh.ad.vo.PlanGetRequest;
import com.lzh.ad.vo.PlanRequest;
import com.lzh.ad.vo.PlanResponse;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

/**
 * @author Li
 **/
@Slf4j
@RestController
public class PlanController {

	@Autowired
	private IPlanService planService;

	@PostMapping("/create/plan")
	public PlanResponse createPlan(@RequestBody PlanRequest request) throws AdException {
		if (log.isDebugEnabled()) {
			log.debug("/create/plan: {}", JSON.toJSONString(request));
		}
		return planService.createPlan(request);
	}

	@PostMapping("/get/plan")
	public List<Plan> getPlanByIds(@RequestBody PlanGetRequest request) throws AdException {
		if (log.isDebugEnabled()) {
			log.debug("/get/plan：{}", JSON.toJSONString(request));
		}
		return planService.getPlanByIds(request);
	}

	@PutMapping("/update/plan")
	public PlanResponse updatePlan(@RequestBody PlanRequest request) throws AdException {
		if (log.isDebugEnabled()) {
			log.debug("/update/plan:{}", JSON.toJSONString(request));
		}
		return planService.updatePlan(request);
	}

	@DeleteMapping("/delete/plan")
	public void deletePlan(@RequestBody PlanRequest request) throws AdException {
		if (log.isDebugEnabled()) {
			log.debug("/delete/plan:{}", JSON.toJSONString(request));
		}
		planService.deletePlan(request);
	}
}
