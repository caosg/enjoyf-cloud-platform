package com.enjoyf.platform.userservice.repository;

import com.enjoyf.platform.userservice.domain.UserProfile;
import org.springframework.cache.annotation.CacheConfig;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;

import java.util.Collection;
import java.util.List;
import java.util.Optional;
import java.util.Set;

/**
 * Spring Data JPA repository for the UserProfile entity.
 */
@SuppressWarnings("unused")
@CacheConfig(cacheNames = "userProfiles")
public interface UserProfileRepository extends JpaRepository<UserProfile,Long>,JpaSpecificationExecutor {

    Optional<UserProfile> findOneBylowercaseNick(String lowercaseNick);

    Optional<UserProfile>  findOneByProfileNo(String profileNo);

    List<UserProfile> findByProfileNoIn(Collection<String> profileNos);

    @Modifying
    @Query("update UserProfile p set p.flag = ?2 where p.id = ?1")
    int setFlagById(Long id, int flag);

    List<UserProfile> findByIdIn(Set<Long> ids);

    List<UserProfile> findByNickContaining(String nick);
}
