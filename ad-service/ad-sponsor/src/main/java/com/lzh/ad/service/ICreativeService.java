package com.lzh.ad.service;

import com.lzh.ad.vo.CreateCreativeRequest;
import com.lzh.ad.vo.CreateCreativeResponse;

/**
 * @author Li
 **/
public interface ICreativeService {

	CreateCreativeResponse createCreative(CreateCreativeRequest request);

}
