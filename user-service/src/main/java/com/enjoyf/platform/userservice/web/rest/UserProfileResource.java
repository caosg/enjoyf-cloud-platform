package com.enjoyf.platform.userservice.web.rest;

import com.codahale.metrics.annotation.Timed;
import com.enjoyf.platform.autoconfigure.web.error.CustomParameterizedException;
import com.enjoyf.platform.userservice.domain.UserProfile;
import com.enjoyf.platform.userservice.service.UserProfileService;
import com.enjoyf.platform.userservice.service.exception.UserException;
import com.enjoyf.platform.userservice.service.util.UserSeviceUtil;
import com.enjoyf.platform.userservice.web.rest.util.HeaderUtil;
import com.enjoyf.platform.userservice.web.rest.util.PaginationUtil;
import com.hazelcast.core.HazelcastInstance;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiParam;
import io.github.jhipster.web.util.ResponseUtil;
import org.apache.commons.collections.CollectionUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.net.URI;
import java.net.URISyntaxException;
import java.time.ZonedDateTime;
import java.util.*;
import java.util.concurrent.locks.Lock;
import java.util.stream.Collectors;

/**
 * REST controller for managing UserProfile.
 */
@RestController
@RequestMapping("/api")
@Api(value = "用户信息")
public class UserProfileResource {

    private final Logger log = LoggerFactory.getLogger(UserProfileResource.class);

    private static final String ENTITY_NAME = "userProfile";

    private final UserProfileService userProfileService;

    private final HazelcastInstance hazelcastInstance;

    public UserProfileResource(UserProfileService userProfileService, HazelcastInstance hazelcastInstance) {
        this.userProfileService = userProfileService;
        this.hazelcastInstance = hazelcastInstance;
    }

    /**
     * POST  /user-profiles : Create a new userProfile.
     *
     * @param userProfile the userProfile to create
     * @return the ResponseEntity with status 201 (Created) and with body the new userProfile, or with status 400 (Bad Request) if the userProfile has already an ID
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PostMapping("/user-profiles")
    @Timed
    public ResponseEntity<UserProfile> createUserProfile(@RequestBody UserProfile userProfile) throws URISyntaxException {
        log.debug("REST request to save UserProfile : {}", userProfile);
        if (userProfile.getId() != null) {
            return ResponseEntity.badRequest().headers(HeaderUtil.createFailureAlert(ENTITY_NAME, "idexists", "A new userProfile cannot already have an ID")).body(null);
        }

        if (userProfile.getAccountNo() == null || userProfile.getProfileKey() == null || userProfile.getNick() == null) {
            return ResponseEntity.badRequest().
                headers(HeaderUtil.createFailureAlert(ENTITY_NAME, "accountNoNotExists", "A new userProfile must have AccountNos and profileKey and nick"))
                .body(null);
        }

        userProfile.setCreatedTime(ZonedDateTime.now());
        userProfile.setCreatedIp("127.0.0.1");
        userProfile.setProfileNo(UserSeviceUtil.generateProfileNo(userProfile.getAccountNo(), userProfile.getProfileKey()));
        userProfile.setLowercaseNick(userProfile.getNick().toLowerCase());
        UserProfile result = userProfileService.save(userProfile);
        return ResponseEntity.created(new URI("/api/user-profiles/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * PUT  /user-profiles : Updates an existing userProfile.
     *
     * @param userProfile the userProfile to update
     * @return the ResponseEntity with status 200 (OK) and with body the updated userProfile,
     * or with status 400 (Bad Request) if the userProfile is not valid,
     * or with status 500 (Internal Server Error) if the userProfile couldnt be updated
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PutMapping("/user-profiles")
    @Timed
    public ResponseEntity<UserProfile> updateUserProfile(@Valid @RequestBody UserProfile userProfile) throws URISyntaxException {
        log.debug("REST request to update UserProfile : {}", userProfile);
        if (userProfile.getId() == null) {
            return createUserProfile(userProfile);
        }
        UserProfile result = userProfileService.save(userProfile);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(ENTITY_NAME, userProfile.getId().toString()))
            .body(result);
    }

    /**
     * 修改昵称，登陆后操作
     *
     * @param nick
     * @return
     */
    @PutMapping("/user-profiles/nick/{nick}")
    @Timed
    public ResponseEntity<UserProfile> updateNick(@PathVariable String nick) {
        Lock lock = hazelcastInstance.getLock(nick);
        lock.lock();
        UserProfile result;
        try {
            result = userProfileService.updateNick(nick);
        } catch (UserException e) {
            log.info("update profile nick errror:" + e.getMessage());
            throw new CustomParameterizedException(e.getMessage(), nick);
        } catch (AccessDeniedException ae) {
            throw ae;
        } finally {
            lock.unlock();
        }

        return ResponseEntity.ok().body(result);
    }

