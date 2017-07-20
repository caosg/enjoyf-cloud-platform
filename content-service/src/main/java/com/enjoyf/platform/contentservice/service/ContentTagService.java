package com.enjoyf.platform.contentservice.service;

import com.enjoyf.platform.contentservice.domain.ContentTag;
import com.enjoyf.platform.contentservice.domain.enumeration.ContentTagLine;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;
import java.util.Optional;

/**
 * Service Interface for managing ContentTag.
 */
public interface ContentTagService {

    /**
     * Save a contentTag.
     *
     * @param contentTag the entity to save
     * @return the persisted entity
     */
    ContentTag save(ContentTag contentTag);

    /**
     * Get all the contentTags.
     *
     * @return the list of entities
     */
    Page<ContentTag> findAllByTagLine(String tagLine, Pageable pageable);


    /**
     * Get all the contentTags.
     *
     * @return the list of entities
     */
    List<ContentTag> findAll();


    List<ContentTag> queryContentTagByTagLine(ContentTagLine contentTagLine);

    /**
     * Get the "id" contentTag.
     *
     * @param id the id of the entity
     * @return the entity
     */
    ContentTag findOne(Long id);

    /**
     * Delete the "id" contentTag.
     *
     * @param id the id of the entity
     */
    void delete(Long id);


}
