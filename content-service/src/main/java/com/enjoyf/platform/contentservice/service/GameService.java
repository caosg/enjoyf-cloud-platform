package com.enjoyf.platform.contentservice.service;

import com.enjoyf.platform.contentservice.domain.Game;
import com.enjoyf.platform.contentservice.domain.enumeration.GameLine;
import com.enjoyf.platform.contentservice.web.rest.vm.GameDetailVM;
import com.enjoyf.platform.contentservice.web.rest.vm.GameVM;
import com.enjoyf.platform.page.ScoreRange;
import com.enjoyf.platform.page.ScoreRangeRows;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.Map;
import java.util.Set;

/**
 * Service Interface for managing Game.
 */
public interface GameService {

    /**
     * Save a game.
     *
     * @param game the entity to save
     * @return the persisted entity
     */
    Game save(Game game);

    /**
     * Get all the games.
     *
     * @param pageable the pagination information
     * @return the list of entities
     */
    Page<Game> findAll(Pageable pageable, String id, String name);

    /**
     * Get the "id" game.
     *
     * @param id the id of the entity
     * @return the entity
     */
    Game findOne(Long id);

    /**
     * Delete the "id" game.
     *
     * @param id the id of the entity
     */
    void delete(Long id);


    /**
     * 根据游戏id集合查找游戏
     *
     * @param gameIds
     * @return
     */
    Map<Long, Game> findByGameids(Set<Long> gameIds);


    /**
     * 根据游戏id集合查找游戏
     *
     * @param gameIds
     * @return
     */
    Map<Long, GameVM> findByGameVMids(Set<Long> gameIds);


    /**
     * @param gameLine
     * @param scoreRange
     * @return
     */
    ScoreRangeRows<GameVM> findByGameLine(GameLine gameLine, String gameTagid, ScoreRange scoreRange);


    /**
     * 含有游戏的基本细细
     *
     * @param id
     * @return
     */
    GameVM findGameDTOById(Long id);


    /**
     * 包含简介等信息内容较多
     *
     * @param id
     * @return
     */
    GameDetailVM findGameDetailVMById(Long id);


    void createGamePlayme(Long id, Long userId);


    ScoreRangeRows<GameVM> myGame(Long userId, ScoreRange scoreRange);


    /**
     * @param id       游戏ID
     * @param score    评分
     * @param scoresum
     */
    void updateGame(Long id, Double score, Integer scoresum);


    Page<GameVM> searchGame(Pageable pageable, String searchText);


}