    /**
     * GET  /user-profiles : get all the userProfiles.
     *
     * @param pageable the pagination information
     * @return the ResponseEntity with status 200 (OK) and the list of userProfiles in body
     */
    @GetMapping("/user-profiles")
    @Timed
    public ResponseEntity<List<UserProfile>> getAllUserProfiles(@ApiParam Pageable pageable) {
        log.debug("REST request to get a page of UserProfiles");
        Page<UserProfile> page = userProfileService.findAll(pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(page, "/api/user-profiles");
        return new ResponseEntity<>(page.getContent(), headers, HttpStatus.OK);
    }

    @GetMapping("/user-profiles/search")
    @Timed
    public ResponseEntity<List<UserProfile>> searchAllUserProfiles(String nick, String inOrNot, String profileNos, String startTime, String endTime, @ApiParam Pageable pageable) {
        log.debug("REST request to get a page of UserProfiles");
        Page<UserProfile> page = userProfileService.findAllWhere(nick, inOrNot, profileNos, startTime, endTime, pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(page, "/api/user-profiles");
        return new ResponseEntity<>(page.getContent(), headers, HttpStatus.OK);
    }

    /**
     * GET  /user-profiles/:id : get the "id" userProfile.
     *
     * @param id the id of the userProfile to retrieve
     * @return the ResponseEntity with status 200 (OK) and with body the userProfile, or with status 404 (Not Found)
     */
    @GetMapping("/user-profiles/{id}")
    @Timed
    public ResponseEntity<UserProfile> getUserProfile(@PathVariable Long id) {
        log.debug("REST request to get UserProfile : {}", id);
        UserProfile userProfile = userProfileService.findOne(id);
        return ResponseUtil.wrapOrNotFound(Optional.ofNullable(userProfile));
    }

    /**
     * DELETE  /user-profiles/:id : delete the "id" userProfile.
     *
     * @param id the id of the userProfile to delete
     * @return the ResponseEntity with status 200 (OK)
     */
    @DeleteMapping("/user-profiles/{id}")
    @Timed
    public ResponseEntity<Void> deleteUserProfile(@PathVariable Long id) {
        log.debug("REST request to delete UserProfile : {}", id);
        userProfileService.delete(id);
        return ResponseEntity.ok().headers(HeaderUtil.createEntityDeletionAlert(ENTITY_NAME, id.toString())).build();
    }

    /**
     * GET  /user-profiles/:id : get the "id" userProfile.
     *
     * @param profileNo the id of the userProfile to retrieve
     * @return the ResponseEntity with status 200 (OK) and with body the userProfile, or with status 404 (Not Found)
     */
    @GetMapping("/user-profiles/profileno/{profileNo}")
    @Timed
    public ResponseEntity<UserProfile> getUserProfileByProfileNo(@PathVariable String profileNo) {
        log.debug("REST request to get UserProfile : {}", profileNo);
        UserProfile userProfile = userProfileService.findOneByProfileNo(profileNo);
        return ResponseUtil.wrapOrNotFound(Optional.ofNullable(userProfile));
    }

    /**
     * GET  /user-profiles/:id : get the "id" userProfile.
     *
     * @param profileNos the id of the userProfile to retrieve
     * @return the ResponseEntity with status 200 (OK) and with body the userProfile, or with status 404 (Not Found)
     */
    @GetMapping("/user-profiles/profileno")
    @Timed
    public ResponseEntity<List<UserProfile>> getProfilesByProfileNos(@RequestParam(value = "profilenos") String[] profileNos) {
        log.debug("REST request to get UserProfile : {}", profileNos);
        Set<String> proflieNo = Arrays.stream(profileNos).collect(Collectors.toSet());
        List<UserProfile> userProfileList = userProfileService.findByProfileNos(proflieNo);
        return ResponseUtil.wrapOrNotFound(Optional.ofNullable(userProfileList));
    }

    /**
     * GET  /user-profiles : get the "ids" userProfiles.
     *
     * @param ids
     * @return
     */
    @GetMapping("/user-profiles/ids")
    @Timed
    public ResponseEntity<List<UserProfile>> getProfilesByIds(@RequestParam(value = "ids") Set<Long> ids) {
        log.debug("REST request to get UserProfile by ids : {}", ids);

        List<UserProfile> userProfileList = null;
        if (CollectionUtils.isEmpty(ids)) {
            return ResponseUtil.wrapOrNotFound(Optional.ofNullable(userProfileList));
        }

        if (ids.size() > 0) {
            userProfileList = userProfileService.findByIds(ids);
        } else {
            UserProfile userProfile = userProfileService.findOne(ids.iterator().next());
            if (userProfile != null) {
                userProfileList = new ArrayList<>();
                userProfileList.add(userProfile);
            }
        }
        return ResponseUtil.wrapOrNotFound(Optional.ofNullable(userProfileList));
    }

    /**
     * 按照昵称模糊查询
     *
     * @param nick
     * @return
     */
    @GetMapping("/user-profiles/by")
    @Timed
    public ResponseEntity<List<UserProfile>> getProfileByLikeNick(@RequestParam String nick) {
        if (log.isDebugEnabled())
            log.debug(" REST request to get UserProfile by like : nick={}", nick);
        List<UserProfile> userProfileList = userProfileService.findByNickLike(nick);
        return ResponseUtil.wrapOrNotFound(Optional.ofNullable(userProfileList));
    }

    /**
     * 按照昵称精确查询
     *
     * @param nick
     * @return
     */
    @GetMapping("/user-profiles/nick")
    public ResponseEntity<UserProfile> getProfileByNick(@RequestParam String nick) {
        if (log.isDebugEnabled())
            log.debug(" REST request to get UserProfile  : nick={}", nick);
        Optional<UserProfile> userProfile = userProfileService.findOneByNick(nick);
        return ResponseUtil.wrapOrNotFound(Optional.ofNullable(userProfile.isPresent() ? userProfile.get() : null));
    }

}
