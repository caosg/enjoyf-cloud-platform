package com.enjoyf.platform.contentservice.repository.redis;

import com.enjoyf.platform.common.util.StringUtil;
import com.enjoyf.platform.contentservice.domain.Comment;
import com.enjoyf.platform.contentservice.domain.CommentRating;
import com.enjoyf.platform.contentservice.domain.enumeration.CommentRatingType;
import com.enjoyf.platform.page.ScoreRange;
import com.google.gson.Gson;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.stereotype.Component;

import java.util.*;

/**
 * Created by pengxu on 2017/6/20.
 */
@Component
public class CommentRedisRepositoryImpl extends AbstractRedis implements CommentRedisRepository {
    private static final String PREFIX_NEW = "comment_micro";

    private static final String KEY_COMMENT_RATING = PREFIX_NEW + "_comment_rating_";//评分信息key
    private static final String KEY_COMMENT_ = PREFIX_NEW + "_comment_";//点评实体
    private static final String KEY_COMMENT_AGREE_NUM = PREFIX_NEW + "_comment_agree_num";//点评点赞数量
    private static final String KEY_COMMENT_REPLY_NUM = PREFIX_NEW + "_comment_reply_num";//点评评论数量
    private static final String KEY_COMMENT_RATING_ENTITY = PREFIX_NEW + "_comment_rating_entity_";//评分实体

    private static final String KEY_COMMENT_HOT_ = PREFIX_NEW + "_comment_hot_";//点评列表 最热
    private static final String KEY_COMMENT_NEW_ = PREFIX_NEW + "_comment_new_";//点评列表 最新

    private static final String KEY_COMMENT_AGREE_LIST_ = PREFIX_NEW + "_comment_agree_list_";//点赞列表

    private static final String KEY_MY_LIST_ = PREFIX_NEW + "_my_list_";//我的点评列表
    private static final String KEY_MY_COMMENT_BY_GAME_LIST_ = PREFIX_NEW + "_my_comment_by_game_list";//我的点评过的游戏列表


    private StringRedisTemplate redisTemplate;

    public CommentRedisRepositoryImpl(StringRedisTemplate redisTemplate) {
        this.redisTemplate = redisTemplate;
    }

    @Override
    public void addCommentEntity(Comment comment) {
        redisTemplate.opsForValue().set(KEY_COMMENT_ + comment.getId(), new Gson().toJson(comment));
        Integer num = comment.getAgreeNum();
        redisTemplate.opsForValue().set(KEY_COMMENT_AGREE_NUM + comment.getId(), (num == null || num == 0) ? "0" : String.valueOf(num));
    }

    @Override
    public Comment getCommentEntity(long id) {
        String entity = redisTemplate.opsForValue().get(KEY_COMMENT_ + id);
        if (StringUtil.isEmpty(entity)) {
            return null;
        }
        Comment comment = new Gson().fromJson(entity, Comment.class);
        String agreeNum = redisTemplate.opsForValue().get(KEY_COMMENT_AGREE_NUM + id);
        String replyNum = redisTemplate.opsForValue().get(KEY_COMMENT_REPLY_NUM + id);
        if (!StringUtil.isEmpty(agreeNum)) {
            comment.setAgreeNum(Integer.parseInt(agreeNum));
        }
        if (!StringUtil.isEmpty(replyNum)) {
            comment.setReplyNum(Integer.parseInt(replyNum));
        }
        return comment;
    }

    public void delCommentEntity(long id) {
        redisTemplate.delete(KEY_COMMENT_ + id);
    }


    @Override
    public void addCommentRatingEntity(CommentRating commentRating) {
        redisTemplate.opsForValue().set(KEY_COMMENT_RATING_ENTITY + commentRating.getGameId(), new Gson().toJson(commentRating));
    }

    @Override
    public void addCommentList(Comment comment) {
        if (StringUtil.isEmpty(comment.getBody())) {
            return;
        }
        long milli = comment.getCreateTime().getTime();
        String hotMilli = comment.getAgreeNum() + "." + comment.getId();
        redisTemplate.opsForZSet().add(KEY_COMMENT_NEW_ + comment.getGameId(), String.valueOf(comment.getId()), milli);
        redisTemplate.opsForZSet().add(KEY_COMMENT_HOT_ + comment.getGameId(), String.valueOf(comment.getId()), Double.valueOf(hotMilli + "1"));
    }

