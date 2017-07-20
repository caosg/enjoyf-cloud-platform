package com.enjoyf.platform.userservice.repository.redis;

import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.stereotype.Component;

import java.util.concurrent.TimeUnit;

/**
 * Spring Data JPA repository for the ProfileRating entity.
 */
@Component
public class RedisProfileRatingRepositoryImpl implements RedisProfileRatingRepository {

    private final String PREFIX_HASH_PROFILE_RATING_ = "profilerating@";

    private final long expiration = 86400;

    private StringRedisTemplate redisTemplate;

    public RedisProfileRatingRepositoryImpl(StringRedisTemplate redisTemplate) {
        this.redisTemplate = redisTemplate;
    }

    @Override
    public long getMobileRating(String key, String type) {
        Object value = redisTemplate.opsForHash().get(PREFIX_HASH_PROFILE_RATING_ + key, type);
        if (value == null) {
            return 0l;
        }
        try {
            return Long.parseLong(String.valueOf(value)) ;
        } catch (Exception e) {
            redisTemplate.opsForHash().delete(PREFIX_HASH_PROFILE_RATING_ + key, type);
            return 0l;
        }
    }

    @Override
    public long incrMobileRating(String key, String type, long value) {
        redisTemplate.expire(PREFIX_HASH_PROFILE_RATING_ + key, expiration, TimeUnit.SECONDS);
        return redisTemplate.opsForHash().increment(PREFIX_HASH_PROFILE_RATING_ + key, type, value);
    }
}
