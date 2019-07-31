package com.lzh.ad.search;

import com.lzh.ad.search.vo.SearchRequest;
import com.lzh.ad.search.vo.SearchResponse;

/**
 * @author Li
 **/
public interface ISearch {

	SearchResponse fetchAds(SearchRequest request);
}
