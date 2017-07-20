package com.enjoyf.platform.messageservice.repository.jpa;

import com.enjoyf.platform.messageservice.domain.PushApp;
import org.springframework.cache.annotation.CacheConfig;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;


/**
 * Spring Data JPA repository for the PushProfileDeviceDTO entity.
 */
@SuppressWarnings("unused")
@CacheConfig(cacheNames = "pushApps")
public interface PushAppRepository extends JpaRepository<PushApp, Long> {

    Optional<PushApp> findOneByAppkey(String appkey);

    Long deleteByAppkey(String appkey);
}
