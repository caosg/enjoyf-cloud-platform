package com.enjoyf.platform.contentservice.web.rest;

import com.codahale.metrics.annotation.Timed;
import com.enjoyf.platform.autoconfigure.context.CommonContextHolder;
import com.enjoyf.platform.autoconfigure.security.EnjoySecurityUtils;
import com.enjoyf.platform.autoconfigure.web.CommonParams;
import com.enjoyf.platform.autoconfigure.web.error.BusinessException;
import com.enjoyf.platform.common.util.CollectionUtil;
import com.enjoyf.platform.contentservice.domain.Game;
import com.enjoyf.platform.contentservice.domain.GameExtJson;
import com.enjoyf.platform.contentservice.domain.enumeration.GameLine;
import com.enjoyf.platform.contentservice.feign.ProfileServiceFeignClient;
import com.enjoyf.platform.contentservice.feign.domain.WikiAppProfileDTO;
import com.enjoyf.platform.contentservice.service.GameService;
import com.enjoyf.platform.contentservice.service.GameTagService;
import com.enjoyf.platform.contentservice.web.rest.util.AskUtil;
import com.enjoyf.platform.contentservice.web.rest.vm.*;
import com.enjoyf.platform.contentservice.web.rest.util.HeaderUtil;
import com.enjoyf.platform.contentservice.web.rest.util.PaginationUtil;
import com.enjoyf.platform.page.ScoreRange;
import com.enjoyf.platform.page.ScoreRangeRows;
import com.enjoyf.platform.page.ScoreSort;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import io.github.jhipster.web.util.ResponseUtil;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.net.URI;
import java.net.URISyntaxException;
import java.util.*;
import java.util.stream.Collectors;

/**
 * REST controller for managing Game.
 */
@RestController
@RequestMapping("/api")
public class GameResource {

    private final Logger log = LoggerFactory.getLogger(GameResource.class);

    private static final String ENTITY_NAME = "game";

    private final GameService gameService;

    private final GameTagService gameTagService;

    private final ProfileServiceFeignClient profileServiceFeignClient;

    @Autowired
    private AskUtil askUtil;

    public GameResource(GameService gameService, GameTagService gameTagService, ProfileServiceFeignClient profileServiceFeignClient) {
        this.gameService = gameService;
        this.gameTagService = gameTagService;
        this.profileServiceFeignClient = profileServiceFeignClient;
    }

