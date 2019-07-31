package com.lzh.ad.vo;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.util.CollectionUtils;

import java.util.List;

/**
 * @author Li
 **/
@Data
@NoArgsConstructor
@AllArgsConstructor
public class PlanGetRequest {
	private Long userId;
	private List<Long> ids;

	public boolean validate() {
		return userId != null && CollectionUtils.isEmpty(ids);
	}
}
