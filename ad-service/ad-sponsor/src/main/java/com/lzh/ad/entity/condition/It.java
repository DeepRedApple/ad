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
@Table(name = "ad_unit_it")
public class It {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "id", nullable = false)
	private Long id;

	@Basic
	@Column(name = "unit_id", nullable = false)
	private Long unitId;

	@Basic
	@Column(name = "it_tag", nullable = false)
	private String tag;

	public It(Long unitId, String tag) {
		this.unitId = unitId;
		this.tag = tag;
	}
}
