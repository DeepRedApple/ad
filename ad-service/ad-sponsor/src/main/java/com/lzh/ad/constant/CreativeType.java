package com.lzh.ad.constant;

import lombok.Getter;

/**
 * @author Li
 **/
@Getter
public enum CreativeType {
	//1-图片
	IMAGE(1, "图片"),
	//2-视频
	VIDEO(2, "视频"),
	//3-文本
	TEXT(3, "文本");

	private Integer type;

	private String desc;

	CreativeType(Integer type, String desc) {
		this.type = type;
		this.desc = desc;
	}
}