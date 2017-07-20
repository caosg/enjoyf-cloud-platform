package com.enjoyf.platform.profileservice.repository.redis;

import com.enjoyf.platform.profileservice.domain.VerifyProfile;
import org.springframework.cache.annotation.CacheConfig;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

/**
 * Created by ericliu on 2017/6/15.
 */
public interface RedisVerifyProfileRepository extends CrudRepository<VerifyProfile, Long> {

    VerifyProfile findOneByProfileNo(String profileNo);
}

