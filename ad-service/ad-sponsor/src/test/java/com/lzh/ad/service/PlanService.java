package com.lzh.ad.service;

import com.lzh.ad.Application;
import com.lzh.ad.exception.AdException;
import com.lzh.ad.vo.PlanGetRequest;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.Collections;

/**
 * @author Li
 **/
@RunWith(SpringRunner.class)
@SpringBootTest(classes = {Application.class}, webEnvironment = SpringBootTest.WebEnvironment.NONE)
public class PlanService {

	@Autowired
	private IPlanService planService;

	@Test
	public void testGetPlan() throws AdException {
		System.out.println(planService.getPlanByIds(new PlanGetRequest(10L, Collections.singletonList(11L))));
	}
}
