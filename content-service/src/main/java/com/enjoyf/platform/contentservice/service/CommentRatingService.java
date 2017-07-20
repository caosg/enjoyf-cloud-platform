package com.enjoyf.platform.contentservice.service;

import com.enjoyf.platform.contentservice.domain.Comment;
import com.enjoyf.platform.contentservice.domain.CommentRating;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;

/**
 * Service Interface for managing CommentRating.
 */
public interface CommentRatingService {

    /**
     * Save a commentRating.
     *
     * @param commentRating the entity to save
     * @return the persisted entity
     */
    CommentRating save(CommentRating commentRating);

    CommentRating update(CommentRating commentRating);

    /**
     * Get all the commentRatings.
     *
     * @param pageable the pagination information
     * @return the list of entities
     */
    Page<CommentRating> findAll(Pageable pageable);

    /**
     * Get the "id" commentRating.
     *
     * @param id the id of the entity
     * @return the entity
     */
    CommentRating findOne(Long id);

    /**
     * Delete the "id" commentRating.
     *
     * @param id the id of the entity
     */
    void delete(Long id);

    CommentRating findOneByGameId(Long gameId);

    CommentRating getCommentRatingInfo(Long gameId);
}
