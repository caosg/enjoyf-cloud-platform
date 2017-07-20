package com.enjoyf.platform.messageservice.repository.jpa;

import com.enjoyf.platform.messageservice.domain.PushProfileDevice;
import org.springframework.stereotype.Repository;

import org.springframework.data.jpa.repository.*;


/**
 * Spring Data JPA repository for the PushProfileDevice entity.
 */
public interface PushProfileDeviceRepository extends JpaRepository<PushProfileDevice, Long> {

    PushProfileDevice findOneByUidAndAppkey(Long uid, String appkey);

    void deleteByUidAndAppkey(Long uid, String appkey);
}
