package com.enjoyf.platform.contentservice.repository.jpa;

import com.enjoyf.platform.contentservice.domain.Game;
import com.enjoyf.platform.contentservice.domain.GameTag;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.repository.*;

import java.util.List;


/**
 * Spring Data JPA repository for the GameTag entity.
 */
@SuppressWarnings("unused")
public interface GameTagRepository extends JpaRepository<GameTag, Long> {


    @Modifying
    @Query("update  GameTag l set l.recommendStatus = ?2 where l.id = ?1 ")
    int setRecommendStatusById(Long id, Integer recommendStatus);

    @Modifying
    @Query("update  GameTag l set l.validStatus = ?2 where l.id = ?1 ")
    int setValidStatusById(Long id, String validStatus);


    @Override
    List<GameTag> findAll(Sort sort);


    GameTag findByTagName(String tagName);


    Page<GameTag> findAllByTagNameLike(Pageable pageable, String tagName);


    Page<GameTag> findAllByValidStatus(Pageable pageable, String validStatus);


    Page<GameTag> findAllByTagNameLikeAndValidStatus(Pageable pageable, String tagName, String validStatus);

}
