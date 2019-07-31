package com.lzh.ad.search;

import com.lzh.ad.Application;
import com.lzh.ad.search.vo.SearchRequest;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

/**
 * @author Li
 **/
@RunWith(SpringRunner.class)
@SpringBootTest(classes = {Application.class}, webEnvironment = SpringBootTest.WebEnvironment.NONE)
public class SearchTest {

	@Autowired
	private ISearch search;

	@Test
	public void testSearch() {
		SearchRequest request = new SearchRequest();
		request.setMediaId("ad");

		System.out.println(search.fetchAds(request));
	}
}
