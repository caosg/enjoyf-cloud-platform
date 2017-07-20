package com.enjoyf.platform.messageservice.service;

import com.enjoyf.platform.messageservice.domain.PushApp;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

/**
 * Service Interface for managing PushApp.
 */
public interface PushAppService {

    /**
     * Save a pushApp.
     *
     * @param pushAppDTO the entity to save
     * @return the persisted entity
     */
    PushApp save(PushApp pushAppDTO);

    /**
     * Get all the pushApps.
     *
     * @param pageable the pagination information
     * @return the list of entities
     */
    Page<PushApp> findAll(Pageable pageable);



    PushApp findOneByAppKey(String appkey);

    /**
     * Delete the "appKey" pushApp.
     *
     * @param appKey the id of the entity
     */
    void delete(String appKey);
}
