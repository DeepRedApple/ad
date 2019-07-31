package com.lzh.ad.constant;

import lombok.Getter;

/**
 * @author Li
 **/
@Getter
public enum  CreativeMaterialType {
	//1-jpg
	JPG(1, "jpg"),
	//2-bmp
	BMP(2, "bmp"),

	//3-mp4
	MP4(3, "mp4"),
	//4-avi
	AVI(4, "avi"),

	//5-txt
	TXT(5, "txt");

	private Integer type;

	private String desc;

	CreativeMaterialType(Integer type, String desc) {
		this.type = type;
		this.desc = desc;
	}
}