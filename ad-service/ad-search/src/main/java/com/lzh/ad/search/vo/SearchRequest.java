package com.lzh.ad.search.vo;

import com.lzh.ad.search.vo.feature.DistrictFeature;
import com.lzh.ad.search.vo.feature.FeatureRelation;
import com.lzh.ad.search.vo.feature.ItFeature;
import com.lzh.ad.search.vo.feature.KeyWordFeature;
import com.lzh.ad.search.vo.media.App;
import com.lzh.ad.search.vo.media.Device;
import com.lzh.ad.search.vo.media.Goe;
import com.lzh.ad.search.vo.media.Slot;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

/**
 * @author Li
 **/
@Data
@AllArgsConstructor
@NoArgsConstructor
public class SearchRequest {

	private String mediaId;

	private RequestInfo requestInfo;

	private FeatureInfo featureInfo;

	@Data
	@AllArgsConstructor
	@NoArgsConstructor
	public static class RequestInfo {
		private String requestId;

		private List<Slot> slots;
		private List<App> apps;
		private List<Goe> goes;
		private List<Device> devices;
	}

	@Data
	@AllArgsConstructor
	@NoArgsConstructor
	public static class FeatureInfo {

		private KeyWordFeature keyWordFeature;
		private ItFeature itFeature;
		private DistrictFeature districtFeature;

		private FeatureRelation featureRelation = FeatureRelation.AND;
	}
}
