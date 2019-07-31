package com.lzh.ad.search.vo.feature;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

/**
 * @author Li
 **/
@Data
@NoArgsConstructor
@AllArgsConstructor
public class DistrictFeature {

	private List<ProvinceAndCity> provinceAndCities;

	@Data
	@NoArgsConstructor
	@AllArgsConstructor
	public static class ProvinceAndCity {
		private String province;
		private String city;
	}
}
