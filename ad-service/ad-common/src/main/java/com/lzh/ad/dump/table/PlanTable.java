package com.lzh.ad.dump.table;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;

/**
 * @author Li
 **/
@Data
@NoArgsConstructor
@AllArgsConstructor
public class PlanTable {

	private Long id;
	private Long userId;
	private Integer status;
	private Date startDate;
	private Date endDate;

}
