package com.enjoyf.platform.messageservice.repository.jpa;

import com.enjoyf.platform.common.domain.enumeration.ValidStatus;
import com.enjoyf.platform.messageservice.domain.AppPushMessage;
import com.enjoyf.platform.messageservice.domain.enumration.SendType;
import org.springframework.stereotype.Repository;

import org.springframework.data.jpa.repository.*;

import java.time.ZonedDateTime;
import java.util.List;
import java.util.Optional;


/**
 * Spring Data JPA repository for the AppPushMessage entity.
 */
@SuppressWarnings("unused")
@Repository
public interface AppPushMessageRepository extends JpaRepository<AppPushMessage, Long>, JpaSpecificationExecutor {


    List<AppPushMessage> findAllBySendTimeBeforeAndSendTypeAndSendStatusAndRemoveStatus(ZonedDateTime sendTime,
                                                                                      SendType sendType,
                                                                                      ValidStatus sendStatus,
                                                                                      ValidStatus removeStatus);

}
