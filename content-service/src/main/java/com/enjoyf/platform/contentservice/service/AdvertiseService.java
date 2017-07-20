package com.enjoyf.platform.contentservice.service;

import com.enjoyf.platform.contentservice.domain.Advertise;
import com.enjoyf.platform.contentservice.web.rest.vm.ContentVM;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;
import java.util.Map;

/**
 * Service Interface for managing Advertise.
 */
public interface AdvertiseService {

    /**
     * Save a advertise.
     *
     * @param advertise the entity to save
     * @return the persisted entity
     */
    Advertise save(Advertise advertise);

    /**
     * Get all the advertises.
     *
     * @return the list of entities
     */
    List<Advertise> findAll();


    /**
     */
    Page<Advertise> findByDomainAndSort(Integer domain, Pageable pageable);

    /**
     * Get the "id" advertise.
     *
     * @param id the id of the entity
     * @return the entity
     */
    Advertise findOne(Long id);

    /**
     * Delete the "id" advertise.
     *
     * @param id the id of the entity
     */
    void delete(Long id);


    Map<Integer, Advertise> getAdMap(String appkey, Integer platform);


    List<ContentVM> queryContentVMeByLineKey(String linekey);
}
