package com.enjoyf.platform.userservice.repository;

import com.enjoyf.platform.userservice.domain.AppConf;
import org.springframework.stereotype.Repository;

import org.springframework.data.jpa.repository.*;

import java.util.Optional;


/**
 * Spring Data JPA repository for the AppConf entity.
 */
@SuppressWarnings("unused")
@Repository
public interface AppConfRepository extends JpaRepository<AppConf,Long> {
    AppConf findOneByAppKey(String appKey);
}
