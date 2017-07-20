package com.enjoyf.platform.contentservice.repository.jpa;

import com.enjoyf.platform.contentservice.domain.Feedback;
import org.springframework.stereotype.Repository;

import org.springframework.data.jpa.repository.*;


/**
 * Spring Data JPA repository for the Feedback entity.
 */
@SuppressWarnings("unused")
@Repository
public interface FeedbackRepository extends JpaRepository<Feedback,Long>,JpaSpecificationExecutor {

}
