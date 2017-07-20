package com.enjoyf.platform.contentservice.service;

import com.enjoyf.platform.contentservice.domain.contentsum.ContentSum;
import com.enjoyf.platform.contentservice.domain.contentsum.ContentSumType;

import java.util.Collection;
import java.util.List;
import java.util.Map;
import java.util.Set;

/**
 * Service Interface for managing ContentSum.
 */
public interface ContentSumService {

    /**
     * Save a contentSum.
     *
     * @param contentSum the entity to save
     * @return the persisted entity
     */
    ContentSum save(ContentSum contentSum);

    /**
     * Get all the contentSums.
     *
     * @return the list of entities
     */
    List<ContentSum> findAll();

    /**
     * Get the "id" contentSum.
     *
     * @param id the id of the entity
     * @return the entity
     */
    ContentSum findOne(Long id);

    /**
     * Delete the "id" contentSum.
     *
     * @param id the id of the entity
     */
    void delete(Long id);

    boolean addContentSum(Long contetId, ContentSumType contentSumType, String profileId);

    Map<Long, ContentSum> queryContentSumByids(Set<Long> contentIds);
}
