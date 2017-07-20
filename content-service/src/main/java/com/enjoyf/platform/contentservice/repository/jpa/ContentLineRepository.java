package com.enjoyf.platform.contentservice.repository.jpa;

import com.enjoyf.platform.contentservice.domain.ContentLine;

import org.springframework.data.jpa.repository.*;

import java.util.List;

/**
 * Spring Data JPA repository for the ContentLine entity.
 */
@SuppressWarnings("unused")
public interface ContentLineRepository extends JpaRepository<ContentLine, Long> {


    @Modifying
    @Query("update ContentLine t set  t.validStatus= ?3 where t.linekey = ?2 and t.destId=?1")
    int setValidStatusByLinekeyAndDestId(Long destId, String linekey, String valid_status);


    @Modifying
    @Query("update ContentLine t set  t.score= ?3 where t.linekey = ?2 and t.destId=?1")
    int setScoreByLinekeyAndDestId(Long destId, String linekey, double score);
}
