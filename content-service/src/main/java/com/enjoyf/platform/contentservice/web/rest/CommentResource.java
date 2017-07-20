package com.enjoyf.platform.contentservice.web.rest;

import com.codahale.metrics.annotation.Timed;
import com.enjoyf.platform.autoconfigure.context.CommonContextHolder;
import com.enjoyf.platform.autoconfigure.security.EnjoySecurityUtils;
import com.enjoyf.platform.autoconfigure.web.CommonParams;
import com.enjoyf.platform.autoconfigure.web.error.BusinessException;
import com.enjoyf.platform.autoconfigure.web.error.CustomParameterizedException;
import com.enjoyf.platform.common.ResultCodeConstants;
import com.enjoyf.platform.common.util.CollectionUtil;
import com.enjoyf.platform.common.util.StringUtil;
import com.enjoyf.platform.contentservice.domain.Comment;
import com.enjoyf.platform.contentservice.domain.CommentRating;
import com.enjoyf.platform.contentservice.domain.enumeration.UserCommentSumFiled;
import com.enjoyf.platform.contentservice.domain.enumeration.ValidStatus;
import com.enjoyf.platform.contentservice.event.ContentEventProcess;
import com.enjoyf.platform.contentservice.feign.ProfileServiceFeignClient;
import com.enjoyf.platform.contentservice.feign.UserProfileFeignClient;
import com.enjoyf.platform.contentservice.feign.domain.UserProfile;
import com.enjoyf.platform.contentservice.feign.domain.VerifyProfileDTO;
import com.enjoyf.platform.contentservice.feign.domain.VerifyProfileType;
import com.enjoyf.platform.contentservice.repository.redis.CommentRedisRepository;
import com.enjoyf.platform.contentservice.service.CommentRatingService;
import com.enjoyf.platform.contentservice.service.CommentService;
import com.enjoyf.platform.contentservice.service.GameService;
import com.enjoyf.platform.contentservice.service.UserCommentSumService;
import com.enjoyf.platform.contentservice.service.dto.CommentDTO;
import com.enjoyf.platform.contentservice.service.dto.CommentDetailDTO;
import com.enjoyf.platform.contentservice.service.dto.ProfileDTO;
import com.enjoyf.platform.contentservice.web.rest.util.AskUtil;
import com.enjoyf.platform.contentservice.web.rest.util.HeaderUtil;
import com.enjoyf.platform.contentservice.web.rest.util.PaginationUtil;
import com.enjoyf.platform.event.message.wikiapp.WikiAppMessageCommentUsefulEvent;
import com.enjoyf.platform.page.ScoreRange;
import com.enjoyf.platform.page.ScoreRangeRows;
import com.enjoyf.platform.page.ScoreSort;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
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

/**
 * REST controller for managing Comment.
 */
@RestController
@RequestMapping("/api")
public class CommentResource {

    private final Logger log = LoggerFactory.getLogger(CommentResource.class);

    private static final String ENTITY_NAME = "comment";
    private static final String TOOLS_USER_NAME = "toolsadmin";//后台请求的username

    private final CommentService commentService;
    private final UserCommentSumService userCommentSumService;
    private final CommentRatingService commentRatingService;
    private final CommentRedisRepository commentRedisRepository;
    private final GameService gameService;
    private final ContentEventProcess contentEventProcess;
    private final ProfileServiceFeignClient profileServiceFeignClient;


    @Autowired
    private UserProfileFeignClient userProfileFeignClient;

    public CommentResource(CommentService commentService, CommentRatingService commentRatingService, CommentRedisRepository commentRedisRepository,
                           GameService gameService, UserCommentSumService userCommentSumService, ContentEventProcess contentEventProcess,
                           ProfileServiceFeignClient profileServiceFeignClient) {
        this.commentService = commentService;
        this.commentRatingService = commentRatingService;
        this.commentRedisRepository = commentRedisRepository;
        this.gameService = gameService;
        this.userCommentSumService = userCommentSumService;
        this.contentEventProcess = contentEventProcess;
        this.profileServiceFeignClient = profileServiceFeignClient;
    }

    @Autowired
    public AskUtil askUtil;

