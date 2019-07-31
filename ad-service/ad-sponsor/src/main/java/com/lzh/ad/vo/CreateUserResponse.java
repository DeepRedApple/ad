package com.lzh.ad.vo;

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
public class CreateUserResponse {
	private Long userId;
	private String name;
	private String token;
	private Date createTime;
	private Date updateTime;

}
