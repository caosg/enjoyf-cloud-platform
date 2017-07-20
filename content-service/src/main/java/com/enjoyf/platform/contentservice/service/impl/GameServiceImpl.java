package com.enjoyf.platform.contentservice.service.impl;

import com.enjoyf.platform.autoconfigure.security.EnjoySecurityUtils;
import com.enjoyf.platform.common.util.CollectionUtil;
import com.enjoyf.platform.contentservice.domain.*;
import com.enjoyf.platform.contentservice.domain.enumeration.GameLine;
import com.enjoyf.platform.contentservice.domain.enumeration.ValidStatus;
import com.enjoyf.platform.contentservice.repository.redis.CommentRedisRepository;
import com.enjoyf.platform.contentservice.repository.redis.RedisGameHandleRepository;
import com.enjoyf.platform.contentservice.repository.redis.RedisGameRepository;
import com.enjoyf.platform.contentservice.service.CommentRatingService;
import com.enjoyf.platform.contentservice.service.GameService;
import com.enjoyf.platform.contentservice.repository.jpa.GameRepository;
import com.enjoyf.platform.contentservice.service.GameTagService;
import com.enjoyf.platform.contentservice.web.rest.util.GameSolrUtil;
import com.enjoyf.platform.contentservice.web.rest.vm.GameDetailVM;
import com.enjoyf.platform.contentservice.web.rest.vm.GameVM;
import com.enjoyf.platform.contentservice.service.dto.GameTagDTO;
import com.enjoyf.platform.contentservice.service.mapper.GameTagMapper;
import com.enjoyf.platform.page.ScoreRange;
import com.enjoyf.platform.page.ScoreRangeRows;
import com.google.common.base.Splitter;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

import java.util.*;

/**
 * Service Implementation for managing Game.
 */
@Service
@Transactional
public class GameServiceImpl implements GameService {

    private final Logger log = LoggerFactory.getLogger(GameServiceImpl.class);

    private final GameRepository gameRepository;


    private final RedisGameRepository redisGameRepository;


    private final RedisGameHandleRepository redisGameHandleRepository;


    private final GameTagService gameTagService;


    private final CommentRatingService commentRatingService;

    private final CommentRedisRepository commentRedisRepository;

    @Autowired
    private GameSolrUtil gameSolrUtil;

    public GameServiceImpl(GameRepository gameRepository, RedisGameRepository redisGameRepository, RedisGameHandleRepository redisGameHandleRepository, GameTagService gameTagService, CommentRatingService commentRatingService, CommentRedisRepository commentRedisRepository) {
        this.gameRepository = gameRepository;
        this.redisGameRepository = redisGameRepository;
        this.redisGameHandleRepository = redisGameHandleRepository;
        this.gameTagService = gameTagService;
        this.commentRatingService = commentRatingService;
        this.commentRedisRepository = commentRedisRepository;
    }

    /**
     * Save a game.
     *
     * @param game the entity to save
     * @return the persisted entity
     */
    @Override
    public Game save(Game game) {
        log.debug("Request to save Game : {}", game);

        List<String> oldTagList = new ArrayList<>();
        if (game.getId() != null) {
            Game oldGame = findOne(game.getId());
            if (oldGame != null) {
                oldTagList = new ArrayList(Splitter.on(",").omitEmptyStrings().splitToList(oldGame.getGameTag()));
            }
        }

        Game result = null;
        if (game.getValidStatus().equals(ValidStatus.VALID.getCode())) {
            //db & redis
            result = gameRepository.save(game);
            redisGameRepository.save(result);

            //求差集
            List<String> newTagList = new ArrayList(Splitter.on(",").omitEmptyStrings().splitToList(result.getGameTag()));
            oldTagList.removeAll(newTagList);
            for (String gameTagid : oldTagList) {
                redisGameHandleRepository.removeGameByGameTagID(result.getId(), gameTagid);
            }
            redisGameHandleRepository.addGame(result);

            //点评评分表
            CommentRating commentRating = new CommentRating();
            commentRating.setGameId(game.getId());
            commentRatingService.save(commentRating);

            //solr
            gameSolrUtil.saveGame(game);

        } else {
            //db & redis
            gameRepository.setValidStatusById(game.getId(), ValidStatus.INVALID.getCode());
            redisGameRepository.delete(game.getId());

            //清除标签对应的游戏
            redisGameHandleRepository.removeGame(game);

            //solr
            result = findOne(game.getId());
            gameSolrUtil.deleteGame(result);


        }

        return result;
    }

