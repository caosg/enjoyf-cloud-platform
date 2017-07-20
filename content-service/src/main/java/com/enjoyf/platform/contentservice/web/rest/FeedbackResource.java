package com.enjoyf.platform.contentservice.web.rest;

import com.codahale.metrics.annotation.Timed;
import com.enjoyf.platform.autoconfigure.context.CommonContextHolder;
import com.enjoyf.platform.autoconfigure.security.EnjoySecurityUtils;
import com.enjoyf.platform.autoconfigure.web.error.CustomParameterizedException;
import com.enjoyf.platform.common.domain.enumeration.ValidStatus;
import com.enjoyf.platform.contentservice.domain.Feedback;
import com.enjoyf.platform.contentservice.domain.enumeration.FeedbackType;
import com.enjoyf.platform.contentservice.service.FeedbackService;
import com.enjoyf.platform.contentservice.web.rest.util.HeaderUtil;
import com.enjoyf.platform.contentservice.web.rest.util.PaginationUtil;
import com.google.common.base.Strings;
import io.swagger.annotations.ApiParam;
import io.github.jhipster.web.util.ResponseUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.net.URI;
import java.net.URISyntaxException;

import java.time.ZonedDateTime;
import java.util.List;
import java.util.Optional;

/**
 * REST controller for managing Feedback.
 */
@RestController
@RequestMapping("/api")
public class FeedbackResource {

    private final Logger log = LoggerFactory.getLogger(FeedbackResource.class);

    private static final String ENTITY_NAME = "feedback";

    private final FeedbackService feedbackService;

    public FeedbackResource(FeedbackService feedbackService) {
        this.feedbackService = feedbackService;
    }

    /**
     * POST  /feedbacks : Create a new feedback.
     *
     * @param feedback the feedback to create
     * @return the ResponseEntity with status 201 (Created) and with body the new feedback, or with status 400 (Bad Request) if the feedback has already an ID
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PostMapping("/feedbacks")
    @Timed
    public ResponseEntity<Feedback> createFeedback(@RequestBody Feedback feedback) throws URISyntaxException {
        log.debug("REST request to save Feedback : {}", feedback);
        if (feedback.getId() != null) {
            return ResponseEntity.badRequest().headers(HeaderUtil.createFailureAlert(ENTITY_NAME, "idexists", "A new feedback cannot already have an ID")).body(null);
        }
        String appkey = CommonContextHolder.getContext().getCommonParams().getAppkey();
        Long uid = EnjoySecurityUtils.getCurrentUid();
        if (Strings.isNullOrEmpty(appkey) || uid == 0) {
            throw new CustomParameterizedException("param.empty", "appkey", "uid");
        }

        feedback.setAppkey(appkey);
        feedback.setUid(uid);
        feedback.setCreateTime(ZonedDateTime.now());
        Feedback result = feedbackService.save(feedback);
        return ResponseEntity.created(new URI("/api/feedbacks/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * PUT  /feedbacks : Updates an existing feedback.
     *
     * @param  id
     * @return the ResponseEntity with status 200 (OK) and with body the updated feedback,
     * or with status 400 (Bad Request) if the feedback is not valid,
     * or with status 500 (Internal Server Error) if the feedback couldnt be updated
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PutMapping("/feedbacks/{id}/status")
    @Timed
    public ResponseEntity<String> updateFeedback(@PathVariable Long id, @RequestParam ValidStatus status) throws URISyntaxException {
        log.debug("REST request to update Feedback : {},{}", id, status);

        boolean bval = feedbackService.updateStatus(status, id);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(ENTITY_NAME, id.toString()))
            .body(bval ? "success" : "failed");
    }

    /**
     * GET  /feedbacks : get all the feedbacks.
     *
     * @param pageable the pagination information
     * @return the ResponseEntity with status 200 (OK) and the list of feedbacks in body
     */
    @GetMapping("/feedbacks")
    @Timed
    public ResponseEntity<List<Feedback>> getAllFeedbacks(
        @RequestParam(required = false) FeedbackType feedbackType,
        @RequestParam(required = false) ValidStatus status,
        @ApiParam Pageable pageable) {
        log.debug("REST request to get a page of Feedbacks");
        Page<Feedback> page = feedbackService.findAll(feedbackType,status,pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(page, "/api/feedbacks");
        return new ResponseEntity<>(page.getContent(), headers, HttpStatus.OK);
    }

    /**
     * GET  /feedbacks/:id : get the "id" feedback.
     *
     * @param id the id of the feedback to retrieve
     * @return the ResponseEntity with status 200 (OK) and with body the feedback, or with status 404 (Not Found)
     */
    @GetMapping("/feedbacks/{id}")
    @Timed
    public ResponseEntity<Feedback> getFeedback(@PathVariable Long id) {
        log.debug("REST request to get Feedback : {}", id);
        Feedback feedback = feedbackService.findOne(id);
        return ResponseUtil.wrapOrNotFound(Optional.ofNullable(feedback));
    }

    /**
     * DELETE  /feedbacks/:id : delete the "id" feedback.
     *
     * @param id the id of the feedback to delete
     * @return the ResponseEntity with status 200 (OK)
     */
    @DeleteMapping("/feedbacks/{id}")
    @Timed
    public ResponseEntity<Void> deleteFeedback(@PathVariable Long id) {
        log.debug("REST request to delete Feedback : {}", id);
        feedbackService.delete(id);
        return ResponseEntity.ok().headers(HeaderUtil.createEntityDeletionAlert(ENTITY_NAME, id.toString())).build();
    }

}
