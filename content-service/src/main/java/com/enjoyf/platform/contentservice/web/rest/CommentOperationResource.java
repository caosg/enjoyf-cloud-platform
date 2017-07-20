package com.enjoyf.platform.contentservice.web.rest;

import com.codahale.metrics.annotation.Timed;
import com.enjoyf.platform.contentservice.domain.CommentOperation;
import com.enjoyf.platform.contentservice.service.CommentOperationService;
import com.enjoyf.platform.contentservice.web.rest.util.HeaderUtil;
import com.enjoyf.platform.contentservice.web.rest.util.PaginationUtil;
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
import java.util.List;
import java.util.Optional;

/**
 * REST controller for managing CommentOperation.
 */
@RestController
@RequestMapping("/api")
public class CommentOperationResource {

    private final Logger log = LoggerFactory.getLogger(CommentOperationResource.class);

    private static final String ENTITY_NAME = "commentOperation";

    private final CommentOperationService commentOperationService;

    public CommentOperationResource(CommentOperationService commentOperationService) {
        this.commentOperationService = commentOperationService;
    }

//    /**
//     * POST  /comment-operations : Create a new commentOperation.
//     *
//     * @param commentOperation the commentOperation to create
//     * @return the ResponseEntity with status 201 (Created) and with body the new commentOperation, or with status 400 (Bad Request) if the commentOperation has already an ID
//     * @throws URISyntaxException if the Location URI syntax is incorrect
//     */
//    @PostMapping("/comment-operations")
//    @Timed
//    public ResponseEntity<CommentOperation> createCommentOperation(@RequestBody CommentOperation commentOperation) throws URISyntaxException {
//        log.debug("REST request to save CommentOperation : {}", commentOperation);
//        if (commentOperation.getId() != null) {
//            return ResponseEntity.badRequest().headers(HeaderUtil.createFailureAlert(ENTITY_NAME, "idexists", "A new commentOperation cannot already have an ID")).body(null);
//        }
//        CommentOperation result = commentOperationService.save(commentOperation);
//        return ResponseEntity.created(new URI("/api/comment-operations/" + result.getId()))
//            .headers(HeaderUtil.createEntityCreationAlert(ENTITY_NAME, result.getId().toString()))
//            .body(result);
//    }
//
//    /**
//     * PUT  /comment-operations : Updates an existing commentOperation.
//     *
//     * @param commentOperation the commentOperation to update
//     * @return the ResponseEntity with status 200 (OK) and with body the updated commentOperation,
//     * or with status 400 (Bad Request) if the commentOperation is not valid,
//     * or with status 500 (Internal Server Error) if the commentOperation couldnt be updated
//     * @throws URISyntaxException if the Location URI syntax is incorrect
//     */
//    @PutMapping("/comment-operations")
//    @Timed
//    public ResponseEntity<CommentOperation> updateCommentOperation(@RequestBody CommentOperation commentOperation) throws URISyntaxException {
//        log.debug("REST request to update CommentOperation : {}", commentOperation);
//        if (commentOperation.getId() == null) {
//            return createCommentOperation(commentOperation);
//        }
//        CommentOperation result = commentOperationService.save(commentOperation);
//        return ResponseEntity.ok()
//            .headers(HeaderUtil.createEntityUpdateAlert(ENTITY_NAME, commentOperation.getId().toString()))
//            .body(result);
//    }
//
//    /**
//     * GET  /comment-operations : get all the commentOperations.
//     *
//     * @param pageable the pagination information
//     * @return the ResponseEntity with status 200 (OK) and the list of commentOperations in body
//     */
//    @GetMapping("/comment-operations")
//    @Timed
//    public ResponseEntity<List<CommentOperation>> getAllCommentOperations(@ApiParam Pageable pageable) {
//        log.debug("REST request to get a page of CommentOperations");
//        Page<CommentOperation> page = commentOperationService.findAll(pageable);
//        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(page, "/api/comment-operations");
//        return new ResponseEntity<>(page.getContent(), headers, HttpStatus.OK);
//    }
//
//    /**
//     * GET  /comment-operations/:id : get the "id" commentOperation.
//     *
//     * @param id the id of the commentOperation to retrieve
//     * @return the ResponseEntity with status 200 (OK) and with body the commentOperation, or with status 404 (Not Found)
//     */
//    @GetMapping("/comment-operations/{id}")
//    @Timed
//    public ResponseEntity<CommentOperation> getCommentOperation(@PathVariable Long id) {
//        log.debug("REST request to get CommentOperation : {}", id);
//        CommentOperation commentOperation = commentOperationService.findOne(id);
//        return ResponseUtil.wrapOrNotFound(Optional.ofNullable(commentOperation));
//    }
//
//    /**
//     * DELETE  /comment-operations/:id : delete the "id" commentOperation.
//     *
//     * @param id the id of the commentOperation to delete
//     * @return the ResponseEntity with status 200 (OK)
//     */
//    @DeleteMapping("/comment-operations/{id}")
//    @Timed
//    public ResponseEntity<Void> deleteCommentOperation(@PathVariable Long id) {
//        log.debug("REST request to delete CommentOperation : {}", id);
//        commentOperationService.delete(id);
//        return ResponseEntity.ok().headers(HeaderUtil.createEntityDeletionAlert(ENTITY_NAME, id.toString())).build();
//    }

}