    /**
     * POST  /games : Create a new game.
     *
     * @return the ResponseEntity with status 201 (Created) and with body the new game, or with status 400 (Bad Request) if the game has already an ID
     * @throws URISyntaxException if the Location URI syntax is incorrect
     * @RequestBody Game game
     */
    @PostMapping("/games")
    @Timed
    public ResponseEntity<Game> createGame(@RequestBody Game game) throws URISyntaxException {
        log.debug("REST request to save Game : {}", game);
//        if (game.getId() != null) {
//            return ResponseEntity.badRequest().headers(HeaderUtil.createFailureAlert(ENTITY_NAME, "idexists", "A new game cannot already have an ID")).body(null);
//        }

        Game oldGame = gameService.findOne(game.getId());
        if (oldGame != null) {
            GameExtJson extJson = game.getExtJson();
            if (StringUtils.isEmpty(game.getExtJson().getRecommend())) {
                extJson.setRecommend(oldGame.getExtJson().getRecommend());
                extJson.setRecommendAuth(oldGame.getExtJson().getRecommendAuth());
                game.setExtJson(extJson);
            } else {
                extJson = oldGame.getExtJson();
                extJson.setRecommend(game.getExtJson().getRecommend());
                extJson.setRecommendAuth(game.getExtJson().getRecommendAuth());
            }
            //分数一直不变
            extJson.setScore(oldGame.getExtJson().getScore());
            extJson.setScoreSum(oldGame.getExtJson().getScoreSum());
            game.setCreateTime(oldGame.getCreateTime());
        }

        Game result = gameService.save(game);
        return ResponseEntity.created(new URI("/api/games/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * PUT  /games : Updates an existing game.
     *
     * @param game the game to update
     * @return the ResponseEntity with status 200 (OK) and with body the updated game,
     * or with status 400 (Bad Request) if the game is not valid,
     * or with status 500 (Internal Server Error) if the game couldnt be updated
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PutMapping("/games")
    @Timed
    public ResponseEntity<Game> updateGame(@RequestBody Game game) throws URISyntaxException {
        log.debug("REST request to update Game : {}", game);
        if (game.getId() == null) {
            return createGame(game);
        }
        Game result = gameService.save(game);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(ENTITY_NAME, game.getId().toString()))
            .body(result);
    }

    /**
     * GET  /games : get all the games.
     *
     * @param pageable the pagination information
     * @return the ResponseEntity with status 200 (OK) and the list of games in body
     */
    @GetMapping("/games")
    @Timed
    public ResponseEntity<List<Game>> getAllGames(@ApiParam Pageable pageable,
                                                  @RequestParam(value = "id", defaultValue = "") String id,
                                                  @RequestParam(value = "name", defaultValue = "") String name) {
        log.debug("REST request to get a page of Games");
        Page<Game> page = gameService.findAll(pageable, id, name);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(page, "/api/games");
        return new ResponseEntity<>(page.getContent(), headers, HttpStatus.OK);
    }

    /**
     * GET  /games/:id : get the "id" game.
     *
     * @param id the id of the game to retrieve
     * @return the ResponseEntity with status 200 (OK) and with body the game, or with status 404 (Not Found)
     */
    @GetMapping("/games/{id}")
    @Timed
    public ResponseEntity<Game> getGame(@PathVariable Long id) {
        log.debug("REST request to get Game : {}", id);
        Game game = gameService.findOne(id);
        return ResponseUtil.wrapOrNotFound(Optional.ofNullable(game));
    }


    @GetMapping("/app/games/{id}")
    @Timed
    public ResponseEntity<GameDetailVM> getAppGame(@PathVariable Long id) {
        log.debug("REST request to getAppGame GameDTO : {}", id);
        GameDetailVM gameDTO = gameService.findGameDetailVMById(id);
        return ResponseUtil.wrapOrNotFound(Optional.ofNullable(gameDTO));
    }

    /**
     * DELETE  /games/:id : delete the "id" game.
     *
     * @param id the id of the game to delete
     * @return the ResponseEntity with status 200 (OK)
     */
    @DeleteMapping("/games/{id}")
    @Timed
    public ResponseEntity<Void> deleteGame(@PathVariable Long id) {
        log.debug("REST request to delete Game : {}", id);
        gameService.delete(id);
        return ResponseEntity.ok().headers(HeaderUtil.createEntityDeletionAlert(ENTITY_NAME, id.toString())).build();
    }


    @GetMapping("/games/line/{gametagid}/{gameline}")
    @Timed
    @ApiOperation(value = "app获取游戏标签列表", response = GameVM.class)
    public ResponseEntity<ScoreRangeRows<GameVM>> getGameLines(
        @PathVariable String gametagid, @PathVariable int gameline,
        @RequestParam(value = "psize", defaultValue = "15") Integer pageSize,
        @RequestParam(name = "flag", defaultValue = "-1") Double flag) {
        log.debug("REST request to get getGameLines : {}", gameline, flag);
        ScoreRange scoreRange = new ScoreRange(-1, flag, pageSize, ScoreSort.DESC);
        GameLine gameLine = null;
        if (gameline == 0) {
            gameLine = GameLine.NEW_GAME_LIEN;
        } else if (gameline == 1) {
            gameLine = GameLine.HOT_GAME_LIEN;
        }
        ScoreRangeRows<GameVM> gameDTOScoreRangeRows = gameService.findByGameLine(gameLine, gametagid, scoreRange);
        return ResponseEntity.ok(gameDTOScoreRangeRows);
    }

    @GetMapping("/game/ids")
    @Timed
    @ApiOperation(value = "根据游戏id集合查询游戏", response = Boolean.class)
    public ResponseEntity<Map<Long, Game>> getByGameIds(@RequestParam(value = "ids") Long[] ids) {
        log.debug("REST request to get ids getByGameIds: {}", ids);
        Map<Long, Game> gameMap = gameService.findByGameids(Arrays.stream(ids).collect(Collectors.toSet()));
        return ResponseEntity.ok(gameMap);
    }


    @PostMapping("/games/playme/{id}")
    @Timed
    @ApiOperation(value = "app玩玩看", response = Boolean.class)
    public ResponseEntity<Boolean> createGamePlayme(@PathVariable Long id) throws URISyntaxException {
        log.debug("REST request to createGamePlayme Game : {}", id);

        Long userId = EnjoySecurityUtils.getCurrentUid();
        if (userId == null || userId <= 0) {
            throw new BusinessException("获取用户失败", "userId:" + userId);
        }
        gameService.createGamePlayme(id, userId);
        return ResponseEntity.ok(true);
    }

    @GetMapping("/games/mygame")
    @Timed
    @ApiOperation(value = "app我的游戏", response = GameVM.class)
    public ResponseEntity<ScoreRangeRows<GameVM>> mygame(@RequestParam(value = "psize", defaultValue = "16") Integer pageSize,
                                                         @RequestParam(name = "flag", defaultValue = "-1") Double flag) throws URISyntaxException {
        log.debug("REST request to mygame Game : {}", flag);
        Long userId = EnjoySecurityUtils.getCurrentUid();
        if (userId == null || userId <= 0) {
            throw new BusinessException("获取用户失败", "userId:" + userId);
        }
        ScoreRange scoreRange = new ScoreRange(-1, flag, pageSize, ScoreSort.DESC);
        ScoreRangeRows<GameVM> gameDTOScoreRangeRows = gameService.myGame(userId, scoreRange);
        return ResponseEntity.ok(gameDTOScoreRangeRows);
    }


    @GetMapping("/games/search/{text}")
    @Timed
    @ApiOperation(value = "点评游戏搜索", response = GameVM.class)
    public ResponseEntity<List<GameVM>> search(@ApiParam Pageable pageable, @PathVariable String text) throws URISyntaxException {
        log.debug("REST request to search Game : {}", text);

        Page<GameVM> page = gameService.searchGame(pageable, text);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(page, "/api/games/search/" + text);
        return new ResponseEntity<>(page.getContent(), headers, HttpStatus.OK);
    }


    @GetMapping("/games/line/index")
    @Timed
    @ApiOperation(value = "首页推荐游戏", response = GameIndexDataVM.class)
    public ResponseEntity<GameIndexDataVM> getGameLinesIndex(@RequestParam(value = "gamePsize", defaultValue = "16") Integer gamePsize,
                                                             @RequestParam(name = "gameFlag", defaultValue = "-1") Double gameFlag,
                                                             @RequestParam(name = "profileFlag", defaultValue = "-1") Double profileFlag,
                                                             @RequestParam(value = "profilePsize", defaultValue = "4") Integer profilePsize,
                                                             @RequestHeader HttpHeaders headers) {
        log.debug("REST request to get getGameLinesIndex : {}", gamePsize, gameFlag, profilePsize, profileFlag);
        //游戏
        ScoreRange scoreRange = new ScoreRange(-1, gameFlag, gamePsize, ScoreSort.DESC);
        ScoreRangeRows<GameVM> gameDTOScoreRangeRows = gameService.findByGameLine(GameLine.GAME_INDEX_SET, "", scoreRange);

        //推荐用户
        ScoreRangeRows<WikiAppProfileDTO> profileRange = new ScoreRangeRows<>();
        try {
            profileRange = profileServiceFeignClient.recommend(profilePsize, profileFlag);
        } catch (Exception e) {
            log.error("/games/line/index getGameLinesIndex recommend", e);
        }

        //返回数据
        GameIndexDataVM gameIndexDataVM = new GameIndexDataVM();
        List<GameIndexVM> rows = new ArrayList<>();


        //处理第一页
        if (gameFlag == -1) {
            processFirstPage(gameDTOScoreRangeRows, profileRange, rows, gameIndexDataVM);
        }

        if (gameFlag != -1) {
            processNextPage(gameDTOScoreRangeRows, profileRange, rows);
        }

        gameIndexDataVM.setRows(rows);
        gameIndexDataVM.setGameScoreRange(gameDTOScoreRangeRows.getRange());
        gameIndexDataVM.setProfileScoreRange(gameDTOScoreRangeRows.getRange());
        return ResponseEntity.ok(gameIndexDataVM);
    }

    private void processFirstPage(ScoreRangeRows<GameVM> gameDTOScoreRangeRows, ScoreRangeRows<WikiAppProfileDTO> profileRange,
                                  List<GameIndexVM> rows, GameIndexDataVM gameIndexDataVM) {
        int tempIndex = 0;
        List<WikiAppProfileDTO> wikiAppProfileDTOS = profileRange.getRows();
        List<WikiAppProfileDTO> tempProfie = new ArrayList<>();
        tempProfie.addAll(wikiAppProfileDTOS);

        //gameDTOScoreRangeRows.setRows(gameDTOScoreRangeRows.getRows().subList(0, 4));

        for (int i = 0; i < gameDTOScoreRangeRows.getRows().size(); i++) {
            if (i >= 8 && i % 4 == 0) {
                int start = tempIndex * 2;
                int end = tempIndex * 2 + 2;
                if (wikiAppProfileDTOS.size() > start) {
                    wikiAppProfileDTOS.subList(start, wikiAppProfileDTOS.size() > end ? end : wikiAppProfileDTOS.size()).forEach(dto -> {
                        rows.add(new GameIndexVM(3, dto));
                        tempProfie.remove(dto);
                    });
                }
                tempIndex++;
            }
            rows.add(new GameIndexVM(1, gameDTOScoreRangeRows.getRows().get(i)));
            if (i == 3) {
                Long uid = EnjoySecurityUtils.getCurrentUid();
                List<GameTagVM> list = gameTagService.findGameTagByuid(uid);
                rows.add(new GameIndexVM(2, list));
            }
        }


        //处理游戏不足的情况
        if (!CollectionUtil.isEmpty(tempProfie)) {
            //先放标签
            if (gameDTOScoreRangeRows.getRows().size() < 4) {
                Long uid = EnjoySecurityUtils.getCurrentUid();
                List<GameTagVM> list = gameTagService.findGameTagByuid(uid);
                rows.add(new GameIndexVM(2, list));
            }
            tempProfie.forEach(dto -> {
                rows.add(new GameIndexVM(3, dto));
            });
        }


        //轮播图
        CommonParams commonParams = CommonContextHolder.getContext().getCommonParams();
        if (commonParams != null) {
            gameIndexDataVM.setHeadItems(askUtil.getSlideshow(commonParams.getPlatform()));
        }
    }

    private void processNextPage(ScoreRangeRows<GameVM> gameDTOScoreRangeRows, ScoreRangeRows<WikiAppProfileDTO> profileRange, List<GameIndexVM> rows) {
        int tempIndex = 0;
        List<WikiAppProfileDTO> wikiAppProfileDTOS = profileRange.getRows();
        List<WikiAppProfileDTO> tempProfie = new ArrayList<>();
        tempProfie.addAll(wikiAppProfileDTOS);
        for (int i = 0; i < gameDTOScoreRangeRows.getRows().size(); i++) {
            if (i % 4 == 0) {
                int start = tempIndex * 2;
                int end = tempIndex * 2 + 2;
                if (wikiAppProfileDTOS.size() > start) {
                    wikiAppProfileDTOS.subList(start, wikiAppProfileDTOS.size() > end ? end : wikiAppProfileDTOS.size()).forEach(dto -> {
                        rows.add(new GameIndexVM(3, dto));
                        tempProfie.remove(dto);
                    });
                }
                tempIndex++;
            }
            rows.add(new GameIndexVM(1, gameDTOScoreRangeRows.getRows().get(i)));
        }


        if (!CollectionUtil.isEmpty(tempProfie)) {
            tempProfie.forEach(dto -> {
                rows.add(new GameIndexVM(3, dto));
            });
        }
    }

}