    @Override
    public boolean removeCommentListById(Comment comment) {
        redisTemplate.opsForZSet().remove(KEY_COMMENT_HOT_ + comment.getGameId(), String.valueOf(comment.getId()));
        redisTemplate.opsForZSet().remove(KEY_COMMENT_NEW_ + comment.getGameId(), String.valueOf(comment.getId()));
        return true;
    }

    @Override
    public Set<Long> findCommentListByGameId(int type, long gameId, ScoreRange scoreRange) {
        Set<String> set = zrangeByScore(redisTemplate, (type == 0 ? KEY_COMMENT_NEW_ : KEY_COMMENT_HOT_) + gameId, scoreRange);
        Set<Long> idSet = new LinkedHashSet<>();
        set.forEach(String -> idSet.add(Long.parseLong(String)));
        return idSet;
    }

    @Override
    public Long getCommentCount(long gameId) {
        return redisTemplate.opsForZSet().zCard(KEY_COMMENT_NEW_ + gameId);
    }

    @Override
    public Long getUserCommentCount(long uid) {
        return redisTemplate.opsForZSet().zCard(KEY_MY_LIST_ + uid);
    }

    @Override
    public Long getAgreeCountByCommentId(long commentId) {
        return redisTemplate.opsForZSet().zCard(KEY_COMMENT_AGREE_LIST_ + commentId);
    }

    @Override
    public void addCommentAgreeList(Long commentId, Long uid) {
        redisTemplate.opsForZSet().add(KEY_COMMENT_AGREE_LIST_ + commentId, String.valueOf(uid), System.currentTimeMillis());
    }

    @Override
    public boolean getUserAgreeStatus(Long id, Long uid) {
        Double score = redisTemplate.opsForZSet().score(KEY_COMMENT_AGREE_LIST_ + id, String.valueOf(uid));
        if (score == null) {
            return false;
        }
        return true;
    }


    public Set<Long> findCommentAgreeList(long commentId, ScoreRange scoreRange) {
        Set<String> set = zrangeByScore(redisTemplate, KEY_COMMENT_AGREE_LIST_ + commentId, scoreRange);
        Set<Long> idSet = new LinkedHashSet<>();
        set.forEach(String -> idSet.add(Long.parseLong(String)));
        return idSet;
    }

    @Override
    public Set<Long> queryMyCommentsByUid(Long uid, ScoreRange scoreRange) {
        Set<String> set = zrangeByScore(redisTemplate, KEY_MY_LIST_ + uid, scoreRange);
        Set<Long> idSet = new LinkedHashSet<>();
        set.forEach(String -> idSet.add(Long.parseLong(String)));
        return idSet;
    }

    @Override
    public void addMyCommentList(Comment comment) {
        long milli = comment.getCreateTime().getTime();
        redisTemplate.opsForZSet().add(KEY_MY_LIST_ + comment.getUid(), String.valueOf(comment.getId()), milli);
        redisTemplate.opsForZSet().add(KEY_MY_COMMENT_BY_GAME_LIST_ + comment.getUid(), String.valueOf(comment.getGameId()), milli);
    }

    @Override
    public boolean removeMyCommentListById(long uid, Comment comment) {
        redisTemplate.opsForZSet().remove(KEY_MY_LIST_ + uid, String.valueOf(comment.getId()));
        redisTemplate.opsForZSet().remove(KEY_MY_COMMENT_BY_GAME_LIST_ + uid, String.valueOf(comment.getGameId()));
        return true;
    }

    @Override
    public Map<Long, Boolean> queryMyCommentByGameList(long uid, Set<Long> gameIsd) {
        Map<Long, Boolean> returnMap = new HashMap<>();
        gameIsd.forEach(gameId -> {
            Long zrank = redisTemplate.opsForZSet().rank(KEY_MY_COMMENT_BY_GAME_LIST_ + uid, String.valueOf(gameId));
            returnMap.put(gameId, zrank == null ? false : true);
        });
        return returnMap;
    }


    @Override
    public void increaseAgree(Comment comment) {
        long agreeNum = incrementCommentAgreeNum(comment.getId());
        String hotMilli = agreeNum + "." + comment.getId();
        redisTemplate.opsForZSet().add(KEY_COMMENT_HOT_ + comment.getGameId(), String.valueOf(comment.getId()), Double.valueOf(hotMilli + "1"));
    }

    private long incrementCommentAgreeNum(long id) {
        return redisTemplate.opsForValue().increment(KEY_COMMENT_AGREE_NUM + id, 1);
    }

