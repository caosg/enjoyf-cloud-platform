package com.enjoyf.platform.contentservice.service.impl;

import com.enjoyf.platform.common.util.CollectionUtil;
import com.enjoyf.platform.common.util.StringUtil;
import com.enjoyf.platform.contentservice.domain.Comment;
import com.enjoyf.platform.contentservice.domain.CommentOperation;
import com.enjoyf.platform.contentservice.domain.enumeration.CommentOperationType;
import com.enjoyf.platform.contentservice.domain.enumeration.ValidStatus;
import com.enjoyf.platform.contentservice.feign.UserProfileFeignClient;
import com.enjoyf.platform.contentservice.feign.domain.UserProfile;
import com.enjoyf.platform.contentservice.repository.jpa.CommentRepository;
import com.enjoyf.platform.contentservice.repository.redis.CommentRedisRepository;
import com.enjoyf.platform.contentservice.service.CommentOperationService;
import com.enjoyf.platform.contentservice.service.CommentService;
import com.enjoyf.platform.contentservice.service.GameService;
import com.enjoyf.platform.contentservice.service.dto.CommentDTO;
import com.enjoyf.platform.contentservice.service.dto.CommentDetailDTO;
import com.enjoyf.platform.contentservice.service.dto.ProfileDTO;
import com.enjoyf.platform.contentservice.web.rest.vm.GameVM;
import com.enjoyf.platform.page.ScoreRange;
import com.enjoyf.platform.page.ScoreRangeRows;
import com.enjoyf.platform.page.ScoreSort;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;
import java.util.*;

/**
 * Service Implementation for managing Comment.
 */
@Service
@Transactional
public class CommentServiceImpl implements CommentService {

    private final Logger log = LoggerFactory.getLogger(CommentServiceImpl.class);

    private final CommentRepository commentRepository;
    private final UserProfileFeignClient userProfileFeignClient;
    private final CommentRedisRepository commentRedisRepository;
    private final GameService gameService;
    private final CommentOperationService commentOperationService;


    public CommentServiceImpl(CommentRepository commentRepository, UserProfileFeignClient userProfileFeignClient,
                              CommentRedisRepository commentRedisRepository, GameService gameService,
                              CommentOperationService commentOperationService) {
        this.commentRepository = commentRepository;
        this.userProfileFeignClient = userProfileFeignClient;
        this.commentRedisRepository = commentRedisRepository;
        this.gameService = gameService;
        this.commentOperationService = commentOperationService;
    }

    /**
     * Save a comment.
     *
     * @param comment the entity to save
     * @return the persisted entity
     */
    @Override
    public Comment save(Comment comment) {
        log.debug("Request to save Comment : {}", comment);
        Comment result = update(comment);
        if (result.getId() > 0) {
            commentRedisRepository.addCommentList(comment);
            commentRedisRepository.addMyCommentList(comment);
        }
        return result;
    }

    @Override
    public Comment update(Comment comment) {
        log.debug("Request to update Comment : {}", comment);
        Comment result = commentRepository.save(comment);
        commentRedisRepository.delCommentEntity(comment.getId());
        return result;
    }

    @Override
    public Comment findGameIdAndUid(long gameId, long uid) {
        log.debug("Request to update findGameIdAndUid : {},{}", gameId, uid);
        return commentRepository.findOneByGameIdAndUidAndValidStatus(gameId, uid, ValidStatus.VALID.getCode());
    }

    /**
     * Get all the comments.
     *
     * @param pageable the pagination information
     * @return the list of entities
     */
    @Override
    @Transactional(readOnly = true)
    public Page<Comment> findAll(Pageable pageable) {
        log.debug("Request to get all Comments");
        Page<Comment> result = commentRepository.findAll(pageable);
        return result;
    }

    @Transactional(readOnly = true)
    public Page<Comment> findAll(String validStatus, Long gameId, Long commentId, String commentBody, Pageable pageable) {
        log.debug("Request to get all Comments");

        Specification query = new Specification<Comment>() {
            @Override
            public Predicate toPredicate(Root<Comment> root, CriteriaQuery<?> criteriaQuery, CriteriaBuilder criteriaBuilder) {
                List<Predicate> predicates = new ArrayList<>();
                if (!StringUtil.isEmpty(validStatus)) {
                    predicates.add(criteriaBuilder.equal(root.get("validStatus"), validStatus));
                }
                if (gameId != 0) {
                    predicates.add(criteriaBuilder.equal(root.get("gameId"), gameId));
                }
                if (commentId != 0) {
                    predicates.add(criteriaBuilder.equal(root.get("id"), commentId));
                }
                if (!StringUtil.isEmpty(commentBody)) {
                    predicates.add(criteriaBuilder.like(root.get("body"), "%" + commentBody + "%"));
                }
                return criteriaBuilder.and(predicates.toArray(new Predicate[predicates.size()]));
            }
        };
        Page<Comment> page = commentRepository.findAll(query, pageable);


        return page;
    }

