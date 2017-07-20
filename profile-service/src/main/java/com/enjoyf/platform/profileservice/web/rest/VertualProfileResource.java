package com.enjoyf.platform.profileservice.web.rest;

import com.codahale.metrics.annotation.Timed;
import com.enjoyf.platform.profileservice.domain.VerifyProfile;
import com.enjoyf.platform.profileservice.domain.VertualProfile;
import com.enjoyf.platform.profileservice.service.VertualProfileService;
import com.enjoyf.platform.profileservice.service.dto.VerifyProfileDTO;
import com.enjoyf.platform.profileservice.web.rest.util.HeaderUtil;
import com.enjoyf.platform.profileservice.web.rest.util.PaginationUtil;
import com.enjoyf.platform.profileservice.web.rest.vm.VertualProfileVM;
import io.github.jhipster.web.util.ResponseUtil;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.List;
import java.util.Map;
import java.util.Optional;

/**
 * REST controller for managing VertualProfile.
 */
@RestController
@RequestMapping("/api/vertual-profiles")
public class VertualProfileResource {

    private final Logger log = LoggerFactory.getLogger(VertualProfileResource.class);

    private static final String ENTITY_NAME = "vertualProfile";

    private final VertualProfileService vertualProfileService;

    public VertualProfileResource(VertualProfileService vertualProfileService) {
        this.vertualProfileService = vertualProfileService;
    }

    /**
     * POST  /vertual-profiles : Create a new vertualProfile.
     *
     * @param vertualProfileVM the vertualProfile to createVertualProfile
     * @return the ResponseEntity with status 201 (Created) and with body the new vertualProfile, or with status 400 (Bad Request) if the vertualProfile has already an ID
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PostMapping
    @Timed
    @ApiOperation(value = "添加虚拟用户", response = VerifyProfile.class)
    public ResponseEntity<VertualProfile> createVertualProfile(@Valid @RequestBody VertualProfileVM vertualProfileVM) throws URISyntaxException {
        log.debug("REST request to createVertualProfile vertualProfileVM : {}", vertualProfileVM);


        VertualProfile result = vertualProfileService.createVertualProfile(vertualProfileVM);

        //todo add tag
        return ResponseEntity.created(new URI("/api/vertual-profiles/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * GET  /VerifyProfiles/:id : get the "id" VerifyProfile.
     *
     * @param ids the id of the VerifyProfileDTO to retrieve
     * @return the ResponseEntity with status 200 (OK) and with body the VerifyProfileDTO, or with status 404 (Not Found)
     */
    @GetMapping("/ids")
    @Timed
    @ApiOperation(value = "通过ID获取认证用户信息", response = VerifyProfile.class)
    public ResponseEntity<Map<Long, VertualProfile>> getVerifyProfile(@PathVariable Long[] ids) {
        log.debug("REST request to get VerifyProfile : {}", ids);
        Map<Long, VertualProfile> map = vertualProfileService.findByIds(ids);
        return ResponseUtil.wrapOrNotFound(Optional.ofNullable(map));
    }


    /**
     * PUT  /vertual-profiles : Updates an existing vertualProfile.
     *
     * @param vertualProfile the vertualProfile to update
     * @return the ResponseEntity with status 200 (OK) and with body the updated vertualProfile,
     * or with status 400 (Bad Request) if the vertualProfile is not valid,
     * or with status 500 (Internal Server Error) if the vertualProfile couldnt be updated
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PutMapping
    @Timed
    @ApiOperation(value = "修改虚拟用户信息", response = VerifyProfile.class)
    public ResponseEntity<VertualProfile> updateVertualProfile(@Valid @RequestBody VertualProfile vertualProfile) throws URISyntaxException {
        log.debug("REST request to update VertualProfile : {}", vertualProfile);
        if (vertualProfile.getId() == null) {
            return ResponseEntity.badRequest().headers(HeaderUtil.createFailureAlert(ENTITY_NAME, "idnotexists", "Update vertualProfile must be have an ID")).body(null);
        }
        VertualProfile result = vertualProfileService.updateVertualProfile(vertualProfile);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(ENTITY_NAME, vertualProfile.getId().toString()))
            .body(result);
    }

    /**
     * GET  /vertual-profiles : get all the vertualProfiles.
     *
     * @param pageable the pagination information
     * @return the ResponseEntity with status 200 (OK) and the list of vertualProfiles in body
     */
    @GetMapping
    @Timed
    @ApiOperation(value = "获取虚拟用户信息", response = VerifyProfile.class)
    public ResponseEntity<List<VertualProfile>> getAllVertualProfiles(@ApiParam Pageable pageable) {
        log.debug("REST request to get a page of VertualProfiles");
        Page<VertualProfile> page = vertualProfileService.findAll(pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(page, "/api/vertual-profiles");
        return new ResponseEntity<>(page.getContent(), headers, HttpStatus.OK);
    }

    /**
     * GET  /vertual-profiles/:id : get the "id" vertualProfile.
     *
     * @param id the id of the vertualProfile to retrieve
     * @return the ResponseEntity with status 200 (OK) and with body the vertualProfile, or with status 404 (Not Found)
     */
    @GetMapping("/{id}")
    @Timed
    @ApiOperation(value = "根据ID获取虚拟用户信息", response = VerifyProfile.class)
    public ResponseEntity<VertualProfile> getVertualProfile(@PathVariable Long id) {
        log.debug("REST request to get VertualProfile : {}", id);
        VertualProfile vertualProfile = vertualProfileService.findOne(id);
        return ResponseUtil.wrapOrNotFound(Optional.ofNullable(vertualProfile));
    }

    /**
     * GET  /vertual-profiles/:id : get the "id" vertualProfile.
     *
     * @param nick the id of the vertualProfile to retrieve
     * @return the ResponseEntity with status 200 (OK) and with body the vertualProfile, or with status 404 (Not Found)
     */
    @GetMapping("/like/nick")
    @Timed
    @ApiOperation(value = "根据ID获取虚拟用户信息", response = VerifyProfile.class)
    public ResponseEntity<List<VertualProfile>> getVertualProfileByLikeNick(@RequestParam("nick") String nick) {
        log.debug("REST request to get VertualProfile : {}", nick);
        List<VertualProfile> vertualProfile = vertualProfileService.findAllByNickLike(nick);
        return ResponseUtil.wrapOrNotFound(Optional.ofNullable(vertualProfile));
    }

    /**
     * DELETE  /vertual-profiles/:id : delete the "id" vertualProfile.
     *
     * @param id the id of the vertualProfile to delete
     * @return the ResponseEntity with status 200 (OK)
     */
    @DeleteMapping("/{id}")
    @Timed
    @ApiOperation(value = "删除虚拟用户信息", response = VerifyProfile.class)
    public ResponseEntity<Void> deleteVertualProfile(@PathVariable Long id) {
        log.debug("REST request to delete VertualProfile : {}", id);
        vertualProfileService.delete(id);
        return ResponseEntity.ok().headers(HeaderUtil.createEntityDeletionAlert(ENTITY_NAME, id.toString())).build();
    }

}