    @Override
    public void increaseReplyNum(Long id, Integer num) {
        long value = redisTemplate.opsForValue().increment(KEY_COMMENT_REPLY_NUM + id, num);
        if (value < 0) {
            redisTemplate.opsForValue().set(KEY_COMMENT_REPLY_NUM + id, "0");
        }
    }


    @Override
    public CommentRating getCommentRatingEntity(Long gameId) {
        String entity = redisTemplate.opsForValue().get(KEY_COMMENT_RATING_ENTITY + gameId);
        if (StringUtil.isEmpty(entity)) {
            return null;
        }
        return new Gson().fromJson(entity, CommentRating.class);
    }

    public void delCommentRatingEntity(Long gameId) {
        redisTemplate.delete(KEY_COMMENT_RATING_ENTITY + gameId);
    }

    @Override
    public boolean increaseCommentRating(CommentRating commentRating, CommentRatingType commentRatingType, Boolean boolType) {
        redisTemplate.opsForHash().increment(KEY_COMMENT_RATING + commentRating.getGameId(), CommentRatingType.SCORE_SUM.getCode(), commentRating.getScoreSum());
        redisTemplate.opsForHash().increment(KEY_COMMENT_RATING + commentRating.getGameId(), CommentRatingType.SCORE_NUM.getCode(), commentRating.getScoreNum());
        redisTemplate.opsForHash().increment(KEY_COMMENT_RATING + commentRating.getGameId(), commentRatingType.getCode(), boolType ? 1 : -1);

        return true;
    }

    @Override
    public boolean increaseCommentRating(CommentRating commentRating, CommentRatingType newCommentRatingType, CommentRatingType oldCommentRatingType) {
        redisTemplate.opsForHash().increment(KEY_COMMENT_RATING + commentRating.getGameId(), CommentRatingType.SCORE_SUM.getCode(), commentRating.getScoreSum());
        redisTemplate.opsForHash().increment(KEY_COMMENT_RATING + commentRating.getGameId(), newCommentRatingType.getCode(), 1);
        redisTemplate.opsForHash().increment(KEY_COMMENT_RATING + commentRating.getGameId(), oldCommentRatingType.getCode(), -1);
        return true;
    }

    @Override
    public CommentRating getCommentRatingByRedis(CommentRating commentRating) {
        Object scoreNumObject = redisTemplate.opsForHash().get(KEY_COMMENT_RATING + commentRating.getGameId(), CommentRatingType.SCORE_NUM.getCode());
        Object scoreSumObject = redisTemplate.opsForHash().get(KEY_COMMENT_RATING + commentRating.getGameId(), CommentRatingType.SCORE_SUM.getCode());
        Object fiveObject = redisTemplate.opsForHash().get(KEY_COMMENT_RATING + commentRating.getGameId(), CommentRatingType.FIVE_USER_SUM.getCode());
        Object fourObject = redisTemplate.opsForHash().get(KEY_COMMENT_RATING + commentRating.getGameId(), CommentRatingType.FOUR_USER_SUM.getCode());
        Object threeObject = redisTemplate.opsForHash().get(KEY_COMMENT_RATING + commentRating.getGameId(), CommentRatingType.THREE_USER_SUM.getCode());
        Object twoObject = redisTemplate.opsForHash().get(KEY_COMMENT_RATING + commentRating.getGameId(), CommentRatingType.TWO_USER_SUM.getCode());
        Object oneObject = redisTemplate.opsForHash().get(KEY_COMMENT_RATING + commentRating.getGameId(), CommentRatingType.ONE_USER_SUM.getCode());
        commentRating.setScoreNum(scoreNumObject == null ? 0 : Integer.parseInt(scoreNumObject.toString()));
        commentRating.setScoreSum(scoreSumObject == null ? 0 : Double.parseDouble(scoreSumObject.toString()));
        commentRating.setFiveUserSum(fiveObject == null ? 0 : Integer.parseInt(fiveObject.toString()));
        commentRating.setFourUserSum(fourObject == null ? 0 : Integer.parseInt(fourObject.toString()));
        commentRating.setThreeUserSum(threeObject == null ? 0 : Integer.parseInt(threeObject.toString()));
        commentRating.setTwoUserSum(twoObject == null ? 0 : Integer.parseInt(twoObject.toString()));
        commentRating.setOneUserSum(oneObject == null ? 0 : Integer.parseInt(oneObject.toString()));

        return commentRating;
    }
}
