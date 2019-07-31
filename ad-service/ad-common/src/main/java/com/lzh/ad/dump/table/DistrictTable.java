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
public class DistrictTable {

	private Long unitId;
	private String province;
	private String city;
}
