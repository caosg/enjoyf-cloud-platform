package com.enjoyf.platform.contentservice.web.rest;

import com.codahale.metrics.annotation.Timed;
import com.enjoyf.platform.autoconfigure.security.EnjoySecurityUtils;
import com.enjoyf.platform.autoconfigure.web.error.BusinessException;
import com.enjoyf.platform.autoconfigure.web.error.CustomParameterizedException;
import com.enjoyf.platform.contentservice.domain.GameTag;
import com.enjoyf.platform.contentservice.service.GameTagService;
import com.enjoyf.platform.contentservice.web.rest.util.HeaderUtil;
import com.enjoyf.platform.contentservice.web.rest.util.PaginationUtil;
import com.enjoyf.platform.contentservice.web.rest.vm.GameTagVM;
import com.enjoyf.platform.page.ScoreRange;
import com.enjoyf.platform.page.ScoreRangeRows;
import com.enjoyf.platform.page.ScoreSort;
import com.google.common.base.Splitter;
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

import java.net.URI;
import java.net.URISyntaxException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

/**
 * REST controller for managing GameTag.
 */
@RestController
@RequestMapping("/api")
public class GameTagResource {

    private final Logger log = LoggerFactory.getLogger(GameTagResource.class);

    private static final String ENTITY_NAME = "gameTag";

    private final GameTagService gameTagService;

    public GameTagResource(GameTagService gameTagService) {
        this.gameTagService = gameTagService;
    }

    /**
     * POST  /game-tags : Create a new gameTag.
     *
     * @param gameTag the gameTag to create
     * @return the ResponseEntity with status 201 (Created) and with body the new gameTag, or with status 400 (Bad Request) if the gameTag has already an ID
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PostMapping("/game-tags")
    @Timed
    public ResponseEntity<GameTag> createGameTag(@RequestBody GameTag gameTag) throws URISyntaxException {
        log.debug("REST request to save GameTag : {}", gameTag);
        if (gameTag.getId() != null) {
            return ResponseEntity.badRequest().headers(HeaderUtil.createFailureAlert(ENTITY_NAME, "idexists", "A new gameTag cannot already have an ID")).body(null);
        }

        GameTag tag = gameTagService.findByTagName(gameTag.getTagName());
        if (tag != null) {
            throw new CustomParameterizedException("tagName存在", gameTag.getTagName());
        }

        GameTag result = gameTagService.save(gameTag);
        return ResponseEntity.created(new URI("/api/game-tags/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * PUT  /game-tags : Updates an existing gameTag.
     *
     * @param gameTag the gameTag to update
     * @return the ResponseEntity with status 200 (OK) and with body the updated gameTag,
     * or with status 400 (Bad Request) if the gameTag is not valid,
     * or with status 500 (Internal Server Error) if the gameTag couldnt be updated
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PutMapping("/game-tags")
    @Timed
    public ResponseEntity<GameTag> updateGameTag(@RequestBody GameTag gameTag) throws URISyntaxException {
        log.debug("REST request to update GameTag : {}", gameTag);
        if (gameTag.getId() == null) {
            return createGameTag(gameTag);
        }

        GameTag tag = gameTagService.findByTagName(gameTag.getTagName());
        if (tag != null && tag.getId() != gameTag.getId()) {
            throw new BusinessException("tagName存在", gameTag.getTagName());
        }

        GameTag result = gameTagService.save(gameTag);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(ENTITY_NAME, gameTag.getId().toString()))
            .body(result);
    }

    /**
     * GET  /game-tags : get all the gameTags.
     *
     * @param pageable the pagination information
     * @return the ResponseEntity with status 200 (OK) and the list of gameTags in body
     */
    @GetMapping("/game-tags")
    @Timed
    public ResponseEntity<List<GameTag>> getAllGameTags(@ApiParam Pageable pageable,
                                                        @RequestParam(value = "tagName", defaultValue = "") String tagName,
                                                        @RequestParam(value = "validStatus", defaultValue = "") String validStatus) {
        log.debug("REST request to get a page of GameTags");
        Page<GameTag> page = gameTagService.findAll(pageable, tagName, validStatus);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(page, "/api/game-tags");
        return new ResponseEntity<>(page.getContent(), headers, HttpStatus.OK);
    }

    /**
     * GET  /game-tags/:id : get the "id" gameTag.
     *
     * @param id the id of the gameTag to retrieve
     * @return the ResponseEntity with status 200 (OK) and with body the gameTag, or with status 404 (Not Found)
     */
    @GetMapping("/game-tags/{id}")
    @Timed
    public ResponseEntity<GameTag> getGameTag(@PathVariable Long id) {
        log.debug("REST request to get GameTag : {}", id);
        GameTag gameTag = gameTagService.findOne(id);
        return ResponseUtil.wrapOrNotFound(Optional.ofNullable(gameTag));
    }


