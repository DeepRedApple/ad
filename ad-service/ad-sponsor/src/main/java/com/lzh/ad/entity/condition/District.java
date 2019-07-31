package com.lzh.ad.entity.condition;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

/**
 * @author Li
 **/
@Data
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "ad_unit_district")
public class District {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "id", nullable = false)
	private Long id;

	@Basic
	@Column(name = "unit_id", nullable = false)
	private Long unitId;

	@Basic
	@Column(name = "province", nullable = false)
	private String province;

	@Basic
	@Column(name = "city", nullable = false)
	private String city;

	public District(Long unitId, String province, String city) {
		this.unitId = unitId;
		this.province = province;
		this.city = city;
	}
}
