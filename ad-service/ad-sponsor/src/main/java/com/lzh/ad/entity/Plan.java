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
@Table(name = "ad_plan")
public class Plan {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "id", nullable = false)
	private Long id;

	@Basic
	@Column(name = "user_id", nullable = false)
	private Long userId;

	@Basic
	@Column(name = "name", nullable = false)
	private String name;

	@Basic
	@Column(name = "status", nullable = false)
	private Integer status;

	@Basic
	@Column(name = "start_date", nullable = false)
	private Date startDate;

	@Basic
	@Column(name = "end_date", nullable = false)
	private Date endDate;

	@Basic
	@Column(name = "create_time", nullable = false)
	private Date createTime;

	@Basic
	@Column(name = "update_time", nullable = false)
	private Date updateTime;

	public Plan(Long userId, String name, Date startDate, Date endDate) {
		this.userId = userId;
		this.name = name;
		this.startDate = startDate;
		this.endDate = endDate;
		this.status = CommonStatus.VALID.getStatus();
		this.createTime = new Date();
		this.updateTime = new Date();
	}
}