    /**
     * Get one comment by id.
     *
     * @param id the id of the entity
     * @return the entity
     */
    @Override
    @Transactional(readOnly = true)
    public Comment findOne(Long id) {
        log.debug("Request to get Comment : {}", id);
        Comment comment = commentRedisRepository.getCommentEntity(id);
        if (comment == null) {
            comment = commentRepository.findOne(id);
            if (comment != null) {
                commentRedisRepository.addCommentEntity(comment);
            }
        }
        return comment;
    }

    @Override
    public boolean deleteCommentById(Long id, String validStatus) {
        log.debug("Request to deleteCommentById id : {},{}", id);
        boolean bool = commentRepository.modifyCommentStatus(validStatus, id) > 0;
        if (bool) {
            Comment comment = findOne(id);
            commentRedisRepository.removeMyCommentListById(comment.getUid(), comment);
            commentRedisRepository.removeCommentListById(comment);
        }
        return bool;
    }

    @Override
    public ScoreRangeRows<ProfileDTO> queryAgreeListByCommentId(Long id, ScoreRange scoreRange) {
        log.debug("Request to queryAgreeListByCommentId id : {},{}", id, scoreRange);
        ScoreRangeRows<ProfileDTO> result = new ScoreRangeRows<>();
        Set<Long> uidSet = commentRedisRepository.findCommentAgreeList(id, scoreRange);
        if (CollectionUtil.isEmpty(uidSet)) {
            result.setRange(scoreRange);
            return result;
        }
        result.setRange(scoreRange);
        if (!CollectionUtil.isEmpty(uidSet)) {
            List<UserProfile> profileList = userProfileFeignClient.findUserProfilesByUids(uidSet.toArray(new Long[]{}));
            List<ProfileDTO> profileDTOs = new ArrayList<>();
            uidSet.forEach(uid -> profileList.forEach(profile -> {
                if (uid == profile.getId()) {
                    ProfileDTO profileDTO = new ProfileDTO();
                    profileDTO.setNick(profile.getNick());
                    profileDTO.setDescription(StringUtil.isEmpty(profile.getDiscription()) ? "" : profile.getDiscription());
                    profileDTO.setUid(profile.getId());
                    profileDTO.setIcon(StringUtil.isEmpty(profile.getIcon()) ? "" : profile.getIcon());
                    profileDTOs.add(profileDTO);
                }
            }));
            result.setRows(profileDTOs);
        }
        return result;
    }

    /**
     * Delete the  comment by id.
     *
     * @param id the id of the entity
     */
    @Override
    public void delete(Long id) {
        log.debug("Request to delete Comment : {}", id);
        commentRepository.delete(id);
    }

    @Override
    public ScoreRangeRows<CommentDTO> queryCommentByGameId(long gameId, int type, ScoreRange scoreRange, Long currentUid) {
        log.debug("Request to get queryCommentByGameId : {}, scoreRange :{}, type:{}", gameId, scoreRange, type);
        ScoreRangeRows<CommentDTO> result = new ScoreRangeRows<>();
        Set<Long> idSet = commentRedisRepository.findCommentListByGameId(type, gameId, scoreRange);
        result.setRange(scoreRange);
        if (CollectionUtil.isEmpty(idSet)) {
            return result;
        }


        List<Comment> comments = new ArrayList<>();
        idSet.forEach(id -> {
            Comment comment = findOne(id);//查询实体
            if (comment != null) {
                comments.add(comment);
            }
        });
        if (CollectionUtil.isEmpty(comments)) {
            return result;
        }
        Set<Long> idsSet = new HashSet<>();
        comments.forEach(comment -> idsSet.add(comment.getUid()));

        //查询用户信息
        List<UserProfile> userProfiles = userProfileFeignClient.findUserProfilesByUids(idsSet.toArray(new Long[]{}));
        Map<Long, UserProfile> map = new HashMap<>();
        userProfiles.forEach((userProfile -> map.put(userProfile.getId(), userProfile)));

        List<CommentDTO> returnCommentDTO = new ArrayList<>();
        comments.forEach(comment -> {
            UserProfile userProfile = map.get(comment.getUid());
            Boolean currentAgreeStatus = currentUid != null && currentUid != 0 ? commentRedisRepository.getUserAgreeStatus(comment.getId(), currentUid) : false;
            CommentDTO commentDTO = buildCommentDTO(comment, userProfile, currentAgreeStatus);
            returnCommentDTO.add(commentDTO);
        });
        result.setRows(returnCommentDTO);
        return result;
    }

