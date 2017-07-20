package com.enjoyf.platform.contentservice.service;

import com.enjoyf.platform.contentservice.domain.CommentOperation;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import java.util.List;

/**
 * Service Interface for managing CommentOperation.
 */
public interface CommentOperationService {

    /**
     * Save a commentOperation.
     *
     * @param commentOperation the entity to save
     * @return the persisted entity
     */
    CommentOperation save(CommentOperation commentOperation);

    /**
     *  Get all the commentOperations.
     *  
     *  @param pageable the pagination information
     *  @return the list of entities
     */
    Page<CommentOperation> findAll(Pageable pageable);

    /**
     *  Get the "id" commentOperation.
     *
     *  @param id the id of the entity
     *  @return the entity
     */
    CommentOperation findOne(Long id);

    /**
     *  Delete the "id" commentOperation.
     *
     *  @param id the id of the entity
     */
    void delete(Long id);
}
