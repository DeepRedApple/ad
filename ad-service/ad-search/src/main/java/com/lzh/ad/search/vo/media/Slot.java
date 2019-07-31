package com.lzh.ad.search.vo.media;

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
public class Slot {

	private String code;

	private Integer positionType;

	private Integer width;
	private Integer height;

	private List<Integer> type;

	private Integer minCpm;
}
