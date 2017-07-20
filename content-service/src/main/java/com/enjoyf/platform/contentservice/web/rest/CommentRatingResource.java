package com.enjoyf.platform.contentservice.web.rest;

import com.codahale.metrics.annotation.Timed;
import com.enjoyf.platform.autoconfigure.security.EnjoySecurityUtils;
import com.enjoyf.platform.contentservice.domain.Comment;
import com.enjoyf.platform.contentservice.domain.CommentRating;
import com.enjoyf.platform.contentservice.repository.redis.CommentRedisRepository;
import com.enjoyf.platform.contentservice.service.CommentRatingService;
import com.enjoyf.platform.contentservice.service.CommentService;
import com.enjoyf.platform.contentservice.service.GameService;
import com.enjoyf.platform.contentservice.service.dto.GameRatingDTO;
import com.enjoyf.platform.contentservice.web.rest.util.HeaderUtil;
import com.enjoyf.platform.contentservice.web.rest.util.PaginationUtil;
import com.enjoyf.platform.contentservice.web.rest.vm.GameVM;
import io.swagger.annotations.ApiParam;
import io.github.jhipster.web.util.ResponseUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.net.URI;
import java.net.URISyntaxException;
import java.util.List;
import java.util.Optional;

/**
 * REST controller for managing CommentRating.
 */
@RestController
@RequestMapping("/api")
public class CommentRatingResource {

    private final Logger log = LoggerFactory.getLogger(CommentRatingResource.class);

    private static final String ENTITY_NAME = "commentRating";

    private final CommentRatingService commentRatingService;
    private final GameService gameService;
    private final CommentService commentService;

    public CommentRatingResource(CommentRatingService commentRatingService, GameService gameService, CommentService commentService) {
        this.commentRatingService = commentRatingService;
        this.gameService = gameService;
        this.commentService = commentService;
    }

    /**
     * POST  /comment-ratings : Create a new commentRating.
     *
     * @param commentRating the commentRating to create
     * @return the ResponseEntity with status 201 (Created) and with body the new commentRating, or with status 400 (Bad Request) if the commentRating has already an ID
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PostMapping("/comment-ratings")
    @Timed
    public ResponseEntity<CommentRating> createCommentRating(@RequestBody CommentRating commentRating) throws URISyntaxException {
        log.debug("REST request to save CommentRating : {}", commentRating);
        if (commentRating.getId() != null) {
            return ResponseEntity.badRequest().headers(HeaderUtil.createFailureAlert(ENTITY_NAME, "idexists", "A new commentRating cannot already have an ID")).body(null);
        }
        CommentRating result = commentRatingService.save(commentRating);
        return ResponseEntity.created(new URI("/api/comment-ratings/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * PUT  /comment-ratings : Updates an existing commentRating.
     *
     * @param commentRating the commentRating to update
     * @return the ResponseEntity with status 200 (OK) and with body the updated commentRating,
     * or with status 400 (Bad Request) if the commentRating is not valid,
     * or with status 500 (Internal Server Error) if the commentRating couldnt be updated
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
//    @PutMapping("/comment-ratings")
//    @Timed
//    public ResponseEntity<CommentRating> updateCommentRating(@RequestBody CommentRating commentRating) throws URISyntaxException {
//        log.debug("REST request to update CommentRating : {}", commentRating);
//        if (commentRating.getId() == null) {
//            return createCommentRating(commentRating);
//        }
//        CommentRating result = commentRatingService.save(commentRating);
//        return ResponseEntity.ok()
//            .headers(HeaderUtil.createEntityUpdateAlert(ENTITY_NAME, commentRating.getId().toString()))
//            .body(result);
//    }

    /**
     * GET  /comment-ratings : get all the commentRatings.
     *
     * @param pageable the pagination information
     * @return the ResponseEntity with status 200 (OK) and the list of commentRatings in body
     */
//    @GetMapping("/comment-ratings")
//    @Timed
//    public ResponseEntity<List<CommentRating>> getAllCommentRatings(@ApiParam Pageable pageable) {
//        log.debug("REST request to get a page of CommentRatings");
//        Page<CommentRating> page = commentRatingService.findAll(pageable);
//        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(page, "/api/comment-ratings");
//        return new ResponseEntity<>(page.getContent(), headers, HttpStatus.OK);
//    }

    /**
     * GET  /comment-ratings/:id : get the "id" commentRating.
     *
     * @param gameId the id of the commentRating to retrieve
     * @return the ResponseEntity with status 200 (OK) and with body the commentRating, or with status 404 (Not Found)
     */
    @GetMapping("/comment-ratings/{gameId}")
    @Timed
    public ResponseEntity<GameRatingDTO> getCommentRating(@PathVariable Long gameId) {
        log.debug("REST request to get CommentRating : {}", gameId);
        CommentRating commentRating = commentRatingService.getCommentRatingInfo(gameId);
        GameRatingDTO gameRatingDTO = new GameRatingDTO();

        if (commentRating != null) {
            Comment comment = null;
            Long uid = EnjoySecurityUtils.getCurrentUid();
            if (uid != null && uid != 0) {
                comment = commentService.findGameIdAndUid(gameId, uid);
            }

            GameVM gameVM = gameService.findGameDTOById(commentRating.getGameId());
            gameRatingDTO.setId(commentRating.getId());
            gameRatingDTO.setGameId(gameId);
            gameRatingDTO.setGameName(gameVM == null ? "" : gameVM.getName());
            gameRatingDTO.setGameIcon(gameVM == null ? "" : gameVM.getGameLogo());
            gameRatingDTO.setScoreSum(commentRating.getScoreSum());
            gameRatingDTO.setScoreNum(commentRating.getScoreNum());
            gameRatingDTO.setFiveUserSum(commentRating.getFiveUserSum());
            gameRatingDTO.setFourUserSum(commentRating.getFourUserSum());
            gameRatingDTO.setThreeUserSum(commentRating.getThreeUserSum());
            gameRatingDTO.setTwoUserSum(commentRating.getTwoUserSum());
            gameRatingDTO.setOneUserSum(commentRating.getOneUserSum());
            gameRatingDTO.setCreateTime(commentRating.getCreateTime().getTime());
            gameRatingDTO.setModifyTime(commentRating.getModifyTime().getTime());
            gameRatingDTO.setHasComment(comment != null ? true : false);
        }
        return ResponseUtil.wrapOrNotFound(Optional.ofNullable(gameRatingDTO));
    }

    /**
     * DELETE  /comment-ratings/:id : delete the "id" commentRating.
     *
     * @param id the id of the commentRating to delete
     * @return the ResponseEntity with status 200 (OK)
     */
//    @DeleteMapping("/comment-ratings/{id}")
//    @Timed
//    public ResponseEntity<Void> deleteCommentRating(@PathVariable Long id) {
//        log.debug("REST request to delete CommentRating : {}", id);
//        commentRatingService.delete(id);
//        return ResponseEntity.ok().headers(HeaderUtil.createEntityDeletionAlert(ENTITY_NAME, id.toString())).build();
//    }

}
