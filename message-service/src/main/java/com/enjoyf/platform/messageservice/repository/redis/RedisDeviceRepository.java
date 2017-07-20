package com.enjoyf.platform.messageservice.repository.redis;

import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.stereotype.Component;

import java.util.Set;
import java.util.concurrent.TimeUnit;

/**
 * Spring Data JPA repository for the ProfileRating entity.
 */
@Component
public class RedisDeviceRepository {

    private final String PREFIX_HASH_PROFILE_RATING_ = "messageservice:pushdevice:";


    private StringRedisTemplate redisTemplate;

    public RedisDeviceRepository(StringRedisTemplate redisTemplate) {
        this.redisTemplate = redisTemplate;
    }

    public void setDevice(String appkey, Long uid, String device) {
        redisTemplate.opsForValue().set(getDeviceKey(appkey, uid), device);
    }

    public String getDevice(String appkey, Long uid) {
        return redisTemplate.opsForValue().get(getDeviceKey(appkey, uid));
    }

    public boolean delDevice(String appkey, Long uid) {
        redisTemplate.delete(getDeviceKey(appkey, uid));
        return true;
    }

    private String getDeviceKey(String appkey, Long uid) {
        return PREFIX_HASH_PROFILE_RATING_ + ":" + appkey + ":" + uid;
    }
}
