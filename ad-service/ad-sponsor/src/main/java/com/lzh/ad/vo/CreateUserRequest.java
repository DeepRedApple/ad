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
public class CreateUserRequest {
	private String name;

	public boolean validate() {
		return !StringUtils.isEmpty(name);
	}
}
