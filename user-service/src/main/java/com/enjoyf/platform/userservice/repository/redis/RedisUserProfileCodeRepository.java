package com.enjoyf.platform.userservice.repository.redis;

import com.enjoyf.platform.userservice.domain.redis.UserProfileCode;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.data.repository.CrudRepository;

/**
 * Spring Data JPA repository for the UserMobile entity.
 */
public interface RedisUserProfileCodeRepository extends CrudRepository<UserProfileCode, String> {

}
