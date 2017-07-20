package com.enjoyf.platform.userservice.repository;

import com.enjoyf.platform.userservice.domain.UserMobile;

import org.springframework.data.jpa.repository.*;

import java.util.Optional;

/**
 * Spring Data JPA repository for the UserMobile entity.
 */
@SuppressWarnings("unused")
public interface UserMobileRepository extends JpaRepository<UserMobile, Long> {

    Optional<UserMobile> findOneByMobileAndProfileKey(String mobile, String profileKey);

    @Modifying
    @Query("delete  from UserMobile as um where um.mobile = ?1 and um.profileKey = ?2 and um.profileNo=?3")
    boolean deleteProfileMobileByMobileAndProfileKeyAndProfileNo(String mobile, String profileKey,String profileNo);
}
