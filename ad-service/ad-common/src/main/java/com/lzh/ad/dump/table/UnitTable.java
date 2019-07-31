package com.lzh.ad.dump.table;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @author Li
 **/
@Data
@NoArgsConstructor
@AllArgsConstructor
public class UnitTable {

	private Long unitId;
	private Integer status;
	private Integer positionType;

	private Long planId;
}
