package com.enjoyf.platform.contentservice.web.rest;

import com.codahale.metrics.annotation.Timed;
import com.enjoyf.platform.contentservice.domain.ContentTag;
import com.enjoyf.platform.contentservice.domain.enumeration.ContentTagLine;
import com.enjoyf.platform.contentservice.service.ContentTagService;
import com.enjoyf.platform.contentservice.web.rest.util.HeaderUtil;
import com.enjoyf.platform.contentservice.web.rest.util.PaginationUtil;
import com.enjoyf.platform.contentservice.web.rest.vm.ContentTagVM;
import io.github.jhipster.web.util.ResponseUtil;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.apache.commons.lang.StringUtils;
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
import java.util.stream.Collectors;

/**
 * REST controller for managing ContentTag.
 */
@RestController
@RequestMapping("/api")
public class ContentTagResource {

    private final Logger log = LoggerFactory.getLogger(ContentTagResource.class);

    private static final String ENTITY_NAME = "contentTag";

    private final ContentTagService contentTagService;

    public ContentTagResource(ContentTagService contentTagService) {
        this.contentTagService = contentTagService;
    }

    /**
     * POST  /content-tags : Create a new contentTag.
     *
     * @param contentTag the contentTag to create
     * @return the ResponseEntity with status 201 (Created) and with body the new contentTag, or with status 400 (Bad Request) if the contentTag has already an ID
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PostMapping("/content-tags")
    @Timed
    public ResponseEntity<ContentTag> createContentTag(@RequestBody ContentTag contentTag) throws URISyntaxException {
        log.debug("REST request to save ContentTag : {}", contentTag);
        if (contentTag.getId() != null) {
            return ResponseEntity.badRequest().headers(HeaderUtil.createFailureAlert(ENTITY_NAME, "idexists", "A new contentTag cannot already have an ID")).body(null);
        }
        ContentTag result = contentTagService.save(contentTag);
        return ResponseEntity.created(new URI("/api/content-tags/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * PUT  /content-tags : Updates an existing contentTag.
     *
     * @return the ResponseEntity with status 200 (OK) and with body the updated contentTag,
     * or with status 400 (Bad Request) if the contentTag is not valid,
     * or with status 500 (Internal Server Error) if the contentTag couldnt be updated
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PutMapping("/content-tags")
    @Timed
    public ResponseEntity<ContentTag> updateContentTag(@RequestParam(value = "id", required = true) Long id,
                                                       @RequestParam(value = "name", required = false) String name,
                                                       @RequestParam(value = "target", required = false) String target,
                                                       @RequestParam(value = "status", required = false) String status
    ) throws URISyntaxException {
        log.debug("REST request to update ContentTag : {}", id);
        ContentTag result = contentTagService.findOne(id);
        if (result != null) {
            if (!StringUtils.isEmpty(name)) {
                result.setName(name);
            }
            if (!StringUtils.isEmpty(target)) {
                result.setTarget(target);
            }
            if (!StringUtils.isEmpty(status)) {
                result.setValidStatus(status);
            }

            result = contentTagService.save(result);
        }
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(ENTITY_NAME, result.getId().toString()))
            .body(result);
    }


    @PutMapping("/content-tags-sort")
    @Timed
    public ResponseEntity<Boolean> updateContentTagSort(@RequestParam(value = "id", required = true) Long id,
                                                        @RequestParam(value = "otherid", required = false) Long otherid
    ) throws URISyntaxException {
        log.debug("REST request to update ContentTag : {}", id);
        ContentTag result = contentTagService.findOne(id);
        ContentTag otherResult = contentTagService.findOne(otherid);
        if (result != null && otherResult != null) {
            long displayOrder = result.getDisplayOrder();
            long otherDisplayOrder = otherResult.getDisplayOrder();
            result.setDisplayOrder(otherDisplayOrder);
            otherResult.setDisplayOrder(displayOrder);
            contentTagService.save(result);
            contentTagService.save(otherResult);
        }
        return ResponseEntity.ok(true);
    }

    /**
     * GET  /content-tags : get all the contentTags.
     *
     * @return the ResponseEntity with status 200 (OK) and the list of contentTags in body
     */

    @GetMapping("/content-tags")
    @Timed
    public ResponseEntity<List<ContentTag>> getAllContentTags(@ApiParam Pageable pageable,
                                                              @RequestParam(value = "tagline", required = true) String tagline) {
        log.debug("REST request to get a page of Players");
        Page<ContentTag> page = contentTagService.findAllByTagLine(tagline, pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(page, "/api/content-tags");
        return new ResponseEntity<>(page.getContent(), headers, HttpStatus.OK);
    }


    @GetMapping(value = "/contenttags")
    @Timed
    @ApiOperation(value = "修改状态，删除或者恢复", response = Boolean.class)
    public List<ContentTagVM> getAllContentTagsByApp() {
        log.debug("REST request to get all ContentTags");
        return contentTagService.queryContentTagByTagLine(ContentTagLine.RECOMMEND).
            stream().map(ContentTagVM::new).
            collect(Collectors.toList());
    }


    /**
     * GET  /content-tags/:id : get the "id" contentTag.
     *
     * @param id the id of the contentTag to retrieve
     * @return the ResponseEntity with status 200 (OK) and with body the contentTag, or with status 404 (Not Found)
     */
    @GetMapping("/content-tags/{id}")
    @Timed
    public ResponseEntity<ContentTag> getContentTag(@PathVariable Long id) {
        log.debug("REST request to get ContentTag : {}", id);
        ContentTag contentTag = contentTagService.findOne(id);
        return ResponseUtil.wrapOrNotFound(Optional.ofNullable(contentTag));
    }

    /**
     * DELETE  /content-tags/:id : delete the "id" contentTag.
     *
     * @param id the id of the contentTag to delete
     * @return the ResponseEntity with status 200 (OK)
     */
    @DeleteMapping("/content-tags/{id}")
    @Timed
    public ResponseEntity<Void> deleteContentTag(@PathVariable Long id) {
        log.debug("REST request to delete ContentTag : {}", id);
        contentTagService.delete(id);
        return ResponseEntity.ok().headers(HeaderUtil.createEntityDeletionAlert(ENTITY_NAME, id.toString())).build();
    }

}
