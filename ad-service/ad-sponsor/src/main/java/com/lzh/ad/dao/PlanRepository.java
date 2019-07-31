package com.lzh.ad.dao;

import com.lzh.ad.entity.Plan;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

/**
 * @author Li
 **/
public interface PlanRepository extends JpaRepository<Plan, Long> {

	Plan findByIdAndUserId(Long id, Long userId);

	List<Plan> findAllByIdInAndUserId(List<Long> ids, Long userId);

	Plan findByUserIdAndName(Long userId, String name);

	List<Plan> findAllByStatus(Integer status);
}
