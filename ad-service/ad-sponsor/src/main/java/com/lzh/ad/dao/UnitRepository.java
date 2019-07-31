package com.lzh.ad.dao;

import com.lzh.ad.entity.Unit;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

/**
 * @author Li
 **/
public interface UnitRepository extends JpaRepository<Unit, Long> {

	Unit findByPlanIdAndName(Long planId, String name);

	List<Unit> findAllByStatus(Integer status);
}
