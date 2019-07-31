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
@Table(name = "ad_unit")
public class Unit {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "id", nullable = false)
	private Long id;

	@Basic
	@Column(name = "plan_id", nullable = false)
	private Long planId;

	@Basic
	@Column(name = "name", nullable = false)
	private String name;

	@Basic
	@Column(name = "status", nullable = false)
	private Integer status;

	@Basic
	@Column(name = "type", nullable = false)
	public Integer type;

	@Basic
	@Column(name = "budget", nullable = false)
	public Long budget;

	@Basic
	@Column(name = "create_time", nullable = false)
	public Date createTime;

	@Basic
	@Column(name = "update_time", nullable = false)
	public Date updateTime;

	public Unit(Long planId, String name, Integer type, Long budget) {
		this.planId = planId;
		this.name = name;
		this.type = type;
		this.budget = budget;
		this.status = CommonStatus.VALID.getStatus();
		this.createTime = new Date();
		this.updateTime = new Date();
	}
}
