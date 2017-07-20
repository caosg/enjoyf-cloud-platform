package com.enjoyf.platform.profileservice.repository.jpa;

import com.enjoyf.platform.profileservice.domain.VerifyProfile;
import com.enjoyf.platform.profileservice.domain.enumeration.VerifyProfileType;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Repository;

import org.springframework.data.jpa.repository.*;


/**
 * Spring Data JPA repository for the VerifyProfile entity.
 */
@SuppressWarnings("unused")
@Repository
public interface VerifyProfileRepository extends JpaRepository<VerifyProfile, Long> {

    Page<VerifyProfile> findAllByVerifyType(VerifyProfileType verifyType, Pageable pageable);
}
