package com.enjoyf.platform.contentservice.web.rest;

import com.codahale.metrics.annotation.Timed;
import com.enjoyf.platform.contentservice.domain.Advertise;
import com.enjoyf.platform.contentservice.service.AdvertiseService;
import com.enjoyf.platform.contentservice.web.rest.util.HeaderUtil;
import io.github.jhipster.web.util.ResponseUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.net.URI;
import java.net.URISyntaxException;
import java.util.List;
import java.util.Optional;

/**
 * REST controller for managing Advertise.
 */
@RestController
@RequestMapping("/api")
public class AdvertiseResource {

    private final Logger log = LoggerFactory.getLogger(AdvertiseResource.class);

    private static final String ENTITY_NAME = "advertise";
        
    private final AdvertiseService advertiseService;

    public AdvertiseResource(AdvertiseService advertiseService) {
        this.advertiseService = advertiseService;
    }

    /**
     * POST  /advertises : Create a new advertise.
     *
     * @param advertise the advertise to create
     * @return the ResponseEntity with status 201 (Created) and with body the new advertise, or with status 400 (Bad Request) if the advertise has already an ID
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PostMapping("/advertises")
    @Timed
    public ResponseEntity<Advertise> createAdvertise(@RequestBody Advertise advertise) throws URISyntaxException {
        log.debug("REST request to save Advertise : {}", advertise);
        if (advertise.getId() != null) {
            return ResponseEntity.badRequest().headers(HeaderUtil.createFailureAlert(ENTITY_NAME, "idexists", "A new advertise cannot already have an ID")).body(null);
        }
        Advertise result = advertiseService.save(advertise);
        return ResponseEntity.created(new URI("/api/advertises/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * PUT  /advertises : Updates an existing advertise.
     *
     * @param advertise the advertise to update
     * @return the ResponseEntity with status 200 (OK) and with body the updated advertise,
     * or with status 400 (Bad Request) if the advertise is not valid,
     * or with status 500 (Internal Server Error) if the advertise couldnt be updated
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PutMapping("/advertises")
    @Timed
    public ResponseEntity<Advertise> updateAdvertise(@RequestBody Advertise advertise) throws URISyntaxException {
        log.debug("REST request to update Advertise : {}", advertise);
        if (advertise.getId() == null) {
            return createAdvertise(advertise);
        }
        Advertise result = advertiseService.save(advertise);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(ENTITY_NAME, advertise.getId().toString()))
            .body(result);
    }

    /**
     * GET  /advertises : get all the advertises.
     *
     * @return the ResponseEntity with status 200 (OK) and the list of advertises in body
     */
    @GetMapping("/advertises")
    @Timed
    public List<Advertise> getAllAdvertises() {
        log.debug("REST request to get all Advertises");
        return advertiseService.findAll();
    }

    /**
     * GET  /advertises/:id : get the "id" advertise.
     *
     * @param id the id of the advertise to retrieve
     * @return the ResponseEntity with status 200 (OK) and with body the advertise, or with status 404 (Not Found)
     */
    @GetMapping("/advertises/{id}")
    @Timed
    public ResponseEntity<Advertise> getAdvertise(@PathVariable Long id) {
        log.debug("REST request to get Advertise : {}", id);
        Advertise advertise = advertiseService.findOne(id);
        return ResponseUtil.wrapOrNotFound(Optional.ofNullable(advertise));
    }

    /**
     * DELETE  /advertises/:id : delete the "id" advertise.
     *
     * @param id the id of the advertise to delete
     * @return the ResponseEntity with status 200 (OK)
     */
    @DeleteMapping("/advertises/{id}")
    @Timed
    public ResponseEntity<Void> deleteAdvertise(@PathVariable Long id) {
        log.debug("REST request to delete Advertise : {}", id);
        advertiseService.delete(id);
        return ResponseEntity.ok().headers(HeaderUtil.createEntityDeletionAlert(ENTITY_NAME, id.toString())).build();
    }

}
