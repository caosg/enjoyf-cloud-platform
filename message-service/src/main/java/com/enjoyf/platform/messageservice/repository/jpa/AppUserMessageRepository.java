package com.enjoyf.platform.messageservice.repository.jpa;

import com.enjoyf.platform.common.domain.enumeration.ValidStatus;
import com.enjoyf.platform.messageservice.domain.AppUserMessage;
import org.springframework.stereotype.Repository;

import org.springframework.data.jpa.repository.*;


/**
 * Spring Data JPA repository for the AppUserMessage entity.
 */
@SuppressWarnings("unused")
@Repository
public interface AppUserMessageRepository extends JpaRepository<AppUserMessage, Long> {

    AppUserMessage findOneByIdAndValidStatus(Long id, ValidStatus validStatus);
}
