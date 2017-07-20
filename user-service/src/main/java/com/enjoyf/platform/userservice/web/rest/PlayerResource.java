package com.enjoyf.platform.userservice.web.rest;

import com.codahale.metrics.annotation.Timed;
import com.enjoyf.platform.userservice.domain.Player;
import com.enjoyf.platform.userservice.domain.PlayerLevel;
import com.enjoyf.platform.userservice.repository.PlayerLevelRepository;
import com.enjoyf.platform.userservice.repository.PlayerRepository;
import com.enjoyf.platform.userservice.web.rest.util.HeaderUtil;
import com.enjoyf.platform.userservice.web.rest.util.PaginationUtil;
import io.github.jhipster.web.util.ResponseUtil;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiParam;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Example;
import org.springframework.data.domain.ExampleMatcher;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.List;
import java.util.Optional;
import java.util.Set;

/**
 * REST controller for managing Player.
 */
@RestController
@RequestMapping("/api")
@Api(value = "玩家")
public class PlayerResource {

    private final Logger log = LoggerFactory.getLogger(PlayerResource.class);

    private static final String ENTITY_NAME = "player";

    private final PlayerRepository playerRepository;

    private final PlayerLevelRepository levelRepository;

    public PlayerResource(PlayerRepository playerRepository, PlayerLevelRepository levelRepository) {
        this.playerRepository = playerRepository;
        this.levelRepository = levelRepository;
    }

    /**
     * POST  /players : Create a new player.
     *
     * @param player the player to create
     * @return the ResponseEntity with status 201 (Created) and with body the new player, or with status 400 (Bad Request) if the player has already an ID
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PostMapping("/players")
    @Timed
    public ResponseEntity<Player> createPlayer(@Valid @RequestBody Player player) throws URISyntaxException {
        log.debug("REST request to save Player : {}", player);
        if (player.getId() != null) {
            return ResponseEntity.badRequest().headers(HeaderUtil.createFailureAlert(ENTITY_NAME, "idexists", "A new player cannot already have an ID")).body(null);
        }
        Player result = playerRepository.save(player);
        return ResponseEntity.created(new URI("/api/players/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * PUT  /players : Updates an existing player.
     *
     * @param player the player to update
     * @return the ResponseEntity with status 200 (OK) and with body the updated player,
     * or with status 400 (Bad Request) if the player is not valid,
     * or with status 500 (Internal Server Error) if the player couldnt be updated
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PutMapping("/players")
    @Timed
    public ResponseEntity<Player> updatePlayer(@Valid @RequestBody Player player) throws URISyntaxException {
        log.debug("REST request to update Player : {}", player);
        if (player.getId() == null) {
            return createPlayer(player);
        }
        Player result = playerRepository.save(player);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(ENTITY_NAME, player.getId().toString()))
            .body(result);
    }

    /**
     * GET  /players : get all the players.
     *
     * @param pageable the pagination information
     * @return the ResponseEntity with status 200 (OK) and the list of players in body
     */
    @GetMapping("/players")
    @Timed
    public ResponseEntity<List<Player>> getAllPlayers(@ApiParam Pageable pageable) {
        log.debug("REST request to get a page of Players");
        Page<Player> page = playerRepository.findAll(pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(page, "/api/players");
        return new ResponseEntity<>(page.getContent(), headers, HttpStatus.OK);
    }

    @GetMapping("/players/search")
    @Timed
    public ResponseEntity<List<Player>> searchAllPlayers(@ApiParam Pageable pageable,Player player) {
        log.debug("REST request to get a page of Players");
        Page<Player> page;
        ExampleMatcher matcher = ExampleMatcher.matching().withStringMatcher(ExampleMatcher.StringMatcher.CONTAINING);
        if(player!=null){
            if(!StringUtils.isEmpty(player.getNick()))
                matcher.withMatcher("nick", match -> match.contains());
            if (!StringUtils.isEmpty(player.getAppKey()))
                matcher.withMatcher("appKey",match -> match.exact());
            page = playerRepository.findAll(Example.of(player,matcher),pageable);
        }else {
           page = playerRepository.findAll(pageable);
        }
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(page, "/api/players");
        return new ResponseEntity<>(page.getContent(), headers, HttpStatus.OK);
    }

    @GetMapping("/players/profiles")
    public ResponseEntity<List<Player>> getAllPlayersByProfileNos(@RequestParam Set<String> profileNos){
        if(log.isDebugEnabled())
            log.debug("REST request to get  Players by profileNos:{}",profileNos);
        return ResponseUtil.wrapOrNotFound(Optional.ofNullable(playerRepository.findByProfileNoIn(profileNos)));
    }

    /**
     * GET  /players/:id : get the "id" player.
     *
     * @param id the id of the player to retrieve
     * @return the ResponseEntity with status 200 (OK) and with body the player, or with status 404 (Not Found)
     */
    @GetMapping("/players/{id}")
    @Timed
    public ResponseEntity<Player> getPlayer(@PathVariable Long id) {
        log.debug("REST request to get Player : {}", id);
        Player player = playerRepository.findOne(id);
        return ResponseUtil.wrapOrNotFound(Optional.ofNullable(player));
    }

    @GetMapping("/players/profiles/{profileNo}")
    @Timed
    public ResponseEntity<Player> getPlayerByProfileNo(@PathVariable String profileNo) {
        log.debug("REST request to get Player by profileNo: {}", profileNo);
        Player player = playerRepository.findOneByProfileNo(profileNo);
        if(player!=null) {
            PlayerLevel level = levelRepository.getOne(player.getLevelId());
            if(level!=null)
              player.setLevelName(level.getName());
        }
        return ResponseUtil.wrapOrNotFound(Optional.ofNullable(player));
    }

    /**
     * DELETE  /players/:id : delete the "id" player.
     *
     * @param id the id of the player to delete
     * @return the ResponseEntity with status 200 (OK)
     */
    @DeleteMapping("/players/{id}")
    @Timed
    public ResponseEntity<Void> deletePlayer(@PathVariable Long id) {
        log.debug("REST request to delete Player : {}", id);
        playerRepository.delete(id);
        return ResponseEntity.ok().headers(HeaderUtil.createEntityDeletionAlert(ENTITY_NAME, id.toString())).build();
    }

}
