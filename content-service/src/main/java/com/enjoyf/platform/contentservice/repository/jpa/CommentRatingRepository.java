package com.enjoyf.platform.contentservice.repository.jpa;

import com.enjoyf.platform.contentservice.domain.CommentRating;

import org.hibernate.annotations.NamedQuery;
import org.springframework.data.jpa.repository.*;

import java.util.List;

/**
 * Spring Data JPA repository for the CommentRating entity.
 */
@SuppressWarnings("unused")
public interface CommentRatingRepository extends JpaRepository<CommentRating, Long> {

    CommentRating findOneByGameId(Long gameId);

}
