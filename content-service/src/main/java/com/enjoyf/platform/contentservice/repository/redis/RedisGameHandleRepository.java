package com.enjoyf.platform.contentservice.repository.redis;

import com.enjoyf.platform.contentservice.domain.Game;
import com.enjoyf.platform.contentservice.domain.enumeration.GameLine;
import com.enjoyf.platform.page.ScoreRange;
import com.google.common.base.Splitter;
import org.apache.commons.lang3.StringUtils;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.stereotype.Component;
import org.springframework.util.CollectionUtils;

import java.util.HashSet;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Set;

/**
 * Created by zhimingli on 2017/6/21.
 */
@Component
public class RedisGameHandleRepository extends AbstractRedis {

    private static final String PREFIX = "contentservice:";

    //游戏库按标签最新
    private static final String GAMEDB_GAMETAG_SET = PREFIX + "gamedb_gametag_set:";

    //点评按标签最新
    private static final String NEW_GAMETAG_SET = PREFIX + "new_gametag_set:";

    //点评按标签最热   标签对的游戏集合
    private static final String HOT_GAMETAG_SET = PREFIX + "hot_gametag_set:";

    //首页游戏
    private static final String GAME_INDEX = PREFIX + "game_index_set:";

    //app默认标签
    private static final String APP_TAG_DEFAULT_SET = PREFIX + "app_tag_default_set:";

    //用户是否访问过游戏
    private static final String USER_TAG_HISTORY_GAMEID = PREFIX + "user_tag_history_gameid:";

    //用户标签记录集合
    private static final String USER_TAG_HISTORY_SET = PREFIX + "user_tag_history_set:";

    //用户玩玩看集合
    private static final String USER_PLAYGAME_SET = PREFIX + "user_playgame_set:";


    private StringRedisTemplate redisTemplate;

    public RedisGameHandleRepository(StringRedisTemplate redisTemplate) {
        this.redisTemplate = redisTemplate;
    }


    public void addGame(Game game) {
        String[] gameTagArr = game.getGameTag().split(",");
        double milli = game.getCreateTime().toInstant().toEpochMilli();
        for (String tagid : gameTagArr) {
            String hotMilli = Double.valueOf(game.getExtJson().getScore() * 10000).intValue() + "." + game.getExtJson().getScoreSum() + "1";
            redisTemplate.opsForZSet().add(NEW_GAMETAG_SET + tagid, String.valueOf(game.getId()), milli);
            redisTemplate.opsForZSet().add(HOT_GAMETAG_SET + tagid, String.valueOf(game.getId()), Double.valueOf(hotMilli));

        }
        //首页
        redisTemplate.opsForZSet().add(GAME_INDEX, String.valueOf(game.getId()), milli);
    }

    public Set<Long> findAllByGameLine(GameLine gameLine, String tagid, ScoreRange scoreRange) {
        Set<String> set = zrangeByScore(redisTemplate, gameLine.getCode() + tagid, scoreRange);
        Set<Long> idSet = new LinkedHashSet<>();
        for (String id : set) {
            try {
                idSet.add(Long.valueOf(id));
            } catch (NumberFormatException e) {
            }
        }
        return idSet;
    }

    public boolean removeGame(Game game) {
        String[] gameTagArr = game.getGameTag().split(",");
        for (String tagid : gameTagArr) {
            redisTemplate.opsForZSet().remove(NEW_GAMETAG_SET + tagid, game.getId().toString());
            redisTemplate.opsForZSet().remove(HOT_GAMETAG_SET + tagid, game.getId().toString());
        }
        redisTemplate.opsForZSet().remove(GAME_INDEX, String.valueOf(game.getId()));
        return true;
    }

    public boolean removeGameByGameTagID(Long gameid, String gameTagid) {
        redisTemplate.opsForZSet().remove(NEW_GAMETAG_SET + gameTagid, gameid.toString());
        redisTemplate.opsForZSet().remove(HOT_GAMETAG_SET + gameTagid, gameid.toString());
        redisTemplate.opsForZSet().remove(GAME_INDEX, gameid.toString());
        return true;
    }


    private void setUsertaghistoryGameid(Long userId, String gameid) {
        redisTemplate.opsForValue().set(USER_TAG_HISTORY_GAMEID + userId + "_" + gameid, gameid);
    }

    private boolean getUsertaghistoryGameid(Long userId, String gameid) {
        String result = redisTemplate.opsForValue().get(USER_TAG_HISTORY_GAMEID + userId + "_" + gameid);
        if (StringUtils.isEmpty(result)) {
            return false;
        }
        return true;
    }

