package com.enjoyf.platform.contentservice.service.impl;

import com.enjoyf.platform.autoconfigure.security.EnjoySecurityUtils;
import com.enjoyf.platform.contentservice.domain.enumeration.ValidStatus;
import com.enjoyf.platform.contentservice.repository.redis.RedisGameHandleRepository;
import com.enjoyf.platform.contentservice.repository.redis.RedisGameTagRepository;
import com.enjoyf.platform.contentservice.service.GameTagService;
import com.enjoyf.platform.contentservice.domain.GameTag;
import com.enjoyf.platform.contentservice.repository.jpa.GameTagRepository;
import com.enjoyf.platform.contentservice.service.mapper.GameTagMapper;
import com.enjoyf.platform.contentservice.web.rest.vm.GameTagVM;
import com.enjoyf.platform.contentservice.web.rest.vm.GameVM;
import com.enjoyf.platform.page.ScoreRange;
import com.enjoyf.platform.page.ScoreRangeRows;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

/**
 * Service Implementation for managing GameTag.
 */
@Service
@Transactional
public class GameTagServiceImpl implements GameTagService {

    private final Logger log = LoggerFactory.getLogger(GameTagServiceImpl.class);

    private final GameTagRepository gameTagRepository;

    private final RedisGameTagRepository redisGameTagRepository;

    private final RedisGameHandleRepository redisGameHandleRepository;

    public GameTagServiceImpl(GameTagRepository gameTagRepository, RedisGameTagRepository redisGameTagRepository, RedisGameHandleRepository redisGameHandleRepository) {
        this.gameTagRepository = gameTagRepository;
        this.redisGameTagRepository = redisGameTagRepository;
        this.redisGameHandleRepository = redisGameHandleRepository;
    }

    /**
     * Save a gameTag.
     *
     * @param gameTag the entity to save
     * @return the persisted entity
     */
    @Override
    public GameTag save(GameTag gameTag) {
        log.debug("Request to save GameTag : {}", gameTag);
        GameTag result = gameTagRepository.save(gameTag);
        redisGameTagRepository.save(result);
        return result;
    }

    /**
     * Get all the gameTags.
     *
     * @param pageable the pagination information
     * @return the list of entities
     */
    @Override
    @Transactional(readOnly = true)
    public Page<GameTag> findAll(Pageable pageable, String tagName, String validStatus) {
        log.debug("Request to get all GameTags:{}", pageable, tagName, validStatus);
        Page<GameTag> result = null;
        if (StringUtils.isEmpty(tagName) && StringUtils.isEmpty(validStatus)) {
            result = gameTagRepository.findAll(pageable);
        } else if (!StringUtils.isEmpty(tagName) && StringUtils.isEmpty(validStatus)) {
            result = gameTagRepository.findAllByTagNameLike(pageable, "%" + tagName + "%");
        } else if (StringUtils.isEmpty(tagName) && !StringUtils.isEmpty(validStatus)) {
            result = gameTagRepository.findAllByValidStatus(pageable, validStatus);
        } else {
            result = gameTagRepository.findAllByTagNameLikeAndValidStatus(pageable, "%" + tagName + "%", validStatus);
        }
        if (result != null && result.hasContent()) {
            result.getContent().forEach(s -> redisGameTagRepository.save(s));
        }
        return result;
    }

    /**
     * Get one gameTag by id.
     *
     * @param id the id of the entity
     * @return the entity
     */
    @Override
    @Transactional(readOnly = true)
    public GameTag findOne(Long id) {
        log.debug("Request to get GameTag : {}", id);

        GameTag gameTag = redisGameTagRepository.findOne(id);
        if (gameTag == null) {
            gameTag = gameTagRepository.findOne(id);
            if (gameTag != null) {
                redisGameTagRepository.save(gameTag);
            }
        }
        return gameTag;
    }

    /**
     * Delete the  gameTag by id.
     *
     * @param id the id of the entity
     */
    @Override
    public void delete(Long id) {
        log.debug("Request to delete GameTag : {}", id);
        gameTagRepository.delete(id);
        redisGameTagRepository.delete(id);
    }


    @Override
    public List<GameTag> findByGameids(List<Long> gameTagIds) {
        log.debug("Request to get findByGameids : {}", gameTagIds);
        List<GameTag> gameTags = new ArrayList<>();

        gameTagIds.forEach(id -> {
            if (id != null) {
                GameTag gameTag = findOne(id);
                if (gameTag != null) {
                    gameTags.add(gameTag);
                }
            }
        });
        return gameTags;
    }


