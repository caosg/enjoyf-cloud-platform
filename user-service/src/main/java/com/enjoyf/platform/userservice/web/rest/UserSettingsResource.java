package com.enjoyf.platform.userservice.web.rest;

import com.codahale.metrics.annotation.Timed;
import com.enjoyf.platform.userservice.domain.UserSettings;

import com.enjoyf.platform.userservice.repository.UserSettingsRepository;
import com.enjoyf.platform.userservice.repository.redis.RedisUserSettingsRepository;
import com.enjoyf.platform.userservice.web.rest.util.HeaderUtil;
import com.enjoyf.platform.userservice.web.rest.util.PaginationUtil;
import io.swagger.annotations.Api;
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
import java.net.URI;
import java.net.URISyntaxException;
import java.time.ZonedDateTime;
import java.util.List;
import java.util.Optional;

/**
 * REST controller for managing UserSettings.
 */
@RestController
@RequestMapping("/api")
@Api(value = "用户设置")
public class UserSettingsResource {

    private final Logger log = LoggerFactory.getLogger(UserSettingsResource.class);

    private static final String ENTITY_NAME = "userSettings";

    private final UserSettingsRepository userSettingsRepository;

    private final RedisUserSettingsRepository redisUserSettingsRepository;

    public UserSettingsResource(UserSettingsRepository userSettingsRepository, RedisUserSettingsRepository redisUserSettingsRepository) {
        this.userSettingsRepository = userSettingsRepository;
        this.redisUserSettingsRepository = redisUserSettingsRepository;
    }

    /**
     * POST  /user-settings : Create a new userSettings.
     *
     * @param userSettings the userSettings to create
     * @return the ResponseEntity with status 201 (Created) and with body the new userSettings, or with status 400 (Bad Request) if the userSettings has already an ID
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PostMapping("/user-settings")
    @Timed
    public ResponseEntity<UserSettings> createUserSettings(@Valid @RequestBody UserSettings userSettings) throws URISyntaxException {
        log.debug("REST request to save UserSettings : {}", userSettings);
        if (userSettings.getId() != null) {
            return ResponseEntity.badRequest().headers(HeaderUtil.createFailureAlert(ENTITY_NAME, "idexists", "A new userSettings cannot already have an ID")).body(null);
        }
        UserSettings result = userSettingsRepository.save(userSettings);
        redisUserSettingsRepository.save(result);
        return ResponseEntity.created(new URI("/api/user-settings/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * PUT  /user-settings : Updates an existing userSettings.
     *
     * @param userSettings the userSettings to update
     * @return the ResponseEntity with status 200 (OK) and with body the updated userSettings,
     * or with status 400 (Bad Request) if the userSettings is not valid,
     * or with status 500 (Internal Server Error) if the userSettings couldnt be updated
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PutMapping("/user-settings")
    @Timed
    public ResponseEntity<UserSettings> updateUserSettings(@Valid @RequestBody UserSettings userSettings) throws URISyntaxException {
        log.debug("REST request to update UserSettings : {}", userSettings);
        UserSettings result = userSettingsRepository.save(userSettings);
        redisUserSettingsRepository.save(result);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(ENTITY_NAME, userSettings.getId().toString()))
            .body(result);
    }

    @PutMapping("/user-settings/by-profile")
    @Timed
    public ResponseEntity<UserSettings> updateUserSettingsByProfileNo(@Valid @RequestBody UserSettings userSettings) throws URISyntaxException {
        log.debug("REST request to update UserSettings : {}", userSettings);
        if (userSettings.getId() == null) {
            UserSettings existSettings = userSettingsRepository.findOneByProfileNo(userSettings.getProfileNo());
            if(existSettings==null)
                return this.createUserSettings(userSettings);
            userSettings.setUpdatedTime(ZonedDateTime.now());
            userSettings.setId(existSettings.getId());
        }

        UserSettings result = userSettingsRepository.save(userSettings);
        redisUserSettingsRepository.save(result);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(ENTITY_NAME, userSettings.getId().toString()))
            .body(result);
    }


    /**
     * GET  /user-settings : get all the userSettings.
     *
     * @param pageable the pagination information
     * @return the ResponseEntity with status 200 (OK) and the list of userSettings in body
     */
    @GetMapping("/user-settings")
    @Timed
    public ResponseEntity<List<UserSettings>> getAllUserSettings(@ApiParam Pageable pageable) {
        log.debug("REST request to get a page of UserSettings");
        Page<UserSettings> page = userSettingsRepository.findAll(pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(page, "/api/user-settings");
        return new ResponseEntity<>(page.getContent(), headers, HttpStatus.OK);
    }

    /**
     * GET  /user-settings/:id : get the "id" userSettings.
     *
     * @param id the id of the userSettings to retrieve
     * @return the ResponseEntity with status 200 (OK) and with body the userSettings, or with status 404 (Not Found)
     */
    @GetMapping("/user-settings/{id}")
    @Timed
    public ResponseEntity<UserSettings> getUserSettings(@PathVariable Long id) {
        log.debug("REST request to get UserSettings : {}", id);

        UserSettings userSettings = redisUserSettingsRepository.findOne(id);
        if(userSettings==null)
           userSettings = userSettingsRepository.findOne(id);
        return ResponseUtil.wrapOrNotFound(Optional.ofNullable(userSettings));
    }

    @GetMapping("/user-settings/by")
    @Timed
    public ResponseEntity<UserSettings> getUserSettingsByProfileNo(@RequestParam String profileNo) {
        log.debug("REST request to get UserSettings by profileNo: {}", profileNo);
        UserSettings userSettings = redisUserSettingsRepository.findOneByProfileNo(profileNo);
        if(userSettings==null)
            userSettings = userSettingsRepository.findOneByProfileNo(profileNo);
        return ResponseUtil.wrapOrNotFound(Optional.ofNullable(userSettings));
    }

    /**
     * DELETE  /user-settings/:id : delete the "id" userSettings.
     *
     * @param id the id of the userSettings to delete
     * @return the ResponseEntity with status 200 (OK)
     */
    @DeleteMapping("/user-settings/{id}")
    @Timed
    public ResponseEntity<Void> deleteUserSettings(@PathVariable Long id) {
        log.debug("REST request to delete UserSettings : {}", id);
        userSettingsRepository.delete(id);
        redisUserSettingsRepository.delete(id);
        return ResponseEntity.ok().headers(HeaderUtil.createEntityDeletionAlert(ENTITY_NAME, id.toString())).build();
    }

}
