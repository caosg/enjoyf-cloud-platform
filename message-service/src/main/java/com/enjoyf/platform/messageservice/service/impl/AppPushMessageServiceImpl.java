package com.enjoyf.platform.messageservice.service.impl;

import com.enjoyf.platform.common.domain.enumeration.ValidStatus;
import com.enjoyf.platform.event.message.enumration.PushType;
import com.enjoyf.platform.messageservice.domain.AppPushMessage;
import com.enjoyf.platform.messageservice.domain.enumration.SendType;
import com.enjoyf.platform.messageservice.repository.jpa.AppPushMessageRepository;
import com.enjoyf.platform.messageservice.service.AppPushMessageService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;

import javax.persistence.criteria.Predicate;
import java.time.ZonedDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

/**
 * Service Implementation for managing AppPushMessage.
 */
@Service
public class AppPushMessageServiceImpl implements AppPushMessageService {

    private final Logger log = LoggerFactory.getLogger(AppPushMessageServiceImpl.class);

    private final AppPushMessageRepository appPushMessageRepository;


    public AppPushMessageServiceImpl(AppPushMessageRepository appPushMessageRepository) {
        this.appPushMessageRepository = appPushMessageRepository;
    }

    /**
     * Save a appPushMessage.
     *
     * @param appPushMessage the entity to save
     * @return the persisted entity
     */
    @Override
    public AppPushMessage save(AppPushMessage appPushMessage) {
        log.debug("Request to save AppPushMessage : {}", appPushMessage);
        AppPushMessage result = appPushMessageRepository.save(appPushMessage);
        return result;
    }

    /**
     * Get all the appPushMessages.
     *
     * @param pageable the pagination information
     * @return the list of entities
     */
    @Override
    public Page<AppPushMessage> findAll(ValidStatus sendStatus, ValidStatus removeStatus, PushType pushType, SendType sendType, Pageable pageable) {
        log.debug("Request to get all AppPushMessages");
        Page<AppPushMessage> result = appPushMessageRepository.findAll(where(sendStatus, removeStatus, pushType, sendType), pageable);
        return result;
    }


    private Specification<AppPushMessage> where(ValidStatus sendStatus, ValidStatus removeStatus, PushType pushType, SendType sendType) {
        return (root, criteriaQuery, criteriaBuilder) -> {
            List<Predicate> predicates = new ArrayList<>();

            if (sendStatus != null) {
                predicates.add(criteriaBuilder.equal(root.get("sendStatus"), sendStatus));
            }
            if (removeStatus != null) {
                predicates.add(criteriaBuilder.equal(root.get("removeStatus"), removeStatus));
            }
            if (pushType != null) {
                predicates.add(criteriaBuilder.equal(root.get("pushType"), pushType));
            }
            if (sendType != null) {
                predicates.add(criteriaBuilder.equal(root.get("sendType"), sendType));
            }

            log.info("----search where sendStatus:{}, removeStatus:{},pushType:{},sendType:{}", sendStatus, removeStatus, pushType, sendType);
            return criteriaQuery.where(predicates.toArray(new Predicate[predicates.size()])).getRestriction();
        };
    }

    /**
     * Get one appPushMessage by id.
     *
     * @param id the id of the entity
     * @return the entity
     */
    @Override
    public AppPushMessage findOne(Long id) {
        log.debug("Request to get AppPushMessage : {}", id);
        AppPushMessage appPushMessage = appPushMessageRepository.findOne(id);
        return appPushMessage;
    }

    /**
     * Delete the  appPushMessage by id.
     *
     * @param id the id of the entity
     */
    @Override
    public boolean delete(Long id) {
        log.debug("Request to delete AppPushMessage : {}", id);
        AppPushMessage appPushMessage = findOne(id);
        if (appPushMessage == null) {
            return false;
        }

        appPushMessage.setRemoveStatus(ValidStatus.VALID);
        appPushMessageRepository.save(appPushMessage);
        return true;
    }

    @Override
    public boolean modifySendSatus(Long id) {
        log.debug("Request to modifySendStatus AppPushMessage : {}", id);
        AppPushMessage appPushMessage = findOne(id);
        if (appPushMessage == null) {
            return false;
        }

        appPushMessage.setSendStatus(ValidStatus.VALID);
        appPushMessageRepository.save(appPushMessage);
        return true;
    }

    @Override
    public List<AppPushMessage> findAllSubscribeMessageList(ZonedDateTime now, SendType sendType, ValidStatus sendStatus, ValidStatus removeStatus) {
        return appPushMessageRepository.findAllBySendTimeBeforeAndSendTypeAndSendStatusAndRemoveStatus(
            now,
            sendType,
            sendStatus,
            removeStatus
        );

    }
}
