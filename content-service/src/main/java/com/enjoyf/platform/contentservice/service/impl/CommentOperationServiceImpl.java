package com.enjoyf.platform.contentservice.service.impl;

import com.enjoyf.platform.contentservice.service.CommentOperationService;
import com.enjoyf.platform.contentservice.domain.CommentOperation;
import com.enjoyf.platform.contentservice.repository.jpa.CommentOperationRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.stereotype.Service;

/**
 * Service Implementation for managing CommentOperation.
 */
@Service
@Transactional
public class CommentOperationServiceImpl implements CommentOperationService{

    private final Logger log = LoggerFactory.getLogger(CommentOperationServiceImpl.class);

    private final CommentOperationRepository commentOperationRepository;

    public CommentOperationServiceImpl(CommentOperationRepository commentOperationRepository) {
        this.commentOperationRepository = commentOperationRepository;
    }

    /**
     * Save a commentOperation.
     *
     * @param commentOperation the entity to save
     * @return the persisted entity
     */
    @Override
    public CommentOperation save(CommentOperation commentOperation) {
        log.debug("Request to save CommentOperation : {}", commentOperation);
        CommentOperation result = commentOperationRepository.save(commentOperation);
        return result;
    }

    /**
     *  Get all the commentOperations.
     *
     *  @param pageable the pagination information
     *  @return the list of entities
     */
    @Override
    @Transactional(readOnly = true)
    public Page<CommentOperation> findAll(Pageable pageable) {
        log.debug("Request to get all CommentOperations");
        Page<CommentOperation> result = commentOperationRepository.findAll(pageable);
        return result;
    }

    /**
     *  Get one commentOperation by id.
     *
     *  @param id the id of the entity
     *  @return the entity
     */
    @Override
    @Transactional(readOnly = true)
    public CommentOperation findOne(Long id) {
        log.debug("Request to get CommentOperation : {}", id);
        CommentOperation commentOperation = commentOperationRepository.findOne(id);
        return commentOperation;
    }

    /**
     *  Delete the  commentOperation by id.
     *
     *  @param id the id of the entity
     */
    @Override
    public void delete(Long id) {
        log.debug("Request to delete CommentOperation : {}", id);
        commentOperationRepository.delete(id);
    }
}