    /**
     * Get all the games.
     *
     * @param pageable the pagination information
     * @return the list of entities
     */
    @Override
    @Transactional(readOnly = true)
    public Page<Game> findAll(Pageable pageable, String id, String name) {
        log.debug("Request to get all Games");
        Page<Game> result = null;
        if (!StringUtils.isEmpty(id)) {
            result = gameRepository.findAllById(pageable, Long.valueOf(id));
        } else if (!StringUtils.isEmpty(name)) {
            result = gameRepository.findAllByNameLike(pageable, "%" + name + "%");
        } else {
            result = gameRepository.findAll(pageable);
        }
        if (result != null && result.hasContent()) {
            result.getContent().forEach(s -> redisGameRepository.save(s));
        }
        return result;
    }

    /**
     * Get one game by id.
     *
     * @param id the id of the entity
     * @return the entity
     */
    @Override
    @Transactional(readOnly = true)
    public Game findOne(Long id) {
        log.debug("Request to get Game : {}", id);
        Game game = redisGameRepository.findOne(id);
        if (game == null) {
            game = gameRepository.findOne(id);
            if (game != null) {
                redisGameRepository.save(game);
            }
        }
        return game;
    }

    /**
     * Delete the  game by id.
     *
     * @param id the id of the entity
     */
    @Override
    public void delete(Long id) {
        log.debug("Request to delete Game : {}", id);
        Game game = findOne(id);
        gameRepository.delete(id);
        redisGameRepository.delete(id);
        redisGameHandleRepository.removeGame(game);

        gameSolrUtil.deleteGame(game);
    }


    @Override
    public Map<Long, Game> findByGameids(Set<Long> gameIds) {
        log.debug("Request to findByGameids gameIds : {}", gameIds);
        Map<Long, Game> gameMap = new HashMap<>();
        if (CollectionUtils.isEmpty(gameIds)) {
            return gameMap;
        }
        gameIds.forEach(id -> {
            Game game = findOne(id);
            if (game != null) {
                gameMap.put(id, game);
            }
        });
        return gameMap;
    }

    @Override
    public Map<Long, GameVM> findByGameVMids(Set<Long> gameIds) {
        log.debug("Request to findByGameVMids gameIds : {}", gameIds);
        Map<Long, GameVM> gameMap = new HashMap<>();
        if (CollectionUtils.isEmpty(gameIds)) {
            return gameMap;
        }
        gameIds.forEach(id -> {
            Game game = findOne(id);
            if (game != null) {
                gameMap.put(id, toGameDTO(game));
            }
        });
        return gameMap;
    }

    @Override
    public ScoreRangeRows<GameVM> findByGameLine(GameLine gameLine, String gameTagid, ScoreRange scoreRange) {
        log.debug("Request to findByGameLine gameLine : {}", gameLine, gameTagid, scoreRange);
        Set<Long> gameIdSet = redisGameHandleRepository.findAllByGameLine(gameLine, gameTagid, scoreRange);
        return buildScoreRangeRows(scoreRange, gameIdSet);
    }


