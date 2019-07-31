package com.lzh.ad.index.plan;

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
public class PlanObject {

	private Long planId;
	private Long userId;
	private Integer status;
	private Date startDate;
	private Date endDate;

	public void update(PlanObject newObject) {
		if (null != newObject.getPlanId()) {
			this.planId = newObject.getPlanId();
		}
		if (null != newObject.getUserId()) {
			this.userId = newObject.getUserId();
		}
		if (null != newObject.getStatus()) {
			this.status = newObject.getStatus();
		}
		if (null != newObject.getStartDate()) {
			this.startDate = newObject.getStartDate();
		}
		if (null != newObject.getEndDate()) {
			this.endDate = newObject.getEndDate();
		}
	}
}
