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
public class UnitKeyWordRequest {

	private List<UnitKeyWord> unitKeyWords;

	@Data
	@NoArgsConstructor
	@AllArgsConstructor
	public static class UnitKeyWord {
		private Long unitId;
		private String keyword;
	}
}
