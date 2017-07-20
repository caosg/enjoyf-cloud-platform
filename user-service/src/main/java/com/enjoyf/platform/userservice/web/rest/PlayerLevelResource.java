package com.enjoyf.platform.userservice.web.rest;

import com.codahale.metrics.annotation.Timed;
import com.enjoyf.platform.userservice.domain.PlayerLevel;

import com.enjoyf.platform.userservice.repository.PlayerLevelRepository;
import com.enjoyf.platform.userservice.web.rest.util.HeaderUtil;
import io.github.jhipster.web.util.ResponseUtil;
import io.swagger.annotations.Api;
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
 * REST controller for managing PlayerLevel.
 */
@RestController
@RequestMapping("/api")
@Api(value = "玩家等级")
public class PlayerLevelResource {

    private final Logger log = LoggerFactory.getLogger(PlayerLevelResource.class);

    private static final String ENTITY_NAME = "playerLevel";

    private final PlayerLevelRepository playerLevelRepository;

    public PlayerLevelResource(PlayerLevelRepository playerLevelRepository) {
        this.playerLevelRepository = playerLevelRepository;
    }

    /**
     * POST  /player-levels : Create a new playerLevel.
     *
     * @param playerLevel the playerLevel to create
     * @return the ResponseEntity with status 201 (Created) and with body the new playerLevel, or with status 400 (Bad Request) if the playerLevel has already an ID
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PostMapping("/player-levels")
    @Timed
    public ResponseEntity<PlayerLevel> createPlayerLevel(@Valid @RequestBody PlayerLevel playerLevel) throws URISyntaxException {
        log.debug("REST request to save PlayerLevel : {}", playerLevel);
        if (playerLevel.getId() != null) {
            return ResponseEntity.badRequest().headers(HeaderUtil.createFailureAlert(ENTITY_NAME, "idexists", "A new playerLevel cannot already have an ID")).body(null);
        }
        PlayerLevel result = playerLevelRepository.save(playerLevel);
        return ResponseEntity.created(new URI("/api/player-levels/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * PUT  /player-levels : Updates an existing playerLevel.
     *
     * @param playerLevel the playerLevel to update
     * @return the ResponseEntity with status 200 (OK) and with body the updated playerLevel,
     * or with status 400 (Bad Request) if the playerLevel is not valid,
     * or with status 500 (Internal Server Error) if the playerLevel couldnt be updated
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PutMapping("/player-levels")
    @Timed
    public ResponseEntity<PlayerLevel> updatePlayerLevel(@Valid @RequestBody PlayerLevel playerLevel) throws URISyntaxException {
        log.debug("REST request to update PlayerLevel : {}", playerLevel);
        if (playerLevel.getId() == null) {
            return createPlayerLevel(playerLevel);
        }
        PlayerLevel result = playerLevelRepository.save(playerLevel);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(ENTITY_NAME, playerLevel.getId().toString()))
            .body(result);
    }

    /**
     * GET  /player-levels : get all the playerLevels.
     *
     * @return the ResponseEntity with status 200 (OK) and the list of playerLevels in body
     */
    @GetMapping("/player-levels")
    @Timed
    public List<PlayerLevel> getAllPlayerLevels() {
        log.debug("REST request to get all PlayerLevels");
        List<PlayerLevel> playerLevels = playerLevelRepository.findAll();
        return playerLevels;
    }

    /**
     * GET  /player-levels/:id : get the "id" playerLevel.
     *
     * @param id the id of the playerLevel to retrieve
     * @return the ResponseEntity with status 200 (OK) and with body the playerLevel, or with status 404 (Not Found)
     */
    @GetMapping("/player-levels/{id}")
    @Timed
    public ResponseEntity<PlayerLevel> getPlayerLevel(@PathVariable Long id) {
        log.debug("REST request to get PlayerLevel : {}", id);
        PlayerLevel playerLevel = playerLevelRepository.findOne(id);
        return ResponseUtil.wrapOrNotFound(Optional.ofNullable(playerLevel));
    }

    /**
     * DELETE  /player-levels/:id : delete the "id" playerLevel.
     *
     * @param id the id of the playerLevel to delete
     * @return the ResponseEntity with status 200 (OK)
     */
    @DeleteMapping("/player-levels/{id}")
    @Timed
    public ResponseEntity<Void> deletePlayerLevel(@PathVariable Long id) {
        log.debug("REST request to delete PlayerLevel : {}", id);
        playerLevelRepository.delete(id);
        return ResponseEntity.ok().headers(HeaderUtil.createEntityDeletionAlert(ENTITY_NAME, id.toString())).build();
    }

}
