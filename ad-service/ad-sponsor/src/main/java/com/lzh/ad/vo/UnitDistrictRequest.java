package com.lzh.ad.vo;

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
public class UnitDistrictRequest {

	private List<District> districts;

	@Data
	@AllArgsConstructor
	@NoArgsConstructor
	public static class District {
		private Long unitId;
		private String province;
		private String city;
	}

}
