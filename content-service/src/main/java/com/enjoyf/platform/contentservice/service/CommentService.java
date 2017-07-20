package com.enjoyf.platform.contentservice.service;

import com.enjoyf.platform.contentservice.domain.Comment;
import com.enjoyf.platform.contentservice.service.dto.CommentDTO;
import com.enjoyf.platform.contentservice.service.dto.CommentDetailDTO;
import com.enjoyf.platform.contentservice.service.dto.ProfileDTO;
import com.enjoyf.platform.page.ScoreRange;
import com.enjoyf.platform.page.ScoreRangeRows;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.Map;
import java.util.Set;

/**
 * Service Interface for managing Comment.
 */
public interface CommentService {

    /**
     * Save a comment.
     *
     * @param comment the entity to save
     * @return the persisted entity
     */
    Comment save(Comment comment);

    Comment update(Comment comment);

    Comment findGameIdAndUid(long gameId, long uid);

    /**
     * Get all the comments.
     *
     * @param pageable the pagination information
     * @return the list of entities
     */
    Page<Comment> findAll(Pageable pageable);

    /**
     * Get the "id" comment.
     *
     * @param id the id of the entity
     * @return the entity
     */
    Comment findOne(Long id);

    /**
     * Delete the "id" comment.
     *
     * @param id the id of the entity
     */
    void delete(Long id);

//    Comment updateComment(Long id,Comment comment);

    /**
     * 点评列表
     *
     * @param gameId     游戏ID
     * @param type       0=最新  1=热门
     * @param scoreRange
     * @param currentUid 当前登录ID  查询是否点赞过使用
     * @return
     */
    ScoreRangeRows<CommentDTO> queryCommentByGameId(long gameId, int type, ScoreRange scoreRange, Long currentUid);

    /**
     * 获得点评详情
     *
     * @param id         点评表的ID
     * @param currentUid 当前登录用户的UID  查询是否点赞过
     * @return
     */
    CommentDetailDTO getCommentDetailById(long id, Long currentUid);

    Map<Long, CommentDTO> findCommentByIds(Set<Long> ids, Long currentUid);

    /**
     * 点赞
     *
     * @param id  //点评ID
     * @param uid //用户ID
     */
    boolean putAgree(Long id, Long uid);


    /**
     * 查询用户是否点过赞
     *
     * @param id  //点评ID
     * @param uid //用户ID
     * @return
     */
    boolean getUserAgreeStatus(Long id, Long uid);

    /**
     * 通过UID查点评
     *
     * @param uid 需要查询点评列表的ID
     * @param scoreRange
     * @param currentUid 当前登录ID
     * @return
     */
    ScoreRangeRows<CommentDetailDTO> queryMyComments(Long uid, ScoreRange scoreRange, Long currentUid);

    /**
     * 查询用户点评状态
     *
     * @param uid
     * @param gameId
     * @return
     */
    Map<Long, Boolean> getUserCommentStatus(long uid, Set<Long> gameId);

    Page<Comment> findAll(String validStatus, Long gameId, Long commentId, String commentBody, Pageable pageable);

    boolean deleteCommentById(Long id, String validStatus);

    ScoreRangeRows<ProfileDTO> queryAgreeListByCommentId(Long id, ScoreRange scoreRange);

    /**
     * 点评被评论以后数量增加操作
     *
     * @param id
     * @param num
     */
    void increaseReplyNumById(Long id, int num);
}
