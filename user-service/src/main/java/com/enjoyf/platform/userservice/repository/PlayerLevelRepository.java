package com.enjoyf.platform.userservice.repository;

import com.enjoyf.platform.userservice.domain.PlayerLevel;

import org.springframework.data.jpa.repository.*;

import java.util.List;

/**
 * Spring Data JPA repository for the PlayerLevel entity.
 */
@SuppressWarnings("unused")
public interface PlayerLevelRepository extends JpaRepository<PlayerLevel,Long> {

}
