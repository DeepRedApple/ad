package com.lzh.ad.controller;

import com.alibaba.fastjson.JSON;
import com.lzh.ad.annotation.IgnoreResponseAdvice;
import com.lzh.ad.client.SponsorClient;
import com.lzh.ad.client.vo.Plan;
import com.lzh.ad.client.vo.PlanGetRequest;
import com.lzh.ad.search.ISearch;
import com.lzh.ad.search.vo.SearchRequest;
import com.lzh.ad.search.vo.SearchResponse;
import com.lzh.ad.vo.CommonResponse;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;

import java.util.List;

/**
 * @author Li
 **/
@Slf4j
@RestController
public class SearchController {

	@Autowired
	private RestTemplate restTemplate;

	@Autowired
	private SponsorClient sponsorClient;

	@Autowired
	private ISearch search;

	@PostMapping("/fetchAds")
	public SearchResponse fetch(@RequestBody SearchRequest request) {
		if (log.isDebugEnabled()) {
			log.debug("/fetch :{}", JSON.toJSONString(request));
		}

		return search.fetchAds(request);
	}

	@IgnoreResponseAdvice
	@PostMapping("/getPlanByRibbon")
	public CommonResponse<List<Plan>> getPlanByRibbon(@RequestBody PlanGetRequest request) {
		if (log.isDebugEnabled()) {
			log.debug("ad-service: getPlanByRibbon {}", JSON.toJSONString(request));
		}
		ResponseEntity<CommonResponse> entity = restTemplate.postForEntity(
			"http://eureka-client-ad-sponsor/ad-sponsor/get/plan", request, CommonResponse.class);
		return entity.getBody();
	}


}
