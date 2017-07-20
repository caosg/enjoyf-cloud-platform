package com.enjoyf.platform.contentservice.service;

import com.enjoyf.platform.contentservice.domain.GameTag;
import com.enjoyf.platform.contentservice.web.rest.vm.GameTagVM;
import com.enjoyf.platform.page.ScoreRange;
import com.enjoyf.platform.page.ScoreRangeRows;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;
import java.util.Set;

/**
 * Service Interface for managing GameTag.
 */
public interface GameTagService {

    /**
     * Save a gameTag.
     *
     * @param gameTag the entity to save
     * @return the persisted entity
     */
    GameTag save(GameTag gameTag);

    /**
     * Get all the gameTags.
     *
     * @param pageable the pagination information
     * @return the list of entities
     */
    Page<GameTag> findAll(Pageable pageable, String tagName, String validStatus);

    /**
     * Get the "id" gameTag.
     *
     * @param id the id of the entity
     * @return the entity
     */
    GameTag findOne(Long id);

    /**
     * Delete the "id" gameTag.
     *
     * @param id the id of the entity
     */
    void delete(Long id);


    /**
     * 根据游戏标签id集合查找游戏标签
     *
     * @param gameTagIds
     * @return
     */
    List<GameTag> findByGameids(List<Long> gameTagIds);


    int setRecommendStatusById(Long id, Integer recommendStatus);


    int setValidStatusById(Long id, String validStatus);


    /**
     * 获取游戏标签
     * 包含：用户和默认标签
     *
     * @return
     */
    List<GameTagVM> findGameTagByuid(Long uid);

    GameTag findByTagName(String tagName);


    /**
     * 更新标签对应的游戏数
     *
     * @param tagIdList
     */
    void setGameSum(Long gameDbId, List<String> tagIdList);


    ScoreRangeRows<GameTagVM> getGamedbByTagid(Long tagid, ScoreRange scoreRange);
}
