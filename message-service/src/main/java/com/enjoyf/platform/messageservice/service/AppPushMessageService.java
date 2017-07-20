package com.enjoyf.platform.messageservice.service;

import com.enjoyf.platform.common.domain.enumeration.ValidStatus;
import com.enjoyf.platform.event.message.MessageAppPushEvent;
import com.enjoyf.platform.event.message.enumration.PushType;
import com.enjoyf.platform.messageservice.domain.AppPushMessage;
import com.enjoyf.platform.messageservice.domain.enumration.SendType;
import com.enjoyf.platform.messageservice.event.MessageEventProcess;
import com.enjoyf.platform.messageservice.service.mapper.PushMessageMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import java.time.ZonedDateTime;
import java.util.List;
import java.util.Optional;

/**
 * Service Interface for managing AppPushMessage.
 */
public interface AppPushMessageService {

    /**
     * Save a appPushMessage.
     *
     * @param appPushMessage the entity to save
     * @return the persisted entity
     */
    AppPushMessage save(AppPushMessage appPushMessage);

    /**
     *  Get all the appPushMessages.
     *
     *  @param pageable the pagination information
     *  @return the list of entities
     */
    Page<AppPushMessage> findAll(ValidStatus sendStatus, ValidStatus removeStatus, PushType pushType, SendType sendType,Pageable pageable);

    /**
     *  Get the "id" appPushMessage.
     *
     *  @param id the id of the entity
     *  @return the entity
     */
    AppPushMessage findOne(Long id);

    /**
     *  Delete the "id" appPushMessage.
     *
     *  @param id the id of the entity
     */
    boolean delete(Long id);

    boolean modifySendSatus(Long id);

    List<AppPushMessage> findAllSubscribeMessageList(ZonedDateTime now, SendType sendType, ValidStatus sendStatus, ValidStatus removeStatus);

}
