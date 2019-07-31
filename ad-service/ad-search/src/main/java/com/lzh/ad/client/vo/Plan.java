package com.lzh.ad.client.vo;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;

/**
 * @author Li
 **/
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Plan {

	private Long id;
	private Long userId;
	private String name;
	private Integer status;
	private Date startDate;
	private Date endDate;
	private Date createTime;
	private Date updateTime;
}