    @GetMapping("/game-tags/ids")
    @Timed
    public ResponseEntity<List<GameTag>> getGameTag(@RequestParam(value = "ids") Long[] ids) {
        log.debug("REST request to get getGameTag : {}", ids);
        List<GameTag> gameTagList = gameTagService.findByGameids(Arrays.stream(ids).collect(Collectors.toList()));
        return ResponseEntity.ok(gameTagList);
    }


    /**
     * DELETE  /game-tags/:id : delete the "id" gameTag.
     *
     * @param id the id of the gameTag to delete
     * @return the ResponseEntity with status 200 (OK)
     */
    @DeleteMapping("/game-tags/{id}")
    @Timed
    public ResponseEntity<Void> deleteGameTag(@PathVariable Long id) {
        log.debug("REST request to delete GameTag : {}", id);
        gameTagService.delete(id);
        return ResponseEntity.ok().headers(HeaderUtil.createEntityDeletionAlert(ENTITY_NAME, id.toString())).build();
    }


    @PutMapping("/game-tags/recommend/{id}/{status}")
    @Timed
    public ResponseEntity<Boolean> updateGameTagRecommend(@PathVariable Long id, @PathVariable Integer status) throws URISyntaxException {
        log.debug("REST request to updateGameTagRecommend GameTag : {}", status);
        gameTagService.setRecommendStatusById(id, status);
        return ResponseEntity.ok(true);
    }


    @PutMapping("/game-tags/validstatus/{id}/{status}")
    @Timed
    public ResponseEntity<Boolean> updateGameTagValidstatus(@PathVariable Long id, @PathVariable String status) throws URISyntaxException {
        log.debug("REST request to updateGameTagRecommend GameTag : {}", status);
        gameTagService.setValidStatusById(id, status);
        return ResponseEntity.ok(true);
    }


    @GetMapping("/game-tags/my")
    @Timed
    @ApiOperation(value = "获取我的游戏标签", response = Boolean.class)
    public ResponseEntity<List<GameTagVM>> getGameTagByuid() {
        log.debug("REST request to get getDefaultGameTag : {}");
        //先获取用户访问的标签
        Long uid = EnjoySecurityUtils.getCurrentUid();
        if (uid == null || uid == 0) {
            throw new BusinessException("获取用户失败", "uid:" + uid);
        }
        List<GameTagVM> gameTagVMList = gameTagService.findGameTagByuid(uid);
        return ResponseEntity.ok(gameTagVMList);
    }


    @GetMapping("/game-tags/uid/{uid}")
    @Timed
    @ApiOperation(value = "获取他人游戏标签", response = Boolean.class)
    public ResponseEntity<List<GameTagVM>> getGameTagByuid(@PathVariable Long uid) {
        log.debug("REST request to get getGameTagByuid : {}", uid);

        List<GameTagVM> gameTagVMList = gameTagService.findGameTagByuid(uid);
        return ResponseEntity.ok(gameTagVMList);
    }

    @PostMapping("/game-tags/gamedb/{gameDbId}/{tagids}")
    @Timed
    public ResponseEntity<Boolean> addGameTag(@PathVariable Long gameDbId, @PathVariable String tagids) throws URISyntaxException {
        log.debug("REST request to save addGameTag : {}", gameDbId, tagids);

        List<String> tagsList = new ArrayList<>();
        tagsList = new ArrayList(Splitter.on(",").omitEmptyStrings().splitToList(tagids));

        gameTagService.setGameSum(gameDbId, tagsList);
        return ResponseEntity.ok(true);
    }

    @GetMapping("/game-tags/gamedb/{tagid}")
    @Timed
    @ApiOperation(value = "gamedb获取游戏列表", response = ScoreRangeRows.class)
    public ResponseEntity<ScoreRangeRows<GameTagVM>> getGameTag(
        @PathVariable Long tagid,
        @RequestParam(value = "psize", defaultValue = "20") Integer pageSize,
        @RequestParam(name = "flag", defaultValue = "-1") Double flag) {
        log.debug("REST request to get getGameTag : {}", tagid, flag);
        ScoreRange scoreRange = new ScoreRange(-1, flag, pageSize, ScoreSort.DESC);
        return ResponseEntity.ok(gameTagService.getGamedbByTagid(tagid, scoreRange));
    }

}
