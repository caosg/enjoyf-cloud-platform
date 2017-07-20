package com.enjoyf.platform.userservice.web.rest;

import com.codahale.metrics.annotation.Timed;
import com.enjoyf.platform.userservice.domain.AppConf;

import com.enjoyf.platform.userservice.repository.AppConfRepository;
import com.enjoyf.platform.userservice.web.rest.util.HeaderUtil;
import io.github.jhipster.web.util.ResponseUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.net.URI;
import java.net.URISyntaxException;

import java.util.List;
import java.util.Optional;

/**
 * REST controller for managing AppConf.
 */
@RestController
@RequestMapping("/api")
public class AppConfResource {

    private final Logger log = LoggerFactory.getLogger(AppConfResource.class);

    private static final String ENTITY_NAME = "appConf";

    private final AppConfRepository appConfRepository;

    public AppConfResource(AppConfRepository appConfRepository) {
        this.appConfRepository = appConfRepository;
    }

    /**
     * POST  /app-confs : Create a new appConf.
     *
     * @param appConf the appConf to create
     * @return the ResponseEntity with status 201 (Created) and with body the new appConf, or with status 400 (Bad Request) if the appConf has already an ID
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PostMapping("/app-confs")
    @Timed
    public ResponseEntity<AppConf> createAppConf(@Valid @RequestBody AppConf appConf) throws URISyntaxException {
        log.debug("REST request to save AppConf : {}", appConf);
        if (appConf.getId() != null) {
            return ResponseEntity.badRequest().headers(HeaderUtil.createFailureAlert(ENTITY_NAME, "idexists", "A new appConf cannot already have an ID")).body(null);
        }
        AppConf result = appConfRepository.save(appConf);
        return ResponseEntity.created(new URI("/api/app-confs/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * PUT  /app-confs : Updates an existing appConf.
     *
     * @param appConf the appConf to update
     * @return the ResponseEntity with status 200 (OK) and with body the updated appConf,
     * or with status 400 (Bad Request) if the appConf is not valid,
     * or with status 500 (Internal Server Error) if the appConf couldn't be updated
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PutMapping("/app-confs")
    @Timed
    public ResponseEntity<AppConf> updateAppConf(@Valid @RequestBody AppConf appConf) throws URISyntaxException {
        log.debug("REST request to update AppConf : {}", appConf);
        if (appConf.getId() == null) {
            return createAppConf(appConf);
        }
        AppConf result = appConfRepository.save(appConf);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(ENTITY_NAME, appConf.getId().toString()))
            .body(result);
    }

    /**
     * GET  /app-confs : get all the appConfs.
     *
     * @return the ResponseEntity with status 200 (OK) and the list of appConfs in body
     */
    @GetMapping("/app-confs")
    @Timed
    public List<AppConf> getAllAppConfs() {
        log.debug("REST request to get all AppConfs");
        return appConfRepository.findAll();
    }

    /**
     * GET  /app-confs/:id : get the "id" appConf.
     *
     * @param id the id of the appConf to retrieve
     * @return the ResponseEntity with status 200 (OK) and with body the appConf, or with status 404 (Not Found)
     */
    @GetMapping("/app-confs/{id}")
    @Timed
    public ResponseEntity<AppConf> getAppConf(@PathVariable Long id) {
        log.debug("REST request to get AppConf : {}", id);
        AppConf appConf = appConfRepository.findOne(id);
        return ResponseUtil.wrapOrNotFound(Optional.ofNullable(appConf));
    }

    @GetMapping("/app-confs/appkey/{appKey}")
    @Timed
    public ResponseEntity<AppConf> getAppConf(@PathVariable String appKey) {
        log.debug("REST request to get AppConf : {}", appKey);
        AppConf appConf = appConfRepository.findOneByAppKey(appKey);
        return ResponseUtil.wrapOrNotFound(Optional.ofNullable(appConf));
    }

    /**
     * DELETE  /app-confs/:id : delete the "id" appConf.
     *
     * @param id the id of the appConf to delete
     * @return the ResponseEntity with status 200 (OK)
     */
    @DeleteMapping("/app-confs/{id}")
    @Timed
    public ResponseEntity<Void> deleteAppConf(@PathVariable Long id) {
        log.debug("REST request to delete AppConf : {}", id);
        appConfRepository.delete(id);
        return ResponseEntity.ok().headers(HeaderUtil.createEntityDeletionAlert(ENTITY_NAME, id.toString())).build();
    }
}