    @Override
    public GameVM findGameDTOById(Long id) {
        log.debug("Request to findGameDTOById id : {}", id);
        Game game = findOne(id);
        //状态
        if (game == null || !ValidStatus.VALID.getCode().equals(game.getValidStatus())) {
            return null;
        }

        Long userId = EnjoySecurityUtils.getCurrentUid();
        String loginDomain = EnjoySecurityUtils.getCurrentLoginDomain();

        //处理用户阅读的标签
        if (!StringUtils.isEmpty(loginDomain) && !"client".equals(loginDomain)) {
            redisGameHandleRepository.setUsertaghistorySet(userId, game);
        }


        GameVM gameVM = toGameDTO(game);


        //是否点评过
        Set<Long> longSet = new HashSet<>();
        longSet.add(gameVM.getId());
        Map<Long, Boolean> userCommentStatus = commentRedisRepository.queryMyCommentByGameList(userId, longSet);
        gameVM.setHasComment(userCommentStatus.get(gameVM.getId()));

        return gameVM;
    }


    @Override
    public GameDetailVM findGameDetailVMById(Long id) {
        log.debug("Request to findGameDTOById id : {}", id);
        Game game = findOne(id);
        //状态
        if (game == null || !ValidStatus.VALID.getCode().equals(game.getValidStatus())) {
            return null;
        }

        Long userId = EnjoySecurityUtils.getCurrentUid();
        String loginDomain = EnjoySecurityUtils.getCurrentLoginDomain();

        //处理用户阅读的标签
        if (!StringUtils.isEmpty(loginDomain) && !"client".equals(loginDomain)) {
            redisGameHandleRepository.setUsertaghistorySet(userId, game);
        }

        GameDetailVM gameVM = toGameDetailVM(game);


        if (userId != null && userId > 0) {
            //是否点评过
            Set<Long> longSet = new HashSet<>();
            longSet.add(gameVM.getId());
            Map<Long, Boolean> userCommentStatus = commentRedisRepository.queryMyCommentByGameList(userId, longSet);
            gameVM.setHasComment(userCommentStatus.get(gameVM.getId()));
        }
        return gameVM;
    }

    @Override
    public void createGamePlayme(Long id, Long userId) {
        log.debug("Request to createGamePlayme id : {}", id, userId);
        redisGameHandleRepository.setUserplaygameSet(userId, id);
    }


    @Override
    public ScoreRangeRows<GameVM> myGame(Long userId, ScoreRange scoreRange) {
        log.debug("Request to myGame userId : {}", userId, scoreRange);


        Set<Long> gameIdSet = redisGameHandleRepository.getUserplaygameSet(userId, scoreRange);


        ScoreRangeRows<GameVM> gameVM = buildScoreRangeRows(scoreRange, gameIdSet);

        List<GameVM> gameVMList = gameVM.getRows();

        //是否点评过
        Map<Long, Boolean> userCommentStatus = commentRedisRepository.queryMyCommentByGameList(userId, gameIdSet);
        gameVMList.forEach(vm -> {
            vm.setHasComment(userCommentStatus.get(vm.getId()));
        });

        gameVM.setRows(gameVMList);

        return gameVM;
    }

    private ScoreRangeRows<GameVM> buildScoreRangeRows(ScoreRange scoreRange, Set<Long> gameIdSet) {
        ScoreRangeRows<GameVM> result = new ScoreRangeRows<>();
        result.setRange(scoreRange);
        if (CollectionUtil.isEmpty(gameIdSet)) {
            return result;
        }
        List<GameVM> gameList = new ArrayList<>();
        gameIdSet.forEach(id -> {
            Game game = findOne(id);
            if (game != null) {
                gameList.add(toGameDTO(game));
            }
        });
        result.setRows(gameList);
        return result;
    }


    @Override
    public void updateGame(Long id, Double score, Integer scoresum) {
        log.debug("Request to updateGame : {}", id, score, scoresum);
        Game game = findOne(id);
        if (game != null) {
            GameExtJson json = game.getExtJson();
            json.setScore(score);
            json.setScoreSum(scoresum);
            save(game);
            //更新游戏库评分及评分人数
            gameSolrUtil.updateGameDB(id, score, scoresum);
        }
    }

