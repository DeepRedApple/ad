package com.lzh.ad.client;

import com.lzh.ad.client.vo.Plan;
import com.lzh.ad.client.vo.PlanGetRequest;
import com.lzh.ad.vo.CommonResponse;
import org.springframework.stereotype.Component;

import java.util.List;

/**
 * @author Li
 **/
@Component
public class SponsorClientHystrix implements SponsorClient {
	@Override
	public CommonResponse<List<Plan>> getPlans(PlanGetRequest request) {
		return new CommonResponse<>(-1, "eureka-client-ad-sponsor-error");
	}
}
