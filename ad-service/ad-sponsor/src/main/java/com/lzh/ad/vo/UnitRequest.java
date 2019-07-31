package com.lzh.ad.vo;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.apache.commons.lang.StringUtils;

/**
 * @author Li
 **/
@Data
@NoArgsConstructor
@AllArgsConstructor
public class UnitRequest {
	private Long planId;
	private String name;
	private Integer positionType;
	private Long budget;

	public boolean createValidate() {
		return null != planId && !StringUtils.isEmpty(name)
			&& null != positionType && null != budget;
	}
}
