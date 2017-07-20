package com.enjoyf.platform.contentservice.service.impl;

import com.enjoyf.platform.contentservice.feign.UserProfileFeignClient;
import com.enjoyf.platform.contentservice.repository.jpa.CommentRatingRepository;
import com.enjoyf.platform.contentservice.repository.redis.CommentRedisRepository;
import com.enjoyf.platform.contentservice.service.CommentRatingService;
import com.enjoyf.platform.contentservice.domain.CommentRating;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * Service Implementation for managing CommentRating.
 */
@Service
@Transactional
public class CommentRatingServiceImpl implements CommentRatingService {

    private final Logger log = LoggerFactory.getLogger(CommentRatingServiceImpl.class);

    private final CommentRatingRepository commentRatingRepository;
    private final CommentRedisRepository commentRedisRepository;


    public CommentRatingServiceImpl(CommentRatingRepository commentRatingRepository,
                                    CommentRedisRepository commentRedisRepository) {
        this.commentRatingRepository = commentRatingRepository;
        this.commentRedisRepository = commentRedisRepository;
    }

    /**
     * Save a commentRating.
     *
     * @param commentRating the entity to save
     * @return the persisted entity
     */
    @Override
    public CommentRating save(CommentRating commentRating) {
        log.debug("Request to save CommentRating : {}", commentRating);
        CommentRating commentRating1 = findOneByGameId(commentRating.getGameId());
        if (commentRating1 != null) {
            return commentRating1;
        }
        CommentRating result = commentRatingRepository.save(commentRating);
        return result;
    }

    @Override
    public CommentRating update(CommentRating commentRating) {
        log.debug("Request to update CommentRating : {}", commentRating);
        CommentRating result = commentRatingRepository.save(commentRating);
        return result;
    }

    /**
     * Get all the commentRatings.
     *
     * @param pageable the pagination information
     * @return the list of entities
     */
    @Override
    @Transactional(readOnly = true)
    public Page<CommentRating> findAll(Pageable pageable) {
        log.debug("Request to get all CommentRatings");
        Page<CommentRating> result = commentRatingRepository.findAll(pageable);
        return result;
    }

    /**
     * Get one commentRating by id.
     *
     * @param id the id of the entity
     * @return the entity
     */
    @Override
    @Transactional(readOnly = true)
    public CommentRating findOne(Long id) {
        log.debug("Request to get CommentRating : {}", id);
        CommentRating commentRating = commentRatingRepository.findOne(id);
        return commentRating;
    }

    /**
     * Delete the  commentRating by id.
     *
     * @param id the id of the entity
     */
    @Override
    public void delete(Long id) {
        log.debug("Request to delete CommentRating : {}", id);
        commentRatingRepository.delete(id);
    }

    @Override
    public CommentRating findOneByGameId(Long gameId) {
        return commentRatingRepository.findOneByGameId(gameId);
    }

    @Override
    public CommentRating getCommentRatingInfo(Long gameId) {
        log.debug("Request to  getCommentRatingInfo : {}", gameId);
        CommentRating commentRating = commentRedisRepository.getCommentRatingEntity(gameId);
        if (commentRating == null) {
            commentRating = commentRatingRepository.findOneByGameId(gameId);
            if (commentRating != null) {
                commentRedisRepository.addCommentRatingEntity(commentRating);
            } else {
                return commentRating;
            }
        }
        return commentRedisRepository.getCommentRatingByRedis(commentRating);
    }
}
