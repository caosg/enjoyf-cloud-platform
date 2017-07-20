package com.enjoyf.platform.messageservice.service;

import com.enjoyf.platform.messageservice.domain.AppUserMessageSum;
import com.enjoyf.platform.messageservice.domain.AppUserMessage;
import com.enjoyf.platform.messageservice.service.dto.AppUserMessageDTO;
import com.enjoyf.platform.page.ScoreRange;
import com.enjoyf.platform.page.ScoreRangeRows;

import java.util.List;
import java.util.Set;

/**
 * Service Interface for managing AppUserMessage.
 */
public interface AppUserMessageService {

    /**
     * Save a appUserMessage.
     *
     * @param appUserMessage the entity to save
     * @return the persisted entity
     */
    AppUserMessage save(AppUserMessage appUserMessage);


    ScoreRangeRows<AppUserMessageDTO> findByUidAppkey(long uid, String appkey, ScoreRange scoreRange);

    /**
     * Get the "id" appMessage.
     *
     * @param id the id of the entity
     * @return the entity
     */
    AppUserMessage findOne(Long id);


    /**
     * read appmessage
     *
     * @param id
     * @return
     */
    boolean readAppMessage(Long uid,Long id);

    /**
     * Delete the "id" appMessage. modify valid status unvalid(0)
     *
     * @param id the id of the entity
     */
    boolean delete(Long id,Long uid);

    List<AppUserMessageDTO> buildWikiAppUserMessageDTO(Set<AppUserMessage> appUserMessages);


    AppUserMessageSum getAppMessageSum(String appkey, Long uid);
}
