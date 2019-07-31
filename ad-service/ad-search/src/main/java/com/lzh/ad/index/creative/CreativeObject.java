package com.lzh.ad.index.creative;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @author Li
 **/
@Data
@AllArgsConstructor
@NoArgsConstructor
public class CreativeObject {
	private Long adId;
	private String name;
	private Integer type;
	private Integer materialType;
	private Integer height;
	private Integer width;
	private Integer auditStatus;
	private String url;

	public void update(CreativeObject object) {
		if (null != object.getAdId()) {
			adId = object.getAdId();
		}
		if (null != object.getName()) {
			name = object.getName();
		}
		if (null != object.getType()) {
			type = object.getType();
		}
		if (null != object.getMaterialType()) {
			materialType = object.getMaterialType();
		}
		if (null != object.getHeight()) {
			height = object.getHeight();
		}
		if (null != object.getWidth()) {
			width = object.getWidth();
		}
		if (null != object.getAuditStatus()) {
			auditStatus = object.getAuditStatus();
		}
		if (null != object) {
			url = object.getUrl();
		}
	}
}
