package com.lzh.ad.vo;

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
public class UnitItRequest {

	private List<UnitIt> its;

	@Data
	@NoArgsConstructor
	@AllArgsConstructor
	public static class UnitIt {
		private Long unitId;
		private String tag;
	}
}
