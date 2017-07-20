package com.enjoyf.platform.contentservice.repository.jpa;

import com.enjoyf.platform.contentservice.domain.Content;

import org.springframework.data.jpa.repository.*;

import java.util.List;
import java.util.Set;

/**
 * Spring Data JPA repository for the Content entity.
 */
@SuppressWarnings("unused")
public interface ContentRepository extends JpaRepository<Content, Long> {

    Content findByCommentId(String commentId);

    @Modifying
    @Query("update Content t set t.removeStatus= ?2 where t.id = ?1")
    int setRemoveStatusById(Long id, String removeStatus);

    List<Content> findByIdIn(Set<Long> ids);



}