    @Override
    public int setRecommendStatusById(Long id, Integer recommendStatus) {
        log.debug("Request to put setRecommendStatusById : {}", id, recommendStatus);
        int result = gameTagRepository.setRecommendStatusById(id, recommendStatus);
        if (recommendStatus == 0) {
            redisGameHandleRepository.removeApptagDefaultSet(id);
        } else {
            redisGameHandleRepository.setApptagDefaultSet(id);
        }
        return result;
    }

    @Override
    public int setValidStatusById(Long id, String validStatus) {
        log.debug("Request to put setValidStatusById : {}", id, validStatus);
        int result = gameTagRepository.setValidStatusById(id, validStatus);
        if (result > 0) {
            redisGameTagRepository.delete(id);
        }
        return result;
    }

    @Override
    public List<GameTagVM> findGameTagByuid(Long uid) {
        log.debug("Request to get findAppDefaultGameTag : {}");
        List<GameTagVM> returnList = new ArrayList<>();

        Set<String> gameTagSet = null;
        //有用户登录
        if (uid != null && uid > 0) {
            gameTagSet = redisGameHandleRepository.getUsertaghistorySet(uid);
            gameTagSet.forEach(tagid -> {
                GameTag gameTag = findOne(Long.valueOf(tagid));
                if (gameTag != null && ValidStatus.VALID.getCode().equals(gameTag.getValidStatus())) {
                    returnList.add(GameTagMapper.MAPPER.toGameTagVM(gameTag));
                }
            });
        }


        Set<String> apptagDefaultSet = redisGameHandleRepository.getApptagDefaultSet();

        Set<String> finalGameTagSet = gameTagSet;
        apptagDefaultSet.forEach(id -> {
            if (finalGameTagSet != null && finalGameTagSet.contains(String.valueOf(id))) {
                return;
            }
            GameTag gameTag = findOne(Long.valueOf(id));
            if (ValidStatus.VALID.getCode().equals(gameTag.getValidStatus())) {
                returnList.add(GameTagMapper.MAPPER.toGameTagVM(gameTag));
            }
        });

        return returnList.subList(0, returnList.size() > 8 ? 8 : returnList.size());
    }


    @Override
    public GameTag findByTagName(String tagName) {
        log.debug("Request to get findByTagName : {}", tagName);
        GameTag gameTag = redisGameTagRepository.findByTagName(tagName);
        if (gameTag == null) {
            gameTag = gameTagRepository.findByTagName(tagName);
        }
        return gameTag;
    }


    @Override
    public void setGameSum(Long gameDbId, List<String> tagIdList) {
        Set<String> tagSet = redisGameHandleRepository.getGameDbIdById(gameDbId);

        for (String id : tagIdList) {
            GameTag gameTag = redisGameTagRepository.findOne(Long.valueOf(id));
            if (gameTag != null) {
                if (!tagSet.contains(id)) {
                    redisGameHandleRepository.addGamedbByGameTagid(id, String.valueOf(gameDbId));
                    Set<String> tagSize = redisGameHandleRepository.getGameDbIdById(gameTag.getId());
                    gameTag.setGameSum(CollectionUtils.isEmpty(tagSize) ? 0 : tagSize.size());
                    save(gameTag);


                }
            }
        }

        tagSet.forEach(id -> {
            GameTag gameTag = redisGameTagRepository.findOne(Long.valueOf(id));
            if (gameTag != null) {
                if (!tagIdList.contains(id)) {
                    redisGameHandleRepository.removeGamedbByGameTagid(id, String.valueOf(gameDbId));
                    Set<String> tagSize = redisGameHandleRepository.getGameDbIdById(gameTag.getId());
                    gameTag.setGameSum(CollectionUtils.isEmpty(tagSize) ? 0 : tagSize.size() - 1);
                    save(gameTag);
                }
            }
        });

    }

    @Override
    public ScoreRangeRows<GameTagVM> getGamedbByTagid(Long tagid, ScoreRange scoreRange) {
        ScoreRangeRows<GameTagVM> result = new ScoreRangeRows<>();
        result.setRange(scoreRange);
        List<GameTagVM> gameList = new ArrayList<>();

        Set<Long> longSet = redisGameHandleRepository.findGameDbByTagid(tagid, scoreRange);

        longSet.forEach(id -> {
            GameTagVM gameTagVM = new GameTagVM();
            gameTagVM.setId(id);
            gameList.add(gameTagVM);
        });
        result.setRows(gameList);


        return result;
    }
}
