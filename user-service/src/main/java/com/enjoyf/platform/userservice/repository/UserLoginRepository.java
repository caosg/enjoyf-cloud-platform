package com.enjoyf.platform.userservice.repository;

import com.enjoyf.platform.userservice.domain.UserLogin;

import org.springframework.cache.annotation.CacheConfig;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.data.jpa.repository.*;

import java.util.List;
import java.util.Optional;

/**
 * Spring Data JPA repository for the UserLogin entity.
 */
@SuppressWarnings("unused")
public interface UserLoginRepository extends JpaRepository<UserLogin,Long> {

    Optional<UserLogin> findOneByLogin(String login);

    Optional<UserLogin>  findOneByLoginAndLoginDomain(String login,String loginDomain);

    Optional<UserLogin> findOneByAccountNoAndLoginDomain(String accountNo, String loginDomain);

    List<UserLogin>   findAllByAccountNo(String accountNo);

    @Modifying
    @Query("update  UserLogin l set l.accountNo = ?2 where l.id = ?1 ")
    int setAccountNoById(Long id, String accountNo);
}