    @Override
    public Page<GameVM> searchGame(Pageable pageable, String searchText) {
        log.debug("Request to searchGame pageable,searchText : {}", pageable, searchText);
        Page<GameVM> pageRows = new PageImpl<GameVM>(new ArrayList<>(), pageable, 0);

        Page<FiledValue> filedValuePage = gameSolrUtil.searchGame(searchText, pageable);

        if (CollectionUtils.isEmpty(filedValuePage.getContent())) {
            return pageRows;
        }

        Set<Long> gameIdSet = new HashSet<Long>();
        for (FiledValue filedValue : filedValuePage.getContent()) {
            gameIdSet.add(Long.valueOf(filedValue.getKey()));
        }

        List<GameVM> gameVMList = new ArrayList<>();
        gameIdSet.forEach(id -> {
            Game game = findOne(id);
            if (game != null) {
                gameVMList.add(toGameDTO(game));
            }
        });
        pageRows = new PageImpl<GameVM>(gameVMList, pageable, filedValuePage.getTotalElements());
        return pageRows;
    }

    private GameVM toGameDTO(Game game) {
        GameVM gameVM = new GameVM();
        gameVM.setId(game.getId());
        gameVM.setName(game.getName());
        GameExtJson json = game.getExtJson();
        if (json != null) {
            gameVM.setGameLogo(StringUtils.isEmpty(json.getGameLogo()) ? "" : json.getGameLogo());
            gameVM.setScore(json.getScore());
            gameVM.setScoreSum(json.getScoreSum());
            gameVM.setRecommend(StringUtils.isEmpty(json.getRecommend()) ? "" : json.getRecommend());
            gameVM.setRecommendAuth(StringUtils.isEmpty(json.getRecommendAuth()) ? "" : json.getRecommendAuth());
            gameVM.setGameDeveloper(StringUtils.isEmpty(json.getGameDeveloper()) ? "" : json.getGameDeveloper());
        }

        gameVM.setCreateTime(game.getCreateTime().toInstant().toEpochMilli());
        //处理游戏标签
        List<GameTagDTO> gameTagDTOS = new ArrayList<>();
        List<String> gameTagids = Splitter.on(",").omitEmptyStrings().splitToList(game.getGameTag());
        List<Long> gameTagidsLong = new ArrayList<>();

        gameTagids.forEach(s -> {
            gameTagidsLong.add(Long.valueOf(s));
        });


        List<GameTag> gameTags = gameTagService.findByGameids(gameTagidsLong);


        gameTags.forEach(gametag -> {
            gameTagDTOS.add(GameTagMapper.MAPPER.toGameTagDTO(gametag));
        });
        gameVM.setGameTag(gameTagDTOS);
        return gameVM;
    }


    private GameDetailVM toGameDetailVM(Game game) {
        GameDetailVM gameDetailVM = GameTagMapper.MAPPER.toGameDetailVM(toGameDTO(game));
        GameExtJson json = game.getExtJson();
        if (json != null) {
            gameDetailVM.setGameDeveloper(StringUtils.isEmpty(json.getGameDeveloper()) ? "" : json.getGameDeveloper());
            gameDetailVM.setVideo(StringUtils.isEmpty(json.getVideo()) ? "" : json.getVideo());
            gameDetailVM.setPicList(Splitter.on(",").omitEmptyStrings().splitToList(json.getPic()));
            gameDetailVM.setBackPic(StringUtils.isEmpty(json.getBackPic()) ? "" : json.getBackPic());
            gameDetailVM.setPrice((StringUtils.isEmpty(json.getPrice()) ? "" : json.getPrice()));
            gameDetailVM.setVpn(json.isVpn());
            gameDetailVM.setLanguage(Splitter.on(",").omitEmptyStrings().splitToList(StringUtils.isEmpty(json.getLanguage()) ? "" : json.getLanguage()));
            gameDetailVM.setIosDownload(StringUtils.isEmpty(json.getIosDownload()) ? "" : json.getIosDownload());
            gameDetailVM.setAndroidDownload(StringUtils.isEmpty(json.getAndroidDownload()) ? "" : json.getAndroidDownload());
            gameDetailVM.setGameDesc(StringUtils.isEmpty(json.getGameDesc()) ? "" : json.getGameDesc());
        }
        return gameDetailVM;
    }

}