    /**
     * POST  /comments : Create a new comment.
     *
     * @param comment the comment to create
     * @return the ResponseEntity with status 201 (Created) and with body the new comment, or with status 400 (Bad Request) if the comment has already an ID
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PostMapping(value = "/comments")
    @ApiOperation(value = "发布点评", response = Comment.class)
    @Timed
    public ResponseEntity<Comment> createComment(@RequestBody Comment comment) throws URISyntaxException {
        log.debug("REST request to save Comment : {}", comment);
        if (comment.getId() != null) {
            return ResponseEntity.badRequest().headers(HeaderUtil.createFailureAlert(ENTITY_NAME, "idexists", "A new comment cannot already have an ID")).body(null);
        }
        Long currentUid = EnjoySecurityUtils.getCurrentUid();
        if (comment.getScore() == 0 || comment.getGameId() == 0l || currentUid == 0l) {
            throw new CustomParameterizedException(ResultCodeConstants.PARAM_EMPTY.getExtmsg());
        }

        UserProfile userProfile = userProfileFeignClient.findOne(currentUid);
        if (userProfile == null) {
            throw new BusinessException(ResultCodeConstants.USERCENTER_PROFILE_NOT_EXISTS.getMsg(), ResultCodeConstants.USERCENTER_PROFILE_NOT_EXISTS.getExtmsg());
        }

        //判断封禁状态 true=可用 false=封禁
        boolean bool = askUtil.checkUserForbidStatus(userProfile.getProfileNo());
        if (!bool) {
            throw new BusinessException(ResultCodeConstants.COMMENT_PROFILE_FORBID.getExtmsg(), ResultCodeConstants.COMMENT_PROFILE_FORBID.getExtmsg());
        }
        Comment getComment = commentService.findGameIdAndUid(comment.getGameId(), currentUid);
        if (getComment != null) {
            throw new BusinessException(ResultCodeConstants.COMMENT_HAS_EXIST.getExtmsg(), ResultCodeConstants.COMMENT_HAS_EXIST.getExtmsg());
        }

        comment.setUid(currentUid);
        //游戏评分表
        CommentRating commentRating = commentRatingService.findOneByGameId(comment.getGameId());
        if (commentRating == null) {
            throw new CustomParameterizedException(ResultCodeConstants.GAMEDB_GAME_NOTEXISTS.getExtmsg());
        }
        //真实评分
        VerifyProfileDTO verifyProfileDTO = profileServiceFeignClient.findOne(currentUid);
        comment.setReal_score(verifyProfileDTO != null && verifyProfileDTO.getVerifyType().equals(VerifyProfileType.VERIFY) ? comment.getScore() : comment.getScore() * 0.8);

        Comment result = null;
        try {

            result = commentService.save(comment);
        } catch (Exception e) {
            log.error("CommentResource error {}", e.getMessage());
            throw new BusinessException("发布失败", "发布失败");
        }
        askUtil.increaseScore(comment, commentRedisRepository, true);
        //查询最新的评分信息 更新游戏表缓存
        commentRating = commentRatingService.getCommentRatingInfo(commentRating.getGameId());
        if (commentRating != null) {
            gameService.updateGame(commentRating.getGameId(), commentRating.getScoreSum(), commentRating.getScoreNum());
            //更新用户计数
            userCommentSumService.increase(currentUid, UserCommentSumFiled.COMMENT, 1);
            commentRatingService.update(commentRating);//更新表评分信息
        }
        return ResponseEntity.created(new URI("/api/comments/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    @PostMapping(value = "/comments/tools")
    @Timed
    public ResponseEntity<Comment> createCommentByTools(@RequestBody Comment comment) throws URISyntaxException {
        log.debug("REST request to save Comment : {}", comment);
        if (comment.getId() != null) {
            return ResponseEntity.badRequest().headers(HeaderUtil.createFailureAlert(ENTITY_NAME, "idexists", "A new comment cannot already have an ID")).body(null);
        }//
        if (comment.getScore() == 0 || comment.getGameId() == 0l || comment.getUid() == 0l) {
            throw new CustomParameterizedException(ResultCodeConstants.PARAM_EMPTY.getExtmsg());
        }
        //游戏评分表
        CommentRating commentRating = commentRatingService.findOneByGameId(comment.getGameId());
        if (commentRating == null) {
            throw new BusinessException(ResultCodeConstants.GAMEDB_GAME_NOTEXISTS.getExtmsg());
        }

        Comment getComment = commentService.findGameIdAndUid(comment.getGameId(), comment.getUid());
        if (getComment != null) {
            return ResponseEntity.badRequest().headers(HeaderUtil.createFailureAlert(ENTITY_NAME, ResultCodeConstants.COMMENT_HAS_EXIST.getMsg(), ResultCodeConstants.COMMENT_HAS_EXIST.getMsg())).body(null);
        }
        //真实评分
        VerifyProfileDTO verifyProfileDTO = profileServiceFeignClient.findOne(comment.getUid());
        comment.setReal_score(verifyProfileDTO != null && verifyProfileDTO.getVerifyType().equals(VerifyProfileType.VERIFY) ? comment.getScore() : comment.getScore() * 0.8);

        Comment result = null;
        try {
            result = commentService.save(comment);
        } catch (Exception e) {
            log.error("CommentResource error {}", e.getMessage());
            throw new BusinessException("发布失败", "发布失败");
        }
        askUtil.increaseScore(comment, commentRedisRepository, true);
        //查询最新的评分信息 更新游戏表缓存
        commentRating = commentRatingService.getCommentRatingInfo(commentRating.getGameId());
        if (commentRating != null) {
            gameService.updateGame(commentRating.getGameId(), commentRating.getScoreSum(), commentRating.getScoreNum());
            //更新用户计数
            userCommentSumService.increase(comment.getUid(), UserCommentSumFiled.COMMENT, 1);
            commentRatingService.update(commentRating);//更新表评分信息
        }
        return ResponseEntity.created(new URI("/api/comments/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * PUT  /comments : Updates an existing comment.
     *
     * @return the ResponseEntity with status 200 (OK) and with body the updated comment,
     * or with status 400 (Bad Request) if the comment is not valid,
     * or with status 500 (Internal Server Error) if the comment couldnt be updated
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PutMapping("/comments")
    @ApiOperation(value = "修改点评", response = Comment.class)
    @Timed
    public ResponseEntity<Comment> updateComment(@RequestParam Long id,
                                                 @RequestParam(required = false) Integer score,
                                                 @RequestParam(required = false) String body) throws URISyntaxException {
        log.debug("REST request to update Comment : {}", id);
        if (score == null || score == 0) {
            //评分不能为空
            throw new BusinessException(ResultCodeConstants.SCORE_IS_NOT_NULL.getExtmsg(), ResultCodeConstants.SCORE_IS_NOT_NULL.getExtmsg());
        }
        Long uid = EnjoySecurityUtils.getCurrentUid();
        if (uid == null || uid == 0) {
            throw new BusinessException(ResultCodeConstants.USER_NOT_LOGIN.getMsg(), ResultCodeConstants.USER_NOT_LOGIN.getExtmsg());
        }

        Comment comment = commentService.findOne(id);
        if (comment == null) {
            throw new BusinessException("ID不存在", String.valueOf(id));
        }
        if (comment.getUid() != uid) {
            throw new BusinessException(ResultCodeConstants.USER_NOT_LOGIN.getMsg(), ResultCodeConstants.USER_NOT_LOGIN.getExtmsg());
        }
        Comment oldComment = new Comment();
        oldComment.setScore(comment.getScore());
        oldComment.setReal_score(comment.getReal_score());

        comment.score(score);
        //真实评分
        VerifyProfileDTO verifyProfileDTO = profileServiceFeignClient.findOne(comment.getUid());
        comment.setReal_score(verifyProfileDTO != null && verifyProfileDTO.getVerifyType().equals(VerifyProfileType.VERIFY) ? comment.getScore() : comment.getScore() * 0.8);

        if (!StringUtil.isEmpty(body)) {
            comment.body(body);
        }

        comment.modifyTime(new Date());
        Comment result = commentService.save(comment);
        if (score != oldComment.getScore()) {
            askUtil.modifyInscreaseScore(comment, oldComment, commentRedisRepository);
            //查询最新的评分信息 更新游戏表缓存
            CommentRating commentRating = commentRatingService.getCommentRatingInfo(comment.getGameId());
            if (commentRating != null) {
                gameService.updateGame(commentRating.getGameId(), commentRating.getScoreSum(), commentRating.getScoreNum());
                commentRatingService.update(commentRating);//更新表评分信息
            }
        }
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(ENTITY_NAME, comment.getId().toString()))
            .body(result);
    }

    /**
     * GET  /comments : get all the comments.
     *
     * @return the ResponseEntity with status 200 (OK) and the list of comments in body
     */
    @GetMapping("/comments")
    @Timed
    public ResponseEntity<List<CommentDTO>> getAllComments(@ApiParam Pageable pageable,
                                                           @RequestParam(value = "validStatus", defaultValue = "valid") String validStatus,
                                                           @RequestParam(value = "gameId", defaultValue = "0") Long gameId,
                                                           @RequestParam(value = "id", defaultValue = "0") Long id,
                                                           @RequestParam(value = "body", defaultValue = "") String body) {
        log.debug("REST request to get a page of Comments");
        Page<Comment> page = commentService.findAll(validStatus, gameId, id, body, pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(page, "/api/comments");
        List<CommentDTO> returnList = new ArrayList<>();
        if (!CollectionUtil.isEmpty(page.getContent())) {
            returnList = askUtil.queryCommentDTOByParam(page.getContent());
        }
        return new ResponseEntity<>(returnList, headers, HttpStatus.OK);
    }

    @GetMapping("/comments/{gameId}")
    @Timed
    public ResponseEntity<ScoreRangeRows> queryCommentByGameId(@PathVariable Long gameId,
                                                               @RequestParam(value = "psize", defaultValue = "15") Integer pageSize,
                                                               @RequestParam(name = "flag", defaultValue = "-1") Double flag,
                                                               @RequestParam(name = "type", defaultValue = "0") int type) {
        log.debug("REST request to queryCommentByGameId Comment : {}", gameId);
        try {

            Long currentUid = EnjoySecurityUtils.getCurrentUid();
            ScoreRange scoreRange = new ScoreRange(-1, flag, pageSize, ScoreSort.DESC);
            ScoreRangeRows<CommentDTO> scoreRangeRows = commentService.queryCommentByGameId(gameId, type, scoreRange, currentUid);
            scoreRangeRows.setTotalRows(commentRedisRepository.getCommentCount(gameId).intValue());
            return ResponseEntity.ok(scoreRangeRows);
        } catch (Exception e) {
            log.error("queryCommentByGameId.error.e ", e);
            throw new BusinessException("system.error", "");
        }
    }

    @GetMapping("/comments/detail/{id}")
    @ApiOperation(value = "点评详情", response = CommentDTO.class)
    @Timed
    public ResponseEntity<CommentDetailDTO> getCommentDetail(@PathVariable Long id) {
        log.debug("REST request to getCommentDetail Comment : {}", id);

        Long uid = EnjoySecurityUtils.getCurrentUid();
        CommentDetailDTO commentDetailDTO = commentService.getCommentDetailById(id, uid);
        return new ResponseEntity<>(commentDetailDTO, HttpStatus.OK);
    }

    @PutMapping("/comments/agree/{id}")
    @ApiOperation(value = "点赞", response = Boolean.class)
    @Timed
    public ResponseEntity<Boolean> agreeComment(@PathVariable Long id) {
        log.debug("REST request to agreeComment Comment : {}", id);
        Long uid = EnjoySecurityUtils.getCurrentUid();
        if (uid == null || uid == 0) {
            throw new BusinessException(ResultCodeConstants.USER_NOT_LOGIN.getExtmsg(), ResultCodeConstants.USER_NOT_LOGIN.getExtmsg());
        }

        Comment comment = commentService.findOne(id);
        if (comment == null) {
            throw new BusinessException(ResultCodeConstants.COMMENT_BEAN_NULL.getExtmsg(), ResultCodeConstants.COMMENT_BEAN_NULL.getExtmsg());
        }

        boolean userStatusBoolean = commentService.getUserAgreeStatus(id, uid);
        if (userStatusBoolean) {
            throw new BusinessException(ResultCodeConstants.COMMENT_HAS_AGREE.getExtmsg(), ResultCodeConstants.COMMENT_HAS_AGREE.getExtmsg());
        }

        boolean bool = commentService.putAgree(id, uid);
        if (bool) {
            //更新用户计数
            userCommentSumService.increase(comment.getUid(), UserCommentSumFiled.USEFUL, 1);
            CommonParams commonParams = CommonContextHolder.getContext().getCommonParams();
            //发送点赞消息
            WikiAppMessageCommentUsefulEvent send = new WikiAppMessageCommentUsefulEvent();
            send.setUid(comment.getUid());
            send.setDestUid(uid);
            send.setCommentId(comment.getId());
            send.setAppkey(commonParams != null ? commonParams.getAppkey() : "");
            send.setGameId(comment.getGameId());
            contentEventProcess.send(send);
        }
        return new ResponseEntity<>(bool, HttpStatus.OK);
    }

    @GetMapping("/comments/mycomments")
    @ApiOperation(value = "我的点评")
    @Timed
    public ResponseEntity<ScoreRangeRows> queryCommentByGameId(@RequestParam(value = "psize", defaultValue = "15") Integer pageSize,
                                                               @RequestParam(value = "flag", defaultValue = "-1") Double flag,
                                                               @RequestParam(value = "uid", required = false, defaultValue = "0") Long uid) {
        log.debug("REST request to queryCommentByGameId Comment : {}");
        ScoreRangeRows<CommentDetailDTO> scoreRangeRows = new ScoreRangeRows<>();
        if (uid == null || uid == 0) {
            throw new BusinessException(ResultCodeConstants.USER_NOT_LOGIN.getExtmsg(), ResultCodeConstants.USER_NOT_LOGIN.getExtmsg());
        }
        Long currentUid = EnjoySecurityUtils.getCurrentUid();
        ScoreRange scoreRange = new ScoreRange(-1, flag, pageSize, ScoreSort.DESC);
        scoreRangeRows = commentService.queryMyComments(uid, scoreRange, currentUid);
        scoreRangeRows.setTotalRows(commentRedisRepository.getUserCommentCount(uid).intValue());
        return new ResponseEntity<>(scoreRangeRows, HttpStatus.OK);
    }

    @DeleteMapping("/comments/{id}")
    @ApiOperation(value = "删除点评")
    @Timed
    public ResponseEntity<Boolean> deleteComment(@PathVariable Long id) {
        log.debug("REST request to deleteComment Comment : {}", id);
        Long uid = EnjoySecurityUtils.getCurrentUid();
        if (uid == null || uid == 0) {
            throw new BusinessException(ResultCodeConstants.USER_NOT_LOGIN.getExtmsg(), ResultCodeConstants.USER_NOT_LOGIN.getExtmsg());
        }
        Comment comment = commentService.findOne(id);
        if (uid != comment.getUid()) {
            throw new BusinessException(ResultCodeConstants.USER_NOT_LOGIN.getExtmsg(), ResultCodeConstants.USER_NOT_LOGIN.getExtmsg());
        }

        boolean bool = commentService.deleteCommentById(id, ValidStatus.REMOVED.getCode());
        if (bool) {
            askUtil.increaseScore(comment, commentRedisRepository, false);
            //查询最新的评分信息 更新游戏表缓存
            CommentRating commentRating = commentRatingService.getCommentRatingInfo(comment.getGameId());
            if (commentRating != null) {
                gameService.updateGame(commentRating.getGameId(), commentRating.getScoreSum(), commentRating.getScoreNum());
                //更新用户计数
                userCommentSumService.increase(comment.getUid(), UserCommentSumFiled.COMMENT, -1);
                commentRatingService.update(commentRating);//更新表评分信息
            }
        }
        return new ResponseEntity<>(bool, HttpStatus.OK);
    }


    @PutMapping("/comments/tools/{id}")
    public ResponseEntity<Boolean> modifyComments(@PathVariable Long id,
                                                  @RequestParam(value = "status", required = false) String status,
                                                  @RequestParam(value = "uid", required = false) String uid,
                                                  @RequestParam(value = "gameId", required = false) String gameId) {
        if(ValidStatus.VALID.name().equals(status)){
            Comment getComment = commentService.findGameIdAndUid(Long.parseLong(gameId), Long.parseLong(uid));
            if (getComment != null) {
                return ResponseEntity.badRequest().headers(HeaderUtil.createFailureAlert(ENTITY_NAME, ResultCodeConstants.COMMENT_HAS_EXIST.getMsg(), ResultCodeConstants.COMMENT_HAS_EXIST.getMsg())).body(null);
            }
        }
        boolean bool = commentService.deleteCommentById(id, status);
        return new ResponseEntity<>(bool, HttpStatus.OK);
    }

    /**
     * 评论成功OR删除成功后回调接口  type=1 回复成功  type=0删除成功
     *
     * @param id
     * @param type
     * @return
     */
    @PutMapping("/comments/tools/increply/{id}")
    public ResponseEntity<String> increaseReplyById(@PathVariable Long id,
                                                    @RequestParam(value = "type", required = false) String type) {
        Comment comment = commentService.findOne(id);
        if (comment == null) {
            return ResponseEntity.badRequest().headers(HeaderUtil.createFailureAlert(ENTITY_NAME, ResultCodeConstants.COMMENT_HAS_EXIST.getMsg(), ResultCodeConstants.COMMENT_HAS_EXIST.getMsg())).body("error");
        }
        int num = "1".equals(type) ? 1 : -1;
        commentService.increaseReplyNumById(id, num);
        return new ResponseEntity<>("success", HttpStatus.OK);
    }

    @GetMapping("/comments/agreelist/{id}")
    @ApiOperation("点赞用户信息")
    @Timed
    public ResponseEntity<ScoreRangeRows<ProfileDTO>> queryAgreeList(@PathVariable Long id,
                                                                     @RequestParam(value = "psize", defaultValue = "15") Integer pageSize,
                                                                     @RequestParam(name = "flag", defaultValue = "-1") Double flag) {
        log.debug("REST request to queryAgreeList Comment : {}", id);
        ScoreRange scoreRange = new ScoreRange(-1, flag, pageSize, ScoreSort.DESC);
        ScoreRangeRows<ProfileDTO> scoreRangeRows = commentService.queryAgreeListByCommentId(id, scoreRange);
        scoreRangeRows.setTotalRows(commentRedisRepository.getAgreeCountByCommentId(id).intValue());
        return new ResponseEntity<>(scoreRangeRows, HttpStatus.OK);
    }

    @PutMapping("/comments/detail/{id}")
    @Timed
    public ResponseEntity<String> Comment(@PathVariable Long id,
                                          @RequestParam(value = "status", defaultValue = "0") String status) {
        log.debug("REST request to deleteComment Comment : {}", id);
        Long uid = EnjoySecurityUtils.getCurrentUid();
        if (uid == null || uid == 0) {
            throw new BusinessException(ResultCodeConstants.USER_NOT_LOGIN.getExtmsg(), ResultCodeConstants.USER_NOT_LOGIN.getExtmsg());
        }
        String userName = EnjoySecurityUtils.getCurrentUserName();//登录用户名
        if (!TOOLS_USER_NAME.equals(userName)) {
            throw new BusinessException(ResultCodeConstants.USER_NOT_LOGIN.getExtmsg(), ResultCodeConstants.USER_NOT_LOGIN.getExtmsg());
        }
        Comment comment = commentService.findOne(id);
        if (comment != null) {
            comment.setHighQuality(Integer.parseInt(status));
            commentService.update(comment);
        }
        return new ResponseEntity<>(ResultCodeConstants.SUCCESS.getExtmsg(), HttpStatus.OK);
    }


    @GetMapping("/comments/ids")
    public ResponseEntity<Map<Long, CommentDTO>> findCommentByIds(@RequestParam(value = "ids") Long[] ids) {
        log.debug("REST request to findCommentByIds Comment : {}", ids);
        Long currentId = EnjoySecurityUtils.getCurrentUid();
        Map<Long, CommentDTO> returnMap = commentService.findCommentByIds(new HashSet<>(Arrays.asList(ids)), currentId);
        return ResponseEntity.ok(returnMap);
    }
    /**
     * GET  /comments/:id : get the "id" comment.
     *
     * @param id the id of the comment to retrieve
     * @return the ResponseEntity with status 200 (OK) and with body the comment, or with status 404 (Not Found)
     */
//    @GetMapping("/comments/{id}")
//    @Timed
//    public ResponseEntity<Comment> getComment(@PathVariable Long id) {
//        log.debug("REST request to get Comment : {}", id);
//        Comment comment = commentService.findOne(id);
//        return ResponseUtil.wrapOrNotFound(Optional.ofNullable(comment));
//    }

    /**
     * DELETE  /comments/:id : delete the "id" comment.
     *
     * @param id the id of the comment to delete
     * @return the ResponseEntity with status 200 (OK)
     */
//    @DeleteMapping("/comments/{id}")
//    @Timed
//    public ResponseEntity<Void> deleteComment(@PathVariable Long id) {
//        log.debug("REST request to delete Comment : {}", id);
//        commentService.delete(id);
//        return ResponseEntity.ok().headers(HeaderUtil.createEntityDeletionAlert(ENTITY_NAME, id.toString())).build();
//    }

}
