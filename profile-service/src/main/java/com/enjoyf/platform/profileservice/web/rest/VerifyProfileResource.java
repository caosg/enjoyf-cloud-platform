package com.enjoyf.platform.profileservice.web.rest;

import com.codahale.metrics.annotation.Timed;
import com.enjoyf.platform.page.ScoreRange;
import com.enjoyf.platform.page.ScoreRangeRows;
import com.enjoyf.platform.page.ScoreSort;
import com.enjoyf.platform.profileservice.domain.VerifyProfile;
import com.enjoyf.platform.profileservice.domain.enumeration.VerifyProfileType;
import com.enjoyf.platform.profileservice.service.VerifyProfileService;
import com.enjoyf.platform.profileservice.web.rest.util.HeaderUtil;
import com.enjoyf.platform.profileservice.web.rest.util.PaginationUtil;
import com.enjoyf.platform.profileservice.service.dto.VerifyProfileDTO;
import io.swagger.annotations.ApiOperation;
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

import javax.validation.Valid;
import java.math.BigDecimal;
import java.net.URI;
import java.net.URISyntaxException;

import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.Set;

/**
 * REST controller for managing VerifyProfile.
 */
@RestController
@RequestMapping("/api/verify-profiles")
public class VerifyProfileResource {

    private final Logger log = LoggerFactory.getLogger(VerifyProfileResource.class);

    private static final String ENTITY_NAME = "VerifyProfile";

    private final VerifyProfileService verifyProfileService;

    public VerifyProfileResource(VerifyProfileService verifyProfileService) {
        this.verifyProfileService = verifyProfileService;
    }

    /**
     * POST  /VerifyProfiles : Create a new VerifyProfile.
     *
     * @param verifyProfile the verifyProfileDTO to createVertualProfile
     * @return the ResponseEntity with status 201 (Created) and with body the new verifyProfileDTO, or with status 400 (Bad Request) if the VerifyProfile has already an ID
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PostMapping
    @Timed
    @ApiOperation(value = "添加认证用户信息", response = VerifyProfile.class)
    public ResponseEntity<VerifyProfile> createVerifyProfile(@Valid @RequestBody VerifyProfile verifyProfile) throws URISyntaxException {
        log.debug("REST request to createVertualProfile VerifyProfile : {}", verifyProfile);


        VerifyProfile result = verifyProfileService.save(verifyProfile);
        return ResponseEntity.ok(result);
    }

    /**
     * PUT  /VerifyProfiles : Updates an existing VerifyProfile.
     *
     * @param verifyProfile the verifyProfileDTO to update
     * @return the ResponseEntity with status 200 (OK) and with body the updated verifyProfileDTO,
     * or with status 400 (Bad Request) if the verifyProfileDTO is not valid,
     * or with status 500 (Internal Server Error) if the verifyProfileDTO couldnt be updated
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PutMapping
    @Timed
    @ApiOperation(value = "修改认证用户信息", response = VerifyProfile.class)
    public ResponseEntity<VerifyProfile> updateVerifyProfile(@Valid @RequestBody VerifyProfile verifyProfile) throws URISyntaxException {
        log.debug("REST request to update VerifyProfile : {}", verifyProfile);
        if (verifyProfile.getId() == null) {
            return ResponseEntity.badRequest().headers(HeaderUtil.createFailureAlert(ENTITY_NAME, "idnotexists", "A new VerifyProfile must have a UID from userprofile")).body(null);
        }
        VerifyProfile result = verifyProfileService.save(verifyProfile);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(ENTITY_NAME, verifyProfile.getId().toString()))
            .body(result);
    }

    @GetMapping
    @Timed
    @ApiOperation(value = "获取认证用户信息列表", response = VerifyProfile.class)
    public ResponseEntity<List<VerifyProfileDTO>> getVerifyProfile(@ApiParam Pageable pageable) {
        log.debug("REST request to get VerifyProfile : {}", pageable);
        Page<VerifyProfileDTO> page = verifyProfileService.findAll(pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(page, "/api/verify-profiles");
        return new ResponseEntity<>(page.getContent(), headers, HttpStatus.OK);
    }

    /**
     * GET  /VerifyProfiles/:id : get the "id" VerifyProfile.
     *
     * @param id the id of the VerifyProfileDTO to retrieve
     * @return the ResponseEntity with status 200 (OK) and with body the VerifyProfileDTO, or with status 404 (Not Found)
     */
    @GetMapping("/{id}")
    @Timed
    @ApiOperation(value = "获取认证用户信息", response = VerifyProfile.class)
    public ResponseEntity<VerifyProfileDTO> getVerifyProfile(@PathVariable Long id) {
        log.debug("REST request to get VerifyProfile : {}", id);
        VerifyProfileDTO verifyProfileDTO = verifyProfileService.findOne(id);
        return ResponseEntity.ok(verifyProfileDTO);
    }

    @GetMapping("/ids")
    @Timed
    public ResponseEntity<Map<Long, VerifyProfileDTO>> getProfilesByIds(@RequestParam(value = "ids") Set<Long> ids) {
        log.debug("REST request to get UserProfile by ids : {}", ids);
        Map<Long, VerifyProfileDTO> map = verifyProfileService.findVerifyProfileByIds(ids);
        return ResponseUtil.wrapOrNotFound(Optional.ofNullable(map));
    }


    /**
     * DELETE  /VerifyProfiles/:id : delete the "id" VerifyProfile.
     *
     * @param id the id of the VerifyProfileDTO to delete
     * @return the ResponseEntity with status 200 (OK)
     */
    @DeleteMapping("/{id}")
    @Timed
    @ApiOperation(value = "删除认证用户信息", response = VerifyProfile.class)
    public ResponseEntity<Void> deleteVerifyProfile(@PathVariable Long id) {
        log.debug("REST request to delete VerifyProfile : {}", id);
        verifyProfileService.delete(id);
        return ResponseEntity.ok().headers(HeaderUtil.createEntityDeletionAlert(ENTITY_NAME, id.toString())).build();
    }

}
