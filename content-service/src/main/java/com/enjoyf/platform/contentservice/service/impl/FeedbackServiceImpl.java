package com.enjoyf.platform.contentservice.service.impl;

import com.enjoyf.platform.common.domain.enumeration.ValidStatus;
import com.enjoyf.platform.contentservice.domain.Feedback;
import com.enjoyf.platform.contentservice.domain.enumeration.FeedbackType;
import com.enjoyf.platform.contentservice.repository.jpa.FeedbackRepository;
import com.enjoyf.platform.contentservice.service.FeedbackService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.StringUtils;

import javax.persistence.criteria.Predicate;
import java.time.ZoneId;
import java.time.ZonedDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;

/**
 * Service Implementation for managing Feedback.
 */
@Service
public class FeedbackServiceImpl implements FeedbackService {

    private final Logger log = LoggerFactory.getLogger(FeedbackServiceImpl.class);

    private final FeedbackRepository feedbackRepository;

    public FeedbackServiceImpl(FeedbackRepository feedbackRepository) {
        this.feedbackRepository = feedbackRepository;
    }

    /**
     * Save a feedback.
     *
     * @param feedback the entity to save
     * @return the persisted entity
     */
    @Override
    public Feedback save(Feedback feedback) {
        log.debug("Request to save Feedback : {}", feedback);
        Feedback result = feedbackRepository.save(feedback);
        return result;
    }

    @Override
    public boolean updateStatus(ValidStatus validStatus, Long id) {
        log.debug("Request to save Feedback : {},{}", validStatus, id);
        Feedback feedback = feedbackRepository.getOne(id);

        if (feedback == null || feedback.getStatus().equals(validStatus)) {
            return false;
        }

        feedback.setStatus(validStatus);
        feedbackRepository.save(feedback);
        return true;
    }

    /**
     * Get all the feedbacks.
     *
     * @param pageable the pagination information
     * @return the list of entities
     */
    @Override
    public Page<Feedback> findAll(FeedbackType feedbackType, ValidStatus status,Pageable pageable) {
        log.debug("Request to get all Feedbacks");
        Page<Feedback> result = feedbackRepository.findAll(where(feedbackType,status),pageable);
        return result;
    }

    private Specification<Feedback> where(FeedbackType feedbackType, ValidStatus status) {
        return (root, criteriaQuery, criteriaBuilder) -> {
            List<Predicate> predicates = new ArrayList<>();

            if (feedbackType != null) {
                predicates.add(criteriaBuilder.equal(root.get("feedbackType"), feedbackType));
            }
            if (status != null) {
                predicates.add(criteriaBuilder.equal(root.get("status"), status));
            }

            log.info("----search where feedbakType:{}, status:{}", feedbackType, status);
            return criteriaQuery.where(predicates.toArray(new Predicate[predicates.size()])).getRestriction();
        };
    }

    /**
     * Get one feedback by id.
     *
     * @param id the id of the entity
     * @return the entity
     */
    @Override
    public Feedback findOne(Long id) {
        log.debug("Request to get Feedback : {}", id);
        Feedback feedback = feedbackRepository.findOne(id);
        return feedback;
    }

    /**
     * Delete the  feedback by id.
     *
     * @param id the id of the entity
     */
    @Override
    public void delete(Long id) {
        log.debug("Request to delete Feedback : {}", id);
        feedbackRepository.delete(id);
    }
}
