package com.enjoyf.platform.profileservice.service;

import com.enjoyf.platform.profileservice.domain.VertualProfile;
import com.enjoyf.platform.profileservice.web.rest.vm.VertualProfileVM;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;
import java.util.Map;

/**
 * Service Interface for managing VertualProfile.
 */
public interface VertualProfileService {

    /**
     * Save a vertualProfile.
     *
     * @param vertualProfileVM the entity to createVertualProfile
     * @return the persisted entity
     */
    VertualProfile createVertualProfile(VertualProfileVM vertualProfileVM);

    VertualProfile updateVertualProfile(VertualProfile vertualProfile);

    /**
     *  Get all the vertualProfiles.
     *
     *  @param pageable the pagination information
     *  @return the list of entities
     */
    Page<VertualProfile> findAll(Pageable pageable);

    /**
     *  Get the "id" vertualProfile.
     *
     *  @param id the id of the entity
     *  @return the entity
     */
    VertualProfile findOne(Long id);

    List<VertualProfile> findAllByNickLike(String nick);

    /**
     * Get the "ids" vertualProfile.
     * @param ids is array of uid
     * @return
     */
    Map<Long,VertualProfile> findByIds(Long[] ids);

    /**
     *  Delete the "id" vertualProfile.
     *
     *  @param id the id of the entity
     */
    void delete(Long id);
}
