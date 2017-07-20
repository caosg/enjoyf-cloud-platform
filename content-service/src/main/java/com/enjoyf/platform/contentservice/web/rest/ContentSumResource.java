package com.enjoyf.platform.contentservice.web.rest;

import com.codahale.metrics.annotation.Timed;
import com.enjoyf.platform.common.ResultCodeConstants;
import com.enjoyf.platform.common.WanbaResultCodeConstants;
import com.enjoyf.platform.common.domain.comment.CommentDomain;
import com.enjoyf.platform.common.util.CommentUtil;
import com.enjoyf.platform.common.util.StringUtil;
import com.enjoyf.platform.contentservice.domain.Content;
import com.enjoyf.platform.contentservice.domain.contentsum.ContentSum;
import com.enjoyf.platform.contentservice.domain.contentsum.ContentSumType;
import com.enjoyf.platform.contentservice.service.ContentService;
import com.enjoyf.platform.contentservice.service.ContentSumService;
import com.enjoyf.platform.contentservice.web.rest.util.HTTPUtil;
import com.enjoyf.platform.contentservice.web.rest.util.HeaderUtil;
import io.github.jhipster.web.util.ResponseUtil;
import net.sf.json.JSONObject;
import org.hibernate.service.spi.ServiceException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.List;
import java.util.Optional;

/**
 * REST controller for managing ContentSum.
 */
@RestController
@RequestMapping("/api")
public class ContentSumResource {

    private final Logger log = LoggerFactory.getLogger(ContentSumResource.class);

    private static final String ENTITY_NAME = "contentSum";

    private final ContentSumService contentSumService;
    private final ContentService contentService;

    public ContentSumResource(ContentSumService contentSumService, ContentService contentService) {
        this.contentSumService = contentSumService;
        this.contentService = contentService;
    }

