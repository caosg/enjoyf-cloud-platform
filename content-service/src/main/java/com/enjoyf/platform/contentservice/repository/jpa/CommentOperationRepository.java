package com.enjoyf.platform.contentservice.repository.jpa;

import com.enjoyf.platform.contentservice.domain.CommentOperation;

import org.springframework.data.jpa.repository.*;

import java.util.List;

/**
 * Spring Data JPA repository for the CommentOperation entity.
 */
@SuppressWarnings("unused")
public interface CommentOperationRepository extends JpaRepository<CommentOperation,Long> {

}
