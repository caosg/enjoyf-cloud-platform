package com.enjoyf.platform.contentservice.service;

import com.enjoyf.platform.common.domain.enumeration.ValidStatus;
import com.enjoyf.platform.contentservice.domain.Feedback;
import com.enjoyf.platform.contentservice.domain.enumeration.FeedbackType;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

/**
 * Service Interface for managing Feedback.
 */
public interface FeedbackService {

    /**
     * Save a feedback.
     *
     * @param feedback the entity to save
     * @return the persisted entity
     */
    Feedback save(Feedback feedback);

    /**
     * 修改状态
     * @param validStatus
     * @param id
     * @return
     */
    boolean updateStatus(ValidStatus validStatus,Long id);

    /**
     *  Get all the feedbacks.
     *
     *  @param pageable the pagination information
     *  @return the list of entities
     */
    Page<Feedback> findAll(FeedbackType feedbackType, ValidStatus status, Pageable pageable);

    /**
     *  Get the "id" feedback.
     *
     *  @param id the id of the entity
     *  @return the entity
     */
    Feedback findOne(Long id);

    /**
     *  Delete the "id" feedback.
     *
     *  @param id the id of the entity
     */
    void delete(Long id);
}