    public void setUsertaghistorySet(Long userId, Game game) {
        boolean bval = getUsertaghistoryGameid(userId, String.valueOf(game.getId()));
        if (bval) {
            return;
        }
        List<String> gameTagList = Splitter.on(",").omitEmptyStrings().splitToList(game.getGameTag());
        gameTagList.forEach(tagid -> {
            Double zscore = redisTemplate.opsForZSet().score(USER_TAG_HISTORY_SET + userId, tagid);
            if (zscore == null) {
                redisTemplate.opsForZSet().add(USER_TAG_HISTORY_SET + userId, tagid, Double.valueOf(1 + "." + System.currentTimeMillis()));
            } else {
                redisTemplate.opsForZSet().add(USER_TAG_HISTORY_SET + userId, tagid, zscore + 1);
            }
        });
        setUsertaghistoryGameid(userId, String.valueOf(game.getId()));
    }


    public Set<String> getUsertaghistorySet(Long userId) {
        Set<String> tagSet = redisTemplate.opsForZSet().reverseRangeByScore(USER_TAG_HISTORY_SET + userId, 0, System.currentTimeMillis());
        return tagSet;
    }


    //游戏的默认标签
    public void setApptagDefaultSet(Long gameTagId) {
        redisTemplate.opsForZSet().add(APP_TAG_DEFAULT_SET, String.valueOf(gameTagId), System.currentTimeMillis());
    }

    public Set<String> getApptagDefaultSet() {
        Set<String> tagSet = redisTemplate.opsForZSet().reverseRangeByScore(APP_TAG_DEFAULT_SET, 0, System.currentTimeMillis());
        return tagSet;
    }

    public void removeApptagDefaultSet(Long gameTagId) {
        redisTemplate.opsForZSet().remove(APP_TAG_DEFAULT_SET, String.valueOf(gameTagId));
    }


    //游戏玩玩看
    public void setUserplaygameSet(Long uid, Long gameTagId) {
        boolean bval = getUserplaygame(uid, gameTagId);
        if (bval) {
            return;
        }
        redisTemplate.opsForZSet().add(USER_PLAYGAME_SET + uid, String.valueOf(gameTagId), System.currentTimeMillis());
    }

    public Set<Long> getUserplaygameSet(Long uid, ScoreRange scoreRange) {
        Set<String> gameIdSet = zrangeByScore(redisTemplate, USER_PLAYGAME_SET + uid, scoreRange);
        Set<Long> returnList = new LinkedHashSet<>();
        gameIdSet.forEach(id -> {
            returnList.add(Long.valueOf(id));
        });
        return returnList;
    }

    private boolean getUserplaygame(Long uid, Long gameTagId) {
        Long bval = redisTemplate.opsForZSet().rank(USER_PLAYGAME_SET + uid, String.valueOf(gameTagId));
        if (bval == null || bval <= 0) {
            return false;
        }
        return true;
    }


    /**
     * 获取标签对应的游戏的数量
     *
     * @param tagId
     * @return
     */
    public Set<String> getGameDbIdById(Long tagId) {
        Set<String> tagSet = redisTemplate.opsForZSet().reverseRangeByScore(GAMEDB_GAMETAG_SET + tagId, 0, System.currentTimeMillis());
        if (CollectionUtils.isEmpty(tagSet)) {
            tagSet = new HashSet<>();
        }
        return tagSet;
    }

    /**
     * gamedb库对应的游戏标签
     *
     * @param tagid
     * @param gameId
     */
    public void addGamedbByGameTagid(String tagid, String gameId) {
        redisTemplate.opsForZSet().add(GAMEDB_GAMETAG_SET + tagid, gameId, Double.valueOf(gameId));
    }


    /**
     * @param tagid
     * @param gameId
     */
    public void removeGamedbByGameTagid(String tagid, String gameId) {
        redisTemplate.opsForZSet().remove(GAMEDB_GAMETAG_SET + tagid, gameId);
    }

    public Set<Long> findGameDbByTagid(Long tagid, ScoreRange scoreRange) {
        Set<String> set = zrangeByScore(redisTemplate, GAMEDB_GAMETAG_SET + tagid, scoreRange);
        Set<Long> idSet = new LinkedHashSet<>();
        for (String id : set) {
            try {
                idSet.add(Long.valueOf(id));
            } catch (NumberFormatException e) {
            }
        }
        return idSet;
    }


}
