package com.enjoyf.platform.messageservice.repository.redis;

import com.enjoyf.platform.messageservice.domain.AppUserMessage;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Component;

/**
 * Spring Data JPA repository for the ProfileRating entity.
 */
@Component
public interface RedisAppUserMessageRepository extends CrudRepository<AppUserMessage, Long> {

}
