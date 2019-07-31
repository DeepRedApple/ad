package com.lzh.ad.service.impl;

import com.lzh.ad.constant.CommonStatus;
import com.lzh.ad.constant.Constants;
import com.lzh.ad.dao.PlanRepository;
import com.lzh.ad.dao.UserRepository;
import com.lzh.ad.entity.Plan;
import com.lzh.ad.entity.User;
import com.lzh.ad.exception.AdException;
import com.lzh.ad.service.IPlanService;
import com.lzh.ad.utils.CommonUtils;
import com.lzh.ad.vo.PlanGetRequest;
import com.lzh.ad.vo.PlanRequest;
import com.lzh.ad.vo.PlanResponse;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Date;
import java.util.List;
import java.util.Optional;

/**
 * @author Li
 **/
@Slf4j
@Service
public class PlanServiceImpl implements IPlanService {

	@Autowired
	private UserRepository userRepository;

	@Autowired
	private PlanRepository planRepository;

	@Override
	public PlanResponse createPlan(PlanRequest request) throws AdException {
		if (!request.createValidate()) {
			throw new AdException(Constants.ErrorMsg.REQUEST_PARAM_MESSAGE);
		}

		Optional<User> user = userRepository.findById(request.getUserId());
		if (!user.isPresent()) {
			throw new AdException(Constants.ErrorMsg.CAN_NOT_FIND_RECORD);
		}

		Plan oldPlan = planRepository.findByIdAndUserId(request.getId(), request.getUserId());
		if (oldPlan != null) {
			throw new AdException(Constants.ErrorMsg.SAME_NAME_PLAN_ERROR);
		}

		Plan newPlan = planRepository.save(new Plan(request.getUserId(), request.getName(),
			CommonUtils.parseStringDate(request.getStartDate()), CommonUtils.parseStringDate(request.getEndDate())));

		return new PlanResponse(newPlan.getId(), newPlan.getName());
	}

	@Override
	public List<Plan> getPlanByIds(PlanGetRequest request) throws AdException {
		if (!request.validate()) {
			throw new AdException(Constants.ErrorMsg.REQUEST_PARAM_MESSAGE);
		}
		return planRepository.findAllByIdInAndUserId(request.getIds(), request.getUserId());
	}

	@Override
	@Transactional
	public PlanResponse updatePlan(PlanRequest request) throws AdException {
		if (!request.updateValidate()) {
			throw new AdException(Constants.ErrorMsg.REQUEST_PARAM_MESSAGE);
		}
		Plan plan = planRepository.findByIdAndUserId(request.getId(), request.getUserId());
		if (plan == null) {
			throw new AdException(Constants.ErrorMsg.CAN_NOT_FIND_RECORD);
		}
		if (request.getName() != null) {
			plan.setName(request.getName());
		}
		if (request.getStartDate() != null) {
			plan.setStartDate(CommonUtils.parseStringDate(request.getStartDate()));
		}
		if (request.getEndDate() != null) {
			plan.setEndDate(CommonUtils.parseStringDate(request.getEndDate()));
		}
		plan.setUpdateTime(new Date());
		plan = planRepository.save(plan);
		return new PlanResponse(plan.getId(), plan.getName());
	}

	@Override
	@Transactional
	public void deletePlan(PlanRequest request) throws AdException {
		if (!request.deleteValidate()) {
			throw new AdException(Constants.ErrorMsg.REQUEST_PARAM_MESSAGE);
		}
		Plan plan = planRepository.findByIdAndUserId(request.getId(), request.getUserId());
		if (plan == null) {
			throw new AdException(Constants.ErrorMsg.CAN_NOT_FIND_RECORD);
		}
		plan.setStatus(CommonStatus.INVALID.getStatus());
		plan.setUpdateTime(new Date());
		planRepository.save(plan);
	}
}
