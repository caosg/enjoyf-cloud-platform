package com.enjoyf.platform.messageservice.repository.redis;

import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.stereotype.Component;

import java.time.ZonedDateTime;

/**
 * Created by ericliu on 2017/7/10.
 */
@Component
public class RedisAppPushMessageSetRepository extends AbstractRedisRepository {

    private final String KEY_ZSET_SUBSCRIBE = "messageservice:apppushmessage:subscribe";


    public RedisAppPushMessageSetRepository(StringRedisTemplate redisTemplate) {
        super(redisTemplate);
    }


    public void add(ZonedDateTime sendDate, long msgId) {
        redisTemplate.opsForZSet().add(KEY_ZSET_SUBSCRIBE, String.valueOf(msgId), sendDate.toInstant().getEpochSecond());
    }

    public void remove(long msgId) {
        redisTemplate.opsForZSet().remove(KEY_ZSET_SUBSCRIBE, msgId);
    }
}
