package com.enjoyf.platform.contentservice.repository.redis;

import com.enjoyf.platform.common.util.StringUtil;
import com.enjoyf.platform.contentservice.domain.Comment;
import com.enjoyf.platform.contentservice.domain.CommentRating;
import com.enjoyf.platform.contentservice.domain.enumeration.CommentRatingType;
import com.enjoyf.platform.page.ScoreRange;
import com.google.gson.Gson;

import java.util.List;
import java.util.Map;
import java.util.Set;

/**
 * Created by pengxu on 2017/6/20.
 */
public interface CommentRedisRepository {

    /**
     * 点评实体
     *
     * @param comment
     */
    void addCommentEntity(Comment comment);

    /**
     * 查询点评实体
     *
     * @param id 点评ID
     * @return
     */
    Comment getCommentEntity(long id);

    /**
     * 新增点评
     *
     * @param comment
     */
    void addCommentList(Comment comment);

    void addCommentRatingEntity(CommentRating commentRating);

    /**
     * 删除某条点评
     *
     * @param comment
     * @return
     */
    boolean removeCommentListById(Comment comment);

    /**
     * @param type       0=最新  1=最热
     * @param gameId     游戏ID
     * @param scoreRange
     * @return
     */
    Set<Long> findCommentListByGameId(int type, long gameId, ScoreRange scoreRange);

    Long getCommentCount(long gameId);

    Long getUserCommentCount(long uid);

    Long getAgreeCountByCommentId(long commentId);

    CommentRating getCommentRatingEntity(Long gameId);

    //用户点评后更新评分信息
    boolean increaseCommentRating(CommentRating commentRating, CommentRatingType commentRatingType, Boolean boolType);


    /**
     * 用户修改评分后更新评分信息
     *
     * @param commentRating        更新信息
     * @param newCommentRatingType 修改后的星级
     * @param oldCommentRatingType 修改前的星级
     * @return
     */
    boolean increaseCommentRating(CommentRating commentRating, CommentRatingType newCommentRatingType, CommentRatingType oldCommentRatingType);

    /**
     * 根据游戏ID查询评分信息
     *
     * @param commentRating
     * @return
     */
    CommentRating getCommentRatingByRedis(CommentRating commentRating);

    /**
     * 新增点评的用户列表
     *
     * @param commentId d点评ID
     * @param uid       用户ID
     */
    void addCommentAgreeList(Long commentId, Long uid);

    /**
     * 查询用户是否点赞过
     *
     * @param commentId 点评ID
     * @param uid
     * @return
     */
    boolean getUserAgreeStatus(Long commentId, Long uid);

    /**
     * 查询点赞用户UID
     *
     * @param commentId  点评ID
     * @param scoreRange 分页信息
     * @return
     */
    Set<Long> findCommentAgreeList(long commentId, ScoreRange scoreRange);

    /**
     * 我的点评
     *
     * @param uid
     * @param scoreRange
     * @return
     */
    Set<Long> queryMyCommentsByUid(Long uid, ScoreRange scoreRange);

    void addMyCommentList(Comment comment);

    void delCommentEntity(long id);

    boolean removeMyCommentListById(long uid, Comment comment);

    /**
     * 查询用户点评过的游戏
     *
     * @param uid
     * @param gameIds return  Map<游戏ID, boolean>
     */
    Map<Long, Boolean> queryMyCommentByGameList(long uid, Set<Long> gameIds);

    /**
     * 新增点赞数量并点评列表热门排行
     *
     * @param comment
     */
    void increaseAgree(Comment comment);

    void increaseReplyNum(Long id, Integer num);


}
