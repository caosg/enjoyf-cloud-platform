package com.enjoyf.platform.contentservice.web.rest;

import com.codahale.metrics.annotation.Timed;
import com.enjoyf.platform.contentservice.domain.UserCommentSum;
import com.enjoyf.platform.contentservice.domain.enumeration.UserCommentSumFiled;
import com.enjoyf.platform.contentservice.security.SecurityUtils;
import com.enjoyf.platform.contentservice.service.UserCommentSumService;
import com.enjoyf.platform.contentservice.web.rest.util.HeaderUtil;
import com.enjoyf.platform.contentservice.web.rest.vm.UserCommentSumVM;
import io.github.jhipster.web.util.ResponseUtil;
import io.swagger.annotations.ApiOperation;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.net.URI;
import java.net.URISyntaxException;

import java.security.Security;
import java.util.List;
import java.util.Map;
import java.util.Optional;

/**
 * REST controller for managing UserCommentSum.
 */
@RestController
@RequestMapping("/api/user-comment-sums")
public class UserCommentSumResource {

    private final Logger log = LoggerFactory.getLogger(UserCommentSumResource.class);

    private static final String ENTITY_NAME = "userCommentSum";

    private final UserCommentSumService userCommentSumService;

    public UserCommentSumResource(UserCommentSumService userCommentSumService) {
        this.userCommentSumService = userCommentSumService;
    }

    /**
     * GET  /user-comment-sums/:id : get the "id" userCommentSum.
     *
     * @return the ResponseEntity with status 200 (OK) and with body the userCommentSum, or with status 404 (Not Found)
     */
    @PutMapping("/increase")
    @Timed
    @ApiOperation(value = "增加用户点评相关的计数", response = String.class)
    public ResponseEntity<String> increaseUserCommentSum(@RequestBody UserCommentSumVM vm) {
        Long id = SecurityUtils.getCurrentUid();
        long result = userCommentSumService.increase(id, vm.getFiled(), vm.getValue());
        return ResponseEntity.ok("success");
    }


    /**
     * GET  /user-comment-sums/:id : get the "id" userCommentSum.
     *
     * @param id the id of the userCommentSum to retrieve
     * @return the ResponseEntity with status 200 (OK) and with body the userCommentSum, or with status 404 (Not Found)
     */
    @GetMapping("/{id}")
    @Timed
    @ApiOperation(value = "获取用户点评相关的计数", response = UserCommentSum.class)
    public ResponseEntity<UserCommentSum> getUserCommentSum(@PathVariable Long id) {
        log.debug("REST request to get UserCommentSum : {}", id);
        UserCommentSum userCommentSum = userCommentSumService.findOne(id);
        return ResponseUtil.wrapOrNotFound(Optional.ofNullable(userCommentSum));
    }

    @GetMapping("/ids")
    @Timed
    @ApiOperation(value = "批量获取用户点评相关的计数", response = Map.class)
    public ResponseEntity<Map<Long, UserCommentSum>> findByIds(@RequestParam Long[] ids) {
        log.debug("REST request to get UserCommentSum : {}", ids);
        Map<Long, UserCommentSum> userCommentSum = userCommentSumService.findAllByIds(ids);
        return ResponseUtil.wrapOrNotFound(Optional.ofNullable(userCommentSum));
    }

}
