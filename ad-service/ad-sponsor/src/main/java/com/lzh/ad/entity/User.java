package com.lzh.ad.entity;

import com.lzh.ad.constant.CommonStatus;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import java.util.Date;

/**
 * @author Li
 **/
@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "ad_user")
public class User {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "id", nullable = false)
	private Long id;

	@Basic
	@Column(name = "username", nullable = false)
	private String name;

	@Basic
	@Column(name = "token", nullable = false)
	private String token;

	@Basic
	@Column(name = "status", nullable = false)
	private Integer status;

	@Basic
	@Column(name = "create_time", nullable = false)
	private Date createTime;

	@Basic
	@Column(name = "update_time", nullable = false)
	private Date updateTime;

	public User(String name, String token) {
		this.name = name;
		this.token = token;
		this.status = CommonStatus.VALID.getStatus();
		this.createTime = new Date();
		this.updateTime = new Date();
	}
}
