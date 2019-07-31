package com.lzh.ad.service;

import com.lzh.ad.entity.Plan;
import com.lzh.ad.exception.AdException;
import com.lzh.ad.vo.PlanGetRequest;
import com.lzh.ad.vo.PlanRequest;
import com.lzh.ad.vo.PlanResponse;

import java.util.List;

/**
 * @author Li
 **/
public interface IPlanService {

	PlanResponse createPlan(PlanRequest request) throws AdException;

	List<Plan> getPlanByIds(PlanGetRequest request) throws AdException;

	PlanResponse updatePlan(PlanRequest request) throws AdException;

	void deletePlan(PlanRequest request) throws AdException;
}