    @Override
    public Map<Long, CommentDTO> findCommentByIds(Set<Long> ids, Long currentUid) {
        log.debug("Request to get findCommentByIds : {}", ids);
        Map<Long, CommentDTO> returnMap = new HashMap<>();

        List<Comment> comments = new ArrayList<>();
        ids.forEach(id -> {
            Comment comment = findOne(id);//查询实体
            if (comment != null) {
                comments.add(comment);
            }
        });
        if (CollectionUtil.isEmpty(comments)) {
            return returnMap;
        }
        Set<Long> idsSet = new HashSet<>();
        comments.forEach(comment -> idsSet.add(comment.getUid()));

        //查询用户信息
        List<UserProfile> userProfiles = userProfileFeignClient.findUserProfilesByUids(idsSet.toArray(new Long[]{}));
        Map<Long, UserProfile> map = new HashMap<>();
        userProfiles.forEach((userProfile -> map.put(userProfile.getId(), userProfile)));
        comments.forEach(comment -> {
            UserProfile userProfile = map.get(comment.getUid());
            Boolean currentAgreeStatus = currentUid != null && currentUid != 0 ? commentRedisRepository.getUserAgreeStatus(comment.getId(), currentUid) : false;
            CommentDTO commentDTO = buildCommentDTO(comment, userProfile, currentAgreeStatus);
            returnMap.put(comment.getId(), commentDTO);
        });
        return returnMap;
    }

    @Override
    public CommentDetailDTO getCommentDetailById(long id, Long currentUid) {
        log.debug("Request to get getCommentById : {}", id);
        CommentDetailDTO commentDetailDTO = new CommentDetailDTO();
        Comment comment = findOne(id);//点评信息
        if (comment == null) {
            return null;
        }
        //查询当前登录用户是否点赞;
        boolean agreeStatus = false;
        if (currentUid != null && currentUid != 0) {
            agreeStatus = commentRedisRepository.getUserAgreeStatus(id, currentUid);
        }
        //用户信息
        UserProfile userProfile = userProfileFeignClient.findOne(comment.getUid());
        CommentDTO commentDTO = buildCommentDTO(comment, userProfile, agreeStatus);

        //游戏信息
        GameVM gameDTO = gameService.findGameDTOById(comment.getGameId());

        //点击有用人的用户 详情页之需要查7个
        Set<Long> uidSet = commentRedisRepository.findCommentAgreeList(comment.getId(), new ScoreRange(-1, -1, 7, ScoreSort.DESC));
        if (!CollectionUtil.isEmpty(uidSet)) {
            List<UserProfile> profileList = userProfileFeignClient.findUserProfilesByUids(uidSet.toArray(new Long[]{}));
            List<ProfileDTO> profileDTOs = new ArrayList<>();
            uidSet.forEach(uid -> profileList.forEach(profile -> {
                if (uid == profile.getId()) {
                    ProfileDTO profileDTO = new ProfileDTO();
                    profileDTO.setNick(profile.getNick());
                    profileDTO.setDescription(StringUtil.isEmpty(profile.getDiscription()) ? "" : profile.getDiscription());
                    profileDTO.setUid(profile.getId());
                    profileDTO.setIcon(StringUtil.isEmpty(profile.getIcon()) ? "" : profile.getIcon());
                    profileDTOs.add(profileDTO);
                }
            }));

            if (!CollectionUtil.isEmpty(profileDTOs)) {
                commentDetailDTO.setProfile(profileDTOs);
            }
        }
        commentDetailDTO.setComment(commentDTO);
        commentDetailDTO.setGame(gameDTO);
        return commentDetailDTO;
    }

