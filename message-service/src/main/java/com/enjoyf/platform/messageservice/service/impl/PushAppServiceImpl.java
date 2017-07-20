package com.enjoyf.platform.messageservice.service.impl;

import com.enjoyf.platform.messageservice.domain.PushApp;
import com.enjoyf.platform.messageservice.repository.jpa.PushAppRepository;
import com.enjoyf.platform.messageservice.repository.redis.RedisPushAppRepository;
import com.enjoyf.platform.messageservice.service.PushAppService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

/**
 * Service Implementation for managing PushApp.
 */
@Service
@Transactional
public class PushAppServiceImpl implements PushAppService {

    private final Logger log = LoggerFactory.getLogger(PushAppServiceImpl.class);

    private final PushAppRepository pushAppRepository;
    private final RedisPushAppRepository redisPushAppRepository;


    public PushAppServiceImpl(PushAppRepository pushAppRepository, RedisPushAppRepository redisPushAppRepository) {
        this.pushAppRepository = pushAppRepository;
        this.redisPushAppRepository = redisPushAppRepository;
    }

    /**
     * Save a pushApp.
     *
     * @param pushApp the entity to save
     * @return the persisted entity
     */
    @Override
    public PushApp save(PushApp pushApp) {
        log.debug("Request to save PushApp : {}", pushApp);

        pushApp = pushAppRepository.save(pushApp);

        redisPushAppRepository.save(pushApp);


        return pushApp;
    }

    /**
     * Get all the pushApps.
     *
     * @param pageable the pagination information
     * @return the list of entities
     */
    @Override
    public Page<PushApp> findAll(Pageable pageable) {
        log.debug("Request to get all PushApps");
        Page<PushApp> result = pushAppRepository.findAll(pageable);
        return result;
    }



    @Override
    public PushApp findOneByAppKey(String appkey) {
        log.debug("Request to get PushApp : {}", appkey);

        PushApp pushApp = redisPushAppRepository.findOneByAppkey(appkey);
        if (pushApp == null) {
            Optional<PushApp> pushAppOptional = pushAppRepository.findOneByAppkey(appkey);
            pushAppOptional.ifPresent(redisPushAppRepository::save);
            pushApp = pushAppOptional.get();
        }

        return pushApp;
    }

    /**
     * Delete the  pushApp by appKey.
     *
     * @param appKey the id of the entity
     */
    @Override
    public void delete(String appKey) {
        log.debug("Request to delete PushApp : {}", appKey);
        Long id=pushAppRepository.deleteByAppkey(appKey);

        if (id!=null && id >0) {
            redisPushAppRepository.delete(id);
        }

    }
}
