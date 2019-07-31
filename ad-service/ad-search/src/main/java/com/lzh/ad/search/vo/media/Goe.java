package com.lzh.ad.search.vo.media;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @author Li
 **/
@Data
@AllArgsConstructor
@NoArgsConstructor
public class Goe {

	private Float latitude;
	private Float longitude;

	private String city;
	private String province;

}