    @Override
    public boolean putAgree(Long id, Long uid) {
        log.debug("Request to get putAgree : {}, {}", id, uid);

        boolean bool = commentRepository.agreeCommentById(id) > 0;
        if (bool) {
            commentRedisRepository.addCommentAgreeList(id, uid);
            Comment comment = findOne(id);
            commentRedisRepository.increaseAgree(comment);
            //添加操作信息
            CommentOperation commentOperation = new CommentOperation();
            commentOperation.setCommentId(comment.getId());
            commentOperation.setUid(uid);
            commentOperation.setCreateTime(new Date());
            commentOperation.setDestUid(comment.getUid());
            commentOperation.setOperateType(CommentOperationType.AGREE.getCode());
            commentOperationService.save(commentOperation);
        }
        return bool;
    }

    @Override
    public boolean getUserAgreeStatus(Long id, Long uid) {
        log.debug("Request to get getUserAgreeStatus : {}, {}", id, uid);
        return commentRedisRepository.getUserAgreeStatus(id, uid);
    }

    @Override
    public ScoreRangeRows<CommentDetailDTO> queryMyComments(Long uid, ScoreRange scoreRange, Long currentUid) {
        log.debug("Request to get queryMyComments : {}, {}", uid, scoreRange);
        ScoreRangeRows<CommentDetailDTO> result = new ScoreRangeRows<>();
        Set<Long> idSet = commentRedisRepository.queryMyCommentsByUid(uid, scoreRange);
        result.setRange(scoreRange);
        List<Comment> comments = new ArrayList<>();
        idSet.forEach(id -> {
            Comment comment = findOne(id);//查询实体
            if (comment != null) {
                comments.add(comment);
            }
        });
        if (CollectionUtil.isEmpty(comments)) {
            return result;
        }

        UserProfile userProfile = userProfileFeignClient.findOne(uid);
        List<CommentDetailDTO> returnCommentDTO = new ArrayList<>();
        Set<Long> gameIdSet = new HashSet<>();
        comments.forEach(comment -> gameIdSet.add(comment.getGameId()));
        Map<Long, GameVM> gameMap = gameService.findByGameVMids(gameIdSet);

        comments.forEach(comment -> {
            CommentDetailDTO commentDetailDTO = new CommentDetailDTO();
            Boolean currentAgreeStatus = currentUid != null && currentUid != 0 ? commentRedisRepository.getUserAgreeStatus(comment.getId(), currentUid) : false;
            commentDetailDTO.setComment(buildCommentDTO(comment, userProfile, currentAgreeStatus));
            commentDetailDTO.setGame(gameMap.get(comment.getGameId()));
            returnCommentDTO.add(commentDetailDTO);
        });
        result.setRows(returnCommentDTO);
        return result;
    }

    @Override
    @Transactional(readOnly = true)
    public Map<Long, Boolean> getUserCommentStatus(long uid, Set<Long> gameIds) {
        log.debug("Request to get getUserCommentStatus : {}, {}", uid, gameIds.toArray());
        Map<Long, Boolean> returnMap = commentRedisRepository.queryMyCommentByGameList(uid, gameIds);
        return returnMap;
    }


    @Override
    public void increaseReplyNumById(Long id, int num) {
        log.debug("Request to get increaseReplyNumById : {}", id);
        boolean bool = false;
        if (num > 0) {
            bool = commentRepository.increaseReplyNum(id) > 0;
        } else {
            bool = commentRepository.reduceReplyNum(id) > 0;
        }

        if (bool) {
            commentRedisRepository.increaseReplyNum(id, num);
        }
    }

    /**
     * @param comment     点评实体
     * @param userProfile 用户实体
     * @param agreeStuts  用户点赞状态
     * @return
     */

    private CommentDTO buildCommentDTO(Comment comment, UserProfile userProfile, boolean agreeStuts) {
        CommentDTO commentDTO = new CommentDTO();
        commentDTO.setId(comment.getId());
        commentDTO.setGameId(comment.getGameId());
        commentDTO.setIcon(userProfile == null ? "" : userProfile.getIcon());
        commentDTO.setNick(userProfile == null ? "" : userProfile.getNick());
        commentDTO.setUid(userProfile == null ? 0 : userProfile.getId());
        commentDTO.setBody(comment.getBody());
        commentDTO.setScore(comment.getScore());
        commentDTO.setTime(comment.getCreateTime().getTime());
        commentDTO.setAgreeNum(comment.getAgreeNum());
        commentDTO.setHasAgree(agreeStuts);
        commentDTO.setReplyNum(comment.getReplyNum());
        commentDTO.setHighQuality(comment.getHighQuality());
        return commentDTO;
    }
}
