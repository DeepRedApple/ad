package com.lzh.ad.search.vo;

import com.lzh.ad.index.creative.CreativeObject;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @author Li
 **/
@Data
@AllArgsConstructor
@NoArgsConstructor
public class SearchResponse {

	public Map<String, List<Creative>> adSlot2Ads = new HashMap<>();

	@Data
	@AllArgsConstructor
	@NoArgsConstructor
	public static class Creative {
		private Long id;
		private String url;
		private Integer width;
		private Integer height;
		private Integer type;
		private Integer materialType;

		private List<String> showMonitorUrl = new ArrayList<>();
		private List<String> clickMonitorUrl = new ArrayList<>();

	}

	public static Creative convert(CreativeObject object) {
		Creative creative = new Creative();
		creative.setId(object.getAdId());
		creative.setUrl(object.getUrl());
		creative.setWidth(object.getWidth());
		creative.setHeight(object.getHeight());
		creative.setType(object.getType());
		creative.setMaterialType(object.getMaterialType());

		return creative;
	}
}
