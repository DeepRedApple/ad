package com.lzh.ad.dao;

import com.lzh.ad.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;

/**
 * @author Li
 **/
public interface UserRepository extends JpaRepository<User, Long> {


	User findByName(String name);
}
