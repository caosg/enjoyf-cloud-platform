package com.enjoyf.platform.userservice.repository;

import com.enjoyf.platform.userservice.domain.UserAccount;

import org.springframework.cache.annotation.CacheConfig;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.data.jpa.repository.*;

import java.util.List;
import java.util.Optional;

/**
 * Spring Data JPA repository for the UserAccount entity.
 */
@SuppressWarnings("unused")
@CacheConfig(cacheNames = "userAccounts")
public interface UserAccountRepository extends JpaRepository<UserAccount,Long> {
   Optional<UserAccount>  findOneByAccountNo(String accountNo);
}
