package com.enjoyf.platform.profileservice.repository.jpa;

import com.enjoyf.platform.profileservice.domain.VertualProfile;
import org.springframework.stereotype.Repository;

import org.springframework.data.jpa.repository.*;

import java.util.List;


/**
 * Spring Data JPA repository for the VertualProfile entity.
 */
@SuppressWarnings("unused")
@Repository
public interface VertualProfileRepository extends JpaRepository<VertualProfile, Long> {

    List<VertualProfile> findByIdIn(Long[] ids);

    List<VertualProfile> findByNickContaining(String nick);
}
