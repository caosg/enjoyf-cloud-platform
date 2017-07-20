package com.enjoyf.platform.profileservice.service;

import com.enjoyf.platform.page.ScoreRange;
import com.enjoyf.platform.page.ScoreRangeRows;
import com.enjoyf.platform.profileservice.domain.VerifyProfile;
import com.enjoyf.platform.profileservice.domain.enumeration.VerifyProfileType;
import com.enjoyf.platform.profileservice.service.dto.VerifyProfileDTO;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.Map;
import java.util.Set;

/**
 * Service Interface for managing VerifyProfile.
 */
public interface VerifyProfileService {

    /**
     * Save a talent.
     *
     * @param verifyProfile the entity to createVertualProfile
     * @return the persisted entity
     */
    VerifyProfile save(VerifyProfile verifyProfile);

    /**
     * Get all the talents.
     *
     * @param pageable the pagination information
     * @return the list of entities
     */
    Page<VerifyProfileDTO> findByVerifyType(VerifyProfileType verifyProfileType, Pageable pageable);

    Page<VerifyProfileDTO> findAll(Pageable pageable);


    /**
     * Get all the talents.
     *
     * @param scoreRange the pagination information
     * @return the list of entities
     */
    ScoreRangeRows<VerifyProfileDTO> findByVerifyType(VerifyProfileType verifyProfileType, ScoreRange scoreRange);

    /**
     * Get the "id" verifyprofile
     *
     * @param id the id of the entity
     * @return the entity
     */
    VerifyProfileDTO findOne(Long id);

    Map<Long,VerifyProfileDTO> findVerifyProfileByIds(Set<Long> uids);


    /**
     * Delete the "id" talent.
     *
     * @param id the id of the entity
     */
    void delete(Long id);
}
