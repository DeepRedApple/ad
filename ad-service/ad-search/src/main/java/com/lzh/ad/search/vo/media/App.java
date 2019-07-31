package com.lzh.ad.search.vo.media;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @author Li
 **/
@Data
@NoArgsConstructor
@AllArgsConstructor
public class App {

	private String code;

	private String name;

	private String packageName;

	private String activityName;
}
