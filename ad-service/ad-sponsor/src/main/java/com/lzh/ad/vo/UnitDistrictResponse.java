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
public class UnitDistrictResponse {
	private List<Long> ids;
}