    /**
     * POST  /content-sums : Create a new contentSum.
     *
     * @param contentSum the contentSum to create
     * @return the ResponseEntity with status 201 (Created) and with body the new contentSum, or with status 400 (Bad Request) if the contentSum has already an ID
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PostMapping("/content-sums")
    @Timed
    public ResponseEntity<ContentSum> createContentSum(@RequestBody ContentSum contentSum) throws URISyntaxException {
        log.debug("REST request to save ContentSum : {}", contentSum);
        if (contentSum.getId() != null) {
            return ResponseEntity.badRequest().headers(HeaderUtil.createFailureAlert(ENTITY_NAME, "idexists", "A new contentSum cannot already have an ID")).body(null);
        }
        ContentSum result = contentSumService.save(contentSum);
        return ResponseEntity.created(new URI("/api/content-sums/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    @PostMapping(value = "/content-sums/laud")
    @Timed
    public String laud(HttpServletRequest request) {
        String profileId = HTTPUtil.getParam(request, "pid");
        String archiveid = request.getParameter("archiveid");

        JSONObject jsonObject = ResultCodeConstants.SUCCESS.getJsonObject();
        //check param
        if (StringUtil.isEmpty(archiveid) || StringUtil.isEmpty(profileId)) {
            return "laud([" + ResultCodeConstants.PARAM_EMPTY.getJsonString() + "])";
        }

        try {
            Content content = contentService.findByCommentId(CommentUtil.genCommentId(archiveid, CommentDomain.CMS_COMMENT));
            if (content == null) {
                return "laud([" + ResultCodeConstants.PARAM_EMPTY.getJsonString() + "])";
            }

            boolean bval = contentSumService.addContentSum(content.getId(), ContentSumType.AGREE_NUM, profileId);
            if (!bval) {
                return "laud([" + WanbaResultCodeConstants.WANBA_ASK_ANSWER_AGREE_FAILED.getJsonString() + "])";
            }
        } catch (ServiceException e) {
            log.error(this.getClass().getName() + " occured error.e:", e);
            return "laud([" + ResultCodeConstants.SYSTEM_ERROR.getJsonString() + "])";
        }
        return "laud([" + jsonObject.toString() + "])";
    }

    @GetMapping(value = "/content-sums/checklaud")
    @Timed
    public String checklaud(HttpServletRequest request) {
        String profileId = HTTPUtil.getParam(request, "pid");
        String archiveid = request.getParameter("archiveid");

        JSONObject jsonObject = WanbaResultCodeConstants.SUCCESS.getJsonObject();
        //check param
        if (StringUtil.isEmpty(archiveid) || StringUtil.isEmpty(profileId)) {
            return "checklaud([" + WanbaResultCodeConstants.PARAM_EMPTY.getJsonString() + "])";
        }
        try {
            Content content = contentService.findByCommentId(CommentUtil.genCommentId(archiveid, CommentDomain.CMS_COMMENT));
            if (content == null) {
                return "checklaud([" + WanbaResultCodeConstants.PARAM_EMPTY.getJsonString() + "])";
            }
            boolean bval = contentService.checkContentSum(content.getId(), ContentSumType.AGREE_NUM, profileId);
            if (bval) {
                return "checklaud([" + WanbaResultCodeConstants.WANBA_ASK_ANSWER_AGREE_FAILED.getJsonString() + "])";
            }
        } catch (ServiceException e) {
            log.error(this.getClass().getName() + " occured error.e:", e);
            return "checklaud([" + WanbaResultCodeConstants.SYSTEM_ERROR.getJsonString() + "])";
        }
        return "checklaud([" + jsonObject.toString() + "])";
    }

    /**
     * PUT  /content-sums : Updates an existing contentSum.
     *
     * @param contentSum the contentSum to update
     * @return the ResponseEntity with status 200 (OK) and with body the updated contentSum,
     * or with status 400 (Bad Request) if the contentSum is not valid,
     * or with status 500 (Internal Server Error) if the contentSum couldnt be updated
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PutMapping("/content-sums")
    @Timed
    public ResponseEntity<ContentSum> updateContentSum(@RequestBody ContentSum contentSum) throws URISyntaxException {
        log.debug("REST request to update ContentSum : {}", contentSum);
        if (contentSum.getId() == null) {
            return createContentSum(contentSum);
        }
        ContentSum result = contentSumService.save(contentSum);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(ENTITY_NAME, contentSum.getId().toString()))
            .body(result);
    }

    /**
     * GET  /content-sums : get all the contentSums.
     *
     * @return the ResponseEntity with status 200 (OK) and the list of contentSums in body
     */
    @GetMapping("/content-sums")
    @Timed
    public List<ContentSum> getAllContentSums() {
        log.debug("REST request to get all ContentSums");
        return contentSumService.findAll();
    }

    /**
     * GET  /content-sums/:id : get the "id" contentSum.
     *
     * @param id the id of the contentSum to retrieve
     * @return the ResponseEntity with status 200 (OK) and with body the contentSum, or with status 404 (Not Found)
     */
    @GetMapping("/content-sums/{id}")
    @Timed
    public ResponseEntity<ContentSum> getContentSum(@PathVariable Long id) {
        log.debug("REST request to get ContentSum : {}", id);
        ContentSum contentSum = contentSumService.findOne(id);
        return ResponseUtil.wrapOrNotFound(Optional.ofNullable(contentSum));
    }

    /**
     * DELETE  /content-sums/:id : delete the "id" contentSum.
     *
     * @param id the id of the contentSum to delete
     * @return the ResponseEntity with status 200 (OK)
     */
    @DeleteMapping("/content-sums/{id}")
    @Timed
    public ResponseEntity<Void> deleteContentSum(@PathVariable Long id) {
        log.debug("REST request to delete ContentSum : {}", id);
        contentSumService.delete(id);
        return ResponseEntity.ok().headers(HeaderUtil.createEntityDeletionAlert(ENTITY_NAME, id.toString())).build();
    }

}
