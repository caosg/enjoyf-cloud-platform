package com.enjoyf.platform.contentservice.repository.jpa;

import com.enjoyf.platform.contentservice.domain.Game;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.*;

import java.util.List;

/**
 * Spring Data JPA repository for the Game entity.
 */
@SuppressWarnings("unused")
public interface GameRepository extends JpaRepository<Game, Long> {

    Page<Game> findAllById(Pageable pageable, Long id);

    Page<Game> findAllByNameLike(Pageable pageable, String name);

    @Modifying
    @Query("update  Game l set l.validStatus = ?2 where l.id = ?1 ")
    int setValidStatusById(Long id, String validStatus);
}
