package com.lzh.ad.client;

import com.lzh.ad.client.vo.Plan;
import com.lzh.ad.client.vo.PlanGetRequest;
import com.lzh.ad.vo.CommonResponse;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import java.util.List;

/**
 * @author Li
 **/
@FeignClient(value = "eureka-client-ad-sponsor", fallback = SponsorClientHystrix.class)
public interface SponsorClient {

	@RequestMapping(value = "/ad-sponsor/get/plan", method = RequestMethod.POST)
	CommonResponse<List<Plan>> getPlans(@RequestBody PlanGetRequest request);
}
