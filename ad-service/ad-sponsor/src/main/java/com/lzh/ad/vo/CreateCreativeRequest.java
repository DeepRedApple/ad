package com.lzh.ad.vo;

import com.lzh.ad.constant.CommonStatus;
import com.lzh.ad.entity.Creative;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;

/**
 * @author Li
 **/
@Data
@AllArgsConstructor
@NoArgsConstructor
public class CreateCreativeRequest {
	private String name;
	private Integer type;
	private Integer materialType;
	private Integer height;
	private Integer width;
	private Long size;
	private Integer duration;
	private Long userId;
	private String url;

	public Creative convertToEntity() {
		Creative creative = new Creative();
		creative.setName(name);
		creative.setType(type);
		creative.setMaterialType(materialType);
		creative.setHeight(height);
		creative.setWidth(width);
		creative.setSize(size);
		creative.setAuditStatus(CommonStatus.VALID.getStatus());
		creative.setDuration(duration);
		creative.setUserId(userId);
		creative.setCreateTime(new Date());
		creative.setUpdateTime(creative.getCreateTime());
		creative.setUrl(url);
		return creative;
	}
}
