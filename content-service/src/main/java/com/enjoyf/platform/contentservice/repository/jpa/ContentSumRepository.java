package com.enjoyf.platform.contentservice.repository.jpa;

import com.enjoyf.platform.contentservice.domain.contentsum.ContentSum;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;

/**
 * Spring Data JPA repository for the ContentSum entity.
 */
@SuppressWarnings("unused")
public interface ContentSumRepository extends JpaRepository<ContentSum, Long> {

    @Modifying
    @Query("update ContentSum c set c.agree_num=c.agree_num+?1  where c.id=?2")
    int increaseContentSumAgreeNum(int i, Long contentId);
}
