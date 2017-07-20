package com.enjoyf.platform.userservice.repository;

import com.enjoyf.platform.userservice.domain.Player;

import org.springframework.data.jpa.repository.*;

import java.util.List;
import java.util.Set;

/**
 * Spring Data JPA repository for the Player entity.
 */
@SuppressWarnings("unused")
public interface PlayerRepository extends JpaRepository<Player,Long> {

   List<Player> findByProfileNoIn(Set<String> profileNos);

   Player findOneByProfileNo(String profileNo);
}
